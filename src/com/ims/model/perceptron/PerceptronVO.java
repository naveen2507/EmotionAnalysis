package com.ims.model.perceptron;

import java.util.Map;

public class PerceptronVO {

	private String classLabel;
	private Map<String,Double> weightVector;
	private Double score;
	public String getClassLabel() {
		return classLabel;
	}
	public void setClassLabel(String classLabel) {
		this.classLabel = classLabel;
	}
	public Map<String, Double> getWeightVector() {
		return weightVector;
	}
	public void setWeightVector(Map<String, Double> weightVector) {
		this.weightVector = weightVector;
	}
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}
	
	
	
}
