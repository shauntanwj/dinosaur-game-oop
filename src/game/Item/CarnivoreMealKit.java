package game.Item;

import game.Behaviour.BreedingBehaviour;

/**
 * @author shauntan, johanazlan
 * @version 1.0.0
 * @see Corpse
 */

/**
 * A class representing a Carnivore Meal Kit Item
 */
public class CarnivoreMealKit extends PortableItem {

    /**
     * The price of one Carnivore Meal Kit
     */
    private int ecoPointsPrice = 500;

    /**
     * Constructor for Carnivore Meal Kit
     */
    public CarnivoreMealKit() {
        super("CarnivoreMealKit", 'c');
    }

    /**
     * method to get the price of the Carnivore meal kit
     * @return
     */
    public int getEcoPointsPrice(){
        return ecoPointsPrice;
    }
}



