package ie.atu.sw;

import java.util.*;

public class TextSimplifier {

	private final Map<String, float[]> embeddings; // Full embeddings map: word -> vector
    private final Map<String, float[]> googleWordEmbeddings; // Google-1000 embeddings: word -> vector
    private final SimilarityMeasure similarityMeasure; // Strategy for similarity calculations

    private int wordsToSimplify; // Counter for words that needed simplification
    private int wordsInGoogle1000; // Counter for words already in Google-1000
    private int wordsNotInEmbeddings; // Counter for words not found in embeddings
    
    
    
	//constructor
	public TextSimplifier(Map<String, float[]> embeddings, Map<String, float[]> googleWordEmbeddings,
			SimilarityMeasure similarityMeasure) {
		this.embeddings = embeddings;
		this.googleWordEmbeddings = googleWordEmbeddings;
		this.similarityMeasure = similarityMeasure;
		
		 // Initialize counters
        this.wordsToSimplify = 0;
        this.wordsInGoogle1000 = 0;
        this.wordsNotInEmbeddings = 0;
		
	}
	
	 

	public String simplifyLine(String line) {
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

	private String findClosestWord(String targetWord) {
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

	public List<String> simplifyText(List<String> textLines) {
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
	
	// Getters for counters
    public int getWordsToSimplify() {
        return wordsToSimplify;
    }

    public int getWordsInGoogle1000() {
        return wordsInGoogle1000;
    }

    public int getWordsNotInEmbeddings() {
        return wordsNotInEmbeddings;
    }
	

}
