package com.ims.model.perceptron;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ims.data.ApplicationDetails;
import com.ims.evaluation.process.Results;
import com.ims.evaluation.vo.ResultVO;
import com.ims.vo.TweetVO;

public class MultiClassPerceptron {

	public Map<String, Map<String, Double>> getWeightVecors(Set<String> features,
			Map<String, Map<String, Double>> weightMap) {

		List<String> classLabels = ApplicationDetails.labels;

		for (String label : classLabels) {
			Map<String, Double> classWeightVector = weightMap.get(label);
			for (String featureStr : features) {
				if (classWeightVector.containsKey(featureStr)) {
					continue;
				} else {
					classWeightVector.put(featureStr, 0.0);
				}

			}
		}

		return weightMap;

	}

	public Map<String, Map<String, Double>> trainModel(List<TweetVO> listTweetVO,
			Map<String, Map<String, Double>> weightMap) {

		Results result = Results.getInstance();
		Map<String, Double> tempWeights;
		List<TweetVO> listTweetEval;

		System.out.println("------Training Starts-----------");
		for (int i = 0; i < ApplicationDetails.NO_OF_TRAINING_ITERATION; i++) {

			listTweetEval = new ArrayList<TweetVO>();

			Z: for (TweetVO tweetInstance : listTweetVO) {

				Map<String, Double> featureVector = tweetInstance.getFeatureVector();
				weightMap = getWeightVecors(featureVector.keySet(), weightMap);
				featureVector = tuneInitFeatureVector(featureVector);
				tweetInstance.setFeatureVector(featureVector);
				tweetInstance = getWinningPerceptron(tweetInstance, weightMap);

				if (tweetInstance.getMaxScoreCategory() == 0.0) {
					tempWeights = getAdjustedWeight(weightMap.get(tweetInstance.getGoldLabel()), true, featureVector);
					weightMap.put(tweetInstance.getGoldLabel(), tempWeights);
					continue Z;
				}
				if (!(tweetInstance.getGoldLabel().equalsIgnoreCase(tweetInstance.getPredictedLabel()))) {
					tempWeights = getAdjustedWeight(weightMap.get(tweetInstance.getGoldLabel()), true, featureVector);
					weightMap.put(tweetInstance.getGoldLabel(), tempWeights);
					tempWeights = getAdjustedWeight(weightMap.get(tweetInstance.getPredictedLabel()), false,
							featureVector);
					weightMap.put(tweetInstance.getPredictedLabel(), tempWeights);

				}
				listTweetEval.add(tweetInstance);

			}
			System.out.println("Iteration Number : " + i);

			ResultVO evalAggDO = result.getConfusionMatrix(listTweetVO);
			result.getResult(evalAggDO);

		}
		return weightMap;
	}

	/**
	 * Desc : This will return the TweetVO with the winning predicted label and
	 * the maxScore
	 * 
	 * @param tweetInstance
	 * @param weightMap
	 * @return
	 */
	public TweetVO getWinningPerceptron(TweetVO tweetInstance, Map<String, Map<String, Double>> weightMap) {

		double argmax = 0.0;
		String weighStr = "";
		Map<String, Double> featureVector = tweetInstance.getFeatureVector();
		for (String label : weightMap.keySet()) {
			// System.out.println("Label ::: "+label);
			double y = getYValue(weightMap.get(label), featureVector);
			weighStr = weighStr + label + ":" + y + ",";
			if (y > argmax) {
				argmax = y;
				tweetInstance.setPredictedLabel(label);
				tweetInstance.setMaxScoreCategory(y);
			}
		}

		weighStr = weighStr.substring(0, weighStr.length() - 1);
		tweetInstance.setWeightStr(weighStr);
		return tweetInstance;

	}

	/**
	 * Desc : This will return the TweetVO with the predicted label and
	 * their scores in sorted order (decreasing)
	 * @param tweetInstance
	 * @param weightMap
	 * @return
	 */
	public TweetVO getRankedWinningPerceptron(TweetVO tweetInstance, Map<String, Map<String, Double>> weightMap) {
		RankingPerceptron rankedPerceptronObj = new RankingPerceptron();
		HashMap<String, Double> scoreWeight = new HashMap<String, Double>();
		List<String>predictedLabel = new ArrayList<String>();
		double argmax = 0.0;
		String weighStr = "";
		Map<String, Double> featureVector = tweetInstance.getFeatureVector();
		for (String label : weightMap.keySet()) {
			// System.out.println("Label ::: "+label);
			double y = getYValue(weightMap.get(label), featureVector);
			scoreWeight.put(label, y);
			weighStr = weighStr + label + ":" + y + ",";
			if (y > argmax) {
				argmax = y;
				tweetInstance.setPredictedLabel(label);
				tweetInstance.setMaxScoreCategory(y);
			}
		}
		tweetInstance.setScoreMap(sortByValues(scoreWeight));
		//Map<String,Double> rankedPerceptron = rankedPerceptronObj.getTopRankedPerceptrons(tweetInstance);
		//weighStr = weighStr.substring(0, weighStr.length() - 1);
		
		//tweetInstance.setWeightStr(weighStr);
		//predictedLabel.addAll(rankedPerceptron.keySet());
		//tweetInstance.setPredictedLabels(predictedLabel);
		return tweetInstance;

	}

	public TweetVO testModel(TweetVO tweetInstance, Map<String, Map<String, Double>> weightMap) {
		Map<String, Double> featureVector = tweetInstance.getFeatureVector();
		featureVector = tuneInitFeatureVector(featureVector);
		if(ApplicationDetails.RANKING_PERCEPTRON || ApplicationDetails.INCR_RANKING_PERCEPTRON)
			tweetInstance = getRankedWinningPerceptron(tweetInstance, weightMap);
		else
			tweetInstance = getWinningPerceptron(tweetInstance, weightMap);
		return tweetInstance;
	}

	public Map<String, Double> tuneInitFeatureVector(Map<String, Double> featureVector) {
		featureVector.put(ApplicationDetails.feature_pheta, 1.0);
		return featureVector;

	}

	public Double getYValue(Map<String, Double> weights, Map<String, Double> featureVector) {

		double y = 0.0;
		for (String feature : featureVector.keySet()) {
			if (weights.containsKey(feature)) {
				double w = weights.get(feature);
				double x = featureVector.get(feature);
				y = y + (w * x);
			}

		}

		return y;

	}

	public Map<String, Double> getAdjustedWeight(Map<String, Double> weight, boolean action,
			Map<String, Double> featureVector) {

		// Map<String, Double> adjWeightVector = new HashMap<String, Double>();
		for (String feature : featureVector.keySet()) {

			double w = weight.get(feature);
			double x = featureVector.get(feature);
			if (action) {
				w = w + x;
			} else {
				w = w - x;
			}
			weight.put(feature, w);

		}

		return weight;

	}

	private static HashMap sortByValues(HashMap map) {
		List list = new LinkedList(map.entrySet());
		// Defined Custom Comparator here
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				return ((Comparable) ((Map.Entry) (o2)).getValue()).compareTo(((Map.Entry) (o1)).getValue());
			}
		});
		HashMap sortedHashMap = new LinkedHashMap();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			sortedHashMap.put(entry.getKey(), entry.getValue());
		}
		return sortedHashMap;
	}

}
