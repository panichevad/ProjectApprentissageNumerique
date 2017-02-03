package dnn;

/**
 * Created by ZLO on 13.01.2017.
 */
public class Filter {
	
	public Filter(){
		
	}
	
	
	/**
	 * On fait la moyenne des valeurs 1x2
	 * @param value
	 * @return la moyenne des valeurs
	 */
	public double[] applicationFiltre(double[] value){
		double[] result  = new double[value.length];
		for(int i = 0 ; i<value.length ; i++){
			if(i==value.length-1){
				result[i] = (value[i]+value[0])/2;
			}
			else{
				result[i] = (value[i]+value[i+1])/2;
			}
		}
		return result;
	}
	
	

}
