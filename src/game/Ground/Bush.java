package game.Ground;

import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;
import game.Item.Fruit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author shauntan, johanazlan
 * @version 1.0.0
 * @see game.Ground.Dirt
 */

/**
 * A class that represents a Bush
 */
public class Bush extends Ground {

    /**
     * a list which contain Fruit object in the Bush
     */
    private List<Fruit> listOfFruits = new ArrayList<Fruit>();
    private Random rand = new Random();

    /**
     * boolean to indicate whether the Bush has produce fruit or not
     */
    private boolean produceFruit;

    /**
     * Constructor for Bush object
     */
    public Bush() {
        super('*');
    }

    /**
     * This method keep tracks of the behaviour of Bush each turn
     * Each turn it will randomly select a number between 0-9 and if the number is 0 then it will produce a Fruit
     * @param location The location of the Ground
     */
    public void tick(Location location){
        produceFruit = (rand.nextInt(10) == 0);
        // each bush will have a 10% chance of adding a new fruit to their list of fruits
        // rand.nextInt will have random number from 0-9, then the probability of equals to 0 is 10%
        if(produceFruit){
            this.listOfFruits.add(new Fruit());
        }
    }

    /**
     * method to return the list of Fruits
     * @return a list of Fruits
     */
    public List<Fruit> getListOfFruits() {
        return listOfFruits;
    }

    /**
     * method to set the list of fruits
     * @param listOfFruits
     */
    public void setListOfFruits(List<Fruit> listOfFruits) {
        this.listOfFruits = listOfFruits;
    }
}
