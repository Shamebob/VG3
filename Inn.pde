// The inn is the main play area na dis built from walls and floors
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

    // Create the walls of the inn
    void buildWalls() {
        float wallWidth = displayWidth/100 * 3;
        int wallCount = int((this.endX - this.startX)/wallWidth);
        float wallHeight = displayWidth/100 * 4;
        float curX = startX;
        float curY = endY;

        for (int i = 0; i < wallCount; i++) {
            // Create a door at the halfway point
            if(i == wallCount/2) {
                this.doorPos = new PVector(curX, startY);
                this.door = new Wall(curX, startY, wallWidth, wallHeight, WallType.DOOR);
            } else {
                this.walls.add(new Wall(curX, startY, wallWidth, wallHeight, WallType.BOTTOM));
            }
            
            // Put in some windows
            if(i % 4 == 1) {
                this.walls.add(new Wall(curX, endY, wallWidth, wallHeight, WallType.WINDOW));
            } else {
                this.walls.add(new Wall(curX, endY, wallWidth, wallHeight, WallType.TOP));
            }
            curX += wallWidth;
        }

        wallCount = int((this.startY - this.endY)/wallHeight) + 1;
        for(int i = 0; i < wallCount; i++) {
            this.walls.add(new Wall(this.startX, curY, wallWidth/3, wallHeight, WallType.SIDE));
            this.walls.add(new Wall(curX, curY, wallWidth/3, wallHeight, WallType.SIDE));
            curY += wallHeight;
        }
        System.out.println("Number of walls: " + this.walls.size());
    }

    // Construct the floor of the play area.
     void constructFloor() {
        float floorWidth = displayWidth/100 * 6;
        int widthCount = ceil(int(displayWidth/floorWidth)) + 1;
        float floorHeight = displayWidth/100 * 6;
        int heightCount = ceil(int(displayHeight/floorHeight));
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
        widthCount = ceil(int(innWidth/floorWidth));
        heightCount = ceil(int(innHeight/floorHeight)) + 1;
        curY = this.endY;
        for(int i = 0; i < heightCount; i++) {
            curX = this.startX;
            for(int j = 0; j < widthCount; j++) {
                this.floor.add(new Floor(curX, curY, floorWidth, floorHeight, FloorType.INDOOR));
                curX += floorWidth;
            }
            curY += floorHeight;
        }

        // Create a path from the inn to the bottom of the screen.
        // This is where characters are spawned.
        curX = this.doorPos.x;
        curY = this.doorPos.y;
        floorWidth = displayWidth/100 * 3;
        float doorToHUDHeight = displayHeight - this.doorPos.y;
        heightCount = ceil(int(doorToHUDHeight/floorHeight)) + 1;
        for(int i = 0; i < heightCount; i++) {
            this.floor.add(new Floor(curX, curY, floorWidth, floorHeight, FloorType.PATH));
            curY += floorHeight;
        }

        
    }

    // Draw the floor and door
    public void drawFloor() {
        for(Floor floor : this.floor) {
            floor.draw();
        }
        this.door.draw();
    }

    // Draw the inn walls
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

    // Check if a position has a wall.
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
