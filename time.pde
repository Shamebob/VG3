public class Time {
    int day, hour, minute, second;
    boolean dayOver;

    public Time() {
        this.hour = 8;
        this.minute = 0;
        this.day = 1;
        this.second = 0;
        this.dayOver = false;
    }

    public void addMinute() {
        this.minute += 1;
        if(this.minute % 60 == 0) {
            this.addHour();
            this.minute = 0;
        }
    }

    private void addHour() {
        this.hour += 1;

        if(this.hour % 24 == 0) {
            this.hour = 0;
        }

        if(this.hour == 2) {
            this.dayOver = true;
        }
    }

    public void newDay() {
        this.day += 1;
        this.hour = 8;
        this.minute = 0;
    }

    public void draw() {
        this.second += 1;
        if(this.second % 60 == 0) {
            this.addMinute();
            this.second = 0;
        }

        this.drawBackground();
        textSize(16);
        fill(0,255,0);
        if (this.minute < 10) {
            text("Day " + this.day + "\n" + this.hour + ":0" + this.minute, 0, 20);
        } else {
            text("Day " + this.day + "\n" + this.hour + ":" + this.minute, 0, 20);
        }
    }

    private void drawBackground() {
        if(this.hour < 20 && this.hour >= 8) {
            background(255, 255, 255);
        } else {
            background(0, 0, 0);
        }
    }
}