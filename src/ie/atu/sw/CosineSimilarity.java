package ie.atu.sw;

/**
 * The {@code CosineSimilarity} class implements the {@link SimilarityStrategy} interface
 * to calculate similarity using the cosine similarity measure.
 * <p>
 * Cosine similarity computes the normalized dot product of two vectors.
 * This metric evaluates the angle between two vectors to measure their similarity.
 * </p>
 *
 * <h2>Formula:</h2>
 * <pre>
 * similarity = dotProduct(vector1, vector2) / (||vector1|| * ||vector2||)
 * </pre>
 *
 * 
 * @see SimilarityStrategy
 * @see DotProductSimilarity
 * @see EuclideanDistanceSimilarity
 * @see ManhattanDistanceSimilarity
 * @see SimilarityCalculationType#COSINE
 *
 *
 * @author Declan O'Meara
 * @version 1.0
 * @since 1.8
 * 
 */
public class CosineSimilarity implements SimilarityStrategy {

	/**
     * Calculates the cosine similarity between two vectors.
     *
     * @param vector1 The first vector.
     * @param vector2 The second vector.
     * @return A double representing the cosine similarity score.
     */
	@Override
	public double calculate(float[] vector1, float[] vector2) {
		// Calculate dot product first
		double dotProduct = 0.0;
		double normVector1 = 0.0;
		double normVector2 = 0.0;

		for (int i = 0; i < vector1.length; i++) {
			dotProduct += vector1[i] * vector2[i];
			normVector1 += Math.pow(vector1[i], 2);
			normVector2 += Math.pow(vector2[i], 2);
		}

		// Return cosine similarity
		return dotProduct / (Math.sqrt(normVector1) * Math.sqrt(normVector2));
	}
	/**
     * Retrieves the type of similarity calculation implemented.
     *
     * @return {@link SimilarityCalculationType#COSINE}.
     */
	@Override
	public SimilarityCalculationType getCalculationType() {
		
		return SimilarityCalculationType.COSINE;
	}

}
