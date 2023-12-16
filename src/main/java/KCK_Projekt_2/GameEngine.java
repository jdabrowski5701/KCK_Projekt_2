package KCK_Projekt_2;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameEngine extends JFrame implements KeyListener {
    private int playerX;
    private int playerY;
    private char[][] board;
    private JDialog pauseDialog;

    public GameEngine(String playerName) {
        super("Gra - " + playerName);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addKeyListener(this);

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();

        if (!gd.isFullScreenSupported()) {
            System.out.println("Pełny ekran nie jest obsługiwany");
            System.exit(0);
        }

        gd.setFullScreenWindow(this);


        initializeBoard();
        playerY = 5;
        playerX = 5;
        board[playerY][playerX] = 'O';

        JPanel gamePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawBoard(g);
            }
        };
        gamePanel.setPreferredSize(new Dimension(1920, 1080));
        add(gamePanel);

        setVisible(true);
    }

    private void initializeBoard() {
        board = new char[25][48];
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[y].length; x++) {
                if (y == 0 || y == board.length - 1 || x == 0 || x == board[y].length - 1) {
                    board[y][x] = '#';
                } else {
                    board[y][x] = ' ';
                }
            }
        }
    }

    private void drawBoard(Graphics g) {
        int tileSize = 40;
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[y].length; x++) {
                if (board[y][x] == '#') {
                    g.setColor(Color.BLACK);
                    g.fillRect(x * tileSize, y * tileSize, tileSize, tileSize);
                } else if (board[y][x] == 'O') {
                    g.setColor(Color.RED);
                    g.fillRect(x * tileSize, y * tileSize, tileSize, tileSize);
                }
                // Add more conditions for other elements on the board
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

        if (newX >= 0 && newX < board[0].length && newY >= 0 && newY < board.length && board[newY][newX] != '#') {
            board[playerY][playerX] = ' ';
            playerX = newX;
            playerY = newY;
            board[playerY][playerX] = 'O';
        }
    }

    private void showPauseMenu() {
        if (pauseDialog == null || !pauseDialog.isDisplayable()) {
            pauseDialog = new JDialog(this, "Pauza", Dialog.ModalityType.APPLICATION_MODAL);
            pauseDialog.setSize(400, 500);
            pauseDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
            pauseDialog.setResizable(false);
            pauseDialog.setLocationRelativeTo(null);

            ImageIcon backgroundIcon = new ImageIcon(MainMenu.class.getResource("/img/pause.jpg"));
            Image img = backgroundIcon.getImage().getScaledInstance(800, 600, Image.SCALE_SMOOTH);
            backgroundIcon = new ImageIcon(img);
            JLabel backgroundLabel = new JLabel(backgroundIcon);
            backgroundLabel.setLayout(new BorderLayout());

            JPanel pausePanel = new JPanel(new GridLayout(0, 1));
            JButton buttonResume = new JButton("Wznów");
            JButton buttonGuide = new JButton("Instrukcja");
            JButton buttonQuit = new JButton("Wyjście");

            buttonResume.setIcon(new ImageIcon(MainMenu.class.getResource("/img/menu_button.png")));
            buttonResume.setHorizontalTextPosition(SwingConstants.CENTER);
            buttonResume.setVerticalTextPosition(SwingConstants.CENTER);
            buttonResume.setOpaque(false);
            buttonResume.setContentAreaFilled(false);
            buttonResume.setBorderPainted(false);
            buttonResume.setForeground(Color.WHITE);
            buttonResume.setFont(new Font("Arial", Font.BOLD, 20));

            buttonGuide.setIcon(new ImageIcon(MainMenu.class.getResource("/img/menu_button.png")));
            buttonGuide.setHorizontalTextPosition(SwingConstants.CENTER);
            buttonGuide.setVerticalTextPosition(SwingConstants.CENTER);
            buttonGuide.setOpaque(false);
            buttonGuide.setContentAreaFilled(false);
            buttonGuide.setBorderPainted(false);
            buttonGuide.setForeground(Color.WHITE);
            buttonGuide.setFont(new Font("Arial", Font.BOLD, 20));

            buttonQuit.setIcon(new ImageIcon(MainMenu.class.getResource("/img/menu_button.png")));
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
                        "Celem gry jest dotarcie do zamku i pokonanie głównego przeciwnika:\n" +
                                "1. Wybierz nową grę.\n" +
                                "2. Używaj strzałek do poruszania się po mapie.\n" +
                                "3. Wybieraj strzałkami opcje do walki z przeciwnikami.\n" +
                                "4. Wybieraj strzałkami przedmioty u kupca.\n" +
                                "5. Ogniska służą do odpoczynku.",
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

            backgroundLabel.add(pausePanel, BorderLayout.CENTER);

            pauseDialog.setContentPane(backgroundLabel);
            pauseDialog.setVisible(true);
        } else {
            pauseDialog.dispose();
            pauseDialog = null;
        }
    }
}