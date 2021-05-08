
A fun little stickman game build upon JavaFX. Some design patterns are interpreted.

gradle run the project will automatic generate the game window.

-- Keyboard Control:
A - move left
D - move right
W - jump
SPACE - shoot

The game window could be too big to see where the player at when first start. You can jump(W) once to trigger the camera.

The game terminates when you hit the flag, a system.out.print message is given.

slimes move in their own pattern, they will not stay on the screen once they hit the margin.

Once the player hits the slime, it will return to start location.

The bullet can be only shoot to the right, I haven't right code to handle it shooting both ways.

-- JSON:
1] only xPos of the player can be preset, since we always assume the player standing on the ground.
2] level is named "Levelx" with capital L.
3] The layout of levels is represented in char, where
' ' - floor,
'0' - null,
'1' - brick,
'2' - mushroom,
'f' - flag,
'b' - blue slime,
'r' - red slime,
and so on.
4] player can have two sizes "normal" and "large", which will trigger the size modification shown in the game window.
5] more specs can be written but cannot be processed.


The ReadJSON file is preset to read Level1 from the beginning and read next level (Level2) when it is called for the second time.
At this stage, you can change the 'currentLevel' in ReadJSON.java to load a different level.



-- Level Transition: relates to GameEngine, no new class is created
(No design pattern is used)

Once the stickman hit the flag, and there is more than one level defined in the configuration json file, the stickman will be automatically transfer to the next level. 
If the current level is the final level defined in configuration file, You win banner will be shown after hitting the flag.



-- Score and Time: relates to class TargetTimeAdaptor, ObserverSingleton, Level, GameEngine, GameWindow 
(using design pattern Adaptor, Observer, Singleton)

The score and time is the attributes of each level. Instead, the blood is constant throughout the whole game. They will all be displayed on the top left corner. 

The time used is continuously updating, tells you the time elapse since starting this level.

The score is affected by mushroom you ate (50 points); 
the slimes you shot (100 points); 
and the target time defined in the json file (over-time: minus, within time: plus)

The blood is lost if the stickman is fallen or hit by a slime. 
The stickman will be automatically sent back to its starting point and restart this level.
When you lose a life, time and score will restart counting from 0, and all entities you destroyed will be restored.
Final score for each level is updated after you proceed to the next level.

If all bloods are lost, a game over banner will display, the screen is frozen, and no commands can be proceeded.
Total score counting up all levels will be shown at terminal at the time game over banner or you win banner is displayed.

*** You have to eat the mushroom to be able to shoot for each level. 



-- Save and Load, relates to class Memento, StickmanHistory, Level, GameEngine 
(Using design pattern Prototype, Memento. But only Memento is implemented , since Prototype is given)

On your Keyboard:

S - save current state
L - load latest state

You can save multiple times, but only the latest save can be loaded. 
After retrieve the latest save, it will be automatically deleted. 
Next load will load the second last save you added.
