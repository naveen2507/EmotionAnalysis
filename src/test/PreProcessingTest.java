package test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ims.FeatureEngineering.FeatureVectorExtraction;
import com.ims.FeatureEngineering.Helper;
import com.ims.data.ApplicationDetails;
import com.ims.data.Preprocessing;
import com.ims.data.ReadData;
import com.ims.vo.CorpusVO;
import com.ims.vo.TweetVO;



public class PreProcessingTest {

	public static void main(String[] args) {

		try {
			CorpusVO corpus = new CorpusVO();
			corpus.setFileName("D:\\Uni-MS\\NLPTeamLab\\DataTeamLAb\\sample-data.csv");
			corpus.setTestData(true);
			
			
			/*
			TweetVO tweet1 = new TweetVO();
			tweet1.setTweet("Being stupid and angry is not a fault");
			TweetVO tweet2 = new TweetVO();
			tweet2.setTweet("Being stupid and angry is not a fault");
			double score = tweet2.getCosineSimilarityScore(tweet1);
			System.out.println("Score :"+score);*/
			
			
			
			/*ReadData obj = new ReadData();
			List<TweetVO> listTweetDo = obj.getDataValues(corpus).getListTweetVO();
			Preprocessing obj1 = new Preprocessing();
			List<TweetVO> preProtweet = obj1.getPreprocessedtweet(listTweetDo);
			List<TweetVO> filteredTweet = obj1.getFilteredtweet(preProtweet);
			obj1.writeFile(filteredTweet, "D:\\Uni-MS\\NLPTeamLab\\DataTeamLAb\\sample-data-preprocessed.csv");*/
			
			/*Helper obj = new Helper();
			obj.getEmotionDict("C:/Users/user/git/EmotionalAnalysisRepo1/Data/NRC-emotion-lexicon.txt");*/	
			Map<String, String> smileyMap;
			smileyMap = Helper.getInstance().getSmileyDict(ApplicationDetails.smileyDictionary); 
			Map<String, Double> featureVector = new HashMap<String, Double>();
			FeatureVectorExtraction obj = new FeatureVectorExtraction();
			obj.getSmileyFeature("zest is an emotion :(", featureVector , smileyMap);
			
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

	}

	
}
