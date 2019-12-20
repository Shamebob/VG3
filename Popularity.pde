final int FACTIONS = 4;
// The popularity class holds information on all of the factions.
public class Popularity {
    float[] popularity, lowerThresholds, upperThresholds;
    int[] popularityLevels;
    boolean[] satisfiedBoss;

    // Initiliase the information for each of the factions popularity.
    public Popularity() {
        this.popularity = new float[FACTIONS];
        this.popularityLevels = new int[]{1,1,1,1};
        this.lowerThresholds = new float[FACTIONS];
        this.upperThresholds = new float[FACTIONS];
        this.satisfiedBoss = new boolean[FACTIONS];
    }

    // Add popularity to a faction's current total.
    public void addPopularity(Faction faction, float popularity) {
        int index = this.findIndex(faction);
        this.popularity[index] += popularity;
        System.out.println("Popularity Gain: " + popularity);
        
        // Check whether the popularity level should increase
        if(this.popularity[index] >= (this.popularityLevels[index] * 10)) {
            this.lowerThresholds[index] = this.popularityLevels[index] * 10;
            this.popularityLevels[index] += 1;
            this.popularity[index] = 0;

            // Check if the boss should spawn.
            if(this.popularityLevels[index] == 3 && !this.satisfiedBoss[index])
                controller.spawnBoss(faction);
        }
        
        // Decrement the popularity level if popularity has dropped below a threshold
        if(this.popularity[index] <= this.lowerThresholds[index] && this.popularityLevels[index] > 1) {
            this.popularity[index] -= 1;
            this.lowerThresholds[index] = this.popularity[index] * 10;
        } 
    }  

    // Establish whether a boss has been satisfied by the inn's perofrmance
    public void bossSatisfied(boolean satisfied, Faction faction) {
        this.satisfiedBoss[this.findIndex(faction)] = true;
    }

    // Find the index in all of the arrays a given faction belongs to.
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

    // Find the popularity level of a given faction
    public int getPopularityLevel(Faction faction) {
        return this.popularityLevels[this.findIndex(faction)];
    }

    // Check whether the game should have the king spawned
    public boolean kingReady() {
        for(boolean boss : this.satisfiedBoss) {
                if(!boss)
                    return boss;
        }

        return true;
    }

    // Calculate how many characters from a given faction should be spawned
    public int calculateSpawn(Faction faction) {
        int index = this.findIndex(faction);

        if(this.popularity[index] < 2) {
            return 1;
        } else {
            return floor(this.popularity[index]/2);
        }
    }
}