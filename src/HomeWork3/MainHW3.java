
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
	
	public static void findBestHyperParamaters(Instances dataSet, String scaledStatus) throws Exception{
		
		double bestK = 0;
		double bestLpDist = Double.MAX_VALUE;
		double bestWeightIndex = 0;
		double bestCrossValidationError = Double.MAX_VALUE;
		double [] lpValues = {1, 2, 3, Double.POSITIVE_INFINITY};
		String[] weightingSchemes = {"Uniform", "Weighted"};
		// String[] lpMethod = {"Regular" , "Efficient"};
		int numberOfFolds = 10;
		
		Knn knn = new Knn();
		knn.setInstances(dataset);
		
		for(int k = 1; k < 21; k++){
			for(int p = 0; p < lpValues.length; i++){
				
				double currentLpDistance = lpValues[p];
				double 
				for(int j = 0; j < weightingSchemes.length; j++){
					
					knn.setK(k);
					knn.setWeightingScheme(weightingSchemes[j]);
					knn.setP(currentLpDistance);
					knn.set
				}
			}
		}
		
	}
	public static void main(String[] args) throws Exception {
        // first we run withe original data
		Instances trainingData = loadData("auto_price.txt");
		trainData.randomize(new Random());
		findBestHyperParamaters(trainingData, "original");
		
		// then we scale the data
		FeatureScaler featureScaler = new FeatureScaler();
		trainingData  = featureScaler.scaleData(trainingData);
		findBestHyperParamaters(trainingData, "scaled");
	
	}

}
//sb