/**
* A chalice table is used as an environment item that be used to pickup chalices. This is an abstraction of that.
*/
public class ChaliceTable extends EnvironmentItem {

    public ChaliceTable(float x, float y) {
        super(x, y, ((Shape) new Ellipse2D.Float(x, y, 15, 15)), 20);
    }

    public void draw() {
        image(CHALICE_TABLE, this.getX(), this.getY(), 30, 30);
    }
}