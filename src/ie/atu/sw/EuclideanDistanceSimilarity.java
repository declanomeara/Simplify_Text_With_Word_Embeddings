package ie.atu.sw;

public class EuclideanDistanceSimilarity implements SimilarityMeasure {

	@Override
	public double calculate(float[] vector1, float[] vector2) {
		double sum = 0.0;
		for (int i = 0; i < vector1.length; i++) {
			sum += Math.pow(vector1[i] - vector2[i], 2);
		}

		return Math.sqrt(sum);// Return negative for consistency (higher is better)
	}

	@Override
	public SimilarityMethod getSimilarityMethod() {
		return SimilarityMethod.EUCLIDEAN;
	}

}
