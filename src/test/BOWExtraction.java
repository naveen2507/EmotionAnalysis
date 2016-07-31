package test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.ims.FeatureEngineering.Helper;

public class BOWExtraction {

	public static void main(String args[]) throws IOException{
		
		Map<String,Integer> uniGram = new HashMap<String,Integer>();
		Map<String,Integer> biGram = new HashMap<String,Integer>();
		Helper objHelper = Helper.getInstance();
		BufferedReader br;
		
		String line;
		br = new BufferedReader(new FileReader("D:\\Uni-MS\\NLPTeamLabWorkSpace\\Data\\Balanced-DataSet\\sample-data-balanced-preprocessed-train.csv"));
		while ((line = br.readLine()) != null) {
			
			String elem[] = line.split("	",6);
			String tweet = elem[5];
			objHelper.getNgrams(tweet.toLowerCase().trim(), 1, uniGram);
			objHelper.getNgrams(tweet.toLowerCase().trim(), 2, biGram);
	 	}
		
		FileWriter writerUni = new FileWriter("D:\\Uni-MS\\NLPTeamLabWorkSpace\\Data\\Balanced-DataSet\\Unigrams.csv");
		FileWriter writerBi = new FileWriter("D:\\Uni-MS\\NLPTeamLabWorkSpace\\Data\\Balanced-DataSet\\Bigrams.csv");
		
		uniGram = objHelper.sortByComparator(uniGram, false);
		biGram = objHelper.sortByComparator(biGram, false);
		
		for(String word : uniGram.keySet()){
			writerUni.append(word+","+uniGram.get(word));
			writerUni.append("\n");
		}
		for(String word : biGram.keySet()){
			writerBi.append(word+","+biGram.get(word));
			writerBi.append("\n");
		}
		
		writerUni.close();
		writerBi.close();
	}
	
	
}
