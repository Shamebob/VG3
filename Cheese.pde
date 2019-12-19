public class Cheese extends EnvironmentItem {
    public Cheese(float x, float y) {
        super(x, y, ((Shape) new Rectangle2D.Float(x, y, 25, 25)), 1);
    }

    public void draw() {
        image(CHEESE, this.getX(), this.getY(), 25, 25);

    }
}