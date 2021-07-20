package game.Ground;

import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;
import java.util.Random;

/**
 * @author shauntan, johanazlan
 * @version 1.0.0
 * @see game.Ground.Lake
 */

/**
 * A class that represents bare dirt.
 */
public class Dirt extends Ground {

	/**
	 * boolean that indicate if the Dirt object grow a Bush object
	 */
	private boolean growBush;
	private Random rand = new Random();

	/**
	 * Constructor for the Dirt object
	 */
	public Dirt() {
		super('.');
	}

	/**
	 * This function will apply the probability of a Dirt object growing a Bush object
	 * @param location The location of the Ground
	 */
	public void tick(Location location) {
		super.tick(location);
		if(checkGrowBush(location)){
			location.setGround(new Bush());
		};
}

	/**
	 * This method will return a boolean to indicate if the current Dirt will grow a Bush or not
	 * This method will check the surrounding of the current Dirt and apply the appropriate probability to grow a Bush
	 * If beside is Tree 0%, if the two adjacent square is a Bush 10%, any Dirt will have a 1% chance to grow a Bush
	 * @param location The location of the ground on which we lie.
	 * @return true if grow a Bush, otherwise false
	 */
 	public boolean checkGrowBush(Location location) {
		GameMap map = location.map();
		Ground oneLeft, oneRight, oneUp, oneDown, twoLeft, twoRight, twoUp, twoDown;

		// check the left side of the current location we lie on is a Tree
		if (location.x() - 1 > 0) {
			oneLeft = map.at(location.x() - 1, location.y()).getGround();
			if (oneLeft instanceof Tree) {
				return false;
			}
		}

		// check the right side of the current location we lie on is a Tree
		if (location.x() + 1 < map.getXRange().max()) {
			oneRight = map.at(location.x() + 1, location.y()).getGround();
			if (oneRight instanceof Tree) {
				return false;
			}
		}

		// check above of the current location we lie on is a Tree
		if (location.y() - 1 > 0) {
			oneUp = map.at(location.x(), location.y() - 1).getGround();
			if (oneUp instanceof Tree) {
				return false;
			}
		}

		// check below the current location we lie on is a Tree
		if (location.y() + 1 < map.getYRange().max()) {
			oneDown = map.at(location.x(), location.y() + 1).getGround();
			if (oneDown instanceof Tree) {
				return false;
			}
		}

		// check the left two adjacent square if it's a bush
		if (location.x() - 2 > 0) {
			oneLeft = map.at(location.x() - 1, location.y()).getGround();
			twoLeft = map.at(location.x() - 2, location.y()).getGround();
			if ((oneLeft instanceof Bush) && (twoLeft instanceof Bush)) {
				int chance = rand.nextInt(10);
				growBush = (chance == 0);
				return growBush;
			}
		}

		// check the right two adjacent square if it's a bush
		if (location.x() + 2 < map.getXRange().max()) {
			oneRight = map.at(location.x() + 1, location.y()).getGround();
			twoRight = map.at(location.x() + 2, location.y()).getGround();
			if ((oneRight instanceof Bush) && (twoRight instanceof Bush)) {
				int chance = rand.nextInt(10);
				growBush = (chance == 0);
				return growBush;
			}
		}

		// check the upper two adjacent square if it's a bush
		if (location.y() - 2 > 0) {
			oneUp = map.at(location.x(), location.y() - 1).getGround();
			twoUp = map.at(location.x(), location.y() - 2).getGround();
			if ((oneUp instanceof Bush) && (twoUp instanceof Bush)) {
				int chance = rand.nextInt(10);
				growBush = (chance == 0);
				return growBush;
			}
		}

		// check the bottom  two adjacent square if it's a bush
		if (location.y() + 2 < map.getYRange().max()) {
			oneDown = map.at(location.x(), location.y() + 1).getGround();
			twoDown = map.at(location.x(), location.y() + 2).getGround();
			if ((oneDown instanceof Bush) && (twoDown instanceof Bush)) {
				int chance = rand.nextInt(10);
				growBush = (chance == 0);
				return growBush;
			}
		}

		// if none of the condition met, then will only have 1% chance to grow a Bush
		int chance = rand.nextInt(100);
		growBush = (chance == 0);
		return growBush;
	}







}

