import java.awt.geom.*;
/**
* The spawner class is used to add characters to the game.
*/
public class Spawner {
    /**
    * Constructor for a spawner.
    */
    Spawner(){
    }

    /**
    * Spawn a player to the map. If it's the first wave then a new player is spawned, otherwise a the player's location is changed.
    */
    public Player spawnPlayer() {
        return new Player(displayHeight/2, displayWidth/2);
    }
}