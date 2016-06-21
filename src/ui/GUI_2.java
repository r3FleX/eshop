package ui;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import domain.Shopverwaltung;
import domain.exceptions.ArtikelExistiertBereitsException;
import domain.exceptions.ArtikelExistiertNichtException;
import domain.exceptions.BestandUeberschrittenException;
import ui.controller.SuchController;
import ui.panel.ArtikelPanel;
import ui.panel.KundenPanel;
import ui.panel.MenuePanel;
import ui.panel.MitarbeiterPanel;
import ui.panel.SuchPanel;
import ui.panel.WarenkorbButtonPanel;
import ui.panel.WarenkorbPanel;
import valueobjects.Account;
import valueobjects.Artikel;
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
	
	//Konstrukter
	public GUI_2(String datei) {
		try {
			shop = new Shopverwaltung(datei);
			menuBar = new MenuePanel(this);			
			this.suchController = new SuchController(this); 
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
		//Panels Initializieren
		artikelPanel = new ArtikelPanel(shop.gibAlleArtikel(), this);
		mitarbeiterPanel = new MitarbeiterPanel(this);
		kundenPanel = new KundenPanel(this);
		suchPanel = new SuchPanel(suchController, this);
		
		
		//beendet das Programm durch klicken auf [X]
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.mainPanel.setLayout(new BorderLayout());
		this.navframe.setLayout(new BorderLayout());
		this.contentframe.setLayout(new BorderLayout());
		
		//Menuebar
		setJMenuBar(menuBar.getMenue());
		
		//Warenkorb erstellen.
		warenkorbPanel = new WarenkorbPanel(this);
		
		//Warenkorb Schaltflaechen
		warenKorbButtons = new WarenkorbButtonPanel(this);
		
		//SuchPanel
		suchPanelSetzen();
		
		//ObenPanel
		this.contentframe.add(obenPanel, BorderLayout.NORTH);
		
		//"norden splitten"
		obenPanelSetzen();
			
		//ArtikelPanel
		artikelPanelSetzen();
		
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
		//obenPanel.add(warenKorbButtons.getInWarenKorbLegenButton());
		obenPanel.add(warenKorbButtons.zumWarenKorbButton);
	}
	
	public void KundenPanelSetzen(){
		kundenPanel.setLayout(new GridLayout(1, 3));
		kundenPanel.setBorder(BorderFactory.createTitledBorder("Kundenbereich  -  Herzlich Willkommen: "+user.getName()+" !")); //Ueberschrift Kunden Login	
		navframe.add(kundenPanel, BorderLayout.NORTH);
		//nicht benoetigte Panel ausblenden
		obenPanel.setVisible(true);
	}
	
	public void MitarbeiterPanelSetzen(){
		mitarbeiterPanel.setLayout(new GridLayout(1, 3));
		navframe.add(mitarbeiterPanel, BorderLayout.NORTH);
		contentframe.add(mitarbeiterPanel.getContentframe());
		mitarbeiterPanel.setBorder(BorderFactory.createTitledBorder("Mitarbeiterbereich  -  Herzlich Willkommen: "+user.getName()+" !")); //Ueberschrift Mitarbeiter Login
		//nicht benoetigte Panel ausblenden
		artikelPanel.setVisible(false);
		warenkorbPanel.setVisible(false);
		untenframe.setVisible(false);
	}	
	//SuchPanel
	public void suchPanelSetzen(){		
		obenPanel.add(suchPanel, BorderLayout.NORTH);	
	}
	
	//ArtikelPanel
	public void artikelPanelSetzen(){
		this.contentframe.add(artikelPanel, BorderLayout.CENTER);	
	}
	
	//untenframe
	public void untenframe(){
		untenframe.setLayout(new GridLayout(1, 3));
		untenframe.add(new JLabel());//Platzhalter
		untenframe.add(warenKorbButtons.kaufAbschliessenButton);
		untenframe.add(new JLabel());//Platzhalter
		untenframe.setBorder(BorderFactory.createTitledBorder("Kaufabwicklungsbereich")); //Ueberschrift Kaufabwicklungsbereich
	}
		
	//Wenn Benutzer eingeloggt
	public void userLoggedIn(Account user) {
		//Menuebar anpassen 
		menuBar.setUserLoggedIn(true);
		
			if (user instanceof Kunde) {
				//kunde einloggen und warenkorb �bernehmen
				if(!(user.getAccountNr() == -1)){
					Warenkorb wk = ((Kunde) this.user).getWarenkorb();
					this.user = user;
					((Kunde) this.user).setWarenkorb(wk);
				}				
				//Panel einblenden
				KundenPanelSetzen();
				//Hinweisfesnterchen das man eingelogt ist
				System.out.println("Kunde " + user.getName() + " ist eingeloggt.");
			}
			else if(user instanceof Mitarbeiter) {
				obenPanel.setVisible(false);
				//mitarbeiter einloggen
				this.user = user;
				//Panel einblenden
				MitarbeiterPanelSetzen();
				//hinweisfesnterchen
				System.out.println("Mitarbeiter " + user.getName() + " ist eingeloggt.");	
			}
	}
	
	//Wenn Benutzer ausgeloggt
	public void userLoggedOut(){
		if (user instanceof Kunde) {
			contentframe.remove(kundenPanel);
		}else{
			navframe.remove(mitarbeiterPanel);
			contentframe.remove(mitarbeiterPanel.getContentframe());
			artikelPanel.setVisible(true);
			warenkorbPanel.setVisible(true);
			untenframe.setVisible(true);			
		}
		JOptionPane.showMessageDialog(null,"Erfolgreich ausgeloggt!");
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
		//obenPanel.remove(warenKorbButtons.getInWarenKorbLegenButton());
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
		//obenPanel.add(warenKorbButtons.getInWarenKorbLegenButton());
		obenPanel.add(warenKorbButtons.zumWarenKorbButton);
		contentframe.add(artikelPanel);
		
		refresh();
	}
	/**Fuegt einen Artikel in den Warenkorb
	 * 
	 * @param anzahl -> Anzahl der Artikel die Hinzugef�gt werden sollen
	 * @throws NumberFormatException
	 * @throws BestandUeberschrittenException
	 * @throws ArtikelExistiertNichtException
	 */
	public void zumWarenkorbHinzufuegen(int anzahl) throws NumberFormatException, BestandUeberschrittenException, ArtikelExistiertNichtException {

		Artikel art = shop.artikelSuchen(Integer.parseInt((this.getArtikelPanel().getArtikeltable().getValueAt(this.getArtikelPanel().getAusgabeTabelle().getSelectedRow(),0)).toString()));
		shop.inWarenkorbEinfuegen(art,anzahl,(Kunde) this.user);
		//aktuallisere Warenkorb
		Kunde user = (Kunde) this.getUser();
        this.getWarenkorbPanel().updateData(user.getWarenkorb());
		
	}
	
	
	public void ausWarenkorbentfernen(int artikelnummer) throws ArtikelExistiertNichtException, BestandUeberschrittenException {
		shop.ausWarenkorbloechen(shop.artikelSuchen(artikelnummer), (Kunde) user);
		Kunde user = (Kunde) this.getUser();
        this.getWarenkorbPanel().updateData(user.getWarenkorb());		
	}	
	
	
	
	
	/** Fuegt einen Artikel dem shop hinzu
	 * 
	 * @param atkl
	 */
	public void addArtikel(String artikelname,int artikelnummer, int bestand,float preis,int packungsgroesse) {
/*		
		try {
			shop.fuegeArtikelEin(artikelname, artikelnummer, bestand, preis, packungsgroesse);
			try {
				shop.schreibeArtikeldaten();
				JOptionPane.showMessageDialog(null,"Artikel erfolgreich hinzugefuegt!");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (ArtikelExistiertBereitsException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
		}
		*/		
	}
	/** Loescht einen Artikel aus dem Shop
	 * 
	 * @param atkl
	 */	
	public void deleteArtikel(int nr) {
	/*	try {
			shop.entferneArtikel(nr);
		} catch (ArtikelExistiertNichtException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}	
	public void aendereArtikel(String artikelname,int artikelnummer, int bestand,float preis,int packungsgroesse) {
		
		try {
			shop.aendereArtikel(artikelname, artikelnummer, bestand, preis, packungsgroesse);
		} catch (ArtikelExistiertBereitsException | ArtikelExistiertNichtException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			shop.schreibeArtikeldaten();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			shop.schreibeStatsdaten();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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