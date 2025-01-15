package ie.atu.sw;

/**
 * The {@code Runner} class serves as the entry point for the text simplifier application.
 * <p>
 * This class initializes the application by invoking the {@link Menu#start()} method.
 * </p>
 *
 * <h2>Usage Example:</h2>
 * <pre>
 * public static void main(String[] args) {
 *     Menu menu = new Menu();
 *     menu.start();
 * }
 * </pre>
 *
 * <h2>Responsibilities:</h2>
 * <ul>
 * <li>Initialize the application menu.</li>
 * <li>Invoke the start process for user interaction.</li>
 * </ul>
 *
 * @author YourName
 * @version 1.0
 * @since 1.8
 */

public class Runner {
	
	/**
     * The main method serves as the application entry point.
     * <p>
     * This method initializes the {@link Menu} object and starts the user interface loop.
     * </p>
     *
     * @param args Command-line arguments passed during the execution (currently unused).
     */
	public static void main(String[] args) {

		Menu m = new Menu();
		m.start();

		
		
	}

}