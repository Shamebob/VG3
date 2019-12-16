import java.awt.geom.*;
import java.util.Arrays;
/**
* The spawner class is used to add characters to the game.
*/
public class Spawner {
    PVector doorPos;
    /**
    * Constructor for a spawner.
    */
    Spawner(){
    }

    public void setDoorPos(PVector doorPos) {
        this.doorPos = doorPos;
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

    private void generateLikesAndDislikes(Customer customer, int popularityLevel) {
        //TODO: Give likes and dislikes based on accumulated gold and not popularity.
        ArrayList<ItemType> items = new ArrayList<ItemType>(Arrays.asList(ItemType.values()));
        int itemNumber = popularityLevel;
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