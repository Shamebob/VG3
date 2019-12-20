enum Emotion {
    HAPPY, SAD, LEAVING;
}

// THe feeling class is used to express what the characters in the game are doing.
public class Feeling extends Character {
    int drawCounter;
    PImage drawing;

    // Construct a feeling, setting the image to whatever the feeling is.
    public Feeling(float x, float y, Emotion currentFeeling) {
        super(x, y, null);
        this.drawCounter = 0;

        if(currentFeeling == Emotion.HAPPY) {
            this.drawing = HAPPY;
        } else if (currentFeeling == Emotion.SAD) {
            this.drawing = SAD;
        } else if(currentFeeling == Emotion.LEAVING) {
            this.drawing = LEAVING;
        }
    }

    // Draw the feeling, moving it and destroying it after a few seconds.
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