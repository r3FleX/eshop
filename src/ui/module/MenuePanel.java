package ui.module;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import domain.Shopverwaltung;
import ui.GUI_2;
import valueobjects.Account;

public class MenuePanel extends JPanel implements ActionListener{

	private JMenuBar menuBar;
	private Shopverwaltung shop;
	private Account user;
	JLabel gesamt = new JLabel();
	private SuchPanel suchPanel;
	private JPanel loginPanel;
	private JMenuItem mnLogin;
	private JMenuItem mnReg;
	private JMenuItem mnLogout;
	
	//Konstruktor
	public MenuePanel(GUI_2 gui, Shopverwaltung shop) {
		this.shop = shop;
		
		JMenuBar menueBar = new JMenuBar();		
		
		JMenu mnDatei = new JMenu("Datei");
		menueBar.add(mnDatei);

		JMenuItem mntmBeenden = new JMenuItem("Beenden");
		mntmBeenden.addActionListener(this);
		mnDatei.add(mntmBeenden);
		
		JMenu mnAccount = new JMenu("Account");
		menueBar.add(mnAccount);
		
		mnLogin = new JMenuItem("Einloggen");
		mnAccount.add(mnLogin);
		LoginPanel loginPanel = new LoginPanel(gui, shop, user);
		mnLogin.addActionListener(loginPanel);
		
		mnReg = new JMenuItem("Registrieren");
		mnReg.addActionListener(loginPanel);
		mnAccount.add(mnReg);
		
		mnLogout = new JMenuItem("Ausloggen");

		mnLogout.setEnabled(false);   //ausgegraut                            
		mnAccount.add(mnLogout);
		mnLogout.addActionListener(this);

		JMenu mnHilfe = new JMenu("Hilfe");
		menueBar.add(mnHilfe);

		JMenuItem menuItem = new JMenuItem("Wie Artikel kaufen?");
		mnHilfe.add(menuItem);
		menuItem.addActionListener(this);

		setMenue(menueBar);
	}
	
	//Getter und Setter
	public SuchPanel getSuchPanel() {
		return suchPanel;
	}

	public void setSuchPanel(SuchPanel suchPanel) {
		this.suchPanel = suchPanel;
	}
		
	public JMenuBar getMenue() {
		return this.menuBar;
	}
	
	public void setMenue(JMenuBar menue) {
		this.menuBar = menue;
	}
	
	//ACTIONLISTENER
	public void actionPerformed(ActionEvent arg0) {
		String command = arg0.getActionCommand();
		
		//Fuer Menue Datei -> Beenden Button
		if (command.equals("Beenden")) {
			System.exit(0);
		}
		//Fuer Menue Datei -> Ausloggen Button
		else if(command.equals("Ausloggen")){
			user = shop.logoutAccount(user.getName(), user.getPasswort());
			gesamt.setVisible(true);
			//this.setContentPane(this.hauptscreen);
			System.out.println("Tschuess!");
		}
		//Fuer Menue Hilfe -> Artikel kaufen? Button
		else if (command.equals("Wie Artikel kaufen?")) {
			JOptionPane.showMessageDialog(null,
				"Willkommen im E-Shop. \n Wenn Sie Artikel kaufen wollen, dann registrieren"
				+ "Sie sich und loggen Sie sich ein! \n Anschliessend koennen Sie die gewuenschten "
			    + "Artikel kaufen.");
		}
		
		System.out.println("menuepanel Aktion ausgefuehrt");
	}

	public void setUserLoggedin(boolean b) {
		mnLogout.setEnabled(b);
		mnReg.setEnabled(!b);
		mnLogin.setEnabled(!b);
	}
}
