package game.Behaviour;

import edu.monash.fit2099.engine.*;
import game.Distance;

/**
 * @author shauntan, johanazlan
 * @version 1.0.0
 * @see game.Behaviour.HungryBehaviour
 */

/**
 * A Behaviour that will move one step closer to a target location
 */
public class FollowLocationBehaviour implements Behaviour {


    /**
     * the target location
     */
    private Location targetLocation;

    /**
     * Constructor for FollowLocationBehaviour
     * @param targetLocation
     */
    public FollowLocationBehaviour(Location targetLocation) {
            this.targetLocation = targetLocation;
        }

    /**
     *
     * @param actor the Actor acting
     * @param map the GameMap containing the Actor
     * @return
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        if(!map.contains(actor))
            return null;

        Location here = map.locationOf(actor);
        Location there = targetLocation;

        int currentDistance = Distance.distance(here, there);
        for (Exit exit : here.getExits()) {
            Location destination = exit.getDestination();
            if (destination.canActorEnter(actor)) {
                int newDistance = Distance.distance(destination, there);
                if (newDistance < currentDistance) {
                    return new MoveActorAction(destination, exit.getName());
                }
            }
        }

        return new WanderBehaviour().getAction(actor, map);
    }
}

