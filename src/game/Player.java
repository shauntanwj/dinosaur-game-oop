package game;

import edu.monash.fit2099.engine.*;
import game.Action.FeedAction;
import game.Action.PickUpTreeBushAction;
import game.Action.QuitAction;
import game.Ground.Tree;
import game.Item.Egg;

import java.util.ArrayList;

/**
 * Class representing the Player.
 */
public class Player extends Actor {

	/**
	 * menu is an object of the Menu() class
	 */
	private Menu menu = new Menu();

	/**
	 * total_eco_points is the total eco points of the player, totalMoves is the total no. of moves the user
	 * sets for a challenge game, moveCounter keeps track of the number of moves the player took,
	 * ecoPointsWin is the number of eco points required to win a challenge game.
	 */
	private int total_eco_points, totalMoves, moveCounter = 0, ecoPointsWin;

	/**
	 * gameDecision stores the decision of the player whether its challenge or sandbox
	 */
	private String gameDecision;

	/**
	 * A list of maps in the game
	 */
	private ArrayList<GameMap> listOfMaps = new ArrayList<GameMap>();

	/**
	 * Constructor.
	 *
	 * @param name        Name to call the player in the UI
	 * @param displayChar Character to represent the player in the UI
	 * @param hitPoints   Player's starting number of hitpoints
	 */
	public Player(String name, char displayChar, int hitPoints) {
		super(name, displayChar, hitPoints);
		this.total_eco_points = 0;
	}


	/**
	 * For each player turn, check the gameDecision, else output the menu as usual.
	 *
	 * @param actions    collection of possible Actions for this Actor
	 * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
	 * @param map        the map containing the Actor
	 * @param display    the I/O object to which messages may be written
	 * @return The menu for each turn unless the player wins or loses in a Challenge game.
	 */
	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		actions.add(new QuitAction());
		calculateTotalEcoPoint();
		System.out.println("");
		System.out.println("Player's total Eco Point: " + getTotal_eco_points());

		if (checkCurrentPos(this, map) != null){
			actions.add(checkCurrentPos(this, map));
		}

		if (gameDecision.equals("Challenge")){
			System.out.println("Eco points required to win: " + ecoPointsWin);
			System.out.println("Move Counter: " + moveCounter);
			System.out.println("Total Moves: " + totalMoves);
			System.out.println("");

			if (challenge().equals("win")){ //if player win in challenge game ->
				System.out.println("Congratulations!!! You have won the game!");
				System.out.println("");
				Application.selection();


			}
			else if (challenge().equals("lose")){
				System.out.println("Sorry, you lost the game. Please try again...");
				System.out.println("");
				Application.selection();

			}
			// Handle multi-turn Actions
			else {
				moveCounter++;
				if (lastAction.getNextAction() != null) {
					if (lastAction.getNextAction() instanceof QuitAction) {
						System.exit(0);
					} else {
						return lastAction.getNextAction();
					}
				}
				return menu.showMenu(this, actions, display);
			}
			return new QuitAction();
		}

		else{
			if (lastAction.getNextAction() != null) {
				if (lastAction.getNextAction() instanceof QuitAction) {
					System.exit(0);
				} else {
					return lastAction.getNextAction();
				}
			}
			return menu.showMenu(this, actions, display);
		}
	}

	/**
	 * Implements the Challenge game.
	 *
	 * @return the result of the player playing the game. Either win or lose
	 */
	public String challenge(){
		//Player wins if they get the specified number of eco points within the chosen number of moves, else lose.
		//Specified number of eco points -> ecoPointsWin
		//Chosen number of moves -> totalMoves

		if (moveCounter <= totalMoves) { //Check if no.of moves < totalMoves
			if (getTotal_eco_points() >= ecoPointsWin){ //if player meets the ecoPointsWin within the required no.of moves
				return "win"; //Return true = win
			}
			return "";
		}
		return "lose"; //Return False = lose

	}

	/**
	 * checkCurrentPos checks the current position of the actor in the map and add a new MoveActorAction when the
	 * actor is eligible to move to the new map and also move back to the old map.
	 *
	 * @param actor the current actor
	 * @param map the map that the actor is at
	 * @return new Action or null
	 */
	private Action checkCurrentPos(Actor actor, GameMap map){
		//Check curr pos of actor
		//if y = 0 -> return new moveActorAction
		Location actorLocation = map.locationOf(actor);
		GameMap gameMap1 = listOfMaps.get(0);
		GameMap gameMap2 = listOfMaps.get(1);

		if (map == gameMap1) {
			if (actorLocation.y() == 0) {
				return new MoveActorAction(gameMap2.at(actorLocation.x(), gameMap2.getYRange().max()), "to new map!");
			}
		}
		else if (map == gameMap2){
			if (actorLocation.y() == gameMap2.getYRange().max()) {
				return new MoveActorAction(gameMap1.at(actorLocation.x(), 0), "to old map!");
			}
		}
		return null;
	}

	/**
	 * getter for listOfMaps
	 */
	public ArrayList<GameMap> getMap() {
		return listOfMaps;
	}

	/**
	 * Setter for listOfMaps
	 * @param map
	 */
	public void setMap(ArrayList<GameMap> map) {
		this.listOfMaps = map;
	}

	/**
	 * method to set the total eco points
	 *
	 * @param total_eco_points set the total eco points
	 */
	public void setTotal_eco_points(int total_eco_points) {
		this.total_eco_points = total_eco_points;
	}

	/**
	 * method to get the total eco points
	 *
	 * @return total eco points
	 */
	public int getTotal_eco_points() {
		return total_eco_points;
	}

	/**
	 * method to calculate all the scenarios happen and add to total eco points
	 */
	private void calculateTotalEcoPoint() {
		this.total_eco_points = Egg.getEco_points() + Tree.getEco_points() + FeedAction.getEcoPoints() + PickUpTreeBushAction.getEcoPoints();
	}

	/**
	 * Sets the total moves the player can take for a Challenge game
	 *
	 * @param totalMoves the total moves the player can take for a Challenge game
	 */
	public void setTotalMoves(int totalMoves) {
		this.totalMoves = totalMoves;
	}

	/**
	 * Sets the eco points the player needs to win the challenge game
	 *
	 * @param ecoPointsWin eco points the player needs to win the challenge game
	 */
	public void setEcoPointsWin(int ecoPointsWin){
		this.ecoPointsWin = ecoPointsWin;
	}

	/**
	 * Sets the player's decision if the player wants to play Challenge or Sandbox
	 *
	 * @param gameDecision The player's decision whether Challenge or Sandbox
	 */
	public void setDecision(String gameDecision){
		this.gameDecision = gameDecision;
	}

}
