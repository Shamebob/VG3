// Kegs are used to hold beer, and are the source of it in the world. Can be dropped in build mode.
public class Keg extends EnvironmentItem {

    public Keg(float x, float y) {
        super(x, y, ((Shape) new Ellipse2D.Float(x, y, 15, 15)), 20);
    }

    public void draw() {
        image(KEG, this.getX(), this.getY(), 30, 30);
    }
}