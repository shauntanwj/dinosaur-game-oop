package game.Behaviour;

import edu.monash.fit2099.engine.*;
import game.Action.DeadAction;
import game.Dinosaur.*;

/**
 * @author shauntan, johanazlan
 * @version 1.1.0
 * @see game.Behaviour.WanderBehaviour
 * @see game.Behaviour.WalkToTreeBehaviour
 */

/**
 * A Behaviour when the Dinosaur is Unconscious
 */
public class UnconsciousBehaviour implements Behaviour {
    private Location actorLocation;

    /**
     * This method will print out which actor is currently unconscious and check if the Actor total
     * unconscious turn have read their dead turn if it has then it will return a DeadAction(),
     * otherwise it will return a DoNothingAction()
     * @param actor the Actor acting
     * @param map the GameMap containing the Actor
     * @return
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {

        actorLocation = map.locationOf(actor);
        System.out.println(actor + " at x:" + actorLocation.x() + ", y:" + actorLocation.y() + " is unconscious");

        Dinosaur dinosaur = (Dinosaur) actor;

        // if the dinosaur is unconscious due to thirst
        if(dinosaur.isUnconsciousThirst()){
            // if the total turn is 15 then return DeadAction
            if(dinosaur.getUnconsciousDueToThirst() == 5){
                return new DeadAction();
            }
            else{
                dinosaur.setUnconsciousDueToThirst(dinosaur.getUnconsciousDueToThirst() + 1);
                return new DoNothingAction();
            }
        }

        else{
            // if the total turn of unconscious equal to the dead turn of the dinosaur
            if(dinosaur.getUnconsciousTurn() == dinosaur.getDEAD_TURN()){
                return new DeadAction();
            }
            else{
                dinosaur.setUnconsciousTurn(dinosaur.getUnconsciousTurn() + 1);
                return new DoNothingAction();
            }
        }

    }
}
