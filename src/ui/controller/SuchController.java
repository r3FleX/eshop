package ui.controller;

import domain.Shopverwaltung;
import ui.GUI_2;
import valueobjects.Artikel;

import java.util.List;

/**
 * Created by Tobi on 30.05.16.
 */
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
                shopverwaltung.sucheNachArtikel(suchText);
        gui.getArtikelPanel().getArtikeltable().setDataVector(suchErgebnis);
    }
}
