public class Player extends Staff {
    public Player (float x, float y) {
        super(x, y);
        this.staffImage = HERO_DOWN_IDLE;
    }

    public void setFacing(Facing direction) {
        if(direction == this.currentFacing)
            return;
        
        this.currentFacing = direction;

        switch (direction) {
            case UP:
                this.staffImage = HERO_UP_IDLE;
                break;
            
            case DOWN:
                this.staffImage = HERO_DOWN_IDLE;
                break;
            
            case LEFT:
                this.staffImage = HERO_LEFT_IDLE;
                break;
            
            case RIGHT:
                this.staffImage = HERO_RIGHT_IDLE;
                break;
        }
    }

    @Override
    public void pickupItem() {
        this.staffImage = HERO_PICKUP;
        super.pickupItem();
    }

    @Override
    public void useItem(int index) {
        this.staffImage = HERO_USEITEM;
        super.useItem(index);
    }
}
