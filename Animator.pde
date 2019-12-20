/**
* The Animator class is used to draw the game, and holds parts of the state it needs to know in order for the game to be completely drawn.
*/
public class Animator {
    float actionBarStartX, actionBarHeight, infoStartX, infoWidth, customerEmotionsWidth, customerEmotionsStartX;
    ItemType[] newCustomerLikes, newCustomerDislikes;
    PImage[] crests = new PImage[]{KNIGHT_CREST, WIZARD_CREST, ELF_CREST, ZOMBIE_CREST};
    PVector serverImagePos;
    float serverWidth, serverHeight;

    // Constructor for an animator, setup the information to draw the HUD
    public Animator() {
        this.actionBarStartX = displayWidth/4;
        this.actionBarHeight = displayHeight/10;
        this.infoWidth = displayWidth/15;
        this.infoStartX = displayWidth - this.infoWidth;
        this.customerEmotionsWidth = displayWidth/4 - this.infoWidth;
        this.customerEmotionsStartX = displayWidth - this.infoWidth - this.customerEmotionsWidth;
        this.newCustomerLikes = new ItemType[0];
        this.newCustomerDislikes = new ItemType[0];
    }

    // When a new customer reaches the inn display their likes and dislikes
    public void newCustomer(ItemType[] likes, ItemType[] dislikes) {
        this.newCustomerLikes = likes;
        this.newCustomerDislikes = dislikes;
    }

    // Draw an ongoing game.
    public void drawActiveGame(Controller controller) {
        // Draw each of the elements that are held in the gamestates and update them continously
        this.drawTimedBackground(controller.time);
        controller.inn.drawFloor();
        controller.player.draw();

        for(Worker worker: controller.workers) {
            worker.draw();
        }
        
        for(EnvironmentItem item : controller.items) {
            item.draw();
        }

        for(Customer customer: controller.customers) {
            customer.draw();
        }

        for(Feeling feeling: controller.feelings) {
            feeling.draw();
        }

        controller.inn.drawWalls();

        // Draw the HUD to give the player details about the state of the game.
        this.drawHUD(controller);
    }


    // End the day, display the summary screen and the build mode details
    public void endDay(Controller controller) {
        this.drawTimedBackground(controller.time);
        textSize(16);
        controller.inn.drawFloor();

        fill(255, 255, 255);
        textSize(56);
        text("Day " + controller.time.day + "\nGold: " + controller.gold.getAmount(), (displayWidth/2)-100, (displayHeight/2) - 100);


        for(EnvironmentItem item : controller.items) {
            item.draw();
        }

        for(Worker worker: controller.workers) {
            worker.draw();
        }

        controller.inn.drawWalls();
        this.drawHUD(controller);

        if(controller.buildMode)
            controller.build.draw();
    }

    // Draw the background and change to suit the time, IE: Dark when night time.
    private void drawTimedBackground(Time time) {
        if(time.hour < 20 && time.hour >= 8) {
            background(255, 255, 255);
        } else {
            background(0, 0, 0);
        }
    }

    // Draw the HUD, which is used to give the player information of the game state.
    private void drawHUD(Controller controller) {
        textSize(16);
        fill(101,67,33);
        rect(0, displayHeight - this.actionBarHeight, displayWidth, this.actionBarHeight);
        this.drawActionBar(controller);
        this.drawInfo(controller);
        this.drawCustomerEmotions(controller);
        this.drawPopularity(controller);
    }

    // Draw the popularity levels and faction icons for each of the factions.
    private void drawPopularity(Controller controller) {
        float popularityBoxWidth = (displayWidth/4)/4;
        float crestWidth = (popularityBoxWidth/2);
        float crestStartY = (displayHeight - this.actionBarHeight) + (this.actionBarHeight/4);
        float crestHeight = (this.actionBarHeight/2);
        float currentPoint = 0;
        int[] popularityLevels = controller.popularity.getPopularityLevels();

        for(int i = 0; i < this.crests.length; i++) {
            fill(139, 93, 46);
            rect(currentPoint, displayHeight - this.actionBarHeight, popularityBoxWidth, this.actionBarHeight);
            image(this.crests[i], currentPoint + (popularityBoxWidth/4), crestStartY, crestWidth, crestHeight);

            fill(0, 0, 0);
            text(popularityLevels[i], currentPoint + (popularityBoxWidth/2) - 5, displayHeight - 3);
            currentPoint += popularityBoxWidth;
        }
    }

    // Draw the action bar, either with inventory items in play mode or purchasable items to extend the inn in build mode.
    private void drawActionBar(Controller controller) {
        float actionBoxWidth = (displayWidth/2)/5;
        float currentPoint = this.actionBarStartX;
        for (int i = 0; i < 5; i++) {
            fill(139, 93, 46);
            rect(currentPoint, displayHeight - this.actionBarHeight, actionBoxWidth, this.actionBarHeight);
            fill(0, 0, 0);
            text(i + 1, currentPoint + 5, displayHeight - this.actionBarHeight + 20);
            currentPoint += actionBoxWidth;
        }

        if(controller.buildMode) {
            drawBuildItems(actionBoxWidth);
        } else {
            drawInventoryItems(actionBoxWidth);
        }
    }

    // Show the items that can be bought for the inn and their costs.
    private void drawBuildItems(float actionBoxWidth) {
        int counter = 0;
        int cost = 0;
        for(EnvironmentItem item : controller.build.purchaseItems) {
            cost = findItemCost(counter);
            text(cost + "g", item.getX(), item.getY() - 20);
            item.draw();
            counter+= 1;
        }

        cost = findItemCost(counter);
        text(cost + "g", serverImagePos.x, serverImagePos.y - 20);
        image(SERVER_DOWN_IDLE, this.serverImagePos.x, this.serverImagePos.y, 30, 40);
    }

    // Initialise the items to be shown during build mode, and set their positions on the screen.
    public void setupBuildItems() {
        float actionBoxWidth = (displayWidth/2)/5;
        PVector currentPos = new PVector(this.actionBarStartX + (actionBoxWidth/2), displayHeight - (actionBarHeight/2));
        PVector factorChange = new PVector(actionBoxWidth, 0);
        
        for(EnvironmentItem item : controller.build.purchaseItems) {
            item.setPos(currentPos.copy());
            currentPos = currentPos.add(factorChange);
        }

        this.serverImagePos = currentPos.copy();
        this.serverWidth = actionBoxWidth/2;
        this.serverHeight = this.actionBarHeight/2;
    }

    // Draw the items held in the user's inventory.s
    private void drawInventoryItems(float actionBoxWidth) {
        PVector currentPos = new PVector(this.actionBarStartX + (actionBoxWidth/2), displayHeight - (actionBarHeight/2));
        PVector factorChange = new PVector(actionBoxWidth, 0);
        for(EnvironmentItem item : controller.player.inventory) {
            item.setPos(currentPos.copy());
            item.draw();
            currentPos = currentPos.add(factorChange);
        }
    }

    // Display to the screen information on the day, the amount of gold held by the player and the time.
    private void drawInfo(Controller controller) {
        controller.time.draw();
        fill(139, 93, 46);
        rect(this.infoStartX, displayHeight - this.actionBarHeight, this.infoWidth, this.actionBarHeight);

        textSize(16);
        fill(0,0,0);
        if (controller.time.minute < 10) {
            text("Day " + controller.time.day + "\n" + controller.time.hour + ":0" + controller.time.minute + "\nGold: " + controller.gold.getAmount(), this.infoStartX + 5, displayHeight - this.actionBarHeight + 20);
        } else {
            text("Day " + controller.time.day + "\n" + controller.time.hour + ":" + controller.time.minute + "\nGold: " + controller.gold.getAmount(), this.infoStartX + 5, displayHeight - this.actionBarHeight + 20);
        }
    }

    // Show the newest customer's likes and dislikes so that the player knows what to serve.
    private void drawCustomerEmotions(Controller controller) {
        fill(255, 255, 255);
        rect(this.customerEmotionsStartX, displayHeight - this.actionBarHeight, this.customerEmotionsWidth, this.actionBarHeight);
        image(HAPPY, this.customerEmotionsStartX + (this.customerEmotionsWidth/4) - 10, displayHeight - (actionBarHeight - (this.actionBarHeight/10)), 20, 20);
        image(SAD, this.customerEmotionsStartX + (3 * (this.customerEmotionsWidth/4)) - 10, displayHeight - (actionBarHeight - (this.actionBarHeight/10)), 20, 20);

        float imageSpace = this.customerEmotionsWidth/4;
        float imageY = displayHeight - (this.actionBarHeight/2);
        float imageHeight = this.actionBarHeight/3;
        float currentPoint = this.customerEmotionsStartX + (imageSpace/4);
        float imageWidth = imageSpace/2;

        for(ItemType item : this.newCustomerLikes) {
            drawItemType(item, currentPoint, imageY, imageWidth, imageHeight);
            currentPoint += imageSpace;
        }

        currentPoint = this.customerEmotionsStartX + (this.customerEmotionsWidth/2) + (imageSpace/4);

        for(ItemType item : this.newCustomerDislikes) {
            drawItemType(item, currentPoint, imageY, imageWidth, imageHeight);
            currentPoint += imageSpace;
        }

    }

    // Draw the individual items that are being displayed. Needs a switch to avoid creating new objects every time.
    public void drawItemType(ItemType item, float x, float y, float width, float height) {
        PImage itemImage = null;

        switch (item) {
            case BEER:
                itemImage = BEER;
                break;
            
            case CHICKENLEG:
                itemImage = CHICKEN_LEG;
                break;

            case CHALICE:
                itemImage = CHALICE;
                break;

            case CHEESE:
                itemImage = CHEESE;
                break;
        }
    

        if(itemImage != null)
            image(itemImage, x, y, width, height);
    }

    // Finds the cost of an item, to be used when displaying in build mode.
    public int findItemCost(int index) {
        switch(index) {
            case 0:
                return 50;
            
            case 1:
                return 25;
            
            case 2:
                return 40;
            
            case 3:
                return 70;

            case 4:
                return 500;
        }

        return 0;
    } 

    // Show the end game screen when the game has over, accompanied by a success or failure message
    public void drawEndScreen() {
        background(139, 93, 46);
        controller.inn.drawFloor();
        controller.inn.drawWalls();
        controller.player.draw();
        fill(255, 255, 255);
        textSize(32);
        text(controller.displayMessage, (displayWidth/4), 50);
    }
}