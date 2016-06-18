package ui.panel;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import domain.Shopverwaltung;
import domain.exceptions.AccountExistiertBereitsException;
import domain.exceptions.AccountExistiertNichtException;
import domain.exceptions.ArtikelExistiertBereitsException;
import domain.exceptions.ArtikelExistiertNichtException;
import domain.exceptions.BestandUeberschrittenException;
import ui.ArtikelTableModel;
import ui.ButtonEditor;
import ui.ButtonRenderer;
import ui.GUI_2;
import valueobjects.Account;
import valueobjects.Artikel;
import valueobjects.Kunde;
import valueobjects.Mitarbeiter;

public class MitarbeiterPanel extends JPanel{
	
	private GUI_2 gui;
	private JPanel artikelPanel = new JPanel();
	private ArtikelTableModel artikeltable;
	private JTable ausgabeTabelle;
	private JScrollPane scrollPane;
	private JButton statistikButton = new JButton("Statistik",new ImageIcon("src/assets/statistikIcon.png"));
	private JButton artikelHinzufuegenButton = new JButton("Artikel hinzufuegen", new ImageIcon("src/assets/artikelHinzufuegenIcon.png"));
	private JButton refreshButton = new JButton("refresh", new ImageIcon("src/assets/refreshIcon.png"));
	
	//Konstruktor
	public MitarbeiterPanel(GUI_2 gui) {
		
		this.gui = gui;

		//Artikelliste f�r Mitarbeiter erstellen
		
		artikelPanel.setLayout(new GridLayout());
		artikelPanel.setBorder(BorderFactory.createTitledBorder("Artikel")); //Ueberschrift Artikel
		
		// TableModel als "Datencontainer" anlegen:
		artikeltable = new ArtikelTableModel(true,true);
		
		// Artikel-Liste aufbereiten
		//artikeltable.setDataVector(new Vector<Artikel>(),"Kaufen");
		
		// JTable-Objekt erzeugen und mit Datenmodell initialisieren:
		ausgabeTabelle = new JTable(artikeltable);
		ausgabeTabelle.setAutoCreateRowSorter(true);

		// JTable in ScrollPane platzieren:
		scrollPane = new JScrollPane(ausgabeTabelle);
				
		// Anzeige der Artikelliste auch in der Kunden-Ansicht
		artikeltable.setDataVector(gui.getShop().gibAlleArtikel(), "Speichern");	
		renderOption();	
		artikelPanel.add(scrollPane);
			
		
		
		add(statistikButton);
		add(artikelHinzufuegenButton);
		add(refreshButton);
		
		hinzufugenPanel(); 
		
		refreshButton.addActionListener(new ActionListener() { 
			
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("refresh");
//TODO artikelliste aktualliseren
			}
		});		
		
	}			
			
	private void renderOption() {
		 ActionListener listen = new ActionListener() {
			    public void actionPerformed(ActionEvent e) {
					try {
						//geaenderte Daten auslesen
						int artikelnummer = Integer.parseInt((artikeltable.getValueAt(ausgabeTabelle.getSelectedRow(),0)).toString());
						String artikelname =  artikeltable.getValueAt(ausgabeTabelle.getSelectedRow(), 1).toString();
						int bestand = Integer.parseInt((artikeltable.getValueAt(ausgabeTabelle.getSelectedRow(),2)).toString());
						float preis = Float.parseFloat((artikeltable.getValueAt(ausgabeTabelle.getSelectedRow(),3)).toString());
						int packungsgroesse = Integer.parseInt((artikeltable.getValueAt(ausgabeTabelle.getSelectedRow(),4)).toString());
						//�nderungen speichern
						gui.aendereArtikel(artikelname,artikelnummer, bestand,preis,packungsgroesse);							
						JOptionPane.showMessageDialog(null,"Artikel�nderungen Gespeichert");
					} catch (NumberFormatException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}    				
		    };
		};
		//Layout Tabelle -> Button fuer "Option"
		ausgabeTabelle.getColumn("Option").setCellRenderer(new ButtonRenderer());
		ausgabeTabelle.getColumn("Option").setCellEditor(new ButtonEditor(new JCheckBox(),gui, listen));		
	}

	//Getter uns Setter
	public JButton getStatistikButton() {
		return statistikButton;
	}

	public void setStatistikButton(JButton statistikButton) {
		this.statistikButton = statistikButton;
	}
	
	public JButton getArtikelHinzufuegenButton() {
		return artikelHinzufuegenButton;
	}

	public void setArtikelHinzufuegenButton(JButton artikelHinzufuegenButton) {
		this.artikelHinzufuegenButton = artikelHinzufuegenButton;
	}
	
	public void hinzufugenPanel() {
		final JFrame artikelHinzufuegenFrame = new JFrame();

		artikelHinzufuegenFrame.setSize(400, 300);
		artikelHinzufuegenFrame.setLayout(new GridLayout(10, 1));

		JLabel artikelname = new JLabel("Artikelname:");
		artikelHinzufuegenFrame.add(artikelname);

		final JTextField artikelnameFeld = new JTextField();
		artikelHinzufuegenFrame.add(artikelnameFeld);

		JLabel artikelnummer = new JLabel("Artikelnummer:");
		artikelHinzufuegenFrame.add(artikelnummer);

		final JTextField artikelnummerFeld = new JTextField();
		artikelHinzufuegenFrame.add(artikelnummerFeld);

		JLabel preis = new JLabel("Preis:");
		artikelHinzufuegenFrame.add(preis);

		final JTextField preisFeld = new JTextField();
		artikelHinzufuegenFrame.add(preisFeld);

		JLabel bestand = new JLabel("Bestand:");
		artikelHinzufuegenFrame.add(bestand);

		final JTextField bestandFeld = new JTextField();
		artikelHinzufuegenFrame.add(bestandFeld);
		
		JLabel packungsgroesse = new JLabel("Packungsgroesse:");
		artikelHinzufuegenFrame.add(packungsgroesse);

		final JTextField packungsgroesseFeld = new JTextField();
		artikelHinzufuegenFrame.add(packungsgroesseFeld);
		
		JLabel platzhalter = new JLabel("");
		artikelHinzufuegenFrame.add(platzhalter);
		
		JButton hinzufuegenButton = new JButton("hinzufuegen");
		artikelHinzufuegenFrame.add(hinzufuegenButton);
		artikelHinzufuegenFrame.setVisible(false);
		artikelHinzufuegenButton.addActionListener(new ActionListener() { 
			
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Artikel hinzufuegen");
				//Fuer Menue Account -> Registrieren Button
				artikelHinzufuegenFrame.setVisible(true);
					
					//Artikel hinzufuegen
					hinzufuegenButton.addActionListener(new ActionListener() { 
				
						public void actionPerformed(ActionEvent arg0) {
							
							String artikelname = artikelnameFeld.getText();
							int artikelnummer = Integer.parseInt(artikelnummerFeld.getText());
							int bestand = Integer.parseInt(bestandFeld.getText());
							float preis = Float.parseFloat(preisFeld.getText());
							int packungsgroesse = Integer.parseInt(packungsgroesseFeld.getText());
							gui.addArtikel(artikelname,artikelnummer, bestand,preis,packungsgroesse);
							artikelHinzufuegenFrame.setVisible(false);	
						}
					});
			}
		});		
	}

	public JPanel getContentframe() {
		return this.artikelPanel;
	}	
}