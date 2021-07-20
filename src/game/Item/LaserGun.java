package game.Item;

import edu.monash.fit2099.engine.WeaponItem;

/**
 * @author shauntan, johanazlan
 * @version 1.0.0
 * @see PortableItem
 */

/**
 * A class representing Laser Gun item
 */
public class LaserGun extends WeaponItem {
    /**
     * the price of one Laser Gun item
     */
    private int ecoPointsPrice = 500;

    /**
     * Constructor
     */
    public LaserGun() {
        super("Laser Gun", '>', 50, "shoots");
    }

    public int getEcoPointsPrice(){
        return ecoPointsPrice;
    }

}