enum FloorType {
    GRASS, INDOOR, PATH;
}

// The floor class is used to tile the room and make the concept of a floor
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

    // Determine what type of floor tile should be displayed
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
