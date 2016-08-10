package test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RawDataByID {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		RawDataByID obj = new RawDataByID();
		
		BufferedReader br;
		
		String line;
		List<String> tweetIdList = obj.getTweetList();
		FileWriter writer = new FileWriter("D:\\Uni-MS\\NLPTeamLabWorkSpace\\EmotionAnalysis\\Data\\56K-Data\\sample-data-56K.csv");
		br = new BufferedReader(new FileReader("D:\\Uni-MS\\NLPTeamLabWorkSpace\\EmotionAnalysis\\Data\\gold-data.csv"));
		System.out.println(tweetIdList.size());
		int i=0;
		while ((line = br.readLine()) != null) {
			String elem[] = line.split("	",9);
		   String tweetId = elem[3];
		   i++;
		   System.out.println(i);
		   if(tweetIdList.contains(tweetId)){
			   writer.append(elem[0]);
			   writer.append(",");
			   writer.append(elem[3]);
			   writer.append(",");
			   writer.append(elem[8]);
			   writer.append("\n");
		   }
		}
		writer.flush();
		writer.close();
	}
	
	public List<String> getTweetList() throws IOException{
		BufferedReader br;
		
		String line;
		
		List<String> tweetIdList = new ArrayList<String>();
		br = new BufferedReader(new FileReader("D:\\Uni-MS\\NLPTeamLabWorkSpace\\EmotionAnalysis\\Data\\56K-Data\\sample-data-56K-preprocessed.csv"));
		while ((line = br.readLine()) != null) {
			
			String elem[] = line.split("	");
			String tweetId = elem[1];
			tweetIdList.add(tweetId);
		}
		br.close();
		
		return tweetIdList;
	}

}
