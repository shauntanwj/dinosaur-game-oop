package game.Action;

import edu.monash.fit2099.engine.*;

import java.lang.Math;
import game.Dinosaur.Allosaurs;
import game.Dinosaur.Brachiosaur;
import game.Dinosaur.Pterodactyl;
import game.Dinosaur.Stegosaur;
import game.Item.CarnivoreMealKit;
import game.Item.Fruit;
import game.Item.VegetarianMealKit;
import game.Player;

/**
 * @author shauntan, johanazlan
 * @version 1.0.1
 * @see game.Action.MatingAction
 */

/**
 * This class is used when the player wants to feed the dinosaurs.
 */
public class FeedAction extends Action {
    protected Actor target;
    private static int ecoPoints;

    /**
     * Constructor for feedAction
     * @param target target is the dinosaur
     */
    public FeedAction(Actor target) {
        this.target = target;
    }


    @Override
    /**
     * This method is used to execute the action of feeding the dinosaurs.
     *
     * @param actor actor is the one which is going to feed the dinosaurs
     * @param map The map the actor is on.
     * @return returns a string indicating if the player fed the dinosaur or not
     */
    public String execute(Actor actor, GameMap map) {
        Location here = map.locationOf(actor);
        //here.getActor() instanceof Player;
        Location there = map.locationOf(target);
        //Check if location of here and location of there are adjacent between each other.
        if ((Math.abs(here.x() - there.x()) == 1) || (Math.abs(here.y() - there.y()) == 1)) {
            //Check if actor is Player
            if (here.getActor() instanceof Player) {
                //Check if target is Stegosaur or Brachiosaur
                if (there.getActor() instanceof Stegosaur || there.getActor() instanceof Brachiosaur) {
                    //Check if actor's inventory has a Fruit
                    if (actor.getInventory().stream().anyMatch(c -> c instanceof Fruit)) {
                        target.heal(20); //Feed the fruit to Stegosaur or brachiosaur and increase its hitpoints
                        for (int i = 0; i < actor.getInventory().size(); i++) { //Loop through the actor's inventory
                            if (actor.getInventory().get(i) instanceof Fruit) { //Check if the inventory has a fruit
                                Fruit fruit = ((Fruit) actor.getInventory().get(i)); //Get the fruit
                                actor.removeItemFromInventory(fruit); //Remove the fruit from inventory
                                ecoPoints += 10; //increase player's eco points for feeding fruit to a dinosaur
                                System.out.println(actor.getInventory());
                                return (menuDescription(actor));

                            }
                        }
                        //Need to add eco points to player. 10 points
                    }

                    //Check if actor's inventory contains vegetarianMealKit
                    else if (actor.getInventory().stream().anyMatch(c -> c instanceof VegetarianMealKit)) {
                        target.heal(160); //Feed the VegetarianMealKit to Stegosaur or brachiosaur and increase their hitpoints to max
                        for (int i = 0; i < actor.getInventory().size(); i++) { //Loop through the actor's inventory
                            if (actor.getInventory().get(i) instanceof VegetarianMealKit) { //Check if the inventory has a VegetarianMealKit
                                VegetarianMealKit vegetarianMealKit = ((VegetarianMealKit) actor.getInventory().get(i)); //Get the VegetarianMealKit
                                actor.removeItemFromInventory(vegetarianMealKit); //Remove the VegetarianMealKit from inventory
                                System.out.println(actor.getInventory());
                                return (menuDescription(actor));
                            }
                        }
                    }

                    else{
                        return ("Player does not have food to feed");
                    }
                }

                //Check if target is Allosaur
                else if (there.getActor() instanceof Allosaurs || there.getActor() instanceof Pterodactyl) {
                    //check if actor's inventory has a carnivoreMealKit
                    if (actor.getInventory().stream().anyMatch(c -> c instanceof CarnivoreMealKit)) {
                        target.heal(100); //Feed the CarnivoreMealKit to Allosaur and increase their hitpoints to max
                        for (int i = 0; i < actor.getInventory().size(); i++) { //Loop through the actor's inventory
                            if (actor.getInventory().get(i) instanceof CarnivoreMealKit) { //Check if the inventory has a CarnivoreMealKit
                                CarnivoreMealKit carnivoreMealKit = ((CarnivoreMealKit) actor.getInventory().get(i)); //Get the CarnivoreMealKit
                                actor.removeItemFromInventory(carnivoreMealKit); //Remove the CarnivoreMealKit from inventory
                                System.out.println(actor.getInventory());
                                return (menuDescription(actor));
                            }
                        }
                    }
                    else{
                        return ("Player does not have food to feed");
                    }
                }
            }
            else{
                return ("Not player");
            }
        }
        return ("No actor's adjacent to each other");

    }

    @Override
    /**
     * This method displays a string on the menu if the player fed the dinosaur
     *
     * @param actor The actor that feeds the dinosaur
     * @return returns a string on the menu when a player can feed a dinosaur.
     */
    public String menuDescription(Actor actor) {

        return("Player feeds " + target); //returns a message
    }

    /**
     * Gets the total eco points for feeding dinosaurs
     * @return total eco points for feeding dinosaurs
     */
    public static int getEcoPoints(){
        return ecoPoints; //get eco points for this action
    }

    /**
     * Sets the eco points after each round of a game
     * @param ecoPoints the eco points after each round of a game
     */
    public static void setEcoPoints(int ecoPoints) {
        FeedAction.ecoPoints = ecoPoints;
    }
}
