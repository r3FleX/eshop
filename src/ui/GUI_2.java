package ui;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

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
import valueobjects.Account;
import valueobjects.Kunde;
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
	private List artikelListe = new List();
	private JTable ausgabeTabelle = null;
	private JTable warenkorbTabelle = null;
	JLabel gesamt = new JLabel();
	private ArtikelPanel artikelPanel;
	private LoginPanel loginPanel;
	
	//Konstrukter
	public GUI_2(String datei) {
		try {
			this.suchController = new SuchController(this, new Shopverwaltung(datei)); //TODO: Eklig, da die ShopVerwaltung gefährliche Sachen im Konstruktor macht.
		} catch (IOException e) {
			e.printStackTrace();
		}
		setTitle("E-Shop");
		setSize(800, 600); //Fenstergroesse
		setResizable(false);
		
		try {
			shop = new Shopverwaltung(datei);
			menuBar = new MenuePanel(this, shop);
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
	
	public void userLoggedIn(Account user) {
		// Panel einblenden
		loginPanel.setVisible(true);
		System.out.println("Person " + user.getName() + " ist eingeloggt.");
		
//		if (user instanceof Mitarbeiter)..
	}
	
	//initialisieren
	private void initialize() {
	
		this.mainPanel.setLayout(new BorderLayout());
		this.navframe.setLayout(new BorderLayout());
		this.contentframe.setLayout(new BorderLayout());	
		
		//SuchPanel
		SuchPanel suchPanel = new SuchPanel(suchController);
		menuBar.setSuchPanel(suchPanel);
		this.contentframe.add(suchPanel.getSuchPanel(), BorderLayout.NORTH);	
		
		//LoginPanel
		loginPanel = new LoginPanel(this, shop);
		loginPanel.setVisible(false);
		this.navframe.add(loginPanel /*.getloginPanel() */, BorderLayout.NORTH);	
		setJMenuBar(menuBar.getMenue());	
		
		//ArtikelPanel
		artikelPanel = new ArtikelPanel(shop.gibAlleArtikel());
		this.contentframe.add(artikelPanel.getArtikelPanel(), BorderLayout.CENTER);	
		
		// GUI setzen
		this.mainPanel.add(this.navframe,BorderLayout.NORTH);
		this.mainPanel.add(this.contentframe,BorderLayout.CENTER);	
		add(this.mainPanel);
		setVisible(true);
	}
	
	//ACTIONLISTENER
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		
		//Fuer Menue Datei -> Beenden Button
		if (command.equals("Beenden")) {
			System.exit(0);
		}
		
		//Fuer Suchen Button
		else if (command.equals("Suchen")) {
				
		}
		//Fuer zum Warenkorb Button
		else if (command.equals("zum Warenkorb")) {
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

				wieViele.setVisible(true);

			} catch (NumberFormatException e1) {
				e1.printStackTrace();
			}	
		}
	}
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