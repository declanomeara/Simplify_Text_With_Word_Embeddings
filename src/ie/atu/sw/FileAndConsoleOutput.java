package ie.atu.sw;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileAndConsoleOutput implements OutputStrategy {
	
	

	@Override
	public void outputResult(String inputText, String simplifiedText, SimilarityCalculationType similarityMethod,
			int wordsToSimplify, int wordsInGoogle1000, int wordsNotInEmbeding, String outputFilePath) {

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFilePath))) {

			// Print headers
			printConsoleAndFileHeader(bw, similarityMethod);

			// Output the input and simplified text
			System.out.println("Input Text:");
			System.out.println(MessageUtil.paddingHeaderHelper("Input Text:"));
			System.out.println(inputText);
			System.out.println();

			System.out.println("Simplified Text:");
			System.out.println(MessageUtil.paddingHeaderHelper("Simplified Text:"));
			System.out.println(simplifiedText);
			System.out.println();

			//File output
			
			bw.write("Input Text:\n" + MessageUtil.paddingHeaderHelper("Input Text:") +"\n "+ inputText + "\n\n");
			bw.write("Simplified Text:\n" + MessageUtil.paddingHeaderHelper("Simplified Text:") +"\n "+simplifiedText + "\n\n");

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
	private void printConsoleAndFileHeader(BufferedWriter bw, SimilarityCalculationType similarityMethod) throws IOException {
		
		// Console output
		MessageUtil.displayMessage("Simplified Text using " + similarityMethod.getDescription(), ConsoleColour.GREEN);
		
		String header = MessageUtil.paddingHeaderHelper("Simplified Text using " + similarityMethod.getDescription());
		// File output
		bw.write(header + "\n");
		bw.write("Simplified Text using " + similarityMethod.getDescription() + "\n");
		bw.write(header + "\n");
		bw.newLine();
	}

	// Helper method to print the processing summary
	private void printProcessingSummary(BufferedWriter bw, int wordsToSimplify, int wordsInGoogle1000,
			int wordsNotInEmbedding) throws IOException {		
		//Console Output
		MessageUtil.displayMessage("Processing Summary", ConsoleColour.GREEN);
		
		System.out.println("Number of words found to simplify: " + wordsToSimplify);
		System.out.println("Number of words already in Google-1000: " + wordsInGoogle1000);
		System.out.println("Number of words not in embedding file: " + wordsNotInEmbedding);

		// File output
		 // Use MessageUtil for the header
	    String header = MessageUtil.paddingHeaderHelper("Processing Summary");
	    
		bw.write(header + "\n");
		bw.write("Processing Summary\n");
		bw.write(header + "\n");
		
		bw.write("Number of words found to simplify: " + wordsToSimplify + "\n");
		bw.write("Number of words already in Google-1000: " + wordsInGoogle1000 + "\n");
		bw.write("Number of words not in embedding file: " + wordsNotInEmbedding + "\n");
		bw.newLine();
		
	}

}
