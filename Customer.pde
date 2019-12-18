enum ItemType {
    BEER, CHICKENLEG;
}

enum Faction {
    KNIGHT;
}

public abstract class Customer extends Character {
    int popularity;
    float satisfaction;
    Gold money;
    PVector direction;
    int moveCounter;
    boolean entering, leaving;
    ItemType[] likes, dislikes;
    int diminishingReturn;

    public Customer(float x, float y, Shape shape, int popularity, int goldAmount) {
        super(x, y, shape);
        this.popularity = popularity;
        this.satisfaction = 0;
        this.money = new Gold();
        this.money.addGold(goldAmount);
        this.direction = this.findDirection();
        this.moveCounter = 0;
        this.leaving = false;
        this.likes = new ItemType[0];
        this.dislikes = new ItemType[0];
        this.diminishingReturn = 0;
        this.enter();
    }

    public void setLikes(ItemType[] likes) {
        this.likes = likes;
    }

    public void setDislikes(ItemType[] dislikes) {
        this.dislikes = dislikes;
    }

    public void draw() {
        if (this.diminishingReturn > 0)
            this.diminishingReturn -= 1;

        if(this.entering)
            this.checkEntered();

        if(this.moveCounter % 120 == 0 && !this.leaving && !this.entering) 
            this.direction = super.findDirection();
    
        if(this.moveCounter % 5 == 0) {
            this.move(this.direction);
        }

        if(this.leaving && this.getY() >= displayHeight) {
            println("Bye bitch");
            this.destroy();
        }

        this.moveCounter += 1;
    }

   
    public void useItem(EnvironmentItem item) {
        boolean likedItem = false;
        ItemType itemType = null;
        if(this.leaving)
            return;
        
        if(item instanceof Beer) {
            itemType = ItemType.BEER;
            //TODO: If the item is correct as to what they want, + lots. Diminishing returns based on likes and dislikes
            //TODO: Could have it so that new patrons declare what they like?
        } else if(item instanceof ChickenLeg) {
            itemType = ItemType.CHICKENLEG;
        }

        this.money.buy(item);
        likedItem = this.reaction(itemType);
        this.addSatisfaction(likedItem);
        println("Satisfaction: : "+ this.satisfaction);
        this.diminishingReturn = 120;

        if(this.money.getAmount() < 10) {
            this.leave();
        } 
    }

    private void addSatisfaction(boolean likedItem) {
        float satisfaction = 0;
        if(likedItem) {
            satisfaction = 25;
        } else {
            satisfaction = -25;
        }

        if(this.diminishingReturn > 0)
            satisfaction = satisfaction/this.diminishingReturn;

        this.satisfaction += satisfaction;
    }

    private boolean reaction(ItemType item) {
        for(ItemType like : this.likes) {
            if(like == item) {
                controller.addFeeling(new Feeling(this.getX(), this.getY() - 5, Emotion.HAPPY));
                return true;
            }
        }

        for(ItemType dislike : this.dislikes) {
            if(dislike == item) {
                controller.addFeeling(new Feeling(this.getX(), this.getY() - 5, Emotion.SAD));
                return false;
            }
        }

        return false;
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
        this.direction = controller.inn.getDoorPos().sub(this.getPos());
    }

    protected float evaluatePerformance() {
        return this.satisfaction/this.popularity;
    }
}