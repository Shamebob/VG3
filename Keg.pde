public class Keg extends EnvironmentItem {

    public Keg(float x, float y) {
        super(x, y, ((Shape) new Ellipse2D.Float(x, y, 15, 15)), 30);
    }

    public void draw() {
        image(KEG, this.getX(), this.getY(), 30, 30);
    }
}