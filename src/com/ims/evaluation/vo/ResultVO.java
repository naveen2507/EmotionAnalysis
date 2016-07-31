package com.ims.evaluation.vo;

import java.util.Map;

/**
 * @author Naveen
 * Class Description : DataObject definition for aggregate measures and
 * 					   getInstance() method initialise the aggregate TP,FP,TN,FN,FScore as 0
 * 					   It also has the evaluation metric for each class in a map , with key as 
 * 					   String element for class label and value as object of EvalautionDO.
 * 						
 * 	
 *
 */
public class ResultVO {

	private double aggTP;
	private double aggFP;
	private double aggTN;
	private double aggFN;
	private double aggFScore;

	private double microfscore;
	private double macrofscore;
	private Map<String, EvalautionVO> evaluationMatrix;

	public Map<String, EvalautionVO> getEvaluationMatrix() {
		return evaluationMatrix;
	}

	public void setEvaluationMatrix(Map<String, EvalautionVO> evaluationMatrix) {
		this.evaluationMatrix = evaluationMatrix;
	}

	public double getAggTP() {
		return aggTP;
	}

	public void setAggTP(double aggTP) {
		this.aggTP = aggTP;
	}

	public double getAggFP() {
		return aggFP;
	}

	public void setAggFP(double aggFP) {
		this.aggFP = aggFP;
	}

	public double getAggTN() {
		return aggTN;
	}

	public void setAggTN(double aggTN) {
		this.aggTN = aggTN;
	}

	public double getAggFN() {
		return aggFN;
	}

	public void setAggFN(double aggFN) {
		this.aggFN = aggFN;
	}

	public double getAggFScore() {
		return aggFScore;
	}

	public void setAggFScore(double aggFScore) {
		this.aggFScore = aggFScore;
	}

	public double getMicrofscore() {
		return microfscore;
	}

	public void setMicrofscore(double microfscore) {
		this.microfscore = microfscore;
	}

	public double getMacrofscore() {
		return macrofscore;
	}

	public void setMacrofscore(double macrofscore) {
		this.macrofscore = macrofscore;
	}

	public static ResultVO getInstance() {
		ResultVO eval = new ResultVO();

		eval.setAggFN(0);
		eval.setAggFP(0);
		eval.setAggTN(0);
		eval.setAggTP(0);
		eval.setAggFScore(0.0);
		return eval;

	}

}
