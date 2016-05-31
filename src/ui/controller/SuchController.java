package ui.controller;

import domain.Shopverwaltung;
import ui.GUI_2;
import valueobjects.Artikel;

import java.util.List;

public class SuchController {

    private GUI_2 gui;
    private Shopverwaltung shopverwaltung;

    public SuchController(GUI_2 gui, Shopverwaltung shopverwaltung) {

        this.gui = gui;
        this.shopverwaltung = shopverwaltung;
    }

    public void suchen(String suchText) {
    	
        List<Artikel> suchErgebnis = suchText.isEmpty() ?
                shopverwaltung.gibAlleArtikel() :
                shopverwaltung.sucheNachArtikelNummer(suchText);
        gui.getArtikelPanel().getArtikeltable().setDataVector(suchErgebnis);
    }
}
