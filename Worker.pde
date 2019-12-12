public class Worker extends Staff {
    public Worker (float x, float y) {
        super(x, y);
    }

    public void draw() {
        fill(0, 255, 0);
        rect(this.getX(), this.getY(), HEIGHT,WIDTH);
        super.draw();
    }

}
