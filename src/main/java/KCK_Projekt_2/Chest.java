package KCK_Projekt_2;

import javax.swing.*;
import java.awt.*;

public class Chest extends JFrame {
    private JDialog chestDialog;
    public Chest(int chestPrize){
        showChestDialog(chestPrize);
    }

    private void showChestDialog(int number) {
        if (chestDialog == null || !chestDialog.isDisplayable()) {
            chestDialog = new JDialog(this, "Chest", Dialog.ModalityType.APPLICATION_MODAL);
            chestDialog.setSize(600, 250);
            chestDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
            chestDialog.setResizable(false);
            chestDialog.setLocationRelativeTo(null);
            chestDialog.setUndecorated(true);

            JPanel chestPanel = new JPanel(new BorderLayout());
            chestPanel.setBackground(new Color(64, 64, 64));


                JTextArea chestMessage = new JTextArea();
                chestMessage.setEditable(false);
                chestMessage.setOpaque(false);
                chestMessage.setForeground(Color.WHITE);
                chestMessage.setFont(new Font("Arial", Font.BOLD, 18));

                if(number == 0) {
                chestMessage.setText(
                        "Znajdujesz zamkniętą skrzynie... Czy to może być pułapka?\n" +
                                "Twoja ciekawość wygrywa i zaglądasz do środka...\n" +
                                "Mikstura! Przecież nie może to być nic złego...\n" +
                                "Po wypiciu mikstury czujesz sie bardziej wytrzymały.\n\n" +
                                "Zwiększono maksymalne zdrowie do 150!");

                }

            if(number == 1) {
                chestMessage.setText(
                        "Kolejna skrzynia! Ciekawe co bedzie w niej tym razem?\n" +
                                "Tym razem kufer wypełniony jest jakimś żelastwem...\n" +
                                "Ooo ostrzałka do miecza! Na pewno mi się przyda!\n" +
                                "Ostrzałka sprawia że twój miecz jest ostrzejszy niż kiedykolwiek.\n\n" +
                                "Zwiększono podstawowe obrażenia do 20!");

            }

            if(number == 2) {
                chestMessage.setText(
                        "Idąc ciemnym korytarzem zaczepiasz się o płytkę.\n" +
                                "Upadasz na ziemię. Klnąc pod nosem próbujesz wstać.\n" +
                                "Widzisz jednak że pod płytką o którą się zaczepiłeś coś jest...\n" +
                                "Monety! Czyli jednak masz szczęście w nieszczęściu...\n\n" +
                                "Znaleziono 500 monet!");

            }

            if(number == 3) {
                chestMessage.setText(
                        "Znajdujesz dziwny posążek. Wyglądem przypomina ci anioła.\n" +
                                "Jest jednak brudny. Dobrze że zabrałeś ze sobą ściereczkę.\n" +
                                "Po przetarciu posążka następuje błysk światła!\n" +
                                " Nie wiesz co sie dzieje..." +
                                "Ukazuje się przed tobą kobieta...\n Nie... to ta postać z posążka!" +
                                "Przemawia do ciebie i\n udziela ci błogosławieństwa. " +
                                "Nagle poczułeś się lepiej. \n\n" +
                                "Zwiększono maksymalne zdrowie do 200!");
            }



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
                chestDialog.dispose();

            });
            chestPanel.add(chestMessage, BorderLayout.CENTER);
            chestPanel.add(buttonQuit, BorderLayout.SOUTH);

            chestDialog.setContentPane(chestPanel);
            chestDialog.setVisible(true);
        } else {
            chestDialog.dispose();
            chestDialog = null;
        }
    }
}
