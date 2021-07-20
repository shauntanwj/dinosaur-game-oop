package game.Item;

import edu.monash.fit2099.engine.Location;
import game.Dinosaur.Allosaurs;
import game.Dinosaur.Brachiosaur;
import game.Dinosaur.Pterodactyl;
import game.Dinosaur.Stegosaur;


/**
 * @author shauntan, johanazlan
 * @version 1.0.0
 * @see game.Item.Fish
 */

/**
 * A class represent an Egg
 */
public class Egg extends PortableItem {

    /**
     * the hatch time of the egg, the age of the egg
     */
    private int hatchTime, ageOfEgg, ecoPointsPrice;
    public static int eco_points = 0;

    /**
     * Constructor
     * @param name the name of the egg
     * @param hatchTime the hatch time of the egg
     */
    public Egg(String name, int hatchTime) {
        super(name, 'e');
        this.hatchTime = hatchTime;
    }

    /**
     * This function will keep track of the age of the egg each turn, if it has reach the hatch time then
     * this function will remove the egg from the current location and add a dinosaur to the current location
     * @param location
     */
    public void tick(Location location) {
        super.tick(location);
        ageOfEgg++;
        if(ageOfEgg >= hatchTime){
            // eco point add 100 if Stegosaur Egg hatch
            if(this.name == "Stegosaur"){
                if(!location.containsAnActor()){
                    eco_points += 100;
                    location.removeItem(this);
                    Stegosaur newBabyStegosaur = new Stegosaur("Stegosaur", true);
                    location.addActor(newBabyStegosaur);
                    System.out.println("A Stegosaur egg has hatched");
                }
                else{
                    ageOfEgg++;
                }
            }

            // eco point add 1000 if Brachiosaur Egg hatch
            else if(this.name == "Brachiosaur"){
                if(!location.containsAnActor()){
                    eco_points += 1000;
                    location.removeItem(this);
                    Brachiosaur newBabyBrachiosaur = new Brachiosaur("Brachiosaur", true);
                    location.addActor(newBabyBrachiosaur);
                    System.out.println("A Brachiosaur egg has hatched");
                }
                else{
                    ageOfEgg++;                }
            }

            // eco point add 1000 if Allosaur Egg hatch
            else if(this.name == "Allosaur"){
                if(!location.containsAnActor()){
                    eco_points += 1000;
                    location.removeItem(this);
                    Allosaurs newBabyAllosaur = new Allosaurs("Allosaur", true);
                    location.addActor(newBabyAllosaur);
                    System.out.println("A Allosaur egg has hatched");
                }
                else{
                    ageOfEgg++;                }
            }

            // eco point add 100 if Pterodactyl Egg hatch
            else if(this.name == "Pterodactyl"){
                if(!location.containsAnActor()){
                    eco_points += 100;
                    location.removeItem(this);
                    Pterodactyl newBabyPterodactyl = new Pterodactyl("Pterodactyl", true);
                    location.addActor(newBabyPterodactyl);
                    System.out.println("A Pterodactyl egg has hatched");
                }
                else{
                    ageOfEgg++;                }
            }
        }
    }

    /**
     * Gets the eco points if an egg hatches.
     * @return  the eco points if an egg hatches.
     */
    public static int getEco_points(){
        return eco_points;
    }

    /**
     * Sets the eco points after each round of a game
     * @param eco_points the eco points after each round of a game
     */
    public static void setEco_points(int eco_points) {
        Egg.eco_points = eco_points;
    }

    /**
     * The price of the egg in vending machine
     * @return price of the egg in vending machine
     */
    public int getEcoPointsPrice(){
        if (this.name == "Stegosaur"){
            ecoPointsPrice = 200;
        }
        else if (this.name == "Brachiosaur"){
            ecoPointsPrice = 500;
        }
        else if (this.name == "Allosaur"){
            ecoPointsPrice = 1000;
        }

        else if (this.name == "Pterodactyl"){
            ecoPointsPrice = 200;
        }

        return ecoPointsPrice;
    }



}
