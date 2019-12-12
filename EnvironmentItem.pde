public abstract class EnvironmentItem extends GameObject {
    int uses;
    public EnvironmentItem(float x, float y, Shape shape, int uses) {
        super(x, y, shape);
        this.uses = uses;
    }

    public void use() {
        this.uses -=1;
        if(this.uses == 0) {
            this.destroy();
        }
    }
}