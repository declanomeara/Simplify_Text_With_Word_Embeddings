package ie.atu.sw;

/**
 * Utility class for displaying messages with different console colors and formatting.
 * <p>
 * This class is used throughout the application to ensure consistent message formatting
 * and easy customization of console output styles.
 * </p>
 *
 * @see ConsoleColour
 * @see HelpUtil
 *
 *
 * @author Your Name
 * @version 1.0
 * @since 1.8
 */
public class MessageUtil {
	
	
	 /**
     * Displays a message to the console with the specified color formatting.
     *
     * @param message the message to display
     * @param color   the color format from {@link ConsoleColour} to apply to the message
     */
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
		
		/**
	     * Creates a centered, padded header line with dashes.
	     * <p>
	     * This method is useful for creating distinct sections in the console output.
	     * </p>
	     *
	     * @param text the text to center and pad
	     * @return a string containing the padded header
	     */
		public static String paddingHeaderHelper(String header) {
			
			StringBuilder sb = new StringBuilder();
			int borderLength = header.length() + 4; // 4 extra characters for padding

			// Pad out the header 
			for (int i = 0; i < borderLength; i++) {
				sb.append('-');
			}
			
			return sb.toString();

		}

}
