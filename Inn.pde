public class Inn {
    private float startX, startY, endX, endY;
    private ArrayList<Wall> walls = new ArrayList<Wall>();
    public Inn() {
        this.startX = displayWidth/4;
        this.endX = this.startX * 3;
        this.startY = displayHeight/4 * 3;
        this.endY = displayHeight/4;
        this.buildWalls();
    }

    void buildWalls() {
        float wallWidth = displayWidth/100 * 3;
        int wallCount = int((this.endX - this.startX)/wallWidth);
        float wallHeight = displayWidth/100 * 4;
        float curX = startX;
        float curY = endY;

        for (int i = 0; i < wallCount; i++) {
            if(i == wallCount/2) {
                this.walls.add(new Wall(curX, startY, wallWidth, wallHeight, WallType.DOOR));
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

        wallCount = int((this.startY - this.endY)/wallHeight) + 1;
        println("Wall count: " + wallCount);
        for(int i = 0; i < wallCount; i++) {
            this.walls.add(new Wall(this.startX, curY, wallWidth/5, wallHeight, WallType.SIDE));
            this.walls.add(new Wall(curX, curY, wallWidth/5, wallHeight, WallType.SIDE));
            curY += wallHeight;
        }
        System.out.println("Number of walls: " + this.walls.size());
    }

    void draw() {
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
