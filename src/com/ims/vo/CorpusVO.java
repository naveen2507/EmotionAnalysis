package com.ims.vo;

import java.util.List;

public class CorpusVO {

	String fileName;
	String modelFileName;
	boolean testData;
	boolean trainData;
	List<TweetVO> listTweetVO;

	
	
	public String getModelFileName() {
		return modelFileName;
	}

	public void setModelFileName(String modelFileName) {
		this.modelFileName = modelFileName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public boolean isTestData() {
		return testData;
	}

	public void setTestData(boolean testData) {
		this.testData = testData;
	}

	public boolean isTrainData() {
		return trainData;
	}

	public void setTrainData(boolean trainData) {
		this.trainData = trainData;
	}

	public List<TweetVO> getListTweetVO() {
		return listTweetVO;
	}

	public void setListTweetVO(List<TweetVO> listTweetVO) {
		this.listTweetVO = listTweetVO;
	}
	
	public static CorpusVO getInstance() {
		CorpusVO corpus = new CorpusVO();

		corpus.setTestData(false);
		corpus.setTrainData(false);
		return corpus;

	}

}
