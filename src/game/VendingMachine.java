package game;

import edu.monash.fit2099.engine.*;
import game.Item.*;

import java.util.Scanner;

/**
 * @author shauntan, johanazlan
 * @version 1.0.1
 * @see Wall
 */

/**
 * A class to implement a Vending Machine item
 */
public class VendingMachine extends Item {
    /**
     * a laser gun item
     */
    private LaserGun laserGun = new LaserGun();

    /**
     * a fruit item
     */
    private Fruit fruit = new Fruit();

    /**
     * a carnivore meal kit item
     */
    private CarnivoreMealKit carnivoreMealKit = new CarnivoreMealKit();

    /**
     * a vegetarian meal kit item
     */
    private VegetarianMealKit vegetarianMealKit = new VegetarianMealKit();

    /**
     * a Stegosaur Egg
     */
    private Egg stegosaurEgg = new Egg("Stegosaur", 10);

    /**
     * a Brachiosaur Egg
     */
    private Egg brachiosaurEgg = new Egg("Brachiosaur", 60);

    /**
     * a Allosaur Egg
     */
    private Egg allosaurEgg = new Egg("Allosaur", 50);

    /**
     * a Pterodoctyl Egg
     */
    private Egg pterodactylEgg = new Egg("Pterodactyl", 10);



    /**
     * Constructor
     */
    public VendingMachine() {
        super("Vending Machine", '$', false);
    }

    /**
     * Called once per turn, so that Locations can experience the passage time. When a Player is at the location
     * of the vending machine, this method will call Menu() which will display the menu.
     * @param location Get the actor at the location of actor and call Menu()
     */
    public void tick(Location location){
        if (location.containsAnActor() && location.getActor() instanceof Player){
            Menu((Player) location.getActor());
        }
    }

    /**
     * This method prints out the menu that is displayed for the user to make their choice.
     * @return returns the choice that the user chose.
     */
    public int printMenu(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("---------------------------");
        System.out.println("Welcome to the Vending Machine! \n");
        System.out.println("1) Fruit (30 eco points)");
        System.out.println("2) Vegeterian Meal Kit (100 eco points)");
        System.out.println("3) Carnivore Meal Kit (500 eco points)");
        System.out.println("4) Stegosaur Eggs (200 eco points)");
        System.out.println("5) Brachiosaur Eggs (500 eco points)");
        System.out.println("6) Allosaur Eggs (1000 eco points)");
        System.out.println("7) Pterodactyl Eggs (200 eco points)");
        System.out.println("8) Laser Gun (500 eco points)");
        System.out.println("9) Exit");
        System.out.print("Select an option to buy: ");

        int choice = scanner.nextInt();
        return choice;

    }

    /**
     * This method will select the choice of the user and execute it.
     */
    public void Menu(Player player){
        int selection;

        do {
            System.out.println("");
            System.out.println("Player Total Eco Point: " + player.getTotal_eco_points());
            System.out.println("Player's Inventory: " + player.getInventory());
            selection = printMenu(); //Display the menu
            switch (selection) {
                case 1:
                    if(player.getTotal_eco_points() >= fruit.getEcoPointsPrice()){
                        player.setTotal_eco_points(player.getTotal_eco_points() - fruit.getEcoPointsPrice()); //Update eco points after purchasing
                        player.addItemToInventory(fruit);
                        System.out.println("Player bought a Fruit");
                    }
                    else{
                        System.out.println("Player not enough eco point");
                    }
                     //Add item purchased to inventory
                    break;

                case 2:
                    if(player.getTotal_eco_points() >= vegetarianMealKit.getEcoPointsPrice()){
                        player.setTotal_eco_points(player.getTotal_eco_points() - vegetarianMealKit.getEcoPointsPrice()); //Update eco points after purchasing
                        player.addItemToInventory(vegetarianMealKit);
                        System.out.println("Player bought a Vegetarian Meal Kit");
                    }
                    else{
                        System.out.println("Player not enough eco point");
                    }
                    //Add item purchased to inventory
                    break;

                case 3:
                    if(player.getTotal_eco_points() >= carnivoreMealKit.getEcoPointsPrice()){
                        player.setTotal_eco_points(player.getTotal_eco_points() - carnivoreMealKit.getEcoPointsPrice()); //Update eco points after purchasing
                        player.addItemToInventory(carnivoreMealKit);
                        System.out.println("Player bought a Carnivore Meal Kit");
                    }
                    else{
                        System.out.println("Player not enough eco point");
                    }
                    break;

                case 4:
                    if(player.getTotal_eco_points() >= stegosaurEgg.getEcoPointsPrice()){
                        player.setTotal_eco_points(player.getTotal_eco_points() - stegosaurEgg.getEcoPointsPrice()); //Update eco points after purchasing
                        player.addItemToInventory(stegosaurEgg);
                        System.out.println("Player bought a Stegosaur Egg");
                    }
                    else{
                        System.out.println("Player not enough eco point");
                    }
                    break;

                case 5:
                    if(player.getTotal_eco_points() >= brachiosaurEgg.getEcoPointsPrice()){
                        player.setTotal_eco_points(player.getTotal_eco_points() - brachiosaurEgg.getEcoPointsPrice()); //Update eco points after purchasing
                        player.addItemToInventory(brachiosaurEgg);
                        System.out.println("Player bought a Brachiosaur Egg");
                    }
                    else{
                        System.out.println("Player not enough eco point");
                    }
                    break;

                case 6:
                    if(player.getTotal_eco_points() >= allosaurEgg.getEcoPointsPrice()){
                        player.setTotal_eco_points(player.getTotal_eco_points() - allosaurEgg.getEcoPointsPrice()); //Update eco points after purchasing
                        player.addItemToInventory(allosaurEgg);
                        System.out.println("Player bought a Allosaur Egg");
                    }
                    else{
                        System.out.println("Player not enough eco point");
                    }
                    break;

                case 7:
                    if(player.getTotal_eco_points() >= pterodactylEgg.getEcoPointsPrice()){
                        player.setTotal_eco_points(player.getTotal_eco_points() - pterodactylEgg.getEcoPointsPrice()); //Update eco points after purchasing
                        player.addItemToInventory(pterodactylEgg);
                        System.out.println("Player bought a Pterodactyl Egg");
                    }
                    else{
                        System.out.println("Player not enough eco point");
                    }
                    break;

                case 8:
                    if(player.getTotal_eco_points() >= laserGun.getEcoPointsPrice()){
                        player.setTotal_eco_points(player.getTotal_eco_points() - laserGun.getEcoPointsPrice()); //Update eco points after purchasing
                        player.addItemToInventory(laserGun);
                        System.out.println("Player bought a Laser Gun");
                    }
                    else{
                        System.out.println("Player not enough eco point");
                    }
                    break;

            }


        }while (selection != 9);


    }




}
