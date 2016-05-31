package ui.module;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import domain.Shopverwaltung;
import domain.exceptions.AccountExistiertBereitsException;
import domain.exceptions.AccountExistiertNichtException;
import ui.GUI_2;
import valueobjects.Account;
import valueobjects.Kunde;
import valueobjects.Mitarbeiter;

public class LoginPanel {

	private JPanel loginPanel;	
	private JPanel suchPanel;	
	private Shopverwaltung shop;
	Shopverwaltung shopverwaltung;
	private Account user;
	private JTextField nameTextField;
	private JPasswordField passTextField;
	private GUI_2 gui;

	//Konstruktor
	public LoginPanel(Shopverwaltung shop, GUI_2 gui) {
		
		this.shop = shop;
		this.gui = gui;

		initialize();
	}
	
	//Getter und Setter
	public JPanel getloginPanel() {
		return loginPanel;
	}

	public void setloginPanel(JPanel loginPanel) {
		this.loginPanel = loginPanel;
	}

	private void initialize() {
	//ACTIONLISTENER
	//public void actionPerformed(ActionEvent arg0) {
		
		loginPanel = new JPanel();
		loginPanel.setLayout(new GridLayout(1, 2));		
		loginPanel.setBorder(BorderFactory.createTitledBorder("Kundenbereich -  Willkommen !")); //Ueberschrift Login
		//setloginPanel(loginPanel);
		
		//zuerst ausgeblendet, wenn eingeloggt, dann eingeblendet
		loginPanel.setVisible(false);
			
		System.out.println("test einloggen");
		//Fuer Menue Account -> Einloggen Button
		//if(command.equals("Einloggen")){
			
			final JFrame login = new JFrame();
	
			login.setSize(200, 300);
			login.setLayout(new GridLayout(7, 1));
	
			JLabel labelname = new JLabel("Name:");
			login.add(labelname);
			
			final JTextField nameFeld = new JTextField();
			login.add(nameFeld);
	
			JLabel labelpasswort = new JLabel("Passwort:");
			login.add(labelpasswort);
	
			final JPasswordField passwortFeld = new JPasswordField();
			login.add(passwortFeld);
			
			JButton loginButton = new JButton("Login");
			login.add(loginButton);
			login.setVisible(true);	
			
			//Fuer Menue Account -> Einloggen -> Login Button
			loginButton.addActionListener(new ActionListener() { 
				
				public void actionPerformed(ActionEvent arg0) {
			
					System.out.println("loginbutton");
					
					//LoginButton
					JButton loginButton = new JButton("Login");
					loginPanel.add(loginButton);
					
					//hole Name und Passwort aus Textfelder
					String name = nameFeld.getText();
					String passwort = String.valueOf(passwortFeld.getPassword());
			
					//Ueberpruefe ob Kunde oder Mitarbeiter
					try {
						Account user = shop.loginAccount(name, passwort);
						
						if (user instanceof Kunde) {
							
							login.setVisible(false);
							loginPanel.setVisible(true);
							
							//Test //loginPanel.setLayout(new GridLayout(4, 3));	
							//suchPanel.setVisible(false);
							
							gui.refresh();
							//funktioniert noch nicht (ausgrauen rueckgaengig machen)  //mnLogout.setEnabled(true); 
							//mnLogout.setEnabled(true);
								
							JOptionPane.showMessageDialog(null,"Erfolgreich als Kunde eingeloggt!");
						}
						else if (user instanceof Mitarbeiter){
							login.setVisible(false);
							System.out.println("Mitarbeiter eingeloggt");
							JOptionPane.showMessageDialog(null,"Erfolgreich als Mitarbeiter eingeloggt!");
						}
					} catch (AccountExistiertNichtException ex) {
						JOptionPane.showMessageDialog(null, ex.getMessage());
					}
				}
			});
			
			//login.setVisible(true);	
		//}
		/*
		//Fuer Menue Account -> Registrieren Button
		else if (command.equals("Registrieren")){
			final JFrame registrieren = new JFrame();

			registrieren.setSize(400, 300);
			registrieren.setLayout(new GridLayout(11, 1));

			JLabel name = new JLabel("Name:");
			registrieren.add(name);

			final JTextField nameFeld = new JTextField();
			registrieren.add(nameFeld);

			JLabel passwort = new JLabel("Passwort:");
			registrieren.add(passwort);

			final JPasswordField passwortFeld = new JPasswordField();
			registrieren.add(passwortFeld);

			JLabel adresse = new JLabel("Adresse:");
			registrieren.add(adresse);

			final JTextField adressFeld = new JTextField();
			registrieren.add(adressFeld);

			JLabel plz = new JLabel("Postleitzahl:");
			registrieren.add(plz);

			final JTextField plzFeld = new JTextField();
			registrieren.add(plzFeld);

			JLabel wohnort = new JLabel("Ort:");
			registrieren.add(wohnort);

			final JTextField ortFeld = new JTextField();
			registrieren.add(ortFeld);

			JButton regButton = new JButton("Registrieren");
			registrieren.add(regButton);
			
			//Fuer Menue Account -> Registrieren -> Registrieren Button
			regButton.addActionListener(new ActionListener() { 
		
				public void actionPerformed(ActionEvent arg0) {
					
					//hole Name, Passwort, Starsse, PLZ und Ort aus Textfelder
					String name = nameFeld.getText();
					String passwort = String.valueOf(passwortFeld.getPassword());
					String strasse = adressFeld.getText();
					int plz = Integer.parseInt(plzFeld.getText());
					String ort = ortFeld.getText();
					
					try {
						shop.fuegeKundenAccountEin(name, passwort, strasse, plz, ort);
						try {
							shop.schreibeKundendaten();
							JOptionPane.showMessageDialog(null,"Erfolgreich als Kunde registriert!");
							registrieren.setVisible(false);
						} catch (IOException e) {
							e.printStackTrace();
						}
					} catch (AccountExistiertBereitsException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					}
				}
			});
			registrieren.setVisible(true);
		}*/
	//}
	}
}