package Donnee;

public class IrisDonnee {
	
	protected String classe;
	protected double[] coordonnee;
	
	public IrisDonnee(String classe, double[] coordonnee){
		this.classe = classe;
		this.coordonnee = coordonnee;
	}

	public String getClasse() {
		return classe;
	}

	public double[] getCoordonnee() {
		return coordonnee;
	}
	
	public double getCoordonneePre(int i){
		return this.coordonnee[i];
	}

	public void setCoordonnee(double[] coordonnee) {
		this.coordonnee = coordonnee;
	}
	
	

	
	
	
	
	
	

}
