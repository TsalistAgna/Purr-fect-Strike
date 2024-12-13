public interface Position {
    public void changeLocation(double x, double y);
    public void update();
    public void changeAngle(float angle);
    public double getX();
    public double getY();
    public float getAngle();
}
