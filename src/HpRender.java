import java.awt.Graphics2D;
import java.awt.Shape;

public abstract class HpRender {
    protected final HealthPoint hp;

    public HpRender(HealthPoint hp) {
        this.hp = hp;
    }

    // Metode abstrak untuk merender HP, harus diimplementasikan di subclass
    protected abstract void hpRender(Graphics2D g2, Shape shape, double y);

    public boolean updateHP(double cutHP) {
        hp.setCurrentHp(hp.getCurrentHp() - cutHP);
        return hp.getCurrentHp() > 0;
    }

    public double getHP() {
        return hp.getCurrentHp();
    }

    public void resetHP() {
        hp.setCurrentHp(hp.getMAX_HP());
    }
}