/**
* The controller is the central logic for the game and stores its state. The Controller is responsible
/* for resolving and updating the game state, drawing the game as well as monitoring if the game is over.
*/
public class Controller {
    boolean gameInPlay;
    Player player;
    Time time;

    CollisionDetector collisionDetector = new CollisionDetector();
    Cleaner cleaner = new Cleaner();
    Spawner spawner = new Spawner();
    Animator animator = new Animator();
    
    public Controller () {
    }

    public void start() {
        this.gameInPlay = true;
        this.player = spawner.spawnPlayer();
        this.time = new Time();
    }

    public void movePlayer(float x, float y) {
        this.player.move(new PVector(x,y));
    }

    public void drawGame() {
        if(this.gameInPlay) {
            animator.drawActiveGame(this);
        } else {
            fill(0,255,0);
            textSize(50);
            text("Game Over", displayWidth/2 - 100, displayHeight/2 - 25);
        }

    }

    /**
    * Draw all of the components of the game.
    */
    public void drawActiveGame() {
        textSize(16);
        fill(0,255,0);
        this.player.draw();

        this.cleaner.cleanGame();
        this.collisionDetector.checkCollisions();
    }

}
