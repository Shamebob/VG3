enum ItemType {
    BEER, CHICKENLEG, CHALICE, CHEESE;
}

enum Faction {
    KNIGHT, WIZARD, ELF, ZOMBIE;
}

// A customer is an abstraction of a character that is attending the inn to purchase it's services.
public abstract class Customer extends Character {
    int popularity, waitTime, diminishingReturn, waitCounter;
    float satisfaction;
    Gold money;
    int moveCounter;
    boolean entering, leaving;
    ItemType[] likes, dislikes;

    // Constructor of a customer.
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
        this.waitTime = 240;
        this.waitCounter = 0;
        this.enter();
    }

    public void setLikes(ItemType[] likes) {
        this.likes = likes;
    }

    public void setDislikes(ItemType[] dislikes) {
        this.dislikes = dislikes;
    }

    // Draw the customer, checking the timers that are used for moving and diminishing returns.
    public void draw() {
        if(this.waitTime == 0)
            this.resetWait();

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
            this.destroy();
        }

        if(!(this.leaving || this.entering))
            this.waitTime -= 1;
        
        this.moveCounter += 1;
    }

    // When an item has been used reset the wait counter.
    private void resetWait() {
        if(this.waitTime == 0) {
            this.satisfaction -= 10;
            this.waitCounter += 1;
            controller.addFeeling(new Feeling(this.getX(), this.getY() - 5, Emotion.SAD));

            if(this.waitCounter == 3)
                this.leave();
        }

        this.waitTime = 240;
    }

    // Move the customer
    @Override
    public void move(PVector change) {
        if(this.leaving || this.entering) {
            this.pos.add(change);
        } else {
            super.move(change);
        }
    }
    
    // Use an item on a customer, establishing it's reaction and the transfer of gold
    public void useItem(EnvironmentItem item) {
        boolean likedItem = false;
        ItemType itemType = null;
        if(this.leaving)
            return;

        if(item instanceof Beer) {
            itemType = ItemType.BEER;
        } else if(item instanceof ChickenLeg) {
            itemType = ItemType.CHICKENLEG;
        } else if(item instanceof Chalice) {
            itemType = ItemType.CHALICE;
        } else if(item instanceof Cheese) {
            itemType = ItemType.CHEESE;
        }

        this.money.buy(item);
        likedItem = this.reaction(itemType);
        this.addSatisfaction(likedItem);
        this.diminishingReturn = 120;
        this.resetWait();

        if(this.money.getAmount() < 10) {
            this.leave();
        } 
    }

    // Add satisfaction to the customer
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

    // Check the customer's reaction to receiving an item and notify the player.
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

    // Have the customer walk to the inn entrance
    protected void enter() {
        this.direction = controller.inn.getDoorPos().sub(this.getPos()).normalize();
        this.direction.mult(3);
        this.entering = true;
    }

    // Check wether or not the customer has entered the inn
    protected void checkEntered() {
        if(this.getY() <= (controller.inn.getDoorPos().y - 20))
            this.entering = false;
    }

    // Have the character leave the inn.
    protected void leave() {
        this.leaving = true;
        controller.addFeeling(new Feeling(this.getX() + 5, this.getY() - 5, Emotion.LEAVING));
        this.direction = controller.inn.getDoorPos().sub(this.getPos()).normalize().mult(4);
    }

    // Evaluate the performance of the inn and then add to it's popularity
    protected float evaluatePerformance() {
        System.out.println("Performance: "+(this.satisfaction + this.popularity)/100);
        return (this.satisfaction + this.popularity)/100;
    }

    public float getSatisfaction() {
        return this.satisfaction;
    }

    public int getPopularity() {
        return this.popularity;
    }

    public int getDiminishingReturns() {
        return this.diminishingReturn;
    }


    public void entourageLeave(Faction faction, float satisfaction) {
        this.satisfaction = satisfaction;
        controller.popularity.addPopularity(faction, this.evaluatePerformance());
        this.leave();
    }
}