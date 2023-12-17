package KCK_Projekt_2;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class GameEngine extends JFrame implements KeyListener {

    private Image wallImage;
    private BufferedImage floorImage;
    private Image bossImage;
    private Image campfireImage;
    private Image enemyImage;
    private Image chestImage;
    private Image hiddenImage;
    private int playerX;
    private int playerY;
    private char[][] board;
    private JDialog pauseDialog;
    private ArrayList<Point> campfires;
    private ArrayList<Point> enemies;
    private ArrayList<Point> chests;
    private char hiddenIcon = '.';
    private JLabel healthLabel;
    private JLabel coinsLabel;


    private int health;
    private int maxHealth;
    private int coins;
    private int damage;
    private int chestPrize;

    private enum PlayerAnimationState {
        LEFT,
        RIGHT,
        UP,
        DOWN
    }

    private PlayerAnimationState animationState = PlayerAnimationState.DOWN;
    private int currentFrame = 0;


    private BufferedImage[] playerLeftFrames;
    private BufferedImage[] playerRightFrames;
    private BufferedImage[] playerUpFrames;
    private BufferedImage[] playerDownFrames;

    public GameEngine(String playerName) {
        super("Gra - " + playerName);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addKeyListener(this);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();

        setSize(width, height);
        setLocationRelativeTo(null);

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();

        /*
        if (!gd.isFullScreenSupported()) {
            System.out.println("Pełny ekran nie jest obsługiwany");
            System.exit(0);
        }

        gd.setFullScreenWindow(this);
        */
        wallImage = createImage("/img/wall.png");
        floorImage = createBufferedImage("/img/floor.png");
        hiddenImage = createImage("/img/hidden.png");
        bossImage = createImage("/img/boss.png");
        campfireImage = createImage("/img/campfire.png");
        enemyImage = createImage("/img/enemy.png");
        chestImage = createImage("/img/chest.png");

        playerLeftFrames = loadPlayerFrames("/img/player_left.png", 4);
        playerRightFrames = loadPlayerFrames("/img/player_right.png", 4);
        playerUpFrames = loadPlayerFrames("/img/player_up.png", 4);
        playerDownFrames = loadPlayerFrames("/img/player_down.png", 4);

        health = 50;
        maxHealth = 100;
        coins = 0;
        damage = 10;
        chestPrize = 0;

        initializeBoard();


        JPanel gamePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawBackground(g);
                drawBoard(g);
            }
        };
        gamePanel.setLayout(new BorderLayout());
        gamePanel.setPreferredSize(new Dimension(1920, 1080));
        add(gamePanel);

        JPanel statsPanel = new JPanel(new GridBagLayout());
        statsPanel.setPreferredSize(new Dimension(1920, 80));
        statsPanel.setBackground(Color.DARK_GRAY);

        JPanel labelsBackground = new JPanel(new GridBagLayout());
        labelsBackground.setPreferredSize(new Dimension(450, 80));
        labelsBackground.setBackground(Color.DARK_GRAY);


        ImageIcon playerIcon = createImageIcon("/img/player_icon.png");
        if (playerIcon != null) {
            playerIcon = new ImageIcon(playerIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH));
            JLabel playerLabel = new JLabel(playerIcon);

            GridBagConstraints gbcPlayerLabel = new GridBagConstraints();
            gbcPlayerLabel.gridx = 0;
            gbcPlayerLabel.gridy = 0;
            gbcPlayerLabel.anchor = GridBagConstraints.WEST;
            gbcPlayerLabel.insets = new Insets(0, 10, 0, 10);
            labelsBackground.add(playerLabel, gbcPlayerLabel);
        }



        healthLabel = new JLabel("Zdrowie: " + health);
        healthLabel.setForeground(Color.WHITE);
        healthLabel.setFont(new Font("Arial", Font.BOLD, 18));
        GridBagConstraints gbcHealthLabel = new GridBagConstraints();
        gbcHealthLabel.gridx = 1;
        gbcHealthLabel.gridy = 0;
        gbcHealthLabel.anchor = GridBagConstraints.WEST;
        gbcHealthLabel.insets = new Insets(0, 0, 0, 10);
        ImageIcon healthIcon = createImageIcon("/img/heart.png");
        if (healthIcon != null) {
            healthIcon = new ImageIcon(healthIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        }
            healthLabel.setIcon(healthIcon);
        labelsBackground.add(healthLabel, gbcHealthLabel);



        coinsLabel = new JLabel("Monety: " + coins);
        coinsLabel.setForeground(Color.WHITE);
        coinsLabel.setFont(new Font("Arial", Font.BOLD, 18));
        GridBagConstraints gbcCoinsLabel = new GridBagConstraints();
        gbcCoinsLabel.gridx = 3;
        gbcCoinsLabel.gridy = 0;
        gbcCoinsLabel.anchor = GridBagConstraints.WEST;
        gbcCoinsLabel.insets = new Insets(0, 10, 0, 10);
        labelsBackground.add(coinsLabel, gbcCoinsLabel);


        ImageIcon[] coinAnimationFrames = new ImageIcon[4];

        try {
            BufferedImage image = ImageIO.read(GameEngine.class.getResource("/img/coins.png"));

            int frameWidth = image.getWidth();
            int frameHeight = image.getHeight() / 4;

            for (int i = 0; i < 4; i++) {
                coinAnimationFrames[i] = new ImageIcon(image.getSubimage(0, i * frameHeight, frameWidth, frameHeight));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        Timer coinAnimationTimer = new Timer(120, new ActionListener() {
            int currentFrame = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                coinsLabel.setIcon(coinAnimationFrames[currentFrame]);
                currentFrame = (currentFrame + 1) % 4; // Zakładamy 4 klatki animacji monet
            }
        });
        coinAnimationTimer.start();


        GridBagConstraints gbcLabelsBackground = new GridBagConstraints();
        gbcLabelsBackground.gridx = 0;
        gbcLabelsBackground.gridy = 0;
        gbcLabelsBackground.anchor = GridBagConstraints.WEST;
        statsPanel.add(labelsBackground, gbcLabelsBackground);

        gamePanel.add(statsPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    private BufferedImage[] loadPlayerFrames(String path, int frameCount) {
        BufferedImage[] frames = new BufferedImage[frameCount];
        try {
            BufferedImage sheet = ImageIO.read(GameEngine.class.getResource(path));
            int frameWidth = sheet.getWidth() / frameCount;
            int frameHeight = sheet.getHeight();

            for (int i = 0; i < frameCount; i++) {
                frames[i] = sheet.getSubimage(i * frameWidth, 0, frameWidth, frameHeight);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return frames;
    }
    private BufferedImage createBufferedImage(String path) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(GameEngine.class.getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }
    private Image createImage(String path) {
        Image img = null;
        try {
            img = ImageIO.read(GameEngine.class.getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }
    protected ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Nie można odnaleźć pliku: " + path);
            return null;
        }
    }

    private void updateStats() {
        healthLabel.setText("Zdrowie: " + health);
        coinsLabel.setText("Monety: " + coins);
    }

    private void initializeBoard() {
        board = new char[25][48];
        campfires = new ArrayList<>();
        enemies = new ArrayList<>();
        chests = new ArrayList<>();


        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[y].length; x++) {
                if (y == 0 || y == board.length - 1 || x == 0 || x == board[y].length - 1) {
                    board[y][x] = '#'; // Utworzenie obramowania planszy
                } else {
                    board[y][x] = ' ';
                }
            }
        }

        // Utworzenie ścian poziomych
        for (int x = 0; x <= 47; x++) {
            board[6][x] = '#';
            for(int xt = 3; xt<7; xt++){board[6][xt] = ' ';}
            for(int xt = 30; xt<34; xt++){board[6][xt] = ' ';}
            for(int xt = 39; xt<45; xt++){board[6][xt] = ' ';}

            board[12][x] = '#';
            for(int xt = 3; xt<7; xt++){board[12][xt] = ' ';}
            for(int xt = 12; xt<16; xt++){board[12][xt] = ' ';}
            for(int xt = 21; xt<25; xt++){board[12][xt] = ' ';}
            for(int xt = 39; xt<45; xt++){board[12][xt] = ' ';}

            board[18][x] = '#';
            for(int xt = 12; xt<16; xt++){board[18][xt] = ' ';}
            for(int xt = 21; xt<25; xt++){board[18][xt] = ' ';}
            for(int xt = 30; xt<34; xt++){board[18][xt] = ' ';}
            for(int xt = 39; xt<45; xt++){board[18][xt] = ' ';}


        }

       // Utworzenie ścian pionowych
        for (int y = 0; y < 24; y++) {
            board[y][9] = '#';
            for(int yt =8; yt<11; yt++){board[yt][9] = ' ';}
            for(int yt =20; yt<23; yt++){board[yt][9] = ' ';}

            board[y][18] = '#';
            for(int yt =2; yt<5; yt++){board[yt][18] = ' ';}
            for(int yt =8; yt<11; yt++){board[yt][18] = ' ';}

            board[y][27] = '#';
            for(int yt =2; yt<5; yt++){board[yt][27] = ' ';}
            for(int yt =8; yt<11; yt++){board[yt][27] = ' ';}
            for(int yt =20; yt<23; yt++){board[yt][27] = ' ';}

            board[y][36] = '#';
            for(int yt =14; yt<17; yt++){board[yt][36] = ' ';}

        }


        int bossX = 41;
        int bossY = 3;
        board[bossY][bossX] = 'X';


        addObjectsToBoard(campfires, 6, 'H');


        addObjectsToBoard(enemies, 12, 'E');


        addObjectsToBoard(chests, 4, 'M');


        // Ustawienie początkowej pozycji gracza
        playerY = 21;
        playerX = 5;
        board[playerY][playerX] = 'O';

        updateHiddenIcon(campfires, '.');
        updateHiddenIcon(enemies, '.');
        updateHiddenIcon(chests, '.');
    }


    private void addObjectsToBoard(ArrayList<Point> list, int quantity, char symbol) {
        while (list.size() < quantity) {
            int randomX = (int) (Math.random() * (board[0].length - 2)) + 1;
            int randomY = (int) (Math.random() * (board.length - 2)) + 1;

            if (board[randomY][randomX] == ' ') {
                list.add(new Point(randomX, randomY));
                board[randomY][randomX] = symbol;
            }
        }
    }

    private void drawBackground(Graphics g) {
        int tileSize = 40;

        // Rysowanie tła planszy
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[y].length; x++) {
                g.drawImage(floorImage, x * tileSize, y * tileSize, tileSize, tileSize, this);
            }
        }
    }
    private void drawBoard(Graphics g) {
        int tileSize = 40;
        BufferedImage[] currentFrames = null;
        switch (animationState) {
            case LEFT:
                currentFrames = playerLeftFrames;
                break;
            case RIGHT:
                currentFrames = playerRightFrames;
                break;
            case UP:
                currentFrames = playerUpFrames;
                break;
            case DOWN:
                currentFrames = playerDownFrames;
                break;
        }

        if (currentFrames != null && currentFrame >= 0 && currentFrame < currentFrames.length) {
            g.drawImage(currentFrames[currentFrame], playerX * tileSize, playerY * tileSize, tileSize, tileSize, this);
        }

        // Inkrementacja aktualnej klatki animacji
        currentFrame = (currentFrame + 1) % currentFrames.length;
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[y].length; x++) {
                if (board[y][x] == '#') {
                    g.drawImage(wallImage, x * tileSize, y * tileSize, tileSize, tileSize, this);
                } else if (board[y][x] == 'O') {
                   // g.drawImage(playerImage, x * tileSize, y * tileSize, tileSize, tileSize, this);
                } else if (board[y][x] == 'H') {
                    g.drawImage(campfireImage, x * tileSize, y * tileSize, tileSize, tileSize, this);
                } else if (board[y][x] == 'E') {
                    g.drawImage(enemyImage, x * tileSize, y * tileSize, tileSize, tileSize, this);
                } else if (board[y][x] == 'M') {
                    g.drawImage(chestImage, x * tileSize, y * tileSize, tileSize, tileSize, this);
                }else if (board[y][x] == 'X') {
                    g.drawImage(bossImage, x * tileSize, y * tileSize, tileSize, tileSize, this);
                } else if (board[y][x] == '.') {
                    g.drawImage(hiddenImage, x * tileSize, y * tileSize, tileSize, tileSize, this);
                }
            }
        }
        updateStats();
    }
    private void updateHiddenIcon(ArrayList<Point> elements, char icon) {
        for (Point element : elements) {
            int elementX = (int) element.getX();
            int elementY = (int) element.getY();


            int playerDistance = Math.abs(playerX - elementX) + Math.abs(playerY - elementY);


            if (playerDistance > 5 && (board[elementY][elementX] == '.' || board[elementY][elementX] == 'H'
                    || board[elementY][elementX] == 'M' || board[elementY][elementX] == 'E')) {
                board[elementY][elementX] = icon;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_ESCAPE) {
            showPauseMenu();
        } else if (key == KeyEvent.VK_LEFT) {
            movePlayer(-1, 0);
        } else if (key == KeyEvent.VK_RIGHT) {
            movePlayer(1, 0);
        } else if (key == KeyEvent.VK_UP) {
            movePlayer(0, -1);
        } else if (key == KeyEvent.VK_DOWN) {
            movePlayer(0, 1);
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    private void movePlayer(int dx, int dy) {
        int newX = playerX + dx;
        int newY = playerY + dy;



        if (dx < 0) {
            animationState = PlayerAnimationState.LEFT;
        } else if (dx > 0) {
            animationState = PlayerAnimationState.RIGHT;
        } else if (dy < 0) {
            animationState = PlayerAnimationState.UP;
        } else if (dy > 0) {
            animationState = PlayerAnimationState.DOWN;
        }
        updateHiddenIcon(campfires, 'H'); // Aktualizacja ikon ognisk
        updateHiddenIcon(enemies, 'E');  // Aktualizacja ikon przeciwników
        updateHiddenIcon(chests, 'M'); // Aktualizacja ikon skrzyń
        if (newX >= 0 && newX < board[0].length && newY >= 0 && newY < board.length && board[newY][newX] != '#') {
            if (newX >= 0 && newX < board[0].length && newY >= 0 && newY < board.length) {
                if (board[newY][newX] == 'H') {
                    // Obsługa ogniska
                    handleCampfire();
                    board[newY][newX] = ' ';
                } else if (board[newY][newX] == 'E') {
                    // Obsługa walki z przeciwnikiem
                    handleEnemy();
                    board[newY][newX] = ' ';
                } else if (board[newY][newX] == 'M') {
                    // Obsługa skrzyni
                    handleChest();
                    board[newY][newX] = ' ';
                } else if (board[newY][newX] == 'X') {
                    // Obsługa bossa
                    handleBoss();
                    board[newY][newX] = ' ';
                }
            }

            board[playerY][playerX] = ' '; // Stara pozycja gracza
            playerX = newX;
            playerY = newY;
            board[playerY][playerX] = 'O'; // Nowa pozycja gracza

            updateHiddenIcon(campfires, '.');
            updateHiddenIcon(enemies, '.');
            updateHiddenIcon(chests, '.');
        }
    }

    private void handleCampfire() {
        System.out.println("Ognisko");
        Campfire campfire = new Campfire();
        health = maxHealth;
        updateStats();

    }

    private void handleEnemy(){
        System.out.println("Przeciwnik");
        Enemy enemy = new Enemy(damage, health);
        updateStats();
    }
    private void handleChest(){
        System.out.println("Skrzynia");
        Chest chest = new Chest(chestPrize);
        switch (chestPrize){
            case 0:
                maxHealth = 150;
                health = maxHealth;
                break;
            case 1:
                damage = 20;
                break;
            case 2:
                coins += 500;
                break;
            case 3:
                maxHealth = 200;
                health = maxHealth;
                break;

        }
        chestPrize++;
    }
    private void handleBoss(){System.out.println("Boss");}

    private void showPauseMenu() {
        if (pauseDialog == null || !pauseDialog.isDisplayable()) {
            pauseDialog = new JDialog(this, "Pauza", Dialog.ModalityType.APPLICATION_MODAL);
            pauseDialog.setSize(400, 500);
            pauseDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
            pauseDialog.setResizable(false);
            pauseDialog.setLocationRelativeTo(null);

            JPanel pausePanel = new JPanel(new GridLayout(0, 1));
            pausePanel.setBackground(new Color(64, 64, 64));

            JButton buttonResume = new JButton("Wznów");
            JButton buttonGuide = new JButton("Instrukcja");
            JButton buttonQuit = new JButton("Wyjście");

            buttonResume.setIcon(new ImageIcon(MainMenu.class.getResource("/img/menu_button1.png")));
            buttonResume.setHorizontalTextPosition(SwingConstants.CENTER);
            buttonResume.setVerticalTextPosition(SwingConstants.CENTER);
            buttonResume.setOpaque(false);
            buttonResume.setContentAreaFilled(false);
            buttonResume.setBorderPainted(false);
            buttonResume.setForeground(Color.WHITE);
            buttonResume.setFont(new Font("Arial", Font.BOLD, 20));

            buttonGuide.setIcon(new ImageIcon(MainMenu.class.getResource("/img/menu_button1.png")));
            buttonGuide.setHorizontalTextPosition(SwingConstants.CENTER);
            buttonGuide.setVerticalTextPosition(SwingConstants.CENTER);
            buttonGuide.setOpaque(false);
            buttonGuide.setContentAreaFilled(false);
            buttonGuide.setBorderPainted(false);
            buttonGuide.setForeground(Color.WHITE);
            buttonGuide.setFont(new Font("Arial", Font.BOLD, 20));

            buttonQuit.setIcon(new ImageIcon(MainMenu.class.getResource("/img/menu_button1.png")));
            buttonQuit.setHorizontalTextPosition(SwingConstants.CENTER);
            buttonQuit.setVerticalTextPosition(SwingConstants.CENTER);
            buttonQuit.setOpaque(false);
            buttonQuit.setContentAreaFilled(false);
            buttonQuit.setBorderPainted(false);
            buttonQuit.setForeground(Color.WHITE);
            buttonQuit.setFont(new Font("Arial", Font.BOLD, 20));

            buttonResume.addActionListener(e -> {
                pauseDialog.dispose();
            });

            buttonGuide.addActionListener(e -> {
                JOptionPane.showMessageDialog(pauseDialog,
                        "Celem gry jest przejście całego lochu i pokonanie bossa\n" +
                                "1. Wybierz nową grę.\n" +
                                "2. Używaj strzałek do poruszania się po mapie.\n" +
                                "3. Za znakiem zapytania ukryte są elementy lochu.\n" +
                                "4. Przy ognisku możesz się wyleczyć i odpocząć.\n" +
                                "5. Czaszka oznacza przeciwnika, walcz dzielnie i wygrywaj!\n" +
                                "6. W skrzyniach czekają na ciebie niespodzianki.",
                        "Instrukcja", JOptionPane.INFORMATION_MESSAGE);
            });

            buttonQuit.addActionListener(e -> {
                pauseDialog.dispose();
                dispose();
                System.exit(0);
            });

            pausePanel.add(buttonResume);
            pausePanel.add(buttonGuide);
            pausePanel.add(buttonQuit);

            pauseDialog.setContentPane(pausePanel);
            pauseDialog.setVisible(true);
        } else {
            pauseDialog.dispose();
            pauseDialog = null;
        }
    }
}