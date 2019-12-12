import java.awt.geom.Rectangle2D;
import java.awt.geom.Ellipse2D;
import java.awt.Shape;

public abstract class GameObject {
    protected PVector pos;
    protected Shape shape;
    protected boolean active;

    public GameObject (float x, float y, Shape shape) {
        this.pos = new PVector(x,y);
        this.shape = shape;
    }

    Shape getShape() {
        return this.shape;
    }

    /**
    * Gets the bounds of the shape to use when checking for intersections.
    * @returns the 2D bounds of the shape, which provide a tight rectangle around the object.
    */
    Rectangle2D getShapeBounds() {
        return this.shape.getBounds2D();
    }

    PVector getPos() {
        // Make a copy so that no changes can be made to the character's values externally.
        return this.pos.copy();
    }

    void setPos(PVector pos) {
        this.pos = pos;
    }

    float getX() {
        return this.pos.x;
    }

    float getY() {
        return this.pos.y;
    }

    boolean isActive() {
        return this.active;
    }

    void destroy() {
        this.active = false;
    }

    abstract void draw();
}
