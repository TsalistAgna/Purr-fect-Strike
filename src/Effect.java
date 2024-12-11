import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.Random;

public class Effect implements Object{
    private final double x;
    private final double y;
    private final double jarakMaks;
    private final int ukuranMaks;
    private final Color warna;
    private final int totalEffect;
    private final float kecepatan;
    private double jarakNow;
    private MiceDie effect[];
    private float alpha =1f;

    public Effect(double x, double y, int totalEffect, int ukuranMaks, double jarakMaks, float kecepatan, Color warna){
        this.x=x;
        this.y=y;
        this.jarakMaks=jarakMaks;
        this.ukuranMaks=ukuranMaks;
        this.warna=warna;
        this.totalEffect=totalEffect;
        this.kecepatan=kecepatan;
        createBooms();
    }

    private void createBooms(){
        effect = new MiceDie[totalEffect];
        float per = 360f / totalEffect;
        Random random = new Random();
        for (int i = 1; i <= totalEffect; i++){
            int r = random.nextInt((int) per)+1;
            int ukuranBoom = random.nextInt(ukuranMaks)+1;
            float angle = i*per+r;
            effect [i-1]=new MiceDie(ukuranBoom, angle);
        }
    }

    public void draw(Graphics2D g2){
        AffineTransform oldTransform = g2.getTransform();
        Composite oldComposite = g2.getComposite();
        g2.setColor(warna);
        g2.translate(x, y);
        for (MiceDie b : effect){
            double bx = Math.cos(Math.toRadians(b.getAngle())) * jarakNow;
            double by = Math.sin(Math.toRadians(b.getAngle())) * jarakNow;
            double ukuranBoom = b.getSize();
            double space = ukuranBoom/2;
            if (jarakNow >= jarakMaks - (jarakMaks *0.7f)){
                alpha = (float) ((jarakMaks - jarakNow) / (jarakMaks * 0.7f));
            }
            if (alpha > 1) {
                alpha = 1;
            }else if (alpha<0) {
                alpha = 0;
            }
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g2.fill(new Rectangle2D.Double(bx-space, by-space, ukuranBoom, ukuranBoom));
        }
        g2.setComposite(oldComposite);
        g2.setTransform(oldTransform);
    }

    public void update(){
        jarakNow += kecepatan;
    }

    public boolean check(){
        return jarakNow< jarakMaks;
    }
}
