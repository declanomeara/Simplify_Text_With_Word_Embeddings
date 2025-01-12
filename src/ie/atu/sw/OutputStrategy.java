package ie.atu.sw;

public interface OutputStrategy {
	
	void outputResult(String inputText,
	        String simplifiedText,
	        SimilarityMethod similarityMethod,
	        int wordsToSimplify,
	        int wordsInGoogle1000,
	        int wordsNotInEmbeddings,
	        String outputFilePath);

}
