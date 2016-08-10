package com.ims.data;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ims.vo.CorpusVO;
import com.ims.vo.TweetVO;

public class ReadData {

	/**
	 * Reads tweets from file and store in List<TweetVO> 
	 * @param corpus
	 * @return CorpusVO object with List<TweetVO> 
	 */
	public CorpusVO getDataValues(CorpusVO corpus) {

		List<TweetVO> listTweetDO = new ArrayList<TweetVO>();
		BufferedReader br;

		String line;
		try {
			Map<String,String> rawTweets = getRawTweet(ApplicationDetails.rawTweetFile);
			br = new BufferedReader(new FileReader(corpus.getFileName()));
			int i = 0;
			while ((line = br.readLine()) != null) {
				TweetVO tweetDO = new TweetVO();
				if (corpus.isTestData()) {
					
					String elem[] = line.split("	", 7);
					if (!(elem[4].trim().equalsIgnoreCase("en")))
						continue;
					tweetDO.setPredictedLabel(elem[0]);
					tweetDO.setGoldLabel(elem[1]);
					tweetDO.setTweetID(elem[2]);
					
					tweetDO.setUserName(elem[3]);
					tweetDO.setProfileName(elem[5]);
					tweetDO.setLanguage(elem[4]);
					tweetDO.setTweet(rawTweets.get(elem[2]));
					tweetDO.setPreprocessedTweet(elem[6]);

				} else if (corpus.isTrainData()) {
					String elem[] = line.split("	", 6);
					if (!(elem[3].trim().equalsIgnoreCase("en")))
						continue;
					tweetDO.setGoldLabel(elem[0]);
					tweetDO.setTweetID(elem[1]);
					tweetDO.setUserName(elem[2]);
					tweetDO.setProfileName(elem[4]);
					tweetDO.setLanguage(elem[3]);
					tweetDO.setTweet(rawTweets.get(elem[1]));
					tweetDO.setPreprocessedTweet(elem[5]);

				}
				listTweetDO.add(tweetDO);
				i++;

			}
			corpus.setListTweetVO(listTweetDO);
			System.out.println("total :" + i);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

		return corpus;

	}

	/**
	 * Read data from file
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public Map<String,String> getRawTweet(String fileName) throws IOException{
		
		BufferedReader br;
		String line;
		Map<String,String> map = new HashMap<String,String>();
		br = new BufferedReader(new FileReader(fileName));
		int i = 0;
		while ((line = br.readLine()) != null) {
			String elem[] = line.split(",", 3);
			if(map.containsKey(elem[1]))
				continue;
			map.put(elem[1], elem[2]);
		}
		return map;
		
	}

}
