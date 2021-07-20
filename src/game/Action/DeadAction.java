package game.Action;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;
import game.Dinosaur.Allosaurs;
import game.Dinosaur.Brachiosaur;
import game.Dinosaur.Pterodactyl;
import game.Dinosaur.Stegosaur;
import game.Item.Corpse;

/**
 * @author shauntan, johanazlan
 * @version 1.0.0
 * @see game.Action.EatAction
 */

/**
 * A class that represent an action when a Actor is dead
 */
public class DeadAction extends Action {

    /**
     * This function will add a Corpse to the current location of the actor with the appropriate
     * remain time and then remove the actor from the location
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        Location actorLocation = map.locationOf(actor);

        // if instance of Stegosaur then the corpse remain time is 20
        if(actor instanceof Stegosaur){
            Stegosaur stegosaur = (Stegosaur) actor;
            // add the corpse to the current location
            map.at(actorLocation.x(), actorLocation.y()).addItem(new Corpse(stegosaur, 20));
            // remove the actor from the map
            map.removeActor(stegosaur);
        }

        // if instance of Brachiosaur then the corpse remain time is 40
        else if(actor instanceof Brachiosaur){
            Brachiosaur brachiosaur = (Brachiosaur) actor;
            map.at(actorLocation.x(), actorLocation.y()).addItem(new Corpse(brachiosaur, 40));
            map.removeActor(brachiosaur);
        }

        // if instance of Allosaurs then the corpse remain time is 20
        else if(actor instanceof Allosaurs){
            Allosaurs allosaurs = (Allosaurs) actor;
            map.at(actorLocation.x(), actorLocation.y()).addItem(new Corpse(allosaurs, 20));
            map.removeActor(allosaurs);
        }

        // if instance of Pterodactyl then the corpse remain time is 20
        else if(actor instanceof Pterodactyl){
            Pterodactyl pterodactyl = (Pterodactyl) actor;
            map.at(actorLocation.x(), actorLocation.y()).addItem(new Corpse(pterodactyl, 20));
            map.removeActor(pterodactyl);
        }

        return  actor + " at x:" + actorLocation.x() + ", y:" + actorLocation.y() + " is dead";
    }

    @Override
    public String menuDescription(Actor actor) {
        return "";
    }
}
