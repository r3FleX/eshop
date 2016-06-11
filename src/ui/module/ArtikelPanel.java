package ui.module;

import java.awt.GridLayout;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import ui.ArtikelTableModel;
import ui.ButtonEditor;
import valueobjects.Account;
import valueobjects.Artikel;
import ui.ButtonRenderer;
import ui.GUI_2;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import domain.Shopverwaltung;

public class ArtikelPanel extends JPanel{
	
	//private JPanel artikelPanel;
	private ArtikelTableModel artikeltable;
	private JTable ausgabeTabelle;
	private JScrollPane scrollPane;
	private Account user;
	private Shopverwaltung shop;
	
	//Konstruktor
	public ArtikelPanel(List<Artikel> artikelliste) {
		
		//JPanel artikelPanel = new JPanel();
		this.setLayout(new GridLayout());
		this.setBorder(BorderFactory.createTitledBorder("Artikel")); //Ueberschrift Artikel
		
		// TableModel als "Datencontainer" anlegen:
		artikeltable = new ArtikelTableModel();
		
		// Artikel-Liste aufbereiten
		artikeltable.setDataVector(new Vector<Artikel>());
		
		// JTable-Objekt erzeugen und mit Datenmodell initialisieren:
		ausgabeTabelle = new JTable(artikeltable);
		ausgabeTabelle.setAutoCreateRowSorter(true);

		// JTable in ScrollPane platzieren:
		scrollPane = new JScrollPane(ausgabeTabelle);
				
		// Anzeige der Artikelliste auch in der Kunden-Ansicht
		artikeltable.setDataVector(artikelliste);	
		this.add(scrollPane);
		renderOption();
	
		//setArtikelPanel(artikelPanel);
	}
	
	public void renderOption() {
		//Layout Tabelle -> Button fuer "Option"
		ausgabeTabelle.getColumn("Option").setCellRenderer(new ButtonRenderer());
		ausgabeTabelle.getColumn("Option").setCellEditor(
	        new ButtonEditor(new JCheckBox(),ausgabeTabelle, user, shop));	
		
	}
	
	//Getter und Setter
	public ArtikelTableModel getArtikeltable() {
		return artikeltable;
	}
	public void setArtikeltable(ArtikelTableModel artikeltable) {
		this.artikeltable = artikeltable;
	}

	public JTable getAusgabeTabelle() {
		return this.ausgabeTabelle;
	}
	
	/*public JPanel getArtikelPanel() {
		return this.artikelPanel;
	}
	public void setArtikelPanel(JPanel artikelPanel) {
		this.artikelPanel = artikelPanel;
	}	
	*/
}
