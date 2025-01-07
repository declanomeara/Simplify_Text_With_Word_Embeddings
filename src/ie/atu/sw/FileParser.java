package ie.atu.sw;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class FileParser {

	// Thread-safe map to store word embeddings
	private final ConcurrentHashMap<String, float[]> embeddings;
	private final int VECTOR_LENGTH = 50; // Embedding vector length
	private final Set<String> googleWords; // Google-1000 words
	private final List<String> textToSimplify; // Text to simplify, stored line-by-line

	// Constructor initializes data structures
	public FileParser() {
		this.embeddings = new ConcurrentHashMap<>();
		this.googleWords = new HashSet<>();
		this.textToSimplify = new ArrayList<>();
	}

	// load the wordembeddings file and parse it to a ConcurrentHashMap
	public void loadEmbeddingsFile(String filePath) {

		// Counter to track the number of processed lines
		AtomicInteger lineCount = new AtomicInteger(0);
		// Counter to track duplicates
		AtomicInteger duplicates = new AtomicInteger(0);

		// Try-with-resources ensures the file reader and executor are closed
		// automatically
		try (var reader = Files.newBufferedReader(Path.of(filePath));

				var executor = java.util.concurrent.Executors.newVirtualThreadPerTaskExecutor()) {

			String line;
			// Read each line in the file
			while ((line = reader.readLine()) != null) {

				// Submit each line for processing in a virtual thread
				final String lineToProcess = line; // Necessary to make it effectively final for lambda
				executor.submit(() -> processLine(lineToProcess, lineCount, duplicates));
			}

			executor.shutdown();

		} catch (Exception e) {

			System.err.println("Error loading embeddings: " + e.getMessage());
			throw new RuntimeException(e);
		}

		// Print summary of processing
		System.out.println();
		System.out.println("Processed " + lineCount.get() + " words");
		System.out.println("Vocabulary size: " + embeddings.size());
		System.out.println("Duplicates Encountered: " + duplicates);
	}

	// load the google-100 file and parse it to a HashSet Data structure
	public void loadGoogleWordsFile(String filePath) {
		try (var reader = Files.newBufferedReader(Path.of(filePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				googleWords.add(line.trim());
			}

		} catch (Exception e) {
			System.err.println("Error loading Google-1000 file: " + e.getMessage());
			throw new RuntimeException(e);
		}

		System.out.println("Loaded " + googleWords.size() + " words from Google-1000 file.");
	}

	// load the text to simplify file and parse it to an ArrayList
	public void loadTextToSimplify(String filePath) {
		try (var reader = Files.newBufferedReader(Path.of(filePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				textToSimplify.add(line.trim());
			}

		} catch (Exception e) {
			System.err.println("Error loading text file: " + e.getMessage());
			throw new RuntimeException(e);
		}

		System.out.println("Loaded " + textToSimplify.size() + " lines of text to simplify.");
	}

	/**
	 * Processes a single line of the embeddings file. The line format is: word dim1
	 * dim2 ... dim50 This method parses the word and its vector and stores it in
	 * the ConcurrentHashMap.
	 *
	 * @param line      The line to process
	 * @param lineCount Atomic counter to track processed lines
	 */
	private void processLine(String line, AtomicInteger lineCount, AtomicInteger duplicates) {
		// Split the line into components: the word and its vector
		String[] parts = line.trim().split(",");
		String word = parts[0]; // The word is the first part
		float[] vector = new float[VECTOR_LENGTH]; // Array to store the vector

		// Parse the vector components
		for (int i = 0; i < VECTOR_LENGTH; i++) {
			vector[i] = Float.parseFloat(parts[i + 1]);
		}

		// Add the word and its vector to the embeddings map and check for duplicates
		if (embeddings.put(word, vector) != null) {
			duplicates.incrementAndGet();
		}

		// Add the word and its vector to the embeddings map
		embeddings.put(word, vector);

		// Increment the line count
		lineCount.incrementAndGet();
	}

	// Getters for the parsed Data, hooks for consumption

	public ConcurrentHashMap<String, float[]> getEmbeddings() {
		return embeddings;
	}

	public Set<String> getGoogleWords() {
		return googleWords;
	}

	public List<String> getTextToSimpify() {
		return textToSimplify;
	}

}
