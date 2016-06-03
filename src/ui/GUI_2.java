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
import valueobjects.Account;
import valueobjects.Kunde;
import valueobjects.Mitarbeiter;
import valueobjects.Warenkorb;

public class GUI_2 extends JFrame{

	private static final long serialVersionUID = 1L;
	
	private SuchController suchController = null;

	private Shopverwaltung shop;
	private Account user;

	//Menuebar
	MenuePanel menuBar;
	
	//LayoutPanel
	private JPanel navframe = new JPanel();		
	private JPanel contentframe = new JPanel();	
	private JPanel mainPanel = new JPanel();
	private SuchPanel suchPanel = new SuchPanel(suchController);
	private ArtikelPanel artikelPanel;
	private UserPanel userpanel;
	private WarenkorbButton WarenKorbButtons;
	
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
	
	//initialisieren
	private void initialize() {
		
		//schlie�t das Programm durch klicken auf [X]
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.mainPanel.setLayout(new BorderLayout());
		this.navframe.setLayout(new BorderLayout());
		this.contentframe.setLayout(new BorderLayout());	
		//warenkorb schaltfl�chen
		WarenKorbButtons = new WarenkorbButton();
		
		//SuchPanel
		//"norden splitten"
		JPanel suchleiste = new JPanel();
		suchleiste.setLayout(new GridLayout(1,1));
		suchleiste.add(suchPanel.getSuchPanel());
		suchleiste.add(WarenKorbButtons.getZumWarenkorbButton(user));
		
		this.contentframe.add(suchleiste, BorderLayout.NORTH);	
		
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
		//men�bar anpassen 
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
		//menuBar.setUserLoggedOut();
		userpanel.setVisible(false);
		
	}
	
	public void zumWarenKorb(){
		contentframe.setVisible(false);
	}

	//refresht alle Panels
	public void refresh(){
		mainPanel.repaint();
		contentframe.repaint();
		navframe.repaint();
		
		mainPanel.revalidate();
		contentframe.revalidate();
		navframe.revalidate();
	}

}