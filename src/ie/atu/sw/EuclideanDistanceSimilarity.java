package ie.atu.sw;
/**
 * The {@code EuclideanDistanceSimilarity} class implements the {@link SimilarityStrategy} interface
 * to calculate similarity using the Euclidean distance.
 * <p>
 * Euclidean distance measures the straight-line distance between two vectors in a multi-dimensional space.
 * </p>
 *
 * <h2>Formula:</h2>
 * <pre>
 * similarity = sqrt(sum((vector1[i] - vector2[i])^2)) for all i
 * </pre>
 *
 * 
 *
 * @see SimilarityStrategy
 * @see DotProductSimilarity
 * @see EuclideanDistanceSimilarity
 * @see ManhattanDistanceSimilarity
 * @see SimilarityCalculationType#EUCLIDEAN
 *
 *
 * @author Declan O'Meara
 * @version 1.0
 * @since 21
 */
public class EuclideanDistanceSimilarity implements SimilarityStrategy {
	
	 /**
     * Calculates the Euclidean distance between two vectors.
     *
     * @param vector1 The first vector.
     * @param vector2 The second vector.
     * @return A double representing the Euclidean distance.
     */
	@Override
	public double calculate(float[] vector1, float[] vector2) {
		double sum = 0.0;
		for (int i = 0; i < vector1.length; i++) {
			sum += Math.pow(vector1[i] - vector2[i], 2);
		}

		return -Math.sqrt(sum);// Return negative for consistency (higher is better)
	}
	/**
     * Retrieves the type of similarity calculation implemented.
     *
     * @return {@link SimilarityCalculationType#EUCLIDEAN}.
     */
	@Override
	public SimilarityCalculationType getCalculationType() {
		return SimilarityCalculationType.EUCLIDEAN;
	}

}
