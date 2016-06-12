package ui.module;

import java.awt.BorderLayout;
import java.awt.Component;
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

public class WarenkorbButtonPanel extends JPanel{
	
	//private JTable warenkorbTabelle = null;
	private JTable ausgabeTabelle = null;
	private GUI_2 gui;
	private Shopverwaltung shop;
	
	public JButton zumWarenKorbButton = new JButton("zum Warenkorb",new ImageIcon("src/assets/warenkorbIcon.png"));
	private JButton inWarenKorbLegenButton = new JButton("in Warenkorb legen",new ImageIcon("src/assets/inWarenkorbLegenIcon.png"));
	public JButton kaufAbschliessenButton = new JButton("Kauf abschlieﬂen");	
	public JButton zumShop = new JButton("zurueck zum Shop");
	public JButton inDenWarenkorbButton = new JButton("In den Warenkorb");	
	
	private JLabel wieOftArtikelKaufenLabel = new JLabel("Wie oft wollen Sie den Artikel kaufen?");
	private JFrame wieOftArtikelKaufenFrame = new JFrame();
	private JTextField anzahl = new JTextField();
	
	//Konstruktor
	public WarenkorbButtonPanel(Shopverwaltung shop, GUI_2 gui) {
		
		this.shop = shop;
		this.gui = gui;
		
		//Fuer Warenkorb Button "zum Warenkorb"
		//ACTIONLISTINER
		zumWarenKorbButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				System.out.println("Warenkorb geˆffnet ausgefuehrt");	
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
							try {  //inWarenkorbEinfuegen(Artikel art, int anzahl, Kunde kunde)
								Artikel art = shop.artikelSuchen(Integer.parseInt((gui.getArtikelPanel().getArtikeltable().getValueAt(gui.getArtikelPanel().getAusgabeTabelle().getSelectedRow(),0)).toString()));
								shop.inWarenkorbEinfuegen(art,Integer.parseInt(anzahl.getText()),(Kunde) gui.getUser());
								//aktuallisere Warenkorb
								Kunde user = (Kunde) gui.getUser();
						        gui.getWarenkorbPanel().getArtikeltable().setDataVector2(user.getWarenkorb());
						//        gui.getArtikelPanel().renderOption();
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
		
		//Fuer Warenkorb Button "zum Shop"
		//ACTIONLISTINER
		zumShop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				System.out.println("zum Shop ausgefuehrt");	
				gui.zumShopButton();
			}
		});	
		
		//Fuer Warenkorb Button "Kauf abschlieﬂen"	
		//ACTIONLISTINER
		kaufAbschliessenButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				System.out.println("Kauf Abschlieﬂ Button");	
			}
		});	
	}//ENDE KONSTRUKTOR
	
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

