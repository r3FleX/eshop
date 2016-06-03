package ui.module;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class WarenkorbPanel extends JPanel implements ActionListener {

	private JPanel warenkorbPanel;
	private JButton inWarenKorbLegenButton;
	private JButton zumWarenKorbButton;

	//Konstruktor
	public WarenkorbPanel() {
			
	
	}
	
	//Getter und Setter
	public JPanel getWarenkorbPanel() {
		return warenkorbPanel;
	}

	public void setWarenkorbPanel(JPanel warenkorbPanel) {
		this.warenkorbPanel = warenkorbPanel;
	}	
	
	public void actionPerformed(ActionEvent arg0) {
		System.out.println("Warenkorb Aktion ausgefuehrt");	
	}			
}
