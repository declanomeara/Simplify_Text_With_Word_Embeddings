package ie.atu.sw;

public interface SimilarityStrategy {
	
	SimilarityCalculationType getCalculationType();
	double calculate(float[] vector1, float[] vector2);

}
