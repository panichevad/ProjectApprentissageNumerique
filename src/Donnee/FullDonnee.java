package Donnee;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class FullDonnee {
	
	protected ArrayList<IrisDonnee> donnee = new ArrayList<IrisDonnee>(150);
	
	public FullDonnee(){
		this.ReadIris();
	}
	
	public void ReadIris() {

		try {
			File source = new File("iris.data");
			BufferedReader in = new BufferedReader(new FileReader(source));
			String ligne = in.readLine();
			while (ligne != null) {
				Scanner rl = new Scanner(ligne);
				rl.useLocale(Locale.US);
				rl.useDelimiter(",");
				double sepalLength = rl.nextDouble(), sepalWidth = rl.nextDouble(), petalLength = rl.nextDouble(),
						petalWidth = rl.nextDouble();
				String classe = rl.next();
				double[] tmp = new double[4];
				tmp[0] = sepalLength;
				tmp[1] = sepalWidth;
				tmp[2] = petalLength;
				tmp[3] = petalWidth;
				this.donnee.add(new IrisDonnee(classe, tmp));
				ligne = in.readLine();
			}
			in.close();
		} catch (Exception e) {
			System.err.println(
					"Erreur dans la lecture du fichier, vous lisez une ligne qui n'existe probablement pas ou autre problème");
		}
	}

	public ArrayList<IrisDonnee> getDonnee() {
		return donnee;
	}
	
	

}
