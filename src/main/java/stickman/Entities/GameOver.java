package stickman.Entities;

public class GameOver extends Entity {

    private static final String imagePath = "/gameover.png";

    public GameOver (double xPos, double yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.layer = Layer.FOREGROUND;
    }

    @Override
    public String getImagePath() {
        return imagePath;
    }

    @Override
    public Entity newInstance() {
        return new GameOver(this.getXPos(),this.getYPos());
    }

}
