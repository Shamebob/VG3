public class Player extends Staff {
    PImage playerImage;
    public Player (float x, float y) {
        super(x, y);
        this.playerImage = HERO_DOWN_IDLE;
    }

    public void setFacing(Facing direction) {
        if(direction == this.currentFacing)
            return;
        
        this.currentFacing = direction;

        switch (direction) {
            case UP:
                this.playerImage = HERO_UP_IDLE;
                break;
            
            case DOWN:
                this.playerImage = HERO_DOWN_IDLE;
                break;
            
            case LEFT:
                this.playerImage = HERO_LEFT_IDLE;
                break;
            
            case RIGHT:
                this.playerImage = HERO_RIGHT_IDLE;
                break;
        }
    }

    @Override
    public void pickupItem() {
        this.playerImage = HERO_PICKUP;
        super.pickupItem();
    }

    @Override
    public void useItem(int index) {
        this.playerImage = HERO_USEITEM;
        super.useItem(index);
    }

    public void draw() {
        image(this.playerImage, this.getX(), this.getY(), HEIGHT, WIDTH);
        super.draw();
    }
}
