package ie.atu.sw;

import java.util.*;

public class TextSimplifier {

	private final Map<String, float[]> embeddings; // Full embeddings map: word -> vector
    private final Map<String, float[]> googleWordEmbeddings; // Google-1000 embeddings: word -> vector
    private final SimilarityMeasure similarityMeasure; // Strategy for similarity calculations

	//constructor
	public TextSimplifier(Map<String, float[]> embeddings, Map<String, float[]> googleWordEmbeddings,
			SimilarityMeasure similarityMeasure) {
		this.embeddings = embeddings;
		this.googleWordEmbeddings = googleWordEmbeddings;
		this.similarityMeasure = similarityMeasure;
	}

	public String simplifyLine(String line) {
		String[] inputWords = line.split("\\s+");
		
		StringBuilder simplifiedLine = new StringBuilder();

		for (String word : inputWords) {
			if (googleWordEmbeddings.containsKey(word)) {
				
				// if the word exists in Google-1000, keep it as is
				simplifiedLine.append(word).append(" ");
				
				// Word is in embeddings but not in Google-1000, find the closest match
			} else if (embeddings.containsKey(word)) {
				
				String closestWord = findClosestWord(word);
				simplifiedLine.append(closestWord).append(" ");
			} else {
				// if you cant find the word in the embeddings just as the word as is
				simplifiedLine.append(word).append(" ");
			}
		}

		return simplifiedLine.toString().trim();
	}

	private String findClosestWord(String targetWord) {
        float[] targetVector = embeddings.get(targetWord); // Get the vector of the target word
        double maxSimilarity = Double.NEGATIVE_INFINITY; // Initialize max similarity
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
		List<String> simplifiedText = new ArrayList<>(); // Create a list to store the simplified lines
	    for (String line : textLines) {
	        
	    	simplifiedText.add(simplifyLine(line)); // Add the simplified line to the result list
	    }
	    return simplifiedText; // Return the list of simplified lines
	}

}
