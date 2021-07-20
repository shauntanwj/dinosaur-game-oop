package game.Action;

import edu.monash.fit2099.engine.*;
import game.Ground.Bush;
import game.Ground.Tree;
import game.Item.Fruit;
import game.Player;

import java.util.Random;

/**
 * @author shauntan, johanazlan
 * @version 1.0.0
 * @see game.Behaviour
 */

/**
 * This class implements an action for the Player to pick up fruit from bush or tree
 */
public class PickUpTreeBushAction extends Action {
    private Random rand = new Random();
    private static int ecoPoints;

    /**
     * This method is used to execute the action of picking up a fruit from a bush or tree if the bush or tree
     * contains a riped fruit. The probability for picking up a fruit from a bush or tree is 40%. Add the fruit
     * to the player's inventory and increase the player's eco points by 10.
     *
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return returns a string indicating if the player picks up a fruit from a bush or tree if there is a ripe fruit.
     */
    public String execute(Actor actor, GameMap map){
        Ground currentGround = map.locationOf(actor).getGround(); //Initialize currentGround

        //Pick up fruit from a tree
        if(currentGround instanceof Tree){ //Check if location of Actor has a Tree
            Tree tree = (Tree) currentGround;
            if (!tree.getListOfFruits().isEmpty()){ //if Tree contains fruit then calculate probability to pick up fruit.
                if (rand.nextDouble() <= 0.4) { //if it is within 40% then pick up fruit
                    tree.getListOfFruits().remove(tree.getListOfFruits().size() - 1); //remove the last fruit from the list
                    actor.addItemToInventory(new Fruit()); //Add fruit to inventory
                    ecoPoints += 10; //Increase eco points for harvesting a fruit from tree
                    System.out.println(actor.getInventory());
                    String retval = menuDescription(actor);
                    return (retval + " from tree");
                }

                else{ //fail to meet 40% probability
                    return("You search the tree or bush for fruit, but you can't find any ripe ones.");
                }
            }
        }

        //Pick up fruit from Bush
        else if (currentGround instanceof Bush){ //Check if location of Actor has a Bush
            Bush bush = (Bush) currentGround;
            if (!bush.getListOfFruits().isEmpty()){ //check if the list of fruits for the bush is empty or not
                if (rand.nextDouble() <= 0.4) { //if it is within 40% then pick up fruit
                    bush.getListOfFruits().remove(bush.getListOfFruits().size() - 1); //remove the last fruit from the list
                    actor.addItemToInventory(new Fruit()); //Add fruit to inventory
                    ecoPoints += 10; //Increase eco points for harvesting a fruit from bush
                    String retval = menuDescription(actor);
                    return (retval + " from bush");
                }
                else{
                    return("You search the tree or bush for fruit, but you can't find any ripe ones.");
                }
            }
        }


        return("No fruits in bush or tree");

    }

    @Override
    /** Returns a string on the menu when a player can pick up a fruit.
     * @param actor The actor that picks up the fruit
     */
    public String menuDescription(Actor actor) {
        return "Player picks up fruit "; //return message
    }

    /**
     * Gets the total eco points for picking up a tree or a bush
     * @return total eco points for picking up a fruit from a tree or a bush
     */
    public static int getEcoPoints(){
        return ecoPoints; //get eco points for this action
    }

    /**
     * Set the eco points after each round of a game
     * @param ecoPoints the eco points after each round of a game
     */
    public static void setEcoPoints(int ecoPoints) {
        PickUpTreeBushAction.ecoPoints = ecoPoints;
    }
}
