import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

public class Laser {
    private double x;
    private double y;
    private Shape shape;
    private Color color = new Color(255, 0, 0);
    private float angle;
    private double size;
    private float speed = 1f;

    public Laser(double x, double y, float angle, double size, float speed){
        x += Cat.CAT_SIZE/2 - (size/2);
        y += Cat.CAT_SIZE/2 - (size/2);

        this.x = x;
        this.y = y;
        this.angle = angle;
        this.size = size;
        this.speed = speed;
        shape = new Ellipse2D.Double(0, 0, size, size);
    }

    public void update(){
        x += Math.cos(Math.toRadians(angle)) * speed;
        y += Math.sin(Math.toRadians(angle)) * speed;
    }

    public boolean check(int width, int height){
        if (x <= -size || y < -size || x > width || y > height) {
            return false;
        } else {
            return true;
        }
    }

    public void draw(Graphics2D g2){
        AffineTransform oldTransform = g2.getTransform();
        g2.setColor(color);
        g2.translate(x, y);
        g2.fill(shape);
        g2.setTransform(oldTransform);
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public double getSize(){
        return size;
    }

    public double getCenterX(){
        return x + size / 2;
    }

    public double getCenterY(){
        return y + size / 2;
    }

}
