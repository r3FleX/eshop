package ui.module;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import domain.Shopverwaltung;
import ui.GUI_2;
import valueobjects.Account;
import valueobjects.Kunde;

public class UserPanel extends JPanel implements ActionListener{
	private Shopverwaltung shop;
	private GUI_2 gui;
	public UserPanel(GUI_2 gui, Shopverwaltung shop, Account user ) {
		
		this.shop = shop;
		this.gui = gui;
	
		//Statistik Button 
		JButton statistikButton = new JButton("Statistik",new ImageIcon("src/assets/statistikIcon.png"));
		add(statistikButton);
		
		//ACTIONLISTINER
		statistikButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
			
		

		
		if (user instanceof Kunde) {
			
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	public void setwarenkorbvisible() {
	//	inWarenKorbLegenButton.setVisible(true);
	}
}