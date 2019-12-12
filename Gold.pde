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
        if(item instanceof Beer) {
            this.amount -= 10;
            controller.addInnGold(10);
        }
    }

}