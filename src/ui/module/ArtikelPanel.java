package ui.module;

import java.awt.GridLayout;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import ui.ArtikelTableModel;
import valueobjects.Artikel;

public class ArtikelPanel {
	
	private JPanel artikelPanel;
	private ArtikelTableModel artikeltable;
	
	//Konstruktor
	public ArtikelPanel(List<Artikel> artikelliste) {
		
		JPanel artikelPanel = new JPanel();
		artikelPanel.setLayout(new GridLayout());
		artikelPanel.setBorder(BorderFactory.createTitledBorder("Artikel")); //Ueberschrift Artikel
		
		// TableModel als "Datencontainer" anlegen:
		artikeltable = new ArtikelTableModel();
		
		// Artikel-Liste aufbereiten
		artikeltable.setDataVector(new Vector<Artikel>());
		
		// JTable-Objekt erzeugen und mit Datenmodell initialisieren:
		JTable ausgabeTabelle = new JTable(artikeltable);
		ausgabeTabelle.setAutoCreateRowSorter(true);
		// JTable in ScrollPane platzieren:
		JScrollPane scrollPane = new JScrollPane(ausgabeTabelle);
				
		// Anzeige der Artikelliste auch in der Kunden-Ansicht
		artikeltable.setDataVector(artikelliste);
		artikelPanel.add(scrollPane);
		
		setArtikelPanel(artikelPanel);
	}
	
	//Getter und Setter
	public ArtikelTableModel getArtikeltable() {
		return artikeltable;
	}
	public void setArtikeltable(ArtikelTableModel artikeltable) {
		this.artikeltable = artikeltable;
	}
	public JPanel getArtikelPanel() {
		return this.artikelPanel;
	}
	public void setArtikelPanel(JPanel artikelPanel) {
		this.artikelPanel = artikelPanel;
	}	
	
}
