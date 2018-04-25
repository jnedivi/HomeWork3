package HomeWork3;

import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Normalize;
import weka.filters.unsupervised.attribute.Standardize;

public class FeatureScaler {
	/**
	 * Returns a scaled version (using standarized normalization) of the given dataset.
	 * @param instances The original dataset.
	 * @return A scaled instances object.
	 */
	public Instances scaleData(Instances instances) throws Exception {
		
		Instances scaledData = new Instances(instances);
		Filter standardize = new Standardize();
		
		standardize.setInputFormat(scaledData);
		scaledData = Filter.useFilter(scaledData, standardize);
		
		return scaledData;
	}
}