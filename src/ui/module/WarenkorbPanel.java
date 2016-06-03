package ui.module;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import ui.ArtikelTableModel;
import ui.GUI_2;
import valueobjects.Account;
import valueobjects.Kunde;

public class WarenkorbPanel extends JPanel implements ActionListener {

	private JPanel warenkorbPanel;
	private GUI_2 gui;
	private Kunde user;

	//Konstruktor
	public WarenkorbPanel(GUI_2 gui, Account user) {
		this.gui = gui;
		this.user = (Kunde) user;
		//erzeuge artikelliste
		ArtikelTableModel table = new ArtikelTableModel();
		
		// JTable-Objekt erzeugen und mit Datenmodell initialisieren:
		JTable ausgabeTabelle = new JTable(table);
		
		// JTable in ScrollPane platzieren:
		JScrollPane scrollPane = new JScrollPane(ausgabeTabelle);
				
		// Anzeige der Artikelliste auch in der Kunden-Ansicht
		table.setDataVector2(this.user.getWarenkorb());
		
		warenkorbPanel.add(scrollPane);
			
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
