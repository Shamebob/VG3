public class Build {
    EnvironmentItem[] purchaseItems = new EnvironmentItem[]{new Keg(0, 0), new Chicken(0, 0), new ChaliceTable(0, 0), new CheeseBarrel(0, 0)};
    PVector buildSquarePos;
    Shape shape;
    float buildSquareWidth, buildSquareHeight;

    public Build() {
        this.buildSquarePos = new PVector(displayWidth/2, displayHeight/2);
        this.shape = new Rectangle2D.Float(buildSquarePos.x, buildSquarePos.y, buildSquareWidth, buildSquareHeight);
        this.buildSquareWidth = 30;
        this.buildSquareHeight = 30;
    }

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

    public void draw() {
        fill(255, 0, 0, 100);
        rect(buildSquarePos.x, buildSquarePos.y, buildSquareWidth, buildSquareHeight);
    }

    public EnvironmentItem placeItem(int itemIndex) {
        float x = buildSquarePos.x;
        float y = buildSquarePos.y;
        EnvironmentItem item = null;
        int cost = 0;

        if(!controller.checkPlacementLocation(this.shape))
            return item;

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
                if(controller.gold.buyItem(500)) {
                    controller.workers.add(controller.spawner.spawnWorker(ItemType.BEER, x, y));
                }

        }

        if(controller.gold.buyItem(cost)) {
            return item;
        } else {
            return null;
        }
    }

}