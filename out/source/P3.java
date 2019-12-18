import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.Iterator; 
import java.awt.geom.Rectangle2D; 
import java.awt.Shape; 
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
PImage HAPPY, SAD;
PImage KNIGHT_IDLE, KNIGHT_CREST, KNIGHT_BOSS_IDLE;
PImage KEG, BEER, CHICKEN, CHICKEN_LEG;

/**
* Setup the game
*/
public void setup() {
  
  noCursor();
  frameRate(60);
  OUTSIDE_WALL = loadImage("outside_wall.png");
  INSIDE_WALL = loadImage("inside_wall.png");
  DOOR = loadImage("door.png");
  KEG = loadImage("keg.png");
  HERO_DOWN_IDLE = loadImage("player_idle.png");
  HERO_RIGHT_IDLE = loadImage("player_right1.png");
  HERO_LEFT_IDLE = loadImage("player_left1.png");
  HERO_UP_IDLE = loadImage("player_up1.png");
  HERO_PICKUP = loadImage("player_pickup.png");
  HERO_USEITEM = loadImage("player_useitem.png");


  WINDOW = loadImage("window.png");
  BEER = loadImage("beer.png");
  HAPPY = loadImage("happy.png");
  SAD = loadImage("sad.png");
  KNIGHT_IDLE = loadImage("knight_idle.png");
  KNIGHT_BOSS_IDLE = loadImage("knight_boss_idle.png");
  KNIGHT_CREST = loadImage("knight_crest.png");
  CHICKEN = loadImage("whole_chicken.png");
  CHICKEN_LEG = loadImage("chicken_leg.png");
  INDOOR_FLOOR = loadImage("indoor_floor.png");
  GRASS = loadImage("grass.png");
  PATH = loadImage("path.png");

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
    PImage[] crests = new PImage[]{KNIGHT_CREST};

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
        for(EnvironmentItem item : controller.build.purchaseItems) {
            item.draw();
        }
    }

    public void setupBuildItems() {
        float actionBoxWidth = (displayWidth/2)/5;
        PVector currentPos = new PVector(this.actionBarStartX + (actionBoxWidth/2), displayHeight - (actionBarHeight/3));
        PVector factorChange = new PVector(actionBoxWidth, 0);
        for(EnvironmentItem item : controller.build.purchaseItems) {
            item.setPos(currentPos.copy());
            currentPos = currentPos.add(factorChange);
        }
    }



    private void drawInventoryItems(float actionBoxWidth) {
        PVector currentPos = new PVector(this.actionBarStartX + (actionBoxWidth/2), displayHeight - (actionBarHeight/3));
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

        float imageSpace = this.customerEmotionsWidth/6;
        float imageY = displayHeight - (this.actionBarHeight/2);
        float imageHeight = this.actionBarHeight/4;
        float currentPoint = this.customerEmotionsStartX + (imageSpace/4);
        float imageWidth = imageSpace/2;

        for(ItemType item : this.newCustomerLikes) {
            drawItemType(item, currentPoint, imageY, imageWidth, imageHeight);
        }

        currentPoint = this.customerEmotionsStartX + (this.customerEmotionsWidth/2) + (imageSpace/4);

        for(ItemType item : this.newCustomerDislikes) {
            drawItemType(item, currentPoint, imageY, imageWidth, imageHeight);
        }

    }

    public void drawItemType(ItemType item, float x, float y, float width, float height) {
        PImage itemImage = null;

        if(item == ItemType.BEER) {
            itemImage = BEER;
        } else if(item == ItemType.CHICKENLEG) {
            itemImage = CHICKEN_LEG;
        }

        if(itemImage != null)
            image(itemImage, x, y, width, height);
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

    public Boss(float x, float y, int popularity, int goldAmount, Faction faction, ArrayList<Customer> entourage) {
        super(x, y, ((Shape) new Rectangle2D.Float(x, y, 40, 50)), popularity, goldAmount);
        this.faction = faction;

        switch (faction) {
            case KNIGHT:
                this.characterImage = KNIGHT_BOSS_IDLE;
                break;
        }

        this.entourage = entourage;
    }

    public void draw() {
        super.draw();
        this.setShape(new Rectangle2D.Float(this.getX(), this.getY(), 40, 50));
        image(this.characterImage, this.getX(), this.getY(), 40, 50);
    }

    @Override
    protected void leave() {
        if(faction == Faction.KNIGHT) {
            controller.popularity.addKnightPopularity(this.evaluatePerformance());
        }

        for(Customer customer : entourage) {
            customer.leave();
        }
        super.leave();
    }


}
public class Build {
    EnvironmentItem[] purchaseItems = new EnvironmentItem[]{new Keg(0, 0), new Chicken(0, 0)};
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
        }

        if(controller.gold.buyItem(cost)) {
            return item;
        } else {
            return null;
        }
    }

}
public abstract class Character extends GameObject {

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
        this.items.add(new Keg(displayWidth/2, displayHeight/2));
        this.items.add(new Chicken(displayWidth/2 + 100, displayHeight/2 + 100));
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
        if(this.popularity.knightPopularityLevel == 1) {
            this.spawner.setKnightSpawn(5);
        } else {
            this.spawner.setKnightSpawn(floor(this.popularity.knightPopularity/2));
        }

        this.time.setSpawnTimer(960/this.spawner.getCustomersInDay());
    }


    public void spawnBoss(Faction faction) {
        Boss boss;

        if(faction == Faction.KNIGHT) {
            boss = spawner.spawnKnightBoss();
        } else {
            boss = spawner.spawnKnightBoss();
        }
    
        for(Customer customer : boss.entourage) {
            this.customers.add(customer);
        }

        this.customers.add(boss);
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
enum ItemType {
    BEER, CHICKENLEG;
}

enum Faction {
    KNIGHT;
}

public abstract class Customer extends Character {
    int popularity;
    float satisfaction;
    Gold money;
    PVector direction;
    int moveCounter;
    boolean entering, leaving;
    ItemType[] likes, dislikes;
    int diminishingReturn;

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
        this.enter();
    }

    public void setLikes(ItemType[] likes) {
        this.likes = likes;
    }

    public void setDislikes(ItemType[] dislikes) {
        this.dislikes = dislikes;
    }

    public void draw() {
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
            println("Bye bitch");
            this.destroy();
        }

        this.moveCounter += 1;
    }

   
    public void useItem(EnvironmentItem item) {
        boolean likedItem = false;
        ItemType itemType = null;
        if(this.leaving)
            return;
        
        if(item instanceof Beer) {
            itemType = ItemType.BEER;
            //TODO: If the item is correct as to what they want, + lots. Diminishing returns based on likes and dislikes
            //TODO: Could have it so that new patrons declare what they like?
        } else if(item instanceof ChickenLeg) {
            itemType = ItemType.CHICKENLEG;
        }

        this.money.buy(item);
        likedItem = this.reaction(itemType);
        this.addSatisfaction(likedItem);
        println("Satisfaction: : "+ this.satisfaction);
        this.diminishingReturn = 120;

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
        this.direction.y -= 3;
        this.entering = true;
    }

    protected void checkEntered() {
        if(this.getY() <= (controller.inn.getDoorPos().y - 20))
            this.entering = false;
    }

    protected void leave() {
        this.leaving = true;
        this.direction = controller.inn.getDoorPos().sub(this.getPos());
    }

    protected float evaluatePerformance() {
        return this.satisfaction/this.popularity;
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
    HAPPY, SAD;
}

public class Feeling extends Character {
    int drawCounter;
    PImage drawing;

    public Feeling(float x, float y, Emotion currentFeeling) {
        super(x, y, null);
        this.drawCounter = 0;

        if(currentFeeling == Emotion.HAPPY) {
            this.drawing = HAPPY;
        } else {
            this.drawing = SAD;
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
            
            if(i % 5 == 1) {
                this.walls.add(new Wall(curX, endY, wallWidth, wallHeight, WallType.WINDOW));
            } else {
                this.walls.add(new Wall(curX, endY, wallWidth, wallHeight, WallType.TOP));
            }
            curX += wallWidth;
        }

        wallCount = PApplet.parseInt((this.startY - this.endY)/wallHeight) + 1;
        println("Wall count: " + wallCount);
        for(int i = 0; i < wallCount; i++) {
            this.walls.add(new Wall(this.startX, curY, wallWidth/4, wallHeight, WallType.SIDE));
            this.walls.add(new Wall(curX, curY, wallWidth/4, wallHeight, WallType.SIDE));
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
                if (!((curX > this.startX && curX < this.endX - floorWidth) && (curY <= this.startY) && (curY >= this.endY))) {
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
public class Knight extends Customer {
    public Knight(float x, float y, int popularity, int goldAmount) {
        super(x, y, ((Shape) new Rectangle2D.Float(x, y, 30, 40)), popularity, goldAmount);
    }

    public void draw() {
        super.draw();
        this.setShape(new Rectangle2D.Float(this.getX(), this.getY(), 30, 40));
        image(KNIGHT_IDLE, this.getX(), this.getY(), 30, 40);
        //TODO: Make sure they don't leave until they're done.
    }

    @Override
    protected void leave() {
        controller.popularity.addKnightPopularity(this.evaluatePerformance());
        super.leave();
    }
}
public abstract class NPC extends Character{

    public NPC (float x, float y, Shape shape) {
        super(x, y, shape);
    }

}
public class Player extends Staff {
    PImage playerImage;
    public Player (float x, float y) {
        super(x, y);
        this.playerImage = HERO_DOWN_IDLE;
    }

    public void setFacing(Facing direction) {
        if(direction == this.currentFacing)
            return;
        
        this.currentFacing = direction;

        switch (direction) {
            case UP:
                this.playerImage = HERO_UP_IDLE;
                break;
            
            case DOWN:
                this.playerImage = HERO_DOWN_IDLE;
                break;
            
            case LEFT:
                this.playerImage = HERO_LEFT_IDLE;
                break;
            
            case RIGHT:
                this.playerImage = HERO_RIGHT_IDLE;
                break;
        }
    }

    @Override
    public void pickupItem() {
        this.playerImage = HERO_PICKUP;
        super.pickupItem();
    }

    @Override
    public void useItem(int index) {
        this.playerImage = HERO_USEITEM;
        super.useItem(index);
    }

    public void draw() {
        image(this.playerImage, this.getX(), this.getY(), HEIGHT, WIDTH);
        super.draw();
    }
}
public class Popularity {
    float knightPopularity, knightLowerThreshold, knightUpperThreshold;
    int knightCounter, knightPopularityLevel;
    boolean seenKnightBoss;


    public Popularity() {
        this.knightPopularity = 0;
        this.knightPopularityLevel = 1;
        this.knightCounter = 0;
        this.knightLowerThreshold = 0;
    }

    public void addKnightPopularity(float popularity) {
        this.knightCounter += 1;
        this.knightPopularity = (this.knightPopularity + popularity);
        System.out.println("Knight Popularity: "+ this.knightPopularity);
        
        if(this.knightPopularity >= (this.knightPopularityLevel * 10)) {
            this.knightLowerThreshold = this.knightPopularityLevel * 10;
            this.knightPopularityLevel += 1;
            this.knightPopularity = 0;
            this.knightCounter = 0;
        }
        
        if(this.knightPopularity <= this.knightLowerThreshold && this.knightPopularity > 0) {
            this.knightPopularityLevel -= 1;
            this.knightLowerThreshold = this.knightPopularityLevel * 10;
        } 
    }

    public int[] getPopularityLevels() {
        return new int[] {this.knightPopularityLevel};
    }

    public int getKnightPopularityLevel() {
        return this.knightPopularityLevel;
    }
}


/**
* The spawner class is used to add characters to the game.
*/
public class Spawner {
    PVector doorPos;
    int knightSpawn, customersInDay;
    /**
    * Constructor for a spawner.
    */
    Spawner(){
        this.knightSpawn = 0;
    }

    public void setDoorPos(PVector doorPos) {
        this.doorPos = doorPos;
    }

    public void setKnightSpawn(int count) {
        this.knightSpawn = count;
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
        int goldAmount = 50;
        int popularity = 50;
        Customer customer;
        customer = new Knight(this.doorPos.x + 10, displayHeight - (displayHeight/10), popularity, goldAmount);
        generateLikesAndDislikes(customer, controller.popularity.getKnightPopularityLevel());
        return customer;
    }
    
    private Customer spawnKnight(float x, float y) {
        int goldAmount = 50;
        int popularity = 50;
        Customer customer;
        customer = new Knight(x, y, popularity, goldAmount);
        generateLikesAndDislikes(customer, controller.popularity.getKnightPopularityLevel());
        return customer;
    }

    public Boss spawnKnightBoss() {
        float x = this.doorPos.x;
        float y = displayHeight - (displayHeight/10);
        ArrayList<Customer> entourage = new ArrayList<Customer>();
        PVector[] locations = new PVector[]{new PVector(x - 50, y - 50), new PVector(x-50, y+50), new PVector(x+50, y-50), new PVector(x+50, y+50)};

        for(PVector location : locations) {
            entourage.add(spawnKnight(x, y));
        }
        Boss boss = new Boss(this.doorPos.x + 10, displayHeight - (displayHeight/10), 500, 500, Faction.KNIGHT, entourage);
        generateLikesAndDislikes(boss, controller.popularity.getKnightPopularityLevel());
        return boss;
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
final float HEIGHT = 30;
final float WIDTH = 30;

enum Facing{
    UP, LEFT, DOWN, RIGHT;
}

public abstract class Staff extends Character {
    Facing currentFacing;
    ArrayList<EnvironmentItem> inventory = new ArrayList<EnvironmentItem>();

    public Staff (float x, float y) {
        super(x, y, ((Shape) new Rectangle2D.Float(x, y, WIDTH, HEIGHT)));
        this.currentFacing = Facing.DOWN;
    }

    public void draw() {
        this.setShape(new Rectangle2D.Float(this.getX(), this.getY(), WIDTH, HEIGHT));
    }

    private Shape findZone() {
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
            item.use();
        }

        if(item instanceof Chicken) {
            inventory.add(new ChickenLeg(0,0));
            item.use();
        }
    }

    public void useItem(int index) {
        if(index <= this.inventory.size()) {
            EnvironmentItem item = this.inventory.get(index - 1);
            controller.useItem(item, this.findZone());
            this.inventory.remove(index - 1);
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
public class Worker extends Staff {
    public Worker (float x, float y) {
        super(x, y);
    }

    public void draw() {
        fill(0, 255, 0);
        rect(this.getX(), this.getY(), HEIGHT,WIDTH);
        super.draw();
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
            controller.endDay = true;
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
