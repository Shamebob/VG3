public class Popularity {
    int totalPopularity;

    public Popularity() {
        this.totalPopularity = 0;
    }

    public void addPopularity(int customerSatisfaction, int customerPopularity) {
        //TODO: Should this be affected by the number of customers the inn has seen?
        this.totalPopularity += (customerSatisfaction * customerPopularity);
    }


}