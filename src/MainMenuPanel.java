import java.awt.*;
import javax.swing.*;

public class MainMenuPanel extends JPanel {
    public MainMenuPanel(Runnable onStartGame) {
        // Menggunakan GridBagLayout untuk memusatkan konten
        setLayout(new GridBagLayout());
        setBackground(new Color(50, 50, 50));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Margin antar elemen
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        // Judul permainan
        JLabel titleLabel = new JLabel("Purr-fect Strike", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
        titleLabel.setForeground(Color.WHITE);

        // Tambahkan judul ke layout
        add(titleLabel, gbc);

        // Tombol Start
        JButton startButton = new JButton("Start");
        startButton.setFont(new Font("Arial", Font.PLAIN, 24));
        startButton.setFocusPainted(false);
        startButton.setPreferredSize(new Dimension(150, 50)); // Ukuran tombol
        startButton.addActionListener(e -> onStartGame.run());

        // Pindahkan posisi ke bawah untuk tombol
        gbc.gridy = 1;
        add(startButton, gbc);
    }
}