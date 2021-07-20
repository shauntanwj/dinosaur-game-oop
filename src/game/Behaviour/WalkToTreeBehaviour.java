package game.Behaviour;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;
import game.Action.RefuelAction;
import game.Dinosaur.Pterodactyl;
import game.Ground.Tree;

/**
 * @author shauntan, johanazlan
 * @version 1.0.0
 * @see game.Behaviour.WanderBehaviour
 */

/**
 * A class which represent a Behaviour when a Dinosaur is walking to a tree.
 */
public class WalkToTreeBehaviour implements Behaviour{

    @Override
    public Action getAction(Actor actor, GameMap map) {
        // get the location of the actor, if instance tree return the RefuelAction
        Location actorLocation = map.locationOf(actor);
        if(actorLocation.getGround() instanceof Tree){
            return new RefuelAction();
        }

        // else go to the nearest unoccupied tree
        else{
            return Pterodactyl.findNearestUnoccupiedTree(actor, map);
        }
    }
}
