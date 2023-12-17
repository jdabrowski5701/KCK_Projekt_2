package KCK_Projekt_2;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                //MainMenu.runMenu();
                //GameEngine gameEngine = new GameEngine("Test");
                //Campfire campfire = new Campfire();
                Enemy enemy = new Enemy(10, 100);
               // enemy.handleEnemy(10,200);
            }
        });
    }
}

