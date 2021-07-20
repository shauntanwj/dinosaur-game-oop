package game.Dinosaur;

import edu.monash.fit2099.engine.*;
import game.Behaviour.Behaviour;
import game.Behaviour.BreedingBehaviour;
import game.Behaviour.WanderBehaviour;
import game.DinosaurCapability;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author shauntan, johanazlan
 * @version 1.0.0
 * @see game.Dinosaur.Pterodactyl
 */

/**
 * A abstract class that represents a Dinosaur which extends Actor class
 */
public abstract class Dinosaur extends Actor {

    /**
     * a list of containing different behaviour fo dinosaur
     */
    private ArrayList<Behaviour> behaviour = new ArrayList<Behaviour>();
   
    /**
     * gender of the dinosaur
     */
    private DinosaurCapability.Gender sex;



    /**
     * hungry level of the dinosaur, the time for dinosaur egg to hatch, the food level to determine whether dinosaur is well fed
     * the number of turn which dinosaur will die, the maximum water capacity of the dinosaur
     */
    private final int HUNGRY_LEVEL, HATCH_TIME, WELL_FED, DEAD_TURN, MAX_WATER_CAP;

    /**
     * The number of turn the dinosaur being pregnant, the total turn of dinosaur being unconscious, the thirst level of dinosaur
     * the number of turn dinosaur is unconscious due to thirst
     */
    private int pregnant_time = 0, age, unconsciousTurn = 0, thirstLevel = 61, unconsciousDueToThirst = 0;

    /**
     * boolean for checking if the dinosaur is baby or not, boolean to check if the dinosaur unconscious due to thirst
     */
    private boolean is_baby, unconsciousThirst;

    private Random rand = new Random();


    /**
     * The constructor for Dinosaur
     * @param name name of the dinosaur
     * @param c character that represent the dinosaur
     * @param hitpoint the hitpoint of the dinosaur
     * @param hurt the hurt level to start the hitpoint
     * @param cap the gender
     * @param hungrylevel the hungry level
     * @param hatchTime the hatch time
     * @param wellFed the level of well fed
     * @param deadTurn the dead turn for the dinosaur
     * @param maxWater the maximum watrer capacity for the dinosaur
     */
    public Dinosaur(String name, char c, int hitpoint, int hurt, DinosaurCapability.Gender cap, int hungrylevel, int hatchTime, int wellFed, int deadTurn, int maxWater){
        super(name, c, hitpoint);
        behaviour.add(new WanderBehaviour());
        this.age = 30;
        this.is_baby = false;
        this.hurt(hurt);
        this.sex = cap;
        this.addCapability(cap);
        this.HUNGRY_LEVEL = hungrylevel;
        this.HATCH_TIME = hatchTime;
        this.WELL_FED = wellFed;
        this.DEAD_TURN = deadTurn;
        this.MAX_WATER_CAP = maxWater;
    }

    /**
     * Constructor for the Dinosaur
     * @param name name of the dinosaur
     * @param c character that represent the dinosaur
     * @param hitpoint the hitpoint of the dinosaur
     * @param hurt the hurt level to start the hitpoint
     * @param is_baby boolean to indicate if the dinosaur is baby or not
     * @param hungrylevel the hungry level
     * @param hatchTime the hatch time
     * @param wellFed the level of well fed
     * @param deadTurn the dead turn for the dinosaur
     * @param maxWater the maximum watrer capacity for the dinosaur
     */
    public Dinosaur(String name, char c, int hitpoint, int hurt, boolean is_baby, int hungrylevel, int hatchTime, int wellFed, int deadTurn, int maxWater){
        super(name, c, hitpoint);
        this.is_baby = is_baby;

        this.HUNGRY_LEVEL = hungrylevel;
        this.HATCH_TIME = hatchTime;
        this.WELL_FED = wellFed;
        this.DEAD_TURN = deadTurn;
        this.MAX_WATER_CAP = maxWater;

        // if the dinosaur is baby then age is 0
        if(this.is_baby){
            this.age = 0;
            behaviour.add(new WanderBehaviour());
            this.hurt(hurt);
        }
        // 50% chance of being a Male, 50% chance of being a Female
        if(rand.nextInt(2) == 0){
            this.sex = DinosaurCapability.Gender.MALE;
            this.addCapability(DinosaurCapability.Gender.MALE);
        }
        else{
            this.sex = DinosaurCapability.Gender.FEMALE;
            this.addCapability(DinosaurCapability.Gender.FEMALE);
        }
    }

    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        return null;
    }

    /**
     * method to get the age of the dinosaur
     * @return age of the dinosaur
     */
    public int getAge() {
        return age;
    }

    /**
     * method to set the age of the dinosaur
     * @param age age of the dinosaur
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * method to get the is_baby boolean
     * @return true if is baby, false otherwise
     */
    public boolean isIs_baby() {
        return is_baby;
    }

    /**
     * method to set the is_baby boolean
     * @param is_baby true if is baby, false otherwise
     */
    public void setIs_baby(boolean is_baby) {
        this.is_baby = is_baby;
    }

    /**
     * method to get the unconscious turn
     * @return the unconscious turn
     */
    public int getUnconsciousTurn() {
        return unconsciousTurn;
    }

    /**
     * method to set the unconscious turn
     * @param unconsciousTurn the unconscious turn
     */
    public void setUnconsciousTurn(int unconsciousTurn) {
        this.unconsciousTurn = unconsciousTurn;
    }


    /**
     * method to get the pregnant time of the dinosaur
     * @return pregnant time of the dinosaur
     */
    public int getPregnant_time() {
        return pregnant_time;
    }

    /**
     * method to set the pregnant time of the dinosaur
     * @param pregnant_time pregnant time of the dinosaur
     */
    public void setPregnant_time(int pregnant_time) {
        this.pregnant_time = pregnant_time;
    }


    /**
     * method to get the thirst level of the dinosaur
     * @return thirst level of the dinosaur
     */
    public int getThirstLevel() {
        return thirstLevel;
    }

    /**
     * method to set the thirst level of the dinosaur
     * @param thirstLevel thirst level of the dinosaur
     */
    public void setThirstLevel(int thirstLevel) {
        this.thirstLevel = Math.min(thirstLevel, MAX_WATER_CAP);
    }

    /**
     * method to get the unconscious due to thirst turn
     * @return unconscious due to thirst turn
     */
    public int getUnconsciousDueToThirst() {
        return unconsciousDueToThirst;
    }

    /**
     * method to set the unconscious due to thirst turn
     * @param unconsciousDueToThirst unconscious due to thirst turn
     */
    public void setUnconsciousDueToThirst(int unconsciousDueToThirst) {
        this.unconsciousDueToThirst = unconsciousDueToThirst;
    }

    /**
     * method to get unconscious thirst boolean
     * @return true if the dinosaur is unconscious due to thirst, false otherwise
     */
    public boolean isUnconsciousThirst() {
        return unconsciousThirst;
    }

    /**
     * method to set unconscious thirst boolean
     * @param unconsciousThirst true if the dinosaur is unconscious due to thirst, false otherwise
     */
    public void setUnconsciousThirst(boolean unconsciousThirst) {
        this.unconsciousThirst = unconsciousThirst;
    }

    /**
     * method to get the list of behaviour of the dinosaur
     * @return the list of behaviour
     */
    public ArrayList<Behaviour> getBehaviour() {
        return behaviour;
    }

    /**
     * method to set the list of behaviour of the dinosaur
     * @param behaviour the list of behaviour
     */
    public void setBehaviour(ArrayList<Behaviour> behaviour) {
        this.behaviour = behaviour;
    }

    /**
     * method to get the dead turn of the dinosaur
     * @return dead turn of the dinosaur
     */
    public int getDEAD_TURN() {
        return DEAD_TURN;
    }

    /**
     * method to get the hatch time of the dinosaur
     * @return hatch time of the dinosaur
     */
    public int getHATCH_TIME() {
        return HATCH_TIME;
    }

    /**
     * method to get the hungry level of the dinosaur
     * @return hungry level of the dinosaur
     */
    public int getHUNGRY_LEVEL() {
        return HUNGRY_LEVEL;
    }

    /**
     * method to get the level of well fed
     * @return the level of well fed
     */
    public int getWELL_FED() {
        return WELL_FED;
    }

    /**
     * method to get the maximum water capacity of the dinosaur
     * @return maximum water capacity of the dinosaur
     */
    public int getMAX_WATER_CAP() {
        return MAX_WATER_CAP;
    }



}
