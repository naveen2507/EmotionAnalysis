package com.ims.evaluation.process;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ims.data.ApplicationDetails;
import com.ims.evaluation.vo.EvalautionVO;
import com.ims.evaluation.vo.ResultVO;
import com.ims.vo.TweetVO;

/**
 * 
 * @author Naveen
 * 
 *         Evaluation class gives you the measures : For each class label :
 *         accuracy, precision,recall,f-score Functions: getConfusionMatrix() :
 *         Gets the confusion matrix for each class getFScore() : Input
 *         precision and recall and gives out f-score getPrecison(): Input tp
 *         and fp and outputs precision getRecall() : Input tp and fn and
 *         outputs recall getMacroScore() : Inputs EvaluationAggregateDO object
 *         and total number of classes and returns score getMicroScore() :
 *         Inputs EvaluationAggregateDO object and returns score.
 *         getEvalautionMetric() : Input EvalautionDO obj and returns the object
 *         after setting precision,recall, fscore by calling in individual
 *         method.
 * 
 * 
 * 
 *
 */
public class Evaluation {

	private static Evaluation eval;

	public static synchronized Evaluation getInstance() {
		if (eval == null)
			eval = new Evaluation();
		return eval;
	}

	public Map<String, EvalautionVO> getRankingConfusionMatrix(List<TweetVO> listTweetDo) {

		// ReadData obj = new ReadData();
		// List<TweetVO> listTweetDo =
		// obj.getDataValues(corpus).getListTweetVO();
		Map<String, EvalautionVO> valueMatrix = new HashMap<String, EvalautionVO>();

		for (int i = 0; i < listTweetDo.size(); i++) {

			TweetVO tweetDO = listTweetDo.get(i);
			List<String> predictedLabels = tweetDO.getPredictedLabels();
			String gold = tweetDO.getGoldLabel();

			if (checkPredictedLabels(gold, predictedLabels)) {

				if (valueMatrix.containsKey(gold)) {
					EvalautionVO values = valueMatrix.get(gold);
					values.setTp(values.getTp() + 1);
					valueMatrix.put(gold, values);
				} else {
					EvalautionVO values = EvalautionVO.getInstance();
					values.setTp(values.getTp() + 1);
					valueMatrix.put(gold, values);

				}

			}

			else if (!(checkPredictedLabels(gold, predictedLabels))) {

				if (valueMatrix.containsKey(gold)) {
					EvalautionVO values = valueMatrix.get(gold);
					values.setFn(values.getFn() + 1);
					valueMatrix.put(gold, values);
				} else {
					EvalautionVO values = EvalautionVO.getInstance();
					values.setFn(values.getFn() + 1);
					valueMatrix.put(gold, values);
				}

				for (String predicted : predictedLabels) {

					if (valueMatrix.containsKey(predicted)) {
						EvalautionVO values = valueMatrix.get(predicted);
						values.setFp(values.getFp() + 1);
						valueMatrix.put(predicted, values);
					} else {
						EvalautionVO values = EvalautionVO.getInstance();
						values.setFp(values.getFp() + 1);
						valueMatrix.put(predicted, values);
					}

				}
			}

		}
		return valueMatrix;

	}

	public Map<String, EvalautionVO> getConfusionMatrix(List<TweetVO> listTweetDo) {

		// ReadData obj = new ReadData();
		// List<TweetVO> listTweetDo =
		// obj.getDataValues(corpus).getListTweetVO();
		Map<String, EvalautionVO> valueMatrix = new HashMap<String, EvalautionVO>();

		for (int i = 0; i < listTweetDo.size(); i++) {

			TweetVO tweetDO = listTweetDo.get(i);
			String predicted = tweetDO.getPredictedLabel();
			String gold = tweetDO.getGoldLabel();

			if (gold.equalsIgnoreCase(predicted)) {

				if (valueMatrix.containsKey(gold)) {
					EvalautionVO values = valueMatrix.get(gold);
					values.setTp(values.getTp() + 1);
					valueMatrix.put(gold, values);
				} else {
					EvalautionVO values = EvalautionVO.getInstance();
					values.setTp(values.getTp() + 1);
					valueMatrix.put(gold, values);

				}

			}

			if (!(gold.equalsIgnoreCase(predicted))) {

				if (valueMatrix.containsKey(gold)) {
					EvalautionVO values = valueMatrix.get(gold);
					values.setFn(values.getFn() + 1);
					valueMatrix.put(gold, values);
				} else {
					EvalautionVO values = EvalautionVO.getInstance();
					values.setFn(values.getFn() + 1);
					valueMatrix.put(gold, values);
				}

				if (valueMatrix.containsKey(predicted)) {
					EvalautionVO values = valueMatrix.get(predicted);
					values.setFp(values.getFp() + 1);
					valueMatrix.put(predicted, values);
				} else {
					EvalautionVO values = EvalautionVO.getInstance();
					values.setFp(values.getFp() + 1);
					valueMatrix.put(predicted, values);
				}
			}

		}
		return valueMatrix;

	}

	public double getFScore(double precision, double recall) {

		if (precision == 0 || recall == 0) {
			return 0.0;
		}
		double fscore = (2 * precision * recall) / (precision + recall);
		return fscore;

	}

	public double getPrecision(double tp, double fp) {
		double precision = (double) tp / (tp + fp);
		return precision;

	}

	public double getRecall(double tp, double fn) {
		double recall = (double) tp / (tp + fn);
		return recall;

	}

	public EvalautionVO getEvalautionMetric(EvalautionVO confMatrixDO) {

		double precision = getPrecision(confMatrixDO.getTp(), confMatrixDO.getFp());
		double recall = getRecall(confMatrixDO.getTp(), confMatrixDO.getFn());
		confMatrixDO.setPrecision(precision);
		confMatrixDO.setRecall(recall);
		confMatrixDO.setFscore(getFScore(precision, recall));
		return confMatrixDO;

	}

	public ResultVO getMacroScore(ResultVO metric, int totalClass) {
		metric.setMacrofscore(metric.getAggFScore() / totalClass);
		return metric;
	}

	public ResultVO getMicroScore(ResultVO metric) {
		double aggPrecision = getPrecision(metric.getAggTP(), metric.getAggFP());
		double aggRecall = getRecall(metric.getAggTP(), metric.getAggFN());
		metric.setMicrofscore(getFScore(aggPrecision, aggRecall));
		return metric;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public boolean checkPredictedLabels(String gold, List<String> predictedLabels) {
		boolean status = false;
		for (String predicted : predictedLabels) {
			if (gold.equalsIgnoreCase(predicted)) {
				status = true;
				break;
			}

		}
		return status;

	}
	
	
	public Map<String, EvalautionVO> getRankingNumConfusionMatrix(List<TweetVO> listTweetDo) {

		Map<String, EvalautionVO> valueMatrix = new HashMap<String, EvalautionVO>();

		for (int i = 0; i < listTweetDo.size(); i++) {
			TweetVO tweetDO = listTweetDo.get(i);
			List<String> predictedLabels = getTopRankingLevels(tweetDO.getScoreMap());			
			String gold = tweetDO.getGoldLabel();
			
			if (checkPredictedLabels(gold, predictedLabels)) {

				for(String currentPrediction: predictedLabels){
					
					if (valueMatrix.containsKey(currentPrediction)) {
						EvalautionVO values = valueMatrix.get(currentPrediction);
						values.setTp(values.getTp() + (double)1/(ApplicationDetails.RANKING_LEVEL));
						valueMatrix.put(currentPrediction, values);
					} else {
						EvalautionVO values = EvalautionVO.getInstance();
						values.setTp(values.getTp() + (double)1/(ApplicationDetails.RANKING_LEVEL));
						valueMatrix.put(currentPrediction, values);

					}

					
				}
				
			}

			else if (!(checkPredictedLabels(gold, predictedLabels))) {

				if (valueMatrix.containsKey(gold)) {
					EvalautionVO values = valueMatrix.get(gold);
					values.setFn(values.getFn() + 1);
					valueMatrix.put(gold, values);
				} else {
					EvalautionVO values = EvalautionVO.getInstance();
					values.setFn(values.getFn() + 1);
					valueMatrix.put(gold, values);
				}

				for (String predicted : predictedLabels) {

					if (valueMatrix.containsKey(predicted)) {
						EvalautionVO values = valueMatrix.get(predicted);
						values.setFp(values.getFp() + (double)1/(ApplicationDetails.RANKING_LEVEL));
						valueMatrix.put(predicted, values);
					} else {
						EvalautionVO values = EvalautionVO.getInstance();
						values.setFp(values.getFp() + (double)1/(ApplicationDetails.RANKING_LEVEL));
						valueMatrix.put(predicted, values);
					}

				}
			}

		}
		return valueMatrix;

	}
	
	public List<String> getTopRankingLevels(Map<String,Double> scoreMap){
		
		int i = 0 ;
		List<String> topRankedPrediction = new ArrayList<String>();
		for(String key : scoreMap.keySet() ){
			i++;
			if(i>ApplicationDetails.RANKING_LEVEL)
				break;
			topRankedPrediction.add(key);						
		}
		return topRankedPrediction;
		
	}

}
