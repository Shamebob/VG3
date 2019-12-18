public class Boss extends Customer {
    ArrayList<Customer> entourage = new ArrayList<Customer>();
    Faction faction;
    PImage characterImage;

    public Boss(float x, float y, int popularity, int goldAmount, Faction faction, ArrayList<Customer> entourage) {
        super(x, y, ((Shape) new Rectangle2D.Float(x, y, 40, 50)), popularity, goldAmount);
        this.faction = faction;

        switch (faction) {
            case KNIGHT:
                this.characterImage = KNIGHT_BOSS_IDLE;
                break;
        }

        this.entourage = entourage;
    }

    public void draw() {
        super.draw();
        this.setShape(new Rectangle2D.Float(this.getX(), this.getY(), 40, 50));
        image(this.characterImage, this.getX(), this.getY(), 40, 50);
    }

    @Override
    protected void leave() {
        if(faction == Faction.KNIGHT) {
            controller.popularity.addKnightPopularity(this.evaluatePerformance());
        }

        for(Customer customer : entourage) {
            customer.leave();
        }
        super.leave();
    }


}