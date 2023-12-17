package KCK_Projekt_2;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Campfire extends JFrame {

    private ImageIcon[] campfireFrames;
    private ImageIcon[] healCampFrames;
    private Timer campfireTimer;
    private Timer healCampTimer;
    private JDialog pauseDialog;
    public Campfire() {
        setTitle("Ognisko");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();

        setSize(width, height);
        setLocationRelativeTo(null);

        // Utworzenie tła z obrazem action_panel.jpg
        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/img/action_panel.jpg"));
        JLabel background = new JLabel(backgroundImage);
        background.setLayout(new BorderLayout());


        // Dodanie przycisku "Powrót"
        JButton backButton = new JButton("Powrót");
        backButton.setIcon(new ImageIcon(MainMenu.class.getResource("/img/menu_button.png")));
        backButton.setHorizontalTextPosition(SwingConstants.CENTER);
        backButton.setVerticalTextPosition(SwingConstants.CENTER);
        backButton.setOpaque(false);
        backButton.setContentAreaFilled(false);
        backButton.setBorderPainted(false);
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 20));
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPauseMenu();
                dispose(); // Zamknięcie okna Campfire
            }
        });

        // Ustawienie przycisku w prawym dolnym rogu
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        buttonPanel.add(backButton);

        // Dodanie przycisku na tle grafiki
        background.add(buttonPanel, BorderLayout.SOUTH);

        // Dodanie tła do okna
        add(background);




        try {
            BufferedImage image = ImageIO.read(getClass().getResource("/img/camp.png"));
            int frameWidth = image.getWidth() / 11;
            int frameHeight = image.getHeight();

            campfireFrames = new ImageIcon[11];

            for (int i = 0; i < 11; i++) {
                campfireFrames[i] = new ImageIcon(image.getSubimage(i * frameWidth, 0, frameWidth, frameHeight)
                        .getScaledInstance(frameWidth / 2, frameHeight / 2, Image.SCALE_DEFAULT));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        JLabel campfireLabel = new JLabel();
        campfireTimer = new Timer(120, new ActionListener() {
            int currentFrame = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentFrame < campfireFrames.length) {
                    campfireLabel.setIcon(campfireFrames[currentFrame]);
                    currentFrame++;
                } else {
                    currentFrame = 0;
                }
             //   background.add(campfireLabel, BorderLayout.CENTER);

                revalidate();
                repaint();
            }
        });

            try {
                BufferedImage image = ImageIO.read(getClass().getResource("/img/heal_camp.png"));
                int frameWidth = image.getWidth() / 4;
                int frameHeight = image.getHeight();

                healCampFrames = new ImageIcon[4];

                for (int i = 0; i < 4; i++) {
                    healCampFrames[i] = new ImageIcon(image.getSubimage(i * frameWidth, 0, frameWidth, frameHeight)
                            .getScaledInstance(frameWidth * 8 , frameHeight * 8 , Image.SCALE_DEFAULT));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            JLabel healCampLabel = new JLabel();
            healCampTimer = new Timer(250, new ActionListener() {
                int currentFrame = 0;

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (currentFrame < healCampFrames.length) {
                        healCampLabel.setIcon(healCampFrames[currentFrame]);
                        currentFrame++;
                    } else {
                        healCampTimer.stop(); // Zatrzymanie animacji po odtworzeniu wszystkich klatek
                    }
                   // background.add(healCampLabel, BorderLayout.WEST);
                    revalidate();
                    repaint();
                }
            });



        JPanel animationPanel = new JPanel(new GridBagLayout());
        animationPanel.setOpaque(false);

        GridBagConstraints gbcCampfire = new GridBagConstraints();
        gbcCampfire.gridx = 0;
        gbcCampfire.gridy = 0;
        gbcCampfire.insets = new Insets(350, 0, 0, 0);
        animationPanel.add(campfireLabel, gbcCampfire);

        GridBagConstraints gbcHealCamp = new GridBagConstraints();
        gbcHealCamp.gridx = 0;
        gbcHealCamp.gridy = 0;
        gbcHealCamp.insets = new Insets(200, 0, 0, 1100);
        animationPanel.add(healCampLabel, gbcHealCamp);

        background.add(animationPanel, BorderLayout.CENTER);


        campfireTimer.start();
        healCampTimer.start();


        setVisible(true);


    }
    private void showPauseMenu() {
        if (pauseDialog == null || !pauseDialog.isDisplayable()) {
            pauseDialog = new JDialog(this, "Pauza", Dialog.ModalityType.APPLICATION_MODAL);
            pauseDialog.setSize(450, 180);
            pauseDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
            pauseDialog.setResizable(false);
            pauseDialog.setLocationRelativeTo(null);
            pauseDialog.setUndecorated(true);

            JPanel pausePanel = new JPanel(new BorderLayout());
            pausePanel.setBackground(new Color(64, 64, 64));

            JTextArea pauseMessage = new JTextArea();
            pauseMessage.setEditable(false);
            pauseMessage.setOpaque(false);
            pauseMessage.setForeground(Color.WHITE);
            pauseMessage.setFont(new Font("Arial", Font.BOLD, 18));
            pauseMessage.setText(
                    "Ciepło ogniska pozwala ci uleczyć rany i odpocząć.\n" +
                            "Twoje zdrowie wraca do maksymalnej wartości.\n" +
                            "Gdy będziesz gotowy wróć do lochów...");


            JButton buttonQuit = new JButton("Powrót");

            buttonQuit.setIcon(new ImageIcon(MainMenu.class.getResource("/img/menu_button.png")));
            buttonQuit.setHorizontalTextPosition(SwingConstants.CENTER);
            buttonQuit.setVerticalTextPosition(SwingConstants.CENTER);
            buttonQuit.setOpaque(false);
            buttonQuit.setContentAreaFilled(false);
            buttonQuit.setBorderPainted(false);
            buttonQuit.setForeground(Color.WHITE);
            buttonQuit.setFont(new Font("Arial", Font.BOLD, 20));

            buttonQuit.addActionListener(e -> {
                pauseDialog.dispose();

            });
            pausePanel.add(pauseMessage, BorderLayout.CENTER);
            pausePanel.add(buttonQuit, BorderLayout.SOUTH);

            pauseDialog.setContentPane(pausePanel);
            pauseDialog.setVisible(true);
        } else {
            pauseDialog.dispose();
            pauseDialog = null;
        }
    }
}