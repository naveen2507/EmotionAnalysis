package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmotionDict{
	
	public static void main(String[] args) {
		BufferedReader br;
		String line;
		Map<String, String> smileyMap = new HashMap<String, String>(); 
		try{
				br = new BufferedReader(new FileReader("C:/Users/user/git/EmotionalAnalysisRepo1/Data/emoticonsWithPolarity2.txt"));
				
				while((line = br.readLine()) != null){
					String elem[] = line.split("	");
					/*byte[] utf8 = elem[0].getBytes();
				    elem[0] = new String(utf8, "UTF-8");*/
					String[] smileyList = elem[0].split("\\s");
					
					for (String smiley : smileyList) {
						smileyMap.put(smiley, elem[1]);
						System.out.println(smiley);
					}
					
					/*
					if(!(tempWord.equalsIgnoreCase(elem[0]))){
						System.out.println("Catgory : "+tempWord);
						for(String emotion: emotions)
							System.out.println(emotion);
						emotionMap.put(tempWord, emotions);
						tempWord = elem[0];
						emotions = new ArrayList<String>();
					}
					if(Integer.parseInt(elem[2]) == 0){
						//emotions.add(elem[1]);
						continue;
					}
					
					emotions.add(elem[1]);	
				}
				
				emotionMap.put(tempWord, emotions);
*/			}			
		}catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
}
