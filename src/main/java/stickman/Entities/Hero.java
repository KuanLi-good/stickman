package stickman.Entities;

import stickman.Entities.HeroPathState.HeroPathStateContext;

public class Hero extends Entity {

    private HeroPathStateContext pathContext;

    public Hero(double xPos, double yPos, HeroPathStateContext.Actor character) {
        pathContext = new HeroPathStateContext(character, xPos);
        this.xPos = xPos;
        this.yPos = yPos;
        this.xVel = 1;
        layer = Layer.FOREGROUND;
    }

    /*
    @Override
    public double getYPos() {
        this.yPos += this.yVel;
        return yPos;
    }

     */

    @Override
    public String getImagePath() {
        return pathContext.getPath(xPos);
    }

    @Override
    public Entity newInstance() {

        return new Hero(this.xPos, this.yPos, HeroPathStateContext.Actor.DEFAULT);
    }

    @Override
    public void setXVel(double xVel) {
        // Do nothing as the Hero's velocity is constant.
    }
}