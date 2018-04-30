package HomeWork3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import weka.core.Instances;

public class MainHW3 {

	public static BufferedReader readDataFile(String filename) {
		BufferedReader inputReader = null;

		try {
			inputReader = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException ex) {
			System.err.println("File not found: " + filename);
		}

		return inputReader;
	}

	public static Instances loadData(String fileName) throws IOException {
		BufferedReader datafile = readDataFile(fileName);
		Instances data = new Instances(datafile);
		data.setClassIndex(data.numAttributes() - 1);
		return data;
	}
	
	public static double[] bestHyperParamaters(Instances dataSet) throws Exception{
		
		double kValue = 0;
		double lpDist = Double.MAX_VALUE;
		double weightIndex = 0;
		double crossValidationError = Double.MAX_VALUE;
		double [] lpValues = {1, 2, 3, Double.POSITIVE_INFINITY};
		String[] weightingSchemes = {"Uniform", "Weighted"};
		
		for(int k = 1; k < 21; k++){
			for(int i = 0; i < lpValues.length; i++){
				double currentLpDistance = lpValues[i];
				for(int j = 0; j < weightingSchemes.length; j++){
					Knn knn = new Knn();
					knn.
				}
			}
		}
		
	}
	public static void main(String[] args) throws Exception {
        //TODO: complete the Main method
	}

}

// new
