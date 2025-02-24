// Gold is the currency used in the world and is used to determine whether characters can buy items.s
public class Gold {
    private int amount;
    private int accumulated;

    public Gold() {
        this.amount = 0;
        this.accumulated = 0;
    }

    public void addGold(int quantity) {
        this.amount += quantity;
        this.accumulated += quantity;
        // When the inn has incurred enough gold then allow for all of the items to be used in the world.
        if(this.accumulated >= 500) {
            controller.build.unlockItems();
        }
    }

    public int getAmount() {
        return this.amount;
    }

    public int getAccumulated() {
        return this.accumulated;
    }

    public boolean buyItem(int amount) {
        if(this.amount < amount)
            return false;

        this.amount -= amount;
        return true;
    }

    // Charge different amounts for different items.
    public void buy(EnvironmentItem item) {
        int val = 0;

        if(item instanceof Beer) {
            val = 10;
        } else if(item instanceof ChickenLeg) {
            val = 5;
        } else if(item instanceof Chalice) {
            val = 8;
        } else if(item instanceof Cheese) {
            val = 7;
        }

        this.amount -= val;
        controller.addInnGold(val);
    }

}