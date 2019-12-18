enum Emotion {
    HAPPY, SAD;
}

public class Feeling extends Character {
    int drawCounter;
    PImage drawing;

    public Feeling(float x, float y, Emotion currentFeeling) {
        super(x, y, null);
        this.drawCounter = 0;

        if(currentFeeling == Emotion.HAPPY) {
            this.drawing = HAPPY;
        } else {
            this.drawing = SAD;
        }
    }

    public void draw() {
        this.drawCounter += 1;

        if(this.drawCounter % 10 == 0) {
            super.move(new PVector(0, -10));
        }

        
        image(this.drawing, this.getX(), this.getY(), 15, 15);

        if(this.drawCounter == 30) {
            this.destroy();
        }
    }
}