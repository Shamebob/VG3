import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.Iterator; 
import java.awt.geom.Rectangle2D; 
import java.awt.Shape; 
import processing.sound.*; 
import java.awt.Shape; 
import java.awt.geom.Area; 
import java.awt.geom.Rectangle2D; 
import java.awt.geom.Ellipse2D; 
import java.awt.Shape; 
import java.awt.geom.*; 
import java.util.Arrays; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class P3 extends PApplet {






// Keep controller as global to control the gamestate.
Controller controller;
PImage OUTSIDE_WALL, INSIDE_WALL, DOOR, WINDOW, INDOOR_FLOOR, GRASS, PATH;
PImage HERO_DOWN_IDLE, HERO_UP_IDLE, HERO_LEFT_IDLE, HERO_RIGHT_IDLE, HERO_PICKUP, HERO_USEITEM;
PImage SERVER_DOWN_IDLE, SERVER_UP_IDLE, SERVER_LEFT_IDLE, SERVER_RIGHT_IDLE, SERVER_PICKUP, SERVER_USEITEM;
PImage HAPPY, SAD, LEAVING;
PImage KNIGHT_IDLE, KNIGHT_CREST, KNIGHT_BOSS_IDLE;
PImage WIZARD_IDLE, WIZARD_CREST, WIZARD_BOSS_IDLE;
PImage ELF_IDLE, ELF_CREST, ELF_BOSS_IDLE;
PImage ZOMBIE_IDLE, ZOMBIE_CREST, ZOMBIE_BOSS_IDLE;
PImage KING_IDLE;
PImage KEG, BEER, CHICKEN, CHICKEN_LEG, CHALICE, CHALICE_TABLE, CHEESE, CHEESE_BARREL;
SoundFile music;

/**
* Setup the game
*/
public void setup() {
  
  noCursor();
  frameRate(30);
  // music = new SoundFile(this, "inn_music.mp3");
  // TODO: Re-enable this.
  // music.loop();

  OUTSIDE_WALL = loadImage("outside_wall.png");
  INSIDE_WALL = loadImage("inside_wall.png");
  WINDOW = loadImage("window.png");
  DOOR = loadImage("door.png");
  INDOOR_FLOOR = loadImage("indoor_floor.png");
  GRASS = loadImage("grass.png");
  PATH = loadImage("path.png");

  BEER = loadImage("beer.png");
  KEG = loadImage("keg.png");
  CHICKEN = loadImage("whole_chicken.png");
  CHICKEN_LEG = loadImage("chicken_leg.png");
  CHALICE = loadImage("chalice.png");
  CHALICE_TABLE = loadImage("chalice_table.png");
  CHEESE = loadImage("cheese.png");
  CHEESE_BARREL = loadImage("cheese_barrel.png");

  HERO_DOWN_IDLE = loadImage("player_idle.png");
  HERO_RIGHT_IDLE = loadImage("player_right1.png");
  HERO_LEFT_IDLE = loadImage("player_left1.png");
  HERO_UP_IDLE = loadImage("player_up1.png");
  HERO_PICKUP = loadImage("player_pickup.png");
  HERO_USEITEM = loadImage("player_useitem.png");

  SERVER_DOWN_IDLE = loadImage("server_idle.png");
  SERVER_RIGHT_IDLE = loadImage("server_right1.png");
  SERVER_LEFT_IDLE = loadImage("server_left1.png");
  SERVER_UP_IDLE = loadImage("server_up1.png");
  SERVER_PICKUP = loadImage("server_pickup.png");
  SERVER_USEITEM = loadImage("server_useitem.png");

  HAPPY = loadImage("happy.png");
  SAD = loadImage("sad.png");
  LEAVING = loadImage("leave.png");

  KNIGHT_IDLE = loadImage("knight_idle.png");
  KNIGHT_BOSS_IDLE = loadImage("knight_boss_idle.png");
  KNIGHT_CREST = loadImage("knight_crest.png");

  WIZARD_IDLE = loadImage("wizard_idle.png");
  WIZARD_BOSS_IDLE = loadImage("wizard_boss_idle.png");
  WIZARD_CREST = loadImage("wizard_crest.png");

  ELF_IDLE = loadImage("elf_idle.png");
  ELF_BOSS_IDLE = loadImage("elf_boss_idle.png");
  ELF_CREST = loadImage("elf_crest.png");

  ZOMBIE_IDLE = loadImage("zombie_idle.png");
  ZOMBIE_BOSS_IDLE = loadImage("zombie_boss_idle.png");
  ZOMBIE_CREST = loadImage("zombie_crest.png");

  KING_IDLE = loadImage("king.png");

  controller = new Controller();
  controller.start();
}

/**
* Draw the game to the screen.
*/
public void draw() {
    controller.drawGame();
}

/**
* Register key pressed for moving and firing.
*/
public void keyPressed() {
    float moveSize = 10;

    switch(key) {
        case 'w':
          controller.movePlayer(0, -moveSize, Facing.UP);
          break;

        case 'a':
          controller.movePlayer(-moveSize, 0, Facing.LEFT);
          break;

        case 's':
          controller.movePlayer(0, moveSize, Facing.DOWN);
          break;

        case 'd':
          controller.movePlayer(moveSize, 0, Facing.RIGHT);
          break;

        case 'e':
          controller.player.pickupItem();
          break;

        case '1':
          controller.itemKeyPress(1);
          break;

        case '2':
          controller.itemKeyPress(2);
          break;

        case '3':
          controller.itemKeyPress(3);
          break;
        
        case '4':
          controller.itemKeyPress(4);
          break;

        case '5':
          controller.itemKeyPress(5);
          break;
        
        case ' ':
          controller.startDay();
    }
}
public class Animator {
    float actionBarStartX, actionBarHeight, infoStartX, infoWidth, customerEmotionsWidth, customerEmotionsStartX;
    ItemType[] newCustomerLikes, newCustomerDislikes;
    PImage[] crests = new PImage[]{KNIGHT_CREST, WIZARD_CREST, ELF_CREST, ZOMBIE_CREST};
    PVector serverImagePos;
    float serverWidth, serverHeight;

    public Animator() {
        this.actionBarStartX = displayWidth/4;
        this.actionBarHeight = displayHeight/10;
        this.infoWidth = displayWidth/15;
        this.infoStartX = displayWidth - this.infoWidth;
        this.customerEmotionsWidth = displayWidth/4 - this.infoWidth;
        this.customerEmotionsStartX = displayWidth - this.infoWidth - this.customerEmotionsWidth;
        this.newCustomerLikes = new ItemType[0];
        this.newCustomerDislikes = new ItemType[0];
    }

    public void newCustomer(ItemType[] likes, ItemType[] dislikes) {
        this.newCustomerLikes = likes;
        this.newCustomerDislikes = dislikes;
    }

    public void drawActiveGame(Controller controller) {
        this.drawTimedBackground(controller.time);
        controller.inn.drawFloor();
        controller.player.draw();

        for(Worker worker: controller.workers) {
            worker.draw();
        }
        
        for(EnvironmentItem item : controller.items) {
            item.draw();
        }

        for(Customer customer: controller.customers) {
            customer.draw();
        }

        for(Feeling feeling: controller.feelings) {
            feeling.draw();
        }

        controller.inn.drawWalls();
        this.drawHUD(controller);
    }


    public void endDay(Controller controller) {
        this.drawTimedBackground(controller.time);
        textSize(16);
        controller.inn.drawFloor();

        fill(255, 255, 255);
        textSize(56);
        text("Day " + controller.time.day + "\nGold: " + controller.gold.getAmount(), (displayWidth/2)-100, (displayHeight/2) - 100);


        for(EnvironmentItem item : controller.items) {
            item.draw();
        }

        for(Worker worker: controller.workers) {
            worker.draw();
        }

        controller.inn.drawWalls();
        this.drawHUD(controller);

        if(controller.buildMode)
            controller.build.draw();
    }

    private void drawTimedBackground(Time time) {
        if(time.hour < 20 && time.hour >= 8) {
            background(255, 255, 255);
        } else {
            background(0, 0, 0);
        }
    }

    private void drawHUD(Controller controller) {
        textSize(16);
        fill(101,67,33);
        rect(0, displayHeight - this.actionBarHeight, displayWidth, this.actionBarHeight);
        this.drawActionBar(controller);
        this.drawInfo(controller);
        this.drawCustomerEmotions(controller);
        this.drawPopularity(controller);
    }

    private void drawPopularity(Controller controller) {
        float popularityBoxWidth = (displayWidth/4)/4;
        float crestWidth = (popularityBoxWidth/2);
        float crestStartY = (displayHeight - this.actionBarHeight) + (this.actionBarHeight/4);
        float crestHeight = (this.actionBarHeight/2);
        float currentPoint = 0;
        int[] popularityLevels = controller.popularity.getPopularityLevels();

        for(int i = 0; i < this.crests.length; i++) {
            fill(139, 93, 46);
            rect(currentPoint, displayHeight - this.actionBarHeight, popularityBoxWidth, this.actionBarHeight);
            image(this.crests[i], currentPoint + (popularityBoxWidth/4), crestStartY, crestWidth, crestHeight);

            fill(0, 0, 0);
            text(popularityLevels[i], currentPoint + (popularityBoxWidth/2) - 5, displayHeight - 3);
            currentPoint += popularityBoxWidth;
        }
    }

    private void drawActionBar(Controller controller) {
        float actionBoxWidth = (displayWidth/2)/5;
        float currentPoint = this.actionBarStartX;
        for (int i = 0; i < 5; i++) {
            fill(139, 93, 46);
            rect(currentPoint, displayHeight - this.actionBarHeight, actionBoxWidth, this.actionBarHeight);
            fill(0, 0, 0);
            text(i + 1, currentPoint + 5, displayHeight - this.actionBarHeight + 20);
            currentPoint += actionBoxWidth;
        }

        if(controller.buildMode) {
            drawBuildItems(actionBoxWidth);
        } else {
            drawInventoryItems(actionBoxWidth);
        }
    }

    private void drawBuildItems(float actionBoxWidth) {
        int counter = 0;
        int cost = 0;
        for(EnvironmentItem item : controller.build.purchaseItems) {
            cost = findItemCost(counter);
            text(cost + "g", item.getX(), item.getY() - 20);
            item.draw();
            counter+= 1;
        }

        cost = findItemCost(counter);
        text(cost + "g", serverImagePos.x, serverImagePos.y - 20);
        image(SERVER_DOWN_IDLE, this.serverImagePos.x, this.serverImagePos.y, 30, 40);
    }

    public void setupBuildItems() {
        float actionBoxWidth = (displayWidth/2)/5;
        PVector currentPos = new PVector(this.actionBarStartX + (actionBoxWidth/2), displayHeight - (actionBarHeight/2));
        PVector factorChange = new PVector(actionBoxWidth, 0);
        
        for(EnvironmentItem item : controller.build.purchaseItems) {
            item.setPos(currentPos.copy());
            currentPos = currentPos.add(factorChange);
        }

        this.serverImagePos = currentPos.copy();
        this.serverWidth = actionBoxWidth/2;
        this.serverHeight = this.actionBarHeight/2;
    }

    private void drawInventoryItems(float actionBoxWidth) {
        PVector currentPos = new PVector(this.actionBarStartX + (actionBoxWidth/2), displayHeight - (actionBarHeight/2));
        PVector factorChange = new PVector(actionBoxWidth, 0);
        for(EnvironmentItem item : controller.player.inventory) {
            item.setPos(currentPos.copy());
            item.draw();
            currentPos = currentPos.add(factorChange);
        }
    }
    
    private void drawInfo(Controller controller) {
        controller.time.draw();
        fill(139, 93, 46);
        rect(this.infoStartX, displayHeight - this.actionBarHeight, this.infoWidth, this.actionBarHeight);

        textSize(16);
        fill(0,0,0);
        if (controller.time.minute < 10) {
            text("Day " + controller.time.day + "\n" + controller.time.hour + ":0" + controller.time.minute + "\nGold: " + controller.gold.getAmount(), this.infoStartX + 5, displayHeight - this.actionBarHeight + 20);
        } else {
            text("Day " + controller.time.day + "\n" + controller.time.hour + ":" + controller.time.minute + "\nGold: " + controller.gold.getAmount(), this.infoStartX + 5, displayHeight - this.actionBarHeight + 20);
        }
    }

    private void drawCustomerEmotions(Controller controller) {
        fill(255, 255, 255);
        rect(this.customerEmotionsStartX, displayHeight - this.actionBarHeight, this.customerEmotionsWidth, this.actionBarHeight);
        image(HAPPY, this.customerEmotionsStartX + (this.customerEmotionsWidth/4) - 10, displayHeight - (actionBarHeight - (this.actionBarHeight/10)), 20, 20);
        image(SAD, this.customerEmotionsStartX + (3 * (this.customerEmotionsWidth/4)) - 10, displayHeight - (actionBarHeight - (this.actionBarHeight/10)), 20, 20);

        float imageSpace = this.customerEmotionsWidth/4;
        float imageY = displayHeight - (this.actionBarHeight/2);
        float imageHeight = this.actionBarHeight/3;
        float currentPoint = this.customerEmotionsStartX + (imageSpace/4);
        float imageWidth = imageSpace/2;

        for(ItemType item : this.newCustomerLikes) {
            drawItemType(item, currentPoint, imageY, imageWidth, imageHeight);
            currentPoint += imageSpace;
        }

        currentPoint = this.customerEmotionsStartX + (this.customerEmotionsWidth/2) + (imageSpace/4);

        for(ItemType item : this.newCustomerDislikes) {
            drawItemType(item, currentPoint, imageY, imageWidth, imageHeight);
            currentPoint += imageSpace;
        }

    }

    public void drawItemType(ItemType item, float x, float y, float width, float height) {
        PImage itemImage = null;

        switch (item) {
            case BEER:
                itemImage = BEER;
                break;
            
            case CHICKENLEG:
                itemImage = CHICKEN_LEG;
                break;

            case CHALICE:
                itemImage = CHALICE;
                break;

            case CHEESE:
                itemImage = CHEESE;
                break;
        }
    

        if(itemImage != null)
            image(itemImage, x, y, width, height);
    }

    public int findItemCost(int index) {
        switch(index) {
            case 0:
                return 50;
            
            case 1:
                return 25;
            
            case 2:
                return 40;
            
            case 3:
                return 70;

            case 4:
                return 500;
        }

        return 0;
    }

    public void drawEndScreen() {
        background(139, 93, 46);
        controller.inn.drawFloor();
        controller.inn.drawWalls();
        controller.player.draw();
        fill(255, 255, 255);
        textSize(32);
        text(controller.displayMessage, (displayWidth/4), 50);
    }
}
public class Beer extends EnvironmentItem {
    public Beer(float x, float y) {
        super(x, y, ((Shape) new Rectangle2D.Float(x, y, 25, 25)), 1);
    }

    public void draw() {
        image(BEER, this.getX(), this.getY(), 25, 25);

    }
}
public class Boss extends Customer {
    ArrayList<Customer> entourage = new ArrayList<Customer>();
    Faction faction;
    PImage characterImage;
    float height, width;

    public Boss(float x, float y, int popularity, int goldAmount, Faction faction, ArrayList<Customer> entourage) {
        super(x, y, ((Shape) new Rectangle2D.Float(x, y, 40, 50)), popularity, goldAmount);
        this.width = 40;
        this.height = 50;
        this.faction = faction;
        this.satisfaction = -50;

        switch (faction) {
            case KNIGHT:
                this.characterImage = KNIGHT_BOSS_IDLE;
                break;
            
            case ELF:
                this.width = 30;
                this.height = 40;
                this.characterImage = ELF_BOSS_IDLE;
                break;

            case WIZARD:
                this.width = 30;
                this.height = 40;
                this.characterImage = WIZARD_BOSS_IDLE;
                break;
            
            case ZOMBIE:
                this.characterImage = ZOMBIE_BOSS_IDLE;
                break;
        }

        this.entourage = entourage;
    }

    public void draw() {
        super.draw();
        this.setShape(new Rectangle2D.Float(this.getX(), this.getY(), this.width, this.height));
        image(this.characterImage, this.getX(), this.getY(), this.width, this.height);
    }

    @Override
    protected float evaluatePerformance() {
        float entourageSatisfaction = this.satisfaction;
        for(Customer customer : entourage) {
            entourageSatisfaction += customer.getSatisfaction();
        }

        entourageSatisfaction = entourageSatisfaction/(this.entourage.size() + 1);
        return entourageSatisfaction;
    }

    @Override
    protected void leave() {
        float averageSatisfaction = this.evaluatePerformance();
        for(Customer customer : entourage) {
            customer.entourageLeave(this.faction, averageSatisfaction);
        }
        
        if(averageSatisfaction <= 50) {
            controller.endGame(-1);
        }

        controller.popularity.addPopularity(this.faction, averageSatisfaction/this.popularity);
        controller.popularity.bossSatisfied(true, this.faction);
        super.leave();
    }


}
public class Build {
    EnvironmentItem[] purchaseItems = new EnvironmentItem[]{new Keg(0, 0), new Chicken(0, 0), new ChaliceTable(0, 0), new CheeseBarrel(0, 0)};
    PVector buildSquarePos;
    Shape shape;
    float buildSquareWidth, buildSquareHeight;

    public Build() {
        this.buildSquarePos = new PVector(displayWidth/2, displayHeight/2);
        this.shape = new Rectangle2D.Float(buildSquarePos.x, buildSquarePos.y, buildSquareWidth, buildSquareHeight);
        this.buildSquareWidth = 30;
        this.buildSquareHeight = 30;
    }

    public void moveBuildSquare(Facing direction) {
        switch (direction) {
            case UP:
                buildSquarePos.y -= buildSquareHeight;
                break;

            case LEFT:
                buildSquarePos.x -= buildSquareWidth;
                break;

            case DOWN:
                buildSquarePos.y += buildSquareHeight;
                break;

            case RIGHT:
                buildSquarePos.x += buildSquareWidth;
                break;
        }

        this.shape = new Rectangle2D.Float(buildSquarePos.x, buildSquarePos.y, buildSquareWidth, buildSquareHeight);
    }

    public void draw() {
        fill(255, 0, 0, 100);
        rect(buildSquarePos.x, buildSquarePos.y, buildSquareWidth, buildSquareHeight);
    }

    public EnvironmentItem placeItem(int itemIndex) {
        float x = buildSquarePos.x;
        float y = buildSquarePos.y;
        EnvironmentItem item = null;
        int cost = 0;

        if(!controller.checkPlacementLocation(this.shape))
            return item;

        switch(itemIndex) {
            case 1:
                item = new Keg(x, y);
                cost = 50;
                break;
            
            case 2:
                item = new Chicken(x,y);
                cost = 25;
                break;
            
            case 3:
                item = new ChaliceTable(x,y);
                cost = 40;
                break;
            
            case 4:
                item = new CheeseBarrel(x,y);
                cost = 70;
                break;
            case 5:
                if(controller.gold.buyItem(500)) {
                    controller.workers.add(controller.spawner.spawnWorker(ItemType.BEER, x, y));
                }

        }

        if(controller.gold.buyItem(cost)) {
            return item;
        } else {
            return null;
        }
    }

}
public class Chalice extends EnvironmentItem {
    public Chalice(float x, float y) {
        super(x, y, ((Shape) new Rectangle2D.Float(x, y, 25, 25)), 1);
    }

    public void draw() {
        image(CHALICE, this.getX(), this.getY(), 25, 25);

    }
}
public class ChaliceTable extends EnvironmentItem {

    public ChaliceTable(float x, float y) {
        super(x, y, ((Shape) new Ellipse2D.Float(x, y, 15, 15)), 30);
    }

    public void draw() {
        image(CHALICE_TABLE, this.getX(), this.getY(), 30, 30);
    }
}
public abstract class Character extends GameObject {
    PVector direction;

    public Character (float x, float y, Shape shape) {
        super(x, y, shape);
    }

    public void move(PVector change) {
        if(controller.checkMove(this.getPos(), change)) {
            this.pos.add(change);
        } else {
            this.pos.add(change.mult(-1));
        }
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    protected PVector findDirection() {
        float randomX = random(3, 5);
        float randomY = random(3, 5);

        if(round(random(0,1)) == 0) {
            randomX *= -1;
        }

        if(round(random(0,1)) == 0) {
            randomY *= -1;
        }

        return new PVector(randomX, randomY);
    }

    public PVector getDirection() {
        return this.direction.copy();
    }

    public PVector getNextMove() {
        return(this.getPos().add(this.getDirection()));
    }

}
public class Cheese extends EnvironmentItem {
    public Cheese(float x, float y) {
        super(x, y, ((Shape) new Rectangle2D.Float(x, y, 25, 25)), 1);
    }

    public void draw() {
        image(CHEESE, this.getX(), this.getY(), 25, 25);

    }
}
public class CheeseBarrel extends EnvironmentItem {

    public CheeseBarrel(float x, float y) {
        super(x, y, ((Shape) new Ellipse2D.Float(x, y, 15, 15)), 30);
    }

    public void draw() {
        image(CHEESE_BARREL, this.getX(), this.getY(), 30, 30);
    }
}
public class Chicken extends EnvironmentItem {

    public Chicken(float x, float y) {
        super(x, y, ((Shape) new Ellipse2D.Float(x, y, 15, 15)), 15);
    }

    public void draw() {
        image(CHICKEN, this.getX(), this.getY(), 30, 30);
    }
}
public class ChickenLeg extends EnvironmentItem {
    public ChickenLeg(float x, float y) {
        super(x, y, ((Shape) new Rectangle2D.Float(x, y, 25, 25)), 1);
    }

    public void draw() {
        image(CHICKEN_LEG, this.getX(), this.getY(), 25, 25);

    }
}
public class Cleaner {
    Cleaner() {
    }

    public void cleanGame() {
        cleanEnvironmentItems(controller.items);
        cleanFeelings(controller.feelings);
        cleanCustomers(controller.customers);
    }

    private void cleanEnvironmentItems(ArrayList<EnvironmentItem> list) {
        Iterator iter = list.iterator();
        EnvironmentItem curObj;
        while(iter.hasNext()) {
            curObj = (EnvironmentItem) iter.next();
            if(!curObj.isActive()) {
                iter.remove();
            }
        }
    }

    private void cleanFeelings(ArrayList<Feeling> list) {
        Iterator iter = list.iterator();
        Feeling curObj;
        while(iter.hasNext()) {
            curObj = (Feeling) iter.next();
            if(!curObj.isActive()) {
                iter.remove();
            }
        }
    }

    private void cleanCustomers(ArrayList<Customer> list) {
        Iterator iter = list.iterator();
        Customer curObj;
        while(iter.hasNext()) {
            curObj = (Customer) iter.next();
            if(!curObj.isActive()) {
                iter.remove();
            }
        }
    }


    
}



/**
* The CollisionDetector class is used to detect collisions between shapes and invoke methods as appropriate for the collision.
*/
public class CollisionDetector {
    /**
    * Constructor for a collision detector.
    */
    CollisionDetector() {
    }

    /**
    * Check the collisions between all of the game items that have to be checked.
    */
    public void checkCollisions() {
    }

    /**
    * Check whether two characters in the game have collided.
    * @param charA, the first shape to intersect.
    * @param charB, the second shape to intersect.
    */
    public boolean checkCollision(Shape shapeA, Shape shapeB) {
        return shapeA.getBounds2D().intersects(shapeB.getBounds2D());
    }

}

/**
* The controller is the central logic for the game and stores its state. The Controller is responsible
/* for resolving and updating the game state, drawing the game as well as monitoring if the game is over.
*/
public class Controller {
    boolean gameInPlay, endDay, buildMode;
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
        Customer customer = spawner.spawnCustomer();
        if(customer != null)
            this.customers.add(customer);
    }
}
enum ItemType {
    BEER, CHICKENLEG, CHALICE, CHEESE;
}

enum Faction {
    KNIGHT, WIZARD, ELF, ZOMBIE;
}

public abstract class Customer extends Character {
    int popularity, waitTime, diminishingReturn, waitCounter;
    float satisfaction;
    Gold money;
    int moveCounter;
    boolean entering, leaving;
    ItemType[] likes, dislikes;

    public Customer(float x, float y, Shape shape, int popularity, int goldAmount) {
        super(x, y, shape);
        this.popularity = popularity;
        this.satisfaction = 0;
        this.money = new Gold();
        this.money.addGold(goldAmount);
        this.direction = this.findDirection();
        this.moveCounter = 0;
        this.leaving = false;
        this.likes = new ItemType[0];
        this.dislikes = new ItemType[0];
        this.diminishingReturn = 0;
        this.waitTime = 240;
        this.waitCounter = 0;
        this.enter();
    }

    public void setLikes(ItemType[] likes) {
        this.likes = likes;
    }

    public void setDislikes(ItemType[] dislikes) {
        this.dislikes = dislikes;
    }

    public void draw() {
        if(this.waitTime == 0)
            this.resetWait();

        if (this.diminishingReturn > 0)
            this.diminishingReturn -= 1;

        if(this.entering)
            this.checkEntered();

        if(this.moveCounter % 120 == 0 && !this.leaving && !this.entering) 
            this.direction = super.findDirection();
    
        if(this.moveCounter % 5 == 0) {
            this.move(this.direction);
        }

        if(this.leaving && this.getY() >= displayHeight) {
            this.destroy();
        }

        if(!(this.leaving || this.entering))
            this.waitTime -= 1;
        
        this.moveCounter += 1;
    }

    private void resetWait() {
        if(this.waitTime == 0) {
            this.satisfaction -= 10;
            this.waitCounter += 1;
            controller.addFeeling(new Feeling(this.getX(), this.getY() - 5, Emotion.SAD));

            if(this.waitCounter == 3)
                this.leave();
        }

        this.waitTime = 240;
    }

    @Override
    public void move(PVector change) {
        if(this.leaving || this.entering) {
            this.pos.add(change);
        } else {
            super.move(change);
        }
    }
   
    public void useItem(EnvironmentItem item) {
        boolean likedItem = false;
        ItemType itemType = null;
        if(this.leaving)
            return;

        
        
        if(item instanceof Beer) {
            itemType = ItemType.BEER;
        } else if(item instanceof ChickenLeg) {
            itemType = ItemType.CHICKENLEG;
        } else if(item instanceof Chalice) {
            itemType = ItemType.CHALICE;
        } else if(item instanceof Cheese) {
            itemType = ItemType.CHEESE;
        }

        this.money.buy(item);
        likedItem = this.reaction(itemType);
        this.addSatisfaction(likedItem);
        this.diminishingReturn = 120;
        this.resetWait();

        if(this.money.getAmount() < 10) {
            this.leave();
        } 
    }

    private void addSatisfaction(boolean likedItem) {
        float satisfaction = 0;
        if(likedItem) {
            satisfaction = 25;
        } else {
            satisfaction = -25;
        }

        if(this.diminishingReturn > 0)
            satisfaction = satisfaction/this.diminishingReturn;

        this.satisfaction += satisfaction;
    }

    private boolean reaction(ItemType item) {
        for(ItemType like : this.likes) {
            if(like == item) {
                controller.addFeeling(new Feeling(this.getX(), this.getY() - 5, Emotion.HAPPY));
                return true;
            }
        }

        for(ItemType dislike : this.dislikes) {
            if(dislike == item) {
                controller.addFeeling(new Feeling(this.getX(), this.getY() - 5, Emotion.SAD));
                return false;
            }
        }

        return false;
    }

    protected void enter() {
        this.direction = controller.inn.getDoorPos().sub(this.getPos()).normalize();
        this.direction.mult(3);
        this.entering = true;
    }

    protected void checkEntered() {
        if(this.getY() <= (controller.inn.getDoorPos().y - 20))
            this.entering = false;
    }

    protected void leave() {
        this.leaving = true;
        controller.addFeeling(new Feeling(this.getX() + 5, this.getY() - 5, Emotion.LEAVING));
        this.direction = controller.inn.getDoorPos().sub(this.getPos()).normalize().mult(4);
    }

    protected float evaluatePerformance() {
        return this.satisfaction/this.popularity;
    }

    public float getSatisfaction() {
        return this.satisfaction;
    }

    public int getPopularity() {
        return this.popularity;
    }

    public int getDiminishingReturns() {
        return this.diminishingReturn;
    }

    public void entourageLeave(Faction faction, float satisfaction) {
        this.satisfaction = satisfaction;
        controller.popularity.addPopularity(faction, this.evaluatePerformance());
        this.leave();
    }
}
public class Elf extends Customer {
    public Elf(float x, float y, int popularity, int goldAmount) {
        super(x, y, ((Shape) new Rectangle2D.Float(x, y, 30, 50)), popularity, goldAmount);
    }

    public void draw() {
        super.draw();
        this.setShape(new Rectangle2D.Float(this.getX(), this.getY(), 30, 50));
        image(ELF_IDLE, this.getX(), this.getY(), 30, 50);
    }

    @Override
    protected void leave() {
        controller.popularity.addPopularity(Faction.ELF, this.evaluatePerformance());
        super.leave();
    }
}
public abstract class EnvironmentItem extends GameObject {
    int uses;
    public EnvironmentItem(float x, float y, Shape shape, int uses) {
        super(x, y, shape);
        this.uses = uses;
    }

    public void use() {
        this.uses -=1;
        if(this.uses == 0) {
            this.destroy();
        }
    }
}
enum Emotion {
    HAPPY, SAD, LEAVING;
}

public class Feeling extends Character {
    int drawCounter;
    PImage drawing;

    public Feeling(float x, float y, Emotion currentFeeling) {
        super(x, y, null);
        this.drawCounter = 0;

        if(currentFeeling == Emotion.HAPPY) {
            this.drawing = HAPPY;
        } else if (currentFeeling == Emotion.SAD) {
            this.drawing = SAD;
        } else if(currentFeeling == Emotion.LEAVING) {
            this.drawing = LEAVING;
        }
    }

    public void draw() {
        this.drawCounter += 1;

        if(this.drawCounter % 10 == 0) {
            super.move(new PVector(0, -10));
        }

        image(this.drawing, this.getX(), this.getY(), 15, 15);

        if(this.drawCounter == 30) {
            this.destroy();
        }
    }
}
enum FloorType {
    GRASS, INDOOR, PATH;
}

public class Floor extends GameObject{
    float width, height;
    PImage imageType;
    FloorType floorType;

    public Floor(float x, float y, float width, float height, FloorType floorType) {
        super(x, y, ((Shape) new Rectangle2D.Float(x, y, width, height)));
        this.width = width;
        this.height = height;
        this.floorType = floorType;
        findImageType();
    }

    public void draw() {
        image(this.imageType, this.getX(), this.getY(), this.width, this.height);
    }

    private void findImageType() {
        if(this.floorType == FloorType.GRASS) {
            this.imageType = GRASS;
        } else if(this.floorType == FloorType.INDOOR) {
            this.imageType = INDOOR_FLOOR;
        } else if(this.floorType == FloorType.PATH) {
            this.imageType = PATH;
        } 
    }
}




public abstract class GameObject {
    protected PVector pos;
    protected Shape shape;
    protected boolean active;

    public GameObject (float x, float y, Shape shape) {
        this.pos = new PVector(x,y);
        this.shape = shape;
        this.active = true;
    }

    public Shape getShape() {
        return this.shape;
    }

    /**
    * Gets the bounds of the shape to use when checking for intersections.
    * @returns the 2D bounds of the shape, which provide a tight rectangle around the object.
    */
    public Rectangle2D getShapeBounds() {
        return this.shape.getBounds2D();
    }

    public PVector getPos() {
        // Make a copy so that no changes can be made to the character's values externally.
        return this.pos.copy();
    }

    public void setPos(PVector pos) {
        this.pos = pos;
    }

    public float getX() {
        return this.pos.x;
    }

    public float getY() {
        return this.pos.y;
    }

    public boolean isActive() {
        return this.active;
    }

    public void destroy() {
        this.active = false;
    }

    public abstract void draw();
}
public class Gold {
    private int amount;
    private int accumulated;

    public Gold() {
        this.amount = 0;
        this.accumulated = 0;
    }

    public void addGold(int quantity) {
        this.amount += quantity;
        this.accumulated += quantity;
    }

    public int getAmount() {
        return this.amount;
    }

    public int getAccumulated() {
        return this.accumulated;
    }

    public boolean buyItem(int amount) {
        if(this.amount < amount)
            return false;

        this.amount -= amount;
        return true;
    }

    public void buy(EnvironmentItem item) {
        int val = 0;
        if(item instanceof Beer) {
            val = 10;
        } else if(item instanceof ChickenLeg) {
            val = 5;
        }

        this.amount -= val;
        controller.addInnGold(val);
    }

}
public class Inn {
    private float startX, startY, endX, endY;
    private ArrayList<Wall> walls = new ArrayList<Wall>();
    private ArrayList<Floor> floor = new ArrayList<Floor>();
    Wall door;
    PVector doorPos;

    public Inn() {
        this.startX = displayWidth/4;
        this.endX = this.startX * 3;
        this.startY = displayHeight/4 * 3;
        this.endY = displayHeight/4;
        this.buildWalls();
        this.constructFloor();
    }

    public void buildWalls() {
        float wallWidth = displayWidth/100 * 3;
        int wallCount = PApplet.parseInt((this.endX - this.startX)/wallWidth);
        float wallHeight = displayWidth/100 * 4;
        float curX = startX;
        float curY = endY;

        for (int i = 0; i < wallCount; i++) {
            if(i == wallCount/2) {
                this.doorPos = new PVector(curX, startY);
                this.door = new Wall(curX, startY, wallWidth, wallHeight, WallType.DOOR);
            } else {
                this.walls.add(new Wall(curX, startY, wallWidth, wallHeight, WallType.BOTTOM));
            }
            
            if(i % 4 == 1) {
                this.walls.add(new Wall(curX, endY, wallWidth, wallHeight, WallType.WINDOW));
            } else {
                this.walls.add(new Wall(curX, endY, wallWidth, wallHeight, WallType.TOP));
            }
            curX += wallWidth;
        }

        wallCount = PApplet.parseInt((this.startY - this.endY)/wallHeight) + 1;
        for(int i = 0; i < wallCount; i++) {
            this.walls.add(new Wall(this.startX, curY, wallWidth/3, wallHeight, WallType.SIDE));
            this.walls.add(new Wall(curX, curY, wallWidth/3, wallHeight, WallType.SIDE));
            curY += wallHeight;
        }
        System.out.println("Number of walls: " + this.walls.size());
    }

     public void constructFloor() {
        float floorWidth = displayWidth/100 * 6;
        int widthCount = ceil(PApplet.parseInt(displayWidth/floorWidth)) + 1;
        float floorHeight = displayWidth/100 * 6;
        int heightCount = ceil(PApplet.parseInt(displayHeight/floorHeight));
        float curX = 0;
        float curY = 0;

        // Make the grass Tiles
        for(int i = 0; i < heightCount; i++) {
            curX = 0;
            for(int j = 0; j < widthCount; j++) {
                if (!((curX > this.startX && curX < this.endX - floorWidth) && (curY <= this.startY - (floorHeight/2) && (curY >= this.endY)))) {
                    this.floor.add(new Floor(curX, curY, floorWidth, floorHeight, FloorType.GRASS));
                }
                curX += floorWidth;
            }
            curY += floorHeight;

        }

        // Make the inn tiles
        float innWidth = (this.endX - this.startX) - 25;
        float innHeight = (this.startY - this.endY);
        floorWidth = innWidth/10;
        floorHeight = innWidth/10;
        widthCount = ceil(PApplet.parseInt(innWidth/floorWidth));
        heightCount = ceil(PApplet.parseInt(innHeight/floorHeight)) + 1;
        curY = this.endY;
        for(int i = 0; i < heightCount; i++) {
            curX = this.startX;
            for(int j = 0; j < widthCount; j++) {
                this.floor.add(new Floor(curX, curY, floorWidth, floorHeight, FloorType.INDOOR));
                curX += floorWidth;
            }
            curY += floorHeight;
        }

        curX = this.doorPos.x;
        curY = this.doorPos.y;
        floorWidth = displayWidth/100 * 3;
        float doorToHUDHeight = displayHeight - this.doorPos.y;
        heightCount = ceil(PApplet.parseInt(doorToHUDHeight/floorHeight)) + 1;
        for(int i = 0; i < heightCount; i++) {
            this.floor.add(new Floor(curX, curY, floorWidth, floorHeight, FloorType.PATH));
            curY += floorHeight;
        }

        
    }

    public void drawFloor() {
        for(Floor floor : this.floor) {
            floor.draw();
        }

        this.door.draw();
    }
    public void drawWalls() {
        for(Wall wall : this.walls) {
            wall.draw();
        }
    }

    public Wall getDoor() {
        return this.door;
    }
    public float getStartX() {
        return this.startX;
    }

    public float getStartY() {
        return this.startY;
    }

    public float getEndX() {
        return this.endX;
    }

    public float getEndY() {
        return this.endY;
    }

    public PVector getDoorPos() {
        return this.doorPos.copy();
    }

    public ArrayList<Wall> getWalls() {
        return this.walls;
    }

    public boolean wallCollision(PVector position) {
      for(Wall wall : this.walls) {
        if(wall.getShape().contains(position.x, position.y) && wall.wallType != WallType.DOOR) {
          System.out.println("True");
          return true;
        }
      }
      return false;
    }
}
public class Keg extends EnvironmentItem {

    public Keg(float x, float y) {
        super(x, y, ((Shape) new Ellipse2D.Float(x, y, 15, 15)), 30);
    }

    public void draw() {
        image(KEG, this.getX(), this.getY(), 30, 30);
    }
}
public class King extends Customer {
    ArrayList<Customer> entourage = new ArrayList<Customer>();
    PImage characterImage;
    float height, width;

    public King(float x, float y, int popularity, int goldAmount, ArrayList<Customer> entourage) {
        super(x, y, ((Shape) new Rectangle2D.Float(x, y, 40, 50)), popularity, goldAmount);
        this.width = 40;
        this.height = 50;
        this.satisfaction = -50;
        this.entourage = entourage;
        this.characterImage = KING_IDLE;
    }

    public void draw() {
        super.draw();
        this.setShape(new Rectangle2D.Float(this.getX(), this.getY(), this.width, this.height));
        image(this.characterImage, this.getX(), this.getY(), this.width, this.height);
    }

    @Override
    protected float evaluatePerformance() {
        float entourageSatisfaction = this.satisfaction;
        for(Customer customer : entourage) {
            entourageSatisfaction += customer.getSatisfaction();
        }

        entourageSatisfaction = entourageSatisfaction/(this.entourage.size() + 1);
        if(entourageSatisfaction >= 75) {
            controller.endGame(1);
        } else {
            controller.endGame(-1);
        }

        return entourageSatisfaction;
    }

    @Override
    protected void leave() {
        this.evaluatePerformance();
        // for(Customer customer : entourage) {
        //     customer.entourageLeave(this.faction, averageSatisfaction);
        // }

        // controller.popularity.addPopularity(this.faction, averageSatisfaction/this.popularity);
        super.leave();
    }


}
public class Knight extends Customer {
    public Knight(float x, float y, int popularity, int goldAmount) {
        super(x, y, ((Shape) new Rectangle2D.Float(x, y, 30, 40)), popularity, goldAmount);
    }

    public void draw() {
        super.draw();
        this.setShape(new Rectangle2D.Float(this.getX(), this.getY(), 30, 40));
        image(KNIGHT_IDLE, this.getX(), this.getY(), 30, 40);
    }

    @Override
    protected void leave() {
        controller.popularity.addPopularity(Faction.KNIGHT, this.evaluatePerformance());
        super.leave();
    }
}
public abstract class NPC extends Character{

    public NPC (float x, float y, Shape shape) {
        super(x, y, shape);
    }

}
public class Player extends Staff {
    public Player (float x, float y) {
        super(x, y);
        this.staffImage = HERO_DOWN_IDLE;
    }

    public void setFacing(Facing direction) {
        if(direction == this.currentFacing)
            return;
        
        this.currentFacing = direction;

        switch (direction) {
            case UP:
                this.staffImage = HERO_UP_IDLE;
                break;
            
            case DOWN:
                this.staffImage = HERO_DOWN_IDLE;
                break;
            
            case LEFT:
                this.staffImage = HERO_LEFT_IDLE;
                break;
            
            case RIGHT:
                this.staffImage = HERO_RIGHT_IDLE;
                break;
        }
    }

    @Override
    public void pickupItem() {
        this.staffImage = HERO_PICKUP;
        super.pickupItem();
    }

    @Override
    public void useItem(int index) {
        this.staffImage = HERO_USEITEM;
        super.useItem(index);
    }
}
final int FACTIONS = 4;
public class Popularity {
    float[] popularity, lowerThresholds, upperThresholds;
    int[] popularityLevels;
    boolean[] satisfiedBoss;


    public Popularity() {
        this.popularity = new float[FACTIONS];
        this.popularityLevels = new int[]{1,1,1,1};
        this.lowerThresholds = new float[FACTIONS];
        this.upperThresholds = new float[FACTIONS];
        this.satisfiedBoss = new boolean[FACTIONS];
    }

    public void addPopularity(Faction faction, float popularity) {
        int index = this.findIndex(faction);
        this.popularity[index] += popularity;
        System.out.println("Popularity Gain: " + popularity);
        
        if(this.popularity[index] >= (this.popularityLevels[index] * 10)) {
            this.lowerThresholds[index] = this.popularityLevels[index] * 10;
            this.popularityLevels[index] += 1;
            this.popularity[index] = 0;

            if(this.popularityLevels[index] == 3 && !this.satisfiedBoss[index])
                controller.spawnBoss(faction);
        }
        
        if(this.popularity[index] <= this.lowerThresholds[index] && this.popularityLevels[index] > 1) {
            this.popularity[index] -= 1;
            this.lowerThresholds[index] = this.popularity[index] * 10;
        } 
    }

    public void bossSatisfied(boolean satisfied, Faction faction) {
        this.satisfiedBoss[this.findIndex(faction)] = true;
    }

    private int findIndex(Faction faction) {
        switch (faction) {
                case KNIGHT:
                    return 0;
                case WIZARD:
                    return 1;
                case ELF:
                    return 2;
                case ZOMBIE:
                    return 3;
        }

        return 0;
    }

    public int[] getPopularityLevels() {
        return this.popularityLevels;
    }

    public int getKnightPopularityLevel() {
        return this.popularityLevels[0];
    }

    public int getPopularityLevel(Faction faction) {
        return this.popularityLevels[this.findIndex(faction)];
    }

    public boolean kingReady() {
        for(boolean boss : this.satisfiedBoss) {
                if(!boss)
                    return boss;
        }

        return true;
    }

    public int calculateSpawn(Faction faction) {
        // int index = this.findIndex(faction);
        // if()
        // if(this.popularity.getKnightPopularityLevel() == 1) {
        //     this.spawner.setKnightSpawn(3);
        // } else {
        //     this.spawner.setKnightSpawn(floor(this.popularity.getKnightPopularityLevel()/2));
        // }

        return 1;
    }
}


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

    public Customer spawnCustomer() {
        int goldAmount = round(random(30, 80));
        int popularity = round(random(30, 80));
        float x = this.doorPos.x + 10;
        float y = displayHeight - (displayHeight/10);
        boolean didSpawn = false;
        Customer customer = null;
        if(this.customersInDay > 0) {
            while(customer == null) {
                int val = floor(random(0, 3.5f));
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

    public Worker spawnWorker(ItemType item, float x, float y) {
        //TODO: Have them walk in the door
        return new Worker(x, y, item);
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

        generateLikesAndDislikes(customer);
        return customer;
    }

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

    public Boss spawnBoss(Faction faction) {
        ArrayList<Customer> entourage = new ArrayList<Customer>();

        for(PVector location : this.locations) {
            entourage.add(spawnEntourage(faction, location.x, location.y));
        }

        Boss boss = new Boss(this.doorPos.x + 10, displayHeight - (displayHeight/10), 500, 500, faction, entourage);
        generateLikesAndDislikes(boss);
        return boss;
    }

    private void generateLikesAndDislikes(Customer customer) {
        //TODO: Give likes and dislikes based on accumulated gold and not popularity.
        // ArrayList<ItemType> items = new ArrayList<ItemType>(Arrays.asList(ItemType.values()));
        ArrayList<ItemType> items;
        if(controller.gold.accumulated > 750) {
            items = new ArrayList<ItemType>(Arrays.asList(ItemType.values()));
        } else {
            items = new ArrayList<ItemType>();
            items.add(ItemType.BEER);
            items.add(ItemType.CHICKENLEG);
        }
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
final float HEIGHT = 30;
final float WIDTH = 30;

enum Facing{
    UP, LEFT, DOWN, RIGHT;
}

public abstract class Staff extends Character {
    Facing currentFacing;
    PImage staffImage;
    ArrayList<EnvironmentItem> inventory = new ArrayList<EnvironmentItem>();

    public Staff (float x, float y) {
        super(x, y, ((Shape) new Rectangle2D.Float(x, y, WIDTH, HEIGHT)));
        this.currentFacing = Facing.DOWN;
    }

    public void draw() {
        image(this.staffImage, this.getX(), this.getY(), HEIGHT, WIDTH);
        this.setShape(new Rectangle2D.Float(this.getX(), this.getY(), WIDTH, HEIGHT));
    }

    protected Shape findZone() {
        float x = this.getX();
        float y = this.getY();
        Shape shapeArea;

        switch (currentFacing) {
            case UP:
                y -= HEIGHT;
                break;

            case LEFT:
                x -= WIDTH;
                break;

            case DOWN:
                y += HEIGHT;
                break;

            case RIGHT:
                x += WIDTH;
                break;
        }

        return new Rectangle2D.Float(x, y, WIDTH, HEIGHT);
    }

    public void pickupItem() {
        EnvironmentItem item = controller.findItem(this.findZone());
        if(item == null || this.inventory.size() >= 5)
            return;
        
        if(item instanceof Keg) {
            inventory.add(new Beer(0,0));
        }

        if(item instanceof Chicken) {
            inventory.add(new ChickenLeg(0,0));
        }

        if(item instanceof ChaliceTable) {
            inventory.add(new Chalice(0,0));
        }

        if(item instanceof CheeseBarrel) {
            inventory.add(new Cheese(0,0));
        }

        item.use();
    }

    public void useItem(int index) {
        if(index <= this.inventory.size()) {
            EnvironmentItem item = this.inventory.get(index - 1);
            controller.useItem(item, this.findZone());
            this.inventory.remove(index - 1);
        }
    }
}
public class Time {
    int day, hour, minute, second;
    boolean dayOver;
    int spawnTimer, spawnCounter;

    public Time() {
        this.hour = 8;
        this.minute = 0;
        this.day = 0;
        this.second = 0;
        this.dayOver = true;
        this.spawnTimer = 120;
        this.spawnCounter = 0;
    }

    public void addMinute() {
        this.minute += 1;
        this.spawnCounter += 1;

        if(this.minute % 60 == 0) {
            this.addHour();
            this.minute = 0;
        }

        if(this.spawnCounter % spawnTimer == 0) {
            controller.newCustomer();
            this.spawnCounter = 0;
        }
    }

    public void setSpawnTimer(int spawnTimer) {
        this.spawnTimer = spawnTimer;
    }

    private void addHour() {
        this.hour += 1;

        if(this.hour % 24 == 0) {
            this.hour = 0;
        }

        if(this.hour == 0) {
            this.dayOver = true;
            controller.dayEnd();
        }
    }

    public void newDay() {
        this.day += 1;
        this.hour = 8;
        this.minute = 0;
        this.dayOver = false;
    }

    public void draw() {
        this.second += 15;
        if(this.second % 60 == 0 && !this.dayOver) {
            this.addMinute();
            this.second = 0;
        }
    }
}
enum WallType {
    BOTTOM, SIDE, TOP, DOOR, WINDOW;
}

public class Wall extends GameObject{
    float width, height;
    WallType wallType;

    public Wall(float x, float y, float width, float height, WallType wallType) {
        super(x, y, ((Shape) new Rectangle2D.Float(x, y, width, height)));
        this.width = width;
        this.height = height;
        this.wallType = wallType;
    }

    public void draw() {
        if(this.wallType == WallType.BOTTOM) {
            image(OUTSIDE_WALL, this.getX(), this.getY(), this.width, this.height);
        } else if(this.wallType == WallType.TOP) {
            image(INSIDE_WALL, this.getX(), this.getY(), this.width, this.height);
        } else if(this.wallType == WallType.DOOR) {
            image(INDOOR_FLOOR, this.getX(), this.getY(), this.width, this.height);
            image(DOOR, this.getX(), this.getY(), this.width, this.height);
        } else if(this.wallType == WallType.WINDOW) {
            image(WINDOW, this.getX(), this.getY(), this.width, this.height);
        } else {
            fill(128, 128, 128);
            rect(this.getX(), this.getY(), this.width, this.height);
        }
    }
}
public class Wizard extends Customer {
    public Wizard(float x, float y, int popularity, int goldAmount) {
        super(x, y, ((Shape) new Rectangle2D.Float(x, y, 30, 40)), popularity, goldAmount);
    }

    public void draw() {
        super.draw();
        this.setShape(new Rectangle2D.Float(this.getX(), this.getY(), 30, 40));
        image(WIZARD_IDLE, this.getX(), this.getY(), 30, 40);
    }

    @Override
    protected void leave() {
        controller.popularity.addPopularity(Faction.WIZARD, this.evaluatePerformance());
        super.leave();
    }
}
public class Worker extends Staff {
    ItemType serving;
    EnvironmentItem nearbyResource;
    boolean achievedGoal;
    Customer target;
    PVector startPos;


    public Worker (float x, float y, ItemType serving) {
        super(x, y);
        this.startPos = new PVector(x,y);
        this.currentFacing = Facing.DOWN;
        this.staffImage = SERVER_DOWN_IDLE;
        this.serving = serving;
        this.nearbyResource = null;
        this.achievedGoal = true;
        this.direction = new PVector(0,0);
    }

    public void setFacing(Facing facingDirection) {
        if(facingDirection == this.currentFacing)
            return;
        
        this.currentFacing = facingDirection;

        switch (facingDirection) {
            case UP:
                this.staffImage = SERVER_UP_IDLE;
                break;
            
            case DOWN:
                this.staffImage = SERVER_DOWN_IDLE;
                break;  
            
            case LEFT:
                this.staffImage = SERVER_LEFT_IDLE;
                break;
            
            case RIGHT:
                this.staffImage = SERVER_RIGHT_IDLE;
                break;
        }
    }

    private boolean isCloser(PVector newPos) {
        try {
            return (this.nearbyResource.getPos().sub(this.getPos()).mag() > newPos.sub(newPos).mag());
        } catch (NullPointerException e) {
            return false;
        }
    }

    private void findKeg() {
        System.out.println("Finding Keg");
        for(EnvironmentItem item : controller.items) {
            if(!(item instanceof Keg))
                continue;

            if(this.nearbyResource == null)
                this.nearbyResource = item;
            
            if(this.isCloser(item.getPos()))
                this.nearbyResource = item;
        }

    }

    private void findChicken() {
        System.out.println("Finding chicken");
        for(EnvironmentItem item : controller.items) {
            if(!(item instanceof Chicken))
                continue;
                
            if(this.nearbyResource == null)
                this.nearbyResource = item;
            
            if(this.isCloser(item.getPos()))
                this.nearbyResource = item;
        }
    }

    private void findCheese() {
        System.out.println("Finding cheese");
        for(EnvironmentItem item : controller.items) {
            if(!(item instanceof CheeseBarrel))
                continue;
                
            if(this.nearbyResource == null)
                this.nearbyResource = item;
            
            if(this.isCloser(item.getPos()))
                this.nearbyResource = item;
        }
    }

    private void findChalice() {
        System.out.println("Finding cheese");
        for(EnvironmentItem item : controller.items) {
            if(!(item instanceof ChaliceTable))
                continue;
                
            if(this.nearbyResource == null)
                this.nearbyResource = item;
            
            if(this.isCloser(item.getPos()))
                this.nearbyResource = item;
        }
    }

    private void findResource() {
        switch(this.serving) {
            case BEER:
                this.findKeg();
                break;
                
            case CHICKENLEG:
                this.findChicken();
                break;

            case CHALICE:
                this.findChalice();
                break;
            
            case CHEESE:
                this.findCheese();
                break;
        }
    }

    private void findCustomer() {
        boolean likesItem = false;
        for(Customer customer : controller.customers) {
            for(ItemType item : customer.likes) {
                if(item == this.serving) {
                     likesItem = true;
                     break;
                }
            }

            if(!likesItem || customer.entering || customer.leaving || customer.getDiminishingReturns() >= 30)
                continue;

            if(this.target == null)
                this.target = customer;
            
            if(this.isCloser(customer.getPos()))
                this.target = customer;
        }
    }

    @Override
    public void move(PVector change) {
        if (!achievedGoal) {
            if(this.target != null) {
                this.achievedGoal = this.targetSearch();
            } else if(this.nearbyResource != null) {
                this.achievedGoal = this.resourceSearch();
            }
        } else {
            if(this.inventory.size() == 0) {
                // System.out.println("Going to find resources");
                this.nearbyResource = null;
                this.findResource();      

                if(this.nearbyResource == null) {
                    this.beIdle();
                } else {
                    this.achievedGoal = false;
                }
                
            } else if(this.target == null) {
                // System.out.println("Going to find targets");
                this.findCustomer();
                if(this.target == null) {
                    this.beIdle();
                } else {
                    this.achievedGoal = false;
                }
            }
        }

        // System.out.println("Moving!: " +change);
        super.move(this.direction);
    }

    private void beIdle() {
        this.findDirection(this.startPos.copy());
        if(this.getPos().sub(this.startPos.copy()).mag() < 1) {
            this.direction = new PVector(0,0);
         }
        this.achievedGoal = true;
    }

    private boolean targetSearch() {
        if(controller.collisionDetector.checkCollision(this.target.getShape(), this.findZone())) {
            System.out.println("Found target!");
            this.useItem(1);
            this.target = null;
            return true;
        } else {
            this.findDirection(this.target.getPos());
            return false;
        }
    }

    private boolean resourceSearch() {
        if(controller.collisionDetector.checkCollision(this.nearbyResource.getShape(), this.findZone())) {
            System.out.println("Found resource!");
            this.direction = new PVector(0,0);
            while(this.inventory.size() < 5) {
                this.pickupItem();
            }
            this.nearbyResource = null;
            return true;
        } else {
            this.findDirection(this.nearbyResource.getPos());
            return false;
        }
    }

    private void findDirection(PVector targetPos) {
        PVector change = targetPos.sub(this.getPos()).normalize();
        float xChange = change.x;
        float yChange = change.y;
        float moveSize = 10;

        if(abs(xChange) > abs(yChange)) {
            if(xChange >= 0) {
                this.setFacing(Facing.RIGHT);
                this.direction = new PVector(moveSize, 0);
            } else {
                this.setFacing(Facing.LEFT);
                this.direction = new PVector(-moveSize, 0);
            }
        } else {
            if(yChange >= 0) {
                this.setFacing(Facing.DOWN);
                this.direction = new PVector(0, moveSize);
            } else {
                this.setFacing(Facing.UP);
                this.direction = new PVector(0, -moveSize);
            }
        }
    }

    public void fillInventory() {
    }

    @Override
    public void draw() {
        this.move(this.direction);
        super.draw();
    }

     @Override
    public void pickupItem() {
        this.staffImage = SERVER_PICKUP;
        super.pickupItem();
    }

    @Override
    public void useItem(int index) {
        this.staffImage = SERVER_USEITEM;
        super.useItem(index);
    }

}
public class Zombie extends Customer {
    public Zombie(float x, float y, int popularity, int goldAmount) {
        super(x, y, ((Shape) new Rectangle2D.Float(x, y, 30, 40)), popularity, goldAmount);
    }

    public void draw() {
        super.draw();
        this.setShape(new Rectangle2D.Float(this.getX(), this.getY(), 30, 40));
        image(ZOMBIE_IDLE, this.getX(), this.getY(), 30, 40);
    }

    @Override
    protected void leave() {
        controller.popularity.addPopularity(Faction.ZOMBIE, this.evaluatePerformance());
        super.leave();
    }
}
  public void settings() {  fullScreen(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "P3" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
