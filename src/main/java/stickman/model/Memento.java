package stickman.model;
import java.util.Stack;
import stickman.levels.Level;

public class Memento {

    private GameEngine model;
    private Stack<StickmanHistory> mementoStack = new Stack<>();

    public Memento(GameEngine model){
        this.model = model; // originator
    }
    // care taker
    public void save(){
        StickmanHistory newSave = new StickmanHistory(model.getCurrentLevel().replicate(), model.getCurrentLevelNumber(),model.bloodLeft());
        mementoStack.push(newSave);
    }


    public void load(){
        if (!mementoStack.empty()) {
            StickmanHistory newLoad = mementoStack.pop();
            model.setCurrentLevel(newLoad.getLevel());
            model.setBlood(newLoad.getBlood());
            model.setCurrentLevelNumber(newLoad.getCurrentLevelNumber());
        }
    }

    class StickmanHistory{
        private Level level;
        private int blood;
        private int currentLevelNumber;

        public StickmanHistory(Level level, int currentLevelNumber, int blood){
            this.blood = blood;
            this.currentLevelNumber = currentLevelNumber;
            this.level = level;
        }

        public Level getLevel(){
            return level;
        }

        public int getBlood(){
            return blood;
        }

        public int getCurrentLevelNumber() {
            return currentLevelNumber;
        }
    }


}