package com.ims.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ims.FeatureEngineering.FeatureVectorExtraction;
import com.ims.FeatureEngineering.Helper;
import com.ims.vo.CorpusVO;
import com.ims.vo.TweetVO;

public class Preprocessing {

	public String removeHashtag(String tweet) {

		List<String> hashTagList = Arrays.asList("#afraid", "#anger", "#bitter", "#disguise", "#disgust", "#fear",
				"#glad", "#grief", "#happiness", "#happy", "#hate", "#hatred", "#horror", "#joy", "#lucky", "#misery",
				"#panic", "#rage", "#sad", "#sadness", "#scare", "#sorrow", "#surprise", "#worry");
		String preprocessedTweet = tweet.toLowerCase();
		for (String hashtag : hashTagList) {
			preprocessedTweet = preprocessedTweet.replaceAll(hashtag, "");
		}
		// System.out.println(preprocessedTweet);
		return preprocessedTweet;

	}

	public Map<String, String> mapSmileyemotion() {
		BufferedReader br;
		String line;
		Map<String, String> smileyMap = new HashMap<String, String>();

		try {
			br = new BufferedReader(
					new FileReader("C:/Users/user/git/EmotionalAnalysisRepo/Data/dict/emoticonsWithPolarity_New.txt"));
			while ((line = br.readLine()) != null) {
				String elem[] = line.split("\t", 2);
				// System.out.println(elem[1]);
				// System.out.println(elem[0]);
				smileyMap.put(elem[0], elem[1]);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		return smileyMap;

	}

	public String replaceSmileyEmotion(Map<String, String> smileyMap, String tweet) {

		return null;

	}
	
	/*public String removeStopWords(String text){
		
		Helper objHelper = Helper.getInstance();
		List<String> listStopWords = objHelper.getStopList();
		
		return null;
		
	}*/
	

	public List<TweetVO> getPreprocessedtweet(List<TweetVO> lstTweetVO) {

		
		List<TweetVO> preprocessedList = new ArrayList<TweetVO>();
		//Map<String, String> smileyMap = mapSmileyemotion();
		try {
			for (TweetVO tweetinstance : lstTweetVO) {
				String tweet = tweetinstance.getTweet();
				
				// update smiley with emotions
				//preprocessedTweet = replaceSmileyEmotion(smileyMap, preprocessedTweet);

				// remove all user names and RT
				String preprocessedTweet = tweet.replaceAll("@[A-Za-z]+", "").replaceAll("RT", "").replaceAll("(\\[#RT\\])", "")
						
						// remove "[NEWLINE]"
						.replaceAll("\\[NEWLINE\\]", ""); 
						
				// remove defined set of hashtags
				preprocessedTweet = removeHashtag(preprocessedTweet);
				
						
				//remove non English characters
				preprocessedTweet=preprocessedTweet.replaceAll("[^A-Za-z0-9,\"?#!'::.\\[\\]\\p{M}\\p{Nd}\\s\\\\\\/]", " ");
				
				
				
				// remove special characters
				//preprocessedTweet = preprocessedTweet.replaceAll("[#ì‘†Œëž‘•´â™¡¥Žà‹¸¹]", "");
				
				//remove extra spaces if any
				preprocessedTweet = preprocessedTweet.trim().replaceAll("\\s+(?=\\s)", "") 
						
						//replace urls with "[URL]"
						.replaceAll("https?.*$", "[URL]")
						//replace multiple occurrences
						.replaceAll("[.?\\s:!,]{2,}", " ");
				
				tweetinstance.setPreprocessedTweet(preprocessedTweet);
				preprocessedList.add(tweetinstance);

			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

		return preprocessedList;
	}

	public void writeFile(List<TweetVO> listTweetVO, String fileName) throws IOException {

		FileWriter writer = new FileWriter(fileName);
		String separator = "\t";
		try {
			for (TweetVO tweetInstance : listTweetVO) {

				writer.append(tweetInstance.getGoldLabel());
				writer.append(separator);
				writer.append(tweetInstance.getTweetID());
				writer.append(separator);
				writer.append(tweetInstance.getUserName());
				writer.append(separator);
				writer.append(tweetInstance.getLanguage());
				writer.append(separator);
				writer.append(tweetInstance.getProfileName());
				writer.append(separator);
				writer.append(tweetInstance.getPreprocessedTweet());
				writer.append("\n");

			}
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public List<TweetVO> getFilteredtweet(List<TweetVO> lstTweetVO) {
		List<TweetVO> preprocessedList = new ArrayList<TweetVO>();
		Set<String> tempHashSet = new HashSet<String>();

		try {
			for (TweetVO tweetinstance : lstTweetVO) {
				
				Double score = 0.0;
				Integer flag = 0;
				String tweet1 = tweetinstance.getPreprocessedTweet();
				if (!(tweet1.trim().equalsIgnoreCase("[URL]") | tweet1.trim().isEmpty())) {
					for (TweetVO tweet2instance : preprocessedList) {
						score = tweetinstance.getCosineSimilarityScore(tweet2instance);
						if (score > 0.9999) {
							flag = 1;
							//System.out.println(tweetinstance.getPreprocessedTweet());
							//System.out.println(tweet2instance.getPreprocessedTweet());
							break;
						} 	
					}
					if(flag == 0){
						//System.out.println(tweetinstance.getPreprocessedTweet());
						preprocessedList.add(tweetinstance);
					}
				}
			}
		} catch (Exception e) {

		}

		return preprocessedList;

	}
	
	public List<TweetVO> getFilteredtweet(List<TweetVO> lstTweetVO,String fileName) throws IOException {
		List<TweetVO> preprocessedList = new ArrayList<TweetVO>();
		//Set<String> tempHashSet = new HashSet<String>();
		FileWriter writer = new FileWriter(fileName);
		FileWriter rawWriter = new FileWriter("D:\\Uni-MS\\NLPTeamLab\\DataTeamLAb\\sample-data-balanced.csv");
		String separator = "\t";
	
		try {
			int i = 0;
			for (TweetVO tweetInstance : lstTweetVO) {
				i++;
				
				Double score = 0.0;
				Integer flag = 0;
				String tweet1 = tweetInstance.getPreprocessedTweet();
				
				if (!(tweet1.trim().equalsIgnoreCase("[URL]") | tweet1.trim().isEmpty())) {
					for (TweetVO tweet2instance : preprocessedList) {
						score = tweetInstance.getCosineSimilarityScore(tweet2instance);
						if (score > 0.9) {
							i++;
							
							//System.out.println("Tweet1:"+tweetinstance.getPreprocessedTweet());
							//System.out.println("Tweet2:"+tweet2instance.getPreprocessedTweet());
							flag = 1;
							//System.out.println(tweetinstance.getPreprocessedTweet());
							//System.out.println(tweet2instance.getPreprocessedTweet());
							break;
						} 	
					}
					if(flag == 0){
						//System.out.println(tweetinstance.getPreprocessedTweet());
						writer.append(tweetInstance.getGoldLabel());
						writer.append(separator);
						writer.append(tweetInstance.getTweetID());
						writer.append(separator);
						writer.append(tweetInstance.getUserName());
						writer.append(separator);
						writer.append(tweetInstance.getLanguage());
						writer.append(separator);
						writer.append(tweetInstance.getProfileName());
						writer.append(separator);
						writer.append(tweetInstance.getPreprocessedTweet());
						writer.append("\n");
						rawWriter.append(tweetInstance.getTweetID());
						rawWriter.append(separator);
						rawWriter.append(tweetInstance.getTweet());
						rawWriter.append("\n");
						
						preprocessedList.add(tweetInstance);
						
					}
				}
				if(i%10==0){
					System.out.println(preprocessedList.size());
				}

			}
			writer.flush();
			writer.close();
			rawWriter.flush();
			rawWriter.close();
		} catch (Exception e) {

		}

		return preprocessedList;

	}

	

	public static void main(String[] args) {

		try {
			
			
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
			obj.getSmileyFeature(" :}", featureVector , smileyMap);
			
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

	}
}
