package launcher;

import ui.controller.ViewController;

import java.awt.*;

public class Launcher {

    //MAIN
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new ViewController();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
