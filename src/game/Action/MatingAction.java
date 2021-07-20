package game.Action;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import game.DinosaurCapability;

/**
 * @author shauntan, johanazlan
 * @version 1.1.0
 * @see game.Action.PickUpTreeBushAction
 */

/**
 * A class represents an action when two Dinosaur are mating
 */
public class MatingAction extends Action {

    /**
     * the target of the Dinosaur to mate with
     */
    private Actor target;

    /**
     * Constructor
     * @param target the target of the Dinosaur to mate with
     */
    public MatingAction(Actor target){
        this.target = target;
    }

    /**
     * This function will add a capability of Pregnant to the Dinosaur if it successfully mate,
     * if the target is already pregnant then it will return a message for it
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        // check if the target is not pregnant
        if (target.hasCapability(DinosaurCapability.Pregnant.PREGNANT) == false) {
            // add the Enum Pregnant to the target
            target.addCapability(DinosaurCapability.Pregnant.PREGNANT);
            return (target + " at x:" + map.locationOf(target).x() + ", y:" + map.locationOf(target).y() + " successfully pregnant");
        }
        return target + " at x:" + map.locationOf(target).x() + ", y:" + map.locationOf(target).y() +  " is already pregnant";


    }

    @Override
    public String menuDescription(Actor actor) {
        return ("");
    }
}
