package ui.module;

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
	

	private JTable ausgabeTabelle = null;
	private JTable warenkorbTabelle = null;
	private JLabel gesamt = new JLabel();
	private GUI_2 gui;
	private Shopverwaltung shop;
	private RechnungsPanel rechnungspanel;
	
	private Account user;
	private float gesamtpreis = 0.0f;
	
	public JButton zumWarenKorbButton = new JButton("zum Warenkorb",new ImageIcon("src/assets/warenkorbIcon.png"));
	private JButton inWarenKorbLegenButton = new JButton("in Warenkorb legen",new ImageIcon("src/assets/inWarenkorbLegenIcon.png"));
	public JButton kaufAbschliessenButton = new JButton("Kauf abschliessen");	
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
				
				System.out.println("Warenkorb geöffnet ausgefuehrt");	
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
		
		//Fuer Warenkorb Button "Kauf abschließen"	
		//ACTIONLISTINER
		kaufAbschliessenButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				System.out.println("Kauf Abschließ Button");
				//JOptionPane.showMessageDialog(null,"bitte einloggen!");
				//rechnungspanel.actionPerformed(arg0);
				//prüfen ob User eingelogt?
				Account user = gui.getUser();
				//JOptionPane.showMessageDialog(null,"bitte einloggen!");
				if(!(user.getAccountNr() == -1)){
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
							gesamt.setText("Gesamtpreis: " + gesamtpreis + "€");
			
							JScrollPane scrollPane2 = new JScrollPane();
							// JScrollBar scrollbar = new JScrollBar();
			
							Vector spalten2 = new Vector();
							spalten2.add("Nummer");
							spalten2.add("Name");
							spalten2.add("Anzahl");
							spalten2.add("Preis");
							
							// TableModel als "Datencontainer" anlegen:
							ArtikelTableModel tModel2 = new ArtikelTableModel();
			
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

