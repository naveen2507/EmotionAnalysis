package test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ClassCorrelation {

	
	Map<String,Integer> countMap = new HashMap<String,Integer>();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Map<String,Integer> countMap = new HashMap<String,Integer>();
		Map<String,Map<String,Integer>> otherClassMap = new HashMap<String,Map<String,Integer>>();
		
		
		
	}
	
	
	public Map<String,Double> getCorrelationMap(String label) throws IOException{
		Map<String,Double> corMap = new HashMap<String,Double>();
	
		if(countMap.containsKey(label)){
			
		}
		BufferedReader br;
		String line;
		br = new BufferedReader(new FileReader("C:/Users/user/git/EmotionalAnalysisRepo1/Data/emoticonsWithPolarity2.txt"));
		while((line = br.readLine()) != null){
			 
			
		}

		
		return corMap;

	
	}
	

}
