public class Knight extends Customer {
    public Knight(float x, float y, int popularity, int goldAmount) {
        super(x, y, ((Shape) new Rectangle2D.Float(x, y, 30, 40)), popularity, goldAmount);
    }

    public void draw() {
        super.draw();
        this.setShape(new Rectangle2D.Float(this.getX(), this.getY(), 30, 40));
        image(KNIGHT_IDLE, this.getX(), this.getY(), 30, 40);
    }

    @Override
    protected void leave() {
        System.out.println("Called:");
        controller.popularity.addPopularity(Faction.KNIGHT, this.evaluatePerformance());
        super.leave();
    }
}