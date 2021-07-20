package game.Item;

import game.Behaviour.BreedingBehaviour;

/**
 * @author shauntan, johanazlan
 * @version 1.0.0
 * @see game.CheckBehaviour
 */

/**
 * A class representing a Vegetarian Meal Kit Item
 */
public class VegetarianMealKit extends PortableItem {
    /**
     * The price of one Vegetarian Meal Kit
     */
    private int ecoPointsPrice = 100;

    /**
     * Constructor for Vegeterian Meal Kit
     */
    public VegetarianMealKit() {
        super("VegetarianMealKit", 'v');
    }

    /**
     * Method to get the ecoPointsPrice of the vegeterian meal kit
     * @return returns the ecoPointsPrice
     */
    public int getEcoPointsPrice(){
        return ecoPointsPrice;
    }


}

