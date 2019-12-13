enum ItemType {
    BEER;
}

public abstract class Customer extends Character {
    int popularity;
    float satisfaction;
    Gold money;
    PVector direction;
    int moveCounter;
    boolean entering, leaving;
    ItemType[] likes, dislikes;

    public Customer(float x, float y, Shape shape, int popularity, int goldAmount) {
        super(x, y, shape);
        this.popularity = popularity;
        this.satisfaction = 0;
        this.money = new Gold();
        this.money.addGold(goldAmount);
        this.direction = this.findDirection();
        this.moveCounter = 0;
        this.leaving = false;
        this.likes = new ItemType[]{};
        this.dislikes = new ItemType[]{ItemType.BEER};
        this.enter();
    }

    public abstract void draw();

    private PVector findDirection() {
        return new PVector(random(-2, 2), random(-2, 2));
    }

    public void useItem(EnvironmentItem item) {
        if(item instanceof Beer) {
            this.reaction(ItemType.BEER);
            this.money.buy(item);
            //TODO: If the item is correct as to what they want, + lots. Diminishing returns based on likes and dislikes
            //TODO: Could have it so that new patrons declare what they like?
        }

        if(this.money.getAmount() < 10) {
            this.leave();
        } 
    }

    private void reaction(ItemType item) {
        for(ItemType like : this.likes) {
            if(like == item) {
                controller.addFeeling(new Feeling(this.getX(), this.getY() - 5, Emotion.HAPPY));
                return;
            }
        }

        for(ItemType dislike : this.dislikes) {
            if(dislike == item) {
                controller.addFeeling(new Feeling(this.getX(), this.getY() - 5, Emotion.SAD));
                this.satisfaction -= 10;
                return;
            }
        }
    }

    protected void enter() {
        this.direction = controller.inn.getDoorPos().sub(this.getPos()).normalize();
        this.direction.y -= 3;
        this.entering = true;
    }

    protected void checkEntered() {
        if(this.getY() <= (controller.inn.getDoorPos().y - 20))
            this.entering = false;
    }

    protected void leave() {
        this.leaving = true;
        this.direction = controller.inn.getDoorPos().sub(this.getPos()).normalize();
    }

    protected float evaluatePerformance() {
        return this.satisfaction/this.popularity;
    }
}