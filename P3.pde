import java.util.Iterator;
import java.awt.geom.Rectangle2D;
import java.awt.Shape;

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
void setup() {
  fullScreen();
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
void draw() {
    controller.drawGame();
}

/**
* Register key pressed for moving and firing.
*/
void keyPressed() {
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
        
        case ' ':
          controller.startDay();
    }
}