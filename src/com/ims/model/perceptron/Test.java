package com.ims.model.perceptron;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ims.FeatureEngineering.FeatureVectorExtraction;
import com.ims.data.ApplicationDetails;
import com.ims.data.ReadData;
import com.ims.evaluation.process.Results;
import com.ims.evaluation.vo.ResultVO;
import com.ims.vo.CorpusVO;
import com.ims.vo.TweetVO;

public class Test {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Test testObj = new Test();
		CorpusVO corpus = new CorpusVO();
		corpus.setFileName(ApplicationDetails.testPreprcossedTweetFile);
		corpus.setTrainData(true);
		corpus.setModelFileName(ApplicationDetails.modelFile);

		
		ReadData readObj = new ReadData();

		List<TweetVO> listTweetVO = readObj.getDataValues(corpus).getListTweetVO();
		FeatureVectorExtraction featureExt = new FeatureVectorExtraction();
		listTweetVO = featureExt.getFeatureVectors(listTweetVO);

		PerceptronModelling perceptron = new PerceptronModelling();
		List<TweetVO> listTweetVOPredicted = perceptron.perceptronTest(corpus.getModelFileName(), listTweetVO);
		featureExt.writeFeatures(ApplicationDetails.testFeatureWriteFile,
				listTweetVOPredicted);
		if(ApplicationDetails.RANKING_PERCEPTRON){
			testObj.getCorrelationMap(listTweetVOPredicted);
		}
		testObj.getConfusionMatrix(listTweetVOPredicted);
		Results result = Results.getInstance();
		
		ResultVO evalAggDO = result.getConfusionMatrix(listTweetVOPredicted);
		result.getResult(evalAggDO);
		testObj.writeResults(ApplicationDetails.testOutDataFile, listTweetVOPredicted);
	}
	
	public void getConfusionMatrix(List<TweetVO> listTweetVOPredicted){
		
		Map<String, Map<String, Integer>> otherClassMap = initiazeCorrelationMap();
	
		for (TweetVO tweetInstance : listTweetVOPredicted) {
			
			String gold = tweetInstance.getGoldLabel();
			String predicted = tweetInstance.getPredictedLabel();
			Map<String,Integer> classMap = otherClassMap.get(gold);
			classMap.put(predicted, classMap.get(predicted)+1);
			otherClassMap.put(gold, classMap);
		}
		
		
		for (String label : otherClassMap.keySet()) {
			Map<String, Integer> classmAp = otherClassMap.get(label);
			for (String labelInner : classmAp.keySet()) {
				System.out
						.println("Total number for gold : " + label + " :with: " + labelInner + " :" + classmAp.get(labelInner));
			}

		}

	}

	public Map<String, Double> getCorrelationMap(List<TweetVO> listTweetVO) throws IOException {
		Map<String, Double> corMap = new HashMap<String, Double>();

		Map<String, Integer> countMap = new HashMap<String, Integer>();

		Map<String, Map<String, Integer>> otherClassMap = initiazeCorrelationMap();

		String classLAbel[] = { "sad", "anger", "fear", "happy", "disgust", "surprise" };
		for (String label : classLAbel) {
			countMap.put(label, 0);
			Map<String, Integer> classMap = otherClassMap.get(label);
			for (TweetVO tweetInstance : listTweetVO) {
				List<String> predictedLabels = tweetInstance.getPredictedLabels();
				if (predictedLabels.contains(label)) {
					countMap.put(label, countMap.get(label) + 1);
					for (String predicted : predictedLabels) {
						if (predicted.equalsIgnoreCase(label))
							continue;
						classMap.put(predicted, classMap.get(predicted)+1);
						otherClassMap.put(label, classMap);
					}

				}

			}

		}

		for (String label : otherClassMap.keySet()) {
			Map<String, Integer> classmAp = otherClassMap.get(label);
			System.out.println("Total number for " + label + ": " + countMap.get(label));
			for (String labelInner : classmAp.keySet()) {
				System.out
						.println("Total number for " + label + " with " + labelInner + " :" + classmAp.get(labelInner));
			}

		}

		return corMap;

	}

	public Map<String, Map<String, Integer>> initiazeCorrelationMap() {

		Map<String, Map<String, Integer>> otherClassMap = new HashMap<String, Map<String, Integer>>();
		String classLAbel[] = { "sad", "anger", "fear", "happy", "disgust", "surprise" };
		for (String elem : classLAbel) {
			Map<String, Integer> classMap = new HashMap<String, Integer>();
			for (String elem2 : classLAbel) {
				/*if (elem2.equals(elem))
					continue;*/
				classMap.put(elem2, 0);
			}

			otherClassMap.put(elem, classMap);

		}

		return otherClassMap;

	}

	
	public void writeResults(String fileName, List<TweetVO> listTweetVOPredicted) throws IOException {

		FileWriter writer = new FileWriter(fileName);
		for (TweetVO tweetInstance : listTweetVOPredicted) {
			writer.append("" + tweetInstance.getPredictedLabel());
			writer.append(",");
			writer.append("" + tweetInstance.getGoldLabel());
			writer.append(",");
			writer.append("" + tweetInstance.getTweet());
			writer.append(",");

			Map<String, Double> featureList = tweetInstance.getFeatureVector();
			for (String featureId : featureList.keySet()) {
				writer.append("" + featureList.get(featureId));
				writer.append(",");
			}
			writer.append("\n");
		}

	}

	public List<TweetVO> getTweetVO(String fileName) {

		List<TweetVO> listTweetVO = new ArrayList<TweetVO>();
		// Map<String,Integer> classLAbel =
		// ApplicationDetails.getInstance().getMapLabel();
		BufferedReader br;
		try {
			String line;
			br = new BufferedReader(new FileReader(fileName));
			int i = 0;
			while ((line = br.readLine()) != null) {
				if (line.isEmpty())
					continue;
				i++;
				String elem[] = line.split(",");
				TweetVO tweetVO = new TweetVO();
				tweetVO.setTweetID(i + "");
				Map<String, Double> featureVector = new HashMap<String, Double>();
				tweetVO.setFeatureVector(featureVector);
				tweetVO.setGoldLabel(elem[4]);
				listTweetVO.add(tweetVO);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return listTweetVO;

	}

}
