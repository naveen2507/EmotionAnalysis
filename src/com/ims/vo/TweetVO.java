package com.ims.vo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * 
 * @author Naveen
 * Class Description : DataObject definition for data. It reads the file and stores the value as an object for each row/instance.
 * 					   It includes tweetID,tweet,goldLabel,predictedLabel,userName,profileName,language .
 * 
 */
public class TweetVO {

	private String tweetID;
	private String tweet;
	private String goldLabel;
	private String predictedLabel;
	private String preprocessedTweet;
	private String userName;
	private String profileName;
	private String language;
	private String weightStr;
	
	private Map<String,Double> scoreMap;
	private List<String> predictedLabels;
	private int binaryGoldLabel;	
	private Map<String,Double> featureVector;
	private Double maxScoreCategory = 0.0;

	
	public List<String> getPredictedLabels() {
		return predictedLabels;
	}

	public void setPredictedLabels(List<String> predictedLabels) {
		this.predictedLabels = predictedLabels;
	}

	public Map<String, Double> getScoreMap() {
		return scoreMap;
	}

	public void setScoreMap(Map<String, Double> scoreMap) {
		this.scoreMap = scoreMap;
	}

	public String getPreprocessedTweet() {
		return preprocessedTweet;
	}

	public void setPreprocessedTweet(String preprocessedTweet) {
		this.preprocessedTweet = preprocessedTweet;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getProfileName() {
		return profileName;
	}

	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getTweetID() {
		return tweetID;
	}

	public void setTweetID(String tweetID) {
		this.tweetID = tweetID;
	}

	public String getTweet() {
		return tweet;
	}

	public void setTweet(String tweet) {
		this.tweet = tweet;
	}

	public String getGoldLabel() {
		return goldLabel;
	}

	public void setGoldLabel(String goldLabel) {
		this.goldLabel = goldLabel;
	}

	public String getPredictedLabel() {
		return predictedLabel;
	}

	public void setPredictedLabel(String predictedLabel) {
		this.predictedLabel = predictedLabel;
	}

	public int getBinaryGoldLabel() {
		return binaryGoldLabel;
	}

	public void setBinaryGoldLabel(int binaryGoldLabel) {
		this.binaryGoldLabel = binaryGoldLabel;
	}

	public Map<String, Double> getFeatureVector() {
		return featureVector;
	}

	public void setFeatureVector(Map<String, Double> featureVector) {
		this.featureVector = featureVector;
	}

	public Double getMaxScoreCategory() {
		return maxScoreCategory;
	}

	public void setMaxScoreCategory(Double maxScoreCategory) {
		this.maxScoreCategory = maxScoreCategory;
	}

	public Double getCosineSimilarityScore(TweetVO tweetVO2){
		TweetVO tweetVO1 = TweetVO.this;
		List<String> uniqueList = getUniqueList(tweetVO1.getPreprocessedTweet(), tweetVO2.getPreprocessedTweet());
		double score = getScore(uniqueList, tweetVO1.getPreprocessedTweet().toLowerCase(), tweetVO2.getPreprocessedTweet().toLowerCase());
		return score;
	}
	
	
	public List<String> getUniqueList(String tweet1,String tweet2){
		
		List<String> wordList = new ArrayList<String>();
		StringTokenizer tweet1Elements = new StringTokenizer(tweet1," ");
		StringTokenizer tweet2Elements = new StringTokenizer(tweet2," ");

		
		while(tweet1Elements.hasMoreTokens()){
			
			String word = tweet1Elements.nextToken().trim();
			if(wordList.contains(word.toLowerCase())){
				continue;
			}
			else{
				wordList.add(word);
			}
		}
		
		while(tweet2Elements.hasMoreTokens()){
			
			String word = tweet2Elements.nextToken().trim();
			if(wordList.contains(word.toLowerCase())){
				continue;
			}
			else{
				wordList.add(word);
			}
		}
		
		return wordList;
		
	}

	
	public Double getScore(List<String> wordList,String tweet1, String tweet2 ){
		
		List<Integer> similarityVector1 = new LinkedList<Integer>();
		List<Integer> similarityVector2 = new LinkedList<Integer>();
		
		for(String word : wordList){
			
			if(tweet1.contains(word.toLowerCase())){
				similarityVector1.add(1);
			}
			else{
				similarityVector1.add(0);
			}
			if(tweet2.contains(word.toLowerCase())){
				similarityVector2.add(1);
			}
			else{
				similarityVector2.add(0);
			}
		}
		
		double numerator = 0;
		double denListTweet1Sqr=0;
		double denListTweet2Sqr=0;
		
		for(int i=0;i<wordList.size();i++){
			int a = similarityVector1.get(i);
			int b = similarityVector2.get(i);
			numerator = numerator + (a*b);
			denListTweet1Sqr=denListTweet1Sqr+Math.pow(a,2);
			denListTweet2Sqr =denListTweet2Sqr+Math.pow(b,2);
		}
		double cosineSimilarityScore = numerator/((Math.sqrt(denListTweet1Sqr))*(Math.sqrt(denListTweet2Sqr)));
		return cosineSimilarityScore;
		
	}

	public String getWeightStr() {
		return weightStr;
	}

	public void setWeightStr(String weightStr) {
		this.weightStr = weightStr;
	}
	
	
	
	
}
