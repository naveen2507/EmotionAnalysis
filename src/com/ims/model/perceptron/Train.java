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
import com.ims.vo.CorpusVO;
import com.ims.vo.TweetVO;

public class Train {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		// Train trainObj = new Train();
		CorpusVO corpus = new CorpusVO();
		corpus.setFileName(ApplicationDetails.trainPreprcossedTweetFile);
		// corpus.setFileName("D:\\Uni-MS\\NLPTeamLabWorkSpace\\Data\\4K-Data\\sample-data-preprocessed-train.csv");

		corpus.setTrainData(true);
		corpus.setModelFileName(ApplicationDetails.modelFile);
		// corpus.setModelFileName("D:\\Uni-MS\\NLPTeamLabWorkSpace\\Data\\4K-Data\\model-4Kdata-w-NRC-DiscNeg");

		ReadData readObj = new ReadData();

		List<TweetVO> listTweetVO = readObj.getDataValues(corpus).getListTweetVO();
		FeatureVectorExtraction featureExt = new FeatureVectorExtraction();
		listTweetVO = featureExt.getFeatureVectors(listTweetVO);
		featureExt.writeFeatures(ApplicationDetails.trainFeatureWriteFile, listTweetVO);
		PerceptronModelling perceptron = new PerceptronModelling();
		perceptron.perceptronTrain(listTweetVO, corpus.getModelFileName());

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
				i++;

				TweetVO tweetVO = new TweetVO();
				String elem[] = line.split(",");
				tweetVO.setTweetID("" + i);
				Map<String, Double> featureVector = new HashMap<String, Double>();
				featureVector.put("1", Double.parseDouble(elem[0]));
				featureVector.put("2", Double.parseDouble(elem[1]));
				featureVector.put("3", Double.parseDouble(elem[2]));
				featureVector.put("4", Double.parseDouble(elem[3]));
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
