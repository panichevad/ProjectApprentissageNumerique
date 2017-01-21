package dnn;

import java.util.ArrayList;
import java.util.List;

import Donnee.FullDonnee;
import Donnee.IrisDonnee;


public class ReseauConvolution {
	
	int nL; // nb de couches cachees
    int k; // nombre de parametres
    int n; // nb de neurones dans la couche
    double OuputError = 0;

    protected Layer input; // 3 neurones
    protected Layer output; // sortie - 3 neurone
    protected List<Layer> hidden; // 3 couches de 2 neurones
    protected Filter filtre;
    protected FullDonnee donneeIris;
    protected String OuputAttendu;

    public ReseauConvolution(int _nL, int _n, int _k){
    	this.donneeIris = new FullDonnee();
        this.input = new Layer(_n);
        this.output = new Layer(_n);
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
   public double Ouputerror(){
	   return 0.;
	   
   }
   
   /**
	 * Applique la retro-propagation du gradient pour modifier les poids des neuronnes
	 */
	 public void modificationWeight(){
		// Aide : https://mattmazur.com/2015/03/17/a-step-by-step-backpropagation-example/
	
	 }
    
    

}
