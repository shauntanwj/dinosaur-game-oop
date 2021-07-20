package game.Dinosaur;

import edu.monash.fit2099.engine.*;
import game.Action.AttackAction;
import game.Action.FeedAction;
import game.Behaviour.Behaviour;
import game.Behaviour.FollowLocationBehaviour;
import game.Behaviour.WalkToTreeBehaviour;
import game.Behaviour.WanderBehaviour;
import game.CheckBehaviour;
import game.DinosaurCapability;
import game.Distance;
import game.Ground.Tree;

/**
 * A flying Dinosaur that called Pterodactyl
 */
public class Pterodactyl extends Dinosaur {

    /**
     * the total flying turn of the Pterodactyl
     */
    private int flyingTurn = 0;

    /**
     * Constructor of the Pterodactyl, 'P' is the character that represents it
     * @param name the name of the Pterodactyl
     * @param cap the gender of the Pterodactyl
     */
    public Pterodactyl(String name, DinosaurCapability.Gender cap) {
        // change hurt to 49
        super(name, 'P', 100, 49, cap, 90, 20, 50, 20, 100);

    }

    /**
     * Constructor of the Pterodactyl, 'P' is the character that represents it
     * @param name the name of the Pterodactyl
     * @param is_baby the boolean to indicate if the Pterodactyl is baby or not
     */
    public Pterodactyl(String name, boolean is_baby) {
        super(name, 'P', 100, 89, is_baby, 90, 20, 50, 20, 100);
    }

    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {

//        CheckBehaviour.minusHitPoints(this, hitPoints);
//        CheckBehaviour.minusThirstLevel(this);
        CheckBehaviour.checkAdult(this);
        CheckBehaviour.checkThirsty(this);
        CheckBehaviour.checkHungry(this, hitPoints);
        CheckBehaviour.checkBreeding(this, hitPoints);
        checkFlying(map);
        CheckBehaviour.checkUnconscious(this, map);

        if(this.hasCapability(DinosaurCapability.Gender.FEMALE)){
            CheckBehaviour.checkLayEgg(this, map);
        }
        System.out.println(this.name + " Hitpoints: "+ hitPoints + " Thirst Level: " + this.getThirstLevel() + " Flying Turn: " + flyingTurn);

        if(!isConscious()){
            System.out.println(this.name + " Unconscious Turn:" +  this.getUnconsciousTurn());

        }
        if(isUnconsciousThirst()){
            System.out.println(this.name + " Unconscious due to thirst:" +  this.getUnconsciousDueToThirst());
        }

        if(this.hasCapability(DinosaurCapability.Pregnant.PREGNANT)){
            System.out.println(this.name + " Pregnant Turn:" +  this.getUnconsciousDueToThirst());

        }

        if(this.hasCapability(DinosaurCapability.Status.FLYING)){
            System.out.println(this + " is flying");
        }
        else if(this.hasCapability(DinosaurCapability.Status.WALKING)){
            System.out.println(this + " is walking to Tree");
        }

        Behaviour b = this.getBehaviour().get(this.getBehaviour().size()-1);
        Action action = b.getAction(this, map);
        return action;
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
     * This method is is to check if the Pterodactyl have reach it's limit of flying
     */
    public void checkFlying(GameMap map){
        boolean containWalkToTreeBehaviour = this.getBehaviour().stream().anyMatch(c -> c instanceof WalkToTreeBehaviour);

        // if Pterodactyl is conscious and flying turn is zero and dont have the capability to fly and have the walk to tree behaviour
        // add the flying capability and remove the walk to tree behaviour
        if(this.isConscious() && flyingTurn==0 && !this.hasCapability(DinosaurCapability.Status.FLYING) && containWalkToTreeBehaviour){
            this.addCapability(DinosaurCapability.Status.FLYING);
            for(int i = 0; i < this.getBehaviour().size(); i ++){
                if(this.getBehaviour().get(i) instanceof WalkToTreeBehaviour){
                    this.getBehaviour().remove(i);
                }
            }
        }

        // if Pterodactyl is conscious and flying turn not equal to 30 and no flying capability
        // add the flying capability
        if(this.isConscious() && flyingTurn==0 && !this.hasCapability(DinosaurCapability.Status.FLYING)){
            this.addCapability(DinosaurCapability.Status.FLYING);
        }

        // if Pterodactyl is conscious and flying turn larger then 30 and have flying capability
        // remove the flying capability and add walking capability
        else if(this.isConscious() && flyingTurn > 10 && this.hasCapability(DinosaurCapability.Status.FLYING)){
            this.removeCapability(DinosaurCapability.Status.FLYING);
            this.addCapability(DinosaurCapability.Status.WALKING);
            this.getBehaviour().add(new WalkToTreeBehaviour());
        }

        // if Pterodactyl have flying capability, add the flying turn
        if(this.hasCapability(DinosaurCapability.Status.FLYING)){
            flyingTurn++;
        }
    }

    /**
     * method to get the flying turn of Pterodactyl
     * @return flying turn of Pterodactyl
     */
    public int getFlyingTurn() {
        return flyingTurn;
    }

    /**
     * method to set the flying turn of Pterodactyl
     * @param flyingTurn flying turn of Pterodactyl
     */
    public void setFlyingTurn(int flyingTurn) {
        this.flyingTurn = flyingTurn;
    }

    /**
     * This method is to find the nearest unoccupied tree in the map and move one step closer to that tree
     * @param actor the Pterodactyl
     * @param map the Gamemap
     * @return an action that move one step closer to the tree
     */
    public static Action findNearestUnoccupiedTree(Actor actor, GameMap map){
        Location actorLocation = map.locationOf(actor);
        int minimumDistance = -1, distanceToTree = 0;
        Location treeLocation = null;

        // loop through the whole map and find a Tree that does not have an actor
        for(int y = 0; y < map.getYRange().max(); y++) {
            for (int x = 0; x < map.getXRange().max(); x++) {
                Location there = map.at(x, y);
                if (there.getGround() instanceof Tree && !there.containsAnActor()) {
                    distanceToTree = Distance.distance(actorLocation, there);
                    if (minimumDistance == -1) {
                        minimumDistance = distanceToTree;
                        treeLocation = there;
                    } else if (distanceToTree < minimumDistance && distanceToTree != 0) {
                        minimumDistance = distanceToTree;
                        treeLocation = there;
                    }
                }
            }
        }

        // if there's no unoccupied tree on the map, then just wander around the map
        if(treeLocation == null){
            return new WanderBehaviour().getAction(actor, map);
        }

        // if there's unoccupied tree on the map, then move on step closer
        else {
            return new FollowLocationBehaviour(treeLocation).getAction(actor, map);
        }
    }


}
