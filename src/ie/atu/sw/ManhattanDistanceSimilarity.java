package ie.atu.sw;
/**
 * The {@code ManhattanDistanceSimilarity} class implements the {@link SimilarityStrategy} interface
 * to calculate similarity using the Manhattan distance.
 * <p>
 * Manhattan distance measures the sum of absolute differences between two vectors.
 * </p>
 *
 * <h2>Formula:</h2>
 * <pre>
 * similarity = sum(abs(vector1[i] - vector2[i])) for all i
 * </pre>
 *
 * <h2>Usage Example:</h2>
 * <pre>
 * ManhattanDistanceSimilarity manhattan = new ManhattanDistanceSimilarity();
 * double score = manhattan.calculate(vector1, vector2);
 * </pre>
 *
 * @author YourName
 * @version 1.0
 * @since 1.8
 */
public class ManhattanDistanceSimilarity implements SimilarityStrategy {
	
	/**
     * Calculates the Manhattan distance between two vectors.
     *
     * @param vector1 The first vector.
     * @param vector2 The second vector.
     * @return A double representing the Manhattan distance (negative for consistency).
     */
	@Override
	public double calculate(float[] vector1, float[] vector2) {
		double sum = 0;
		
		for (int i = 0; i < vector1.length; i++) {
            sum += Math.abs(vector1[i] - vector2[i]);
        }

        return -sum; // Negate for consistency (higher is better)
	}
	/**
     * Retrieves the type of similarity calculation implemented.
     *
     * @return {@link SimilarityCalculationType#MANHATTAN}.
     */
	@Override
	public SimilarityCalculationType getCalculationType() {
		return SimilarityCalculationType.MANHATTAN;
	}
	
}
