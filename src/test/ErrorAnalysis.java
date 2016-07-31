package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ErrorAnalysis {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		ErrorAnalysis er = new ErrorAnalysis();
		Map<String,Map<String,Integer>> countMAp = er.getData("D:/Uni-MS/NLPTeamLabWorkSpace/Data/50K-Data/features-test-model4");
		er.getPercentage(countMAp);
		
	}
	
	public Map<String,Map<String,Integer>> getData(String fileName) throws IOException{
		
		BufferedReader br;
		
		Map<String,Map<String,Integer>> countMAp = new HashMap<String,Map<String,Integer>>();
		Map<String,Integer> predCountMAp;
		
		String line;
		br = new BufferedReader(new FileReader(fileName));
		while ((line = br.readLine()) != null) {
			
			String elem[] = line.split(",");
			
			String gold = elem[0];
			String pred = elem[1];
			
			String disgustWeight = elem[2];
			String sadWeight = elem[2];
			String angerWeight = elem[2];
			String happyWeight = elem[2];
			String surpriseWeight = elem[2];
			String fearWeight = elem[2];
			
			if(countMAp.containsKey(gold)){
				predCountMAp = countMAp.get(gold);
				if(predCountMAp.containsKey(pred)){
					predCountMAp.put(pred, predCountMAp.get(pred)+1);
					countMAp.put(gold, predCountMAp);
				}else{
					predCountMAp.put(pred, 1);
					countMAp.put(gold, predCountMAp);
				}
			}else{
				predCountMAp = new HashMap<String,Integer>();
				predCountMAp.put(pred, 1);
				countMAp.put(gold, predCountMAp);
			}
			
			
			
			
			
			
		}
		return countMAp;
		
	}
	
	
	public void getPercentage(Map<String,Map<String,Integer>> countMAp){
		
		Map<String,Integer> predCount ;
		
		for(String gold : countMAp.keySet()){
			
			System.out.println("For gold label "+gold+ " : Predicted classes in percentage ");
			 
			predCount= countMAp.get(gold);
			int total = 0;
			
			for(String pred : predCount.keySet()){
				total = total + predCount.get(pred);
			}
			

			for(String pred : predCount.keySet()){
				float percentage =  (predCount.get(pred)/(float)total)*100;
				System.out.println("Predicted Precentage for "+pred+" class : "+ percentage);
			}
			
		}
		
	}

}
