package game;

import edu.monash.fit2099.engine.*;
import game.Behaviour.*;
import game.Dinosaur.*;
import game.Ground.Tree;
import game.Item.Egg;
import java.util.ArrayList;

/**
 * @author shauntan, johanazlan
 * @version 1.1.0
 * @see game.DinosaurCapability
 */

/**
 * A class which contains all static method to check all the Behaviour of the Dinosaur
 */
public class CheckBehaviour {


    /**
     * This function will add the BreedingBehaviour() to the Dinosaur list of Behaviour if it is sufficiently well fed
     * @param dinosaur the dinosaur
     * @param currentHitPoint the current food level of the dinosaur
     */
    public static void checkBreeding(Dinosaur dinosaur, int currentHitPoint) {
        ArrayList<Behaviour> currentBehaviour = dinosaur.getBehaviour();

        boolean containBreedingBehaviour = currentBehaviour.stream().anyMatch(c -> c instanceof BreedingBehaviour);

        // Check if sufficiently well-fed and no breeding behaviour
        if (currentHitPoint > dinosaur.getWELL_FED() && !containBreedingBehaviour && !dinosaur.isIs_baby()) {
            // if a male then add
            if (dinosaur.hasCapability(DinosaurCapability.Gender.MALE) && !dinosaur.hasCapability(DinosaurCapability.Pregnant.BREEDED)) {
                currentBehaviour.add(new BreedingBehaviour());
                dinosaur.setBehaviour(currentBehaviour);
            }

            // if a female and not pregnant then add the breeding behaviour
            else if ((dinosaur.hasCapability(DinosaurCapability.Gender.FEMALE)) && (!dinosaur.hasCapability(DinosaurCapability.Pregnant.PREGNANT))) {
                ArrayList<Behaviour> newBehaviour = dinosaur.getBehaviour();
                newBehaviour.add(new BreedingBehaviour());
                dinosaur.setBehaviour(newBehaviour);
            }
        }

        // if pregnant and got breeding behaviour then remove the breeding behaviour
        // or if current hitpoint/foodlevel lower than wellfed level
        else if ((dinosaur.hasCapability(DinosaurCapability.Pregnant.PREGNANT) && containBreedingBehaviour) ||
                (currentHitPoint < dinosaur.getWELL_FED())) {
            for (int i = 0; i < currentBehaviour.size(); i++) {
                if (currentBehaviour.get(i) instanceof BreedingBehaviour) {
                    currentBehaviour.remove(i);
                    dinosaur.setBehaviour(currentBehaviour);
                }
            }
        }
    }


    /**
     * This function is to check if the Dinosaur is hungry or not, if it is then it will add the HungryBehaviour to the
     * Dinosaur list of Behaviour
     * @param dinosaur the Dinosaur
     * @param hitPoint the food level of the Dinosaur
     */
    public static void checkHungry(Dinosaur dinosaur, int hitPoint){
        ArrayList<Behaviour> currentBehaviour = dinosaur.getBehaviour();
        boolean containHungryBehaviour = currentBehaviour.stream().anyMatch(c-> c instanceof HungryBehaviour);

        // if hitpoint is lower than hungry level and does not contain HungryBehaviour
        if((hitPoint < dinosaur.getHUNGRY_LEVEL()) && !containHungryBehaviour){
            currentBehaviour.add(new HungryBehaviour());
            dinosaur.setBehaviour(currentBehaviour);
        }

        // if not hungry and contain the HungryBehaviour, remove it
        else if(hitPoint >= dinosaur.getHUNGRY_LEVEL() && containHungryBehaviour){
            for(int i = 0; i < currentBehaviour.size(); i++){
                if(currentBehaviour.get(i) instanceof HungryBehaviour){
                    currentBehaviour.remove(i);
                    dinosaur.setBehaviour(currentBehaviour);
                    break;
                }
            }
        }
    }


    /**
     * This function will check if the Dinosaur is conscious or not, if not it will add the UnconsciouisBehaviour to the list
     * of Behaviour of the Dinosaur
     * @param dinosaur the Dinosaur
     * @param map the current Gamemap
     */
    public static void checkUnconscious(Dinosaur dinosaur, GameMap map){
        ArrayList<Behaviour> currentBehaviour = dinosaur.getBehaviour();
        boolean containUnconsciousBehaviour = currentBehaviour.stream().anyMatch(c-> c instanceof UnconsciousBehaviour);

        // if the dinosaur is not conscious and doesnt have unconscious behaviour, add the unconscious behaviour
        if((!dinosaur.isConscious() && !containUnconsciousBehaviour)){
            currentBehaviour.add(new UnconsciousBehaviour());
            dinosaur.setBehaviour(currentBehaviour);
        }

        // if the dinosaur is conscious and have unconscious behaviour, remove the unconscious behaviour
        else if((dinosaur.isConscious() && containUnconsciousBehaviour)){
            for(int i = 0; i < currentBehaviour.size(); i++){
                if(currentBehaviour.get(i) instanceof UnconsciousBehaviour){
                    currentBehaviour.remove(i);
                    dinosaur.setBehaviour(currentBehaviour);
                    dinosaur.setUnconsciousTurn(0);
                    break;
                }
            }
        }

        // if the dinosaur thirst level is 0 and doesnt have unconscious behaviour
        // add the unconscious behaviour and set the unconscious thirst to true
        if((dinosaur.getThirstLevel() == 0 && !containUnconsciousBehaviour)){
            ArrayList<Behaviour> newBehaviour = dinosaur.getBehaviour();
            newBehaviour.add(new UnconsciousBehaviour());
            dinosaur.setBehaviour(newBehaviour);
            dinosaur.setUnconsciousThirst(true);
        }

        // if the dinosaur thirst level is larger than 0 and contain unconscious behaviour and the boolean unconscious thirst is true
        // remove the unconscious behaviour and set the unconscious thirst to false and the unconscious due to thirst turn to 0
        else if((dinosaur.getThirstLevel() > 0 && containUnconsciousBehaviour && dinosaur.isUnconsciousThirst())){
            for(int i = 0; i < currentBehaviour.size(); i++){
                if(currentBehaviour.get(i) instanceof UnconsciousBehaviour){
                    currentBehaviour.remove(i);
                    dinosaur.setBehaviour(currentBehaviour);
                    break;
                }
            }
            dinosaur.setUnconsciousThirst(false);
            dinosaur.setUnconsciousDueToThirst(0);
        }


        // if contain unconscious behaviour and if is inconscious thirst then add 10 to the thirst level
        if(containUnconsciousBehaviour && JurassicParkGameMap.hasRain()){
            if(dinosaur.isUnconsciousThirst()){
                dinosaur.setThirstLevel(10);
            }
        }
    }


    /**
     * This function will decrease the foodlevel/hitpoint of the Dinosaur each turn
     * @param dinosaur the Dinosaur
     * @param hitPoints the current hitpoint/foodlevel
     */
    public static void minusHitPoints(Dinosaur dinosaur, int hitPoints){
        if(hitPoints > 0){
            dinosaur.hurt(1);
        }
    }


    /**
     * This function will check if the Dinosaur is an adult or not, if yes set is_baby boolean to false
     * @param dinosaur The Dinosaur
     */
    public static void checkAdult(Dinosaur dinosaur) {
        if (dinosaur instanceof Stegosaur || dinosaur instanceof Pterodactyl) {
            dinosaur.setAge(dinosaur.getAge()+1);
            if (dinosaur.getAge() > 30) {
                dinosaur.setIs_baby(false);
            }

        }
        else if(dinosaur instanceof Brachiosaur || dinosaur instanceof Allosaurs){
            dinosaur.setAge(dinosaur.getAge()+1);
            if (dinosaur.getAge() > 50) {
                dinosaur.setIs_baby(false);
            }
        }

    }

    /**
     * This function will check if the actor/Dinosaur lay an egg or not, if the pregnant time has reach the labour time,
     * then it will add an new egg to the location
     * @param dinosaur the dinosaur
     * @param map the gamemap
     */
    public static void checkLayEgg(Dinosaur dinosaur, GameMap map){
        if(dinosaur.hasCapability(DinosaurCapability.Pregnant.PREGNANT)){

            // Check for Stegosaur pregnant time 10
            if(dinosaur instanceof Stegosaur){
                if(dinosaur.getPregnant_time() > 10){
                    dinosaur.removeCapability(DinosaurCapability.Pregnant.PREGNANT);
                    map.locationOf(dinosaur).addItem(new Egg("Stegosaur", dinosaur.getHATCH_TIME()));
                    dinosaur.setPregnant_time(0);
                    System.out.println(dinosaur + " has lay an Egg at x:" + map.locationOf(dinosaur).x() + ", y:" + map.locationOf(dinosaur).x() );
                }
                else {
                    dinosaur.setPregnant_time(dinosaur.getPregnant_time()+1);

                }
            }

            // Check for Brachiosaur pregnant time 50
            else if(dinosaur instanceof Brachiosaur){
                if(dinosaur.getPregnant_time() > 50){
                    dinosaur.removeCapability(DinosaurCapability.Pregnant.PREGNANT);
                    map.locationOf(dinosaur).addItem(new Egg("Brachiosaur", dinosaur.getHATCH_TIME()));
                    dinosaur.setPregnant_time(0);
                    System.out.println(dinosaur + " has lay an Egg at x:" + map.locationOf(dinosaur).x() + ", y:" + map.locationOf(dinosaur).x() );

                }
                else {
                    dinosaur.setPregnant_time(dinosaur.getPregnant_time()+1);
                }
            }

            // Check for Allosaurs pregnant time 20
            else if(dinosaur instanceof Allosaurs){

                if(dinosaur.getPregnant_time() > 20){
                    dinosaur.removeCapability(DinosaurCapability.Pregnant.PREGNANT);
                    map.locationOf(dinosaur).addItem(new Egg("Allosaur",dinosaur.getHATCH_TIME()));
                    dinosaur.setPregnant_time(0);
                    System.out.println(dinosaur + " has lay an Egg at x:" + map.locationOf(dinosaur).x() + ", y:" + map.locationOf(dinosaur).x() );

                }
                else {
                    dinosaur.setPregnant_time(dinosaur.getPregnant_time()+1);
                }
            }

            // Check for Pterodacytls pregnant time 10
            else if(dinosaur instanceof Pterodactyl){
                ArrayList<Behaviour> currentBehaviour = dinosaur.getBehaviour();
                boolean containWalkToTreeBehaviour = currentBehaviour.stream().anyMatch(c -> c instanceof WalkToTreeBehaviour);
                // if the pregnant time is larger than 10 and the Pterodactyl is on a Tree
                // then it will lay the egg
                if(dinosaur.getPregnant_time() > 10 && (map.locationOf(dinosaur).getGround() instanceof Tree)){
                    dinosaur.removeCapability(DinosaurCapability.Pregnant.PREGNANT);
                    map.locationOf(dinosaur).addItem(new Egg("Pterodactyl", dinosaur.getHATCH_TIME()));
                    dinosaur.setPregnant_time(0);
                    System.out.println(dinosaur + " has lay an Egg on Tree at x:" + map.locationOf(dinosaur).x() + ", y:" + map.locationOf(dinosaur).x() );


                    // if it contain the walk to tree behaviour then it will remove it
                    if(containWalkToTreeBehaviour){
                        for(int i = 0; i< currentBehaviour.size(); i++){
                            if(dinosaur.getBehaviour().get(i) instanceof WalkToTreeBehaviour){
                                currentBehaviour.remove(i);
                                dinosaur.setBehaviour(currentBehaviour);
                                break;
                            }
                        }
                    }
                }

                // if the pregnant time is larger than 10 and the Pterodactyl is not on a Tree
                // add WalkToTreeBehaviour to the list of behaviour of Pterodactyl
                else if(dinosaur.getPregnant_time() > 10 && !(map.locationOf(dinosaur).getGround() instanceof Tree)){
                    if(!containWalkToTreeBehaviour){
                        currentBehaviour.add(new WalkToTreeBehaviour());
                        dinosaur.setBehaviour(currentBehaviour);
                    }

                }
                else {
                    dinosaur.setPregnant_time(dinosaur.getPregnant_time()+1);
                }
            }

        }
    }

    /**
     * This function will minus the thirst level of the dinosaur
     * @param dinosaur the dinosaur
     */
    public static void minusThirstLevel(Dinosaur dinosaur){
        dinosaur.setThirstLevel(Math.max(dinosaur.getThirstLevel() - 1, 0));
    }

    /**
     * This function will check if the Dinosaur's thirst level is below 40, if it is below 40
     * then it will have a ThirstyBehaviour
     * @param dinosaur
     */
    public static void checkThirsty(Dinosaur dinosaur) {
        ArrayList<Behaviour> currentBehaviour = dinosaur.getBehaviour();

        boolean containThirstyBehaviour = currentBehaviour.stream().anyMatch(c-> c instanceof ThirstyBehaviour);

        if((dinosaur.getThirstLevel() < 50) && !containThirstyBehaviour && dinosaur.isConscious()){
            ArrayList<Behaviour> newBehaviour = dinosaur.getBehaviour();
            newBehaviour.add(new ThirstyBehaviour());
            dinosaur.setBehaviour(newBehaviour);
        }

        else if(dinosaur.getThirstLevel() >= 50 && containThirstyBehaviour && dinosaur.isConscious()){
            for(int i = 0; i < currentBehaviour.size(); i++){
                if(currentBehaviour.get(i) instanceof ThirstyBehaviour){
                    currentBehaviour.remove(i);
                    dinosaur.setBehaviour(currentBehaviour);
                    break;
                }
            }
        }
    }

}
