
import java.util.Random;

import weka.classifiers.Classifier;

import weka.core.Capabilities;

import weka.core.Instance;

import weka.core.Instances;

class DistanceCalculator {

	/**
	 * We leave it up to you wheter you want the distance method to get all relevant
	 * parameters(lp, efficient, etc..) or have it has a class variables.
	 */

	public double distance(Instance one, Instance two, double p, knn.DistanceCheck distanceCheck) {
		if(distanceCheck == knn.DistanceCheck.Regular){
			if (Double.isInfinite(p)) {

				return lInfinityDistance(one, two);
			} else {
				return lpDistance(one, two, p);

			}
		}

		if(distanceCheck == knn.DistanceCheck.Efficient){
			if (Double.isInfinite(p)) {

				return efficientLInfinityDistance(one, two);
			} else {
				return efficientLpDistance(one, two, p);

			}
		}
	}



	/**
	 * Returns the Lp distance between 2 instances.
	 * @param one
	 * @param two
	 */

	private double lpDistance(Instance one, Instance two, double p) {

		double sigma = 0;

		int dimension = one.numAttributes() - 1;

		for (int i = 1; i < dimension; i++) {

			sigma += Math.abs(Math.pow((one.value(i) - two.value(i)), p));
		}

		double lPDistance = Math.pow(sigma, (double) 1 / p);

		//System.out.println(lPDistance);

		return lPDistance;

	}



	/**
	 * Returns the L infinity distance between 2 instances.
	 * @param one
	 * @param two
	 * @return
	 */

	private double lInfinityDistance(Instance one, Instance two) {

		double max = 0;

		int dimension = one.numAttributes() - 1;

		for (int i = 1; i < dimension; i++) {

			double tmp = Math.abs(one.value(i) - two.value(i));

			if (max < tmp)
				max = tmp;
		}

		return max;
	}



	/**
	 * Returns the Lp distance between 2 instances, while using an efficient distance check.
	 * @param one
	 * @param two
	 * @return
	 */

	private double efficientLpDistance(Instance one, Instance two, double p) {

		double threshold = Double.MAX_VALUE;
		double sigma = 0;
		int dimension = one.numAttributes() - 1;

		for (int i = 0; i < dimension; i++) {
			sigma += Math.pow(Math.abs(one.value(i) - two.value(i)), p);
			if(sigma > threshold){
				break;
			}
		}
		sigma = Math.pow(sigma, (1.0 / p));
		return sigma;
	}



	/**
	 * Returns the Lp distance between 2 instances, while using an efficient distance check.
	 * @param one
	 * @param two
	 * @return
	 */

	private double efficientLInfinityDistance(Instance one, Instance two) {

		double threshold = Double.MAX_VALUE;
		double sigma = 0;
		int dimension = one.numAttributes() - 1;


		for (int i = 0; i < dimension; i++) {
			double diff = Math.abs((one.value(i) - two.value(i)));
			if (diff > 0)
				sigma = diff;
			if(sigma > threshold) {
				break;
			}
		} 

		return sigma;

	}

}



public class Knn implements Classifier {

	public enum DistanceCheck {
		Regular,
		Efficient
	}

	public enum WeightingScheme {
		Uniform,
		Weighted
	}

	private Instances m_trainingInstances;

	private double m_k;

	private double m_p;

	private WeightingScheme m_WeightingScheme;
	
	private DistanceCheck m_DistanceCheck;



	public void setInstances(Instances dataSet) {

		m_trainingInstances = dataSet;

	}



	public void setK(double i_k) {

		m_k = i_k;

	}



	public void setP(double i_p) {

		m_p = i_p;

	}



	public void setWeightingScheme(WeightingScheme i_WeightingScheme) {

		m_WeightingScheme = i_WeightingScheme;

	}
	
	public void setDistanceCheck(DistanceCheck i_DistanceCheck){
		
		m_DistanceCheck = i_DistanceCheck;
		
	}


	@Override

	/**
	 * Build the knn classifier. In our case, simply stores the given instances for
	 * later use in the prediction.
	 * @param instances
	 */

	public void buildClassifier(Instances instances) throws Exception {



	}



	/**
	 * Returns the knn prediction on the given instance.
	 * @param instance
	 * @return The instance predicted value.
	 */

	public double regressionPrediction(Instance instance) {

		if (m_WeightingScheme.equals(WeightingScheme.Uniform)) {

			return getAverageValue(m_trainingInstances);

		} else {

			return getWeightedAverageValue(m_trainingInstances, instance);

		}

	}



	/**
	 * Caclcualtes the average error on a give set of instances.
	 * The average error is the average absolute error between the target value and the predicted
	 * value across all insatnces.
	 * @param insatnces
	 * @return
	 */

	public double calcAvgError(Instances instances) {
		double totalAmountOfMistakes = 0;

		for (int i = 0; i < instances.numInstances(); i++) {
			if(this.classifyInstance(instances.get(i)) != instances.get(i).classValue()){
				totalAmountOfMistakes++;
			}
		}

		return totalAmountOfMistakes / instances.numInstances();
	}



	/**
	 * Calculates the cross validation error, the average error on all folds.
	 * @param insances Insances used for the cross validation
	 * @param num_of_folds The number of folds to use.
	 * @return The cross validation error.
	 */

	public double crossValidationError(Instances instances, int num_of_folds) {

		Random random = new Random();
		instances.randomize(random);
		double sumOfErrors = 0;
		Instances[] folds = GetFolds(instances, num_of_folds); 


		for(int i = 0; i < folds.length; i++){

			Instances validationSet = folds[i];
			Instances trainingSet = new Instances(instances , 0 ,0);

			for (int j = 0; j < folds.length; j++) {
				if( j != i){
					trainingSet.addAll(folds[j]);
				} 
			}


			m_trainingInstances = trainingSet;
			double averageErrorOfCurrentFolds = this.calcAvgError(validationSet);

			sumOfErrors += averageErrorOfCurrentFolds;

		}

		sumOfErrors /= num_of_folds;

		return sumOfErrors;

	}

	private Instances[] GetFolds(Instances i_Data , int i_NumOfFolds){
		
		Instances[] folds = new Instances[i_NumOfFolds];
		//Instances empty = new Instances(i_Data , 0 ,0);
		for (int i = 0; i < folds.length; i++) {
			folds[i] =  new Instances(i_Data , 0 ,0);
		}
		for (int i = 0; i < i_Data.numInstances(); i++) {
			folds[ i % i_NumOfFolds ].add(i_Data.instance(i));
		}
		return folds;
	}



	/**
	 * Finds the k nearest neighbors.
	 * @param instance
	 */

	public Instances findNearestNeighbors(Instance instance) {

		Instances instances = new Instances(m_trainingInstances);

		Instances allInstances = new Instances(m_trainingInstances);
		
		DistanceCalculator d_c = new DistanceCalculator();

		for (int i = 0; i < allInstances.size(); i++) {

			allInstances.add(i, this.m_trainingInstances.get(i));

		}

		double[] distances = new double[m_trainingInstances.numInstances()];

		if( m_k > m_trainingInstances.numInstances() ){
			return allInstances; 
		}

		for (int i = 0; i < m_trainingInstances.numInstances(); i++) {
			double currentDistance = d_c.distance(instance , m_trainingInstances.get(i), m_p);
			distances[i] = currentDistance;
		}

		sortArray(distances , allInstances );

		for (int i = 0; i < instances.size(); i++) {
			instances.add(i, allInstances.get(i));
		}

		return instances;

	}



	/**
	 * Cacluates the average value of the given elements in the collection.
	 * @param
	 * @return
	 */

	public double getAverageValue(Instances dataSet) {

		return dataSet.meanOrMode(dataSet.classAttribute());

	}



	/**
	 * Calculates the weighted average of the target values of all the elements in the collection
	 * with respect to their distance from a specific instance.
	 * @return
	 */

	public double getWeightedAverageValue(Instances dataSet, Instance instance) {

		double avg = 0;
		DistanceCalculator d_C = new DistanceCalculator();

		double distance = 0;
		double sumOfDistances = 0;
		double sumOfWeights = 0;
		double w_i = 0;
		
		DistanceCalculator d_c = new DistanceCalculator();

		for (int i = 0; i < dataSet.numInstances(); i++) {
			Instance neighbor = dataSet.get(i);

			distance = d_c.distance(neighbor, instance, m_p);
			if (distance == 0) {
				return neighbor.classValue();
			}
			w_i = 1.0 / Math.pow(distance, 2);
			if (w_i > 0) {
				sumOfDistances += w_i;
				sumOfWeights += w_i * neighbor.classValue();
			}
		}
		avg = sumOfWeights / sumOfDistances;
		return avg;
	}





	@Override

	public double[] distributionForInstance(Instance arg0) throws Exception {

		// TODO Auto-generated method stub - You can ignore.

		return null;

	}



	@Override

	public Capabilities getCapabilities() {

		// TODO Auto-generated method stub - You can ignore.

		return null;

	}



	@Override

	public double classifyInstance(Instance instance) {

		// TODO Auto-generated method stub - You can ignore.

		return 0.0;

	}

	private void sortArray(double[] dist , Instances instances) {

		double temp = 0;
		for (int i = 0; i < dist.length; i++) {
			for (int j = 1; j < dist.length - i; j++) {
				if(dist[j -1] > dist[j]){
					// swap distance data
					temp = dist[j - 1];
					dist[j - 1] = dist[j];
					dist[j] = temp;	
					//swap instances
					Instance tempInstance = instances.get(j - 1);
					instances.add(j - 1, instances.get(j));
					instances.add(j, tempInstance);
				}	
			}
		}
	}
}
