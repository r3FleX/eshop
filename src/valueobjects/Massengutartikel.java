package valueobjects;

public class Massengutartikel extends Artikel {

	// Attribute zur Beschreibung der Massengutartikel
	private int packungsgroesse;


	public Massengutartikel(String artname, int artnr, int artbestand, float artpreis, int packungsgroesse) {
		super(artname, artnr, artbestand, artpreis);
		this.packungsgroesse = packungsgroesse;
	}

	
	public Massengutartikel(Massengutartikel a) {
		// Copy-Konstruktor
		this(a.getName(), a.getNummer(), a.getBestand(), a.getPreis(), a.packungsgroesse);
	}

	
	public String toString() {
		return ("Artikelnummer: " + getNummer() + " / Artikelname: " + getName() + " / " + "Bestand: " + getBestand()
				+ " / " + "Packungsgroesse: " + packungsgroesse + " /" + "Preis pro Stueck: " + getPreis() + " " + '\u20ac');
	}
	
	public int getPackungsgroesse() {
		return this.packungsgroesse;
	}

	public void setPackungsgroesse(int packungsgroesse) {
		this.packungsgroesse = packungsgroesse;
	}
}