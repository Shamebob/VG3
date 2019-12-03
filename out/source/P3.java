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
import java.awt.Shape; 
import java.awt.geom.*; 

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

/**
* Setup the game
*/
public void setup() {
  
  noCursor();
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
        controller.movePlayer(0, -moveSize);
        break;

        case 'a':
        controller.movePlayer(-moveSize, 0);
        break;

        case 's':
        controller.movePlayer(0, moveSize);
        break;

        case 'd':
        controller.movePlayer(moveSize, 0);
        break;
    }
}
public class Animator {
    public Animator() {
    }

    public void drawActiveGame(Controller controller) {
        controller.time.draw();
        controller.player.draw();
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
public class Cleaner {
    Cleaner() {
    }

    public void cleanGame() {
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
public abstract class EnvironmentItem extends GameObject {
    public EnvironmentItem(float x, float y, Shape shape) {
        super(x, y, shape);
    }
}



public abstract class GameObject {
    protected PVector pos;
    protected Shape shape;
    protected boolean active;

    public GameObject (float x, float y, Shape shape) {
        this.pos = new PVector(x,y);
        this.shape = shape;
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
public abstract class NPC extends Character{

    public NPC (float x, float y, Shape shape) {
        super(x, y, shape);
    }

}

public class Player extends Staff {

    public Player (float x, float y) {
        super(x, y);
    }

    public void draw() {
        fill(255, 0, 0);
        super.draw();
    }

}

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
final float HEIGHT = 20;
final float WIDTH = 20;

public abstract class Staff extends Character {
    public Staff (float x, float y) {
        super(x, y, ((Shape) new Rectangle2D.Float(x, y, HEIGHT, WIDTH)));
    }

    public void draw() {
        rect(this.getX(), this.getY(), HEIGHT,WIDTH);
        this.setShape(new Rectangle2D.Float(this.getX(), this.getY(), HEIGHT, WIDTH));
    }
}
public class Worker extends Staff {
    public Worker (float x, float y) {
        super(x, y);
    }

    public void draw() {
        fill(0, 255, 0);
        super.draw();
    }

}
public class Time {
    int day, hour, minute, second;
    boolean dayOver;

    public Time() {
        this.hour = 8;
        this.minute = 0;
        this.day = 1;
        this.second = 0;
        this.dayOver = false;
    }

    public void addMinute() {
        this.minute += 1;
        if(this.minute % 60 == 0) {
            this.addHour();
            this.minute = 0;
        }
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
        this.second += 1;
        if(this.second % 60 == 0) {
            this.addMinute();
            this.second = 0;
        }

        this.drawBackground();
        textSize(16);
        fill(0,255,0);
        if (this.minute < 10) {
            text("Day " + this.day + "\n" + this.hour + ":0" + this.minute, 0, 20);
        } else {
            text("Day " + this.day + "\n" + this.hour + ":" + this.minute, 0, 20);
        }
    }

    private void drawBackground() {
        if(this.hour < 20 && this.hour >= 8) {
            background(255, 255, 255);
        } else {
            background(0, 0, 0);
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
