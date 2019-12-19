/**
* The controller is the central logic for the game and stores its state. The Controller is responsible
/* for resolving and updating the game state, drawing the game as well as monitoring if the game is over.
*/
public class Controller {
    boolean gameInPlay, endDay, buildMode;
    Player player;
    Time time;
    Inn inn;
    Gold gold;
    ArrayList<EnvironmentItem> items = new ArrayList<EnvironmentItem>();
    ArrayList<Customer> customers = new ArrayList<Customer>();
    ArrayList<Feeling> feelings = new ArrayList<Feeling>();
    ArrayList<Worker> workers = new ArrayList<Worker>();
    Boss nextBoss;
    CollisionDetector collisionDetector = new CollisionDetector();
    Cleaner cleaner = new Cleaner();
    Spawner spawner = new Spawner();
    Animator animator = new Animator();
    Popularity popularity = new Popularity();
    Build build = new Build();
    
    public Controller () {
        this.gold = new Gold();
    }

    public void start() {
        this.endDay = true;
        this.time = new Time();
        this.inn = new Inn();
        this.buildMode = true;
        this.animator.setupBuildItems();
        this.gold.addGold(100);
        this.calculateCustomers();
        this.spawner.setDoorPos(this.inn.getDoorPos());
        this.gameInPlay = true;
        this.player = spawner.spawnPlayer();
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

            if(this.nextBoss != null) {
                this.customers.add(this.nextBoss);

                for(Customer customer: this.nextBoss.entourage) {
                    this.customers.add(customer);
                }

                this.nextBoss = null;

            } else {
                this.customers.add(spawner.spawnCustomer());
            }

            this.endDay = false;
            this.buildMode = false;
        }
    }

    public boolean checkPlacementLocation(Shape shape) {
        for(EnvironmentItem item : this.items) {
            if(collisionDetector.checkCollision(item.getShape(), shape))
                return false;
        }
        
        for(Wall wall : this.inn.getWalls()) {
            if(collisionDetector.checkCollision(wall.getShape(), shape))
                return false;
        }

        if(collisionDetector.checkCollision(this.inn.getDoor().getShape(), shape))
            return false;

        return true;
    }

    /**
    * Calculates the customers that have to attend the inn that day, based on popularity of faction.
    */
    private void calculateCustomers() {
        if(this.popularity.getKnightPopularityLevel() == 1) {
            this.spawner.setKnightSpawn(3);
        } else {
            this.spawner.setKnightSpawn(floor(this.popularity.getKnightPopularityLevel()/2));
        }

        this.time.setSpawnTimer(960/this.spawner.getCustomersInDay());
    }


    public void spawnBoss(Faction faction) {
        this.nextBoss = spawner.spawnBoss(faction);
    }

    public void movePlayer(float x, float y, Facing direction) {
        PVector change = new PVector(x,y);
        if(buildMode) {
            build.moveBuildSquare(direction);
        } else {
            if(checkMove(this.player.getPos(), change)) {
                this.player.move(change);
                this.player.setFacing(direction);
            }
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

    public void itemKeyPress(int itemKey) {
        if(buildMode) {
            EnvironmentItem item = build.placeItem(itemKey);
            if(item!= null)
                this.items.add(item);
        } else {
            player.useItem(itemKey);
        }
    }

    public void addFeeling(Feeling feeling) {
        this.feelings.add(feeling);
    }

    public void newCustomer() {
        this.customers.add(spawner.spawnCustomer());
    }
}
