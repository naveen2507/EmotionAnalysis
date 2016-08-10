package com.ims.model.perceptron;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ims.vo.TweetVO;


/**
 * Experimental Setup : It is part of experimentation where we tried to get some boundary for getting Top Ranked Perceptron
 * using some metrics of Standard deviation
 * @author Naveen
 *
 */
public class RankingPerceptron {

	
	public Map<String,Double> getTopRankedPerceptrons(TweetVO tweetInstance) {
		
		Map<String,Double> rankedPerceptrons = new HashMap<String,Double>();
		Map<String, Double> scoreWeight = tweetInstance.getScoreMap();
		//if(tweetInstance.getTweetID().equalsIgnoreCase("718346160465952768"))
		//	System.out.println("SDSDS");
		double boundary = getBoundaryPerceptron(scoreWeight);
		System.out.println();
		for (String label : scoreWeight.keySet()) {
			double value = scoreWeight.get(label);
			if(value>boundary){
				System.out.println();
				rankedPerceptrons.put(label, value);
			}
		}
		return rankedPerceptrons;
	}

	public double getBoundaryPerceptron(Map<String, Double> scoreWeight) {

		Collection<Double> listValue = scoreWeight.values();
		double standDeviation = getStdDev(listValue);
		double diff = 0;
		double highest = 0;
		double prev = 1000;
		double boundary=0;
		int i = 0;
		for (String label : scoreWeight.keySet()) {
			double value = scoreWeight.get(label);
			if(i==0){
				prev = value;
				highest = value; 
				i++;
				boundary = value-1;
				continue;		
			}
			
			double localDiff = prev-value;
			//if(localDiff>diff && value<(highest-2*standDeviation)){
			//if(localDiff>diff && localDiff>1.5*standDeviation){
			if(localDiff>diff){
				diff = localDiff;
				boundary = value;
			}
			prev = value;
			i++;
		}

		return boundary;

	}
	

    public double getMean(Collection<Double> data)
    {
        double sum = 0.0;
        for(double a : data)
            sum += a;
        return sum/data.size();
    }

    public double getVariance(Collection<Double> data)
    {
        double mean = getMean(data);
        double temp = 0;
        for(double a :data)
            temp += (mean-a)*(mean-a);
        return temp/data.size();
    }

    public double getStdDev(Collection<Double> data)
    {
        return Math.sqrt(getVariance(data));
    }


}
