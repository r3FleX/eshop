package ui.module;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import domain.Shopverwaltung;
import domain.exceptions.AccountExistiertBereitsException;
import domain.exceptions.AccountExistiertNichtException;
import domain.exceptions.ArtikelExistiertBereitsException;
import ui.GUI_2;
import valueobjects.Account;
import valueobjects.Artikel;
import valueobjects.Kunde;
import valueobjects.Mitarbeiter;

public class MitarbeiterPanel extends JPanel{
	
	private GUI_2 gui;
	public JButton statistikButton = new JButton("Statistik",new ImageIcon("src/assets/statistikIcon.png"));
	public JButton artikelHinzufuegenButton = new JButton("Artikel hinzufuegen", new ImageIcon("src/assets/artikelHinzufuegenIcon.png"));
	
	//Konstruktor
	public MitarbeiterPanel(GUI_2 gui) {
		
		this.gui = gui;
	
		add(statistikButton);
		add(artikelHinzufuegenButton);
		
		
		artikelHinzufuegenButton.addActionListener(new ActionListener() { 
			
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Artikel hinzufuegen");
				//Fuer Menue Account -> Registrieren Button
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
					artikelHinzufuegenFrame.setVisible(true);
					
					
					hinzufuegenButton.addActionListener(new ActionListener() { 
				
						public void actionPerformed(ActionEvent arg0) {
							
							String artikelname = artikelnameFeld.getText();
							int artikelnummer = Integer.parseInt(artikelnummerFeld.getText());
							int bestand = Integer.parseInt(bestandFeld.getText());
							float preis = Float.parseFloat(preisFeld.getText());
							int packungsgroesse = Integer.parseInt(packungsgroesseFeld.getText());
							
							try {
								
								Shopverwaltung shop = gui.getShop();
								shop.fuegeArtikelEin(artikelname, artikelnummer, bestand, preis, packungsgroesse);
								
								try {
									shop.schreibeArtikeldaten();
									JOptionPane.showMessageDialog(null,"Artikel erfolgreich hinzugefuegt!");
									artikelHinzufuegenFrame.setVisible(false);
								} catch (IOException e) {
									e.printStackTrace();
								}
							} catch (ArtikelExistiertBereitsException ex) {
								JOptionPane.showMessageDialog(null, ex.getMessage());
							}
						}
					});//Ende regButton.addActionListener(new ActionListener()
			}
		});
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
}