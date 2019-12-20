/**
* An Elf is a specialisation of a Customer that are from the elven faction
*/
public class Elf extends Customer {
    public Elf(float x, float y, int popularity, int goldAmount) {
        super(x, y, ((Shape) new Rectangle2D.Float(x, y, 30, 50)), popularity, goldAmount);
    }

    public void draw() {
        super.draw();
        this.setShape(new Rectangle2D.Float(this.getX(), this.getY(), 30, 50));
        image(ELF_IDLE, this.getX(), this.getY(), 30, 50);
    }

    // Add popularity to the elven faction when this character leaves
    @Override
    protected void leave() {
        controller.popularity.addPopularity(Faction.ELF, this.evaluatePerformance());
        super.leave();
    }
}