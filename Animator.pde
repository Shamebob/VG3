public class Animator {
    float actionBarStartX, actionBarHeight, infoStartX, infoWidth;

    public Animator() {
        this.actionBarStartX = displayWidth/4;
        this.actionBarHeight = displayHeight/10;
        this.infoWidth = displayWidth/15;
        this.infoStartX = displayWidth - this.infoWidth;
    }

    public void drawActiveGame(Controller controller) {
        this.drawTimedBackground(controller.time);
        this.drawHUD(controller);
        controller.inn.draw();
        controller.player.draw();

        for(EnvironmentItem item : controller.items) {
            item.draw();
        }

        for(Customer customer: controller.customers) {
            customer.draw();
        }
    }

    private void drawTimedBackground(Time time) {
        if(time.hour < 20 && time.hour >= 8) {
            background(255, 255, 255);
        } else {
            background(0, 0, 0);
        }
    }

    private void drawHUD(Controller controller) {
        fill(101,67,33);
        rect(0, displayHeight - this.actionBarHeight, displayWidth, this.actionBarHeight);
        this.drawActionBar(controller);
        this.drawInfo(controller);
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

        PVector currentPos = new PVector(this.actionBarStartX + (actionBoxWidth/2), displayHeight - (actionBarHeight/3));
        PVector factorChange = new PVector(actionBoxWidth, 0);
        for(EnvironmentItem item : controller.player.inventory) {
            item.setPos(currentPos);
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
}