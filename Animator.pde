public class Animator {
    public Animator() {
    }

    public void drawActiveGame(Controller controller) {
        controller.time.draw();
        controller.player.draw();
    }
}