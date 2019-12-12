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
        this.second += 5;
        if(this.second % 60 == 0) {
            this.addMinute();
            this.second = 0;
        }
    }
}