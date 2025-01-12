package ie.atu.sw;

public class CosineSimilarity implements SimilarityMeasure {

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

	@Override
	public SimilarityMethod getSimilarityMethod() {
		
		return SimilarityMethod.COSINE;
	}

}
