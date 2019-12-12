/**
* The controller is the central logic for the game and stores its state. The Controller is responsible
/* for resolving and updating the game state, drawing the game as well as monitoring if the game is over.
*/
public class Controller {
    boolean gameInPlay;
    Player player;
    Time time;
    Inn inn;
    Gold gold;
    ArrayList<EnvironmentItem> items = new ArrayList<EnvironmentItem>();
    ArrayList<Customer> customers = new ArrayList<Customer>();

    CollisionDetector collisionDetector = new CollisionDetector();
    Cleaner cleaner = new Cleaner();
    Spawner spawner = new Spawner();
    Animator animator = new Animator();
    
    public Controller () {
        this.gold = new Gold();
    }

    public void addInnGold(int amount) {
        this.gold.addGold(amount);
    }

    public void start() {
        this.gameInPlay = true;
        this.player = spawner.spawnPlayer();
        this.customers.add(spawner.spawnCustomer());
        this.items.add(new Keg(displayWidth/2, displayHeight/2));
        this.time = new Time();
        this.inn = new Inn();
    }

    public void movePlayer(float x, float y, Facing direction) {
        PVector change = new PVector(x,y);
        if(!checkPlayerCollisions(change)) {
            this.player.move(change);
            this.player.setFacing(direction);
        }
    }

    private boolean checkPlayerCollisions(PVector change) {
        PVector nextPos = this.player.getPos().add(change);
        if(inn.wallCollision(nextPos.copy()))
            return true;
        return false;
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

    public EnvironmentItem findItem(Shape shape) {

        for(EnvironmentItem item : items) {
            if(this.collisionDetector.checkCollision(item.getShape(), shape)) {
                return item;
            }
        }

        return null;
    }

    public void useItem(EnvironmentItem item, Shape shape) {
        for(Customer customer : this.customers) {
            if(this.collisionDetector.checkCollision(customer.getShape(), shape)) {
                customer.useItem(item);
            }
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
