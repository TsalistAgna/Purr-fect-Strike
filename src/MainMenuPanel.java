import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class MainMenuPanel extends JPanel {
    private ArrayList<Asset> cats;
    private int currentCatIndex = 0;
    private JLabel catImageLabel;
    private JLabel catNameLabel;

    public MainMenuPanel(Runnable onStartGame) {
        cats = DatabaseConnection.getCats();

        setLayout(new GridBagLayout());
        setBackground(new Color(0xF6D6D6));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); 
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel titleLabel = new JLabel("Purr-fect Strike", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Poppins", Font.BOLD, 48));
        titleLabel.setForeground(new Color(0xAB4459));
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 10, 40, 10); 
        add(titleLabel, gbc);

        JLabel selectCatLabel = new JLabel("Select your cat hunter", SwingConstants.CENTER);
        selectCatLabel.setFont(new Font("Poppins", Font.PLAIN, 24));
        selectCatLabel.setForeground(new Color(0xAB4459));
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 10, 10, 10); 
        add(selectCatLabel, gbc);

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
        add(catPanel, gbc);

        JButton startButton = new JButton(new ImageIcon(new ImageIcon("Assets\\Images\\Button\\start.png").getImage().getScaledInstance(220, 75, Image.SCALE_SMOOTH)));
        startButton.setFocusPainted(false);
        startButton.setBorder(BorderFactory.createEmptyBorder());
        startButton.setContentAreaFilled(false);
        startButton.addActionListener(e -> {
            onStartGame.run();
        });

        gbc.gridy = 3;
        gbc.insets = new Insets(15, 10, 10, 10); 
        add(startButton, gbc);

        if (!cats.isEmpty()) {
            updateCatDisplay();
        }
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
