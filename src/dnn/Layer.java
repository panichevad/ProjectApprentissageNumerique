package dnn;

import java.util.ArrayList;

/**
 * Created by ZLO on 08.01.2017.
 */
public class Layer {
    //String transferFunction; // la fonction de transition
	
    int n; // nb de neurones dans la couche

    protected ArrayList<Neurone> neurone ;

    Layer(int _n){
    	this.n = _n;
    	this.neurone = new ArrayList<Neurone>(n);
    }

	public ArrayList<Neurone> getNeurone() {
		return neurone;
	}
	
    
    

   
   




}
