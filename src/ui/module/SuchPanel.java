package ui.module;

import java.awt.GridLayout;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ui.GUI_2;
import ui.controller.SuchController;


public class SuchPanel extends JPanel{

	//private JPanel suchPanel;
	//private JPanel warenkorbPanel;
	private List artikelListe;
	private JTextField suchenTextField;
	private SuchController suchController;
	private GUI_2 gui;

	//Konstruktor
	public SuchPanel(SuchController suchController, GUI_2 gui) {
		
		this.suchController = suchController;
		this.gui = gui;
		
		initialize();
		//gui.refresh();
	}

	private void initialize() {
		
		//JPanel suchPanel = new JPanel();
		this.setLayout(new GridLayout(1, 2));

		suchenTextField = new JTextField();
		this.add(suchenTextField);

		JButton suchButton = new JButton("Suchen");
		this.add(suchButton);
		suchButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				suchen(suchenTextField.getText());
				gui.artikelPanelEinblenden(true);
				gui.untenWarenKorbBereichPanel(false);
			}
		});		
		this.setBorder(BorderFactory.createTitledBorder("Suchen")); //Ueberschrift Suchen
		
	}

	private void suchen(String text) {
		suchController.suchen(text);
		//gui.refresh();
	}

	/*
	//Getter und Setter
	public JPanel getSuchPanel() {
		return suchPanel;
	}

	public void setSuchPanel(JPanel suchPanel) {
		this.suchPanel = suchPanel;
	}
	*/
}

