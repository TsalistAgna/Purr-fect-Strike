import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;

import javax.swing.ImageIcon;
public class Cat{

    public static final double CAT_SIZE=64;
    public double x, y;
    private float angle=0f;
    private final Image images;
    // private Image currentImage;

    public Cat(){
        this.images = new ImageIcon(getClass().getResource("Assets/Images/Kucing/1.png")).getImage();

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
        g2.drawImage(images, 0, 0, null);
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
}