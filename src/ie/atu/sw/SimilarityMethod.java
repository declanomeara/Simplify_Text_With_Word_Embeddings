package ie.atu.sw;

public enum SimilarityMethod {
	
	COSINE("Cosine Similarity"),
    DOT_PRODUCT("Dot Product Similarity"),
    EUCLIDEAN("Euclidean Distance"),
    MANHATTAN("Manhattan Distance");

    private final String description;

    SimilarityMethod(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
