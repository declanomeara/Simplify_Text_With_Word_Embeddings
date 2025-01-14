
package ie.atu.sw;

import java.io.FileWriter;
import java.io.IOException;

public class JsonOutputStrategy implements OutputStrategy {

	
	//https://www.json.org/json-en.html
	//looked at Google GSON library but decided against due to dependencies and JAR export.
    @Override
    public void outputResult(String inputText, String simplifiedText, SimilarityCalculationType similarityMethod,
            int wordsToSimplify, int wordsInGoogle1000, int wordsNotInEmbeddings, String outputFilePath) {
         	
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

