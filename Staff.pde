final float HEIGHT = 30;
final float WIDTH = 30;

enum Facing{
    UP, LEFT, DOWN, RIGHT;
}

// A staff member is someone who can serve customers.
public abstract class Staff extends Character {
    Facing currentFacing;
    PImage staffImage;
    // INventory of items that have been picked up
    ArrayList<EnvironmentItem> inventory = new ArrayList<EnvironmentItem>();

    public Staff (float x, float y) {
        super(x, y, ((Shape) new Rectangle2D.Float(x, y, WIDTH, HEIGHT)));
        this.currentFacing = Facing.DOWN;
    }

    public void draw() {
        image(this.staffImage, this.getX(), this.getY(), HEIGHT, WIDTH);
        this.setShape(new Rectangle2D.Float(this.getX(), this.getY(), WIDTH, HEIGHT));
    }

    // Find the area infront of the character that can be interacted with
    protected Shape findZone() {
        float x = this.getX();
        float y = this.getY();
        Shape shapeArea;

        switch (currentFacing) {
            case UP:
                y -= HEIGHT;
                break;

            case LEFT:
                x -= WIDTH;
                break;

            case DOWN:
                y += HEIGHT;
                break;

            case RIGHT:
                x += WIDTH;
                break;
        }

        return new Rectangle2D.Float(x, y, WIDTH, HEIGHT);
    }

    // Pickup an item from the resources when there is inventory space
    public void pickupItem() {
        EnvironmentItem item = controller.findItem(this.findZone());
        if(item == null || this.inventory.size() >= 5)
            return;
        
        if(item instanceof Keg) {
            inventory.add(new Beer(0,0));
        }

        if(item instanceof Chicken) {
            inventory.add(new ChickenLeg(0,0));
        }

        if(item instanceof ChaliceTable) {
            inventory.add(new Chalice(0,0));
        }

        if(item instanceof CheeseBarrel) {
            inventory.add(new Cheese(0,0));
        }

        item.use();
    }

    // Use an item
    public void useItem(int index) {
        if(index <= this.inventory.size()) {
            EnvironmentItem item = this.inventory.get(index - 1);
            controller.useItem(item, this.findZone());
            this.inventory.remove(index - 1);
        }
    }
}
