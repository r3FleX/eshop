package ui.module;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import domain.Shopverwaltung;
import domain.exceptions.ArtikelExistiertNichtException;
import domain.exceptions.BestandUeberschrittenException;
import ui.ArtikelTableModel;
import ui.GUI_2;
import valueobjects.Account;
import valueobjects.Artikel;
import valueobjects.Kunde;

public class WarenkorbPanel extends JPanel implements ActionListener {

	//private JPanel warenkorbPanel;
	private JTable ausgabeTabelle;
	private ArtikelTableModel artikeltable;
	private JScrollPane scrollPane;
	private Shopverwaltung shop;
	private GUI_2 gui;
	private Kunde user;

	//Konstruktor
	public WarenkorbPanel(GUI_2 gui, Account user) {
		this.gui = gui;
		this.user = (Kunde) user;
		
		//warenkorbPanel = new JPanel();
		this.setLayout(new GridLayout());
		this.setBorder(BorderFactory.createTitledBorder("Warenkorb")); //Ueberschrift Warenkorb
		this.setVisible(true);
		
		// TableModel als "Datencontainer" anlegen:
		artikeltable = new ArtikelTableModel();
		
		// JTable-Objekt erzeugen und mit Datenmodell initialisieren:
		ausgabeTabelle = new JTable(artikeltable);
		ausgabeTabelle.setAutoCreateRowSorter(true);
		
		// JTable in ScrollPane platzieren:
		scrollPane = new JScrollPane(ausgabeTabelle);
				
		// Anzeige der Artikelliste auch in der Kunden-Ansicht
		artikeltable.setDataVector2(this.user.getWarenkorb());
		this.add(scrollPane);	
	}	
	
	public void wieOftArtikelZumWarenKorb(){
		
		
	}
	
	/*
	//Getter und Setter
	public JPanel getWarenkorbPanel() {
		return warenkorbPanel;
	}

	public void setWarenkorbPanel(JPanel warenkorbPanel) {
		this.warenkorbPanel = warenkorbPanel;
	}	
	*/
	public void actionPerformed(ActionEvent arg0) {
		System.out.println("Warenkorb Aktion ausgefuehrt");	
	}	
	
	public JTable getAusgabeTabelle() {
		return ausgabeTabelle;
	}
	
	public void setAusgabeTabelle(JTable ausgabeTabelle) {
		this.ausgabeTabelle = ausgabeTabelle;
	}
}
