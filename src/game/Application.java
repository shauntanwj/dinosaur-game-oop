package game;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import edu.monash.fit2099.engine.*;
import game.Action.FeedAction;
import game.Action.PickUpTreeBushAction;
import game.Dinosaur.Allosaurs;
import game.Dinosaur.Brachiosaur;
import game.Dinosaur.Pterodactyl;
import game.Dinosaur.Stegosaur;
import game.Ground.Bush;
import game.Ground.Dirt;
import game.Ground.Lake;
import game.Ground.Tree;
import game.Item.*;


/**
 * @author shauntan, johanazlan
 * @version 1.1.0
 * @see game.Application
 */

/**
 * The main class for the Jurassic World game.
 *
 */
public class Application {
	/**
	 * isChallenge is a boolean indicating if player chose challenge or sandbox.
	 */
	private static boolean isChallenge;

	/**
	 * numberOfMoves is the number of moves the player sets, chosenEcoPoints is the number of eco points the
	 * player sets
	 */
	private static int numberOfMoves, chosenEcoPoints;

	/**
	 * Resets the eco points for Egg class, Tree class, FeedAction class and PickUpTreeBush class.
	 * Once a Challenge or Sandbox ends, need to reset the eco points.
	 *
	 */
	public static void resetEcoPoints(){
		Egg.setEco_points(0);
		Tree.setEco_points(0);
		FeedAction.setEcoPoints(0);
		PickUpTreeBushAction.setEcoPoints(0);
	}

	/**
	 *This method prints the Start Menu for the player to choose the game mode or quit.
	 * @return returns choice of selection.
	 */
	public static int printStartMenu(){
		Scanner scanner = new Scanner(System.in);
		System.out.println("Welcome to Jurassic Park!");
		System.out.println("Select a game mode:");
		System.out.println("1) Challenge");
		System.out.println("2) Sandbox");
		System.out.println("3) Quit");

		int choice = scanner.nextInt();
		return choice;
	}

	/**
	 * This method displays the start menu and starts the game which is selected.
	 */
	public static void selection(){
		int selection;
		try {
			selection = printStartMenu();
			if (selection < 1 || selection > 3) {
				System.out.println("Please Select 1 to 3 Option");
				System.out.println("");
				selection();
			} else {
				switch (selection) {
					case 1:

						boolean validInput = false;
						while (!validInput) {
							try {
								Scanner scanner = new Scanner(System.in);
								System.out.println("");
								System.out.print("Choose number of moves: ");
								numberOfMoves = Integer.parseInt(scanner.nextLine());

								System.out.print("Choose number of eco points to win: ");
								chosenEcoPoints = Integer.parseInt(scanner.nextLine());

								if (numberOfMoves > 0 && chosenEcoPoints > 0) {
									validInput = true;
								}
							}
							catch (NumberFormatException e){
								validInput = false;
							}
						}


						isChallenge = true;
						System.out.println("");
						System.out.println("Starting a challenge game...");
						System.out.println("");
						resetEcoPoints();

						createGameObjects();
						break;

					case 2:
						isChallenge = false;

						System.out.println("");
						System.out.println("Starting a Sandbox game...");
						System.out.println("");

						resetEcoPoints();
						createGameObjects();
						break;

					case 3:
						System.exit(3);
						break;
				}
			}
		}
		catch (InputMismatchException e){
			System.out.println("Please Select Option 1 to 3");
			System.out.println("");
			selection();
		}
	}

	/**
	 * CreateGameObjects is responsible to create all the objects and map for the game.
	 */
	public static void createGameObjects(){
		World world = new World(new Display());

		FancyGroundFactory groundFactory = new FancyGroundFactory(new Dirt(), new Wall(), new Floor(), new Tree(), new Bush(), new Lake());

		List<String> map1 = Arrays.asList(
				"................................................................................",
				"................................................................................",
				".....#######.......................~............................................",
				".....#_____#....................................................................",
				".....#_____#....................................................................",
				".....###.###................................................................~...",
				"................................................................................",
				"......................................+++.......................................",
				".......................................++++.....................................",
				"...................................+++++........................................",
				".....~...............................++++++.....................................",
				"......................................+++.......................................",
				".....................................+++........................................",
				"................................................................................",
				"............+++.........................................................~.......",
				".............+++++..............................................................",
				"...............++........................................+++++..................",
				".............+++....................................++++++++....................",
				"............+++.......................................+++.......................",
				".............................................~..................................",
				".........................................................................++.....",
				"........................................................................++.++...",
				"..........~..............................................................++++...",
				"..........................................................................++....",
				"................................................................................");
		GameMap gameMap1 = new JurassicParkGameMap(groundFactory, map1);
		world.addGameMap(gameMap1);

		// Creating map2
		List<String> map2 = Arrays.asList(
				"...............................................................~................",
				"................................................................................",
				"........................++......................................................",
				"........................++.....................................+++..............",
				"........................+++++.................................+++...............",
				"..........+++..................................................+................",
				"............+..................................................+................",
				"...........+..........................+++......................++...............",
				".......................................++++.....................................",
				"........~..........................+++++........................................",
				".....................................++++++..................~..................",
				"......................................+++.......................................",
				".....................................+++........................................",
				"................................................................................",
				"............+++.................................................................",
				".............+++++......................................................~.......",
				"...............++...............~.........................+++++..................",
				".............+++....................................++++++++....................",
				"............+++.......................................+++.......................",
				"................................................................................",
				".........................................................................++.....",
				"........................................................................++.++...",
				".........................................................................++++...",
				"..........................................................................++....",
				"................................................................................");

		//Adding gameMap2 to world
		GameMap gameMap2 = new JurassicParkGameMap(groundFactory, map2);
		world.addGameMap(gameMap2);

		Player player = new Player("Player", '@', 100);
		//Add both the gameMap into the map arrayList.
		player.getMap().add(gameMap1);
		player.getMap().add(gameMap2);

		player.setTotal_eco_points(0);
		player.setDecision("Challenge");
		player.setEcoPointsWin(chosenEcoPoints);
		player.setTotalMoves(numberOfMoves);
		if (!isChallenge){
			player.setDecision("Sandbox");
		}
		else{
			player.setDecision("Challenge");
		}

		world.addPlayer(player, gameMap1.at(9, 4));

		// Added a Vending Machine item
		VendingMachine vm = new VendingMachine();
		gameMap1.at(3,6).addItem(vm);

		Corpse stegosaur = new Corpse(new Pterodactyl("Corpse", true), 100);
		gameMap1.at(2, 10).addItem(stegosaur);
		gameMap1.at(2,6).addActor(new Pterodactyl("Pterodactyl 1",  DinosaurCapability.Gender.MALE));
		gameMap1.at(20,20).addActor(new Pterodactyl("Pterodactyl 2",  DinosaurCapability.Gender.MALE));
		gameMap1.at(2,8).addActor(new Pterodactyl("Pterodactyl 3",  DinosaurCapability.Gender.FEMALE));
		gameMap1.at(11,11).addActor(new Pterodactyl("Pterodactyl 4",  DinosaurCapability.Gender.FEMALE));

		// Place a pair of stegosaurs in the middle of the map
		gameMap1.at(52, 7).addActor(new Stegosaur("Stegosaur 1", DinosaurCapability.Gender.MALE));
		gameMap1.at(30, 21).addActor(new Stegosaur("Stegosaur 2", DinosaurCapability.Gender.FEMALE));


		// Place 2 Male and 2 Female Brachiosaur and different places of the map
		gameMap1.at(60, 21).addActor(new Brachiosaur("Brachiosaur 1", DinosaurCapability.Gender.MALE));
		gameMap1.at(20, 5).addActor(new Brachiosaur("Brachiosaur 2", DinosaurCapability.Gender.MALE));
		gameMap1.at(6, 20).addActor(new Brachiosaur("Brachiosaur 3", DinosaurCapability.Gender.FEMALE));
		gameMap1.at(67, 2).addActor(new Brachiosaur("Brachiosaur 4", DinosaurCapability.Gender.FEMALE));

		// Place Allosaur
		gameMap1.at(2,7).addActor(new Allosaurs("Allosaur 1", true));
		gameMap1.at(2,23).addActor(new Allosaurs("Allosaur 2", true));
		gameMap1.at(10,15).addActor(new Allosaurs("Allosaur 3", true));



		world.run();
	}

	public static void main(String[] args) {
		selection();
	}
}
