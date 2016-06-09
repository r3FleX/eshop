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

public class UserPanel extends JPanel {
	
	private Shopverwaltung shop;
	private GUI_2 gui;
	private JButton statistikButton = new JButton("Statistik",new ImageIcon("src/assets/statistikIcon.png"));
	
	//Konstruktor
	public UserPanel(GUI_2 gui, Shopverwaltung shop, Account user ) {
		
		this.shop = shop;
		this.gui = gui;
	
		add(statistikButton);
	}
}