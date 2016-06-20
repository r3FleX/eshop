package ui.panel;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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
import ui.ButtonEditor;
import ui.ButtonRenderer;
import ui.GUI_2;
import valueobjects.Account;
import valueobjects.Artikel;
import valueobjects.Kunde;
import valueobjects.Warenkorb;

public class WarenkorbPanel extends JPanel implements ActionListener {

	//private JPanel warenkorbPanel;
	private JTable ausgabeTabelle;
	private ArtikelTableModel artikeltable;
	private JScrollPane scrollPane;
	private GUI_2 gui;
		
	//Konstruktor
	public WarenkorbPanel(GUI_2 gui) {
		this.gui = gui;
		
		//warenkorbPanel = new JPanel();
		this.setLayout(new GridLayout());
		this.setBorder(BorderFactory.createTitledBorder("Warenkorb")); //Ueberschrift Warenkorb
		this.setVisible(true);
		
		// TableModel als "Datencontainer" anlegen:
		artikeltable = new ArtikelTableModel(true,false);
		
		// JTable-Objekt erzeugen und mit Datenmodell initialisieren:
		ausgabeTabelle = new JTable(artikeltable);
		ausgabeTabelle.setAutoCreateRowSorter(true);
		
		// JTable in ScrollPane platzieren:
		scrollPane = new JScrollPane(ausgabeTabelle);
				
		Kunde user =(Kunde) this.gui.getUser();
		// Anzeige der Artikelliste auch in der Kunden-Ansicht
		this.updateData(user.getWarenkorb());
		this.add(scrollPane);
	}
	
	public ArtikelTableModel getArtikeltable() {
		return artikeltable;
	}

	public void setArtikeltable(ArtikelTableModel artikeltable) {
		this.artikeltable = artikeltable;
	}
	
	private void renderOption() {
		 ActionListener listen = new ActionListener() {
			    public void actionPerformed(ActionEvent e) {
					try {
						//zu entfernenden artikel suchen
						int artikelnummer = Integer.parseInt((artikeltable.getValueAt(ausgabeTabelle.getSelectedRow(),0)).toString());
						try {
							gui.ausWarenkorbentfernen(artikelnummer);
							JOptionPane.showMessageDialog(null,"Artikel wurde aus dem Warenkorb entfernt");
						} catch (ArtikelExistiertNichtException | BestandUeberschrittenException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} catch (NumberFormatException e1) {
						e1.printStackTrace();
					}    				
		    };
		};
		//Layout Tabelle -> Button fuer "Option"
		ausgabeTabelle.getColumn("Option").setCellRenderer(new ButtonRenderer());
		ausgabeTabelle.getColumn("Option").setCellEditor(new ButtonEditor(new JCheckBox(),gui, listen));	
		
	}

	public void wieOftArtikelZumWarenKorb(){}

	public void actionPerformed(ActionEvent arg0) {
		System.out.println("Warenkorb Aktion ausgefuehrt");	
	}	
	
	public JTable getAusgabeTabelle() {
		return ausgabeTabelle;
	}
	
	public void setAusgabeTabelle(JTable ausgabeTabelle) {
		this.ausgabeTabelle = ausgabeTabelle;
	}

	public void updateData(Warenkorb warenkorb) {
		artikeltable.setDataVector2(warenkorb,"Entfernen");
		renderOption();	
	}
}