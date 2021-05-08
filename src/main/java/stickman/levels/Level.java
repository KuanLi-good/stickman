package stickman.levels;
import stickman.Entities.Entity;
import stickman.view.BackgroundDrawer;

import java.util.List;

public interface Level {
    List<Entity> getEntities();
    BackgroundDrawer getBGDrawer();
    double getHeight();
    double getWidth();
    void tick();
    double getFloorHeight();
    Entity getHero();
    double getHeroX();
    double getHeroY();
    void setHeroX(double xPos);
    boolean jump();
    boolean shoot();
    boolean moveLeft();
    boolean moveRight();
    boolean stopMoving();
    boolean levelLost();
    boolean levelWon();
    void setFinal();
    boolean isFinal();


    int getScore();
    int getFinalScore();
    String timeUsed();
    boolean dead();
    void setDead();


    int getJumpCount();

    boolean canShoot();

    void setParameters(boolean levelLost, boolean levelWon, boolean isFinal,
                       boolean heroCanShoot, int jumpCount, int score, boolean dead );

    Level replicate();
    void setTimeUsed(String timeUsed);


}
