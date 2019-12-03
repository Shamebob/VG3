final float HEIGHT = 20;
final float WIDTH = 20;

public abstract class Staff extends Character {
    public Staff (float x, float y) {
        super(x, y, ((Shape) new Rectangle2D.Float(x, y, HEIGHT, WIDTH)));
    }

    public void draw() {
        rect(this.getX(), this.getY(), HEIGHT,WIDTH);
        this.setShape(new Rectangle2D.Float(this.getX(), this.getY(), HEIGHT, WIDTH));
    }
}
