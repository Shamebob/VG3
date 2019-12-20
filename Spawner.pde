import java.awt.geom.*;
import java.util.Arrays;
/**
* The spawner class is used to add characters to the game.
*/
public class Spawner {
    PVector doorPos;
    int knightSpawn, elfSpawn, wizardSpawn, zombieSpawn, customersInDay;
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

    public void setWizardSpawn(int count) {
        this.wizardSpawn = count;
        this.customersInDay += count;
    }

    public void setElfSpawn(int count) {
        this.elfSpawn = count;
        this.customersInDay += count;
    }

    public void setZombieSpawn(int count) {
        this.zombieSpawn = count;
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

    // Randomly spawn Customers when there are some left to be spawned.
    public Customer spawnCustomer() {
        int goldAmount = round(random(30, 80));
        int popularity = round(random(30, 80));
        float x = this.doorPos.x + 10;
        float y = displayHeight - (displayHeight/10);
        boolean didSpawn = false;
        Customer customer = null;
        if(this.customersInDay > 0) {
            while(customer == null) {
                // Randomise which type of customer should spawn
                int val = floor(random(0, 3.5));
                switch(val) {
                    case 0:
                        if(this.knightSpawn > 0) {
                            customer = new Knight(x, y, popularity, goldAmount);
                            this.knightSpawn -= 1;
                        }
                        break;

                    case 1:
                        if(this.wizardSpawn > 0) {
                            customer = new Wizard(x, y, popularity, goldAmount);
                            this.wizardSpawn -= 1;
                        }
                        break;

                    case 2:
                        if(this.elfSpawn > 0) {
                            customer = new Elf(x, y, popularity, goldAmount);
                            this.elfSpawn -= 1;
                        }
                        break;

                    case 3:
                        if(this.zombieSpawn > 0) {
                            customer = new Zombie(x, y, popularity, goldAmount);
                            this.zombieSpawn -= 1;
                        }
                        break;
                }

            }

            this.customersInDay -= 1;
            generateLikesAndDislikes(customer);
            return customer;
        } else {
            return null;
        }
    }

    // Spawn a new Worker AI.
    public Worker spawnWorker(ItemType item, float x, float y) {
        return new Worker(x, y, item);
    }

    // Spawn an entourage for a boss froma  given faction.
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

        generateLikesAndDislikes(customer);
        return customer;
    }

    // Spawn a king with an assorted entourage
    public King spawnKing() {
        ArrayList<Customer> entourage = new ArrayList<Customer>();
        Faction[] factions = Faction.values();
        int counter = 0;
        for(PVector location : this.locations) {
            entourage.add(spawnEntourage(factions[counter], location.x, location.y));
            counter += 1;
        }

        King king = new King(this.doorPos.x + 10, displayHeight - (displayHeight/10), 1000, 1000, entourage);
        generateLikesAndDislikes(king);
        return king;
    }

    // Spawn a new boss character
    public Boss spawnBoss(Faction faction) {
        ArrayList<Customer> entourage = new ArrayList<Customer>();

        for(PVector location : this.locations) {
            entourage.add(spawnEntourage(faction, location.x, location.y));
        }

        Boss boss = new Boss(this.doorPos.x + 10, displayHeight - (displayHeight/10), 500, 500, faction, entourage);
        generateLikesAndDislikes(boss);
        return boss;
    }

    // Generate the likes and dislikes of a customer
    private void generateLikesAndDislikes(Customer customer) {
        ArrayList<ItemType> items;
        // Limit the items that can be used until they are unlocked by the player
        if(controller.gold.accumulated >= 500) {
            items = new ArrayList<ItemType>(Arrays.asList(ItemType.values()));
        } else {
            items = new ArrayList<ItemType>();
            items.add(ItemType.BEER);
            items.add(ItemType.CHICKENLEG);
        }
        int itemNumber = floor(items.size()/2);

        ItemType[] likedItems = new ItemType[itemNumber];
        ItemType[] dislikedItems = new ItemType[itemNumber];
        
        // Add one item to the likes and one to the dislikes each time.
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