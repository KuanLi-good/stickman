package stickman.model;

import stickman.levels.Level;

public interface GameEngine {
    Level getCurrentLevel();

    void startLevel();

    // Hero inputs - boolean for success (possibly for sound feedback)
    boolean jump();
    boolean moveLeft();
    boolean moveRight();
    boolean stopMoving();

    void tick();

    int getCurrentLevelNumber();
    void notifyScoreObserver(int score);
    void notifyBloodObserver();
    boolean dead();
    void save();
    void load();
    int bloodLeft();

    void setCurrentLevelNumber(int number);
    void setCurrentLevel(Level level);
    void setBlood(int blood);
    ObserverSingleton getObserver();
}
