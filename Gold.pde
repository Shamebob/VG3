public class Gold {
    private int amount;

    public Gold() {
        this.amount = 0;
    }

    public void addGold(int quantity) {
        this.amount += quantity;
    }

    public int getAmount() {
        return this.amount;
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