import java.awt.geom.*;
import java.util.Arrays;
/**
* The spawner class is used to add characters to the game.
*/
public class Spawner {
    PVector doorPos;
    int knightSpawn, customersInDay;
    PVector[] locations;
    /**
    * Constructor for a spawner.
    */
    Spawner(){
        this.knightSpawn = 0;
    }

    public void setDoorPos(PVector doorPos) {
        this.doorPos = doorPos;
        float x = this.doorPos.x;
        float y = displayHeight - (displayHeight/10);
        this.locations = new PVector[]{new PVector(x - 50, y - 50), new PVector(x-50, y+50), new PVector(x+50, y-50), new PVector(x+50, y+50)};
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
        int goldAmount = round(random(30, 80));
        int popularity = round(random(30, 80));
        Customer customer;
        customer = new Knight(this.doorPos.x + 10, displayHeight - (displayHeight/10), popularity, goldAmount);
        generateLikesAndDislikes(customer, controller.popularity.getKnightPopularityLevel());
        return customer;
    }

    public Worker spawnWorker(ItemType item) {
        //TODO: Have them walk in the door
        return new Worker(displayWidth/2 - 100, displayHeight/2 - 100, item);
    }

    public Customer spawnEntourage(Faction faction, float x, float y) {
        int goldAmount = round(random(30, 80));
        int popularity = 80;
        Customer customer = null;

        switch (faction) {
            case KNIGHT:
                customer = new Knight(x, y, popularity, goldAmount);
                break;

            case ELF:
                customer = new Elf(x, y, popularity, goldAmount);
                break;

            case WIZARD:
                customer = new Wizard(x, y, popularity, goldAmount);
                break;
            
            case ZOMBIE:
                customer = new Zombie(x, y, popularity, goldAmount);
                break;
        }

        generateLikesAndDislikes(customer, controller.popularity.getPopularityLevel(faction));
        return customer;
    }

    public Boss spawnBoss(Faction faction) {
        ArrayList<Customer> entourage = new ArrayList<Customer>();

        for(PVector location : this.locations) {
            entourage.add(spawnEntourage(faction, location.x, location.y));
        }

        Boss boss = new Boss(this.doorPos.x + 10, displayHeight - (displayHeight/10), 500, 500, faction, entourage);
        generateLikesAndDislikes(boss, controller.popularity.getPopularityLevel(faction));
        return boss;
    }

    private void generateLikesAndDislikes(Customer customer, int popularityLevel) {
        //TODO: Give likes and dislikes based on accumulated gold and not popularity.
        ArrayList<ItemType> items = new ArrayList<ItemType>(Arrays.asList(ItemType.values()));
        //TODO: Allow this int itemNumber = popularityLevel;
        int itemNumber = floor(items.size()/2);

        ItemType[] likedItems = new ItemType[itemNumber];
        ItemType[] dislikedItems = new ItemType[itemNumber];

        for(int i = 0; i < itemNumber; i++) {
            int index = floor(random(0, items.size()));
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