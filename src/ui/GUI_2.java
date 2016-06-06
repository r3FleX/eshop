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
import ui.module.WarenkorbButton;
import ui.module.WarenkorbPanel;
import valueobjects.Account;
import valueobjects.Kunde;
import valueobjects.Mitarbeiter;
import valueobjects.Warenkorb;

public class GUI_2 extends JFrame {

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
	private SuchPanel suchPanel = new SuchPanel(suchController, this);
	private JPanel obenPanel = new JPanel();
	private JPanel untenWarenKorbBereichPanel = new JPanel();
	private JPanel hinzugefuegteArtikelPanel = new JPanel();
	private JPanel warenKorbBereichPanel = new JPanel();
	private ArtikelPanel artikelPanel;
	private UserPanel userpanel;
	
	private WarenkorbButton WarenKorbButtons;
	private WarenkorbPanel warenkorb;
	

	//Konstrukter
	public GUI_2(String datei) {
		try {
			this.suchController = new SuchController(this, new Shopverwaltung(datei)); //TODO: Eklig, da die ShopVerwaltung gefaehrliche Sachen im Konstruktor macht.
		} catch (IOException e) {
			e.printStackTrace();
		}
		setTitle("E-Shop");
		setSize(800, 600); //Fenstergroesse
		//setResizable(false);
		
		try {
			shop = new Shopverwaltung(datei);
			menuBar = new MenuePanel(this, shop, user);
		} catch (IOException e2) {

		}
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
	
	public WarenkorbPanel getWarenkorb() {
		return warenkorb;
	}

	public void setWarenkorb(WarenkorbPanel warenkorb) {
		this.warenkorb = warenkorb;
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
		
		//Warenkorb schaltflächen
		WarenKorbButtons = new WarenkorbButton();
		//Warenkorb erstellen.
		warenkorb = new WarenkorbPanel(this,user);	
		
		//SuchPanel
		SuchPanel suchPanel = new SuchPanel(suchController, this);
		this.contentframe.add(suchPanel.getSuchPanel(), BorderLayout.NORTH);	
		//menuBar.setSuchPanel(suchPanel);
		
		//"norden splitten"
		obenPanel.setLayout(new GridLayout(1,1));
		obenPanel.add(suchPanel.getSuchPanel());
		obenPanel.add(WarenKorbButtons.getZumWarenkorbButton(this));
		obenPanel.add(WarenKorbButtons.createInWarenkorbLegenButton());
		
		this.contentframe.add(obenPanel, BorderLayout.NORTH);
	//TODO das gehört in den warenkorbPanel!!!
	//TODO Navigationsleiste für Warenkorb
		
		//Unten WarenKorb Panel
		untenWarenKorbBereichPanel.add(WarenKorbButtons.getKaufAbschliessenButton());
		this.contentframe.add(untenWarenKorbBereichPanel, BorderLayout.SOUTH);
		untenWarenKorbBereichPanel.setBorder(BorderFactory.createTitledBorder("Kaufabwicklungsbereich")); //Ueberschrift Kaufabwicklungsbereich
		untenWarenKorbBereichPanel.setVisible(false);
		
		//Test Panel fuer Zum WarenKorb 
		JPanel warenKorbBereichPanel = new JPanel();
		warenKorbBereichPanel.setLayout(new GridLayout(1,1));
		//warenKorbBereichPanel.add(warenKorbBereichPanel);
		contentframe.add(warenKorbBereichPanel);
		JButton warenKorbBereichButton = new JButton();
		warenKorbBereichPanel.setBorder(BorderFactory.createTitledBorder("WarenKorbBereich")); //Ueberschrift WarenKorbBereich
		warenKorbBereichPanel.setVisible(false);
		
		//Hinzugefuegte Artikel Panel
		this.contentframe.add(hinzugefuegteArtikelPanel);
		hinzugefuegteArtikelPanel.setLayout(new GridLayout(1, 1));
		hinzugefuegteArtikelPanel.setBorder(BorderFactory.createTitledBorder("Hinzugefuegte Artikel")); //Ueberschrift Hinzugefuegte Artikel
		hinzugefuegteArtikelPanel.setVisible(true);
		
		//LoginPanel
		userpanel = new UserPanel(this, shop, user);
		userpanel.setLayout(new GridLayout(1, 3));
		userpanel.setVisible(false);
		this.navframe.add(userpanel, BorderLayout.NORTH);	
		setJMenuBar(menuBar.getMenue());	
		
		//ArtikelPanel
		artikelPanel = new ArtikelPanel(shop.gibAlleArtikel());
		this.contentframe.add(artikelPanel.getArtikelPanel(), BorderLayout.CENTER);	
		
		//GUI setzen
		this.mainPanel.add(this.navframe,BorderLayout.NORTH);
		this.mainPanel.add(this.contentframe,BorderLayout.CENTER);	
		add(this.mainPanel);
		setVisible(true);
	}
		
	//Wenn Benutzer eingeloggt
	public void userLoggedIn(Account user) {
		//menübar anpassen 
		menuBar.setUserLoggedIn(true);
		//userpanel einblenden
		if (user instanceof Kunde) {
			userpanel.setVisible(true); //Panel einblenden
			System.out.println("Kunde " + user.getName() + " ist eingeloggt.");
			userpanel.setwarenkorbvisible();
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
	}
	
	//warenkorb anzeigen
	public void zumWarenKorb(){
		//artikelPanel.getArtikelPanel().setVisible(false);
		untenWarenKorbBereichPanel.setVisible(true);
		//obenPanel Ausblenden/entfernen
		contentframe.remove(obenPanel);
		refresh();
	}
	
	public void artikelPanelEinblenden(boolean b){
		artikelPanel.getArtikelPanel().setVisible(b);
	}
	
	public void untenWarenKorbBereichPanel(boolean b){
		untenWarenKorbBereichPanel.setVisible(b);
	}
	
	public void mittigWarenKorbBereich(){
		warenKorbBereichPanel.setVisible(true);
	}

	//refresht alle Panels
	public void refresh(){
		mainPanel.repaint();
		contentframe.repaint();
		navframe.repaint();
		menuBar.repaint();
		
		mainPanel.revalidate();
		contentframe.revalidate();
		navframe.revalidate();
		menuBar.revalidate();
	}

}