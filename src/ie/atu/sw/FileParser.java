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
/**
 * The {@code FileParser} class is responsible for loading and processing files
 * required by the text simplifier application.
 * <p>
 * This class handles embeddings, Google-1000 words, and text files, storing them
 * in appropriate data structures for efficient processing.
 * </p>
 *
 * <h2>Responsibilities:</h2>
 * <ul>
 * <li>Load and parse word embeddings into a thread-safe map.</li>
 * <li>Load and store Google-1000 words for comparison.</li>
 * <li>Load text files to be simplified line by line.</li>
 * </ul>
 *
 * <h2>Usage Example:</h2>
 * <pre>
 * FileParser parser = new FileParser();
 * parser.loadEmbeddingsFile("embeddings.txt");
 * parser.loadGoogleWordsFile("google1000.txt");
 * List<String> text = parser.loadTextToSimplify("input.txt");
 * </pre>
 *
 * @author YourName
 * @version 1.0
 * @since 1.8
 */
public class FileParser {

	// Thread-safe map to store word embeddings
	private final ConcurrentHashMap<String, float[]> embeddings;
	private final Map<String, float[]> googleWordEmbeddings; // Google-1000 words
	private final List<String> textToSimplify; // Text to simplify, stored line-by-line
	
	private final int VECTOR_LENGTH = 50; // Embedding vector length
	/**
     * Constructs a new {@code FileParser} instance.
     */
	// Constructors to  initialise
	public FileParser() {
		this.embeddings = new ConcurrentHashMap<>();
		this.googleWordEmbeddings = new HashMap<>();
		this.textToSimplify = new ArrayList<>();
		
	}
	 /**
     * Loads the word embeddings from a file and stores them in a map.
     * <p>
     * Each line in the file should contain a word followed by its vector values.
     * The vectors are parsed and stored in a thread-safe {@code ConcurrentHashMap}.
     * </p>
     *
     * @param filePath The path to the word embeddings file.
     * @throws RuntimeException If an error occurs while reading the file.
     */
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
	/**
     * Parses a single line of the embeddings file and adds it to the map.
     *
     * @param line The line to process.
     * @param lineCount Counter for processed lines.
     * @param duplicates Counter for duplicate entries.
     */
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
	
	
	
		/**
	     * Loads the Google-1000 words file and stores their embeddings in a map.
	     * <p>
	     * Only words present in the embeddings map are included.
	     * </p>
	     *
	     * @param filePath The path to the Google-1000 words file.
	     * @throws RuntimeException If an error occurs while reading the file.
	     */
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
	 /**
     * Loads the text file to simplify and stores its lines in a list.
     *
     * @param filePath The path to the text file.
     * @return A list of lines from the file.
     * @throws RuntimeException If an error occurs while reading the file.
     */
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
	 /**
     * Retrieves the embeddings map.
     *
     * @return A map of words to their vector embeddings.
     */
	public ConcurrentHashMap<String, float[]> getEmbeddings() {
		return this.embeddings;
	}
	/**
     * Retrieves the Google-1000 words map.
     *
     * @return A map of Google-1000 words to their vector embeddings.
     */
	public Map<String, float[]> getGoogleWords() {
		return this.googleWordEmbeddings;
	}
	
	/**
     * Retrieves the list of text lines to simplify.
     *
     * @return A list of lines from the input text file.
     */
	public List<String> getTextToSimpify() {
		return this.textToSimplify;
	}

}
