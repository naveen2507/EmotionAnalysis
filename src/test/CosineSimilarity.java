package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import java.util.StringTokenizer;


public class CosineSimilarity {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CosineSimilarity c =new CosineSimilarity();
		c.getCategories();
	
	}
	public void getCategories(){
		
		
		BufferedReader br = null;
		String inputFile = "E:\\INGSolution\\Sub-Classification\\sample-key.txt";
		String outputFile = "E:\\INGSolution\\Sub-Classification\\cleanTweetsMerged.csv";
		String line = "";
		Map<String,List<String>> taxanomyCategory = getTaxanomy("E:\\INGSolution\\Separate-taxanomy-for-Cluster");
		try{
		FileWriter writer = new FileWriter(outputFile);
		br = new BufferedReader(new FileReader(inputFile));
	
		int clusterNumber =0;
		while ((line = br.readLine()) != null) {
			System.out.println("cluster::"+clusterNumber);
			StringBuilder out = new StringBuilder();
			out.append(clusterNumber);
			Map<String,Double> mapCategoryCosine = new HashMap<String,Double>();	
			String element[]=line.split("\t");
			String topicWords = element[2];
			for(Map.Entry<String, List<String>> entry: taxanomyCategory.entrySet()){
				
				String category = entry.getKey();
				System.out.println("Category is:"+category);
				List<String> taxanomyList = entry.getValue();
				List<String> uniqueList = getUniqueList(taxanomyList,topicWords);
				Double cosineValue = getCosineSimilarity(uniqueList, taxanomyList, topicWords);
				out.append(",");
				out.append(cosineValue);
				mapCategoryCosine.put(category, cosineValue);
				
			}
			
			out.append("\n");
			writer.append(out);
			clusterNumber++;
		}
		writer.flush();
		writer.close();
		
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public static Map<String,List<String>> getTaxanomy(String inputDir){
		
		Map<String,List<String>> taxanomy = new HashMap<String,List<String>>();
		BufferedReader br = null;
		File folder = new File(inputDir);
		File[] listOfFiles = folder.listFiles();
		String inputFile;
		String category;
		List<String> listTaxanomyWords;
		int fileNumber=0;
		String line;
		  for(int i=0;i<listOfFiles.length;i++){
			  fileNumber++; 
			  listTaxanomyWords=new ArrayList<String>();
			  inputFile = listOfFiles[i].getAbsolutePath();
			  category=listOfFiles[i].getName();
			  try{
			  br = new BufferedReader(new FileReader(inputFile));
				while ((line = br.readLine()) != null) {
					listTaxanomyWords.add(line.trim().toLowerCase());
					
				}
				
				taxanomy.put(category, listTaxanomyWords);
				System.out.println("File Number is:"+fileNumber+"  ::  "+category);
			  }
			  catch(Exception e){
				  e.printStackTrace();
			  }
		  }
		
		
		
		return taxanomy;
	}
	
	
	public List<String> getUniqueList(List<String> taxanomy,String topicList){
		
		List<String> wordList = new ArrayList<String>();
		wordList.addAll(taxanomy);
		
		StringTokenizer topicElements = new StringTokenizer(topicList," ");
		
		while(topicElements.hasMoreTokens()){
			
			String word = topicElements.nextToken().trim();
			if(wordList.contains(word.toLowerCase())){
				continue;
			}
			else{
				wordList.add(word);
			}
		}
		
		
		return wordList;
		
	}

	
	public Double getCosineSimilarity(List<String> wordList,List<String> taxanomy,String topicList ){
		
		List<Integer> similarityTaxanomy = new ArrayList<Integer>();
		List<Integer> similarityTopicList = new ArrayList<Integer>();
		
		Iterator<String> itrWordList = wordList.iterator();
		
		
		while(itrWordList.hasNext()){
			
			String word = itrWordList.next().trim();
			if(taxanomy.contains(word.toLowerCase())){
				similarityTaxanomy.add(1);
			}
			else{
				similarityTaxanomy.add(0);
			}
			if(topicList.contains(word.toLowerCase())){
				similarityTopicList.add(1);
			}
			else{
				similarityTopicList.add(0);
			}
		}
		
		int lstSize = similarityTaxanomy.size();
		double numerator = 0;
		double denListTaxanomySqr=0;
		double denListTopicSqr=0;
		
		for(int i=0;i<lstSize;i++){
			
			int a = similarityTaxanomy.get(i);
			int b = similarityTopicList.get(i);
			numerator = numerator + (a*b);
			denListTaxanomySqr=denListTaxanomySqr+Math.pow(a,2);
			denListTopicSqr =denListTopicSqr+Math.pow(b,2);
		}
		double cosineSimilarityScore = numerator/((Math.sqrt(denListTaxanomySqr))*(Math.sqrt(denListTopicSqr)));
		return cosineSimilarityScore;
		
	}
}
