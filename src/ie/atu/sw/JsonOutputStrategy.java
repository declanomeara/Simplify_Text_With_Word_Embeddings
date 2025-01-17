
package ie.atu.sw;

import java.io.FileWriter;
import java.io.IOException;

//Considered using Google GSON in implmentation but decided against due to potential dependencies
// for JAR Export

/**
 * The {@code JsonOutputStrategy} class provides functionality for outputting results
 * in JSON format. It implements the {@link OutputStrategy} interface and is used
 * to generate  a JSON representations of text simplification results.
 * 
 *
 * @see OutputStrategy
 * @see FileAndConsoleOutput
 * @see TextSimplifier
 *
 * @author Declan O'Meara
 * @version 1.0
 * @since 21
 */
public class JsonOutputStrategy implements OutputStrategy {

	
	// https://www.json.org/json-en.html
	// Considered using Google GSON in implmentation but decided against due to potential dependencies for JAR Export
	/**
     * Outputs the result of the text simplification process in JSON format.
     * <p>
     * The JSON includes details about the input text, simplified text, similarity
     * calculation method, and a summary of the processing results.
     * </p>
     *
     * @param inputText          The original input text.
     * @param simplifiedText     The simplified version of the input text.
     * @param similarityMethod   The similarity calculation method used (e.g., Cosine, Dot Product).
     * @param wordsToSimplify    The number of words that were identified for simplification.
     * @param wordsInGoogle1000  The number of words already present in the Google-1000 dataset.
     * @param wordsNotInEmbeddings The number of words not found in the embeddings file.
     * @param outputFilePath     The file path where the JSON output should be saved.
     * 
     * 
     */
    @Override
    public void outputResult(String inputText, String simplifiedText, SimilarityCalculationType similarityMethod,
            int wordsToSimplify, int wordsInGoogle1000, int wordsNotInEmbeddings, String outputFilePath) {//Big-O Notation: O(1) complexity scales linearly with size of strings
         	
    	// Create JSON using StringBuilder
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{\n");

        // Input text
        jsonBuilder.append("  \"inputText\": ").append("\"").append(inputText.replace("\n", "\\n")).append("\",\n");

        // Simplified text
        jsonBuilder.append("  \"simplifiedText\": ").append("\"").append(simplifiedText.replace("\n", "\\n")).append("\",\n");

        // Similarity method
        jsonBuilder.append("  \"similarityMethod\": ").append("\"").append(similarityMethod.getDescription()).append("\",\n");

        // Summary section
        jsonBuilder.append("  \"summary\": {\n");
        jsonBuilder.append("    \"wordsToSimplify\": ").append(wordsToSimplify).append(",\n");
        jsonBuilder.append("    \"wordsInGoogle1000\": ").append(wordsInGoogle1000).append(",\n");
        jsonBuilder.append("    \"wordsNotInEmbeddings\": ").append(wordsNotInEmbeddings).append("\n");
        jsonBuilder.append("  }\n");

        jsonBuilder.append("}");

        // Convert to string
        String jsonOutput = jsonBuilder.toString();

        // Print JSON to console
        System.out.println(jsonOutput);

        // Write JSON to file
        try (FileWriter writer = new FileWriter(outputFilePath)) {
            writer.write(jsonOutput);
            System.out.println("[INFO] JSON output written to " + outputFilePath);
        } catch (IOException e) {
            System.err.println("[ERROR] Failed to write JSON output: " + e.getMessage());
        }
    	
    	
    }
}

