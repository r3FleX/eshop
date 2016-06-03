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

public class GUI_2 extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	private SuchController suchController = null;

	private Shopverwaltung shop;
	JPanel mainPanel = new JPanel();
	
	//Menuebar
	MenuePanel menuBar;
	
	//LayoutPanel
	JPanel navframe = new JPanel();		
	JPanel contentframe = new JPanel();	
	
	private Account user;
	//private List artikelListe = new List();
	//private JTable ausgabeTabelle = null;
	//private JTable warenkorbTabelle = null;
	private JLabel gesamt = new JLabel();
	private ArtikelPanel artikelPanel;
	//private LoginPanel loginPanel;
	private UserPanel userpanel;
	private WarenkorbButton WarenKorbButtons;
	private SuchPanel suchPanel = new SuchPanel(suchController);
	
	//Konstrukter
	public GUI_2(String datei) {
		try {
			this.suchController = new SuchController(this, new Shopverwaltung(datei)); //TODO: Eklig, da die ShopVerwaltung gefaehrliche Sachen im Konstruktor macht.
		} catch (IOException e) {
			e.printStackTrace();
		}
		setTitle("E-Shop");
		setSize(800, 600); //Fenstergroesse
		setResizable(false);
		
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
	
		this.mainPanel.setLayout(new BorderLayout());
		this.navframe.setLayout(new BorderLayout());
		this.contentframe.setLayout(new BorderLayout());	
		//warenkorb schaltflächen
		WarenKorbButtons = new WarenkorbButton();
		
		//SuchPanel
		//"norden splitten"
		JPanel suchleiste = new JPanel();
		suchleiste.setLayout(new GridLayout(1,1));
		suchleiste.add(suchPanel.getSuchPanel());
		suchleiste.add(WarenKorbButtons.getZumWarenkorbButton());
		
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
	
	
	//ACTIONLISTENER
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		/*
		
		//Fuer zum Warenkorb Button
		if (command.equals("zum Warenkorb")) {
			Warenkorb suchErgebnis;
			Kunde kunde = (Kunde) user;

			suchErgebnis = kunde.getWarenkorb();

			ArtikelTableModel tModel = (ArtikelTableModel) warenkorbTabelle.getModel();
			tModel.setDataVector2(suchErgebnis);

			float gesamtpreis = 0.0f;

			gesamt.setText("Gesampreis: " + gesamtpreis + "Euro");
		}
		//Fuer Warenkorb Button
		else if (command.equals("in Warenkorb legen")) {
			try {

				JLabel anz = new JLabel("Wie oft wollen Sie den Artikel kaufen?");
				final JTextField anzahl1 = new JTextField();
				JButton ok = new JButton("In den Warenkorb");

				final JFrame wieViele = new JFrame();
				wieViele.getContentPane().setLayout(new GridLayout(2, 1));
				wieViele.setSize(450, 100);
				wieViele.getContentPane().add(anz);
				wieViele.getContentPane().add(anzahl1);
				wieViele.getContentPane().add(ok);
				wieViele.setVisible(true);
				
				ok.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent arg0) {
						try {
							shop.inWarenkorbEinfuegen(shop.artikelSuchen(Integer.parseInt((ausgabeTabelle.getValueAt(ausgabeTabelle.getSelectedRow(),0)).toString())),Integer.parseInt(anzahl1.getText()),(Kunde) user);
							wieViele.setVisible(false);
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
	  */
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
	public void userLoggedOut() {
		//menuBar.setUserLoggedOut();
		userpanel.setVisible(false);
		
	}
	
	
	//refresht alle Panels
	public void refresh(){
		mainPanel.repaint();
		contentframe.repaint();
		navframe.repaint();
		gesamt.repaint();
		
		mainPanel.revalidate();
		contentframe.revalidate();
		navframe.revalidate();
		gesamt.revalidate();	
	}

}