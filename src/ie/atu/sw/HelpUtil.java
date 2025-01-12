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

        System.out.println(ConsoleColour.GREEN_BOLD + "(2) Specify Google-1000 Words File" + ConsoleColour.RESET);
        System.out.println("    - Sets the file path for the Google-1000 words file used for simplification.");
        System.out.println();

        System.out.println(ConsoleColour.GREEN_BOLD + "(3) Specify Text File to Simplify" + ConsoleColour.RESET);
        System.out.println("    - Sets the file path for the input text file that needs to be simplified.");
        System.out.println();

        System.out.println(ConsoleColour.GREEN_BOLD + "(4) Specify an Output File" + ConsoleColour.RESET);
        System.out.println("    - Sets the file path for the output file where simplified text will be saved (default is ./out.txt).");
        System.out.println();

        System.out.println(ConsoleColour.GREEN_BOLD + "(5) Select Similarity Method" + ConsoleColour.RESET);
        System.out.println("    - Choose the similarity measure to use: Cosine Similarity, Dot Product, Euclidean Distance, or Manhattan Distance.");
        System.out.println();

        System.out.println(ConsoleColour.GREEN_BOLD + "(6) Select Output Strategy" + ConsoleColour.RESET);
        System.out.println("    - Choose how the results will be saved or displayed (e.g., plain text, JSON).");
        System.out.println();

        System.out.println(ConsoleColour.GREEN_BOLD + "(7) Execute Text Simplification" + ConsoleColour.RESET);
        System.out.println("    - Processes the input text file and generates the simplified text based on the selected similarity measure and output strategy.");
        System.out.println();

        System.out.println(ConsoleColour.GREEN_BOLD + "(8) Show Current File Paths" + ConsoleColour.RESET);
        System.out.println("    - Displays the current paths for the word embeddings file, Google-1000 file, text file to simplify, and output file.");
        System.out.println();

        System.out.println(ConsoleColour.GREEN_BOLD + "(9) Help" + ConsoleColour.RESET);
        System.out.println("    - Displays this help information.");
        System.out.println();

        System.out.println(ConsoleColour.GREEN_BOLD + "(10) Quit" + ConsoleColour.RESET);
        System.out.println("    - Exits the application.");
        System.out.println();
	}

}
