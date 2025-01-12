package ie.atu.sw;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class FileParser {

	// Thread-safe map to store word embeddings
	private final ConcurrentHashMap<String, float[]> embeddings;
	private final Map<String, float[]> googleWordEmbeddings; // Google-1000 words
	private final List<String> textToSimplify; // Text to simplify, stored line-by-line
	
	private final int VECTOR_LENGTH = 50; // Embedding vector length

	// Constructors to  initialise
	public FileParser() {
		this.embeddings = new ConcurrentHashMap<>();
		this.googleWordEmbeddings = new HashMap<>();
		this.textToSimplify = new ArrayList<>();
		
	}

	// load the wordembeddings file and parse it to a ConcurrentHashMap
	public void loadEmbeddingsFile(String filePath) {

		// Counter to track the number of processed lines
		AtomicInteger lineCount = new AtomicInteger(0);
		// Counter to track duplicates
		AtomicInteger duplicates = new AtomicInteger(0);

		// Try-with-resources ensures the file reader and executor are closed automatically
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
	
	//threads processing the embeddings file
		private void processLine(String line, AtomicInteger lineCount, AtomicInteger duplicates) {
			// Split the line into components: the word and its vector
			String[] parts = line.trim().split(",");
			String word = parts[0]; // The word is the first part
			float[] vector = new float[VECTOR_LENGTH]; // Array to store the vector

			try {
	            for (int i = 0; i < VECTOR_LENGTH; i++) {
	                vector[i] = Float.parseFloat(parts[i + 1]);
	            }

	            if (embeddings.put(word, vector) != null) {
	                duplicates.incrementAndGet();
	            }

	            lineCount.incrementAndGet();

	        } catch (NumberFormatException e) {
	            System.err.println("[ERROR] Malformed vector for word: " + word);
	        }
		}
	
	
	

	// load the google-100 file and parse it to a HashSet Data structure
	public void loadGoogleWordsFile(String filePath) {
		
		try (var reader = Files.newBufferedReader(Path.of(filePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String word = line.trim();
				float[] vector = embeddings.get(word);

				if(vector !=null) {
                    googleWordEmbeddings.put(word, vector);
				}	
			}

		} catch (Exception e) {
			System.err.println("Error loading Google-1000 file: " + e.getMessage());
			throw new RuntimeException(e);
		}
		System.out.println();
		System.out.println("Loaded " + getEmbeddings().size() + " words from Google-1000 file.");
		
	}

	// load the text to simplify file and parse it to an ArrayList
	public List<String> loadTextToSimplify(String filePath) {
	   this.textToSimplify.clear();

	    try (var reader = Files.newBufferedReader(Path.of(filePath))) {
	        String line;
	        while ((line = reader.readLine()) != null) {
	            this.textToSimplify.add(line.trim());
	        }
	    } catch (Exception e) {
	        System.err.println("Error loading text file: " + e.getMessage());
	        throw new RuntimeException(e);
	    }

	    System.out.println("Loaded " + textToSimplify.size() + " lines of text to simplify.");
	    return textToSimplify; // Return the parsed list
	}


	// Getters for the parsed Data, hooks for consumption

	public ConcurrentHashMap<String, float[]> getEmbeddings() {
		return this.embeddings;
	}

	public Map<String, float[]> getGoogleWords() {
		return this.googleWordEmbeddings;
	}
	

	public List<String> getTextToSimpify() {
		return this.textToSimplify;
	}

}
