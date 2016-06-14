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
	
	private GUI_2 gui;
	public JButton statistikButton = new JButton("Statistik",new ImageIcon("src/assets/statistikIcon.png"));
	
	//Konstruktor
	public UserPanel(GUI_2 gui) {
		
		this.gui = gui;
	
		add(statistikButton);
	}
	
	//Getter uns Setter
	public JButton getStatistikButton() {
		return statistikButton;
	}

	public void setStatistikButton(JButton statistikButton) {
		this.statistikButton = statistikButton;
	}
}