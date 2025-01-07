package ie.atu.sw;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class FileParser {

    // Thread-safe map to store word embeddings
    private final ConcurrentHashMap<String, float[]> embeddings;

    // Expected number of dimensions in the embedding vectors
    private final int VECTOR_LENGTH = 50;

    // Constructor initializes the ConcurrentHashMap
    public FileParser() {
        this.embeddings = new ConcurrentHashMap<>();
    }

    /**
     * Loads and parses the embeddings file.
     * Each line in the file represents a word and its corresponding vector.
     * The file is processed line-by-line using virtual threads for scalability.
     *
     * @param filePath Path to the embeddings file
     */
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
                executor.submit(() -> processLine(lineToProcess, lineCount,duplicates));
            }

        } catch (Exception e) {
            // Catch and rethrow any exception that occurs during file loading
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
     * Processes a single line of the embeddings file.
     * The line format is: word dim1 dim2 ... dim50
     * This method parses the word and its vector and stores it in the ConcurrentHashMap.
     *
     * @param line       The line to process
     * @param lineCount  Atomic counter to track processed lines
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

    /**
     * Returns the parsed embeddings map.
     * The map keys are words, and the values are their corresponding vectors.
     *
     * @return ConcurrentHashMap of word embeddings
     */
    public ConcurrentHashMap<String, float[]> getEmbeddings() {
        return embeddings;
    }
}
