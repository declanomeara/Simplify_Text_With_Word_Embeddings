package ie.atu.sw;

/**
* The {@code OutputStrategy} interface defines a contract for outputting
* the results of the text simplification process.
* <p>
* Implementations of this interface provide specific structures for
* presenting the simplified text and processing summary.
* </p>
*
* <h2>Expected Output:</h2>
* <ul>
* <li>Formatted text results (e.g., plain text, JSON).</li>
* <li>Summary details of the simplification process (e.g., number of words simplified).</li>
* </ul>
*
* <h2>Implementations:</h2>
* <ul>
* <li>{@link FileAndConsoleOutput}</li>
* <li>{@link JsonOutputStrategy}</li>
* </ul>
*
*
* @see JsonOutputStrategy
* @see FileAndConsoleOutput
* @see TextSimplifier#simplifyText(List)
*
*
* @author Declan O'Meara
* @version 1.0
* @since 21
*/
public interface OutputStrategy {
	/**
     * Outputs the result of the text simplification process.
     *
     * @param inputText          The original input text.
     * @param simplifiedText     The simplified version of the input text.
     * @param similarityMethod   The similarity calculation method used (e.g., Cosine, Dot Product).
     * @param wordsToSimplify    The number of words that were identified for simplification.
     * @param wordsInGoogle1000  The number of words already present in the Google-1000 dataset.
     * @param wordsNotInEmbeddings The number of words not found in the embeddings file.
     * @param outputFilePath     The file path where the output should be saved.
     */
	void outputResult(String inputText,
	        String simplifiedText,
	        SimilarityCalculationType similarityMethod,
	        int wordsToSimplify,
	        int wordsInGoogle1000,
	        int wordsNotInEmbeddings,
	        String outputFilePath);

}
