public class Popularity {
    float knightPopularity, knightLowerThreshold, knightUpperThreshold;
    int knightCounter, knightPopularityLevel;
    boolean seenKnightBoss;


    public Popularity() {
        this.knightPopularity = 0;
        this.knightPopularityLevel = 1;
        this.knightCounter = 0;
        this.knightLowerThreshold = 0;
    }

    public void addKnightPopularity(float popularity) {
        this.knightCounter += 1;
        this.knightPopularity = (this.knightPopularity + popularity);
        System.out.println("Knight Popularity: "+ this.knightPopularity);
        
        if(this.knightPopularity >= (this.knightPopularityLevel * 10)) {
            this.knightLowerThreshold = this.knightPopularityLevel * 10;
            this.knightPopularityLevel += 1;
            this.knightPopularity = 0;
            this.knightCounter = 0;
        }
        
        if(this.knightPopularity <= this.knightLowerThreshold && this.knightPopularity > 0) {
            this.knightPopularityLevel -= 1;
            this.knightLowerThreshold = this.knightPopularityLevel * 10;
        } 
    }

    public int[] getPopularityLevels() {
        return new int[] {this.knightPopularityLevel};
    }

    public int getKnightPopularityLevel() {
        return this.knightPopularityLevel;
    }
}