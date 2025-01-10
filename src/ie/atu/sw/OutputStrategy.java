package ie.atu.sw;

public interface OutputStrategy {
	
	void outPutTopMatch(String word, double score, String outputFilePath, int calculationOption);

}
