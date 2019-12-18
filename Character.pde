public abstract class Character extends GameObject {

    public Character (float x, float y, Shape shape) {
        super(x, y, shape);
    }

    public void move(PVector change) {
        if(controller.checkMove(this.getPos(), change)) {
            this.pos.add(change);
        } else {
            this.pos.add(change.mult(-1));
        }
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    protected PVector findDirection() {
        float randomX = random(3, 5);
        float randomY = random(3, 5);

        if(round(random(0,1)) == 0) {
            randomX *= -1;
        }

        if(round(random(0,1)) == 0) {
            randomY *= -1;
        }

        return new PVector(randomX, randomY);
    }

}
