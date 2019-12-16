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
PImage OUTSIDE_WALL, INSIDE_WALL, DOOR, HERO_IDLE, WINDOW, HAPPY, SAD;
PImage KNIGHT_IDLE, KNIGHT_CREST;
PImage KEG, BEER, CHICKEN, CHICKEN_LEG;

/**
* Setup the game
*/
public void setup() {
  
  noCursor();
  OUTSIDE_WALL = loadImage("outside_wall.png");
  INSIDE_WALL = loadImage("inside_wall.png");
  DOOR = loadImage("door.png");
  KEG = loadImage("keg.png");
  HERO_IDLE = loadImage("hero_idle.png");
  WINDOW = loadImage("window.png");
  BEER = loadImage("beer.png");
  HAPPY = loadImage("happy.png");
  SAD = loadImage("sad.png");
  KNIGHT_IDLE = loadImage("knight_idle.png");
  KNIGHT_CREST = loadImage("knight_crest.png");
  CHICKEN = loadImage("whole_chicken.png");
  CHICKEN_LEG = loadImage("chicken_leg.png");

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
          controller.player.useItem(1);
          break;

        case '2':
          controller.player.useItem(2);
          break;

        case '3':
          controller.player.useItem(3);
          break;
        
        case '4':
          controller.player.useItem(4);
          break;

        case '5':
          controller.player.useItem(5);
          break;
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
        this.drawHUD(controller);
        controller.inn.draw();
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
    }

    private void drawTimedBackground(Time time) {
        if(time.hour < 20 && time.hour >= 8) {
            background(255, 255, 255);
        } else {
            background(0, 0, 0);
        }
    }

    private void drawHUD(Controller controller) {
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

        PVector currentPos = new PVector(this.actionBarStartX + (actionBoxWidth/2), displayHeight - (actionBarHeight/3));
        PVector factorChange = new PVector(actionBoxWidth, 0);
        for(EnvironmentItem item : controller.player.inventory) {
            item.setPos(currentPos);
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
public abstract class Character extends GameObject {

    public Character (float x, float y, Shape shape) {
        super(x, y, shape);
    }

    public void move(PVector change) {
        this.pos.add(change);
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }
}
public class Chicken extends EnvironmentItem {

    public Chicken(float x, float y) {
        super(x, y, ((Shape) new Ellipse2D.Float(x, y, 15, 15)), 10);
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
    boolean gameInPlay;
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

    public void start() {
        this.time = new Time();
        this.inn = new Inn();
        this.spawner.setDoorPos(this.inn.getDoorPos());
        this.gameInPlay = true;
        this.player = spawner.spawnPlayer();
        this.customers.add(spawner.spawnCustomer());
        this.items.add(new Keg(displayWidth/2, displayHeight/2));
        this.items.add(new Chicken(displayWidth/2 + 100, displayHeight/2 + 100));
        
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
enum ItemType {
    BEER, CHICKENLEG;
}

public abstract class Customer extends Character {
    int popularity;
    float satisfaction;
    Gold money;
    PVector direction;
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
        this.enter();
    }

    public void setLikes(ItemType[] likes) {
        this.likes = likes;
    }

    public void setDislikes(ItemType[] dislikes) {
        this.dislikes = dislikes;
    }

    public abstract void draw();

    private PVector findDirection() {
        return new PVector(random(-2, 2), random(-2, 2));
    }

    public void useItem(EnvironmentItem item) {
        if(this.leaving)
            return;
        
        if(item instanceof Beer) {
            this.reaction(ItemType.BEER);
            //TODO: If the item is correct as to what they want, + lots. Diminishing returns based on likes and dislikes
            //TODO: Could have it so that new patrons declare what they like?
        } else if(item instanceof ChickenLeg) {
            this.reaction(ItemType.CHICKENLEG);
        }

        this.money.buy(item);

        if(this.money.getAmount() < 10) {
            this.leave();
        } 
    }

    private void reaction(ItemType item) {
        for(ItemType like : this.likes) {
            if(like == item) {
                controller.addFeeling(new Feeling(this.getX(), this.getY() - 5, Emotion.HAPPY));
                return;
            }
        }

        for(ItemType dislike : this.dislikes) {
            if(dislike == item) {
                controller.addFeeling(new Feeling(this.getX(), this.getY() - 5, Emotion.SAD));
                this.satisfaction -= 10;
                return;
            }
        }
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
        this.direction = controller.inn.getDoorPos().sub(this.getPos()).normalize();
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
        if(this.drawCounter % 30 == 0) {
            super.move(new PVector(0, -5));
        }

        
        image(this.drawing, this.getX(), this.getY(), 15, 15);

        if(this.drawCounter == 145) {
            this.destroy();
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

    public Gold() {
        this.amount = 0;
    }

    public void addGold(int quantity) {
        this.amount += quantity;
    }

    public int getAmount() {
        return this.amount;
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
    PVector doorPos;

    public Inn() {
        this.startX = displayWidth/4;
        this.endX = this.startX * 3;
        this.startY = displayHeight/4 * 3;
        this.endY = displayHeight/4;
        this.buildWalls();
    }

    public void buildWalls() {
        float wallWidth = displayWidth/100 * 3;
        int wallCount = PApplet.parseInt((this.endX - this.startX)/wallWidth);
        float wallHeight = displayWidth/100 * 4;
        float curX = startX;
        float curY = endY;

        for (int i = 0; i < wallCount; i++) {
            if(i == wallCount/2) {
                this.walls.add(new Wall(curX, startY, wallWidth, wallHeight, WallType.DOOR));
                this.doorPos = new PVector(curX, startY);
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
            this.walls.add(new Wall(this.startX, curY, wallWidth/5, wallHeight, WallType.SIDE));
            this.walls.add(new Wall(curX, curY, wallWidth/5, wallHeight, WallType.SIDE));
            curY += wallHeight;
        }
        System.out.println("Number of walls: " + this.walls.size());
    }

    public void draw() {
        for(Wall wall : this.walls) {
            wall.draw();
        }
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
        super(x, y, ((Shape) new Ellipse2D.Float(x, y, 15, 15)), 10);
    }

    public void draw() {
        image(KEG, this.getX(), this.getY(), 30, 30);
    }
}
public class Knight extends Customer {
    public Knight(float x, float y, int popularity, int goldAmount) {
        super(x, y, ((Shape) new Rectangle2D.Float(x, y, 20, 20)), popularity, goldAmount);
    }

    public void draw() {

        if(this.entering)
            super.checkEntered();

        if(this.moveCounter % 120 == 0 && !this.leaving && !this.entering) 
            this.direction = super.findDirection();

        if(this.moveCounter % 15 == 0)
            this.move(this.direction);

        this.moveCounter += 1;

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
    public Player (float x, float y) {
        super(x, y);
    }

    public void setFacing(Facing direction) {
        this.currentFacing = direction;
    }

    public void draw() {
        image(HERO_IDLE, this.getX(), this.getY(), HEIGHT, WIDTH);
        super.draw();
    }
}
public class Popularity {
    float knightPopularity;
    int knightCounter;
    int knightPopularityLevel;

    public Popularity() {
        this.knightPopularity = 0;
        this.knightPopularityLevel = 1;
        this.knightCounter = 0;
    }

    public void addKnightPopularity(float popularity) {
        this.knightCounter += 1;
        this.knightPopularity = (this.knightPopularity + popularity)/knightCounter;
        System.out.println("Popularity: "+ this.knightPopularity);
        
        if(this.knightPopularity >= (this.knightPopularityLevel * 10)) {
            this.knightPopularityLevel += 1;
            this.knightPopularity = 0;
            this.knightCounter = 0;
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
        Customer customer;
        customer = new Knight(this.doorPos.x + 10, displayHeight - (displayHeight/10), popularity, goldAmount);
        generateLikesAndDislikes(customer, controller.popularity.getKnightPopularityLevel());
        return customer;
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
public class Time {
    int day, hour, minute, second;
    boolean dayOver;
    int spawnTimer, spawnCounter;

    public Time() {
        this.hour = 8;
        this.minute = 0;
        this.day = 1;
        this.second = 0;
        this.dayOver = false;
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
            //TODO: Turn back on
            // controller.newCustomer();
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

        if(this.hour == 2) {
            this.dayOver = true;
        }
    }

    public void newDay() {
        this.day += 1;
        this.hour = 8;
        this.minute = 0;
    }

    public void draw() {
        this.second += 5;
        if(this.second % 60 == 0) {
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
