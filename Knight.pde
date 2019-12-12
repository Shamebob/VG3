public class Knight extends Customer {
    public Knight(float x, float y, int popularity, int goldAmount) {
        super(x, y, ((Shape) new Rectangle2D.Float(x, y, 20, 20)), popularity, goldAmount);
    }

    public void draw() {
        this.setShape(new Rectangle2D.Float(this.getX(), this.getY(), 20, 20));
        ellipse(this.getX(), this.getY(), 20, 20);
        super.draw();
    }
}