import java.util.Iterator;
import java.awt.geom.Rectangle2D;
import java.awt.Shape;

// Keep controller as global to control the gamestate.
Controller controller;

/**
* Setup the game
*/
void setup() {
  fullScreen();
  noCursor();
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