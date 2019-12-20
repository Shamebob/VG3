/**
*   A character is an abstraction of a humanoid person in the game.
*/
public abstract class Character extends GameObject {
    PVector direction;

    public Character (float x, float y, Shape shape) {
        super(x, y, shape);
    }

    // Move the character in the specified direction, only if the move is not blocked.
    public void move(PVector change) {
        if(controller.checkMove(this.getPos(), change)) {
            this.pos.add(change);
        } else {
            this.pos.add(change.mult(-1));
        }
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    // Find a new direction for the character to move in.
    protected PVector findDirection() {
        float randomX = random(3, 5);
        float randomY = random(3, 5);

        if(round(random(0,1)) == 0) {
            randomX *= -1;
        }

        if(round(random(0,1)) == 0) {
            randomY *= -1;
        }

        return new PVector(randomX, randomY);
    }

    public PVector getDirection() {
        return this.direction.copy();
    }

    // Show the character's next position.
    public PVector getNextMove() {
        return(this.getPos().add(this.getDirection()));
    }

}
