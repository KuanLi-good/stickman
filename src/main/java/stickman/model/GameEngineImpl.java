package stickman.model;
import stickman.levels.*;
import org.json.simple.JSONObject;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import stickman.Entities.*;

public class GameEngineImpl implements GameEngine {
    Level currentLevel;
    int currentLevelNumber;
    LevelDirector levelDirector;
    JSONObject configuration;

    int blood;
    ObserverSingleton observer;
    Memento memento;


    public GameEngineImpl(JSONObject configuration) {
        this.configuration = configuration;
        this.currentLevelNumber = 1;
        observer = ObserverSingleton.getInstance();
        blood = ((Long)configuration.get("blood")).intValue();
        startLevel();
        observer.setModel(this);
        observer.refreshBloodInfo(blood);
        memento = new Memento(this);
    }

    private void loadLevel(int levelNumber) {
        JSONObject levels = (JSONObject) configuration.get("levels");

        String key = String.valueOf(levelNumber);
        JSONObject level = (JSONObject) levels.get(key);

        if (level != null) {
            levelDirector = new LevelDirector(new DefaultLevelBuilder(level));
            currentLevel = levelDirector.construct();
            notifyScoreObserver(0);
        }
        int keyNum = Integer.parseInt(key) + 1;
        level = (JSONObject) levels.get(Integer.toString(keyNum));

        if (level == null) {
            currentLevel.setFinal();
        }
    }

    @Override
    public Level getCurrentLevel() {
        return currentLevel;
    }

    @Override
    public void startLevel() {
        loadLevel(this.currentLevelNumber);
    }

    @Override
    public boolean jump() {
        return currentLevel.jump();
    }

    @Override
    public boolean moveLeft() {
        currentLevel.moveLeft();
        return false;
    }

    @Override
    public boolean moveRight() {
        currentLevel.moveRight();
        return false;
    }

    @Override
    public boolean stopMoving() {
        currentLevel.stopMoving();
        return false;
    }

    @Override
    public void tick() {

        if (observer.getScore(currentLevelNumber) != currentLevel.getScore()) {
            notifyScoreObserver(currentLevel.getScore());
        }

            if (currentLevel.levelLost() && !currentLevel.dead()) {
                notifyBloodObserver();
                if (!dead()) {
                    startLevel();
                } else {
                    int totalScore = 0;
                    for (int i = 1; i < currentLevelNumber; i ++){
                        totalScore += observer.getScore(i);
                    }
                    totalScore += currentLevel.getFinalScore();
                    System.out.println("Final score: " + totalScore);
                    Entity youLose = new GameOver(currentLevel.getHeroX() - 300, -500);
                    currentLevel.getEntities().add(youLose);
                    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(17),
                            t -> {
                                youLose.setYPos(youLose.getYPos() + 2);
                            }));

                    timeline.setCycleCount(250);
                    timeline.play();
                    currentLevel.setDead();
//                    System.out.println("GAMEOVER");
                    return;
                }
            }
        if (currentLevel.levelWon()) {
            if (currentLevel.isFinal()) {
                int totalScore = 0;
                for (int i = 1; i < currentLevelNumber; i ++){
                    totalScore += observer.getScore(i);
                }
                totalScore += currentLevel.getFinalScore();
                System.out.println("Final score: " + totalScore);
                return;
            }
            notifyScoreObserver(currentLevel.getFinalScore());
            currentLevelNumber += 1;
            startLevel();
        }
        currentLevel.tick();
        observer.tick();

    }


    @Override
    public void notifyScoreObserver(int score) {
        observer.refreshScoreInfo(currentLevelNumber, score);
    }

    @Override
    public int getCurrentLevelNumber() {
        return currentLevelNumber;
    }

    @Override
    public int bloodLeft(){
        return blood;
    }

    @Override
    public void notifyBloodObserver() {
        blood -= 1;
        observer.refreshBloodInfo(blood);
    }

    @Override
    public boolean dead() {
        return observer.dead();
    }

    @Override
    public void save() {
        memento.save();
    }

    @Override
    public void load() {
        memento.load();
    }

    @Override
    public void setCurrentLevel(Level currentLevel) {
        this.currentLevel = currentLevel;
    }
    @Override
    public void setCurrentLevelNumber(int currentLevelNumber) {
        this.currentLevelNumber = currentLevelNumber;
    }
    @Override
    public ObserverSingleton getObserver() {
        return observer;
    }
    @Override
    public void setBlood(int blood){
        this.blood = blood;
    }
}