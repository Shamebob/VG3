/**
* A chalice is an item that can be consumed by patron's.
*/
public class Chalice extends EnvironmentItem {
    public Chalice(float x, float y) {
        super(x, y, ((Shape) new Rectangle2D.Float(x, y, 25, 25)), 1);
    }

    public void draw() {
        image(CHALICE, this.getX(), this.getY(), 25, 25);

    }
}