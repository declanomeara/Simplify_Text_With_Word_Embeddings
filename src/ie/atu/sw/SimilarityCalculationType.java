package ie.atu.sw;
/**
 * The {@code SimilarityCalculationType} enum defines various types of similarity
 * calculation methods used in the text simplifier application.
 * <p>
 * Each enum constant represents a specific strategy for calculating similarity between vectors.
 * </p>
 *
 * <h2>Available Types:</h2>
 * <ul>
 * <li>{@link #COSINE}: Cosine similarity.</li>
 * <li>{@link #DOT_PRODUCT}: Dot product similarity.</li>
 * <li>{@link #EUCLIDEAN}: Euclidean distance similarity.</li>
 * <li>{@link #MANHATTAN}: Manhattan distance similarity.</li>
 * </ul>
 *
 *
 * @author Declan O'Meara
 * @version 1.0
 * @since 21
 */
public enum SimilarityCalculationType {
	
	COSINE("Cosine Similarity"),
    DOT_PRODUCT("Dot Product Similarity"),
    EUCLIDEAN("Euclidean Distance"),
    MANHATTAN("Manhattan Distance");

    private final String description;
    /**
     * Constructs a {@code SimilarityCalculationType} with a specific description.
     *
     * @param description The description of the similarity calculation type.
     */
    SimilarityCalculationType(String description) {
        this.description = description;
    }
    /**
     * Retrieves the description of the similarity calculation type.
     *
     * @return A string describing the similarity calculation type.
     */
    public String getDescription() {
        return description;
    }

}
