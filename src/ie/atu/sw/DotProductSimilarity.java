package ie.atu.sw;
/**
 * The {@code DotProductSimilarity} class implements the {@link SimilarityStrategy} interface
 * to calculate similarity using dot product.
 * <p>
 * Dot product measures the magnitude of projection of one vector onto another.
 * </p>
 *
 * <h2>Formula:</h2>
 * <pre>
 * similarity = sum(vector1[i] * vector2[i]) for all i
 * </pre>
 *
 *
 * @see SimilarityStrategy
 * @see DotProductSimilarity
 * @see EuclideanDistanceSimilarity
 * @see ManhattanDistanceSimilarity
 * @see SimilarityCalculationType#DOT_PRODUCT
 *
 *
 * @author Declan O'Meara
 * @version 1.0
 * @since 21
 */
public class DotProductSimilarity implements SimilarityStrategy {
	
	/**
     * Calculates the dot product similarity between two vectors.
     *
     * @param vector1 The first vector.
     * @param vector2 The second vector.
     * @return A double representing the dot product score.
     */
	@Override
    public double calculate(float[] vector1, float[] vector2) {//Big-O Notation: O(n) - Iterates through the vectors to compute the dot product
        double dotProduct = 0.0;
        
        for (int i = 0; i < vector1.length; i++) {
            dotProduct += vector1[i] * vector2[i];
        }
        
        return dotProduct;
    }
	/**
     * Retrieves the type of similarity calculation implemented.
     *
     * @return {@link SimilarityCalculationType#DOT_PRODUCT}.
     */
	@Override
	public SimilarityCalculationType getCalculationType() {//Big-O Notation: O(1) -Returns a constant value
		return SimilarityCalculationType.DOT_PRODUCT;
	}

}
