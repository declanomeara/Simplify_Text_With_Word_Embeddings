package ie.atu.sw;

public interface SimilarityMeasure {
	
	SimilarityMethod getSimilarityMethod();
	double calculate(float[] vector1, float[] vector2);

}
