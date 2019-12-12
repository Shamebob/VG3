import java.util.Iterator;
import java.awt.geom.Rectangle2D;
import java.awt.Shape;

// Keep controller as global to control the gamestate.
Controller controller;
PImage OUTSIDE_WALL, INSIDE_WALL, DOOR, KEG, BEER, HERO_IDLE, WINDOW;

/**
* Setup the game
*/
void setup() {
  fullScreen();
  noCursor();
  OUTSIDE_WALL = loadImage("outside_wall.png");
  INSIDE_WALL = loadImage("inside_wall.png");
  DOOR = loadImage("door.png");
  KEG = loadImage("keg.png");
  HERO_IDLE = loadImage("hero_idle.png");
  WINDOW = loadImage("window.png");
  BEER = loadImage("beer.png");

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
    }
}