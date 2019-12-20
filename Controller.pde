/**
* The controller is the central logic for the game and stores its state. The Controller is responsible
/* for resolving and updating the game state, drawing the game as well as monitoring if the game is over.
*/
public class Controller {
    boolean gameInPlay, endDay, buildMode, chooseWorkerServe;
    PVector workerSpawn;
    String displayMessage;
    int winCondition = 0;
    Player player;
    Time time;
    Inn inn;
    Gold gold;
    ArrayList<EnvironmentItem> items = new ArrayList<EnvironmentItem>();
    ArrayList<Customer> customers = new ArrayList<Customer>();
    ArrayList<Feeling> feelings = new ArrayList<Feeling>();
    ArrayList<Worker> workers = new ArrayList<Worker>();
    Boss nextBoss;
    King king;
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
        this.spawner.setDoorPos(this.inn.getDoorPos());
        this.gameInPlay = true;
        this.player = spawner.spawnPlayer();
    }

    public void addInnGold(int amount) {
        this.gold.addGold(amount);
    }

    public void dayEnd() {
        this.endDay = true;
        this.buildMode = true;
        if(this.popularity.kingReady()) {
            this.spawnKing();
        }
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

            } else if(this.king != null) {
                this.customers.add(this.king);

                for(Customer customer: this.king.entourage) {
                    this.customers.add(customer);
                }

                this.king = null;
            } else {
                this.customers.add(spawner.spawnCustomer());
            }

            this.endDay = false;
            this.buildMode = false;
        }
    }

    public void chooseWorkerServe(float x, float y) {
        this.workerSpawn = new PVector(x, y);
        this.chooseWorkerServe = true;
    }

    public void workerServer(ItemType item) {
        if(this.gold.buyItem(0) && this.build.unlocked) {
            this.workers.add(this.spawner.spawnWorker(item, this.workerSpawn.x, this.workerSpawn.y));
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
        this.spawner.setKnightSpawn(this.popularity.calculateSpawn(Faction.KNIGHT));
        this.spawner.setWizardSpawn(this.popularity.calculateSpawn(Faction.WIZARD));
        this.spawner.setElfSpawn(this.popularity.calculateSpawn(Faction.ELF));
        this.spawner.setZombieSpawn(this.popularity.calculateSpawn(Faction.ZOMBIE));
        println("Customers: "+this.spawner.getCustomersInDay());
        this.time.setSpawnTimer(960/this.spawner.getCustomersInDay());
    }


    public void spawnBoss(Faction faction) {
        this.nextBoss = spawner.spawnBoss(faction);
    }

    public void spawnKing() {
        this.king = spawner.spawnKing();
    }

    public void movePlayer(float x, float y, Facing direction) {
        PVector change = new PVector(x,y);
        if(buildMode) {
            if(!this.chooseWorkerServe) {
                build.moveBuildSquare(direction);
            }
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

    public void endGame(int win) {
        this.winCondition = win;
        this.endDay = false;
        this.gameInPlay = false;
        if(win == -1) {
            this.displayMessage = "You did not satisfy his majesty. Game over.";
        } else {
            this.displayMessage = "Huzzah! You have gained the glory of the crown,\nand are to become the royal innkeep";
        }
        
        this.displayMessage += "\nTotal Gold Made: " + this.gold.accumulated;
    }

    public void drawGame() {
        if(this.winCondition != 0) {
            this.animator.drawEndScreen();
        } else if(this.endDay) {
            this.animator.endDay(this);
        } else if(this.gameInPlay) {
            this.animator.drawActiveGame(this);
            this.cleaner.cleanGame();
            this.collisionDetector.checkCollisions();
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
            if(this.chooseWorkerServe) {
                boolean selected = false;
                System.out.println("Switching");
                switch (itemKey) {
                    case 1 :
                        this.workerServer(ItemType.BEER);
                        selected = true;
                    break;

                    case 2 :
                        this.workerServer(ItemType.CHICKENLEG);
                        selected = true;
                    break;	
                    
                    case 3 :
                        this.workerServer(ItemType.CHALICE);
                        selected = true;
                    break;	

                    case 4:
                        this.workerServer(ItemType.CHEESE);
                        selected = true;
                    break;
                }

                if(selected)
                    this.chooseWorkerServe = false;
            } else {
                EnvironmentItem item = build.placeItem(itemKey);
                if(item!= null)
                this.items.add(item);
            }
            
        } else {
            player.useItem(itemKey);
        }
    }

    public void addFeeling(Feeling feeling) {
        this.feelings.add(feeling);
    }

    public void newCustomer() {
        Customer customer = spawner.spawnCustomer();
        if(customer != null)
            this.customers.add(customer);
    }
}
