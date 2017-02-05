package dnn;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Donnee.FullDonnee;
import Donnee.Donnee;


public class ReseauConvolution {
	
	int nL; // nb de couches cachees
    int k; // nombre de parametres entree
    int n; // nb de neurones dans la couche cachée
    int n_entree;
    double alpha = 0.5;
    double outputErrorTotal;

    protected Layer input; // 3 neurones
    protected Layer output; // sortie - 3 neurone
    protected ArrayList<Layer> hidden; // 3 couches de 2 neurones
    protected Filter filtre;
    protected FullDonnee donneeIris;
    protected ArrayList<Donnee> donneeIrisfiltre;
    protected String OuputAttendu;

    public ReseauConvolution(int _nL, int _n, int _k, int n_entree, String nomfichier){
    	this.donneeIris = new FullDonnee(nomfichier);
    	this.donneeIrisfiltre = new ArrayList<Donnee>(this.donneeIris.getDonnee().size());
        this.input = new Layer(n_entree);
        this.nL = _nL;
        this.n = _n;
        this.k = _k;
        this.n_entree = n_entree;
        this.output = new Layer(3);
        this.hidden = new ArrayList<Layer>(nL);
        for (int i = 0; i < nL; i++) {
            hidden.add(new Layer(n));
        }
        this.filtre = new Filter();
        
        this.start(20);
        
       
 
        
        
    }
    
    public void parcoursApprentissage(Donnee donnee){
    	//Entree
    	for(int i = 0 ; i < this.n_entree ; i++){
    		this.input.getNeurone().get(i).input = donnee.getCoordonnee();
    		this.input.getNeurone().get(i).sum();
    		this.input.getNeurone().get(i).transition();
    	}
    	this.input.output_value();
    	//Cachee
    	for(int i = 0 ; i < this.nL ; i++){
    		for(int j = 0 ; j < this.n ; j++){
    			if(i == 0){
    				this.hidden.get(i).getNeurone().get(j).input = this.input.getOutput();
    				this.hidden.get(i).getNeurone().get(j).sum();
    				this.hidden.get(i).getNeurone().get(j).transition();
    			}
    			else{
    				this.hidden.get(i).getNeurone().get(j).input = this.hidden.get(i-1).getOutput();
    				this.hidden.get(i).getNeurone().get(j).sum();
    				this.hidden.get(i).getNeurone().get(j).transition();
    			}
    		}
    		this.hidden.get(i).output_value();
    	}
    	//Sortie
    	for(int i = 0 ; i < 3 ; i++){
    		this.output.getNeurone().get(i).input = this.hidden.get(nL-1).getOutput();
    		this.output.getNeurone().get(i).sum();
    		this.output.getNeurone().get(i).tangh();
    	}
    	this.Ouputerror(donnee.getClasse());
    	System.out.println("APPRENTISSAGE, Erreur Totale : "+ this.outputErrorTotal);
    	this.retroPropagationGradient(donnee.getClasse());
    }
    
    public String resultat(int a){
    	String tmp = "";
    	if(a==0){
    		tmp = "Iris-setosa";
    	}
    	if(a==1){
    		tmp = "Iris-versicolor";
    	}
    	if(a==2){
    		tmp = "Iris-virginica";
    	}
    	return tmp;
    }
    
    
    public void parcours(Donnee donnee){
    	//Entree
    	for(int i = 0 ; i < this.n_entree ; i++){
    		this.input.getNeurone().get(i).input = donnee.getCoordonnee();
    		this.input.getNeurone().get(i).sum();
    		this.input.getNeurone().get(i).transition();
    	}
    	this.input.output_value();
    	//Cachee
    	for(int i = 0 ; i < this.nL ; i++){
    		for(int j = 0 ; j < this.n ; j++){
    			if(i == 0){
    				this.hidden.get(i).getNeurone().get(j).input = this.input.getOutput();
    				this.hidden.get(i).getNeurone().get(j).sum();
    				this.hidden.get(i).getNeurone().get(j).transition();
    			}
    			else{
    				this.hidden.get(i).getNeurone().get(j).input = this.hidden.get(i-1).getOutput();
    				this.hidden.get(i).getNeurone().get(j).sum();
    				this.hidden.get(i).getNeurone().get(j).transition();
    			}
    		}
    		this.hidden.get(i).output_value();
    	}
    	//Sortie
    	for(int i = 0 ; i < 3 ; i++){
    		this.output.getNeurone().get(i).input = this.hidden.get(nL-1).getOutput();
    		this.output.getNeurone().get(i).sum();
    		this.output.getNeurone().get(i).tangh();
    	}
    	for(int i = 0 ; i<this.output.getNeurone().size() ; i++){
        	System.out.println("Valeur de sortie : "+this.output.getNeurone().get(i).ouput);
        }
    	if(donnee.getClasse().equals(this.resultat(this.OutputValeurReseau()))){
    		System.out.println("EXACTE : Valeur attendue : "+donnee.getClasse()+" Valeur obtenue : "+this.resultat(this.OutputValeurReseau()));
    	}
    	else{
    		System.out.println("ERREUR : Valeur attendue : "+donnee.getClasse()+" Valeur obtenue : "+this.resultat(this.OutputValeurReseau()));
    	}
    }
    
    public void start(int apprentissage){
    	this.creerNeurone();
        this.InitialisationPoidsNeurone();
        this.appliquerFiltre();
        

        //Apprentissage
    	for(int i = 0 ; i < apprentissage ; i++){
    		Random rand = new Random();
   		 	int nombreAleatoire = rand.nextInt(149 - 1 + 1) + 1;
   		 	this.parcoursApprentissage(this.donneeIrisfiltre.get(nombreAleatoire));
    	}

    	for(int i =0  ; i < 10 ; i++){
    		 Random rand = new Random();
    		 int nombreAleatoire = rand.nextInt(140 - 1 + 1) + 1;
    		 this.parcours(this.donneeIrisfiltre.get(nombreAleatoire));
    	}
 
    	
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
	   for(int i = 0 ; i<this.input.n ; i++){
			 for(int j = 0 ; j<this.input.getNeurone().get(i).weight.length ; j++){
				  double nombreAleatoire = rand.nextInt(20 + 5);
				  double random = (double)nombreAleatoire/100;
				  this.input.getNeurone().get(i).weight[j] = random;
			 }
	   }
	   for(int i = 0 ; i< this.hidden.size() ; i++){
		   for(int j = 0 ; j< this.hidden.get(i).n ; j++){
			   for(int k = 0 ; k< this.hidden.get(i).getNeurone().get(j).weight.length ; k++){
				   double nombreAleatoire = rand.nextInt(20 + 5);
				   double random = (double)nombreAleatoire/100;
				   this.hidden.get(i).getNeurone().get(j).weight[k] = random;
			   }
		   }
	   }
	   for(int i = 0 ; i<this.output.n ; i++){
			 for(int j = 0 ; j<this.output.getNeurone().get(i).weight.length ; j++){
				  double nombreAleatoire = rand.nextInt(20 + 5);
				  double random = (double)nombreAleatoire/100;
				  this.output.getNeurone().get(i).weight[j] = random;
			 }
	   }
   }
   
   public void appliquerFiltre(){
	   for(int i = 0 ; i< this.donneeIris.getDonnee().size() ; i++){
		   double tab[] = new double[this.donneeIris.getDonnee().size()];
		   tab = this.filtre.applicationFiltre(this.donneeIris.getDonnee().get(i).getCoordonnee());
		   Donnee tmp = new Donnee(this.donneeIris.getDonnee().get(i).getClasse(), tab);
		   this.donneeIrisfiltre.add(tmp);
	   }
   }
   
   public int OutputValeurReseau(){
	   double max = -1;
	   int res = 0;
	   for(int i = 0 ; i<this.output.n ; i++){
		   if(this.output.getNeurone().get(i).getOuput() > max){
			   max = this.output.getNeurone().get(i).getOuput();
			   res = i;
		   }
	   }
	   return res;
   }
   /**
    * Calcul de l'erreur. Somme des erreurs de tous les neurones de sorties.
    * @return
    */
   public double Ouputerror(String classeAttendu){
	   int n_classe = 0;
		 if(classeAttendu.equals("Iris-setosa")){
			 n_classe = 0;
		 }
		 if(classeAttendu.equals("Iris-versicolor")){
			 n_classe = 1;
		 }
		 if(classeAttendu.equals("Iris-virginica")){
			 n_classe = 2;
		 }
	   double OutputErreurtotal =0.0;
	   for(int i = 0 ; i<this.output.n ; i++){
		   if(i==n_classe){
			   OutputErreurtotal += 0.5 * (0.99-this.output.getNeurone().get(i).ouput)*(0.99-this.output.getNeurone().get(i).ouput);
		   }
		   else{
			   OutputErreurtotal += 0.5 * (0.01-this.output.getNeurone().get(i).ouput)*(0.01-this.output.getNeurone().get(i).ouput);
		   }
	   }
	   this.outputErrorTotal = OutputErreurtotal;
	   return OutputErreurtotal;
   }
   
   /**
	 * Applique la retro-propagation du gradient pour modifier les poids des neuronnes
	 */
	 public void retroPropagationGradient(String classeAttendu){
		 int n_classe = 0;
		 if(classeAttendu.equals("Iris-setosa")){
			 n_classe = 0;
		 }
		 if(classeAttendu.equals("Iris-versicolor")){
			 n_classe = 1;
		 }
		 if(classeAttendu.equals("Iris-virginica")){
			 n_classe = 2;
		 }
		 double errortmp = 0;
		 
		// Poids entre derniere couchée et couche de sortie
		 for(int i = 0 ; i<this.output.n ; i++){
			 for(int j = 0 ; j<this.output.getNeurone().get(i).weight.length ; j++){
				 if(i==n_classe){
					 double tmpderive = 1 - (Math.tanh(this.output.getNeurone().get(i).ouput)*Math.tanh(this.output.getNeurone().get(i).ouput));
					 errortmp = (this.output.getNeurone().get(i).ouput-1) * tmpderive * this.hidden.get(nL-1).getNeurone().get(j).ouput;
				 }
				 else{
					 double tmpderive = 1 - (Math.tanh(this.output.getNeurone().get(i).ouput)*Math.tanh(this.output.getNeurone().get(i).ouput));
					 errortmp = ((this.output.getNeurone().get(i).ouput-0)) * tmpderive * this.hidden.get(nL-1).getNeurone().get(j).ouput;
				 }
				 this.output.getNeurone().get(i).weight[j] = this.output.getNeurone().get(i).weight[j] - (this.alpha * errortmp);
			 }
		 }
		// Poids entre les couches cachées
		for(int i = this.hidden.size()-1 ; i>0 ; i--){
			for(int j = 0 ; j<this.hidden.get(i).n ; j++){
				double errorlastlayer = 0;
				for(int g = 0 ; g < this.output.n ; g++){
					if(g==n_classe){
						 double tmpderive = 1 - (Math.tanh(this.output.getNeurone().get(g).ouput)*Math.tanh(this.output.getNeurone().get(g).ouput));
						 errorlastlayer += ((this.output.getNeurone().get(g).ouput-1)) * tmpderive * this.output.getNeurone().get(g).weight[j];
					 }
					 else{
						 double tmpderive = 1 - (Math.tanh(this.output.getNeurone().get(g).ouput)*Math.tanh(this.output.getNeurone().get(g).ouput));
						 errorlastlayer += ((this.output.getNeurone().get(g).ouput-0)) * tmpderive * this.output.getNeurone().get(g).weight[j]; 
					 }
				}
				for(int k = 0 ; k<this.hidden.get(i).getNeurone().get(j).weight.length ; k++){
					double tmpderive = 0;
					if(this.hidden.get(i).getNeurone().get(j).getOuput()>0){
						tmpderive = 1;
					}
					else{
						tmpderive = 0;
					}
					if(i==0){
						errortmp = errorlastlayer * tmpderive * this.input.getNeurone().get(k).ouput;
						this.hidden.get(i).getNeurone().get(j).weight[k] = this.hidden.get(i).getNeurone().get(j).weight[k] - (this.alpha * errortmp);
					}
					else{
						errortmp = errorlastlayer * tmpderive * this.hidden.get(i-1).getNeurone().get(k).ouput;
						this.hidden.get(i).getNeurone().get(j).weight[k] = this.hidden.get(i).getNeurone().get(j).weight[k] - (this.alpha * errortmp);
					}
				}
			}
		}
	 }
	 
	 public static void main(String[] args) {
		 ReseauConvolution test = new ReseauConvolution(3, 2, 4, 2, "iris.data");
	 }
    
    

}
