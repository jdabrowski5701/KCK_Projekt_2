package KCK_Projekt_2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Timer;

public class MainMenu {
    private static ImageIcon[] frames;
    private static int currentFrame = 0;
    private static Timer timer;

    public static void runMenu() {
        JFrame window = new JFrame("Main menu");
        window.setSize(1920, 1080);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel backgroundPanel = new JPanel() {
            private Image backgroundImage;
            {
                try {
                    backgroundImage = ImageIO.read(getClass().getResource("/img/campfire_background.jpg"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        JPanel panelHolder = new JPanel(new BorderLayout());
        panelHolder.setOpaque(false);

        JPanel options = new JPanel(new GridLayout(3, 1, 10, 10));
        options.setOpaque(false);
        options.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel labelMenu = new JLabel();
        try {
            BufferedImage image = ImageIO.read(MainMenu.class.getResource("/img/Loot_v2.png"));

            frames = new ImageIcon[8];
            int frameWidth = image.getWidth();
            int frameHeight = image.getHeight() / 8;
            for (int i = 0; i < 8; i++) {
                frames[i] = new ImageIcon(image.getSubimage(0, i * frameHeight, frameWidth, frameHeight));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                labelMenu.setIcon(frames[currentFrame]);
                currentFrame = (currentFrame + 1) % 8;
            }
        });
        timer.start();
        labelMenu.setHorizontalAlignment(SwingConstants.CENTER);


        JButton buttonStart = new JButton("Nowa gra");
        buttonStart.setIcon(new ImageIcon(MainMenu.class.getResource("/img/menu_button.png")));
        buttonStart.setHorizontalTextPosition(SwingConstants.CENTER);
        buttonStart.setVerticalTextPosition(SwingConstants.CENTER);
        buttonStart.setOpaque(false);
        buttonStart.setContentAreaFilled(false);
        buttonStart.setBorderPainted(false);
        buttonStart.setForeground(Color.WHITE);
        buttonStart.setFont(new Font("Arial", Font.BOLD, 20));
        buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField inputField = new JTextField(10);
                JPanel panel = new JPanel();
                panel.add(new JLabel("Wprowadź swoje imię:"));
                panel.add(inputField);

                int result = JOptionPane.showConfirmDialog(null, panel, "Wprowadź swoje imię",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    String playerName = inputField.getText();
                    if (!playerName.isEmpty()) {
                        GameEngine gameEngine = new GameEngine(playerName);
                        //gameEngine.startNewGame(playerName);
                        System.out.println("Rozpoczęto nową grę dla gracza: " + playerName);
                        window.dispose();
                    } else {
                        JOptionPane.showMessageDialog(window, "Wprowadź poprawne imię!", "Błąd", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        JButton buttonGuide = new JButton("Instrukcja");
        buttonGuide.setIcon(new ImageIcon(MainMenu.class.getResource("/img/menu_button.png")));
        buttonGuide.setHorizontalTextPosition(SwingConstants.CENTER);
        buttonGuide.setVerticalTextPosition(SwingConstants.CENTER);
        buttonGuide.setOpaque(false);
        buttonGuide.setContentAreaFilled(false);
        buttonGuide.setBorderPainted(false);
        buttonGuide.setForeground(Color.WHITE);
        buttonGuide.setFont(new Font("Arial", Font.BOLD, 20));

        buttonGuide.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(window,
                        "Celem gry jest dotarcie do zamku i pokonanie głównego przeciwnika:\n" +
                                "1. Wybierz nową grę.\n" +
                                "2. Używaj strzałek do poruszania się po mapie.\n" +
                                "3. Wybieraj strzałkami opcje do walki z przeciwnikami.\n" +
                                "4. Wybieraj strzałkami przedmioty u kupca.\n" +
                                "5. Ogniska służą do odpoczynku.",
                        "Instrukcja",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });


        JButton buttonQuit = new JButton("Wyjście");
        buttonQuit.setIcon(new ImageIcon(MainMenu.class.getResource("/img/menu_button.png")));
        buttonQuit.setHorizontalTextPosition(SwingConstants.CENTER);
        buttonQuit.setVerticalTextPosition(SwingConstants.CENTER);
        buttonQuit.setOpaque(false);
        buttonQuit.setContentAreaFilled(false);
        buttonQuit.setBorderPainted(false);
        buttonQuit.setForeground(Color.WHITE);
        buttonQuit.setFont(new Font("Arial", Font.BOLD, 20));

        buttonQuit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.dispose();
            }
        });


        options.add(buttonStart);
        options.add(buttonGuide);
        options.add(buttonQuit);

        panelHolder.add(labelMenu, BorderLayout.NORTH);
        panelHolder.add(options, BorderLayout.CENTER);

        window.add(backgroundPanel);
        backgroundPanel.add(panelHolder);
        window.setVisible(true);
    }

}
