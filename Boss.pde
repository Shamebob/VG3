public class Boss extends Customer {
    ArrayList<Customer> entourage = new ArrayList<Customer>();
    Faction faction;
    PImage characterImage;
    float height, width;

    public Boss(float x, float y, int popularity, int goldAmount, Faction faction, ArrayList<Customer> entourage) {
        super(x, y, ((Shape) new Rectangle2D.Float(x, y, 40, 50)), popularity, goldAmount);
        this.width = 40;
        this.height = 50;
        this.faction = faction;
        this.satisfaction = -50;

        switch (faction) {
            case KNIGHT:
                this.characterImage = KNIGHT_BOSS_IDLE;
                break;
            
            case ELF:
                this.width = 30;
                this.height = 40;
                this.characterImage = ELF_BOSS_IDLE;
                break;

            case WIZARD:
                this.width = 30;
                this.height = 40;
                this.characterImage = WIZARD_BOSS_IDLE;
                break;
            
            case ZOMBIE:
                this.characterImage = ZOMBIE_BOSS_IDLE;
                break;
        }

        this.entourage = entourage;
    }

    public void draw() {
        super.draw();
        this.setShape(new Rectangle2D.Float(this.getX(), this.getY(), this.width, this.height));
        image(this.characterImage, this.getX(), this.getY(), this.width, this.height);
    }

    @Override
    protected float evaluatePerformance() {
        float entourageSatisfaction = this.satisfaction;
        for(Customer customer : entourage) {
            entourageSatisfaction += customer.getSatisfaction();
        }

        entourageSatisfaction = entourageSatisfaction/(this.entourage.size() + 1);
        return entourageSatisfaction;
    }

    @Override
    protected void leave() {
        float averageSatisfaction = this.evaluatePerformance();
        for(Customer customer : entourage) {
            customer.entourageLeave(this.faction, averageSatisfaction);
        }
        
        if(averageSatisfaction <= 50) {
            controller.endGame(-1);
        }

        controller.popularity.addPopularity(this.faction, averageSatisfaction/this.popularity);
        controller.popularity.bossSatisfied(true, this.faction);
        super.leave();
    }


}