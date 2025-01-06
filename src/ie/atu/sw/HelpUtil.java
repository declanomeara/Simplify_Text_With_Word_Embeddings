package ie.atu.sw;

public class HelpUtil {

	// Help menu options
	public static void displayHelp() {
		System.out.println();
		System.out.println();
		System.out.println(ConsoleColour.BLUE_BOLD + "********************************************************");
		System.out.println("*                  HELP INFORMATION                    *");
		System.out.println("********************************************************" + ConsoleColour.RESET);
		System.out.println();

		System.out.println(ConsoleColour.GREEN_BOLD + "(1) Specify Embedding File" + ConsoleColour.RESET);
		System.out.println("    - Sets the file path for the word embeddings file to be used by the application.");
		System.out.println();

		System.out.println(ConsoleColour.GREEN_BOLD + "(2) Specify an Output File" + ConsoleColour.RESET);
		System.out.println(
				"    - Sets the file path for the output file where results will be saved (default is ./out.txt).");
		System.out.println();

		System.out.println(
				ConsoleColour.GREEN_BOLD + "(3) Find Most/Least Similar (Cosine Similarity)" + ConsoleColour.RESET);
		System.out.println("    - Enter a word or text to find the most/least similar words using cosine similarity.");
		System.out.println();

		System.out
				.println(ConsoleColour.GREEN_BOLD + "(4) Find Most/Least Similar (Dot Product)" + ConsoleColour.RESET);
		System.out.println(
				"    - Enter a word or text to find the most/least similar words using dot product similarity.");
		System.out.println();

		System.out.println(
				ConsoleColour.GREEN_BOLD + "(5) Find Most/Least Similar (Euclidean Distance)" + ConsoleColour.RESET);
		System.out.println("    - Enter a word or text to find the most/least similar words using Euclidean distance.");
		System.out.println();

		System.out.println(ConsoleColour.GREEN_BOLD + "(6) Toggle Similarity Mode" + ConsoleColour.RESET);
		System.out.println("    - Switch between finding the most or least similar words.");
		System.out.println();

		System.out.println(ConsoleColour.GREEN_BOLD + "(7) Show Embedding File Path" + ConsoleColour.RESET);
		System.out.println("    - Display the current path of the word embeddings file.");
		System.out.println();

		System.out.println(ConsoleColour.GREEN_BOLD + "(8) Show Output File Path" + ConsoleColour.RESET);
		System.out.println("    - Display the current path of the output file.");
		System.out.println();

		System.out.println(ConsoleColour.GREEN_BOLD + "(9) Help" + ConsoleColour.RESET);
		System.out.println("    - Show this help information.");
		System.out.println();

		System.out.println(ConsoleColour.GREEN_BOLD + "(10) Quit" + ConsoleColour.RESET);
		System.out.println("    - Exits the application.");
		System.out.println();
	}

}
