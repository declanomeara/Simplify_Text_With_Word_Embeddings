
package ie.atu.sw;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JsonOutputStrategy implements OutputStrategy {

    @Override
    public void outputResult(String inputText, String simplifiedText, SimilarityMethod similarityMethod,
            int wordsToSimplify, int wordsInGoogle1000, int wordsNotInEmbeddings, String outputFilePath) {
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            // Create JSON structure using nested maps
            Map<String, Object> jsonOutput = new HashMap<>();
            
            // Add text content
            jsonOutput.put("inputText", escapeJsonString(inputText));
            jsonOutput.put("simplifiedText", escapeJsonString(simplifiedText));
            jsonOutput.put("similarityMethod", similarityMethod.getDescription());
            
            // Add Summary as a nested object
            Map<String, Integer> statistics = new HashMap<>();
            statistics.put("wordsToSimplify", wordsToSimplify);
            statistics.put("wordsInGoogle1000", wordsInGoogle1000);
            statistics.put("wordsNotInEmbeddings", wordsNotInEmbeddings);
            jsonOutput.put("Summary", statistics);

            // Convert map to JSON string
            String jsonString = convertToJsonString(jsonOutput);
            
            // Write to file
            writer.write(jsonString);
            writer.flush();
            
            // Display success message
            MessageUtil.displayMessage("Result written to " + outputFilePath + " in JSON format", ConsoleColour.BLUE_BOLD);
            
            // Also display JSON to console
            System.out.println("\nJSON Output:");
            System.out.println(jsonString);

        } catch (IOException e) {
            MessageUtil.displayMessage("Error writing JSON output: " + e.getMessage(), ConsoleColour.RED_BOLD);
        }
    }

    /**
     * Converts a Map to a JSON string.
     * @param map The map to convert
     * @return A properly formatted JSON string
     */
    private String convertToJsonString(Map<String, Object> map) {
        StringBuilder json = new StringBuilder();
        json.append("{\n");
        
        int count = 0;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (count > 0) {
                json.append(",\n");
            }
            json.append("  \"").append(entry.getKey()).append("\": ");
            
            Object value = entry.getValue();
            if (value instanceof Map) {
                // Handle nested maps (for statistics)
                @SuppressWarnings("unchecked")
                Map<String, Object> nestedMap = (Map<String, Object>) value;
                json.append(convertNestedMapToJson(nestedMap));
            } else if (value instanceof String) {
                // Handle string values
                json.append("\"").append(value).append("\"");
            } else {
                // Handle other values (numbers, etc.)
                json.append(value);
            }
            count++;
        }
        
        json.append("\n}");
        return json.toString();
    }

    /**
     * Converts a nested map to JSON string.
     * @param map The nested map to convert
     * @return A properly formatted JSON string for the nested object
     */
    private String convertNestedMapToJson(Map<String, Object> map) {
        StringBuilder json = new StringBuilder();
        json.append("{\n");
        
        int count = 0;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (count > 0) {
                json.append(",\n");
            }
            json.append("    \"").append(entry.getKey()).append("\": ").append(entry.getValue());
            count++;
        }
        
        json.append("\n  }");
        return json.toString();
    }

    /**
     * Escapes special characters in JSON strings.
     * @param input The string to escape
     * @return The escaped string
     */
    private String escapeJsonString(String input) {
        return input.replace("\\", "\\\\")
                   .replace("\"", "\\\"")
                   .replace("\n", "\\n")
                   .replace("\r", "\\r")
                   .replace("\t", "\\t");
    }
}

