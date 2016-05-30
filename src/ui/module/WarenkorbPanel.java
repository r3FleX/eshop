package ui.module;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class WarenkorbPanel implements ActionListener {

	private JPanel warenkorbPanel;

	//Konstruktor
	public WarenkorbPanel() {
			
		JPanel warenKorbPanel = new JPanel();
		warenKorbPanel.setLayout(new GridLayout(1, 3));
		
		warenKorbPanel.setBorder(BorderFactory.createTitledBorder("Warenkorb")); //Ueberschrift Warenkorb
		setWarenkorbPanel(warenKorbPanel);		
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
