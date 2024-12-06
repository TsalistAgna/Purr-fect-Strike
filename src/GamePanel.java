import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    private int shotTime;

    private List<Laser> lasers = new ArrayList<>();
    private Cat player = new Cat();
    private List<Mice> mice = new ArrayList<>();

    public void start() {
        width = getWidth();
        height = getHeight();
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        
        initObjectGame();
        initKeyboard();

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
    
    private void addMice(){
        Random ran = new Random();
        int locationY = ran.nextInt(height - 50) + 25;
        Mice mouse1 = new Mice();
        mouse1.changeLocation(0, locationY);
        mouse1.changeAngle(0);
        mice.add(mouse1);
        int locationY2 = ran.nextInt(height - 50) + 25;
        Mice mouse2 = new Mice();
        mouse2.changeLocation(width, locationY2);
        mouse2.changeAngle(180);
        mice.add(mouse2);
    }

    private void initObjectGame() {
        player.changeLocation(150, 150);
        System.out.println("Lokasi kucing: " + player.getX() + ", " + player.getY());
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
                    if (key.isKey_j() || key.isKey_k()) {
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
                    player.changeAngle(angle);
                    for (int i = 0; i < mice.size(); i++) {
                        Mice mouse = mice.get(i);
                        if (mouse != null) {
                            mouse.update();
                            if (!mouse.check(width, height)) {
                                mice.remove(mouse);
                            }
                        }
                    }
                    repaint();
                    sleep(5);

                }
            }
        }).start();
    }

    private void checkLaser(Laser laser) {
        for (int i = 0; i < mice.size(); i++) {
            Mice mouse = mice.get(i);
            if (mouse != null) {
                Area area = new Area(laser.getShape());
                area.intersect(mouse.getShape());
                if (!area.isEmpty()) {
                    mice.remove(mouse);
                    lasers.remove(laser);
                }
            }
        }
    }

    private void initLasers(){
        new Thread(new Runnable()  {
            @Override
            public void run(){
                while (start) {
                    for (int i=0; i < lasers.size(); i++) {
                        Laser laser = lasers.get(i);
                        if (laser != null) {
                            laser.update();
                            checkLaser(laser);
                            if(!laser.check(width, height)){
                                lasers.remove(laser);
                            } else {
                                lasers.remove(laser);
                            }
                        }
                        sleep(1);
                    }
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