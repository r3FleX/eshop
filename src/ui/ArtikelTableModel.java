package ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

import ui.module.WarenkorbButton;
import valueobjects.Artikel;
import valueobjects.Kunde;
import valueobjects.Warenkorb;


public class ArtikelTableModel extends DefaultTableModel {
	
	Kunde kunde;
	private HashMap<Artikel, Integer> positionen = null;
	private float gesamtpreis = 0.0f;
	private int anzahl = 0;	
	
	public ArtikelTableModel() {
		// Ober-Objekt der Klasse DefaultTableModel initialisieren
		super();
		Vector spalten = new Vector();		
		spalten.add("Nummer");
		spalten.add("Name");
		spalten.add("Bestand");
		spalten.add("Preis");
		spalten.add("Packungsgroesse");
		spalten.add("Option");

		// Spaltennamen in geerbtem Attribut merken
		this.columnIdentifiers = spalten;
		/*
		//Fuer Warenkorb Button "in Warenkorb legen" 
		
		JPanel inWarenkorbLegenPanel = new JPanel();
			
		inWarenkorbLegenPanel.add(inWarenkorbLegenPanel, BorderLayout.NORTH);	
		
		JButton inWarenKorbLegenButton = new JButton("in Warenkorb legen",new ImageIcon("src/assets/inWarenkorbLegenIcon.png"));
		inWarenkorbLegenPanel.add(inWarenKorbLegenButton);
		inWarenkorbLegenPanel.setVisible(true);
		
		//ACTIONLISTINER
		inWarenKorbLegenButton.addActionListener(new ActionListener() {	
			public void actionPerformed(ActionEvent e) {
				System.out.println("in den warenkorb");
			}
		});
		*/
	}
	
	// Tabellendaten hinzufügen
	public void setDataVector(List<Artikel> articles) {
		// DefaultTableModel erwartet Repräsentation der Tabellendaten
		// als Vector von Vectoren
		Vector rows = new Vector();
		//WarenkorbButton warenkorbButton = new WarenkorbButton();
		for (Artikel artikel: articles) {
			Vector einArtikelAlsVector = new Vector();
			einArtikelAlsVector.add(artikel.getNummer());
			einArtikelAlsVector.add(artikel.getName());
			einArtikelAlsVector.add(artikel.getBestand());
			einArtikelAlsVector.add(artikel.getPreis());
			einArtikelAlsVector.add(artikel.getPackungsgroesse());
			
		//	einArtikelAlsVector.add(warenkorbButton.createInWarenkorbLegenButton());
			rows.add(einArtikelAlsVector);
			//einArtikelAlsVector.add(kunde.getWarenkorb().getInhalt().values());
		}
		// Vector von Vectoren mit Bücher-Strings eintragen
		// (geerbte Methode)
		this.setDataVector(rows, columnIdentifiers);
	}
	
	/**
	 * Tabellendaten hinzufügen (für den Warenkorb)
	 * 
	 * @param articles
	 */
	
	public void setDataVector2(Warenkorb warenkorb) {
		Set<Artikel> articles = warenkorb.getInhalt().keySet();
		
//		this.positionen = kunde.getWarenkorb().getInhalt();
//		Set<Artikel> articles1 = this.positionen.keySet();
		
		// Bücher aus Liste aufbereiten:
		// DefaultTableModel erwartet Repräsentation der Tabellendaten
		// als Vector von Vectoren
//		Set<Artikel> articles1 = this.positionen.keySet();
		Vector rows = new Vector();
		for (Artikel artikel : articles) {
//			 for (Artikel artikel1 : articles1) {
//				 anzahl = this.positionen.get(artikel1);
//				 
//			 }
			
//			for (Artikel artikel1 : articles) {
////				int anzahl = kunde.getWarenkorb().getInhalt().get(artikel1);
//				int anzahl = this.positionen.get(artikel);
//			}
			Vector einArtikelAlsVector = new Vector();
			einArtikelAlsVector.add(artikel.getNummer());
			einArtikelAlsVector.add(artikel.getName());
			einArtikelAlsVector.add((Integer)warenkorb.getInhalt().get(artikel));
			einArtikelAlsVector.add(artikel.getPreis());
			
		//	einArtikelAlsVector.add
			rows.add(einArtikelAlsVector);
		}
		// Vector von Vectoren mit Bücher-Strings eintragen
		// (geerbte Methode)
		this.setDataVector(rows, columnIdentifiers);
	}
}
