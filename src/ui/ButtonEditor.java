package ui;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import domain.Shopverwaltung;
import domain.exceptions.ArtikelExistiertNichtException;
import domain.exceptions.BestandUeberschrittenException;
import ui.module.WarenkorbPanel;
import valueobjects.Account;
import valueobjects.Kunde;


public class ButtonEditor extends DefaultCellEditor {

	protected JButton button;
	private String label;
	private boolean isPushed;
	private Shopverwaltung shop;
	private Account user;
	private WarenkorbPanel warenkorbpanel;
	private JTable ausgabeTabelle = null;
	private GUI_2 gui;

	public ButtonEditor(JCheckBox checkBox) {
		super(checkBox);
	    button = new JButton();
	    button.setOpaque(true);
	    
	    //Actionlistener
	    button.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
			  
			    fireEditingStopped();
			    //warenkorbpanel.wieOftArtikelZumWarenKorb();
			    try {
					JLabel wieOftArtikelKaufenLabel = new JLabel("Wie oft wollen Sie den Artikel kaufen?");
					final JTextField anzahl = new JTextField();
					JButton inDenWarenkorbButton = new JButton("In den Warenkorb");

					final JFrame wieOftArtikelKaufenFrame = new JFrame();
					wieOftArtikelKaufenFrame.getContentPane().setLayout(new GridLayout(2, 1));
					wieOftArtikelKaufenFrame.setSize(450, 100);
					wieOftArtikelKaufenFrame.getContentPane().add(wieOftArtikelKaufenLabel);
					wieOftArtikelKaufenFrame.getContentPane().add(anzahl);
					wieOftArtikelKaufenFrame.getContentPane().add(inDenWarenkorbButton);
					wieOftArtikelKaufenFrame.setVisible(true);
					
					inDenWarenkorbButton.addActionListener(new ActionListener() {

						public void actionPerformed(ActionEvent arg0) {
							try {  //inWarenkorbEinfuegen(Artikel art,                ->                                                                                            ,int anzahl                        , Kunde kunde)	
								shop.inWarenkorbEinfuegen(shop.artikelSuchen(Integer.parseInt((ausgabeTabelle.getValueAt(ausgabeTabelle.getSelectedRow(),0)).toString())),Integer.parseInt(anzahl.getText()),(Kunde) user);
								wieOftArtikelKaufenFrame.setVisible(false);
							} catch (NumberFormatException e) {
								e.printStackTrace();
							} catch (BestandUeberschrittenException e) {
								JOptionPane.showMessageDialog(null, e.getMessage());
							} catch (ArtikelExistiertNichtException e) {
								JOptionPane.showMessageDialog(null, e.getMessage());
							}
						}
					});

				} catch (NumberFormatException e1) {
					e1.printStackTrace();
				}
			}
	    });
	}

	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column){
		if (isSelected) {
			button.setForeground(table.getSelectionForeground());
			button.setBackground(table.getSelectionBackground());
		}else {
			button.setForeground(table.getForeground());
	    	button.setBackground(table.getBackground());
	    }
	    label = (value == null) ? "" : value.toString();
	    button.setText(label);
	    isPushed = true;
	    return button;
	}	

	public Object getCellEditorValue() {
		if (isPushed) { 
			//JOptionPane.showMessageDialog(button, label + ": Ouch!");
			System.out.println(label + ": Ouch!");   
		}
		isPushed = false;
    	return new String(label);
    }	

	public boolean stopCellEditing() {
		isPushed = false;
		return super.stopCellEditing();
	}

	protected void fireEditingStopped() {
		super.fireEditingStopped();
	}
}
