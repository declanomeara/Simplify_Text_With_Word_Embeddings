package ie.atu.sw;

import java.io.File;
import java.util.Scanner;

public class Menu {

	private Scanner scanner;
	private boolean keepRunning = true;
	// private WordEmbeddings wordEmbeddings;
	private String embeddingsFilePath;
	private String googleFilePath;
	private String outputFilePath = "./out.txt"; // default unless changed by user
	//private Boolean findMostSimilar = true; // default to finding most similar

	public Menu() {
		scanner = new Scanner(System.in);

	}

	// Main loop to display menu options and handle user input
	public void start() {

		while (keepRunning) {
			showMenuOptions();
			int choice = getUserInput();
			switch (choice) {
			 case 1 -> setFilePath("word embeddings");
			 case 2 -> setFilePath("Google 1000");
			 case 3 -> setOutputFilepath();
			// case 3 -> enterWordOrText(3);//Cosine Similarity
			// case 4 -> enterWordOrText(4);//Dot Product Similarity
			// case 5 -> enterWordOrText(5);//Euclidean Distance
			// case 6 -> configureOptions();
			 case 4 -> getFilePath("word embeddings");
			 case 5 -> getFilePath("Google 1000");
			 case 6 -> getFilePath("out");
			 case 7 -> HelpUtil.displayHelp();
			 case 8 -> keepRunning = false;

			// used if integer input is greater than options
			default -> selectionOutOfRange();

			}
		}
		scanner.close();// Release the scanner resource

		MessageUtil.displayMessage("[INFO] Program Exiting...Thank you, Goodbye", ConsoleColour.BLUE_BOLD);
	}

	// Displays the main menu options to the user
	/*public void showMenuOptions() {
		// You should put the following code into a menu or Menu class
		System.out.println();
		System.out.println("************************************************************");
		System.out.println("*     ATU - Dept. of Computer Science & Applied Physics    *");
		System.out.println("*                                                          *");
		System.out.println("*             Virtual Threaded Text Simplifier             *");
		System.out.println("*                                                          *");
		System.out.println("************************************************************");
		System.out.println("(1) Specify Embeddings File");
		System.out.println("(2) Specify Google 1000 File");
		System.out.println("(3) Specify an Output File (default: ./out.txt)");
		System.out.println("(4) Execute, Analyse and Report");
		System.out.println("(5) Optional Extras...");
		System.out.println("(?) Quit");

		// Output a menu of options and solicit text from the user
		System.out.print(ConsoleColour.BLACK_BOLD_BRIGHT);
		System.out.print("Select Option [1-4]>"  + ConsoleColour.RESET););
		System.out.println();

	}*/

	private void showMenuOptions() {
		System.out.println();
		System.out.println(ConsoleColour.BLACK_BOLD +"************************************************************");
		System.out.println("*     ATU - Dept. of Computer Science & Applied Physics    *");
		System.out.println("*                                                          *");
		System.out.println("*          Virtual Threaded Text Simplifier                *");
		System.out.println("*                                                          *");
		System.out.println("************************************************************" + ConsoleColour.RESET);
		System.out.println(ConsoleColour.GREEN_BOLD + "(1) Specify Embedding File" + ConsoleColour.RESET);
		System.out.println(ConsoleColour.GREEN_BOLD + "(2) Specify Google 1000 File" + ConsoleColour.RESET);
		System.out.println(ConsoleColour.GREEN_BOLD + "(3) Specify an Output File (default: ./out.txt)" + ConsoleColour.RESET);
		//System.out.println(ConsoleColour.GREEN_BOLD + "(3) Enter a Word or Text to find Most/Least Similar using Cosine Similarity" + ConsoleColour.RESET);
		//System.out.println(ConsoleColour.GREEN_BOLD + "(4) Enter a Word or Text to find Most/Least Similar using Dotproduct Similarity" + ConsoleColour.RESET);
		//System.out.println(ConsoleColour.GREEN_BOLD + "(5) Enter a Word or Text to find Most/Least Similar using Euclidean Similarity" + ConsoleColour.RESET);	
		//System.out.println(ConsoleColour.GREEN_BOLD + "(6) Toggle between similar and disimilar matching" + ConsoleColour.RESET);	
		System.out.println(ConsoleColour.GREEN_BOLD + "(4) Display word embedding file path" + ConsoleColour.RESET);
		System.out.println(ConsoleColour.GREEN_BOLD + "(5) Display Google 100 file path" + ConsoleColour.RESET);
		System.out.println(ConsoleColour.GREEN_BOLD + "(6) Display output file path" + ConsoleColour.RESET);
		System.out.println(ConsoleColour.GREEN_BOLD + "(7) Help - Display help information for each options" + ConsoleColour.RESET);
		System.out.println(ConsoleColour.GREEN_BOLD + "(8) Quit" + ConsoleColour.RESET);
		
		// Output a menu of options and solicit text from the user
				System.out.println();
				System.out.print(ConsoleColour.BLACK_BOLD_BRIGHT);
				System.out.print("Select Option [1-8]>"  + ConsoleColour.RESET);
				System.out.println();

	}
	
	
	//Menu methods
	
	// Check to make sure integer selected
		private int getUserInput() {
			while (true) {
				System.out.print("Pick a menu option: ");
				String input = scanner.next().trim();
				try {
					return Integer.parseInt(input);

				} catch (NumberFormatException e) {
					MessageUtil.displayMessage("[ERROR] - Invalid input, Please enter a selection option: ", ConsoleColour.RED_BOLD);
				}
			}
		}
		
		//If user selected an integer greater than options available
		private void selectionOutOfRange() {
			
			MessageUtil.displayMessage("[Error] - Invalid selection", ConsoleColour.RED_BOLD);
		}
		
		
		// set file paths
		private void setFilePath(String fileType) {

			System.out.print("Enter path to " + fileType +  " file: ");
			String filePath = scanner.next().trim();

			File file = new File(filePath);

			if (!file.exists()) {
				MessageUtil.displayMessage("[ERROR] - The " +  fileType + " file doesn't exist at " + filePath,
						ConsoleColour.RED);
				return; // Exit if the file doesn't exist
			}
			
			//Assign to appropriate variable
			if (fileType.equalsIgnoreCase("word embeddings")) {
				embeddingsFilePath = filePath;
			} else if (fileType.equalsIgnoreCase("Google 1000")) {
				googleFilePath = filePath;
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
		
		//get file path
		public void getFilePath(String fileType) {
	        String filePath;
	        if (fileType.equalsIgnoreCase("word embeddings")) {
	            filePath = embeddingsFilePath;
	        } else if (fileType.equalsIgnoreCase("Google 1000")) {
	            filePath = googleFilePath;
	        } else if (fileType.equalsIgnoreCase("output")) {
	            filePath = outputFilePath;
	        } else {
	            MessageUtil.displayMessage("[ERROR] - Unknown file type: " + fileType, ConsoleColour.RED_BOLD);
	            return;
	        }

	        MessageUtil.displayMessage("[INFO] - Current filepath for " + fileType + " file is: " + filePath, ConsoleColour.BLUE_BOLD);
	    }
		
		
		
		

}
