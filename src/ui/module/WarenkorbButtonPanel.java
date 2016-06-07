package ui.module;

import java.awt.BorderLayout;
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
import valueobjects.Warenkorb;

public class WarenkorbButtonPanel extends JPanel implements ActionListener {
	
	//private JButton zumWarenKorbButton;
	//private JTable warenkorbTabelle = null;
	private JTable ausgabeTabelle = null;
	//private JLabel gesamt = new JLabel();
	private GUI_2 gui;
	private Shopverwaltung shop;
	private Account user;
	//private WarenkorbPanel warenkorbpanel;
	
	//Konstruktor
	public WarenkorbButtonPanel(GUI_2 gui) {
		this.gui = gui;
		shop = gui.getShop();
	}
	public WarenkorbButtonPanel() {
		
	}
	public String createInWarenkorbLegenButtonTable() {
		return "Buy it";
	}
	
	public JButton createInWarenkorbLegenButton() {
		
	//Warenkorb Button "in Warenkorb legen" 
		JButton inWarenKorbLegenButton = new JButton("in Warenkorb legen",new ImageIcon("src/assets/inWarenkorbLegenIcon.png"));
		//zumWarenKorbButtonPanel.add(inWarenKorbLegenButton);
		//
		
		//ACTIONLISTINER
		inWarenKorbLegenButton.addActionListener(new ActionListener() {	
			public void actionPerformed(ActionEvent e) {
			//public void wieOftArtikelKaufenMethode(){}	
				System.out.println("in den warenkorb");
				//TODO public class WarenkorbButton -> warenkorbpanel.wieOftArtikelZumWarenKorb();
				//TODO: warenkorbPanel ist null
				//warenkorbpanel.wieOftArtikelZumWarenKorb();	
				try {
					JLabel wieOftArtikelKaufenLabel = new JLabel("Wie oft wollen Sie den Artikel kaufen?");
					final JTextField anzahl = new JTextField();
					JButton inDenWarenkorbButton = new JButton("In den Warenkorb");

					final JFrame wieOftArtikelKaufenFrame = new JFrame();
					wieOftArtikelKaufenFrame.getContentPane().setLayout(new GridLayout(2, 1));
					wieOftArtikelKaufenFrame.setSize(450, 100);
					wieOftArtikelKaufenFrame.getContentPane().add(wieOftArtikelKaufenLabel);
					wieOftArtikelKaufenFrame.getContentPane().add(anzahl);
					wieOftArtikelKaufenFrame.getContentPane().add(inDenWarenkorbButton);
					wieOftArtikelKaufenFrame.setVisible(true);
					
					inDenWarenkorbButton.addActionListener(new ActionListener() {

						public void actionPerformed(ActionEvent arg0) {
							try {  //inWarenkorbEinfuegen(Artikel art,                ->                                                                                  ,int anzahl                       , Kunde kunde)	
								shop.inWarenkorbEinfuegen(shop.artikelSuchen(Integer.parseInt((ausgabeTabelle.getValueAt(ausgabeTabelle.getSelectedRow(),0)).toString())),Integer.parseInt(anzahl.getText()),(Kunde) user);
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
		return inWarenKorbLegenButton;	
	}
	
	//Zum Warenkorb Button
	public JButton getZumWarenkorbButton(GUI_2 gui) {
		
		this.gui = gui;

		//Warenkorb Button "zum Warenkorb"
		JButton zumWarenKorbButton = new JButton("zum Warenkorb",new ImageIcon("src/assets/warenkorbIcon.png"));
		
		//ACTIONLISTINER
		zumWarenKorbButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				System.out.println("Warenkorb geöffnet ausgefuehrt");	
				gui.zumWarenKorb();
				gui.mittigWarenKorbBereich();
			}
		});	
		return zumWarenKorbButton;
	}	
	
	// getZumWarenKorbPanel
	public JButton getKaufAbschliessenButton(){
		
		JButton kaufAbschliessenButton = new JButton("Kauf abschließen");
	
		//ACTIONLISTINER
		kaufAbschliessenButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				System.out.println("Kauf Abschließ Button");	
			}
		});	
		return kaufAbschliessenButton;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		System.out.println("Warenkorb Aktion ausgefuehrt");	
	}			
}

