package ie.atu.sw;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileAndConsoleOutput implements OutputStrategy {

	@Override
	public void outputResult(String inputText, String simplifiedText, SimilarityMethod similarityMethod,
			int wordsToSimplify, int wordsInGoogle1000, int wordsNotInEmbeding, String outputFilePath) {

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFilePath))) {

			// Print headers
			printConsoleAndFileHeader(bw, similarityMethod);

			// Output the input and simplified text
			System.out.println("Input Text:");
			System.out.println(inputText);
			System.out.println();

			System.out.println("Simplified Text:");
			System.out.println(simplifiedText);
			System.out.println();

			bw.write("Input Text:\n" + inputText + "\n\n");
			bw.write("Simplified Text:\n" + simplifiedText + "\n\n");

			// Output the processing summary
			printProcessingSummary(bw, wordsToSimplify, wordsInGoogle1000, wordsNotInEmbeding);

			// Ensure everything is written to the file
			bw.flush();
			MessageUtil.displayMessage("Result also written to " + outputFilePath, ConsoleColour.BLUE_BOLD);

		} catch (IOException ex) {
			MessageUtil.displayMessage("Error writing to file", ConsoleColour.RED_BOLD);
			System.err.println(ex.getMessage());
		}
	}

	// Helper method to print headers to both console and file
	private void printConsoleAndFileHeader(BufferedWriter bw, SimilarityMethod similarityMethod) throws IOException {
		// Console output
		System.out.println("---------------------------");
		System.out.println("Simplified Text using " + similarityMethod.getDescription());
		System.out.println("---------------------------");

		// File output
		bw.write("---------------------------\n");
		bw.write("Simplified Text using " + similarityMethod.getDescription() + "\n");
		bw.write("---------------------------\n");
		bw.newLine();
	}

	// Helper method to print the processing summary
	private void printProcessingSummary(BufferedWriter bw, int wordsToSimplify, int wordsInGoogle1000,
			int wordsNotInEmbedding) throws IOException {
		// Console output
		System.out.println("---------------------------");
		System.out.println("Processing Summary");
		System.out.println("---------------------------");
		System.out.println("Number of words to simplify: " + wordsToSimplify);
		System.out.println("Number of words already in Google-1000: " + wordsInGoogle1000);
		System.out.println("Number of words not in embedding file: " + wordsNotInEmbedding);

		// File output
		bw.write("---------------------------\n");
		bw.write("Processing Summary\n");
		bw.write("---------------------------\n");
		bw.write("Number of words to simplify: " + wordsToSimplify + "\n");
		bw.write("Number of words already in Google-1000: " + wordsInGoogle1000 + "\n");
		bw.write("Number of words not in embedding file: " + wordsNotInEmbedding + "\n");
		bw.newLine();
	}

}
