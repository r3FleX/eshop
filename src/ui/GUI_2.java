package ui;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
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
import ui.controller.SuchController;
import ui.module.ArtikelPanel;
import ui.module.LoginPanel;
import ui.module.MenuePanel;
import ui.module.SuchPanel;
import ui.module.UserPanel;
import ui.module.WarenkorbButtonPanel;
import ui.module.WarenkorbPanel;
import valueobjects.Account;
import valueobjects.Kunde;
import valueobjects.Mitarbeiter;
import valueobjects.Warenkorb;

public class GUI_2 extends JFrame{

	private static final long serialVersionUID = 1L;
	
	private SuchController suchController = null;
	private Shopverwaltung shop;
	private Account user = (Account) new Kunde("Gast", "gast", -1, "none", 12345, "none");
	
	//Menuebar
	private MenuePanel menuBar;
	
	//LayoutPanel
	private JPanel navframe = new JPanel();		
	private JPanel contentframe = new JPanel();	
	private JPanel mainPanel = new JPanel();
	
	private JPanel untenWarenKorbBereichPanel = new JPanel();
	//private JPanel warenkorbPanel = new JPanel();
	//private JPanel warenKorbBereichPanel = new JPanel();
	
	private JPanel obenPanel = new JPanel();
	private UserPanel userpanel;
	private SuchPanel suchPanel;
	private ArtikelPanel artikelPanel;
	private WarenkorbButtonPanel warenKorbButtons;
	private WarenkorbPanel warenkorbPanel;
	

	public WarenkorbPanel getWarenkorbPanel() {
		return warenkorbPanel;
	}

	public void setWarenkorbPanel(WarenkorbPanel warenkorbPanel) {
		this.warenkorbPanel = warenkorbPanel;
	}

	//Konstrukter
	public GUI_2(String datei) {
		try {
			shop = new Shopverwaltung(datei);
			menuBar = new MenuePanel(this, shop, user);			
			this.suchController = new SuchController(this, shop); //TODO: Eklig, da die ShopVerwaltung gefaehrliche Sachen im Konstruktor macht.
		} catch (IOException e) {
			e.printStackTrace();
		}
		setTitle("E-Shop");
		setSize(800, 600); //Fenstergroesse
		//setResizable(false);
		this.initialize();
	}	
	
	//Getter und Setter
	public ArtikelPanel getArtikelPanel() {
		return artikelPanel;
	}
	public void setArtikelPanel(ArtikelPanel artikelPanel) {
		this.artikelPanel = artikelPanel;
	}
	public Shopverwaltung getShop() {
		return shop;
	}
	
	public Account getUser() {
		return user;
	}

	public void setUser(Account user) {
		this.user = user;
	}
	
	//initialisieren
	private void initialize() {
		
		//beendet das Programm durch klicken auf [X]
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.mainPanel.setLayout(new BorderLayout());
		this.navframe.setLayout(new BorderLayout());
		this.contentframe.setLayout(new BorderLayout());
		
		//Warenkorb erstellen.
		warenkorbPanel = new WarenkorbPanel(this);	
		
		//Warenkorb Schaltflarchen
		warenKorbButtons = new WarenkorbButtonPanel(shop, this);
		
		//SuchPanel
		suchPanelSetzen();
		
		//Oben Panel
		this.contentframe.add(obenPanel, BorderLayout.NORTH);
		
		//"norden splitten"
		obenPanelSetzen();

		//LoginPanel
		userPanelSetzen();
			
		//ArtikelPanel
		artikelPanelSetzen();
		
		//GUI setzen
		this.mainPanel.add(this.navframe,BorderLayout.NORTH);
		this.mainPanel.add(this.contentframe,BorderLayout.CENTER);	
		add(this.mainPanel);
		setVisible(true);
	}
	
	//ObenPanel
	public void obenPanelSetzen(){
		obenPanel.setLayout(new GridLayout(1,1));
		obenPanel.add(suchPanel);
		obenPanel.add(warenKorbButtons.zumWarenKorbButton);
		obenPanel.add(warenKorbButtons.getInWarenKorbLegenButton());
	}
	
	//LoginPanel
	public void userPanelSetzen(){
		userpanel = new UserPanel(this);
		userpanel.setLayout(new GridLayout(1, 3));
		userpanel.setVisible(false);
		this.navframe.add(userpanel, BorderLayout.NORTH);	
		setJMenuBar(menuBar.getMenue());
	}
	
	//SuchPanel
	public void suchPanelSetzen(){
		suchPanel = new SuchPanel(suchController, this);
		this.obenPanel.add(suchPanel, BorderLayout.NORTH);	
	}
	
	//ArtikelPanel
	public void artikelPanelSetzen(){
		artikelPanel = new ArtikelPanel(shop.gibAlleArtikel());
		this.contentframe.add(artikelPanel, BorderLayout.CENTER);	
	}
		
	//Wenn Benutzer eingeloggt
	public void userLoggedIn(Account user) {
		//menuebar anpassen 
		menuBar.setUserLoggedIn(true);
		
	//TODO Fragen nach eleganterer lösung
		//GUI user ersetzen
		Kunde guest = (Kunde) this.user;
		Warenkorb tmpWarenkorb = guest.getWarenkorb();
		this.user = user;
		//warenkorb übernehmen
		//guest wird zum aktuellen "kunden"
		guest = (Kunde) this.user;
		guest.setWarenkorb(tmpWarenkorb);
		//und zurück
		this.user = guest;
		//userpanel einblenden
		if (user instanceof Kunde) {
			userpanel.setVisible(true); //Panel einblenden
			System.out.println("Kunde " + user.getName() + " ist eingeloggt.");
			userpanel.setBorder(BorderFactory.createTitledBorder("Kundenbereich  -  Herzlich Willkommen: "+user.getName()+" !")); //Ueberschrift Kunden Login
		}
		else if(user instanceof Mitarbeiter) {
			userpanel.setVisible(true); //Panel einblenden
			System.out.println("Mitarbeiter " + user.getName() + " ist eingeloggt.");		
			userpanel.setBorder(BorderFactory.createTitledBorder("Mitarbeiterbereich  -  Herzlich Willkommen: "+user.getName()+" !")); //Ueberschrift Mitarbeiter Login
		}
	}
	
	//Wenn Benutzer ausgeloggt
	public void userLoggedOut(){
		userpanel.setVisible(false);
		//Gastkonto "reaktivieren"
		this.user = (Account) new Kunde("Gast", "gast", -1, "none", 12345, "none");
	}
	
	//warenkorb anzeigen
	public void zumWarenKorb(){
		//Hinzufuegen unten Warenkorb Panel
		this.contentframe.add(untenWarenKorbBereichPanel, BorderLayout.SOUTH);
		untenWarenKorbBereichPanel.add(warenKorbButtons.kaufAbschliessenButton);
		untenWarenKorbBereichPanel.setBorder(BorderFactory.createTitledBorder("Kaufabwicklungsbereich")); //Ueberschrift Kaufabwicklungsbereich
		untenWarenKorbBereichPanel.setVisible(true);
		
		//loescht Artikel Panel
		contentframe.remove(artikelPanel);
		
		//Hinzufuegen hinzugefuegte Artikel Panel
		this.contentframe.add(warenkorbPanel);
		
		obenPanel.remove(suchPanel);
		contentframe.remove(suchPanel);
		// TODO: obenPanel global machen
		
		obenPanel.remove(warenKorbButtons.zumWarenKorbButton);
		obenPanel.remove(warenKorbButtons.getInWarenKorbLegenButton());
		obenPanel.add(warenKorbButtons.zumShop);
	
		refresh();
	}
	
	public void zumShopButton(){
		obenPanel.remove(warenKorbButtons.zumShop);
		contentframe.remove(warenkorbPanel);
		contentframe.remove(untenWarenKorbBereichPanel);
		untenWarenKorbBereichPanel.remove(warenKorbButtons.kaufAbschliessenButton);
		
		initialize();
		refresh();
	}
	
	public void untenWarenKorbBereichPanel(boolean b){
		untenWarenKorbBereichPanel.setVisible(b);
	}

	//refresht alle Panels
	public void refresh(){

		mainPanel.revalidate();
		contentframe.revalidate();
		navframe.revalidate();
		menuBar.revalidate();
		
		mainPanel.repaint();
		contentframe.repaint();
		navframe.repaint();
		menuBar.repaint();
	}
}