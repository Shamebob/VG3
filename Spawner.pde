import java.awt.geom.*;
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
        return new Knight(this.doorPos.x + 10, displayHeight - (displayHeight/10), popularity, goldAmount);
    }
}