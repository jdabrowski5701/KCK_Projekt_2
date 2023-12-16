package KCK_Projekt_2;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IntroductionWindow extends JFrame {
    public IntroductionWindow(String playerName) {
        setTitle("Wprowadzenie do gry");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 750);
        setLocationRelativeTo(null);

        // Panel główny
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Dodanie tła graficznego
        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/img/pause.jpg"));
        JLabel background = new JLabel(backgroundImage);
        panel.add(background, BorderLayout.CENTER);
        background.setLayout(new BorderLayout());

        // Tekst wprowadzenia
        JLabel introLabel = new JLabel("<html><div style='text-align: center; font-family: Arial;'><span style='font-size: 18px; color: white;'>" +
                "Witaj " + playerName + "!<br><br> Opowieści o twoich wyprawach obiegły już cały świat. " +
                "Jednak teraz nadchodzi największe wyzwanie w twoim życiu. Na północy odkryto wejście do zapomnianego lochu. " +
                "Wielu śmiałków szukało tego miejsca. Ludzie myśleli, że to tylko legendy.<br> Ale teraz stoisz przed wejściem. " +
                "Chcesz być tym, kto zdobędzie wieczną chwałę!<br> Otwierasz wrota i wchodzisz do środka.<br><br>" +
                "Twoim celem jest dotarcie do środka lochu, pokonanie wszelkich przeszkód i ostateczny pojedynek z największym przeciwnikiem – Strażnikiem Wiecznego Lochu.<br>" +
                "Czy jesteś gotowy na to wyzwanie? Nadszedł czas, by rozpocząć nową przygodę!</html>");

        introLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Przycisk rozpoczęcia gry
        JButton startButton = new JButton("Zaczynamy");
        startButton.setIcon(new ImageIcon(MainMenu.class.getResource("/img/menu_button.png")));
        startButton.setHorizontalTextPosition(SwingConstants.CENTER);
        startButton.setVerticalTextPosition(SwingConstants.CENTER);
        startButton.setOpaque(false);
        startButton.setContentAreaFilled(false);
        startButton.setBorderPainted(false);
        startButton.setForeground(Color.WHITE);
        startButton.setFont(new Font("Arial", Font.BOLD, 20));
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                GameEngine gameEngine = new GameEngine(playerName);
            }
        });

        // Dodanie tekstu i przycisku na tle grafiki
        background.add(introLabel, BorderLayout.CENTER);
        background.add(startButton, BorderLayout.SOUTH);

        // Dodanie panelu do okna
        add(panel);
        setVisible(true);
    }
}
