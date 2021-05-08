package stickman.levels;

import stickman.Entities.*;
import stickman.view.BackgroundDrawer;
import java.util.ArrayList;
import java.util.List;

import java.time.Duration;
import java.time.Instant;



public class TargetTimeAdaptor extends LevelImp implements Level  {

    private int targetTime;
    private Instant start;
    private String timeUsed;

    public TargetTimeAdaptor(List<Entity> entities, Entity hero, BackgroundDrawer bg_drawer, int targetTime){
        super(entities, hero, bg_drawer);
        this.targetTime = targetTime;
        start = Instant.now();
    }


    @Override
    public int getFinalScore(){
        Duration timeElapsed = Duration.between(start, Instant.now());
        int finalScore = this.getScore() + (int)(targetTime - timeElapsed.toSeconds());

        if (finalScore > 0 ){
            return finalScore;
        }else {
            return 0;
        }
    }

    @Override
    public String timeUsed(){

        if (this.dead()) return timeUsed;
        else {
            timeUsed = String.valueOf(Duration.between(start, Instant.now()).toSeconds());
            return timeUsed;
        }
    }
    @Override
    public void setTimeUsed(String timeUsed){
        this.timeUsed = timeUsed;
    }


    @Override
    public Level replicate(){
        List<Entity> newEntities = new ArrayList<>();
        Entity newHero = this.getHero().newInstance();
        newHero.setHeight(this.getHero().getHeight());
        newHero.setWidth(this.getHero().getWidth());
        for (Entity entity : this.getEntities()) {
            if (entity == this.getHero()) {
                continue;
            } else {
                newEntities.add(entity.newInstance());
            }
        }
        newEntities.add(newHero);
        Level newLevel = new TargetTimeAdaptor(newEntities, newHero, this.getBGDrawer(), targetTime);
        newLevel.setParameters(this.levelLost(), this.levelWon(), this.isFinal(),
                this.canShoot(), this.getJumpCount(), this.getScore(), this.dead());
        newLevel.setTimeUsed(timeUsed);

        return newLevel;
    }

}