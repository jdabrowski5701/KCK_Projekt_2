package KCK_Projekt_2;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class Boss extends JFrame {

    private BufferedImage playerAttackSheet;
    private ImageIcon[] playerAttackFrames;

    private BufferedImage enemyAttackSheet;
    private ImageIcon[] enemyAttackFrames;

    private int currentFrameIndex = 0;
    private JLabel playerLabel;
    private JLabel enemyLabel;
    private int playerWidth = 500;
    private int playerHeight = 500;
    private int enemyWidth = 800;
    private int enemyHeight = 800;
    private JPanel mainPanel;
    private JLabel infoLabel;
    private JLabel enemyInfoLabel;
    private JLabel playerHealthLabel;
    private JLabel enemyHealthLabel;
    private int enemyDamage;
    private int enemyHealth;
    private int playerHealth;
    private int playerDamage;

    public Boss(int damage, int health) {
        setTitle("Boss");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();

        setSize(width, height);
        setLocationRelativeTo(null);

        playerHealth = health;
        playerDamage = damage;


        mainPanel = new JPanel(new GridBagLayout());

        JLabel background = new JLabel(new ImageIcon(getClass().getResource("/img/action_panel.jpg")));

        ImageIcon playerImage = new ImageIcon(getClass().getResource("/img/player.png"));

        try {
            playerAttackSheet = ImageIO.read(getClass().getResource("/img/player_attacks.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        int frameWidth = playerAttackSheet.getWidth() / 7;
        int frameHeight = playerAttackSheet.getHeight();

        playerAttackFrames = new ImageIcon[7];


        for (int i = 0; i < 7; i++) {
            BufferedImage frameImage = playerAttackSheet.getSubimage(i * frameWidth, 0, frameWidth, frameHeight);
            playerAttackFrames[i] = new ImageIcon(frameImage);
        }


        ImageIcon enemyImage = null;

                enemyImage = new ImageIcon(getClass().getResource("/img/boss_model.png"));
                enemyDamage = 10;
                enemyHealth = 150;

        Image scaledEnemyImage = enemyImage.getImage().getScaledInstance(enemyWidth, enemyHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledEnemyImage);

        Image scaledPlayerImage = playerImage.getImage().getScaledInstance(playerWidth, playerHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledPlayerIcon = new ImageIcon(scaledPlayerImage);

        enemyLabel = new JLabel(scaledIcon);
        GridBagConstraints gbcEnemy = new GridBagConstraints();
        gbcEnemy.gridx = 1;
        gbcEnemy.gridy = 0;
        gbcEnemy.anchor = GridBagConstraints.EAST;
        gbcEnemy.insets = new Insets(200, 0, 0, 0);

        mainPanel.add(enemyLabel, gbcEnemy);

        playerLabel = new JLabel(scaledPlayerIcon);
        GridBagConstraints gbcPlayer = new GridBagConstraints();
        gbcPlayer.gridx = 0;
        gbcPlayer.gridy = 0;
        gbcPlayer.anchor = GridBagConstraints.WEST;
        gbcPlayer.insets = new Insets(240, 200, 0, 0);

        mainPanel.add(playerLabel, gbcPlayer);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(Color.BLACK);
        JButton lightAttackButton = new JButton("Lekki atak");
        lightAttackButton.setIcon(new ImageIcon(MainMenu.class.getResource("/img/menu_button.png")));
        lightAttackButton.setHorizontalTextPosition(SwingConstants.CENTER);
        lightAttackButton.setVerticalTextPosition(SwingConstants.CENTER);
        lightAttackButton.setOpaque(false);
        lightAttackButton.setContentAreaFilled(false);
        lightAttackButton.setBorderPainted(false);
        lightAttackButton.setForeground(Color.WHITE);
        lightAttackButton.setFont(new Font("Arial", Font.BOLD, 20));
        lightAttackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handlePlayerAction("Lekki atak");
            }
        });
        buttonPanel.add(lightAttackButton);

        JButton strongAttackButton = new JButton("Silny atak");
        strongAttackButton.setIcon(new ImageIcon(MainMenu.class.getResource("/img/menu_button.png")));
        strongAttackButton.setHorizontalTextPosition(SwingConstants.CENTER);
        strongAttackButton.setVerticalTextPosition(SwingConstants.CENTER);
        strongAttackButton.setOpaque(false);
        strongAttackButton.setContentAreaFilled(false);
        strongAttackButton.setBorderPainted(false);
        strongAttackButton.setForeground(Color.WHITE);
        strongAttackButton.setFont(new Font("Arial", Font.BOLD, 20));
        strongAttackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handlePlayerAction("Silny atak");
            }
        });
        buttonPanel.add(strongAttackButton);

        JButton defendButton = new JButton("Obrona");
        defendButton.setIcon(new ImageIcon(MainMenu.class.getResource("/img/menu_button.png")));
        defendButton.setHorizontalTextPosition(SwingConstants.CENTER);
        defendButton.setVerticalTextPosition(SwingConstants.CENTER);
        defendButton.setOpaque(false);
        defendButton.setContentAreaFilled(false);
        defendButton.setBorderPainted(false);
        defendButton.setForeground(Color.WHITE);
        defendButton.setFont(new Font("Arial", Font.BOLD, 20));
        defendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handlePlayerAction("Obrona");
            }
        });
        buttonPanel.add(defendButton);

        infoLabel = new JLabel("", SwingConstants.LEFT);
        infoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        infoLabel.setForeground(Color.WHITE);
        infoLabel.setBackground(Color.BLACK);
        GridBagConstraints gbcInfoLabel = new GridBagConstraints();
        gbcInfoLabel.gridx = 0;
        gbcInfoLabel.gridy = 0;
        gbcInfoLabel.anchor = GridBagConstraints.WEST;
        gbcInfoLabel.insets = new Insets(860, 20, 0, 0);
        mainPanel.add(infoLabel, gbcInfoLabel);

        enemyInfoLabel = new JLabel("", SwingConstants.RIGHT);
        enemyInfoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        enemyInfoLabel.setForeground(Color.WHITE);
        enemyInfoLabel.setBackground(Color.BLACK);
        GridBagConstraints gbcEnemyInfoLabel = new GridBagConstraints();
        gbcEnemyInfoLabel.gridx = 1;
        gbcEnemyInfoLabel.gridy = 0;
        gbcEnemyInfoLabel.anchor = GridBagConstraints.EAST;
        gbcEnemyInfoLabel.insets = new Insets(860, 0, 0, 20);
        mainPanel.add(enemyInfoLabel, gbcEnemyInfoLabel);

        playerHealthLabel = new JLabel("Zdrowie gracza: " + playerHealth, SwingConstants.LEFT);
        playerHealthLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        playerHealthLabel.setForeground(Color.WHITE);
        playerHealthLabel.setBackground(Color.BLACK);
        GridBagConstraints gbcPlayerHealthLabel = new GridBagConstraints();
        gbcPlayerHealthLabel.gridx = 0;
        gbcPlayerHealthLabel.gridy = 0;
        gbcPlayerHealthLabel.anchor = GridBagConstraints.WEST;
        gbcPlayerHealthLabel.insets = new Insets(820, 20, 0, 0);
        mainPanel.add(playerHealthLabel, gbcPlayerHealthLabel);

        enemyHealthLabel = new JLabel("Zdrowie przeciwnika: " + enemyHealth, SwingConstants.RIGHT);
        enemyHealthLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        enemyHealthLabel.setForeground(Color.WHITE);
        enemyHealthLabel.setBackground(Color.BLACK);
        GridBagConstraints gbcEnemyHealthLabel = new GridBagConstraints();
        gbcEnemyHealthLabel.gridx = 1;
        gbcEnemyHealthLabel.gridy = 0;
        gbcEnemyHealthLabel.anchor = GridBagConstraints.EAST;
        gbcEnemyHealthLabel.insets = new Insets(820, 0, 0, 20);
        mainPanel.add(enemyHealthLabel, gbcEnemyHealthLabel);

        GridBagConstraints gbcBackground = new GridBagConstraints();
        gbcBackground.gridx = 0;
        gbcBackground.gridy = 0;
        gbcBackground.gridwidth = GridBagConstraints.REMAINDER;
        gbcBackground.fill = GridBagConstraints.BOTH;
        mainPanel.add(background, gbcBackground);

        GridBagConstraints gbcButtonPanel = new GridBagConstraints();
        gbcButtonPanel.gridx = 0;
        gbcButtonPanel.gridy = 1;
        gbcButtonPanel.gridwidth = GridBagConstraints.REMAINDER;
        gbcButtonPanel.fill = GridBagConstraints.HORIZONTAL;
        gbcButtonPanel.anchor = GridBagConstraints.SOUTH;
        gbcButtonPanel.insets = new Insets(0, 0, 100, 0);
        mainPanel.add(buttonPanel, gbcButtonPanel);

        add(mainPanel);

        setVisible(true);
    }

    public void handlePlayerAction(String playerAction) {
        System.out.println("Gracz wykonuje: " + playerAction);


        Random random = new Random();
        int playerDamageDealt = 0;
        int enemyDamageDealt = 0;

        // Logika akcji przeciwnika
        int enemyAction = random.nextInt(3);
        switch (enemyAction) {
            case 0:
                enemyInfoLabel.setText("Przeciwnik wykonuje: Lekki atak " + enemyDamage + " OBR");
                playerDamageDealt = enemyDamage;
                playEnemyAttackAnimation("Light");
                break;
            case 1:
                enemyInfoLabel.setText("Przeciwnik wykonuje: Silny atak " + enemyDamage * 2 + " OBR");
                playerDamageDealt = enemyDamage * 2;
                playEnemyAttackAnimation("Strong");
                break;
            case 2:
                enemyInfoLabel.setText("Przeciwnik wykonuje: Obrona");
                enemyDamageDealt = enemyDamageDealt / 2;
                break;
            default:
                break;
        }

        // Logika akcji gracza
        if (playerAction.equals("Lekki atak")) {
            infoLabel.setText("Gracz wykonuje: " + playerAction + " " + playerDamage + " OBR");
            enemyDamageDealt = playerDamage;
            playPlayerAttackAnimation();
        } else if (playerAction.equals("Silny atak")) {
            infoLabel.setText("Gracz wykonuje: " + playerAction + " " + playerDamage * 2 + " OBR");
            enemyDamageDealt = playerDamage * 2;
            playPlayerAttackAnimation();
        } else if (playerAction.equals("Obrona")) {
            infoLabel.setText("Gracz wykonuje: " + playerAction);
            playerDamageDealt = playerDamageDealt / 2;
        }


        enemyHealth -= enemyDamageDealt;
        enemyHealth = Math.max(0, enemyHealth);

        playerHealth -= playerDamageDealt;
        playerHealth = Math.max(0, playerHealth);


        playerHealthLabel.setText("Zdrowie gracza: " + playerHealth);
        enemyHealthLabel.setText("Zdrowie przeciwnika: " + enemyHealth);


        if (enemyHealth <= 0) {
            System.out.println("Wygrałeś! Przeciwnik został pokonany.");
            infoLabel.setText("Wygrałeś! Przeciwnik został pokonany.");
            //JOptionPane.showMessageDialog(this, "Wygrałeś! Przeciwnik został pokonany.", "Wygrana", JOptionPane.INFORMATION_MESSAGE);
            EndingWindow endWin = new EndingWindow(GameEngine.playername);
            dispose();
        } if(playerHealth <= 0) {
            System.out.println("Przegrałeś! Gracz został pokonany.");
            infoLabel.setText("Przegrałeś! Gracz został pokonany.");
            JOptionPane.showMessageDialog(this, "Przegrałeś! Niestety twoja przygoda się kończy", "Przegrana", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            System.exit(0);
        }
    }

    private void playPlayerAttackAnimation() {

        playerAttackFrames = new ImageIcon[7];
        int frameWidth = playerAttackSheet.getWidth() / 7;
        int frameHeight = playerAttackSheet.getHeight();


        for (int i = 0; i < 7; i++) {
            playerAttackFrames[i] = new ImageIcon(playerAttackSheet.getSubimage(i * frameWidth, 0, frameWidth, frameHeight)
                    .getScaledInstance(playerWidth, playerHeight, Image.SCALE_SMOOTH));
        }


        currentFrameIndex = 0;
        Timer timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentFrameIndex < playerAttackFrames.length) {
                    playerLabel.setIcon(playerAttackFrames[currentFrameIndex]);
                    currentFrameIndex++;
                } else {
                    currentFrameIndex = 0;
                    playerLabel.setIcon(playerAttackFrames[currentFrameIndex]);
                    ((Timer) e.getSource()).stop();
                }
            }
        });
        timer.start();
    }

    private void playEnemyAttackAnimation(String attackType) {
        try {

            String enemyAttackImage = "/img/boss" + attackType + "_attacks.png";
            enemyAttackSheet = ImageIO.read(getClass().getResource(enemyAttackImage));

            enemyAttackFrames = new ImageIcon[8];

            int frameWidth = enemyAttackSheet.getWidth() / 8;
            int frameHeight = enemyAttackSheet.getHeight();

            for (int i = 0; i < 8; i++) {
                BufferedImage frameImage = enemyAttackSheet.getSubimage(i * frameWidth, 0, frameWidth, frameHeight);
                enemyAttackFrames[i] = new ImageIcon(frameImage.getScaledInstance(enemyWidth, enemyHeight, Image.SCALE_SMOOTH));
            }


            enemyLabel.setIcon(enemyAttackFrames[0]);


            Timer timer = new Timer(100, new ActionListener() {
                int currentFrameIndex = enemyAttackFrames.length - 1;

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (currentFrameIndex >= 0) {
                        enemyLabel.setIcon(enemyAttackFrames[currentFrameIndex]);
                        currentFrameIndex--;
                    } else {
                        currentFrameIndex = enemyAttackFrames.length - 1;
                        enemyLabel.setIcon(enemyAttackFrames[currentFrameIndex]);
                        ((Timer) e.getSource()).stop();
                    }
                }
            });
            timer.setRepeats(true);
            timer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

