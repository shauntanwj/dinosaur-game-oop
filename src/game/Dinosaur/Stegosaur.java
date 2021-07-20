package game.Dinosaur;


import edu.monash.fit2099.engine.*;
import game.*;
import game.Action.AttackAction;
import game.Action.FeedAction;
import game.Behaviour.Behaviour;

/**
 * @author shauntan, johanazlan
 * @version 1.1.0
 * @see game.Ground
 */

/**
 * A herbivorous dinosaur called Stegosaur
 *
 */
public class Stegosaur extends Dinosaur {

	/**
	 * Constructor for Stegosaur, 'S' is the character that represents it
	 * @param name the name of the Stegosaur
	 * @param cap the Gender of the Stegosaur
	 */
	public Stegosaur(String name, DinosaurCapability.Gender cap) {
		super(name, 'S', 100, 49, cap, 90, 20, 50, 20, 100);
	}

	/**
	 * Constructor for Stegosaur, 'S' is the character that represents it
	 * @param name the name of the Stegosaur
	 * @param is_baby boolean that indicate if the Stegosaur is baby or not
	 */
	public Stegosaur(String name, boolean is_baby){
		super(name, 'S', 100, 89, is_baby, 90, 20, 50, 20, 100);
	}


	/**
	 *
	 * @param otherActor the Actor that might be performing attack
	 * @param direction  String representing the direction of the other Actor
	 * @param map current GameMap
	 * @return
	 */
	@Override
	public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
		Actions actions = new Actions();
		actions.add(new AttackAction(this));
		actions.add(new FeedAction(this));
		return actions;

	}

	/**
	 *
	 * @param actions collection of possible Actions for this Actor
	 * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
	 * @param map the map containing the Actor
	 * @param display the I/O object to which messages may be written
	 * @return
	 */
	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {

		CheckBehaviour.minusThirstLevel(this);
		CheckBehaviour.minusHitPoints(this, hitPoints);
		CheckBehaviour.checkAdult(this);
		CheckBehaviour.checkThirsty(this);
		CheckBehaviour.checkHungry(this, hitPoints);
		CheckBehaviour.checkBreeding(this, hitPoints);
		CheckBehaviour.checkUnconscious(this, map);

		if(this.hasCapability(DinosaurCapability.Gender.FEMALE)){
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
		Behaviour b = this.getBehaviour().get(this.getBehaviour().size()-1);
		Action action = b.getAction(this, map);
		return action;
	}



}
