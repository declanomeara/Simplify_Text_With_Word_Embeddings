package ie.atu.sw;

import java.util.*;
/**
 * The {@code TextSimplifier} class provides functionality to simplify text by converting
 * words to their closest matches from the Google-1000 word list.
 * <p>
 * This class uses preloaded word embeddings and a similarity strategy to find and
 * replace words in a given text with simpler alternatives.
 * </p>
 *
 * <h2>Features:</h2>
 * <ul>
 * <li>Uses various similarity strategies to find the closest word.</li>
 * <li>Keeps track of statistics, such as words simplified and words not found.</li>
 * <li>Supports batch processing of multiple lines of text.</li>
 * </ul>
 *
 *
 *@see SimilarityStrategy
 *@see FileParser#getEmbeddings()
 *@see FileParser#getGoogleWords()
 *
 * @author Declan O'Meara
 * @version 1.0
 * @since 21
 */
public class TextSimplifier {

	private final Map<String, float[]> embeddings; // Full embeddings map: word -> vector
    private final Map<String, float[]> googleWordEmbeddings; // Google-1000 embeddings: word -> vector
    private final SimilarityStrategy similarityMeasure; // Strategy for similarity calculations

    private int wordsToSimplify; // Counter for words that needed simplification
    private int wordsInGoogle1000; // Counter for words already in Google-1000
    private int wordsNotInEmbeddings; // Counter for words not found in embeddings
    
    
    /**
     * Constructs a {@code TextSimplifier} with the specified embeddings, Google-1000 words,
     * and similarity strategy.
     *
     * @param embeddings The full embeddings map (word to vector).
     * @param googleWordEmbeddings The Google-1000 embeddings map (word to vector).
     * @param similarityMeasure The strategy used to calculate word similarity.
     */
	public TextSimplifier(Map<String, float[]> embeddings, Map<String, float[]> googleWordEmbeddings,
			SimilarityStrategy similarityMeasure) {
		this.embeddings = embeddings;
		this.googleWordEmbeddings = googleWordEmbeddings;
		this.similarityMeasure = similarityMeasure;
		
		 // Initialize counters
        this.wordsToSimplify = 0;
        this.wordsInGoogle1000 = 0;
        this.wordsNotInEmbeddings = 0;
		
	}
	
	 
	 /**
     * Simplifies a single line of text by replacing words with their closest matches
     * from the Google-1000 word list.
     *
     * @param line The input line of text to simplify.
     * @return A simplified version of the input line.
     */
	public String simplifyLine(String line) {//Big-O Notation: O(n) The method iterates through each word in the input line to simplify it.
		String[] inputWords = line.split("\\s+");
		
		StringBuilder simplifiedLine = new StringBuilder();

		for (String word : inputWords) {
			if (googleWordEmbeddings.containsKey(word)) {
				
				// if the word exists in Google-1000, keep it as is
				simplifiedLine.append(word).append(" ");
				wordsInGoogle1000++; // Increment counter for Google-1000 words
				
				// Word is in embeddings but not in Google-1000, find the closest match
			} else if (embeddings.containsKey(word)) {
				
				String closestWord = findClosestWord(word);
				simplifiedLine.append(closestWord).append(" ");
				 wordsToSimplify++;
			} else {
				// if you cant find the word in the embeddings just as the word as is
				simplifiedLine.append(word).append(" ");
				wordsNotInEmbeddings++; // Increment counter for words not in embeddings
			}
		}

		return simplifiedLine.toString().trim();
	}
	 /**
     * Finds the closest word from the Google-1000 list for a given word based on
     * the selected similarity strategy.
     *
     * @param targetWord The word to simplify.
     * @return The closest match from the Google-1000 word list.
     */
	private String findClosestWord(String targetWord) {//Big-O Notation: O(n)  The method iterates through a set of possible matches to calculate similarity scores and find the closest match
        float[] targetVector = embeddings.get(targetWord); // Get the vector of the target word
        double maxSimilarity = Double.NEGATIVE_INFINITY; // set to most minimum number to start
        String closestWord = null;

        for (Map.Entry<String, float[]> entry : googleWordEmbeddings.entrySet()) {
            String googleWord = entry.getKey();
            float[] googleVector = entry.getValue();

            // Calculate similarity using the selected similarity measure
            double similarity = similarityMeasure.calculate(targetVector, googleVector);
            if (similarity > maxSimilarity) {
                maxSimilarity = similarity;
                closestWord = googleWord;
            }
        }

        return closestWord; // Return the closest Google-1000 word
    }
	/**
     * Simplifies multiple lines of text.
     *
     * @param textLines A list of text lines to simplify.
     * @return A list of simplified text lines.
     */
	public List<String> simplifyText(List<String> textLines) {//Big-O Notation: O(n).Iterating through a list.
		// Reset counters for each new simplification
        wordsToSimplify = 0;
        wordsInGoogle1000 = 0;
        wordsNotInEmbeddings = 0;
		
		List<String> simplifiedText = new ArrayList<>(); // Create a list to store the simplified lines
	    for (String line : textLines) {
	        
	    	simplifiedText.add(simplifyLine(line)); // Add the simplified line to the result list
	    }
	    return simplifiedText; // Return the list of simplified lines
	}
	 /**
     * Retrieves the number of words that needed simplification.
     *
     * @return The count of words simplified.
     */
    public int getWordsToSimplify() {//Big-O Notation: O(1) retrieving counters
        return wordsToSimplify;
    }
    /**
     * Retrieves the number of words found in the Google-1000 list.
     *
     * @return The count of words in Google-1000.
     */
    public int getWordsInGoogle1000() {//Big-O Notation: O(1) retrieving counters
        return wordsInGoogle1000;
    }
    /**
     * Retrieves the number of words not found in the embeddings.
     *
     * @return The count of words missing in the embeddings.
     */
    public int getWordsNotInEmbeddings() {//Big-O Notation: O(1) retrieving counters
        return wordsNotInEmbeddings;
    }
	

}
