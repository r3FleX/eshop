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
import ui.GUI_2;
import valueobjects.Account;
import valueobjects.Kunde;

public class UserPanel extends JPanel{
	
	private GUI_2 gui;
	public JButton statistikButton = new JButton("Statistik",new ImageIcon("src/assets/statistikIcon.png"));
	public JButton artikelHinzufuegenButton = new JButton("Artikel hinzufuegen", new ImageIcon("src/assets/artikelHinzufuegenIcon.png"));
	
	//Konstruktor
	public UserPanel(GUI_2 gui) {
		
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
					
					JLabel platzhalter = new JLabel("");
					artikelHinzufuegenFrame.add(platzhalter);

					JButton hinzufuegenButton = new JButton("hinzufuegen");
					artikelHinzufuegenFrame.add(hinzufuegenButton);
					artikelHinzufuegenFrame.setVisible(true);
					
					
					hinzufuegenButton.addActionListener(new ActionListener() { 
				
						public void actionPerformed(ActionEvent arg0) {
							
							//hole ..
							String artikelname = artikelnameFeld.getText();
							String artikelnummer = artikelnummerFeld.getText();
							int preis = Integer.parseInt(preisFeld.getText());
							String bestand = bestandFeld.getText();				
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