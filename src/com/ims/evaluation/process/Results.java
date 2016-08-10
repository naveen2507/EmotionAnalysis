package com.ims.evaluation.process;

import java.util.List;
import java.util.Map;

import com.ims.data.ApplicationDetails;
import com.ims.data.ReadData;
import com.ims.evaluation.vo.EvalautionVO;
import com.ims.evaluation.vo.ResultVO;
import com.ims.vo.CorpusVO;
import com.ims.vo.TweetVO;

public class Results {

	/**
	 * @author Naveen
	 * Class Description : 
	 * 
	 * This is main class for result evaluation. 
	 * Input : Data File tab separated
	 * Data Format : PredictedClass, GoldClass , and rest fields as per in orginal file
	 * Output :
	 * Prints evaluation metrics for each class and print macro and micro F-score.
	 * 
	 */
	
	
	private static Results result;

	public static synchronized Results getInstance() {
		if (result == null)
			result = new Results();
		return result;
	}

	/**
	 * Read data from corpus
	 * @param corpus
	 * @return List<TweetVO>
	 */
	public List<TweetVO> getData(CorpusVO corpus) {
		ReadData obj = new ReadData();
		List<TweetVO> listTweetDo = obj.getDataValues(corpus).getListTweetVO();
		return listTweetDo;
	}
	
	/**
	 * Get confusion Matrix depending upon the configuration for Ranking or not
	 * @param listTweetVO
	 * @return
	 */
	public ResultVO getConfusionMatrix(List<TweetVO> listTweetVO){
		Evaluation eval = Evaluation.getInstance();
		Map<String, EvalautionVO> confusionMatrix;
		if(ApplicationDetails.RANKING_PERCEPTRON){
			confusionMatrix = eval.getRankingConfusionMatrix(listTweetVO);
		}
		
		//INCR_RANKING states for top N ranking and adding TP and FP by 1/N
		else if (ApplicationDetails.INCR_RANKING_PERCEPTRON) {
			confusionMatrix = eval.getRankingNumConfusionMatrix(listTweetVO);
		}
		else{
			confusionMatrix = eval.getConfusionMatrix(listTweetVO);
		}		
		ResultVO metric = ResultVO.getInstance();
		metric.setEvaluationMatrix(confusionMatrix);
		return metric;
		
	}
	
	

	/**
	 * Print results 
	 * @param metric
	 */
	public void getResult(ResultVO metric) {

		
		Map<String, EvalautionVO> confusionMatrix = metric.getEvaluationMatrix();
		Evaluation eval = Evaluation.getInstance();
		
		for (String key : confusionMatrix.keySet()) {

			EvalautionVO evalValue = eval.getEvalautionMetric(confusionMatrix.get(key));

			//System.out.println("True Positive for " + key + " : " + evalValue.getTp());
			metric.setAggTP(metric.getAggTP() + evalValue.getTp());

			//System.out.println("False Positive for " + key + " : " + evalValue.getFp());
			metric.setAggFP(metric.getAggFP() + evalValue.getFp());

			//System.out.println("True Negative for " + key + " : " + evalValue.getTn());
			metric.setAggTN(metric.getAggTN() + evalValue.getTn());

			//System.out.println("False Negative for " + key + " : " + evalValue.getFn());
			metric.setAggFN(metric.getAggFN() + evalValue.getFn());

			//System.out.println("Recall for " + key + " : " + evalValue.getRecall());
			//System.out.println("Precision for " + key + " : " + evalValue.getPrecision());
			System.out.println("FScore for " + key + " : " + evalValue.getFscore());
			metric.setAggFScore(metric.getAggFScore() + evalValue.getFscore());
		}

		//System.out.println("---------------Micro FScore-------------------");
		metric = eval.getMicroScore(metric);
		System.out.println("Micro FScore  : " + metric.getMicrofscore());

		//System.out.println("---------------Macro FScore-------------------");
		metric = eval.getMacroScore(metric, confusionMatrix.size());
		System.out.println("Macro FScore  : " + metric.getMacrofscore());

		System.out.println("Total Tp :"+metric.getAggTP());
		//(ApplicationDetails.RANKING_PERCEPTRON){		
			//double accuracy = metric.getAggTP()/2608.0;
			//System.out.println("Sum of Fn,FP,TP "+ metric.getAggFN()+metric.getAggFP()+metric.getAggTP());
			//System.out.println("Accuracy Score : "+ accuracy);
			
		//}
		
	}

	
	/**
	 * 
	 * 
	 * @param arg
	 * 
	 * 
	 */

	
	
	public static void main(String arg[]){
		
		Results result = Results.getInstance();
		CorpusVO corpus = new CorpusVO();
		corpus.setFileName("D:/Uni-MS/NLPTeamLabWorkSpace/Data/sample-data.csv");
		corpus.setTestData(true);
		List<TweetVO> listTweetVO = result.getData(corpus);
		ResultVO evalAggDO = result.getConfusionMatrix(listTweetVO);
		
		result.getResult(evalAggDO);
		
	}
}
