/**
* A boss is a speciality of a customer, and is used to represent the faction leaders.
*/
public class Boss extends Customer {
    ArrayList<Customer> entourage = new ArrayList<Customer>();
    Faction faction;
    PImage characterImage;
    float height, width;

    // Constructor for a boss. Sets the faction and image to be used to display the boss. These are set so that
    // only one boss class has to be implemented as all of the faction leaders work the same way.
    public Boss(float x, float y, int popularity, int goldAmount, Faction faction, ArrayList<Customer> entourage) {
        super(x, y, ((Shape) new Rectangle2D.Float(x, y, 40, 50)), popularity, goldAmount);
        // Change the image, height and width of the bosses so that they are drawn correctly.
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

        // Set the entourage that will share satisfaction with the boss
        this.entourage = entourage;
    }

    // Draw the boss
    public void draw() {
        super.draw();
        this.setShape(new Rectangle2D.Float(this.getX(), this.getY(), this.width, this.height));
        image(this.characterImage, this.getX(), this.getY(), this.width, this.height);
    }

    // Override the evaluate performance method in order to have the satisfaction of the boss and it's entourage evaluated together.
    @Override
    protected float evaluatePerformance() {
        float entourageSatisfaction = this.satisfaction;
        for(Customer customer : entourage) {
            entourageSatisfaction += customer.getSatisfaction();
        }

        entourageSatisfaction = entourageSatisfaction/(this.entourage.size() + 1);
        return entourageSatisfaction;
    }

    // When the boss leaves, have all of its entourage leave with it.
    @Override
    protected void leave() {
        float averageSatisfaction = this.evaluatePerformance();
        for(Customer customer : entourage) {
            customer.entourageLeave(this.faction, averageSatisfaction);
        }
        
        // Where the boss and it's entourage is not satisfied suitably then end the game.
        if(averageSatisfaction <= 50) {
            controller.endGame(-1);
        }

        controller.popularity.addPopularity(this.faction, averageSatisfaction/this.popularity);
        controller.popularity.bossSatisfied(true, this.faction);
        super.leave();
    }


}