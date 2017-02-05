package Donnee;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Scanner;

import Knn.Knn;

public class FullDonnee {
	
	protected ArrayList<Donnee> donnee;
	
	public FullDonnee(String nom){
		this.donnee = new ArrayList<>(this.tailleFichier(nom));
		this.ReadIris(nom);
	}
	
	public int tailleFichier(String nom){
		int taillefichier = 0;
		try {
			
			File source = new File(nom);
			BufferedReader in = new BufferedReader(new FileReader(source));
			String ligne = in.readLine();
			while (ligne != null) {
				ligne = in.readLine();
				taillefichier++;
			}
			in.close();
		} catch (Exception e) {
			System.err.println(
					"Erreur dans la lecture du fichier, vous lisez une ligne qui n'existe probablement pas ou autre probl�me");
		}
		return taillefichier;
		
	}
	
	public void ReadIris(String nom) {

		try {
			File source = new File(nom);
			BufferedReader in = new BufferedReader(new FileReader(source));
			String ligne = in.readLine();
			Scanner r1 = new Scanner(ligne);
			r1.useLocale(Locale.US);
			r1.useDelimiter(", ");
			int taille = 0;
			while(r1.hasNextDouble()){
				r1.nextDouble();
				taille++;
			}
			while (ligne != null) {
				Scanner rl = new Scanner(ligne);
				rl.useLocale(Locale.US);
				rl.useDelimiter(", ");
				int m = 0;
				double[] tmp = new double[taille];
				while(rl.hasNextDouble()){
					double valeur = rl.nextDouble();
					tmp[m] = valeur;
					m++;
				}
				String classe = rl.next();
				this.donnee.add(new Donnee(classe, tmp));
				ligne = in.readLine();
			}
			in.close();
		} catch (Exception e) {
			System.err.println(
					"Erreur dans la lecture du fichier, vous lisez une ligne qui n'existe probablement pas ou autre probl�me");
		}
	}
	

	public ArrayList<Donnee> getDonnee() {
		return donnee;
	}


	

	
	public static void main(String[] args) {
		// On lance l'algo
		FullDonnee a = new FullDonnee("iris.data");
	}
	
	
	
	

}
