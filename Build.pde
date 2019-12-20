/**
* The Build class is an abstraction of the required functionality for build mode.
*/
public class Build {
    // List the items that are required to be drawn when in build mode.
    EnvironmentItem[] purchaseItems = new EnvironmentItem[]{new Keg(0, 0), new Chicken(0, 0), new ChaliceTable(0, 0), new CheeseBarrel(0, 0)};
    PVector buildSquarePos;
    Shape shape;
    float buildSquareWidth, buildSquareHeight;
    boolean unlocked = false;

    // Initialise the build square, which is used as a cursor to drop items on the map.
    public Build() {
        this.buildSquarePos = new PVector(displayWidth/2, displayHeight/2);
        this.shape = new Rectangle2D.Float(buildSquarePos.x, buildSquarePos.y, buildSquareWidth, buildSquareHeight);
        this.buildSquareWidth = 30;
        this.buildSquareHeight = 30;
    }

    // Move the build square, in the same way as the player.
    public void moveBuildSquare(Facing direction) {
        switch (direction) {
            case UP:
                buildSquarePos.y -= buildSquareHeight;
                break;

            case LEFT:
                buildSquarePos.x -= buildSquareWidth;
                break;

            case DOWN:
                buildSquarePos.y += buildSquareHeight;
                break;

            case RIGHT:
                buildSquarePos.x += buildSquareWidth;
                break;
        }

        this.shape = new Rectangle2D.Float(buildSquarePos.x, buildSquarePos.y, buildSquareWidth, buildSquareHeight);
    }

    // Draw the build square.
    public void draw() {
        fill(255, 0, 0, 100);
        rect(buildSquarePos.x, buildSquarePos.y, buildSquareWidth, buildSquareHeight);
    }

    // Place an item on the map and charge the inn for it.
    public EnvironmentItem placeItem(int itemIndex) {
        float x = buildSquarePos.x;
        float y = buildSquarePos.y;
        EnvironmentItem item = null;
        int cost = 0;

        // Check that the location the item is being placed in is valid.
        if(!controller.checkPlacementLocation(this.shape))
            return item;

        // Switch based on the item being dropped.
        switch(itemIndex) {
            case 1:
                item = new Keg(x, y);
                cost = 50;
                break;
            
            case 2:
                item = new Chicken(x,y);
                cost = 25;
                break;
            
            case 3:
                item = new ChaliceTable(x,y);
                cost = 40;
                break;
            
            case 4:
                item = new CheeseBarrel(x,y);
                cost = 70;
                break;
            case 5:
                if(controller.gold.amount >= 500)
                    controller.chooseWorkerServe(x, y);
                break;
        }

        // Only allow the first two items to be placed if the others haven't been unlocked yet.
        if(this.unlocked == false && ((item instanceof ChaliceTable) || (item instanceof CheeseBarrel)))
            return null;

        // Purchase theitem if possible.
        if(controller.gold.buyItem(cost)) {
            return item;
        } else {
            return null;
        }
    }

    // Unlock items once enough gold has been accrued.
    public void unlockItems() {
        this.unlocked = true;
    }

}