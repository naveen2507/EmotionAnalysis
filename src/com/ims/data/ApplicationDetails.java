package com.ims.data;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.ims.evaluation.process.Results;

public class ApplicationDetails {

	//public static String[] labels = { "sad", "anger", "fear", "happy", "disgust", "surprise"};

	private static ApplicationDetails appDetails;

	public static synchronized ApplicationDetails getInstance() {
		if (appDetails == null)
			appDetails = new ApplicationDetails();
		return appDetails;
	}

	
	public static int NO_OF_TRAINING_ITERATION = 50;
	public static List<String> labels = Arrays.asList("sad", "anger", "fear", "happy", "disgust", "surprise");
	
	public static List<String> HASH_TAG_LIST = Arrays.asList("#afraid", "#anger", "#bitter", "#disguise", "#disgust", "#fear",
			"#glad", "#grief", "#happiness", "#happy", "#hate", "#hatred", "#horror", "#joy", "#lucky", "#misery",
			"#panic", "#rage", "#sad", "#sadness", "#scare", "#sorrow", "#surprise", "#worry");
	
	public static String stop_word_file = ";";
	public static String model_file_sep = "------:::::::::::::::::::::::::::::------";
	public static String model_file_class_sep = "------------------------------------";
	
	
	public static String CONJ_FOL = "but,however,nevertheless,otherwise,yet,still,nonetheless,therefore,furthermore,consequently,thus,eventually,hence";
	public static String CONJ_PREV = "till , until , despite , inspite , though , although";
	public static String CONDITIONALS = "if,might,could,can,would,may";
	
	
//	public static int numOfFeatures = 4;
//	public static String feature1="1";
//	public static String feature2="2";
//	public static String feature3="3";
//	public static String feature4="4";
	public static String feature_pheta="pheta";
	public static double theta = 0.0;
	//public static String unigramDictioanry   = "D:\\Uni-MS\\NLPTeamLabWorkSpace\\Data\\4K-Data\\Unigrams-4K.csv";
	//public static String unigramDictioanry   = "D:\\Uni-MS\\NLPTeamLabWorkSpace\\Data\\56K-Data\\Unigrams-56K.csv";
	//public static String bigramDictioanry   = "D:\\Uni-MS\\NLPTeamLabWorkSpace\\Data\\56K-Data\\Bigrams-56K.csv";
	//public static String bigramDictioanry   = "D:\\Uni-MS\\NLPTeamLabWorkSpace\\Data\\4K-Data\\Bigrams-4K.csv";
	
	public static String unigramDictioanry   = "D:/Uni-MS/NLPTeamLabWorkSpace/Data/50K-Data/Unigrams-50K.csv";
	public static String bigramDictioanry   = "D:/Uni-MS/NLPTeamLabWorkSpace/Data/50K-Data/Bigrams-50K.csv";
	
	//public static String emotionDictionary = "C:/Users/user/git/EmotionalAnalysisRepo1/Data/NRC-emotion-lexicon.txt";
	public static String emotionDictionary = "D:\\Uni-MS\\NLPTeamLabWorkSpace\\Data\\NRC-emotion-lexicon.txt";
	public static String negationDictionaryPath = "D:\\Uni-MS\\NLPTeamLabWorkSpace\\Data\\NegationDictionary";
	//public static String discourseDictionaryPath = "D:\\Uni-MS\\NLPTeamLabWorkSpace\\Data\\NegationDictionary";

	//public static String emotionDictionary = "C:/Users/user/git/EmotionalAnalysisRepo1/Data/NRC-emotion-lexicon.txt";
	//public static String smileyDictionary = "C:/Users/user/git/EmotionalAnalysisRepo1/Data/emoticonsWithPolarity2.txt";
	public static String smileyDictionary = "D:/Uni-MS/NLPTeamLabWorkSpace/emoticonsWithPolarity2.txt";
	
	public static int RANKING_LEVEL = 6;
	public static boolean RANKING_PERCEPTRON = false;
	public static boolean INCR_RANKING_PERCEPTRON = false;
	
	
	
	public static String rawTweetFile = "D:/Uni-MS/NLPTeamLabWorkSpace/Data/50K-Data/sample-data-50K.csv";
	public static String trainPreprcossedTweetFile = "D:/Uni-MS/NLPTeamLabWorkSpace/Data/50K-Data/sample-data-50K-preprocessed-train.csv";
	public static String testPreprcossedTweetFile = "D:/Uni-MS/NLPTeamLabWorkSpace/Data/50K-Data/sample-data-50K-preprocessed-test.csv";
	public static String modelFile = "D:/Uni-MS/NLPTeamLabWorkSpace/Data/50K-Data/model6";
	public static String testOutDataFile = "D:/Uni-MS/NLPTeamLabWorkSpace/Data/50K-Data/out-test-model1";
	public static String trainFeatureWriteFile = "D:/Uni-MS/NLPTeamLabWorkSpace/Data/50K-Data/features-train-model6";
	public static String testFeatureWriteFile = "D:/Uni-MS/NLPTeamLabWorkSpace/Data/50K-Data/features-test-model6";
	
	/*public Set<String> featureNames;

	public Set<String> getFeatureNames() {
		return featureNames;
	}

	public void setFeatureNames(Set<String> featureNames) {
		this.featureNames = featureNames;
	}
*/	
	
	

}
