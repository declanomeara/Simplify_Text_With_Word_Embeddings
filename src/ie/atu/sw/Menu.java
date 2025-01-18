package ie.atu.sw;

import java.io.File;
import java.util.List;
import java.util.Scanner;

/**
 * The {@code Menu} class provides the user interface for the text simplifier application.
 * <p>
 * It allows users to load files, configure settings, and execute the text simplification process.
 * </p>
 *
 * <h2>Responsibilities:</h2>
 * <ul>
 * <li>Display a menu with options for file selection, strategy configuration, and execution.</li>
 * <li>Handle user input and validate file paths.</li>
 * <li>Coordinate between different components of the application.</li>
 * </ul>
 *
 * <h2>Menu Options:</h2>
 * <ul>
 * <li>Specify file paths for embeddings, Google-1000 words, and input text.</li>
 * <li>Select output strategies and similarity calculation methods.</li>
 * <li>Display current configuration and execute the simplification process.</li>
 * <li>Show help information or exit the application.</li>
 * </ul>
 *
 *
 * @see FileParser
 * @see TextSimplifier
 * @see OutputStrategy
 * @see SimilarityStrategy
 * @see HelpUtil#displayHelp()
 *
 *
 * @author Declan O'Meara
 * @version 1.0
 * @since 21
 */
public class Menu {

	private Scanner scanner;
	private boolean keepRunning = true;
	private FileParser fileParser;
	private String embeddingsFilePath;
	private String googleFilePath;
	private String outputFilePath = "./out.txt"; // default unless changed by user
	private String textToSimplifyPath;
	private SimilarityStrategy calculationStrategy;
	private OutputStrategy outputStrategy = new FileAndConsoleOutput(); // Default output strategy

	 /**
     * Constructs a new {@code Menu} instance and initializes default configurations.
     */
	public Menu() {
		scanner = new Scanner(System.in);
		this.fileParser = new FileParser();
		this.calculationStrategy = new CosineSimilarity(); // Default similarity measure

	}
	/**
     * Starts the main menu loop to interact with the user and handle user input.
     * <p>
     * Displays menu options, handles user input, and coordinates the text simplification process.
     * </p>
     */
	public void start() {//Big-O Notation: O(1) - defined set of menu options

		while (keepRunning) {
			showMenuOptions();
			int choice = getUserInput();
			switch (choice) {
			case 1 -> setFilePath("word embeddings");
			case 2 -> setFilePath("Google 1000");
			case 3 -> setFilePath("text to simplify");
			case 4 -> setOutputFilepath();
			case 5 -> selectCalculationStrategy();
			case 6 -> executeTextSimplification();
			case 7 -> selectOutputStrategy();
			case 8 -> showCurrentFilePaths();
			case 9 -> HelpUtil.displayHelp();
			case 10 -> keepRunning = false;

			// used if integer input is greater than options
			default -> selectionOutOfRange();

			}
		}
		scanner.close();// Release the scanner resource

		MessageUtil.displayMessage("[INFO] Program Exiting...Thank you, Goodbye", ConsoleColour.BLUE_BOLD);
	}

    /**
     * Displays the menu options to the user.
     */
	private void showMenuOptions() {//Big-O Notation: O(1) - prints menu options to screen
		System.out.println();
		System.out.println(ConsoleColour.BLACK_BOLD + "************************************************************");
		System.out.println("*     ATU - Dept. of Computer Science & Applied Physics    *");
		System.out.println("*                                                          *");
		System.out.println("*          Virtual Threaded Text Simplifier                *");
		System.out.println("*                                                          *");
		System.out.println("************************************************************" + ConsoleColour.RESET);
		System.out.println(ConsoleColour.GREEN_BOLD + "(1) Specify file path of wordembedding File" + ConsoleColour.RESET);
		System.out.println(ConsoleColour.GREEN_BOLD + "(2) Specify file path of Google 1000 words" + ConsoleColour.RESET);
		System.out.println(ConsoleColour.GREEN_BOLD + "(3) Specify file path of text to simplify " + ConsoleColour.RESET);
		System.out.println(ConsoleColour.GREEN_BOLD + "(4) Specify an Output File (default: ./out.txt)" + ConsoleColour.RESET);
		System.out.println(ConsoleColour.GREEN_BOLD + "(5) Select Similarity Calculation Strategy" + ConsoleColour.RESET);
		System.out.println(ConsoleColour.GREEN_BOLD + "(6) Execute Text Simplification" + ConsoleColour.RESET);
		System.out.println(ConsoleColour.GREEN_BOLD + "(7) Select Output Strategy" + ConsoleColour.RESET);
		System.out.println(ConsoleColour.GREEN_BOLD + "(8) Show Current File Paths" + ConsoleColour.RESET);
		System.out.println(ConsoleColour.GREEN_BOLD + "(9) Help - Display help information for each options"+ ConsoleColour.RESET);
		System.out.println(ConsoleColour.GREEN_BOLD + "(10) Quit" + ConsoleColour.RESET);

		// Output a menu of options and solicit text from the user
		System.out.println();
		System.out.print(ConsoleColour.BLACK_BOLD_BRIGHT);
		System.out.print("Select Option [1-10]>" + ConsoleColour.RESET);
		System.out.println();

	}
	
	 /**
     * Retrieves user input for menu selection and ensures valid integer input.
     *
     * @return The menu option selected by the user.
     */
	private int getUserInput() {//Big-O Notation: O(1). Reading user input is a single operation
		while (true) {
			System.out.print("Pick a menu option: ");
			String input = scanner.next().trim();
			try {
				return Integer.parseInt(input);

			} catch (NumberFormatException e) {
				MessageUtil.displayMessage("[ERROR] - Invalid input, Please enter a selection option: ",
						ConsoleColour.RED_BOLD);
			}
		}
	}
	
	// If user selected an integer greater than options available
	
	/**
     * Handles invalid menu selections.
     */
	private void selectionOutOfRange() {//Big-O Notation: O(1). Single operation

		MessageUtil.displayMessage("[Error] - Invalid selection", ConsoleColour.RED_BOLD);
	}
	
	/**
     * Prompts the user to set file paths for required inputs.
     *
     * @param fileType The type of file to set (e.g., embeddings, Google 1000, text to simplify).
     */
	private void setFilePath(String fileType) {//Big-O Notation: O(1). Validating file existence and assigning it to a variable constant-time operations.

		System.out.print("Enter path to " + fileType + " file: ");
		String filePath = scanner.next().trim();

		File file = new File(filePath);

		if (!file.exists()) {
			MessageUtil.displayMessage("[ERROR] - The " + fileType + " file doesn't exist at " + filePath,
					ConsoleColour.RED);
			return; // Exit if the file doesn't exist
		}

		// Assign to correct variable
		switch (fileType.toLowerCase()) {
		case "word embeddings" -> {
			embeddingsFilePath = filePath;
			fileParser.loadEmbeddingsFile(filePath);
		}
		case "google 1000" -> {
			googleFilePath = filePath;
			fileParser.loadGoogleWordsFile(filePath);
		}
		case "text to simplify" -> {
			textToSimplifyPath = filePath;
			fileParser.loadTextToSimplify(filePath);
		}
		default -> MessageUtil.displayMessage("[ERROR] Unknown file type.", ConsoleColour.RED_BOLD);
		
		}

		MessageUtil.displayMessage("[INFO] - " + fileType + " file loaded successfully!", ConsoleColour.BLUE_BOLD);

		System.out.println();
	}
	
	/**
     * Sets the output file path for results.
     */
	private void setOutputFilepath() {//Big-O Notation: O(1).

		System.out.print("Enter path for output file(default: ./out.txt): ");
		outputFilePath = scanner.next().trim();

		MessageUtil.displayMessage("[INFO] - Output filepath set sucessfully", ConsoleColour.BLUE_BOLD);
	}
	
	/**
     * Displays the current file paths configured in the application.
     */
	public void showCurrentFilePaths() {//Big-O Notation: O(1). This is a constant-time operation because it kust prints out a set of filepaths 
		System.out.println();
		System.out.println(ConsoleColour.BLUE_BOLD + "************************************************************");
		System.out.println("*                     Current File Paths                   *");
		System.out.println("************************************************************" + ConsoleColour.RESET);
		System.out.println("Word Embeddings File Path: " + (embeddingsFilePath != null ? embeddingsFilePath : ConsoleColour.RED_BOLD + "Not Set" + ConsoleColour.RESET));
		System.out.println("Google-1000 Words File Path: " + (googleFilePath != null ? googleFilePath : ConsoleColour.RED_BOLD + "Not Set" + ConsoleColour.RESET));
		System.out.println("Text-to-Simplify File Path: " + (textToSimplifyPath != null ? textToSimplifyPath : ConsoleColour.RED_BOLD + "Not Set" + ConsoleColour.RESET));
		System.out.println("Output File Path: " + (outputFilePath != null ? outputFilePath : ConsoleColour.RED_BOLD + "Not Set" + ConsoleColour.RESET));
		System.out.println(ConsoleColour.BLUE_BOLD + "************************************************************"+ ConsoleColour.RESET);
	}
	
	/**
	 * Sets the output strategy for the text simplification results.
	 * 
	 * @param strategy The OutputStrategy implementation to use
	 * @see OutputStrategy
	 * @see FileAndConsoleOutput
	 * @see JsonOutputStrategy
	 */
	public void setOutputStrategy(OutputStrategy strategy) {//Big-O Notation: O(1) constant time operation
		this.outputStrategy = strategy;
		MessageUtil.displayMessage("[INFO] Output strategy set to: " + strategy.getClass().getSimpleName(),
				ConsoleColour.BLUE_BOLD);
	}
	/**
	 * Allows user to select a similarity calculation strategy.
	 * Provides options for:
	 * <ul>
	 * <li>Cosine Similarity</li>
	 * <li>Dot Product Similarity</li>
	 * <li>Euclidean Distance Similarity</li>
	 * <li>Manhattan Distance Similarity</li>
	 * </ul>
	 * Defaults to Cosine Similarity if invalid selection.
	 * 
	 * @see SimilarityStrategy
	 * @see CosineSimilarity
	 */
	private void selectCalculationStrategy() {//Big-O Notation: O(1) constant time operation
		System.out.println();
		System.out.println(ConsoleColour.GREEN_BOLD + """
				Select Similarity Calculation Strategy:
				(1) Cosine Similarity
				(2) Dot Product Similarity
				(3) Euclidean Distance Similarity
				(4) Manhattan Distance Similarity
				""" + ConsoleColour.RESET);
		System.out.print("Enter your choice: ");
		int choice = getUserInput();

		calculationStrategy = switch (choice) {
		case 1 -> new CosineSimilarity();
		case 2 -> new DotProductSimilarity();
		case 3 -> new EuclideanDistanceSimilarity();
		case 4 -> new ManhattanDistanceSimilarity();
		default -> {
			MessageUtil.displayMessage("[ERROR] Invalid choice. Defaulting to Cosine Similarity.",
					ConsoleColour.RED_BOLD);
			yield new CosineSimilarity();
		}
		};
		
		SimilarityCalculationType calculationType = calculationStrategy.getCalculationType();
		MessageUtil.displayMessage("[INFO] Similarity Measure " + calculationType.getDescription() + " set successfully.", ConsoleColour.BLUE_BOLD);
	}
	/**
	 * Allows user to select an output strategy.
	 * Provides options for:
	 * <ul>
	 * <li>File and Console Output</li>
	 * <li>JSON Output</li>
	 * </ul>
	 * Defaults to File and Console Output if invalid selection.
	 * 
	 * @see OutputStrategy
	 */
	private void selectOutputStrategy() {//Big-O Notation: O(1) constant time just prints some strings
	    System.out.println(ConsoleColour.GREEN_BOLD + """
	        Select Output Strategy:
	        (1) File and Console Output
	        (2) JSON Output
	        """ + ConsoleColour.RESET);
	    System.out.print("Enter your choice: ");
	    int choice = getUserInput();

	    switch (choice) {
	        case 1 -> setOutputStrategy(new FileAndConsoleOutput());
	        case 2 -> setOutputStrategy(new JsonOutputStrategy());
	        default -> MessageUtil.displayMessage("[ERROR] Invalid choice. Defaulting to File and Console Output.", ConsoleColour.RED_BOLD);
	    }
	}

	/**
	 * Executes the text simplification process using configured settings.
	 * <p>
	 * Validates that all required files are loaded and a similarity measure
	 * is selected before proceeding. Uses the selected output strategy to
	 * display and save results.
	 * </p>
	 * 
	 * 
	 * @see TextSimplifier
	 * @see OutputStrategy
	 */
	private void executeTextSimplification() {//Big-O Notation: O(n) - loading, simplifying and outputting
		if (embeddingsFilePath == null || googleFilePath == null || textToSimplifyPath == null) {
			MessageUtil.displayMessage("[ERROR] Ensure all required files are loaded before executing.",
					ConsoleColour.RED_BOLD);
			return;
		}
		if (calculationStrategy == null) {
            MessageUtil.displayMessage("[ERROR] Similarity measure not set. Please select a similarity measure.",
                    ConsoleColour.RED_BOLD);
            return;
        }
		
		try {
			List<String> textLines = fileParser.getTextToSimpify();

			TextSimplifier simplifier = new TextSimplifier(fileParser.getEmbeddings(), fileParser.getGoogleWords(),
					calculationStrategy);

			List<String> simplifiedText = simplifier.simplifyText(textLines);

			// Retrieve the counters from TextSimplifier
			int wordsToSimplify = simplifier.getWordsToSimplify();
			int wordsInGoogle1000 = simplifier.getWordsInGoogle1000();
			int wordsNotInEmbeddings = simplifier.getWordsNotInEmbeddings();
			
			
			outputStrategy.outputResult(
		            String.join("\n", textLines),
		            String.join("\n", simplifiedText),
		            calculationStrategy.getCalculationType(),
		            wordsToSimplify,
		            wordsInGoogle1000,
		            wordsNotInEmbeddings,
		            outputFilePath
		        );

		        MessageUtil.displayMessage("[INFO] Text simplification completed successfully.", ConsoleColour.GREEN_BOLD);

		    } catch (Exception e) {
		        MessageUtil.displayMessage("[ERROR] Text simplification failed: " + e.getMessage(), ConsoleColour.RED_BOLD);
		    }
	}

}
