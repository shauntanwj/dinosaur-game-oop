package game.Ground;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;
import game.Dinosaur.Allosaurs;
import game.Dinosaur.Brachiosaur;
import game.Dinosaur.Pterodactyl;
import game.Dinosaur.Stegosaur;
import game.DinosaurCapability;
import game.Item.Fish;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author shauntan, johanazlan
 * @version 1.0.0
 * @see game.Ground.Tree
 */

/**
 * A class which represents a Lake extending Ground class
 */
public class Lake extends Ground {

    /**
     * the capacity of the lake
     */
    private int lakeCapacity = 25; //Each lake capacity = 25 sips
    private Random rand = new Random();

    /**
     * A boolean which indicates if it is raining or not
     */
    public static boolean gotRain = false;

    /**
     * The amount rainfall
     */
    public static float rainFall;

    /**
     * a list of Fish object
     */
    private List<Fish> listOfFish;

    /**
     * Constructor for Lake
     */
    public Lake(){
        super('~');
        listOfFish = new ArrayList<Fish>();
        listOfFish.add(new Fish());
        listOfFish.add(new Fish());
        listOfFish.add(new Fish());
        listOfFish.add(new Fish());
        listOfFish.add(new Fish());
        //System.out.println(listOfFish);
    }


    /**
     * tick adds fishes to the lake each turn if it meets the probability and checks if there is rain or not. If it is raining
     * add the rainfall amount to the lakeCapacity.
     *
     * @param location The location of the Ground
     */
    @Override
    public void tick(Location location) {
        super.tick(location);
        System.out.println(this + " list of fish before " + listOfFish);
        if (listOfFish.size() < lakeCapacity){
            if (rand.nextDouble() <= 0.6) {
                listOfFish.add(new Fish());
                System.out.println(this + " list of fish after" + listOfFish);
            }
        }
        if(gotRain){
            this.lakeCapacity += rainFall;
        }

        System.out.println("Lake Water " + lakeCapacity);

    };

    /**
     * canActorEnter checks which actor can enter
     *
     * @param actor the Actor to check
     * @return a boolean to indicate if actor can enter or not
     */
    @Override
    public boolean canActorEnter(Actor actor){
        if (actor instanceof Stegosaur || actor instanceof Brachiosaur || actor instanceof Allosaurs) {return false;}
        else if(actor instanceof Pterodactyl){
            if(actor.hasCapability(DinosaurCapability.Status.FLYING)){
                return true;
            }
            else if(actor.hasCapability(DinosaurCapability.Status.WALKING)){
                return false;
            }
        }
        return false;
    }

    /**
     * A method for raining
     *
     * @param rain The boolean if it is raining or not
     * @param amtOfRain The amount of rain
     */
    public static void raining(boolean rain, float amtOfRain){
        gotRain = rain;
        rainFall = amtOfRain;
    }

    /**
     *Gets the lake capacity
     * @return the lake capacity
     */
    public int getLakeCapacity() {
        return lakeCapacity;
    }

    /**
     *Sets the lake capacity
     * @param lakeCapacity the lake capacity
     */
    public void setLakeCapacity(int lakeCapacity) {
        this.lakeCapacity = lakeCapacity;
    }

    /**
     *
     * Gets the list of fish
     * @return a list of fish
     */
    public List<Fish> getListOfFish() {
        return listOfFish;
    }

    /**
     *Sets the list of fish
     * @param listOfFish A list of fish
     */
    public void setListOfFish(List<Fish> listOfFish) {
        this.listOfFish = listOfFish;
    }
}
