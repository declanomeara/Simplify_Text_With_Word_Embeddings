package ie.atu.sw;


/**
 * The {@code HelpUtil} class provides a utility for displaying help information
 * for the text simplifier application.
 * <p>
 * This class outputs a detailed description of menu options and their functionalities.
 * </p>
 *
 * <h2>Usage Example:</h2>
 * <pre>
 * HelpUtil.displayHelp();
 * </pre>
 *
 * <h2>Output Details:</h2>
 * <ul>
 * <li>Lists all menu options.</li>
 * <li>Provides descriptions of their functionalities.</li>
 * <li>Highlights the purpose of each option.</li>
 * </ul>
 *
 * @see Menu
 * @see ConsoleColour
 *
 *
 * @author YourName
 * @version 1.0
 * @since 1.8
 */

public class HelpUtil {
	
	 /**
     * Displays the help menu for the application.
     * <p>
     * The help menu includes detailed explanations for each option available
     * in the main menu.
     * </p>
     *
     * <h2>Menu Options:</h2>
     * <ul>
     * <li><b>Specify Embedding File:</b> Sets the file path for the word embeddings file.</li>
     * <li><b>Specify Google-1000 Words File:</b> Sets the file path for the Google-1000 words file.</li>
     * <li><b>Specify Text File to Simplify:</b> Sets the file path for the input text file to simplify.</li>
     * <li><b>Specify Output File:</b> Sets the output file path (default: ./out.txt).</li>
     * <li><b>Select Similarity Calculation Strategy:</b> Choose a similarity measure (e.g., Cosine, Dot Product).</li>
     * <li><b>Select Output Strategy:</b> Define how results will be displayed or saved.</li>
     * <li><b>Execute Text Simplification:</b> Starts the simplification process.</li>
     * <li><b>Show Current File Paths:</b> Displays paths for required files.</li>
     * <li><b>Help:</b> Displays this help menu.</li>
     * <li><b>Quit:</b> Exits the application.</li>
     * </ul>
     */
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

        System.out.println(ConsoleColour.GREEN_BOLD + "(5) Select Similarity Calculation Strategy" + ConsoleColour.RESET);
        System.out.println("    - Choose the similarity strategy to use: Cosine Similarity, Dot Product, Euclidean Distance, or Manhattan Distance.");
        System.out.println();

        System.out.println(ConsoleColour.GREEN_BOLD + "(6) Select Output Strategy" + ConsoleColour.RESET);
        System.out.println("    - Choose how the results will be saved or displayed (e.g., plain text, JSON).");
        System.out.println();

        System.out.println(ConsoleColour.GREEN_BOLD + "(7) Execute Text Simplification" + ConsoleColour.RESET);
        System.out.println("    - Processes the input text file and generates the simplified text based on the selected similarity calculation strategy and output strategy.");
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
