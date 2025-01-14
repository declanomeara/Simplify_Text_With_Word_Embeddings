package ie.atu.sw;

public class DotProductSimilarity implements SimilarityStrategy {

	@Override
    public double calculate(float[] vector1, float[] vector2) {
        double dotProduct = 0.0;
        
        for (int i = 0; i < vector1.length; i++) {
            dotProduct += vector1[i] * vector2[i];
        }
        
        return dotProduct;
    }

	@Override
	public SimilarityCalculationType getCalculationType() {
		return SimilarityCalculationType.DOT_PRODUCT;
	}

}
