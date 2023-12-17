package KCK_Projekt_2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Enemy extends JFrame {

    private JLabel infoLabel;
    private JLabel enemyInfoLabel;
    private JLabel playerHealthLabel;
    private JLabel enemyHealthLabel;
    private int enemyType;
    private int enemyDamage;
    private int enemyHealth;
    private int playerHealth;
    private int playerDamage;

    public Enemy(int damage, int health) {
        setTitle("Przeciwnik");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();

        setSize(width, height);
        setLocationRelativeTo(null);

        playerHealth=health;
        playerDamage=damage;


        JPanel mainPanel = new JPanel(new GridBagLayout());

        JLabel background = new JLabel(new ImageIcon(getClass().getResource("/img/action_panel.jpg")));

        ImageIcon playerImage = new ImageIcon(getClass().getResource("/img/player.png"));
        int playerWidth = 500;
        int playerHeight = 500;
        Random random = new Random();
        enemyType = random.nextInt(3) + 1;
        ImageIcon enemyImage = null;
        int enemyWidth = 800;
        int enemyHeight = 800;
        switch (enemyType) {
            case 1:
                enemyImage = new ImageIcon(getClass().getResource("/img/enemy1.png"));
                enemyDamage = 10;
                enemyHealth = 50;
                break;
            case 2:
                enemyImage = new ImageIcon(getClass().getResource("/img/enemy2.png"));
                enemyDamage = 15;
                enemyHealth = 70;
                break;
            case 3:
                enemyImage = new ImageIcon(getClass().getResource("/img/enemy3.png"));
                enemyDamage = 20;
                enemyHealth = 60;
                break;
        }

        Image scaledEnemyImage = enemyImage.getImage().getScaledInstance(enemyWidth, enemyHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledEnemyImage);

        Image scaledPlayerImage = playerImage.getImage().getScaledInstance(playerWidth, playerHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledPlayerIcon = new ImageIcon(scaledPlayerImage);

        JLabel enemyLabel = new JLabel(scaledIcon);
        GridBagConstraints gbcEnemy = new GridBagConstraints();
        gbcEnemy.gridx = 1;
        gbcEnemy.gridy = 0;
        gbcEnemy.anchor = GridBagConstraints.EAST;
        gbcEnemy.insets = new Insets(250, 0, 0, 0);

        mainPanel.add(enemyLabel, gbcEnemy);

        JLabel playerLabel = new JLabel(scaledPlayerIcon);
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

        GridBagConstraints gbcBackground= new GridBagConstraints();
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
                enemyInfoLabel.setText("Przeciwnik wykonuje: Lekki atak "+ enemyDamage + " OBR");
                playerDamageDealt = enemyDamage;
                break;
            case 1:
                enemyInfoLabel.setText("Przeciwnik wykonuje: Silny atak "+ enemyDamage*2 + " OBR");

                playerDamageDealt = enemyDamage * 2;
                break;
            case 2:
                enemyInfoLabel.setText("Przeciwnik wykonuje: Obrona");
                enemyDamageDealt = enemyDamageDealt/2;
                break;
            default:
                break;
        }

        // Logika akcji gracza
        if (playerAction.equals("Lekki atak")) {
            infoLabel.setText("Gracz wykonuje: " + playerAction+" "+ playerDamage + " OBR");
            enemyDamageDealt = playerDamage;
        } else if (playerAction.equals("Silny atak")) {
            infoLabel.setText("Gracz wykonuje: " + playerAction +" "+ playerDamage*2 + " OBR");
            enemyDamageDealt = playerDamage*2;
        } else if (playerAction.equals("Obrona")) {
            infoLabel.setText("Gracz wykonuje: " + playerAction);
            playerDamageDealt = playerDamageDealt/2;
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
            dispose();
        } else {
            System.out.println("Przeciwnik nadal ma " + enemyHealth + " zdrowia.");

        }
    }

}
