package dnn;

import java.util.ArrayList;
import java.util.List;

import Donnee.FullDonnee;
import Donnee.IrisDonnee;


public class ReseauConvolution {
	
	int nL; // nb de couches cachees
    int k; // nombre de parametres
    int n; // nb de neurones dans la couche cachée
    double alpha = 0.5;
    double outputErrorTotal;

    protected Layer input; // 3 neurones
    protected Layer output; // sortie - 3 neurone
    protected ArrayList<Layer> hidden; // 3 couches de 2 neurones
    protected Filter filtre;
    protected FullDonnee donneeIris;
    protected String OuputAttendu;

    public ReseauConvolution(int _nL, int _n, int _k){
    	this.donneeIris = new FullDonnee();
        this.input = new Layer(_n);
        this.output = new Layer(this.donneeIris.getClasse().size());
        System.out.println(this.donneeIris.getClasse().size());
        this.hidden = new ArrayList<Layer>(_nL);
        for (int i = 0; i < _nL; i++) {
            hidden.add(new Layer(_n));
        }
        this.filtre = new Filter();
    }
    
   public void InitialisationPoidsNeurone(){
	   // Définier les poids avec des valeurs aléatoires pour commencer. Retro propagation du gradient pour affiner les poids
   }
   
   public double OutputValeurReseau(){
	   double max = -1;
	   for(int i = 0 ; i<this.output.n ; i++){
		   if(this.output.getNeurone().get(i).getOuput() > max){
			   max = this.output.getNeurone().get(i).getOuput();
		   }
	   }
	   return max;
   }
   /**
    * Calcul de l'erreur. Somme des erreurs de tous les neurones de sorties.
    * @return
    */
   public double Ouputerror(String classeAttendu){
	   int n_classe = this.donneeIris.getClasse().get(classeAttendu);
	   double OutputErreurtotal =0.0;
	   for(int i = 0 ; i<this.output.n ; i++){
		   if(i==n_classe){
			   OutputErreurtotal += 0.5 * (1-this.output.getNeurone().get(i).ouput)*(1-this.output.getNeurone().get(i).ouput);
		   }
		   else{
			   OutputErreurtotal += 0.5 * (0-this.output.getNeurone().get(i).ouput)*(0-this.output.getNeurone().get(i).ouput);
		   }
	   }
	   this.outputErrorTotal = OutputErreurtotal;
	   return OutputErreurtotal;
   }
   
   /**
	 * Applique la retro-propagation du gradient pour modifier les poids des neuronnes
	 */
	 public void modificationWeight(){
		// Aide : https://mattmazur.com/2015/03/17/a-step-by-step-backpropagation-example/
		 // Point entre derniere couchée et couche de sortie
		 for(int i = 0 ; i<this.output.n ; i++){
			 for(int j = 0 ; j<this.output.getNeurone().get(i).weight.length ; j++){
				 this.output.getNeurone().get(i).weight[j] = this.output.getNeurone().get(i).weight[j] - this.alpha;/** ( this.outputErrorTotal/)*/
			 }
		 }
	 }
	 
	 public static void main(String[] args) {
		 ReseauConvolution test = new ReseauConvolution(3, 2, 0);
	 }
    
    

}
