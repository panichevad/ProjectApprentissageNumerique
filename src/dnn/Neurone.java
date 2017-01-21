package dnn;

public class Neurone {
	
	protected double ouput;
	protected double[] input; // valeur d'entree
	protected double[] weight; // poids associés à chaque input
	protected int nb_neurone_coucprec;
	
	public Neurone(int nb_neurone_coucprec){
		this.nb_neurone_coucprec = nb_neurone_coucprec;
		this.input = new double[nb_neurone_coucprec];
		this.weight = new double[nb_neurone_coucprec];
	}
	
	 public double sum(){
	        double sum = 0;
	        for (int i = 0; i < this.nb_neurone_coucprec; i++) {
	        	sum += weight[i]*input[i];
	        }
	        return sum;
	 }
	 
	 public void transition(){
		 this.ouput = Math.max(sum(),0);
	 }
	 
	 public void tangh(){
		 this.ouput = Math.tanh(sum());
	 }

	

	public double getOuput() {
		return ouput;
	}
	 
	 
}
