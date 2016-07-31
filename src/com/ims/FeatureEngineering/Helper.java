package com.ims.FeatureEngineering;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.ims.data.ApplicationDetails;

public class Helper {

	private static Helper objHelper;

	public static synchronized Helper getInstance() {
		if (objHelper == null)
			objHelper = new Helper();
		return objHelper;
	}

	public List<String> getStopList() {

		String filePath = ApplicationDetails.stop_word_file;
		List<String> stopWordLst = new ArrayList<String>();
		BufferedReader br;

		String line;
		try {
			br = new BufferedReader(new FileReader(filePath));
			while ((line = br.readLine()) != null) {
				stopWordLst.add(line);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

		return stopWordLst;

	}
	
	public Map<String, Integer> getNgramDictionary(String path) throws IOException {

		Map<String, Integer> ngramMap = new HashMap<String, Integer>();
		BufferedReader br;

		String line;
		br = new BufferedReader(new FileReader(path));
		while ((line = br.readLine()) != null) {
			String elem[] = line.split(",");
			
			if (elem.length == 2) {
				if (elem[0].length() <= 2)
					continue;
				if(Integer.parseInt(elem[1]) <= 2)
					continue;
				ngramMap.put(elem[0], Integer.parseInt(elem[1]));
			}
		}
		br.close();
		return ngramMap;
	}



	public Map<String, Integer> getNgrams(String text, int N, Map<String, Integer> nGramMp) {

		String[] parts = text.split(" ");
		// String[] result = new String[parts.length - n + 1];
		for (int i = 0; i < parts.length - N + 1; i++) {
			StringBuilder sb = new StringBuilder();
			for (int k = 0; k < N; k++) {
				if (k > 0)
					sb.append(' ');
				sb.append(parts[i + k].trim());
			}
			String ngram = sb.toString().trim();
			if (nGramMp.containsKey(ngram)) {
				int value = nGramMp.get(ngram);
				nGramMp.put(ngram.toString(), value + 1);

			} else {
				nGramMp.put(ngram, 1);
			}
		}
		return nGramMp;
	}

	public static Map<String, Integer> sortByComparator(Map<String, Integer> unsortMap, final boolean order) {

		List<Entry<String, Integer>> list = new LinkedList<Entry<String, Integer>>(unsortMap.entrySet());

		// Sorting the list based on values
		Collections.sort(list, new Comparator<Entry<String, Integer>>() {
			public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
				if (order) {
					return o1.getValue().compareTo(o2.getValue());
				} else {
					return o2.getValue().compareTo(o1.getValue());

				}
			}
		});

		// Maintaining insertion order with the help of LinkedList
		Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
		for (Entry<String, Integer> entry : list) {
			sortedMap.put(entry.getKey(), entry.getValue());
		}

		return sortedMap;
	}

	public Map<String, List<String>> getEmotionDict(String filename) {
		BufferedReader br;
		String line;
		Map<String, List<String>> emotionMap = new HashMap<String, List<String>>();
		try {
			br = new BufferedReader(new FileReader(filename));
			String tempWord = "";
			List<String> emotions = null;
			while ((line = br.readLine()) != null) {
				String elem[] = line.split("	");
				if (Integer.parseInt(elem[2]) == 0) {
					continue;
				}
				if (tempWord.length() == 0) {
					tempWord = elem[0];
					emotions = new ArrayList<String>();
				}
				if (!(tempWord.equalsIgnoreCase(elem[0]))) {
					emotionMap.put(tempWord, emotions);
					tempWord = elem[0];
					emotions = new ArrayList<String>();
				}

				emotions.add(elem[1]);
			}

			emotionMap.put(tempWord, emotions);

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

		return emotionMap;

	}

	public Map<String, String> getSmileyDict(String filename) {
		BufferedReader br;
		String line;
		Map<String, String> smileyMap = new HashMap<String, String>();
		try {
			br = new BufferedReader(
					new FileReader(ApplicationDetails.smileyDictionary));

			while ((line = br.readLine()) != null) {
				String elem[] = line.split("	");
				String[] smileyList = elem[0].split("\\s");

				for (String smiley : smileyList) {
					smileyMap.put(smiley, elem[1]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

		return smileyMap;
	}

	public List<String> getNegationDict(String filename) {
		BufferedReader br;
		String line;
		List<String> negationDictionary = new ArrayList<String>();
		try {
			br = new BufferedReader(new FileReader(filename));

			while ((line = br.readLine()) != null) {
				negationDictionary.add(line.trim().toLowerCase());
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		return negationDictionary;
	}
	
	
	/*public List<String> getDiscourseDict(String filename) {
		BufferedReader br;
		String line;
		List<String> negationDictionary = new ArrayList<String>();
		try {
			br = new BufferedReader(new FileReader(ApplicationDetails.negationDictionary));

			while ((line = br.readLine()) != null) {
				negationDictionary.add(line.trim().toLowerCase());
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		return negationDictionary;
	}*/
	

}
