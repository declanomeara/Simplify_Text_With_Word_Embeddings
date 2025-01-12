package ie.atu.sw;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class JsonOutputStrategy implements OutputStrategy {

    @Override
    public void outPutTopMatch(String line, double score, String outputFilePath, int calculationOption) {
        // Example: Write results to a JSON file
        try (var writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            String jsonOutput = String.format("{\"line\": \"%s\", \"score\": %.2f}", line, score);
            writer.write(jsonOutput);
            System.out.println("Results written to JSON file: " + outputFilePath);
        } catch (IOException e) {
            System.err.println("Error writing to JSON file: " + e.getMessage());
        }
    }
}
