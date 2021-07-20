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
 * @see game.Item
 */

/**
 * A class that represent a Tree.
 */
public class Tree extends Ground {
	/**
	 * the age of the tree
	 */
	private int age = 0;

	/**
	 * boolean to indicate if the Tree produce a Fruit, boolean to indicate if the Tree drop Fruit
	 */
	private boolean produceFruit, dropFruit;

	/**
	 * a list of Fruits object
	 */
	private List<Fruit> listOfFruits = new ArrayList<Fruit>();

	private Random rand = new Random();

	public static int eco_points;


	/**
	 * Constructor for the Tree object
	 */
	public Tree() {
		super('+');
	}

	/**
	 * This method keep tracks of the behaviour of the Tree object each turn
	 * This method apply the probablity of a Tree dropping a Fruit object and producing a Fruit object
	 * @param location The location of the Ground
	 */
	@Override
	public void tick(Location location) {

		// set the percentage which for the tree to produce fruit and drop fruit
		produceFruit = (rand.nextInt(2) == 0);
		dropFruit = (rand.nextInt(20) == 0);

		super.tick(location);
		age++;
		if (age == 10)
			displayChar = 't';
		if (age == 20)
			displayChar = 'T';

		// each tree will have a 50% chance to produce a fruit and added to thier list of fruits
		if(produceFruit){
			this.listOfFruits.add(new Fruit());
			eco_points+=1;
		}

		// fruit on the tree will be remove if drop tree is true and will be added to the location
		if((listOfFruits.size() != 0) && (dropFruit)){
			Fruit droppedFruit = this.listOfFruits.remove(listOfFruits.size()-1);
			location.addItem(droppedFruit);
		}
	}


	/**
	 * method to return the list of Fruits
	 * @return list of fruits
	 */
	public List<Fruit> getListOfFruits() {
		return listOfFruits;
	}

	/**
	 * method to set the list of Fruits
	 * @param listOfFruits
	 */
	public void setListOfFruits(List<Fruit> listOfFruits) {
		this.listOfFruits = listOfFruits;
	}

	/**
	 * method to get the static variable eco points
	 * @return the eco points
	 */
	public static int getEco_points(){
		return eco_points;
	}

	/**
	 * Sets the eco points after each round of a game
	 * @param eco_points the eco points after each round of a game
	 */
	public static void setEco_points(int eco_points) {
		Tree.eco_points = eco_points;
	}
}
