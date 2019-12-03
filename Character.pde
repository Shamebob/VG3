public abstract class Character extends GameObject {

    public Character (float x, float y, Shape shape) {
        super(x, y, shape);
    }

    public void move(PVector change) {
        this.pos.add(change);
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }
}
