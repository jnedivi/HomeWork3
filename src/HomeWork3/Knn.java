<<<<<<< HEAD
package HomeWork3;

import weka.classifiers.Classifier;
import weka.core.Capabilities;
import weka.core.Instance;
import weka.core.Instances;

class DistanceCalculator {
    /**
    * We leave it up to you wheter you want the distance method to get all relevant
    * parameters(lp, efficient, etc..) or have it has a class variables.
    */
    public double distance (Instance one, Instance two) {
        return 0.0;
    }

    /**
     * Returns the Lp distance between 2 instances.
     * @param one
     * @param two
     */
    private double lpDistance(Instance one, Instance two, double p) {
    	
		double sigma = 0;
		int dimension = one.numAttributes() -1;
		for (int i=1; i < dimension; i++) {
			sigma += Math.pow((one.value(i) - two.value(i)), p);
		}
		double lPDistance = Math.pow(sigma, (double) 1/p);
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
        return 0.0;
    }

    /**
     * Returns the Lp distance between 2 instances, while using an efficient distance check.
     * @param one
     * @param two
     * @return
     */
    private double efficientLpDisatnce(Instance one, Instance two) {
        return 0.0;
    }

    /**
     * Returns the Lp distance between 2 instances, while using an efficient distance check.
     * @param one
     * @param two
     * @return
     */
    private double efficientLInfinityDistance(Instance one, Instance two) {
        return 0.0;
    }
}

public class Knn implements Classifier {

    public enum DistanceCheck{Regular, Efficient}
    private Instances m_trainingInstances;

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
        return 0.0;
    }

    /**
     * Caclcualtes the average error on a give set of instances.
     * The average error is the average absolute error between the target value and the predicted
     * value across all insatnces.
     * @param insatnces
     * @return
     */
    public double calcAvgError (Instances insatnces){
        return 0.0;
    }

    /**
     * Calculates the cross validation error, the average error on all folds.
     * @param insances Insances used for the cross validation
     * @param num_of_folds The number of folds to use.
     * @return The cross validation error.
     */
    public double crossValidationError(Instances instances, int num_of_folds){
        return 0.0;
    }


    /**
     * Finds the k nearest neighbors.
     * @param instance
     */
    public Instances findNearestNeighbors(Instance instance) {
    	Instances instances = new Instances(m_trainingInstances);
    	
    	return instances;

    }

    /**
     * Calculates the average value of the given elements in the collection.
     * @param
     * @return
     */
    public double getAverageValue (/* Collection of your choice */) {
        return 0.0;
    }

    /**
     * Calculates the weighted average of the target values of all the elements in the collection
     * with respect to their distance from a specific instance.
     * @return
     */
    public double getWeightedAverageValue(/* Collection of your choice */) {
        return 0.0;
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
}
=======

package HomeWork3;

import weka.classifiers.Classifier;
import weka.core.Capabilities;
import weka.core.Instance;
import weka.core.Instances;

class DistanceCalculator {
    /**
    * We leave it up to you wheter you want the distance method to get all relevant
    * parameters(lp, efficient, etc..) or have it has a class variables.
    */
    public double distance (Instance one, Instance two, double p) {
    	if (Double.isInfinite(p)) {
    		return lInfinityDistance(one,two);
    	} else {
    		return lpDistance(one, two, p);
    	}
    	
    }

    /**
     * Returns the Lp distance between 2 instances.
     * @param one
     * @param two
     */
    private double lpDistance(Instance one, Instance two, double p) {
    	
		double sigma = 0;
		int dimension = one.numAttributes() -1;
		for (int i=1; i < dimension; i++) {
			sigma += Math.pow((one.value(i) - two.value(i)), p);
		}
		double lPDistance = Math.pow(sigma, (double) 1/p);
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
		int dimension = one.numAttributes() -1;
        for (int i=1; i<dimension; i++) {
        	double tmp = Math.abs(one.value(i)-two.value(i));
        	if (max<tmp)
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
    private double efficientLpDisatnce(Instance one, Instance two) {
        return 0.0;
    }

    /**
     * Returns the Lp distance between 2 instances, while using an efficient distance check.
     * @param one
     * @param two
     * @return
     */
    private double efficientLInfinityDistance(Instance one, Instance two) {
        return 0.0;
    }
}

public class Knn implements Classifier {

    public enum DistanceCheck{Regular, Efficient}
    public enum WeightingScheme{Uniform, Weighted}
    private Instances m_trainingInstances;
    private double m_k;
    private double m_p;
    private WeightingScheme m_WeightingScheme;
    
    public void setInstances(Instances dataSet){
    	m_trainingInstances = dataSet;
    }
    
    public void setK(double i_k){
    	m_k = i_k;
    }
    
    public void setP(double i_p){
    	m_p = i_p;
    }
    
    public void setWeightingScheme(WeightingScheme i_WeightingScheme){
    	m_WeightingScheme = i_WeightingScheme;
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
    	
    	if(m_WeightingScheme.equals(WeightingScheme.Uniform)){
    		return getAverageValue(m_trainingInstances);
    	}else{
    		return getWeightedAverageValue(m_trainingInstances);
    	}
    }

    /**
     * Caclcualtes the average error on a give set of instances.
     * The average error is the average absolute error between the target value and the predicted
     * value across all insatnces.
     * @param insatnces
     * @return
     */
    public double calcAvgError (Instances insatnces){
        return 0.0;
    }

    /**
     * Calculates the cross validation error, the average error on all folds.
     * @param insances Insances used for the cross validation
     * @param num_of_folds The number of folds to use.
     * @return The cross validation error.
     */
    public double crossValidationError(Instances instances, int num_of_folds){
        return 0.0;
    }


    /**
     * Finds the k nearest neighbors.
     * @param instance
     */
    public Instances findNearestNeighbors(Instance instance) {
    	Instances instances = new Instances(m_trainingInstances);
    	
    	return instances;

    }

    /**
     * Cacluates the average value of the given elements in the collection.
     * @param
     * @return
     */
    public double getAverageValue (Instances dataSet) {
        return 0.0;
    }

    /**
     * Calculates the weighted average of the target values of all the elements in the collection
     * with respect to their distance from a specific instance.
     * @return
     */
    public double getWeightedAverageValue(Instances dataSet) {
        return 0.0;
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
}

>>>>>>> 5b55383d5be50144140451a9417545da9a4b7d78
