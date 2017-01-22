package dnn;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    protected ArrayList<IrisDonnee> donneeIrisfiltre;
    protected String OuputAttendu;

    public ReseauConvolution(int _nL, int _n, int _k){
    	this.donneeIris = new FullDonnee();
    	this.donneeIrisfiltre = new ArrayList<IrisDonnee>(this.donneeIris.getDonnee().size());
        this.input = new Layer(_n);
        this.nL = _nL;
        this.n = _n;
        this.k = _k;
        this.output = new Layer(this.donneeIris.getClasse().size());
        System.out.println(this.donneeIris.getClasse().size());
        this.hidden = new ArrayList<Layer>(nL);
        for (int i = 0; i < nL; i++) {
            hidden.add(new Layer(n));
        }
        this.filtre = new Filter();
        
        
        this.creerNeurone();
        this.InitialisationPoidsNeurone();
        this.appliquerFiltre();
        for(int i = 0 ; i<this.output.getNeurone().size() ; i++){
        	for(int j = 0 ; j < this.output.getNeurone().get(i).weight.length ; j++){
        		System.out.println(this.output.getNeurone().get(i).weight[j]);
        	}
        }
        
        
    }
    
    public void start(){
    	
    }
    
    public void creerNeurone(){
		for(int i = 0 ; i<this.input.n ; i++){
			this.input.getNeurone().add(new Neurone(k));
		}
		for(int i = 0 ; i<this.output.n ; i++){
			this.output.getNeurone().add(new Neurone(n));
		}
		for(int i = 0 ; i<this.nL; i++){
			if(i ==0){
				for(int j = 0 ; j<this.n ; j++){
					this.hidden.get(i).getNeurone().add(new Neurone(this.input.n));
				}
			}
			else{
				for(int j = 0 ; j<this.n ; j++){
					this.hidden.get(i).getNeurone().add(new Neurone(this.n));
				}
			}
		}
	}
    
   public void InitialisationPoidsNeurone(){
	   // Définier les poids avec des valeurs aléatoires pour commencer. Retro propagation du gradient pour affiner les poids
	   Random rand = new Random();
	   for(int i = 0 ; i< this.hidden.size() ; i++){
		   for(int j = 0 ; j< this.hidden.get(i).n ; j++){
			   for(int k = 0 ; k< this.hidden.get(i).getNeurone().get(j).weight.length ; k++){
				   double nombreAleatoire = rand.nextInt(100 + 1);
				   double random = (double)nombreAleatoire/100;
				   this.hidden.get(i).getNeurone().get(j).weight[k] = random;
			   }
		   }
	   }
	   for(int i = 0 ; i<this.output.n ; i++){
			 for(int j = 0 ; j<this.output.getNeurone().get(i).weight.length ; j++){
				  double nombreAleatoire = rand.nextInt(100 + 1);
				  double random = (double)nombreAleatoire/100;
				  this.output.getNeurone().get(i).weight[j] = random;
			 }
	   }
   }
   
   public void appliquerFiltre(){
	   for(int i = 0 ; i< this.donneeIris.getDonnee().size() ; i++){
		   double tab[] = new double[2];
		   tab[0] = this.filtre.firstfiltre(this.donneeIris.getDonnee().get(i).getCoordonnee());
		   tab[1] = this.filtre.secondfiltre(this.donneeIris.getDonnee().get(i).getCoordonnee());
		   IrisDonnee tmp = new IrisDonnee(this.donneeIris.getDonnee().get(i).getClasse(), tab);
		   this.donneeIrisfiltre.add(tmp);
	   }
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
	 public void retroPropagationGradient(String classeAttendu){
		// Aide : https://mattmazur.com/2015/03/17/a-step-by-step-backpropagation-example/
		 int n_classe = this.donneeIris.getClasse().get(classeAttendu);
		 double errortmp = 0;
		 
		// Poids entre derniere couchée et couche de sortie
		 for(int i = 0 ; i<this.output.n ; i++){
			 for(int j = 0 ; j<this.output.getNeurone().get(i).weight.length ; j++){
				 if(i==n_classe){
					 errortmp = (-(1-this.output.getNeurone().get(i).ouput)) * (this.output.getNeurone().get(i).ouput*(1-this.output.getNeurone().get(i).ouput)) * this.output.getNeurone().get(i).input[j];
				 }
				 else{
					 errortmp = (-(0-this.output.getNeurone().get(i).ouput)) * (this.output.getNeurone().get(i).ouput*(1-this.output.getNeurone().get(i).ouput)) * this.output.getNeurone().get(i).input[j]; 
				 }
				 this.output.getNeurone().get(i).weight[j] = this.output.getNeurone().get(i).weight[j] - this.alpha * errortmp;
			 }
		 }
		 
		// Poids entre les couches cachées
		for(int i = this.hidden.size()-1 ; i==0 ; i--){
			for(int j = 0 ; j<this.hidden.get(i).n ; j++){
				double errorlastlayer = 0;
				if(i==this.hidden.size()-1){
					for(int g = 0 ; g<this.output.n ; g++){
						if(i==n_classe){
							errorlastlayer += (-(1-this.output.getNeurone().get(g).ouput)) * (this.output.getNeurone().get(g).ouput*(1-this.output.getNeurone().get(g).ouput)) * this.output.getNeurone().get(g).weight[j];
						}
						else{
							errorlastlayer += (-(0-this.output.getNeurone().get(i).ouput)) * (this.output.getNeurone().get(i).ouput*(1-this.output.getNeurone().get(i).ouput)) * this.output.getNeurone().get(g).weight[j];	
						}
		
					}
				}
				else{
					for(int g = 0 ; g<this.hidden.get(i+1).n ; g++){
						if(i==n_classe){
							errorlastlayer = (-(1-this.hidden.get(i+1).getNeurone().get(g).ouput)) * (this.hidden.get(i+1).getNeurone().get(g).ouput*(1-this.hidden.get(i+1).getNeurone().get(g).ouput)) * this.hidden.get(i+1).getNeurone().get(g).weight[j];
						}
						else{
							errorlastlayer = (-(0-this.hidden.get(i+1).getNeurone().get(i).ouput)) * (this.hidden.get(i+1).getNeurone().get(i).ouput*(1-this.hidden.get(i+1).getNeurone().get(i).ouput)) * this.hidden.get(i+1).getNeurone().get(g).weight[j];	
						}
					}
						
				}
				for(int k = 0 ; k<this.hidden.get(i).getNeurone().get(j).weight.length ; k++){
					errortmp = errorlastlayer * this.hidden.get(i).getNeurone().get(j).getOuput()*(1-this.hidden.get(i).getNeurone().get(j).getOuput())*this.hidden.get(i).getNeurone().get(j).input[k];
					this.hidden.get(i).getNeurone().get(j).weight[k] = this.hidden.get(i).getNeurone().get(j).weight[k] - this.alpha * errortmp;
				}
			}
		}
	 }
	 
	 public static void main(String[] args) {
		 ReseauConvolution test = new ReseauConvolution(3, 2, 2);
	 }
    
    

}
