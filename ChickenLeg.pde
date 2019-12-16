public class ChickenLeg extends EnvironmentItem {
    public ChickenLeg(float x, float y) {
        super(x, y, ((Shape) new Rectangle2D.Float(x, y, 25, 25)), 1);
    }

    public void draw() {
        image(CHICKEN_LEG, this.getX(), this.getY(), 25, 25);

    }
}