public class Player extends Staff {
    public Player (float x, float y) {
        super(x, y);
    }

    public void setFacing(Facing direction) {
        this.currentFacing = direction;
    }

    public void draw() {
        image(HERO_IDLE, this.getX(), this.getY(), HEIGHT, WIDTH);
        super.draw();
    }
}
