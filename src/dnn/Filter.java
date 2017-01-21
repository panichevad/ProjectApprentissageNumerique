package dnn;

/**
 * Created by ZLO on 13.01.2017.
 */
public class Filter {
	
	public Filter(){
		
	}
	
	/**
	 * On fait la moyenne des longueurs 1x2
	 * @param value
	 * @return la moyenne des longueurs
	 */
	public double firstfiltre(double[] value){
		double result = (value[0]+value[2])/2;
		return result;
	}
	
	/**
	 * On fait la moyenne des largeurs 1x2
	 * @param value
	 * @return la moyenne des largeurs
	 */
	public double secondfiltre(double[] value){
		double result = (value[1]+value[3])/2;
		return result;
	}
	
	

}
