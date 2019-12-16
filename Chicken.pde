public class Chicken extends EnvironmentItem {

    public Chicken(float x, float y) {
        super(x, y, ((Shape) new Ellipse2D.Float(x, y, 15, 15)), 10);
    }

    public void draw() {
        image(CHICKEN, this.getX(), this.getY(), 30, 30);
    }
}