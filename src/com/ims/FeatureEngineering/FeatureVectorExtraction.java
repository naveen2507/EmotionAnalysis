package com.ims.FeatureEngineering;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.ims.data.ApplicationDetails;
import com.ims.vo.TweetVO;
/**
 * 
 * 
 * @author Naveen
 * This Class sets the configuration for the selected features to extract the features for each tweet.
 */
public class FeatureVectorExtraction {

	public Set<String> featureNames = new HashSet<String>();
	Map<String, Integer> unigramMap;
	Map<String, Integer> bigramMap;
	List<String> negationDictionary;
	Map<String, List<String>> emotionMap;
	Map<String, String> smileyMap;

	/**
	 * Extract features for tweets
	 * @param lstTweetVO
	 * @return
	 * @throws IOException
	 */
	public List<TweetVO> getFeatureVectors(List<TweetVO> lstTweetVO) throws IOException {
		unigramMap = Helper.getInstance().getNgramDictionary(ApplicationDetails.unigramDictioanry);
		bigramMap = Helper.getInstance().getNgramDictionary(ApplicationDetails.bigramDictioanry);
		negationDictionary = Helper.getInstance().getNegationDict(ApplicationDetails.negationDictionaryPath);
		System.out.println("Size of Uni :" + unigramMap.size());
		System.out.println("Size of Bi :" + bigramMap.size());
		//emotionMap = Helper.getInstance().getEmotionDict(ApplicationDetails.emotionDictionary);
		//System.out.println("Size of Emo :" + emotionMap.size());
		smileyMap = Helper.getInstance().getSmileyDict(ApplicationDetails.smileyDictionary);
		List<TweetVO> lstFeatureTweetVO = new ArrayList<TweetVO>();
		int i = 0;
		for (TweetVO tweetVO : lstTweetVO) {
			i++;
			if (i % 100 == 0)
				System.out.println("Feature Ext for Tweet No :" + i);
			Map<String, Double> featureVector = new HashMap<String, Double>();
			String tweet = tweetVO.getPreprocessedTweet().toLowerCase();
			
			/**
			 * Setting for all the features required
			 */
			getNgramFeatures(tweet, featureVector);
			//getEmotionFeature(tweet, featureVector);
			//getWinningEmotionFeature(tweet, featureVector);
			//getNegationFeatures(tweet, featureVector);
			getNegationDiscourseFeature(tweet, featureVector);
			discourseFeature(tweet, featureVector);
			//getSmileyFeature(tweet, featureVector, smileyMap);
			getSmileyFeatureScores(tweetVO.getTweet(), featureVector, smileyMap);
			/****
			 * Other Features Call Here
			 */

			tweetVO.setFeatureVector(featureVector);
			lstFeatureTweetVO.add(tweetVO);

		}
		//ApplicationDetails appDetails = ApplicationDetails.getInstance();
		//appDetails.setFeatureNames(featureNames);
		return lstFeatureTweetVO;
	}

	/**
	 * SImple Negation feature for present of Negation word 
	 * @param tweet
	 * @param featureVector
	 * @throws IOException
	 */
	public void getNegationFeatures(String tweet, Map<String, Double> featureVector) throws IOException {

		for (String negation : negationDictionary) {
			if (tweet.contains(negation)) {
				featureVector.put("Negation", -1.0);
				featureNames.add("Negation");
			}
		}
		
	}
	
	
	/**
	 * Negation Discourse Feature add as negation feature for window of three words after negation word until Conj_Fol occurs. 
	 * @param tweet
	 * @param featureVector
	 * @throws IOException
	 */
	public void getNegationDiscourseFeature(String tweet, Map<String, Double> featureVector) throws IOException {
		String[] wordList = tweet.split(" ");
		List<String> conj_fol = Arrays.asList(ApplicationDetails.CONJ_FOL.split(","));
		int negation_window = 0;
		for (String word : wordList) {
			if(negationDictionary.contains(word.trim())){
				negation_window = 3;
				continue;
			}
			
			if(negation_window > 0){
				if(conj_fol.contains(word.trim())){
					break;
				}
				featureVector.put("Negation-"+word, 1.0);
				featureNames.add("Negation-"+word);
				negation_window--;
			}
			
		}
	}

	
	/**
	 * Gets Discourse features
	 * @param tweet
	 * @param featureVector
	 * @throws IOException
	 */
	public void discourseFeature(String tweet, Map <String, Double> featureVector) throws IOException {
		String[] wordList = tweet.split(" ");
		List<String> conditionals = Arrays.asList(ApplicationDetails.CONDITIONALS.split(","));
		List<String> conj_fol = Arrays.asList(ApplicationDetails.CONJ_FOL.split(","));
		boolean conjFlag = false;
		for (String word : wordList) {
			
			double weight = 1;
			if(conditionals.contains(word.trim())){
				featureVector.put("hyp_"+word, 1.0);
				
			}
			else if(conj_fol.contains(word.trim())){
				conjFlag = true;
			}
			
			if(conjFlag){
				
				weight = weight+1;
			}
			featureVector.put("weight_"+word, weight);
			
		}
	}

	
	/**
	 * Get Ngram present/absent feature
	 * @param tweet
	 * @param featureVector
	 * @throws IOException
	 */
	public void getNgramFeatures(String tweet, Map<String, Double> featureVector) throws IOException {

		for (String unigram : unigramMap.keySet()) {
			if (tweet.contains(" "+unigram+" ")) {
				featureVector.put(unigram, 1.0);
				featureNames.add(unigram);
			}
		}
		for (String bigram : bigramMap.keySet()) {
			if (tweet.contains(" "+bigram+" ")) {
				featureVector.put(bigram, 1.0);
				featureNames.add(bigram);
			}
		}

	}

	/**
	 * Get NRC emotion Feature
	 * @param tweet
	 * @param featureVector
	 * @throws IOException
	 */
	public void getEmotionFeature(String tweet, Map<String, Double> featureVector) throws IOException {
		String[] wordList = tweet.split(" ");
		for (String word : wordList) {
			if (emotionMap.containsKey(word)) {
				List<String> emotions = emotionMap.get(word);
				for (String emotion : emotions) {
					String featureNameStr = "NRC-" + emotion.trim();

					if (featureVector.containsKey(featureNameStr))
						featureVector.put(featureNameStr, featureVector.get(featureNameStr) + 1.0);
					else
						featureVector.put(featureNameStr, 1.0);
					// String feature = word.concat("-").concat(emotion);
					// featureVector.put(feature, 1.0);
					featureNames.add(featureNameStr);
				}
			}
		}

	}

	
	/**
	 * Get NRC winning emotion feature which occurs most number of times in a text.
	 * @param tweet
	 * @param featureVector
	 * @throws IOException
	 */
	public void getWinningEmotionFeature(String tweet, Map<String, Double> featureVector) throws IOException {
		String[] wordList = tweet.split(" ");
		Map<String,Double> maxEmotionMap = new HashMap<String,Double>();
		
		for (String word : wordList) {
			if (emotionMap.containsKey(word)) {
				List<String> emotions = emotionMap.get(word);
				for (String emotion : emotions) {
					String featureNameStr = "NRC-" + emotion.trim();

					if (maxEmotionMap.containsKey(featureNameStr))
						maxEmotionMap.put(featureNameStr, maxEmotionMap.get(featureNameStr) + 1.0);
					else
						maxEmotionMap.put(featureNameStr, 1.0);
					
				}
			}
		}
		
		if(maxEmotionMap.size()==1){
			featureVector.putAll(maxEmotionMap);
		}else if(maxEmotionMap.size() > 1){
			Double maxValueInMap=(Collections.max(maxEmotionMap.values()));  // This will return max value in the Hashmap
	        for (Entry<String, Double> entry : maxEmotionMap.entrySet()) {  // Itrate through hashmap
	            if (entry.getValue()==maxValueInMap) {
	            	
	            }
	        }
		}
	}

	
	/**
	 * Adds up smiley feature to feature vector for present/absent
	 * @param tweet
	 * @param featureVector
	 * @param smileyMap
	 * @throws IOException
	 */
	public void getSmileyFeature(String tweet, Map<String, Double> featureVector, Map<String, String> smileyMap)
			throws IOException {
		String[] wordList = tweet.split(" ");
		for (String word : wordList) {
			if (smileyMap.containsKey(word)) {
				String emotion = smileyMap.get(word);
				String feature = word.concat("-").concat(emotion);
				featureVector.put(feature, 1.0);
				featureNames.add(feature);

			}
		}
	}
	
	
	/**
	 * Gives score to different smiley in text at different scale
	 * @param tweet
	 * @param featureVector
	 * @param smileyMap
	 * @throws IOException
	 */
	public void getSmileyFeatureScores(String tweet, Map<String, Double> featureVector, Map<String, String> smileyMap)
			throws IOException {
		String[] wordList = tweet.split(" ");
		for (String word : wordList) {
			if (smileyMap.containsKey(word)) {
				String val = smileyMap.get(word);
				String feature = word;
				if(val.equalsIgnoreCase("Extremely-Positive")){
					featureVector.put(feature, 2.0);	
				}
				else if (val.equalsIgnoreCase("Extremely-Negative")) {
					featureVector.put(feature, -2.0);
				}
				else if (val.equalsIgnoreCase("Positive")) {
					featureVector.put(feature, 1.0);
				}
				else if (val.equalsIgnoreCase("Negative")) {
					featureVector.put(feature, -1.0);
				}
				else if (val.equalsIgnoreCase("Neutral")) {
					featureVector.put(feature, 0.0);
				}
				featureVector.put(feature, 1.0);
				featureNames.add(feature);

			}
		}
	}
	
	
	/**
	 * (Not used in pipeline) Write feature vectors in a file
	 * @param fileName
	 * @param listTweetVO
	 * @throws IOException
	 */
	public void writeFeatures(String fileName,List<TweetVO> listTweetVO) throws IOException{
	
		
		FileWriter writer = new FileWriter(fileName);
		FileWriter writerClasses = new FileWriter("D:/Uni-MS/NLPTeamLabWorkSpace/Data/50K-Data/balanced-class-test-model5-wo-stddev");
		System.out.println("Start Writing");
		for(TweetVO tweetVO : listTweetVO){
			
			writer.append(tweetVO.getGoldLabel());
			writer.append(",");
			writer.append(tweetVO.getPredictedLabel());
			writer.append(",");
			writer.append(""+tweetVO.getWeightStr());
			writer.append(",");
			
			
			writer.append(tweetVO.getTweetID());
			writer.append(",");
			writer.append(tweetVO.getTweet());
			writer.append(",");
			
			if(ApplicationDetails.RANKING_PERCEPTRON){
				writerClasses.append(tweetVO.getTweetID());
				writerClasses.append(",");
				writerClasses.append(tweetVO.getGoldLabel());
				writerClasses.append(",");
				for(String predicted : tweetVO.getPredictedLabels()){
					writerClasses.append(predicted);
					writerClasses.append(",");
					
				}
				for(String labels: tweetVO.getScoreMap().keySet()){
					writerClasses.append(labels+":"+tweetVO.getScoreMap().get(labels));
					writerClasses.append(",");
				}
				writerClasses.append(tweetVO.getTweet());
				writerClasses.append("\n");
			}
			
			Map<String,Double> featureMap = tweetVO.getFeatureVector();
			
			for(String features : featureMap.keySet()){
				writer.append(features);
				writer.append(":");
				writer.append(""+featureMap.get(features));
				writer.append(",");				
			}
			writer.append("\n");				
		}
		writer.flush();
		writer.close();
		writerClasses.flush();
		writerClasses.close();
			
	}

}
