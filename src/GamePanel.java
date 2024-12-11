import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.FontMetrics;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class GamePanel extends JComponent {
    private final int FPS = 60;
    private final int TARGET_TIME = 1000000000 / FPS;

    private int width, height;
    private Thread thread;
    private boolean start = true;

    private BufferedImage image;
    private BufferedImage bufferImage;
    private Graphics2D g2;
    private BufferedImage backgroundImage;
    private Key key;
    private int shotTime;

    private Sound sound;
    private List<Laser> lasers = new ArrayList<>();
    private Cat player = new Cat();
    private List<Mice> mice = Collections.synchronizedList(new ArrayList<>());
    private List<Effect> effects;
    private int score = 0;

    public void start() {
        width = getWidth();
        height = getHeight();
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        
        initObjectGame();
        initKeyboard();
        requestFocus(); // Pastikan panel menerima fokus

        thread = new Thread(() -> {
            while (start) {
                long startTime = System.nanoTime();
                drawBackground();
                drawGame();
                render();
                long time = System.nanoTime() - startTime;
    
                if (time < TARGET_TIME) {
                    long sleep = (TARGET_TIME - time) / 1000000;
                    sleep(sleep);
                }
            }
        });
        initObjectGame();
        initKeyboard();
        initLasers();
        thread.start();
    }
    
    private void addMice() {
        Random ran = new Random();
        int locationY = ran.nextInt(Math.max(height - 50, 1)) + 25;

        Mice mouse1 = new Mice();
        mouse1.changeLocation(0, locationY);
        mouse1.changeAngle(0); 
        mice.add(mouse1);

        int locationY2 = ran.nextInt(Math.max(height - 50, 1)) + 25;

        Mice mouse2 = new Mice();
        mouse2.changeLocation(width, locationY2);
        mouse2.changeAngle(180); 
        mice.add(mouse2);
    }

    private void initObjectGame() {
        sound = new Sound();
        player.changeLocation(650, 300);
        System.out.println("Lokasi kucing: " + player.getX() + ", " + player.getY());
        effects = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (start) {
                    addMice();
                    sleep(3000);
                }
            }
        }).start();
    }

    private void resetGame(){
        score = 0;
        mice.clear();
        lasers.clear();

        player.changeLocation(650, 300);
        nameField.setText("");

        player.reset();
        requestFocus(); // Pastikan panel menerima fokus
        initKeyboard(); // Reinitialisasi key listener
    }

    private void initKeyboard(){
        key = new Key();
        requestFocus();
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e){
                if (e.getKeyCode()==KeyEvent.VK_A){
                    key.setKiri(true);
                }
                else if (e.getKeyCode()==KeyEvent.VK_D){
                    key.setKanan(true);
                }
                else if (e.getKeyCode()==KeyEvent.VK_SPACE){
                    key.setSpasi(true);
                }
                else if (e.getKeyCode()==KeyEvent.VK_J){
                    key.setKey_j(true);
                }
                else if (e.getKeyCode()==KeyEvent.VK_ENTER){
                    key.setKey_enter(true);
                }
            }

            @Override
            public void keyReleased(KeyEvent e){
                if (e.getKeyCode()==KeyEvent.VK_A){
                    key.setKiri(false);
                }
                else if (e.getKeyCode()==KeyEvent.VK_D){
                    key.setKanan(false);
                }
                else if (e.getKeyCode()==KeyEvent.VK_SPACE){
                    key.setSpasi(false);
                }
                else if (e.getKeyCode()==KeyEvent.VK_J){
                    key.setKey_j(false);
                }
                else if (e.getKeyCode()==KeyEvent.VK_ENTER){
                    key.setKey_enter(false);
                }
            }
        });
        new Thread(new Runnable(){
            @Override
            public void run(){
                float s =0.5f;
                while (start){
                    if(player.isAlive()){
                        float angle = player.getAngle();
                        if(key.isKiri()){
                            angle -= s ;
                        }
                        if(key.isKanan()){
                            angle += s ;
                        }
                        if (key.isKey_j()) {
                            if (shotTime == 0) {
                                lasers.add(0, new Laser(player.getX(), player.getY(), player.getAngle(), 5, 3f));
                            } else {
                                lasers.add(0, new Laser(player.getX(), player.getY(), player.getAngle(), 10, 3f));
                            }
                            shotTime++;
                            if (shotTime == 5) {
                                shotTime = 0;
                            }
                        } else {
                            shotTime = 0;
                        }
                        if (key.isSpasi()) {
                            player.speedUp();
                        } else {
                            player.speedDown();
                        }
                        player.update();
                        if (player.getX() < 0) {
                            player.changeLocation(0, player.getY());
                        } else if (player.getX() + Cat.CAT_SIZE > width) {
                            player.changeLocation(width - Cat.CAT_SIZE, player.getY());
                        }
                        if (player.getY() < 0) {
                            player.changeLocation(player.getX(), 0);
                        } else if (player.getY() + Cat.CAT_SIZE > height) {
                            player.changeLocation(player.getX(), height - Cat.CAT_SIZE);
                        }
                        player.changeAngle(angle);
                    }else{
                        if(key.isKey_enter()){
                            resetGame();
                        }
                    }

                    for (int i = 0; i < mice.size(); i++) {
                        Mice mouse = mice.get(i);
                        if (mouse != null) {
                            mouse.update();
                            if (!mouse.check(width, height)) {
                                mice.remove(mouse);
                            }else{
                                if(player.isAlive()){
                                    checkCat(mouse);
                                }
                            }
                        }
                    }
                    repaint();
                    sleep(5);

                }
            }
        }).start();
    }


    private void checkCat(Mice mice) {
        if (mice != null) {
            Area area = new Area(player.getShape());
            area.intersect(mice.getShape());
            if (!area.isEmpty()) {
                double miceHp = mice.getHP();
                if (!mice.updateHP(player.getHP())){
                    this.mice.remove(mice);
                    sound.soundHit();
                    double x = mice.getX() + Mice.MICE_SIZE/2;
                    double y = mice.getY() + Mice.MICE_SIZE/2;
                    effects.add(new Effect(x, y, 5, 5, 75, 0.05f, new Color (32, 178, 169)));
                    effects.add(new Effect(x, y, 5, 5, 75, 0.01f, new Color (32, 178, 169)));
                    effects.add(new Effect(x, y, 10, 10, 100, 0.3f, new Color (230, 207, 105)));
                    effects.add(new Effect(x, y, 10, 5, 100, 0.5f, new Color (255, 70, 70)));
                    effects.add(new Effect(x, y, 10, 5, 100, 0.2f, new Color (255, 255, 255)));
                }
                if (!player.updateHP(miceHp)){
                    player.setAlive(false);
                    sound.soundDead();
                    double x = player.getX() + Cat.CAT_SIZE/2;
                    double y = player.getY() + Cat.CAT_SIZE/2;
                    effects.add(new Effect(x, y, 5, 5, 75, 0.05f, new Color (32, 178, 169)));
                    effects.add(new Effect(x, y, 5, 5, 75, 0.01f, new Color (32, 178, 169)));
                    effects.add(new Effect(x, y, 10, 10, 100, 0.3f, new Color (230, 207, 105)));
                    effects.add(new Effect(x, y, 10, 5, 100, 0.5f, new Color (255, 70, 70)));
                    effects.add(new Effect(x, y, 10, 5, 100, 0.2f, new Color (255, 255, 255)));
                }
            }
        }
    }

    private void checkLaser(Laser laser) {
        if (score > 0 && score % 10 == 0 && !isLaserEnlarged) {
            isLaserEnlarged = true;
            laserEnlargeStartTime = System.currentTimeMillis(); 
        }
    
        for (int i = 0; i < mice.size(); i++) {
            Mice mouse = mice.get(i);
            if (mouse != null) {
                Area area = new Area(laser.getShape());  
                area.intersect(mouse.getShape());
                if (!area.isEmpty()) {
                    effects.add(new Effect(laser.getCenterX(), laser.getCenterY(), 3, 5, 60, 0.5f, new Color(230, 207, 105)));
                    if (!mouse.updateHP(laser.getSize())) {
                        score++;
                        mice.remove(mouse);
                        sound.soundShoot();
                        double x = mouse.getX() + Mice.MICE_SIZE / 2;
                        double y = mouse.getY() + Mice.MICE_SIZE / 2;
                        effects.add(new Effect(x, y, 5, 5, 75, 0.05f, new Color(32, 178, 169)));
                        effects.add(new Effect(x, y, 5, 5, 75, 0.01f, new Color(32, 178, 169)));
                        effects.add(new Effect(x, y, 10, 10, 100, 0.3f, new Color(230, 207, 105)));
                        effects.add(new Effect(x, y, 10, 5, 100, 0.5f, new Color(255, 70, 70)));
                        effects.add(new Effect(x, y, 10, 5, 100, 0.2f, new Color(255, 255, 255)));
                    }
    
                    lasers.remove(laser);
                }
            }
        }
    
        if (isLaserEnlarged) {
            long elapsedTime = System.currentTimeMillis() - laserEnlargeStartTime;
            if (elapsedTime > laserEnlargeDuration) {
                isLaserEnlarged = false;  
            } else {
                laser.setLaserEnlarged(true);  
            }
        } else {
            laser.setLaserEnlarged(false);  
        }
    }
    
    
    
    private void initLasers(){
        new Thread(new Runnable()  {
            @Override
            public void run(){
                while (start) {
                    List<Laser> toRemove = new ArrayList<>();
                    for (int i = 0; i < lasers.size(); i++) {
                        Laser laser = lasers.get(i);
                        if (laser != null) {
                            laser.update();
                            checkLaser(laser);
                            if (!laser.check(width, height)) {
                                toRemove.add(laser);
                            }
                        }
                    }
                    lasers.removeAll(toRemove);
                    for (int i = 0; i < effects.size(); i++) {
                        Effect eff = effects.get(i);
                        if (eff != null) {
                            eff.update();
                            if (!eff.check()) {
                                effects.remove(eff);  
                            }
                        }
                    }
                    sleep(1);
                }
            }
        }).start();
    }

    public void loadBackground(String filePath) {
        try {
            backgroundImage = ImageIO.read(new File(filePath));
            System.out.println("Latar belakang berhasil dimuat: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Gagal memuat gambar: " + filePath);
        }
    }
    

    private void drawBackground() {
        if (backgroundImage != null) {
            g2.drawImage(backgroundImage, 0, 0, width, height, null);
        } else {
            g2.setColor(new Color(30, 30, 30));
            g2.fillRect(0, 0, width, height);
        }
    }

    private void drawGame() {
        if(player.isAlive()){
            player.draw(g2);
        }
        if (player != null) {
            player.draw(g2);
            for(int i = 0; i < lasers.size(); i++){
                Laser laser = lasers.get(i);
                if (laser != null) {
                    laser.draw(g2);
                }
            }
        }
        for (int i = 0; i < mice.size(); i++) {
            Mice mouse = mice.get(i);
            if (mouse != null) {
                mouse.draw(g2);
            }
        }
        for (int i = 0; i < effects.size(); i++){
            Effect eff = effects.get(i);
            if (eff != null){
                eff.draw(g2);
            }
        }

        g2.setColor(Color.BLACK);
        g2.setFont((new Font("Poppins", Font.BOLD, 20)));
        g2.drawString("Score: " + score, 10, 25);

        if(!player.isAlive()){
            String text = "GAME OVER";
            String text2 = "Tekan enter untuk mulai lagi";
            g2.setFont(new Font("Arial", Font.BOLD, 50));
            FontMetrics fm = g2.getFontMetrics();
            Rectangle2D r2 = fm.getStringBounds(text, g2);

            // double textWidth = r2.getWidth();
            // double textHeight = r2.getHeight();
            // double x = (width - textWidth) / 2;
            // double y = (height - textHeight) / 2;

            // g2.drawString(text, (int) x, (int) y + fm.getAscent());
            // g2.setFont(getFont().deriveFont(Font.BOLD, 20));
            // fm = g2.getFontMetrics();
            // r2 = fm.getStringBounds(text2, g2);
            // textWidth = r2.getWidth();
            // textHeight = r2.getHeight();
            // x = (width - textWidth) / 2;
            // y = (height - textHeight) / 2;
            // g2.drawString(text2, (int) x, (int) y + fm.getAscent() + 50);
            drawGameOver(g2);
        }
    }
    

    private void render() {
        Graphics g = getGraphics();
        if (g != null) {
            g.drawImage(image, 0, 0, null);
            g.dispose();
        }
    }

    private void sleep(long sleep) {
        try {
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
            System.err.println(e);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (bufferImage == null || bufferImage.getWidth() != getWidth() || bufferImage.getHeight() != getHeight()) {
            bufferImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        }

        Graphics2D g2d = bufferImage.createGraphics();
        
        g2d.drawImage(image, 0, 0, null);  
        drawBackground(g2d);
        drawGame(g2d);  
        
        if (!player.isAlive()) {
            drawGameOver(g2d);
        }
        
        g.drawImage(bufferImage, 0, 0, null); 
        g2d.dispose();
    }
    
    private void drawGame(Graphics2D g2) {
        if (player.isAlive()) {
            player.draw(g2);
        }

        for (Laser laser : lasers) {
            if (laser != null) {
                laser.draw(g2);
            }
        }

        for (Mice mouse : mice) {
            if (mouse != null) {
                mouse.draw(g2);
            }
        }

        for (Effect eff : effects) {
            if (eff != null) {
                eff.draw(g2);
            }
        }
    
        g2.setColor(Color.BLACK);
        g2.setFont((new Font("Poppins", Font.BOLD, 20)));
        g2.drawString("Score: " + score, 10, 25);
    }
    

    private void drawBackground(Graphics2D g2) {
        if (backgroundImage != null) {
            g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
        } else {
            g2.setColor(new Color(30, 30, 30));
            g2.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    public void setGameOver(boolean isGameOver) {
        gameOver = isGameOver;
        nameField.setVisible(isGameOver);
        // saveButton.setVisible(false); // Tidak perlu tombol save
        if (isGameOver) {
            nameField.addActionListener(e -> saveScore()); // Tambahkan listener untuk Enter
        } else {
            nameField.removeActionListener(null); // Hapus listener saat tidak game over
        }
        repaint();
    }
    
    private void saveScore() {
        String playerName = nameField.getText();
        if (playerName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama harus diisi!");
            return;
        }
    
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/purrfect_strike", "root", "")) {
            String query = "INSERT INTO players (nama, score) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, playerName);
            stmt.setInt(2, score);
            stmt.executeUpdate();
    
            // JOptionPane.showMessageDialog(this, "Score saved successfully!");
    
            // Sembunyikan text field
            nameField.setVisible(false);
    
            // Reset game state
            resetGame();
            gameOver = false;
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to save score!");
        }
    }
    
    // public void setGameOver(boolean isGameOver) {
    //     gameOver = isGameOver;
    //     nameField.setVisible(isGameOver);
    //     saveButton.setVisible(isGameOver);
    //     repaint();
    // }

    private void drawGameOver(Graphics2D g2) {
        String text = "GAME OVER";
        String text2 = "Tekan enter untuk mulai lagi";
        g2.setFont(new Font("Arial", Font.BOLD, 50));
        FontMetrics fm = g2.getFontMetrics();
        Rectangle2D r2 = fm.getStringBounds(text, g2);

        double textWidth = r2.getWidth();
        double textHeight = r2.getHeight();
        double x = (getWidth() - textWidth) / 2;
        double y = (getHeight() - textHeight) / 2;

        g2.drawString(text, (int) x, (int) y + fm.getAscent());
        g2.setFont(new Font("Poppins", Font.BOLD, 20));
        fm = g2.getFontMetrics();
        r2 = fm.getStringBounds(text2, g2);
        textWidth = r2.getWidth();
        textHeight = r2.getHeight();
        x = (getWidth() - textWidth) / 2;
        y = (getHeight() - textHeight) / 2;
        g2.drawString(text2, (int) x, (int) y + fm.getAscent() + 50);

        // Show text field and button
        int fieldX = (getWidth() - fieldWidth) / 2;
        int fieldY = (int) (y + fm.getAscent() + 70); // Posisi di bawah teks kedua
        nameField.setBounds(fieldX, fieldY, fieldWidth, fieldHeight);

        // Posisi JButton
        // int buttonX = fieldX + fieldWidth + 10; // Beri jarak 10px setelah text field
        // int buttonY = fieldY;
        // int buttonX = (getWidth() - fieldWidth) / 2;
        // int buttonY = (int) (y + fm.getAscent() + 70);
        // saveButton.setBounds(buttonX, buttonY, buttonWidth, buttonHeight);

        nameField.setVisible(true);
        // saveButton.setVisible(true);
    }

}