public class Popularity {
    float knightPopularity;
    int knightCounter;
    int knightPopularityLevel;

    public Popularity() {
        this.knightPopularity = 0;
        this.knightPopularityLevel = 1;
        this.knightCounter = 0;
    }

    public void addKnightPopularity(float popularity) {
        this.knightCounter += 1;
        this.knightPopularity = (this.knightPopularity + popularity)/knightCounter;
        System.out.println("Popularity: "+ this.knightPopularity);
        
        if(this.knightPopularity >= (this.knightPopularityLevel * 10)) {
            this.knightPopularityLevel += 1;
            this.knightPopularity = 0;
            this.knightCounter = 0;
        }
    }

    public int[] getPopularityLevels() {
        return new int[] {this.knightPopularityLevel};
    }
}