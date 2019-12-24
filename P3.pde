import java.util.Iterator;
import java.awt.geom.Rectangle2D;
import java.awt.Shape;
import processing.sound.*;

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
void setup() {
  fullScreen();
  noCursor();
  frameRate(30);
  music = new SoundFile(this, "inn_music.mp3");
  music.loop();

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
