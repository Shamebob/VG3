public class Animator {
    float actionBarStartX, actionBarHeight, infoStartX, infoWidth, customerEmotionsWidth, customerEmotionsStartX;
    ItemType[] newCustomerLikes, newCustomerDislikes;
    PImage[] crests = new PImage[]{KNIGHT_CREST, WIZARD_CREST, ELF_CREST, ZOMBIE_CREST};
    PVector serverImagePos;
    float serverWidth, serverHeight;

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

    public void newCustomer(ItemType[] likes, ItemType[] dislikes) {
        this.newCustomerLikes = likes;
        this.newCustomerDislikes = dislikes;
    }

    public void drawActiveGame(Controller controller) {
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
        this.drawHUD(controller);
    }


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

    private void drawTimedBackground(Time time) {
        if(time.hour < 20 && time.hour >= 8) {
            background(255, 255, 255);
        } else {
            background(0, 0, 0);
        }
    }

    private void drawHUD(Controller controller) {
        textSize(16);
        fill(101,67,33);
        rect(0, displayHeight - this.actionBarHeight, displayWidth, this.actionBarHeight);
        this.drawActionBar(controller);
        this.drawInfo(controller);
        this.drawCustomerEmotions(controller);
        this.drawPopularity(controller);
    }

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

    private void drawInventoryItems(float actionBoxWidth) {
        PVector currentPos = new PVector(this.actionBarStartX + (actionBoxWidth/2), displayHeight - (actionBarHeight/2));
        PVector factorChange = new PVector(actionBoxWidth, 0);
        for(EnvironmentItem item : controller.player.inventory) {
            item.setPos(currentPos.copy());
            item.draw();
            currentPos = currentPos.add(factorChange);
        }
    }
    
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