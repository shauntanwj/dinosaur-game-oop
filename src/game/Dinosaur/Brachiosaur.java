package game.Dinosaur;

import edu.monash.fit2099.engine.*;
import game.*;
import game.Action.FeedAction;
import game.Behaviour.Behaviour;
import game.Ground.Bush;
import game.Ground.Dirt;

import java.util.Random;

/**
 * @author shauntan, johanazlan
 * @version 1.1.0
 * @see game.Dinosaur
 */

/**
 * A herbivore dinosaur called Brachiosaur
 */
public class Brachiosaur extends Dinosaur {

    private Random rand = new Random();


    /**
     * Constructor for Brachiosaur, 'B' is the character that represents it
     * @param name the name of the Brachiosaur
     * @param cap  the Gender of the Brachiosaur
     */
    public Brachiosaur(String name, DinosaurCapability.Gender cap) {
        super(name, 'B', 160, 59, cap, 140, 60, 70, 15, 200);
    }

    /**
     * Constructor for Brachiosaur, 'B' is the character that represents it
     * @param name    the name of the Brachiosaur
     * @param is_baby boolean that indicate if the Brachiosaur is baby or not
     */
    public Brachiosaur(String name, boolean is_baby) {
        super(name, 'B', 160, 149, is_baby, 140, 60, 70, 15, 200);
    }

    /**
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return
     */
    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        CheckBehaviour.minusHitPoints(this, hitPoints);
        CheckBehaviour.minusThirstLevel(this);
        CheckBehaviour.checkAdult(this);
        destroyBush(map);
        CheckBehaviour.checkThirsty(this);
        CheckBehaviour.checkHungry(this, hitPoints);
        CheckBehaviour.checkBreeding(this, hitPoints);
        CheckBehaviour.checkUnconscious(this, map);

        if (this.hasCapability(DinosaurCapability.Gender.FEMALE)) {
            CheckBehaviour.checkLayEgg(this, map);
        }

        System.out.println(this.name + " Hitpoints: "+ hitPoints + " Thirst Level: " + this.getThirstLevel());

        if(!isConscious()){
            System.out.println(this.name + " Unconscious Turn:" +  this.getUnconsciousTurn());

        }
        if(isUnconsciousThirst()){
            System.out.println(this.name + " Unconscious due to thirst:" +  this.getUnconsciousDueToThirst());
        }

        if(this.hasCapability(DinosaurCapability.Pregnant.PREGNANT)){
            System.out.println(this.name + " Pregnant Turn:" +  this.getUnconsciousDueToThirst());

        }
        Behaviour b = this.getBehaviour().get(this.getBehaviour().size() - 1);
        Action action = b.getAction(this, map);

        return action;
    }

    /**
     * @param otherActor the Actor that might be performing attack
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return
     */

    @Override
    public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
        Actions actions = new Actions();
        actions.add(new FeedAction(this));
        return actions;

    }

    /**
     * This method to destroy the Bush that the Brachiosaur is currently stepping
     * @param map the game map
     */
    public void destroyBush(GameMap map) {
        // if current ground is instance of Bush
        if (map.locationOf(this).getGround() instanceof Bush) {
            // Have 50% chance of destroying Bush
            if (rand.nextInt(2) == 0) {
                map.locationOf(this).setGround(new Dirt());
            }
        }
    }

}
