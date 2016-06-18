package ui.panel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
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
import valueobjects.Rechnung;
import valueobjects.Warenkorb;

public class WarenkorbButtonPanel extends JPanel{
	
	private GUI_2 gui;
	
	public JButton zumWarenKorbButton = new JButton("zum Warenkorb",new ImageIcon("src/assets/warenkorbIcon.png"));
	private JButton inWarenKorbLegenButton = new JButton("in Warenkorb legen",new ImageIcon("src/assets/inWarenkorbLegenIcon.png"));
	public JButton kaufAbschliessenButton = new JButton("Kauf abschliessen");	
	public JButton zumShop = new JButton("zurueck zum Shop");
	//public JButton inDenWarenkorbButton = new JButton("In den Warenkorb");	
	
	private JLabel wieOftArtikelKaufenLabel = new JLabel("Wie oft wollen Sie den Artikel kaufen?");
	private JFrame wieOftArtikelKaufenFrame = new JFrame();
	private JTextField anzahl = new JTextField();
	
	//Konstruktor
	public WarenkorbButtonPanel(GUI_2 gui) {
		
		this.gui = gui;
		
	
		/*
		//Fuer Warenkorb Button "zum Warenkorb"
		//ACTIONLISTINER
		zumWarenKorbButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				System.out.println("Warenkorb ge�ffnet ausgefuehrt");	
				gui.zumWarenKorb();
			}
		});	
		
		//Fuer Warenkorb Button "in Warenkorb legen" 
		//ACTIONLISTINER 
		inWarenKorbLegenButton.addActionListener(new ActionListener() {	
			public void actionPerformed(ActionEvent e) {
				System.out.println("in den warenkorb");
				//TODO public class WarenkorbButton -> warenkorbpanel.wieOftArtikelZumWarenKorb();
				//TODO: warenkorbPanel ist null
				try {			
					wieOftArtikelKaufenFrame.setLayout(new GridLayout(2, 1));
					wieOftArtikelKaufenFrame.setSize(450, 100);
					wieOftArtikelKaufenFrame.add(wieOftArtikelKaufenLabel);
					wieOftArtikelKaufenFrame.add(anzahl);
					wieOftArtikelKaufenFrame.add(inDenWarenkorbButton);
					wieOftArtikelKaufenFrame.setVisible(true);

					inDenWarenkorbButton.addActionListener(new ActionListener() {	
						public void actionPerformed(ActionEvent arg1) {
							try {
								gui.zumWarenkorbHinzufuegen(Integer.parseInt(anzahl.getText()));
								
								wieOftArtikelKaufenFrame.setVisible(false);
							} catch (NumberFormatException e) {
								e.printStackTrace();
							} catch (BestandUeberschrittenException e) {
								JOptionPane.showMessageDialog(null, e.getMessage());
							} catch (ArtikelExistiertNichtException e) {
								JOptionPane.showMessageDialog(null, e.getMessage());
							}
						}
					});
				} catch (NumberFormatException e1) {
					e1.printStackTrace();
				}
			}
		});
		*/
		//Fuer Warenkorb Button "zum Shop"
		//ACTIONLISTINER
		zumShop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				System.out.println("zum Shop ausgefuehrt");	
				gui.zumShopButton();
			}
		});	
		
		//Fuer Warenkorb Button "Kauf abschlie�en"	
		//ACTIONLISTINER
		kaufAbschliessenButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				System.out.println("Kauf Abschlie� Button");
				//JOptionPane.showMessageDialog(null,"bitte einloggen!");
				//rechnungspanel.actionPerformed(arg0);
				//pr�fen ob User eingelogt?
				Account user = gui.getUser();
				//JOptionPane.showMessageDialog(null,"bitte einloggen!");
				if(!(user.getAccountNr() == -1)){
					int jaNein = JOptionPane.showConfirmDialog(null,"Bestellung abschliessen?");
					if (jaNein == 0) {
						
						Shopverwaltung shop = gui.getShop();
						HashMap<Artikel, Integer> fehlerliste = shop .pruefeKauf((Kunde) user);
						if (!fehlerliste.isEmpty()) {
							JOptionPane.showMessageDialog(null,"Es konnten nicht alle Artikel zum Kauf angeboten werden.");				
							} else {
								RechnungsPanel rechnung = new RechnungsPanel(gui);			
						}//Ende else (!fehlerliste.isEmpty())
					}//if (jaNein == 0)
					
					else if (user.getName()== "Gast") {
						System.out.println("Gast konto");
						//user muss sich erst einloggen 	
					}
				}else{
					JOptionPane.showMessageDialog(null,"bitte einloggen!");
						
				}//Ende else if(user instanceof Kunde)
			}//Ende public void actionPerformed(ActionEvent e)
		});//Endekauf AbschliessenButton.addActionListener(new ActionListener()
	}
	public JButton getInWarenKorbLegenButton() {
		return inWarenKorbLegenButton;
	}

	public void setInWarenKorbLegenButton(JButton inWarenKorbLegenButton) {
		this.inWarenKorbLegenButton = inWarenKorbLegenButton;
	}
	
	public String createInWarenkorbLegenButtonTable() {
		return "Buy it";
	}	
}

