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

import domain.Shopverwaltung;
import ui.panel.WarenkorbButtonPanel;
import ui.panel.WarenkorbPanel;
import valueobjects.Account;
import valueobjects.Artikel;
import valueobjects.Kunde;
import valueobjects.Mitarbeiter;
import valueobjects.Warenkorb;


public class ArtikelTableModel extends DefaultTableModel {
	boolean edit = false;
	public ArtikelTableModel(boolean option, boolean edit) {
		// Ober-Objekt der Klasse DefaultTableModel initialisieren		
		super();
		
		Vector spalten = new Vector();		
		spalten.add("Nummer");
		spalten.add("Name");
		spalten.add("Bestand");
		spalten.add("Preis");
		spalten.add("Packungsgroesse");
		if (option) spalten.add("Option");

		// Spaltennamen in geerbtem Attribut merken
		this.columnIdentifiers = spalten;
		this.edit = edit;
	}
	
		// Tabellendaten hinzuf�gen
		public void setDataVector(List<Artikel> articles, String optionname) {
			// DefaultTableModel erwartet Repr�sentation der Tabellendaten
			// als Vector von Vectoren
			Vector rows = new Vector();

			for (Artikel artikel: articles) {
				Vector einArtikelAlsVector = new Vector();
				einArtikelAlsVector.add(artikel.getNummer());
				einArtikelAlsVector.add(artikel.getName());
				einArtikelAlsVector.add(artikel.getBestand());
				einArtikelAlsVector.add(artikel.getPreis());
				einArtikelAlsVector.add(artikel.getPackungsgroesse());
				if (!optionname.isEmpty()) einArtikelAlsVector.add(optionname);
				rows.add(einArtikelAlsVector);
			}
			this.setDataVector(rows, columnIdentifiers);
		}
	
	
	/**
	 * Tabellendaten hinzuf�gen (f�r den Warenkorb)
	 */
	
	public void setDataVector2(Warenkorb warenkorb) {
		Set<Artikel> articles = warenkorb.getInhalt().keySet();
		
		Vector rows = new Vector();
	
		for (Artikel artikel : articles) {

			Vector einArtikelAlsVector = new Vector();
			einArtikelAlsVector.add(artikel.getNummer());
			einArtikelAlsVector.add(artikel.getName());
			einArtikelAlsVector.add((Integer)warenkorb.getInhalt().get(artikel));
			einArtikelAlsVector.add(artikel.getPreis());
			rows.add(einArtikelAlsVector);
		}
		this.setDataVector(rows, columnIdentifiers);
	}
	
    public boolean isCellEditable(int row, int col) {
    	//Tabelle bearbeitbar?
    	if (this.edit == false) {
    		//Optionen freigeben
    		if (col > 4) return true;
    		return false;
    	}
    	//artikelnummern d�rfen nicht geaednert werden ..
        if (col < 1) {
          return false;
        } else {
          return true;
        }
      }
}
