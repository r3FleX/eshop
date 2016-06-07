package ui.module;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ObenPanel extends JPanel{
	
	JButton zumWarenKorbButtonAnklicken = new JButton("in Warenkorb legen",new ImageIcon("src/assets/inWarenkorbLegenIcon.png"));
	JButton zumShopButton = new JButton("zum Shop");
	//suchPnal rein
	public ObenPanel(){
		
	}
	
	public void zumWarenKorbButtonAnklicken(){
		//this.remove(zumWarenKorbButtonAnklicken);
		//this.remove(inWarenKorbLegenButton);
		this.add(zumShopButton);
		
	}
	
	public void zeigeArtikel(){
		//this.add(inWarenKorbLegenButton);
	}
}
