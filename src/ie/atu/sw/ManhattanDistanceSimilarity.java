package ie.atu.sw;

public class ManhattanDistanceSimilarity implements SimilarityMeasure {

	@Override
	public double calculate(float[] vector1, float[] vector2) {
		double sum = 0;
		
		for (int i = 0; i < vector1.length; i++) {
            sum += Math.abs(vector1[i] - vector2[i]);
        }

        return -sum; // Negate for consistency (higher is better)
	}
	
}
