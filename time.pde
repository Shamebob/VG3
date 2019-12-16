public class Time {
    int day, hour, minute, second;
    boolean dayOver;
    int spawnTimer, spawnCounter;

    public Time() {
        this.hour = 23;
        this.minute = 58;
        this.day = 1;
        this.second = 0;
        this.dayOver = false;
        this.spawnTimer = 120;
        this.spawnCounter = 0;
    }

    public void addMinute() {
        this.minute += 1;
        this.spawnCounter += 1;

        if(this.minute % 60 == 0) {
            this.addHour();
            this.minute = 0;
        }

        if(this.spawnCounter % spawnTimer == 0) {
            //TODO: Turn back on
            controller.newCustomer();
            this.spawnCounter = 0;
        }
    }

    public void setSpawnTimer(int spawnTimer) {
        this.spawnTimer = spawnTimer;
    }

    private void addHour() {
        this.hour += 1;

        if(this.hour % 24 == 0) {
            this.hour = 0;
        }

        if(this.hour == 0) {
            this.dayOver = true;
            controller.endDay = true;
        }
    }

    public void newDay() {
        this.day += 1;
        this.hour = 8;
        this.minute = 0;
        this.dayOver = false;
    }

    public void draw() {
        this.second += 5;
        if(this.second % 60 == 0 && !this.dayOver) {
            this.addMinute();
            this.second = 0;
        }
    }
}