package game;

import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.GroundFactory;
import game.Ground.Lake;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author shauntan, johanazlan
 * @version 1.0.0
 * @see game.Player
 */

/**
 * A class which represent the Jurassic Park map
 */
public class JurassicParkGameMap extends GameMap {
    /**
     *Keeps track of the number of turns until it rains
     */
    private int rainCounter = 0;

    /**
     *the amount of rain
     */
    private static float rainfall = 0;//, random_number;

    /**
     *Boolean which indicates if it is raining or not
     */
    private static boolean rain;

    /**
     * Constructor for JurassicParkGameMap
     *
     * @param groundFactory create new map locations
     * @param lines A list of strings
     */
    public JurassicParkGameMap(GroundFactory groundFactory, List<String> lines) {
        super(groundFactory, lines);
    }

    /**
     * Tick is used to check if there is rain and calculate the probability of rainfall
     */
    @Override
    public void tick() {
        super.tick();
        System.out.println("Rain counter = " + rainCounter);
        Lake.raining(false, rainfall);
        if (rainCounter == 10) {
            rainCounter = 0; //Reset the rainCounter to 0 after every 10 turns.
            rain = checkRain();
            if (rain) {
                float random_number = (float) ThreadLocalRandom.current().nextInt(1, 6);
                rainfall = (random_number / 10) * 20;
                Lake.raining(rain, rainfall);
                System.out.println("Rainfall: " + rainfall);
            } else {
                System.out.println("No rain");
            }
        } else {
            rainCounter++;
        }
    }

    /**
     *checkRain is used to indicate if raining or not
     *
     * @return returns a boolean to indicate if raining or not
     */
    public boolean checkRain() {
        Random rand = new Random();
        if (rand.nextDouble() <= 0.5) {
            return true;
        }
        return false;
    }

    /**
     * Gets the boolean for rain
     * @return the boolean in rain
     */
    public static boolean hasRain() {
        return rain;
    }
}