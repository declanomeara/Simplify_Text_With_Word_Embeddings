package ie.atu.sw;

/**
 * The {@code SimilarityStrategy} interface represents a contract for implementing
 * various similarity calculation algorithms.
 * <p>
 * Implementations of this interface should provide methods to calculate similarity
 * between two word vectors and specify the type of similarity calculation.
 * </p>
 *
 * <h2>Known Implementations:</h2>
 * <ul>
 * <li>{@link CosineSimilarity}</li>
 * <li>{@link DotProductSimilarity}</li>
 * <li>{@link EuclideanDistanceSimilarity}</li>
 * <li>{@link ManhattanDistanceSimilarity}</li>
 * </ul>
 *
 * @see CosineSimilarity
 * @see DotProductSimilarity
 * @see EuclideanDistanceSimilarity
 * @see ManhattanDistanceSimilarity
 * @see SimilarityCalculationType
 *
 *
 * @author YourName
 * @version 1.0
 * @since 1.8
 */
public interface SimilarityStrategy {
	
	/**
     * Retrieves the type of similarity calculation implemented by this strategy.
     *
     * @return {@link SimilarityCalculationType} representing the type of similarity calculation.
     */
	SimilarityCalculationType getCalculationType();
	
	/**
     * Calculates the similarity score between two vectors.
     *
     * @param vector1 The first vector.
     * @param vector2 The second vector.
     * @return A double representing the similarity score.
     */
	double calculate(float[] vector1, float[] vector2);

}
