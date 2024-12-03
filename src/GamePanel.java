import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GamePanel extends JComponent {
    private final int FPS = 60;
    private final int TARGET_TIME = 1000000000 / FPS;

    private int width, height;
    private Thread thread;
    private boolean start = true;

    private BufferedImage image; 
    private Graphics2D g2;
    private BufferedImage backgroundImage;

    private Cat player;

    public void start() {
        width = getWidth();
        height = getHeight();
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

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
        thread.start();
    }

    public void loadBackground(String filePath) {
        try {
            backgroundImage = ImageIO.read(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Gagal memuat gambar: " + filePath);
        }
    }

    private void initObjectGame(){
        player = new Cat();
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
        player.draw(g2);
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
}
