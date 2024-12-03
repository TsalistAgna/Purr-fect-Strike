import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GameFrame extends JFrame {

    public GameFrame() {
        setTitle("Purr-fect Strike");
        setSize(1440, 900);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        GamePanel gamePanel = new GamePanel();
        add(gamePanel);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                gamePanel.loadBackground("Assets\\Images\\Background\\bg2.png");
                gamePanel.start(); 
            }
        });
        setVisible(true);
    }
}
