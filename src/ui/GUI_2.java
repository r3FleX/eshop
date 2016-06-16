package ui;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import domain.Shopverwaltung;
import ui.controller.SuchController;
import ui.module.ArtikelPanel;
import ui.module.KundenPanel;
import ui.module.MenuePanel;
import ui.module.SuchPanel;
import ui.module.MitarbeiterPanel;
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
	private Account user = new Kunde("Gast", "gast", -1, "none", 12345, "none");
	
	//Menuebar
	private MenuePanel menuBar;
	
	//LayoutPanel
	public JPanel navframe = new JPanel();		
	public JPanel contentframe = new JPanel();	
	public JPanel mainPanel = new JPanel();
	
	private JPanel untenWarenKorbBereichPanel = new JPanel();
	private JPanel obenPanel = new JPanel();
	public JPanel untenframe = new JPanel();
	
	private MitarbeiterPanel mitarbeiterPanel;
	private KundenPanel kundenPanel;
	private SuchPanel suchPanel;
	private ArtikelPanel artikelPanel;
	private WarenkorbButtonPanel warenKorbButtons;
	private WarenkorbPanel warenkorbPanel;
	private ArtikelTableModel atm;
	
	//Konstrukter
	public GUI_2(String datei) {
		try {
			shop = new Shopverwaltung(datei);
			menuBar = new MenuePanel(this, shop, user);			
			this.suchController = new SuchController(this, shop); 
		} catch (IOException e) {
			e.printStackTrace();
		}
		setTitle("E-Shop");
		setSize(800, 600); //Fenstergroesse
		//setResizable(false);
		this.initialize();
	}	
	
	//initialisieren
	private void initialize() {
		
		//beendet das Programm durch klicken auf [X]
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.mainPanel.setLayout(new BorderLayout());
		this.navframe.setLayout(new BorderLayout());
		this.contentframe.setLayout(new BorderLayout());
		
		//Menuebar
		setJMenuBar(menuBar.getMenue());
		
		//Warenkorb erstellen.
		warenkorbPanel = new WarenkorbPanel(this);	
		
		//Warenkorb Schaltflarchen
		warenKorbButtons = new WarenkorbButtonPanel(this);
		
		//SuchPanel
		suchPanelSetzen();
		
		//ObenPanel
		this.contentframe.add(obenPanel, BorderLayout.NORTH);
		
		//"norden splitten"
		obenPanelSetzen();
			
		//ArtikelPanel
		artikelPanelSetzen();
		
		//untenframe
		untenframe();
		
		//GUI setzen
		this.mainPanel.add(this.navframe,BorderLayout.NORTH);
		this.mainPanel.add(this.contentframe,BorderLayout.CENTER);	
		this.mainPanel.add(this.untenframe,BorderLayout.SOUTH);	
		add(this.mainPanel);
		setVisible(true);
	}
	
	//ObenPanel
	public void obenPanelSetzen(){
		obenPanel.setLayout(new GridLayout(1,1));
		obenPanel.add(suchPanel);
		obenPanel.add(warenKorbButtons.getInWarenKorbLegenButton());
		obenPanel.add(warenKorbButtons.zumWarenKorbButton);
	}
	
	public void KundenPanelSetzen(){
		kundenPanel = new KundenPanel(this);
		kundenPanel.setLayout(new GridLayout(1, 3));
		this.navframe.add(kundenPanel, BorderLayout.NORTH);			
		
	}
	
	public void MitarbeiterPanelSetzen(){
		mitarbeiterPanel = new MitarbeiterPanel(this);
		mitarbeiterPanel.setLayout(new GridLayout(1, 3));
		this.navframe.add(mitarbeiterPanel, BorderLayout.NORTH);	
		
	}	
	//SuchPanel
	public void suchPanelSetzen(){
		suchPanel = new SuchPanel(suchController, this);
		this.obenPanel.add(suchPanel, BorderLayout.NORTH);	
	}
	
	//ArtikelPanel
	public void artikelPanelSetzen(){
		artikelPanel = new ArtikelPanel(shop.gibAlleArtikel(), this);
		this.contentframe.add(artikelPanel, BorderLayout.CENTER);	
	}
	
	//untenframe
	public void untenframe(){
		untenframe.setLayout(new GridLayout(1, 3));
		untenframe.add(new JLabel());//Platzhalter
		untenframe.add(warenKorbButtons.kaufAbschliessenButton);
		untenframe.add(new JLabel());//Platzhalter
		untenframe.setBorder(BorderFactory.createTitledBorder("Kaufabwicklungsbereich")); //Ueberschrift Kaufabwicklungsbereich
		untenframe.setVisible(false);
	}
		
	//Wenn Benutzer eingeloggt
	public void userLoggedIn(Account user) {
		//Menuebar anpassen 
			if (user instanceof Kunde) {
				if(!(user.getAccountNr() == -1)){
					obenPanel.setVisible(true);
					menuBar.setUserLoggedIn(true);

					Warenkorb wk = ((Kunde) this.user).getWarenkorb();
					this.user = user;
					
					((Kunde) this.user).setWarenkorb(wk);
				}				
				
				//Panel einblenden
				KundenPanelSetzen();
				obenPanel.setVisible(true);
				
				System.out.println("Kunde " + user.getName() + " ist eingeloggt.");
				kundenPanel.setBorder(BorderFactory.createTitledBorder("Kundenbereich  -  Herzlich Willkommen: "+user.getName()+" !")); //Ueberschrift Kunden Login	
				
				refresh();
			}
			else if(user instanceof Mitarbeiter) {
				obenPanel.setVisible(false);
				menuBar.setUserLoggedIn(true); //Menuebar mitteilen das user eingelogt
				
				//mitarbeiter einloggen
				this.user = user;
				MitarbeiterPanelSetzen();
				
				System.out.println("Mitarbeiter " + user.getName() + " ist eingeloggt.");	
				mitarbeiterPanel.setBorder(BorderFactory.createTitledBorder("Mitarbeiterbereich  -  Herzlich Willkommen: "+user.getName()+" !")); //Ueberschrift Mitarbeiter Login

				refresh();
			}
	}
	
	//Wenn Benutzer ausgeloggt
	public void userLoggedOut(){
		if (user instanceof Kunde) {
			kundenPanel.setVisible(false);
		}else{	
			mitarbeiterPanel.setVisible(false);
		}		
		//Gastkonto "reaktivieren"
		this.user = (Account) new Kunde("Gast", "gast", -1, "none", 12345, "none");
	}
	
	//warenkorb anzeigen
	public void zumWarenKorb(){
		
		obenPanel.setVisible(true);
		untenframe.setVisible(true);
		
		//loescht Artikel Panel
		contentframe.remove(artikelPanel);
		
		//Hinzufuegen hinzugefuegte Artikel Panel
		this.contentframe.add(warenkorbPanel);
		
		obenPanel.remove(suchPanel);
		contentframe.remove(suchPanel);
		
		obenPanel.remove(warenKorbButtons.zumWarenKorbButton);
		obenPanel.remove(warenKorbButtons.getInWarenKorbLegenButton());
		obenPanel.add(warenKorbButtons.zumShop);
		refresh();
	}
	
	public void zumShopButton(){	
		
		obenPanel.setVisible(true);
		untenframe.setVisible(false);
		
		obenPanel.remove(warenKorbButtons.zumShop);
		contentframe.remove(warenkorbPanel);
		contentframe.remove(untenWarenKorbBereichPanel);
		untenWarenKorbBereichPanel.remove(warenKorbButtons.kaufAbschliessenButton);		
		
		obenPanel.add(suchPanel);
		obenPanel.add(warenKorbButtons.getInWarenKorbLegenButton());
		obenPanel.add(warenKorbButtons.zumWarenKorbButton);
		contentframe.add(artikelPanel);
		
		refresh();
	}
	
	public void untenWarenKorbBereichPanel(boolean b){
		untenWarenKorbBereichPanel.setVisible(b);
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
	
	public WarenkorbPanel getWarenkorbPanel() {
		return warenkorbPanel;
	}

	public void setWarenkorbPanel(WarenkorbPanel warenkorbPanel) {
		this.warenkorbPanel = warenkorbPanel;
	}
	
	public JPanel getObenPanel() {
		return obenPanel;
	}

	public void setObenPanel(JPanel obenPanel) {
		this.obenPanel = obenPanel;
	}
	
	//refresht alle Panels
	public void refresh(){

		mainPanel.revalidate();
		contentframe.revalidate();
		obenPanel.revalidate();
		
		mainPanel.repaint();
		contentframe.repaint();
		navframe.repaint();
	}
}