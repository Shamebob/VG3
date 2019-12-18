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

    public void buy(EnvironmentItem item) {
        int val = 0;
        if(item instanceof Beer) {
            val = 10;
        } else if(item instanceof ChickenLeg) {
            val = 5;
        }

        this.amount -= val;
        controller.addInnGold(val);
    }

}