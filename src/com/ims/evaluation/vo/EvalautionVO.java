package com.ims.evaluation.vo;


/**
 * 
 * 
 * @author Naveen
 * Class Description : DataObject definition for confusion matrix of individual classes and other evaluation metrics
 * 					   which includes tp,fp,fn,tn,recall,precision,fscore.
 *
 */
public class EvalautionVO {

	
	private double tp ;
	private double fp;
	private double tn ;
	private double fn ;
	private double recall;
	private double precision;
	private double fscore;
	
	
	public double getRecall() {
		return recall;
	}
	public void setRecall(double recall) {
		this.recall = recall;
	}
	public double getPrecision() {
		return precision;
	}
	public void setPrecision(double precision) {
		this.precision = precision;
	}
	public double getFscore() {
		return fscore;
	}
	public void setFscore(double fscore) {
		this.fscore = fscore;
	}
	
	
	
	
	
	public double getTp() {
		return tp;
	}
	public void setTp(double tp) {
		this.tp = tp;
	}
	public double getFp() {
		return fp;
	}
	public void setFp(double fp) {
		this.fp = fp;
	}
	public double getTn() {
		return tn;
	}
	public void setTn(double tn) {
		this.tn = tn;
	}
	public double getFn() {
		return fn;
	}
	public void setFn(double fn) {
		this.fn = fn;
	}
	public static EvalautionVO getInstance(){
		
		
		EvalautionVO confMatrixDO = new EvalautionVO();
		confMatrixDO.setFn(0.0);
		confMatrixDO.setFp(0.0);
		confMatrixDO.setTn(0.0);
		confMatrixDO.setTp(0.0);
		return confMatrixDO;
		
			
	}

	
	/*public static ConfusionMatrixDO process(ConfusionMatrixDO confMatrixDO){
		
		double precision = confMatrixDO.getTp()/(confMatrixDO.getFp()+confMatrixDO.getTp());
		double recall = confMatrixDO.getTp()/(confMatrixDO.getFn()+confMatrixDO.getTp());
		confMatrixDO.setPrecision(precision);
		confMatrixDO.setRecall(recall);
		confMatrixDO.setFscore((2*precision*recall)/(precision+recall));
		return confMatrixDO;
				
	}
*/
	
	
}

