package ie.atu.sw;

import java.io.File;
import java.util.List;
import java.util.Scanner;

public class Menu {

	private Scanner scanner;
	private boolean keepRunning = true;
	private FileParser fileParser;
	private String embeddingsFilePath;
	private String googleFilePath;
	private String outputFilePath = "./out.txt"; // default unless changed by user
	private String textToSimplifyPath;
	private SimilarityMeasure similarityMeasure;
	private OutputStrategy outputStrategy = new FileAndConsoleOutput(); // Default output strategy

	public Menu() {
		scanner = new Scanner(System.in);
		this.fileParser = new FileParser();

	}

	// Main loop to display menu options and handle user input
	public void start() {

		while (keepRunning) {
			showMenuOptions();
			int choice = getUserInput();
			switch (choice) {
			case 1 -> setFilePath("word embeddings");
			case 2 -> setFilePath("Google 1000");
			case 3 -> setFilePath("text to simplify");
			case 4 -> setOutputFilepath();
			case 5 -> selectSimilarityMeasure();
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

	private void showMenuOptions() {
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
		System.out.println(ConsoleColour.GREEN_BOLD + "(5) Select Similarity Method" + ConsoleColour.RESET);
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

	// Check to make sure integer selected
	private int getUserInput() {
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
	private void selectionOutOfRange() {

		MessageUtil.displayMessage("[Error] - Invalid selection", ConsoleColour.RED_BOLD);
	}

	// set file paths
	private void setFilePath(String fileType) {

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

	// set results output file path
	private void setOutputFilepath() {

		System.out.print("Enter path for output file(default: ./out.txt): ");
		outputFilePath = scanner.next().trim();

		MessageUtil.displayMessage("[INFO] - Output filepath set sucessfully", ConsoleColour.BLUE_BOLD);
	}

	// Display current file paths
	public void showCurrentFilePaths() {
		System.out.println();
		System.out.println(ConsoleColour.BLUE_BOLD + "************************************************************");
		System.out.println("*                     Current File Paths                   *");
		System.out.println("************************************************************" + ConsoleColour.RESET);

		System.out.println("Word Embeddings File Path: " + (embeddingsFilePath != null ? embeddingsFilePath
				: ConsoleColour.RED_BOLD + "Not Set" + ConsoleColour.RESET));

		System.out.println("Google-1000 Words File Path: "
				+ (googleFilePath != null ? googleFilePath : ConsoleColour.RED_BOLD + "Not Set" + ConsoleColour.RESET));

		System.out.println("Text-to-Simplify File Path: " + (textToSimplifyPath != null ? textToSimplifyPath
				: ConsoleColour.RED_BOLD + "Not Set" + ConsoleColour.RESET));

		System.out.println("Output File Path: "
				+ (outputFilePath != null ? outputFilePath : ConsoleColour.RED_BOLD + "Not Set" + ConsoleColour.RESET));

		System.out.println(ConsoleColour.BLUE_BOLD + "************************************************************"
				+ ConsoleColour.RESET);
	}
	
	public void setOutputStrategy(OutputStrategy strategy) {
		this.outputStrategy = strategy;
		MessageUtil.displayMessage("[INFO] Output strategy set to: " + strategy.getClass().getSimpleName(),
				ConsoleColour.BLUE_BOLD);
	}

	private void selectSimilarityMeasure() {
		System.out.println();
		System.out.println(ConsoleColour.GREEN_BOLD + """
				Select Similarity Measure:
				(1) Cosine Similarity
				(2) Dot Product Similarity
				(3) Euclidean Distance Similarity
				(4) Manhattan Distance Similarity
				""" + ConsoleColour.RESET);
		System.out.print("Enter your choice: ");
		int choice = getUserInput();

		similarityMeasure = switch (choice) {
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

		MessageUtil.displayMessage("[INFO] Similarity Measure set successfully.", ConsoleColour.BLUE_BOLD);
	}
	
	private void selectOutputStrategy() {
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


	private void executeTextSimplification() {
		if (embeddingsFilePath == null || googleFilePath == null || textToSimplifyPath == null) {
			MessageUtil.displayMessage("[ERROR] Ensure all required files are loaded before executing.",
					ConsoleColour.RED_BOLD);
			return;
		}

		if (similarityMeasure == null) {
			MessageUtil.displayMessage("[ERROR] Select a similarity measure before executing.", ConsoleColour.RED_BOLD);
			return;
		}

		try {
			List<String> textLines = fileParser.loadTextToSimplify(textToSimplifyPath);

			TextSimplifier simplifier = new TextSimplifier(fileParser.getEmbeddings(), fileParser.getGoogleWords(),
					similarityMeasure);

			List<String> simplifiedText = simplifier.simplifyText(textLines);

			for (String line : simplifiedText) {
				outputStrategy.outPutTopMatch(line, 0.0, outputFilePath, 0);
			}

			MessageUtil.displayMessage("[INFO] Text simplification completed successfully.", ConsoleColour.GREEN_BOLD);

		} catch (Exception e) {
			MessageUtil.displayMessage("[ERROR] Text simplification failed: " + e.getMessage(), ConsoleColour.RED_BOLD);
		}
	}

}
