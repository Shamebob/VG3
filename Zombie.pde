public class Zombie extends Customer {
    public Zombie(float x, float y, int popularity, int goldAmount) {
        super(x, y, ((Shape) new Rectangle2D.Float(x, y, 30, 40)), popularity, goldAmount);
    }

    public void draw() {
        super.draw();
        this.setShape(new Rectangle2D.Float(this.getX(), this.getY(), 30, 40));
        image(ZOMBIE_IDLE, this.getX(), this.getY(), 30, 40);
    }

    @Override
    protected void leave() {
        controller.popularity.addPopularity(Faction.ZOMBIE, this.evaluatePerformance());
        super.leave();
    }
}