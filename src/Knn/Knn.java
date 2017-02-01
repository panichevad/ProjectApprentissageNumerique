package Knn;
import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
import javax.swing.plaf.synth.SynthSeparatorUI;

import Donnee.FullDonnee;
import Donnee.IrisDonnee;

public class Knn {

	protected FullDonnee donnee;

	public Knn(String nom) {
		// On récupère les données
		this.donnee = new FullDonnee(nom);
		// On lance l'algo
		System.out.println(this.algorithme(1,8));

	}

	public double distanceManhattan(double[] a, double[] b) {
		double dist = 0.;
		if (a.length != b.length)
			System.out.println("Erreur avec les tableaux, ils doivent avoir la même taille!");
		else {
			for (int i = 0; i < a.length; i++) {
				dist += Math.abs(b[i] - a[i]);
			}
		}
		return dist;
	}
	

	// Calcul les distances entre 1 point test et les autres points et retourne
	// les k plus proches voisins
	public IrisDonnee[] distanceMin(IrisDonnee test, IrisDonnee[] apprentissage, int k) {
		double[] distance = new double[52];
		HashMap<Double, IrisDonnee> donnee = new HashMap(52);
		IrisDonnee[] min = new IrisDonnee[k];
		for (int j = 0; j < 52; j++) {
			distance[j] = distanceManhattan(test.getCoordonnee(), apprentissage[j].getCoordonnee());
			donnee.put(distance[j], apprentissage[j]);
		}
		Arrays.sort(distance);
		for (int i = 0; i < k; i++) {
			min[i] = donnee.get(distance[i]);
		}
		return min;
	}

	public String prediction(IrisDonnee[] prediction) {
		int compteurc1 = 0;
		int compteurc2 = 0;		
		String valeur;
		for (int i = 0; i < prediction.length; i++) {
			if (prediction[i].getClasse().equals("Mine")) {
				compteurc1++;
			} else {
				if (prediction[i].getClasse().equals("Rock")) {
					compteurc2++;
				} 
			}
		}
		if (Integer.max(compteurc1, compteurc2) == compteurc1) {
			valeur = "Mine";
		} else {			
				valeur = "Rock";
			} 
		
		return valeur;
	}

	public int algorithme(int PetitK, int GrandK) {
		// Créer les 5 blocs ici
		ArrayList<IrisDonnee> copie = new ArrayList<>(this.donnee.getDonnee());
		IrisDonnee[][] bloc = new IrisDonnee[4][52];
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 52; j++) {
				int rand = (int) (Math.random() * (copie.size() - 1 + 1));
				bloc[i][j] = copie.get(rand);
				copie.remove(rand);
			}
		}
		// Fin

		// calcul des distances

		int[] erreur = new int[GrandK - PetitK + 1];
		for (int t = 0; t < 4; t++) {
			for (int v = 0; v < 4; v++) {
				if (v != t) {
					for (int k = PetitK; k < GrandK+1; k++) {
						for (int i = 0; i < 4; i++) {
							if (i != t && i != v) {
								for (int j = 0; j < 30; j++) {
									IrisDonnee[] valeur = new IrisDonnee[k];
									valeur = distanceMin(bloc[t][j], bloc[i], k);
									String prediction = prediction(valeur);
									System.out.println(prediction+": PREDICTION "+bloc[t][j].getClasse()+": VraiValeur");
									if (!bloc[t][j].getClasse().equals(prediction)) {
										System.out.println("Une Erreur trouvée pour k="+k);
										erreur[k - PetitK]++;
									}
								}
							}
						}
					}
				}
			}
		}
		int min = Integer.MAX_VALUE;
		int meilleurk = GrandK;
		for (int i = 0; i <GrandK - PetitK + 1 ; i++) {
			System.out.println("Nb Erreur sur k = "+(i+PetitK)+" : "+erreur[i]);
			if (erreur[i] <= min) {
				min = erreur[i];
				meilleurk = i;
			}
		}
		System.out.println("Le meilleur K est :");
		return meilleurk + PetitK ;
	}

	public static void main(String[] args) {
		// On lance l'algo
		Knn a = new Knn("sonar3.data");

	}

}
