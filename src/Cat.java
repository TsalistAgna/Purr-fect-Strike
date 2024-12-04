import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import javax.swing.ImageIcon;

public class Cat{

    public Cat() {
        try {
            images = new ImageIcon("Assets\\Images\\Kucing\\1.png")
                        .getImage()
                        .getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Gagal memuat gambar kucing.");
        }
    }


    public static final double CAT_SIZE=64;
    public double x, y;
    private float MAX_SPEED = 1f;
    private float speed = 0f;
    private float angle=0f;
    private Image images;
    private Image image_speed;
    private boolean speedUp;

    public void changeLocation(double x, double y){
        this.x=x;
        this.y=y;
    }

    public void update(){
        x += Math.cos(Math.toRadians(angle)) * speed;
        y += Math.sin(Math.toRadians(angle)) * speed;
    }

    public void changeAngle(float angle){
        if (angle<0){
            angle=359;
        } else if (angle>359) {
            angle=0;
        }
        this.angle=angle;
    }
    
    public void draw(Graphics2D g2){
        AffineTransform oldTransform=g2.getTransform();
        g2.translate(x,y);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f)); // Full opacity

        AffineTransform tran =new AffineTransform();

        tran.rotate(Math.toRadians(angle+45), CAT_SIZE/2, CAT_SIZE/2);
        g2.drawImage(speedUp? image_speed : this.images, 0, 0, null);
        g2.setTransform(oldTransform);
    }

    public double getX() {
        return x;
    }

    public float getAngle() {
        return angle;
    }

    public double getY() {
        return y;
    }

    public void speedUp(){
        speedUp = true;
        if (speed > MAX_SPEED) {
            speed = MAX_SPEED;
        } else {
            speed += 0.01f;
        }
    }

    public void speedDown(){
        speedUp = false;
        if (speed <= 0) {
            speed = 0;
        } else {
            speed -= 0.004f;
        }
    }
}