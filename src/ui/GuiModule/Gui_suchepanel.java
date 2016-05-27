package ui.GuiModule;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import domain.Shopverwaltung;
import ui.ArtikelTableModel;
import ui.GUI_2;
import valueobjects.Artikel;

public class Gui_suchepanel implements ActionListener {
	private GUI_2 gui;
	private Shopverwaltung shop;
	private JPanel suchPanel;
	private JPanel warenkorbPanel;
	private List artikelListe;
	private JTextField suchenTextField;
	
	//Konstruktor
	public Gui_suchepanel(GUI_2 GUI) {
		this.gui = GUI;
		this.shop = GUI.getShop();
		
		JPanel suchPanel = new JPanel();
		suchPanel.setLayout(new GridLayout(1, 4));
		
		suchenTextField = new JTextField();		
		suchPanel.add(suchenTextField);
		
		JButton suchButton = new JButton("Suchen");
		suchPanel.add(suchButton);
		suchButton.addActionListener(this);
		
		suchPanel.add(new JLabel()); //Platzhalter

		JButton inWarenKorbLegenButton = new JButton("in Warenkorb legen");
		suchPanel.add(inWarenKorbLegenButton);
		inWarenKorbLegenButton.addActionListener(this);
		
		JButton zumWarenKorbButton = new JButton("zum Warenkorb");
		suchPanel.add(zumWarenKorbButton);
		zumWarenKorbButton.addActionListener(this);
		
		suchPanel.setBorder(BorderFactory.createTitledBorder("Shop")); //Ueberschrift Suchen
		setSuchPanel(suchPanel);
	}
	
	//Getter und Setter
	public JPanel getSuchPanel() {
		return suchPanel;
	}

	public void setSuchPanel(JPanel suchPanel) {
		this.suchPanel = suchPanel;
	}
	
	//ACTIONSLISTENER
	public void actionPerformed(ActionEvent arg0) {		
		System.out.println("Test Suchen");	
		Gui_artikelpanel artikelPanel;
		String suche = suchenTextField.getText();
		java.util.List<Artikel> suchErgebnis;
		System.out.println(suche);
		
		if (suche.isEmpty()) {
			suchErgebnis = this.shop.gibAlleArtikel();
		} else {
			suchErgebnis = shop.sucheNachArtikelNummer(suche);
		}
		// TODO: keine Gui_artikelpanel
		//funktioniert noch nicht. Gibt keine gesuchte Artikel wieder
		//artikelListe.removeAll(artikelListe);
		gui.setArtikelPanel(new Gui_artikelpanel(suchErgebnis));
	}		
}

