package ui.module;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import ui.GUI_2;
import valueobjects.Account;

public class WarenkorbButton extends JPanel implements ActionListener {
	
	private JButton zumWarenKorbButton;
	private GUI_2 gui;
	
	//Konstruktor
	public WarenkorbButton() {
		
		//JPanel zumWarenKorbButtonPanel = new JPanel();
		//zumWarenKorbButtonPanel.setLayout(new GridLayout(1, 1));
		//zumWarenKorbButtonPanel.setBorder(BorderFactory.createTitledBorder("Warenkorb")); //Ueberschrift Warenkorb
		
	}
	public JButton createInWarenkorbLegenButton() {
		//Warenkorb Button "in Warenkorb legen" 
		JButton inWarenKorbLegenButton = new JButton("in Warenkorb legen",new ImageIcon("src/assets/inWarenkorbLegenIcon.png"));	
		
		//ACTIONLISTINER
		inWarenKorbLegenButton.addActionListener(new ActionListener() {	
			public void actionPerformed(ActionEvent e) {
				System.out.println("in den warenkorb");
			}
		});	
		return inWarenKorbLegenButton;
	}

	public JButton getZumWarenkorbButton() {
		//Warenkorb Button "zum Warenkorb"
		zumWarenKorbButton = new JButton("zum Warenkorb",new ImageIcon("src/assets/warenkorbIcon.png"));
		
		//ACTIONLISTINER
		zumWarenKorbButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Warenkorb geöffnet ausgefuehrt");	
			}
		});	
		return zumWarenKorbButton;
	}		
	
	public void actionPerformed(ActionEvent arg0) {
		System.out.println("Warenkorb Aktion ausgefuehrt");	
	}			
}

