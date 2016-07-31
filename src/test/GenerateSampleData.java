package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GenerateSampleData {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		
	
		String names[] = {"sad", "anger", "fear", "happy", "disgust", "surprise"};
		List<String> listTweet = new ArrayList<String>();
		BufferedReader br;
		Map<String,Integer> countMap  = new HashMap<String,Integer>();
		String line;
		try {
			br = new BufferedReader(new FileReader("D:\\Uni-MS\\NLPTeamLab\\DataTeamLAb\\gold-data.csv"));
			int i=0;
			while ((line = br.readLine()) != null) {
				String elem[] = line.split("	",9);
			    try{
			    	if(!(elem[6].trim().equalsIgnoreCase("en")))
				    	continue;
			    	if(elem[8].trim().length()<15)
			    		continue;
			    	String category = elem[0];
			    	if(countMap.containsKey(category)){
			    		countMap.put(category, countMap.get(category)+1);
			    	}else{
			    		countMap.put(category, 1);
			    	}
				    listTweet.add(line);
					i++;
					System.out.println(i);
			    } catch (Exception e) {
					e.printStackTrace();
					continue;
				}
				

			}

			System.out.println("total :"+i);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		
			String tab = "	";
			
		FileWriter writer = new FileWriter("D:\\Uni-MS\\NLPTeamLab\\DataTeamLAb\\sample-data-balanced.csv");
		Random randomGenerator = new Random();
	    for (int idx = 1; idx <= 500000; ++idx){
	      int randomInt = randomGenerator.nextInt(496254);
	      //String predRandom = names[randomGenerator.nextInt(6)];
	      String tweet = listTweet.get(randomInt);
	      
	      //writer.append(predRandom);
	      //writer.append(tab);
	      writer.append(tweet);
	      writer.append("\n");
	      
	      
	    }
	   
	   
	    writer.flush();
	    writer.close();
	

	}

}
