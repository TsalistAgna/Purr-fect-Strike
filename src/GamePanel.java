import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class GamePanel extends JComponent {
    private final int FPS = 60;
    private final int TARGET_TIME = 1000000000 / FPS;

    private int width, height;
    private Thread thread;
    private boolean start = true;

    private BufferedImage image; 
    private Graphics2D g2;
    private BufferedImage backgroundImage;
    private Key key;

    private Cat player;

    public void start() {
        width = getWidth();
        height = getHeight();
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        
        // initObjectGame();
        // initKeyboard();

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
        thread.start();
    }
    

    private void initObjectGame() {
        player = new Cat();
        player.changeLocation(150, 150);
        System.out.println("Lokasi kucing: " + player.getX() + ", " + player.getY());
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
                else if (e.getKeyCode()==KeyEvent.VK_K){
                    key.setKey_k(true);
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
                else if (e.getKeyCode()==KeyEvent.VK_K){
                    key.setKey_k(false);
                }
            }
        }); 
        new Thread(new Runnable(){
            @Override
            public void run(){
                float s =0.5f;
                while (start){
                    float angle = player.getAngle();
                    if(key.isKiri()){
                        angle -= s ;
                    }
                    if(key.isKanan()){
                        angle += s ;
                    }
                    player.changeAngle(angle);
                    sleep(5);

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
        if (player != null) {
            System.out.println("Menggambar kucing...");
            player.draw(g2);
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
        Graphics2D g2 = (Graphics2D) g;

        // Gambar latar belakang
        if (backgroundImage != null) {
            g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
        }

        // Gambar objek kucing
        if (player != null) {
            player.draw(g2);
        }
    }

}
