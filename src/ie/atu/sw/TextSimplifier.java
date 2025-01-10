package ie.atu.sw;

import java.util.*;

public class TextSimplifier {

	private final Map<String, float[]> embeddings;
	private final Set<String> googleWords;
	private final SimilarityMeasure similarityMeasure;

	//constructor
	public TextSimplifier(Map<String, float[]> embeddings, Set<String> googleWords,
			SimilarityMeasure similarityMeasure) {
		this.embeddings = embeddings;
		this.googleWords = googleWords;
		this.similarityMeasure = similarityMeasure;
	}

	public String simplifyLine(String line) {
		String[] inputWords = line.split("\\s+");
		
		StringBuilder simplifiedLine = new StringBuilder();

		for (String word : inputWords) {
			if (googleWords.contains(word)) {
				
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
		float[] targetVector = embeddings.get(targetWord);
		double maxSimilarity = Double.NEGATIVE_INFINITY;
		
		String closestWord = null;

		for (String googleWord : googleWords) {
			float[] googleVector = embeddings.get(googleWord);

			if (googleVector != null) { // Ensure the Google word has an embedding
				double similarity = similarityMeasure.calculate(targetVector, googleVector);
				if (similarity > maxSimilarity) {
					maxSimilarity = similarity;
					closestWord = googleWord;
				}
			}
		}

		return closestWord;
	}

	public List<String> simplifyText(List<String> textLines) {
		List<String> simplifiedLines = new ArrayList<>(); // Create a list to store the simplified lines
	    for (String line : textLines) {
	        String simplifiedLine = simplifyLine(line); // Simplify each line
	        simplifiedLines.add(simplifiedLine); // Add the simplified line to the result list
	    }
	    return simplifiedLines; // Return the list of simplified lines
	}

}
