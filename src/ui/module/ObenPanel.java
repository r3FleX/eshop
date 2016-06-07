package ui.module;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ObenPanel extends JPanel{
	
	JButton inWarenKorbLegenButton = new JButton("in Warenkorb legen",new ImageIcon("src/assets/inWarenkorbLegenIcon.png"));
	//suchPnal rein
	public ObenPanel(){
		
	}
	
	public void zeigeWarenKorb(){
		//this.remove(index);
		this.add(inWarenKorbLegenButton);
	}
	
	public void zeigeArtikel(){
		this.add(inWarenKorbLegenButton);
	}
}
