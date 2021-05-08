package stickman.model;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;


public class ObserverSingleton {
    private static ObserverSingleton instance;
    private Map<Integer, Integer> scores = new HashMap<>();
    private int blood;


    private Pane root;
    private GameEngine model;
    private Text scoreOnScreen = new Text(0,10,"");

    private ObserverSingleton(){}

    public static ObserverSingleton getInstance() {
        if (instance == null) {
            instance = new ObserverSingleton();
        }
        return instance;
    }


    public void setModel(GameEngine model){
        this.model = model;
    }

    public void setRoot(Pane root){
        this.root = root;
        root.getChildren().add(scoreOnScreen);
    }


    public void refreshScoreInfo(int currentLevelNumber, int score){
        if (scores.containsKey(currentLevelNumber + 1)){
            int i = 1;
            while(scores.containsKey(currentLevelNumber + i)){
                scores.remove(currentLevelNumber + i);
                i ++;
            }
        }
        scores.put(currentLevelNumber, score);
    }

    public void refreshBloodInfo(int blood){
        this.blood = blood;
    }


    public void tick(){
        scoreOnScreen.setText( timeInfo() + bloodInfo() + "\n"+ scoreInfo());
    }


    private String scoreInfo(){
        String scoreInfo = "Score Count : \n";
        for(int i = 1; i <= model.getCurrentLevelNumber(); i++){
            scoreInfo += "Level  " + i + " : " + scores.get(i) + "\n";
        }
        return scoreInfo;
    }

    private String bloodInfo(){
        String bloodInfo = "Blood Left: " + blood + "\n";
        return bloodInfo;
    }

    private String timeInfo(){
        String timeInfo = "Time Spent: " + model.getCurrentLevel().timeUsed() + "\n";
        return timeInfo;
    }

    public int getScore(int levelNumber){
        return scores.get(levelNumber);
    }

    public boolean dead() {
        if (blood <= 0) {
            blood = 0;
            return true;
        }
        return false;
    }


}