package ui.module;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import domain.Shopverwaltung;
import ui.GUI_2;
import ui.controller.SuchController;
import valueobjects.Artikel;

public class SuchPanel {

	private JPanel suchPanel;
	private JPanel warenkorbPanel;
	private List artikelListe;
	private JTextField suchenTextField;
	private SuchController suchController;

	//Konstruktor
	public SuchPanel(SuchController suchController) {
		this.suchController = suchController;
		initialize();
	}

	private void initialize() {
		JPanel suchPanel = new JPanel();
		suchPanel.setLayout(new GridLayout(1, 2));

		suchenTextField = new JTextField();
		suchPanel.add(suchenTextField);

		JButton suchButton = new JButton("Suchen");
		suchPanel.add(suchButton);
		suchButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				suchen(suchenTextField.getText());
			}
		});		
		suchPanel.setBorder(BorderFactory.createTitledBorder("Suchen")); //Ueberschrift Suchen
		setSuchPanel(suchPanel);
	}

	private void suchen(String text) {
		suchController.suchen(text);
	}

	//Getter und Setter
	public JPanel getSuchPanel() {
		return suchPanel;
	}

	public void setSuchPanel(JPanel suchPanel) {
		this.suchPanel = suchPanel;
	}
}

