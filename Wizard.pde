/**
* An Wizard is a specialisation of a Customer that are from the Wizard faction
*/
public class Wizard extends Customer {
    public Wizard(float x, float y, int popularity, int goldAmount) {
        super(x, y, ((Shape) new Rectangle2D.Float(x, y, 30, 40)), popularity, goldAmount);
    }

    public void draw() {
        super.draw();
        this.setShape(new Rectangle2D.Float(this.getX(), this.getY(), 30, 40));
        image(WIZARD_IDLE, this.getX(), this.getY(), 30, 40);
    }

    // Add popularity to the Wizards faction when this character leaves
    @Override
    protected void leave() {
        controller.popularity.addPopularity(Faction.WIZARD, this.evaluatePerformance());
        super.leave();
    }
}