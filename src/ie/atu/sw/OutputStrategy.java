package ie.atu.sw;

public interface OutputStrategy {
	
	void outputResult(String inputText,
	        String simplifiedText,
	        SimilarityCalculationType similarityMethod,
	        int wordsToSimplify,
	        int wordsInGoogle1000,
	        int wordsNotInEmbeddings,
	        String outputFilePath);

}
