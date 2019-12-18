import java.awt.geom.*;
import java.util.Arrays;
/**
* The spawner class is used to add characters to the game.
*/
public class Spawner {
    PVector doorPos;
    int knightSpawn, customersInDay;
    /**
    * Constructor for a spawner.
    */
    Spawner(){
        this.knightSpawn = 0;
    }

    public void setDoorPos(PVector doorPos) {
        this.doorPos = doorPos;
    }

    public void setKnightSpawn(int count) {
        this.knightSpawn = count;
        this.customersInDay += count;
    }

    public int getCustomersInDay() {
        return this.customersInDay;
    }
    /**
    * Spawn a player to the map. If it's the first wave then a new player is spawned, otherwise a the player's location is changed.
    */
    public Player spawnPlayer() {
        return new Player(displayWidth/2, displayHeight/2);
    }

    public Customer spawnCustomer() {
        int goldAmount = 50;
        int popularity = 50;
        Customer customer;
        customer = new Knight(this.doorPos.x + 10, displayHeight - (displayHeight/10), popularity, goldAmount);
        generateLikesAndDislikes(customer, controller.popularity.getKnightPopularityLevel());
        return customer;
    }
    
    private Customer spawnKnight(float x, float y) {
        int goldAmount = 50;
        int popularity = 50;
        Customer customer;
        customer = new Knight(x, y, popularity, goldAmount);
        generateLikesAndDislikes(customer, controller.popularity.getKnightPopularityLevel());
        return customer;
    }

    public Boss spawnKnightBoss() {
        float x = this.doorPos.x;
        float y = displayHeight - (displayHeight/10);
        ArrayList<Customer> entourage = new ArrayList<Customer>();
        PVector[] locations = new PVector[]{new PVector(x - 50, y - 50), new PVector(x-50, y+50), new PVector(x+50, y-50), new PVector(x+50, y+50)};

        for(PVector location : locations) {
            entourage.add(spawnKnight(x, y));
        }
        Boss boss = new Boss(this.doorPos.x + 10, displayHeight - (displayHeight/10), 500, 500, Faction.KNIGHT, entourage);
        generateLikesAndDislikes(boss, controller.popularity.getKnightPopularityLevel());
        return boss;
    }

    private void generateLikesAndDislikes(Customer customer, int popularityLevel) {
        //TODO: Give likes and dislikes based on accumulated gold and not popularity.
        ArrayList<ItemType> items = new ArrayList<ItemType>(Arrays.asList(ItemType.values()));
        int itemNumber = popularityLevel;
        ItemType[] likedItems = new ItemType[itemNumber];
        ItemType[] dislikedItems = new ItemType[itemNumber];

        for(int i = 0; i < itemNumber; i++) {
            int index = floor(random(0, items.size() - 1));
            likedItems[i] = items.get(index);
            items.remove(index);

            index = floor(random(0, items.size()));
            dislikedItems[i] = items.get(index);
            items.remove(index);
        }

        customer.setLikes(likedItems);
        customer.setDislikes(dislikedItems);
        controller.animator.newCustomer(likedItems, dislikedItems);
    }
}