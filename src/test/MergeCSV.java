package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MergeCSV {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader br = null;
		String inputDir = "D:/Uni-MS/NLPTeamLab/DataTeamLAb/emotion";
		String outputFile = "D:/Uni-MS/NLPTeamLab/DataTeamLAb/gold-data.csv";
		File folder = new File(inputDir);
		File[] listOfFiles = folder.listFiles();
		String inputFile;
		FileWriter writer = new FileWriter(outputFile);
		int fileNumber = 0;
		String line;
		for (int i = 0; i < listOfFiles.length; i++) {
			fileNumber++;
			inputFile = listOfFiles[i].getAbsolutePath();
			br = new BufferedReader(new FileReader(inputFile));
			int lineNumber = 0;

			while ((line = br.readLine()) != null) {
				lineNumber++;
				writer.append(line);
				writer.append("\n");
				System.out.println("Processing lineNumber :" + lineNumber + ":::of file::" + fileNumber);

			}
		}
		writer.flush();
		writer.close();
	}

}
