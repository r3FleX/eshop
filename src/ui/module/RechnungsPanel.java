package ui.module;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import domain.Shopverwaltung;
import ui.ArtikelTableModel;
import ui.GUI_2;
import valueobjects.Account;
import valueobjects.Artikel;
import valueobjects.Kunde;
import valueobjects.Rechnung;


public class RechnungsPanel {
	private GUI_2 gui;
	private Shopverwaltung shop;
	private Account user;
	private float gesamtpreis = 0.0f;
	private JLabel gesamt = new JLabel();
	private JTable ausgabeTabelle = null;
	private JTable warenkorbTabelle = null;
	
	//Konstruktor
	public RechnungsPanel(GUI_2 gui) {
		
		this.gui = gui;
	}	
	
		
	//ACTIONLISTENER
	/*
	public void actionPerformed(ActionEvent arg0){
		String command = arg0.getActionCommand();

		if (command.equals("Bestellung abschliessen")) {
			int jaNein = JOptionPane.showConfirmDialog(null,"Bestellung abschliessen?");
	
			if (jaNein == 0) {
				HashMap<Artikel, Integer> fehlerliste = shop.pruefeKauf((Kunde) user);
				if (!fehlerliste.isEmpty()) {
					JOptionPane.showMessageDialog(null,"Es konnten nicht alle Artikel zum Kauf angeboten werden.");
				} else {
					Rechnung rechnung = null;
	
					try {
						rechnung = shop.kaufAbwickeln((Kunde) user);
					}
					catch (IOException e1) {
					}
	
					final JFrame rechnungFenster = new JFrame();
					rechnungFenster.setTitle("Rechnung");
					rechnungFenster.setSize(500, 500);
					rechnungFenster.getContentPane().setLayout(new GridLayout(3, 1));
	
					JPanel oben = new JPanel();
					JPanel unten = new JPanel();
					JPanel ganz_unten = new JPanel();
	
					JPanel links_oben = new JPanel();
					JPanel links_unten = new JPanel();
	
					JLabel datum = new JLabel("Kaufdatum: "+ rechnung.getDatum());
					JLabel kaeufer = new JLabel(user.getName() + " ");
					JLabel strasse = new JLabel(((Kunde) user).getStrasse());
					JLabel plzUndOrt = new JLabel(((Kunde) user).getPlz() + " "+ ((Kunde) user).getWohnort());
	
					rechnungFenster.getContentPane().add(oben);
					rechnungFenster.getContentPane().add(unten);
					rechnungFenster.getContentPane().add(ganz_unten);
					rechnungFenster.setVisible(true);
	
					oben.setLayout(new GridLayout(1, 1));
	
					unten.setLayout(new GridLayout(2, 1));
	
					oben.add(links_oben);
					oben.add(links_unten);
	
					// oben kommt das Datum und die Adresse des Kunden hin
	
					links_oben.setLayout(new GridLayout(4, 1));
					links_oben.setBorder(BorderFactory.createTitledBorder("Kundendaten"));
					links_oben.add(datum);
					links_oben.add(kaeufer);
					links_oben.add(strasse);
					links_oben.add(plzUndOrt);
	
					// unten kommen die gekauften Artikel hin
	
					unten.setBorder(BorderFactory.createTitledBorder("Gekaufte Artikel"));
	
					java.util.Set<Artikel> gekauft;
					Kunde kunde = (Kunde) user;
					gekauft = kunde.getWarenkorb().getInhalt().keySet();
	
					gesamtpreis = rechnung.getGesamtpreis();
					gesamt.setText("Gesamtpreis: " + gesamtpreis + "Ä");
	
					JScrollPane scrollPane2 = new JScrollPane();
					// JScrollBar scrollbar = new JScrollBar();
	
					Vector spalten2 = new Vector();
					spalten2.add("Nummer");
					spalten2.add("Name");
					spalten2.add("Anzahl");
					spalten2.add("Preis");
					
					// TableModel als "Datencontainer" anlegen:
					ArtikelTableModel tModel2 = new ArtikelTableModel(new Vector<Artikel>(), spalten2);
	
					tModel2 = (ArtikelTableModel) warenkorbTabelle.getModel();
	
					// JTable-Objekt erzeugen und mit Datenmodell initialisieren:
					warenkorbTabelle = new JTable(tModel2);
					
					// JTable in ScrollPane platzieren:
					scrollPane2 = new JScrollPane(warenkorbTabelle);
	
					unten.add(scrollPane2);
	
					//Fuer Button Schliessen
					JButton schliessen = new JButton("Schliessen");
	
					ganz_unten.setLayout(new GridLayout(4, 1));
					ganz_unten.add(gesamt);
					ganz_unten.add(schliessen);
					
					//Fuer schliessen Button
					schliessen.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent actionEv) {
							rechnungFenster.setVisible(false);
						}
					});//Ende schliessen Button						
				}//Ende else
			}//Ende if (jaNein == 0)
		}//Ende if (command.equals("Bestellung abschliessen"))
	}//Ende public void actionPerformed(ActionEvent arg0)
	*/
}//Ende RechnungsPanel
