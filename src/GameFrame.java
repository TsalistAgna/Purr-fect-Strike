import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

public class GameFrame extends JFrame {

    public GameFrame() {
        setTitle("Purr-fect Strike");
        setSize(1440, 900);
        setResizable(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        GamePanel gamePanel = new GamePanel();
        add(gamePanel);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                try {
                    gamePanel.loadBackground("Assets/Images/Background/bg2.png");
                    System.out.println("Background berhasil dimuat.");
                    gamePanel.start();
                    System.out.println("GamePanel dimulai.");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    System.out.println("Error: " + ex.getMessage());
                }
            }
        });
        setVisible(true);
    }
}
