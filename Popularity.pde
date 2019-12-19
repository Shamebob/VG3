final int FACTIONS = 4;
public class Popularity {
    float[] popularity, lowerThresholds, upperThresholds;
    int[] popularityLevels;
    boolean[] satisfiedBoss;


    public Popularity() {
        this.popularity = new float[FACTIONS];
        this.popularityLevels = new int[]{1,1,1,1};
        this.lowerThresholds = new float[FACTIONS];
        this.upperThresholds = new float[FACTIONS];
        this.satisfiedBoss = new boolean[FACTIONS];
    }

    public void addPopularity(Faction faction, float popularity) {
        int index = this.findIndex(faction);
        this.popularity[index] += popularity;
        
        if(this.popularity[index] >= (this.popularityLevels[index] * 10)) {
            this.lowerThresholds[index] = this.popularityLevels[index] * 10;
            this.popularityLevels[index] += 1;
            this.popularity[index] = 0;

            if(this.popularityLevels[index] == 3 && !this.satisfiedBoss[index])
                controller.spawnBoss(faction);
        }
        
        if(this.popularity[index] <= this.lowerThresholds[index] && this.popularityLevels[index] > 1) {
            this.popularity[index] -= 1;
            this.lowerThresholds[index] = this.popularity[index] * 10;
        } 
    }

    public void bossSatisfied(boolean satisfied, Faction faction) {
        this.satisfiedBoss[this.findIndex(faction)] = true;
    }

    private int findIndex(Faction faction) {
        switch (faction) {
                case KNIGHT:
                    return 0;
                case WIZARD:
                    return 1;
                case ELF:
                    return 2;
                case ZOMBIE:
                    return 3;
        }

        return 0;
    }

    public int[] getPopularityLevels() {
        return this.popularityLevels;
    }

    public int getKnightPopularityLevel() {
        return this.popularityLevels[0];
    }

    public int getPopularityLevel(Faction faction) {
        return this.popularityLevels[this.findIndex(faction)];
    }

    public boolean kingReady() {
        for(boolean boss : this.satisfiedBoss) {
                if(!boss)
                    return boss;
        }

        return true;
    }
}