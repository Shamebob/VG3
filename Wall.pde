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
