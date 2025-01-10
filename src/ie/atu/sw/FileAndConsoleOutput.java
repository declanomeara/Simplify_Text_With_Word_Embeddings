package ie.atu.sw;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileAndConsoleOutput implements OutputStrategy {

	@Override
	public void outPutTopMatch(String word, double score, String outputFilePath, int calculationOption) {
		// TODO Auto-generated method stub

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFilePath))) {
			String comparisonMethod = getComparisonMethodString(calculationOption);

			// Print headers
			printConsoleAndFileHeader(comparisonMethod, bw);

			// Output the top match
			System.out.printf("%-20s %10.10f%n", word, score);
			bw.write(String.format("%-20s %10.10f%n", word, score));
			bw.newLine();

			MessageUtil.displayMessage("Result also written to " + outputFilePath, ConsoleColour.BLUE_BOLD);
			bw.flush();

		} catch (IOException ex) {
			MessageUtil.displayMessage("Error writing to file", ConsoleColour.RED_BOLD);
			System.err.println(ex.getMessage());
		}
	}

	// Helper method to get the comparison method string
	private String getComparisonMethodString(int calculationOption) {
		return switch (calculationOption) {
		case 3 -> "Cosine Similarity";
		case 4 -> "Dot Product Similarity";
		case 5 -> "Euclidean Similarity";
		case 6 -> "Manhattan Distance";
		default -> "Unknown Method";
		};

	}

	// Helper method to print headers to both console and file
	private void printConsoleAndFileHeader(String comparisonMethod, BufferedWriter bw) throws IOException {
		// Console output
		System.out.println();
		System.out.printf("Top most similar match using %s%n", comparisonMethod);
		System.out.println("--------------------------------------");
		System.out.printf("%-20s %-15s%n", "Word", "Score");
		System.out.println();

		// File output
		bw.write("Top most similar match using " + comparisonMethod + "\n");
		bw.write("--------------------------------------\n");
		bw.write(String.format("%-20s %-15s%n", "Word", "Score"));
		bw.newLine();
	}

}
