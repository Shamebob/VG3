public abstract class Customer extends Character {
    int popularity;
    Gold money;
    PVector direction;
    int moveCounter;

    public Customer(float x, float y, Shape shape, int popularity, int goldAmount) {
        super(x, y, shape);
        this.popularity = popularity;
        this.money = new Gold();
        this.money.addGold(goldAmount);
        this.direction = this.findDirection();
        this.moveCounter = 0;
    }

    public void draw() {
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
        }
    }
}