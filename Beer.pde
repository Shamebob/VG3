public class Beer extends EnvironmentItem {
    public Beer(float x, float y) {
        super(x, y, ((Shape) new Rectangle2D.Float(x, y, 25, 25)), 1);
    }

    public void draw() {
        image(BEER, this.getX(), this.getY(), 25, 25);

    }
}