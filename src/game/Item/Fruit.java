package game.Item;

import edu.monash.fit2099.engine.Location;
import game.Behaviour.BreedingBehaviour;

/**
 * @author shauntan, johanazlan
 * @version 1.0.0
 * @see LaserGun
 */

public class Fruit extends PortableItem {

    /**
     * a counter to keep track of the total turn the Fruit on ground
     */
    private int totalTurnsOnGround, ecoPointsPrice = 30;
    public Fruit() {
        super("Fruit",'f');
    }

    /**
     * This method will keep track of the Fruit object, if the Fruit object have been on the Ground
     * for more than 15 turns, then it will be removed
     * @param location The location of the ground on which we lie.
     */
    @Override
    public void tick(Location location) {
        super.tick(location);
        totalTurnsOnGround++;
        if(totalTurnsOnGround == 15){
            location.removeItem(this);
        }
    }

    /**
     * gets the eco point price of the fruit
     * @return The eco point price of the fruit
     */
    public int getEcoPointsPrice() {
        return ecoPointsPrice;
    }
}
