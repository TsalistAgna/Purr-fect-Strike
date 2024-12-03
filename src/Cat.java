import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import javax.swing.ImageIcon;

public class Cat{

    public Cat() {
        try {
            images = new ImageIcon(getClass().getResource("Assets/Images/Kucing/1.png"))
                        .getImage()
                        .getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Gagal memuat gambar kucing.");
        }
    }

    public void draw(Graphics2D g) {
        if (images != null) {
            g.drawImage(this.images, 0, 0, null);
        } else {
            g.setColor(Color.RED); // Gambarkan placeholder jika gambar gagal dimuat
            g.fillRect(0, 0, 100, 100);
        }
    }

    public static final double CAT_SIZE=64;
    public double x, y;
    private float angle=0f;
    private Image images;
    // private Image currentImage;

    public void changeLocation(double x, double y){
        this.x=x;
        this.y=y;
    }

    public void changeAngle(float angle){
        if (angle<0){
            angle=359;
        } else if (angle>359) {
            angle=0;
        }
        this.angle=angle;
    }
    // public void draw(Graphics2D g2){
    //     AffineTransform oldTransform=g2.getTransform();
    //     g2.translate(x,y);
    //     g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f)); // Full opacity
    //     AffineTransform tran =new AffineTransform();
    //     tran.rotate(Math.toRadians(angle+45), CAT_SIZE/2, CAT_SIZE/2);
    //     g2.drawImage(this.images, 0, 0, null);
    //     g2.setTransform(oldTransform);
    // }

    public double getX() {
        return x;
    }

    public float getAngle() {
        return angle;
    }

    public double getY() {
        return y;
    }
}