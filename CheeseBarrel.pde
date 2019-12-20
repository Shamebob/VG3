public class CheeseBarrel extends EnvironmentItem {

    public CheeseBarrel(float x, float y) {
        super(x, y, ((Shape) new Ellipse2D.Float(x, y, 15, 15)), 20);
    }

    public void draw() {
        image(CHEESE_BARREL, this.getX(), this.getY(), 30, 30);
    }
}