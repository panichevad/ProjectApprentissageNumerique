package dnn;

import java.util.ArrayList;

/**
 * Created by ZLO on 08.01.2017.
 */
public class Layer {
    //String transferFunction; // la fonction de transition
	
    int n; // nb de neurones dans la couche
    double[] output;

    protected ArrayList<Neurone> neurone ;

    Layer(int _n){
    	this.n = _n;
    	this.neurone = new ArrayList<Neurone>(n);
    	this.output = new double[n];
    }

	public ArrayList<Neurone> getNeurone() {
		return neurone;
	}
	
	public void output_value(){
		for(int i = 0 ; i < n ; i++){
			this.output[i] = this.neurone.get(i).getOuput();
		}
	}

	public double[] getOutput() {
		return output;
	}
	
	
	
    
    

   
   




}
