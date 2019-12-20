// The King is the final boss of the game.
public class King extends Customer {
    ArrayList<Customer> entourage = new ArrayList<Customer>();
    PImage characterImage;
    float height, width;

    // Construct a king
    public King(float x, float y, int popularity, int goldAmount, ArrayList<Customer> entourage) {
        super(x, y, ((Shape) new Rectangle2D.Float(x, y, 40, 50)), popularity, goldAmount);
        this.width = 40;
        this.height = 50;
        this.satisfaction = -50;
        this.entourage = entourage;
        this.characterImage = KING_IDLE;
    }

    // Draw the king to the screen.
    public void draw() {
        super.draw();
        this.setShape(new Rectangle2D.Float(this.getX(), this.getY(), this.width, this.height));
        image(this.characterImage, this.getX(), this.getY(), this.width, this.height);
    }

    // Evaluate the performance, either winning or losing the game for the player.
    @Override
    protected float evaluatePerformance() {
        float entourageSatisfaction = this.satisfaction;
        for(Customer customer : entourage) {
            entourageSatisfaction += customer.getSatisfaction();
        }

        entourageSatisfaction = entourageSatisfaction/(this.entourage.size() + 1);
        if(entourageSatisfaction >= 75) {
            controller.endGame(1);
        } else {
            controller.endGame(-1);
        }

        return entourageSatisfaction;
    }

    @Override
    protected void leave() {
        this.evaluatePerformance();
        super.leave();
    }


}