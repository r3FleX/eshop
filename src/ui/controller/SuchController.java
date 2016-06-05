package ui.controller;

import domain.Artikelverwaltung;
import domain.Shopverwaltung;
import ui.GUI_2;
import ui.module.ArtikelPanel;
import valueobjects.Artikel;

import java.util.List;

public class SuchController {

    private GUI_2 gui;
    private Shopverwaltung shopverwaltung;

    public SuchController(GUI_2 gui, Shopverwaltung shopverwaltung) {

        this.gui = gui;
        this.shopverwaltung = shopverwaltung;
    }
    
    //Der Such Button ruft im suchController suchen auf. 
    //Der sagt der GUI -> Tabelle ändern.
    
    public void suchen(String suchText) {
    	
        List<Artikel> suchErgebnis = suchText.isEmpty() ?
                shopverwaltung.gibAlleArtikel() :
                shopverwaltung.sucheNachArtikel(suchText);                
        gui.getArtikelPanel().getArtikeltable().setDataVector(suchErgebnis);
        gui.getArtikelPanel().renderOption();
    }
}
