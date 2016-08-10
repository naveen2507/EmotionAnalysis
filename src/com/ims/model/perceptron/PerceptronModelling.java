package com.ims.model.perceptron;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ims.data.ApplicationDetails;
import com.ims.vo.TweetVO;
/**
 * 
 * @author Naveen
 *
 */
public class PerceptronModelling {

	/**
	 * Training Method
	 * @param listTweetVO
	 * @param modelfileName
	 * @throws IOException
	 */
	public void perceptronTrain(List<TweetVO> listTweetVO, String modelfileName) throws IOException {

		Map<String, Map<String, Double>> weightMap = initializeWeightVecor();

		MultiClassPerceptron perceptron = new MultiClassPerceptron();

		weightMap = perceptron.trainModel(listTweetVO, weightMap);

		writeWeights(modelfileName, weightMap);

	}

	public List<TweetVO> perceptronTest(String weightFileName, List<TweetVO> listTweetVO)
			throws IOException {

		Map<String, Map<String, Double>> weightMap = readWeights(weightFileName);
		MultiClassPerceptron perceptron = new MultiClassPerceptron();
		List<TweetVO> listPredictedTweet = new ArrayList<TweetVO>();
		for (TweetVO tweetInstance : listTweetVO) {

			tweetInstance = perceptron.testModel(tweetInstance, weightMap);
			System.out.println("GOldClass : " + tweetInstance.getGoldLabel() + "  ::::  Predicted Class ::"
					+ tweetInstance.getPredictedLabel());
			listPredictedTweet.add(tweetInstance);
		}

		return listPredictedTweet;

	}

	public void writeWeights(String fileName, Map<String, Map<String, Double>> weightMap) throws IOException {

		FileWriter writer = new FileWriter(fileName);

		for (String label : weightMap.keySet()) {
			writer.append(label);
			writer.append("\n");
			Map<String, Double> weight = weightMap.get(label);
			for (String feature : weight.keySet()) {
				writer.append(feature + ApplicationDetails.model_file_sep + weight.get(feature));
				writer.append("\n");
			}
			writer.append(ApplicationDetails.model_file_class_sep);
			writer.append("\n");
		}
		writer.flush();
		writer.close();

	}

	public Map<String, Map<String, Double>> readWeights(String fileName) throws IOException {

		//int featuresNumber = ApplicationDetails.numOfFeatures + 1;
		Map<String, Map<String, Double>> weightMap = new HashMap<String, Map<String, Double>>();

		String line;
		BufferedReader br;
		try {
			Map<String, Double> weight = new HashMap<String, Double>();
			String tempCategory = "";
			int i = 0;
			br = new BufferedReader(new FileReader(fileName));
			while ((line = br.readLine()) != null) {

				if (line.isEmpty()) {
					continue;
				} else if (line.contains(ApplicationDetails.model_file_sep)) {
					String elem[] = line.split(ApplicationDetails.model_file_sep);
					try{
					weight.put(elem[0], Double.parseDouble(elem[1]));
					}catch  (Exception e){
						System.out.println(line);
						
						System.out.println(elem[0]+"--------"+elem[1]);
						
						break;
					}
				} 
				else if (line.equalsIgnoreCase(ApplicationDetails.model_file_class_sep)) {
					weightMap.put(tempCategory, weight);
					weight = new HashMap<String, Double>();
					tempCategory = "";
				}
				else {
					tempCategory = line;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return weightMap;

	}

	public Map<String, Map<String, Double>> initializeWeightVecor() {

		Map<String, Map<String, Double>> weightMap = new HashMap<String, Map<String, Double>>();
		List<String> classLabels = ApplicationDetails.labels;
		for (String label : classLabels) {
			Map<String, Double> classWeightVector = new HashMap<String, Double>();
			classWeightVector.put(ApplicationDetails.feature_pheta, ApplicationDetails.theta);
			weightMap.put(label, classWeightVector);
		}
		return weightMap;
	}

}
