package ui.module;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import domain.Shopverwaltung;
import ui.GUI_2;
import ui.controller.SuchController;
import valueobjects.Artikel;

public class SuchPanel {

	private JPanel suchPanel;
	private JPanel warenkorbPanel;
	private List artikelListe;
	private JTextField suchenTextField;
	private SuchController suchController;

	//Konstruktor
	public SuchPanel(SuchController suchController) {
		this.suchController = suchController;
		initialize();
	}

	private void initialize() {
		
		JPanel suchPanel = new JPanel();
		suchPanel.setLayout(new GridLayout(1, 4));

		suchenTextField = new JTextField();
		suchPanel.add(suchenTextField);

		JButton suchButton = new JButton("Suchen");
		suchPanel.add(suchButton);
		suchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				suchen(suchenTextField.getText());
			}
		});

		suchPanel.add(new JLabel()); //Platzhalter

		JButton inWarenKorbLegenButton = new JButton("in Warenkorb legen");
		suchPanel.add(inWarenKorbLegenButton);
		inWarenKorbLegenButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});

		JButton zumWarenKorbButton = new JButton("zum Warenkorb");
		suchPanel.add(zumWarenKorbButton);
		zumWarenKorbButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});

		suchPanel.setBorder(BorderFactory.createTitledBorder("Shop")); //Ueberschrift Suchen
		setSuchPanel(suchPanel);
	}

	private void suchen(String text) {
		suchController.suchen(text);
	}

	//Getter und Setter
	public JPanel getSuchPanel() {
		return suchPanel;
	}

	public void setSuchPanel(JPanel suchPanel) {
		this.suchPanel = suchPanel;
	}
	
//	//ACTIONSLISTENER
//	public void actionPerformed(ActionEvent arg0) {
//		System.out.println("Test Suchen");
//		ArtikelPanel artikelPanel;
//		String suche = suchenTextField.getText();
//		java.util.List<Artikel> suchErgebnis;
//		System.out.println(suche);
//
//		if (suche.isEmpty()) {
//			suchErgebnis = this.shop.gibAlleArtikel();
//		} else {
//			suchErgebnis = shop.sucheNachArtikelNummer(suche);
//		}
//		// TODO: keine ArtikelPanel
//		//funktioniert noch nicht. Gibt keine gesuchte Artikel wieder
//		//artikelListe.removeAll(artikelListe);
//		gui.setArtikelPanel(new ArtikelPanel(suchErgebnis));
//	}
}

