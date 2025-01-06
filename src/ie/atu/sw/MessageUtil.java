package ie.atu.sw;

public class MessageUtil {
	
	//Displays a message with dynamic border and colour styling
		public static void displayMessage(String message, ConsoleColour colour) {
			
			// Create a dynamic border based on message length
			int borderLength = message.length() + 4; // 4 extra characters for padding
			char[] borderArray = new char[borderLength];
			
			 // Fill the border array with asterisks
			for (int i = 0; i < borderArray.length; i++) {
			    borderArray[i] = '*';
			}

			String border = new String(borderArray);
			// Output the styled message with the border
			System.out.println();
			System.out.println(colour + border);
			System.out.println(message);
			System.out.println(border + ConsoleColour.RESET);
			System.out.println();
		}

}
