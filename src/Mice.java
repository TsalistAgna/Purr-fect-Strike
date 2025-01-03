import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import javax.swing.ImageIcon;

public class Mice extends HpRenderImpl implements Object, Position {
    public static final double MICE_SIZE = 50;
    private double x;
    private double y;
    private final float speed = 0.3f;
    private float angle = 0;
    private Image image;
    private final Area miceShape;

    public Mice(String imagePath) {
        super(new HealthPoint(20, 20));
    
        try {
            image = new ImageIcon(imagePath)
                    .getImage()
                    .getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Gagal memuat gambar tikus dari: " + imagePath);
        }
    
        Path2D p = new Path2D.Double();
        p.moveTo(0, MICE_SIZE / 2);
        p.lineTo(15, 10);
        p.lineTo(MICE_SIZE - 5, 13);
        p.lineTo(MICE_SIZE + 10, MICE_SIZE / 2);
        p.lineTo(MICE_SIZE - 5, MICE_SIZE - 13);
        p.lineTo(15, MICE_SIZE - 10);
        miceShape = new Area(p);
    }
    

    public void changeLocation(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void update() {
        x += Math.cos(Math.toRadians(angle)) * speed;
        y += Math.sin(Math.toRadians(angle)) * speed;
    }

    public void changeAngle(float angle) {
        if (angle < 0) {
            angle = 359;
        } else if (angle > 359) {
            angle = 0;
        }
        this.angle = angle;
    }

    public void draw(Graphics2D g2) {
        AffineTransform oldTransform = g2.getTransform();
        g2.translate(x, y);

        AffineTransform tran = new AffineTransform();
        if (angle == 180) {
            tran.scale(-1, 1); 
            tran.translate(-MICE_SIZE, 0); 
        }
        g2.drawImage(image, tran, null);

        Shape shap = getShape();
        hpRender(g2, shap, y);

        g2.setTransform(oldTransform);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public float getAngle() {
        return angle;
    }

    public Area getShape() {
        AffineTransform afx = new AffineTransform();
        afx.translate(x, y);
        afx.rotate(Math.toRadians(angle), MICE_SIZE / 2, MICE_SIZE / 2);
        return new Area(afx.createTransformedShape(miceShape));
    }

    public boolean check(int width, int height) {
        Rectangle size = getShape().getBounds();
        return !(x <= -size.getWidth() || y < -size.getHeight() || x > width || y > height);
    }
}