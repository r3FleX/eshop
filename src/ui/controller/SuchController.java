package ui.controller;

import domain.Artikelverwaltung;
import domain.Shopverwaltung;
import ui.GUI_2;
import ui.panel.ArtikelPanel;
import valueobjects.Artikel;

import java.util.List;

public class SuchController {

    private GUI_2 gui;

    public SuchController(GUI_2 gui) {
        this.gui = gui;
    }
    
    //Der Such Button ruft im suchController suchen auf. 
    //Der sagt der GUI -> Tabelle ändern.
    
    public void suchen(String suchText) {
    	
        List<Artikel> suchErgebnis = suchText.isEmpty() ?
                gui.getShop().gibAlleArtikel() :
                gui.getShop().sucheNachArtikel(suchText);                
        gui.getArtikelPanel().getArtikeltable().setDataVector(suchErgebnis,"Kaufen");
        gui.getArtikelPanel().renderOption();
    }
}
