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
import ui.panel.WarenkorbPanel;
import valueobjects.Account;
import valueobjects.Kunde;


public class ButtonEditor extends DefaultCellEditor {

	protected JButton button;
	private String label;
	private boolean isPushed;
//	private Shopverwaltung shop;
	private GUI_2 gui;
	public ButtonEditor(JCheckBox checkBox, GUI_2 gui,ActionListener listen) {

		super(checkBox);
	    button = new JButton();
	    button.setOpaque(true);
	    this.gui = gui;
	    //Actionlistener
	    button.addActionListener(listen);
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
