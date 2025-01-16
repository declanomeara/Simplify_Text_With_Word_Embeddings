package ie.atu.sw;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


/**
 * Implementation of {@link OutputStrategy} that writes results to both console and file.
 * Provides formatted output with headers, content, and processing summaries.
 * 
 * <p>The output includes:
 * <ul>
 * <li>Input and simplified text with formatted headers</li>
 * <li>Similarity method used for processing</li>
 * <li>Statistical summary of word processing</li>
 * </ul>
 * 
 * @see OutputStrategy
 * @see MessageUtil
 * 
 * @author Declan O'Meara
 * @version 1.0
 * @since 1.8
 * 
 */
public class FileAndConsoleOutput implements OutputStrategy {
	
	 /**
     * Outputs the text simplification results to both console and file.
     * Formats the output with headers and statistical summary.
     * 
     * @param inputText          The original input text
     * @param simplifiedText     The simplified version of the text
     * @param similarityMethod   The similarity calculation method used
     * @param wordsToSimplify    Count of words that needed simplification
     * @param wordsInGoogle1000  Count of words found in Google-1000
     * @param wordsNotInEmbeding Count of words not found in embeddings
     * @param outputFilePath     Path to output file
     */
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
	 /**
     * Prints formatted headers to both console and file output.
     * 
     * @param bw                The BufferedWriter for file output
     * @param similarityMethod  The similarity method used
     * @throws IOException if there's an error writing to file
     */
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
	/**
     * Prints processing statistics summary to both console and file.
     * 
     * @param bw                   The BufferedWriter for file output
     * @param wordsToSimplify      Count of words that needed simplification
     * @param wordsInGoogle1000    Count of words found in Google-1000
     * @param wordsNotInEmbedding  Count of words not found in embeddings
     * @throws IOException if there's an error writing to file
     */
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
