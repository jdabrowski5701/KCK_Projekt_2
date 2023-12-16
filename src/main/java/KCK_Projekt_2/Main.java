package KCK_Projekt_2;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainMenu.runMenu();
                //GameEngine gameEngine = new GameEngine("Test");
            }
        });
    }
}
