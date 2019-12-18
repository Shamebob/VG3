/**
* The controller is the central logic for the game and stores its state. The Controller is responsible
/* for resolving and updating the game state, drawing the game as well as monitoring if the game is over.
*/
public class Controller {
    boolean gameInPlay, endDay, buildMove;
    Player player;
    Time time;
    Inn inn;
    Gold gold;
    ArrayList<EnvironmentItem> items = new ArrayList<EnvironmentItem>();
    ArrayList<Customer> customers = new ArrayList<Customer>();
    ArrayList<Feeling> feelings = new ArrayList<Feeling>();

    CollisionDetector collisionDetector = new CollisionDetector();
    Cleaner cleaner = new Cleaner();
    Spawner spawner = new Spawner();
    Animator animator = new Animator();
    Popularity popularity = new Popularity();
    
    public Controller () {
        this.gold = new Gold();
    }

    public void addInnGold(int amount) {
        this.gold.addGold(amount);
    }

    public void startDay() {
        if(this.endDay) {
            this.time.newDay();
            this.calculateCustomers();

            for(Customer customer: this.customers) {
                customer.leave();
            }

            this.customers = new ArrayList<Customer>();
            this.customers.add(spawner.spawnCustomer());
            this.endDay = false;
        }
    }

    /**
    * Calculates the customers that have to attend the inn that day, based on popularity of faction.
    */
    private void calculateCustomers() {
        if(this.popularity.knightPopularityLevel == 1) {
            this.spawner.setKnightSpawn(5);
        } else {
            this.spawner.setKnightSpawn(floor(this.popularity.knightPopularity/2));
        }

        this.time.setSpawnTimer(960/this.spawner.getCustomersInDay());
    }

    public void start() {
        this.endDay = true;
        this.time = new Time();
        this.inn = new Inn();
        this.calculateCustomers();
        this.spawner.setDoorPos(this.inn.getDoorPos());
        this.gameInPlay = true;
        this.player = spawner.spawnPlayer();
        // this.spawnBoss();
        this.items.add(new Keg(displayWidth/2, displayHeight/2));
        this.items.add(new Chicken(displayWidth/2 + 100, displayHeight/2 + 100));
    }

    public void spawnBoss() {
        Boss boss = spawner.spawnKnightBoss();
        for(Customer customer : boss.entourage) {
            this.customers.add(customer);
        }

        this.customers.add(boss);
    }

    public void movePlayer(float x, float y, Facing direction) {
        PVector change = new PVector(x,y);
        if(checkMove(this.player.getPos(), change)) {
            this.player.move(change);
            this.player.setFacing(direction);
        }
    }

    public boolean checkMove(PVector currentPos, PVector change) {
        PVector nextPos = currentPos.add(change);
        if(inn.wallCollision(nextPos.copy()))
            return false;
        return true;
    }

    public void drawGame() {
        if(this.endDay) {
            animator.endDay(this);
        } else if(this.gameInPlay) {
            animator.drawActiveGame(this);
            this.cleaner.cleanGame();
            this.collisionDetector.checkCollisions();
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

    public void addFeeling(Feeling feeling) {
        this.feelings.add(feeling);
    }

    public void newCustomer() {
        this.customers.add(spawner.spawnCustomer());
    }
}
