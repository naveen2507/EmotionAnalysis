package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ims.data.Preprocessing;
import com.ims.vo.TweetVO;

public class SampleDataProcess {

	public static void main(String[] args) throws IOException {
		SampleDataProcess obj = new SampleDataProcess();
		List<TweetVO> listTweetDo = obj.readDataFunction("D:\\Uni-MS\\NLPTeamLab\\DataTeamLAb\\sample-data-500K.csv");
		Preprocessing obj1 = new Preprocessing();
		List<TweetVO> preProtweet = obj1.getPreprocessedtweet(listTweetDo);
		List<TweetVO> filteredTweet = obj1.getFilteredtweet(preProtweet,"D:\\Uni-MS\\NLPTeamLab\\DataTeamLAb\\sample-data-balanced-preprocessed.csv");
		//obj1.writeFile(filteredTweet, "D:\\Uni-MS\\NLPTeamLab\\DataTeamLAb\\sample-data-500K-preprocessed.csv");
		
	}

	public List<TweetVO> readDataFunction(String fileNAme){
	
		/*
		 * 
		surprise
		#surprise
		2016-03-24 15:15:15 +0100
		713006264251523072
		
		@ErikaRriveraa
		es
		Erika Rivera
		#bohochic #jasperring #etsyboho #etsy #fashion #etsyseller #bijou #style #jewelry #silver #naturalstones #surprise … https://t.co/yfSARK295C

		 * 
		 * 
		 */
		String names[] = {"sad", "anger", "fear", "happy", "disgust", "surprise"};
		List<TweetVO> listTweetDO = new ArrayList<TweetVO>();
		BufferedReader br;
		Map<String,Integer> map = new HashMap<String,Integer>();
		for(String label : names){
			map.put(label, 0);
		}

		String line;
		try {
			br = new BufferedReader(new FileReader(fileNAme));
			int i=0;
			while ((line = br.readLine()) != null) {
				TweetVO tweetDO = new TweetVO();
				String elem[] = line.split("	",9);
				
//				System.out.println(elem[0]);
//				System.out.println(elem[1]);
//				System.out.println(elem[2]);
//				System.out.println(elem[3]);
//				System.out.println(elem[4]);
//				System.out.println(elem[5]);
//				System.out.println(elem[6]);
//				System.out.println(elem[7]);
//				System.out.println(elem[8]);
//				
				
				if(!(elem[6].trim().equalsIgnoreCase("en")))
					continue;
				tweetDO.setGoldLabel(elem[0]);
				if(!(tweetDO.getGoldLabel().equalsIgnoreCase("disgust"))){
					if(map.get(tweetDO.getGoldLabel())>10000)
						continue;
				}
				map.put(tweetDO.getGoldLabel(),map.get(tweetDO.getGoldLabel())+1);
				
				tweetDO.setTweetID(elem[3]);
				tweetDO.setUserName(elem[5]);
				tweetDO.setProfileName(elem[7]);
				tweetDO.setLanguage(elem[6]);
				tweetDO.setTweet(elem[8]);
				listTweetDO.add(tweetDO);
				i++;
				//break;
			}

			for(String label:map.keySet()){
				System.out.println(label+" : "+map.get(label));
			}
				
			System.out.println("total :"+i);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		return listTweetDO;


	}
	
}
