import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.*;

public class MainMenuPanel extends JPanel {
    private ArrayList<Asset> cats;
    private int currentCatIndex = 0;
    private JLabel catImageLabel;
    private JLabel catNameLabel;
    private Key key;
    private Runnable onStartGame;

    private JPanel leaderboardPanel;
    private JPanel mainMenuPanel;

    public MainMenuPanel(Runnable onStartGame) {
        this.onStartGame = onStartGame;
        this.key = new Key();
        cats = DatabaseConnection.getCats();

        CardLayout cardLayout = new CardLayout();
        setLayout(cardLayout);

        mainMenuPanel = createMainMenuPanel();
        add(mainMenuPanel, "MainMenu");

        leaderboardPanel = createLeaderboardPanel();
        add(leaderboardPanel, "Leaderboard");

        showMainMenu();
    }

    private JPanel createMainMenuPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(0xF6D6D6));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel titleLabel = new JLabel("Purr-fect Strike", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Poppins", Font.BOLD, 48));
        titleLabel.setForeground(new Color(0xAB4459));
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 10, 20, 10);
        panel.add(titleLabel, gbc);

        JLabel catLabel = new JLabel("The hunter cat character will be selected randomly", SwingConstants.CENTER);
        catLabel.setFont(new Font("Poppins", Font.PLAIN, 24));
        catLabel.setForeground(new Color(0xAB4459));
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(catLabel, gbc);

        JPanel catPanel = new JPanel(new BorderLayout());
        catPanel.setBackground(new Color(0xF6D6D6));

        JButton prevButton = new JButton(new ImageIcon(new ImageIcon("Assets\\Images\\Button\\prev.png").getImage().getScaledInstance(60, 50, Image.SCALE_SMOOTH)));
        prevButton.setFocusPainted(false);
        prevButton.setBorder(BorderFactory.createEmptyBorder());
        prevButton.setContentAreaFilled(false);
        prevButton.addActionListener(e -> showPreviousCat());
        catPanel.add(prevButton, BorderLayout.WEST);

        JPanel imageAndNamePanel = new JPanel(new BorderLayout());
        imageAndNamePanel.setBackground(new Color(0xF6D6D6));

        catImageLabel = new JLabel();
        catImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageAndNamePanel.add(catImageLabel, BorderLayout.CENTER);

        catNameLabel = new JLabel("", SwingConstants.CENTER);
        catNameLabel.setFont(new Font("Poppins", Font.PLAIN, 24));
        catNameLabel.setForeground(new Color(0xAB4459));
        catNameLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0)); 
        imageAndNamePanel.add(catNameLabel, BorderLayout.SOUTH);

        catPanel.add(imageAndNamePanel, BorderLayout.CENTER);

        JButton nextButton = new JButton(new ImageIcon(new ImageIcon("Assets\\Images\\Button\\next.png").getImage().getScaledInstance(60, 50, Image.SCALE_SMOOTH)));
        nextButton.setFocusPainted(false);
        nextButton.setBorder(BorderFactory.createEmptyBorder());
        nextButton.setContentAreaFilled(false);
        nextButton.addActionListener(e -> showNextCat());
        catPanel.add(nextButton, BorderLayout.EAST);

        gbc.gridy = 2;
        gbc.insets = new Insets(10, 10, 10, 10); 
        panel.add(catPanel, gbc);

        JButton startButton = new JButton(new ImageIcon(new ImageIcon("Assets\\Images\\Button\\start.png").getImage().getScaledInstance(220, 75, Image.SCALE_SMOOTH)));
        startButton.setFocusPainted(false);
        startButton.setBorder(BorderFactory.createEmptyBorder());
        startButton.setContentAreaFilled(false);
        startButton.addActionListener(e -> {
            onStartGame.run();
        });

        gbc.gridy = 3;
        gbc.insets = new Insets(15, 10, 10, 10); 
        panel.add(startButton, gbc);

        JButton leaderboardButton = new JButton(new ImageIcon(new ImageIcon("Assets\\Images\\Button\\leaderboard.png").getImage().getScaledInstance(220, 75, Image.SCALE_SMOOTH)));
        leaderboardButton.setFocusPainted(false);
        leaderboardButton.setBorder(BorderFactory.createEmptyBorder());
        leaderboardButton.setContentAreaFilled(false);
        leaderboardButton.addActionListener(e -> showLeaderboard());

        gbc.gridy = 4;
        gbc.insets = new Insets(15, 10, 10, 10); 
        panel.add(leaderboardButton, gbc);

        if (!cats.isEmpty()) {
            updateCatDisplay();
        }

        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPressed(e.getKeyCode());
            }

            @Override
            public void keyReleased(KeyEvent e) {
                handleKeyReleased(e.getKeyCode());
            }
        });
        
        setFocusable(true);
        
        return panel;
    }

    private JPanel createLeaderboardPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(0xF6D6D6));
    
        JPanel outerContainer = new JPanel();
        outerContainer.setLayout(new BoxLayout(outerContainer, BoxLayout.Y_AXIS));
        outerContainer.setBackground(new Color(0xF6D6D6));
        outerContainer.setAlignmentX(Component.CENTER_ALIGNMENT);
    
        outerContainer.add(Box.createRigidArea(new Dimension(0, 30)));
    
        JLabel titleLabel = new JLabel("Leaderboard", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Poppins", Font.BOLD, 36));
        titleLabel.setForeground(new Color(0xAB4459));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        outerContainer.add(titleLabel);
    
        outerContainer.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel headerPanel = new JPanel(new GridLayout(1, 3));
        headerPanel.setBackground(new Color(0xF6D6D6));
    
        JLabel noHeader = new JLabel("No.", SwingConstants.CENTER);
        noHeader.setFont(new Font("Poppins", Font.BOLD, 18));
        noHeader.setForeground(new Color(0x333333));
        headerPanel.add(noHeader);
    
        JLabel nameHeader = new JLabel("Nama", SwingConstants.CENTER);
        nameHeader.setFont(new Font("Poppins", Font.BOLD, 18));
        nameHeader.setForeground(new Color(0x333333));
        headerPanel.add(nameHeader);
    
        JLabel scoreHeader = new JLabel("Skor", SwingConstants.CENTER);
        scoreHeader.setFont(new Font("Poppins", Font.BOLD, 18));
        scoreHeader.setForeground(new Color(0x333333));
        headerPanel.add(scoreHeader);
    
        outerContainer.add(headerPanel);

        // outerContainer.add(Box.createRigidArea(new Dimension(0, 10)));
    
        ArrayList<Score> scores = new ArrayList<>();
        try {
            scores = DatabaseConnection.getTopScores();
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        JPanel scorePanel = new JPanel(new GridLayout(scores.size(), 3, 10, 10));
        scorePanel.setBackground(new Color(0xF6D6D6));
    
        for (int i = 0; i < scores.size(); i++) {
            Score score = scores.get(i);
    
            JLabel rankLabel = new JLabel((i + 1) + ".", SwingConstants.CENTER);
            rankLabel.setFont(new Font("Poppins", Font.PLAIN, 18));
            rankLabel.setForeground(new Color(0x333333));
            scorePanel.add(rankLabel);
    
            JLabel nameLabel = new JLabel(score.getName(), SwingConstants.CENTER);
            nameLabel.setFont(new Font("Poppins", Font.PLAIN, 18));
            nameLabel.setForeground(new Color(0x555555));
            scorePanel.add(nameLabel);
    
            JLabel scoreLabel = new JLabel(String.valueOf(score.getScore()), SwingConstants.CENTER);
            scoreLabel.setFont(new Font("Poppins", Font.PLAIN, 18));
            scoreLabel.setForeground(new Color(0x777777));
            scorePanel.add(scoreLabel);
        }
    
        JScrollPane scrollPane = new JScrollPane(scorePanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setPreferredSize(new Dimension(500, 300));
        scrollPane.getViewport().setBackground(new Color(0xF6D6D6));
        outerContainer.add(scrollPane);
    
        outerContainer.add(Box.createRigidArea(new Dimension(0, 20)));
    
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Poppins", Font.BOLD, 18));
        backButton.setBackground(new Color(0xAB4459));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.addActionListener(e -> showMainMenu());
        outerContainer.add(backButton);
    
        outerContainer.add(Box.createRigidArea(new Dimension(0, 100)));
    
        panel.add(outerContainer, BorderLayout.CENTER);
    
        return panel;
    }

    private void handleKeyPressed(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_RIGHT:
                key.setKanan(true);
                showNextCat();
                break;
            case KeyEvent.VK_LEFT:
                key.setKiri(true);
                showPreviousCat();
                break;
            case KeyEvent.VK_ENTER:
                key.setSpasi(true);
                onStartGame.run();
                break;
        }
    }

    private void handleKeyReleased(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_RIGHT:
                key.setKanan(false);
                break;
            case KeyEvent.VK_LEFT:
                key.setKiri(false);
                break;
            case KeyEvent.VK_ENTER:
                key.setSpasi(false);
                break;
        }
    }

    public void showMainMenu() {
        CardLayout cl = (CardLayout) getLayout();
        cl.show(this, "MainMenu");
    }

    private void showLeaderboard() {
        CardLayout cl = (CardLayout) getLayout();
        cl.show(this, "Leaderboard");
    }

    private void showPreviousCat() {
        currentCatIndex = (currentCatIndex - 1 + cats.size()) % cats.size();
        updateCatDisplay();
    }

    private void showNextCat() {
        currentCatIndex = (currentCatIndex + 1) % cats.size();
        updateCatDisplay();
    }

    private void updateCatDisplay() {
        Asset currentCat = cats.get(currentCatIndex);
        catNameLabel.setText(currentCat.getName());
        ImageIcon icon = new ImageIcon(new ImageIcon(currentCat.getImagePath()).getImage().getScaledInstance(300, 250, Image.SCALE_SMOOTH));
        catImageLabel.setIcon(icon);
    }
}
