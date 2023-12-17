package KCK_Projekt_2;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EndingWindow extends JFrame {
    public EndingWindow(String playerName) {
        setTitle("Koniec gry");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 750);
        setUndecorated(true);
        setLocationRelativeTo(null);

        // Panel główny
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Dodanie tła graficznego
        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/img/introduction.jpg"));
        JLabel background = new JLabel(backgroundImage);
        panel.add(background, BorderLayout.CENTER);
        background.setLayout(new BorderLayout());

        // Tekst wprowadzenia
        JLabel introLabel = new JLabel("<html><div style='text-align: center; font-family: Arial;'><span style='font-size: 18px; color: white;'>" +
                "Gratulacje " + playerName + "!<br><br> Udało ci się przejść cały loch. " +
                "O twoich przygodach będą pisane legendy. Pokonałeś Strażnika Wiecznego Lochu. " +
                "Po walce duch Strażnika wręczył ci klucz. Powiedział że teraz należy do ciebie. Do czego może służyć...<br> " +
                "Póki co to zostaje tajemnicą. Zabierasz klucz oraz łupy i opuszczasz lochy.<br><br>" +
                "Zdobyte monety: "+GameEngine.coins +"</html>");


        introLabel.setHorizontalAlignment(SwingConstants.CENTER);


        JButton endButton = new JButton("Wyjście");
        endButton.setIcon(new ImageIcon(MainMenu.class.getResource("/img/menu_button.png")));
        endButton.setHorizontalTextPosition(SwingConstants.CENTER);
        endButton.setVerticalTextPosition(SwingConstants.CENTER);
        endButton.setOpaque(false);
        endButton.setContentAreaFilled(false);
        endButton.setBorderPainted(false);
        endButton.setForeground(Color.WHITE);
        endButton.setFont(new Font("Arial", Font.BOLD, 20));
        endButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
                dispose();

            }
        });

        // Dodanie tekstu i przycisku na tle grafiki
        background.add(introLabel, BorderLayout.CENTER);
        background.add(endButton, BorderLayout.SOUTH);

        // Dodanie panelu do okna
        add(panel);
        setVisible(true);
    }
}
