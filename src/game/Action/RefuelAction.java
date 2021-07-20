package game.Action;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import game.Dinosaur.Pterodactyl;
import game.DinosaurCapability;

/**
 * @author shauntan, johanazlan
 * @version 1.0.0
 * @see game.Behaviour.Behaviour
 */

/**
 * A class that represent an action that allows the flying dinosaur, Pterodactyl to fly again
 */
public class RefuelAction extends Action {
    @Override
    public String execute(Actor actor, GameMap map) {
        Pterodactyl pterodactyl = (Pterodactyl) actor;

        // if the Pterodactyl currently is walking, then remove the walking capability and set the flying turn to 0
        if(pterodactyl.hasCapability(DinosaurCapability.Status.WALKING)){
            pterodactyl.removeCapability(DinosaurCapability.Status.WALKING);
            pterodactyl.setFlyingTurn(0);
        }
        return actor + " has rested and can fly again";
    }

    @Override
    public String menuDescription(Actor actor) {
        return "";
    }
}
