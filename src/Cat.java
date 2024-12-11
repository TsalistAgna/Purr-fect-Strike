import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import javax.swing.ImageIcon;

public class Cat extends HpRender{
    public static final double CAT_SIZE=64;
    public double x, y;
    private float MAX_SPEED = 1f;
    private float speed = 0f;
    private float angle=0f;
    private Area catShape;
    private Image images;
    private Image image_speed;
    private boolean speedUp;
    private boolean alive = true;
    
    public Cat() {
        super(new HP (100, 100));
        try {
            images = new ImageIcon("Assets\\Images\\Kucing\\1.png")
                        .getImage()
                        .getScaledInstance(120, 120, Image.SCALE_SMOOTH);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Gagal memuat gambar kucing.");
        }
        Path2D p = new Path2D.Double();
        p.moveTo(0, 15);
        p.lineTo(20, 5);
        p.lineTo(CAT_SIZE + 15, CAT_SIZE / 2);
        p.lineTo(20, CAT_SIZE - 5);
        p.lineTo(0, CAT_SIZE - 15);
        catShape = new Area(p);
    }

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
        hpRender(g2, getShape(), y);
        g2.setTransform(oldTransform);

        g2.setColor(new Color(12, 173, 64));
        g2.draw(getShape());
        g2.draw(getShape().getBounds());
    }

    public Area getShape() {
        AffineTransform afx = new AffineTransform();
        afx.translate(x, y);
        afx.rotate(Math.toRadians(angle), CAT_SIZE / 2, CAT_SIZE / 2);
        return new Area(afx.createTransformedShape(catShape));
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

    public boolean isAlive(){
        return alive;
    }

    public void setAlive(boolean alive){
        this.alive=alive;
    }

    public void reset(){
        alive=true;
        speed=0;
        resetHP();
        angle=0;
    }
}