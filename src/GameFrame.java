import java.awt.*;
import javax.swing.*;

public class GameFrame extends JFrame {

    private CardLayout cardLayout;
    private JPanel containerPanel;
    private GamePanel gamePanel;

    public GameFrame() {
        setTitle("Purr-fect Strike");
        setSize(1440, 900);
        setResizable(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        cardLayout = new CardLayout();
        containerPanel = new JPanel(cardLayout);

        MainMenuPanel mainMenuPanel = new MainMenuPanel(this::showGamePanel);
        containerPanel.add(mainMenuPanel, "MainMenu");

        gamePanel = new GamePanel();
        containerPanel.add(gamePanel, "GamePanel");

        add(containerPanel);
        setVisible(true);
    }

    private void showGamePanel() {
        try {
            gamePanel.loadBackground("Assets/Images/Background/bg2.png");
            System.out.println("Background berhasil dimuat.");
            gamePanel.start();
            System.out.println("GamePanel dimulai.");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error: " + ex.getMessage());
        }

        cardLayout.show(containerPanel, "GamePanel");
        gamePanel.requestFocusInWindow();
    }
}
