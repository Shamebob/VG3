public abstract class Customer extends Character {
    int popularity;
    float satisfaction;
    Gold money;
    PVector direction;
    int moveCounter;
    boolean leaving;

    public Customer(float x, float y, Shape shape, int popularity, int goldAmount) {
        super(x, y, shape);
        this.popularity = popularity;
        this.satisfaction = 0;
        this.money = new Gold();
        this.money.addGold(goldAmount);
        this.direction = this.findDirection();
        this.moveCounter = 0;
        this.leaving = false;
    }

    public void draw() {
        if(this.leaving) {
            this.move(this.direction);
            return;
        }

        if(this.moveCounter % 30 == 0) {
            this.move(this.direction);

            if(this.moveCounter % 120 == 0) 
                this.direction = this.findDirection();
        }
        
        this.moveCounter += 1;
    }

    private PVector findDirection() {
        return new PVector(random(-2, 2), random(-2, 2));
    }

    public void useItem(EnvironmentItem item) {
        if(item instanceof Beer) {
            System.out.println("Drinking Beer!");
            this.money.buy(item);
            //TODO: If the item is correct as to what they want, + lots. Diminishing returns based on likes and dislikes.
            //TODO: Could have it so that new patrons declare what they like?
        }

        if(this.money.getAmount() < 10) {
            this.leaving = true;
            this.direction = controller.inn.getDoorPos().sub(this.getPos()).normalize();
        } 
    }
}