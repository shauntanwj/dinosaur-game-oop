package game.Behaviour;

import edu.monash.fit2099.engine.*;
import game.Dinosaur.Pterodactyl;
import game.DinosaurCapability;
import game.Action.MatingAction;
import game.Distance;
import game.Ground.Tree;

/**
 * @author shauntan, johanazlan
 * @version 1.1.0
 * @see game.Behaviour.FollowBehaviour
 */

/**
 * A class which represent a Behaviour when a Dinosaur is breeding.
 */
public class BreedingBehaviour implements Behaviour {

    /**
     * the target for the Dinosaur
     */
    private Actor target = null;
    private int minimum;


    /**
     * This function will first check the adjacent square of the Dinosaur if there's a same species of Dinosaur and different gender,
     * if there is then it will return a MatingAction(), if not then it will loop through the whole map to find the nearest
     * same species Dinosaur with different gender and return a FollowBehaviour to move one step closer to the target
     * @param actor the Actor acting
     * @param map the GameMap containing the Actor
     * @return
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        Actor oneLeft, oneRight, oneUp, oneDown;
        int actor_x = map.locationOf(actor).x(), actor_y = map.locationOf(actor).y();
        System.out.println(actor + " at x:" + actor_x + ", y:" + actor_y + " is breeding");

        if(actor instanceof Pterodactyl){
            return pterodactylBreeding(actor, map);
        }
        else{
            // Check for left adjacent square if there's same species, different sex
            if (actor_x - 1 > 0) {
                boolean containActorLeft = map.at(actor_x - 1, actor_y).containsAnActor();
                oneLeft = map.at(actor_x - 1, actor_y).getActor();
                // if the dinosaur on the left is same species, different sex and not pregnant
                if (containActorLeft && checkSameSpecies(oneLeft, actor) && (checkDiffSex(actor, oneLeft) != null) && notPregnant(oneLeft)) {
                    Actor female = checkDiffSex(actor, oneLeft);
                    return new MatingAction(female);
                }
            }

            // Check for right adjacent square if there's same species, different sex
            if (actor_x + 1 < map.getXRange().max()) {
                boolean containActorRight = map.at(actor_x + 1, actor_y).containsAnActor();
                oneRight = map.at(actor_x + 1, actor_y).getActor();
                // if the dinosaur on the right is same species, different sex and not pregnant
                if (containActorRight && checkSameSpecies(oneRight, actor) && (checkDiffSex(actor, oneRight) != null) && notPregnant(oneRight)) {
                    Actor female = checkDiffSex(actor, oneRight);
                    return new MatingAction(female);
                }
            }

            // Check for upper adjacent square if there's same species, different sex
            if (actor_y - 1 > 0) {
                boolean containActorUp = map.at(actor_x, actor_y - 1).containsAnActor();
                oneUp = map.at(actor_x, actor_y - 1).getActor();
                // if the dinosaur above is same species, different sex and not pregnant
                if (containActorUp && checkSameSpecies(oneUp, actor) && (checkDiffSex(actor, oneUp) != null) && notPregnant(oneUp)) {
                    Actor female = checkDiffSex(actor, oneUp);
                    return new MatingAction(female);
                }
            }

            // Check for bottom adjacent square if there's same species, different sex
            if (actor_y + 1 < map.getYRange().max()) {
                boolean containActorDown = map.at(actor_x, actor_y + 1).containsAnActor();
                oneDown = map.at(actor_x, actor_y + 1).getActor();
                // if the dinosaur at the bottom  is same species, different sex and not pregnant
                if (containActorDown && checkSameSpecies(oneDown, actor) && (checkDiffSex(actor, oneDown) != null) && notPregnant(oneDown)) {
                    Actor female = checkDiffSex(actor, oneDown);
                    return new MatingAction(female);
                }
            }

            minimum =  0;
            // Loop the whole map to find the dinosaur with the same species, opposite sex
            for (int j = 0; j < map.getYRange().max(); j++) {
                for (int i = 0; i < map.getXRange().max(); i++) {
                    // check if there's actor at the location and if the location actor is the same species as the actor
                    Location current_loc = map.at(i,j);
                    if ((current_loc.containsAnActor()) && (checkSameSpecies(current_loc.getActor(), actor)) &&
                            (current_loc.getActor() != actor) && (checkDiffSex(current_loc.getActor(), actor) != null)) {

                        // here is the location of the actor
                        Location here = map.locationOf(actor);

                        // there is the location of the target
                        Location there = map.at(i, j);
                        int distance_to_target = Distance.distance(here, there);
                        if (minimum == 0) {
                            target = there.getActor();
                            minimum = distance_to_target;

                        } else if (distance_to_target <= minimum) {
                            target = there.getActor();
                            minimum = distance_to_target;
                        }
                    }

                }
            }

            // if there's no target to go to, then wanders around
            if(target == null){
                return new WanderBehaviour().getAction(actor, map);
            }
            else{
                if(minimum == 0){
                    return new WanderBehaviour().getAction(actor, map);
                }
                else {
                    return new FollowBehaviour(target).getAction(actor, map);
                }
            }
        }



    }

    /**
     * This function will return the Actor that is a Female
     * @param one first Actor
     * @param two second Actor
     * @return female actor
     */
    private Actor checkDiffSex(Actor one, Actor two){
        if((one.hasCapability(DinosaurCapability.Gender.FEMALE)) && (two.hasCapability(DinosaurCapability.Gender.FEMALE))){
            return null;
        }
        else if(one.hasCapability(DinosaurCapability.Gender.FEMALE)){
            return one;
        }
        else if(two.hasCapability(DinosaurCapability.Gender.FEMALE)){
            return two;
        }
        return null;
    }

    /**
     * This function will check if the two Actor/Dinosaur is the same species
     * @param one first Actor
     * @param two second Actor
     * @return true if same species, otherwise false
     */
    private boolean checkSameSpecies(Actor one, Actor two){
        return one.getClass() == two.getClass();
    }

    /**
     * This function will check if the Actor is pregnant or not
     * @param one the actor
     * @return true if pregnant, otherwise false
     */
    private boolean notPregnant(Actor one){
        return (one.hasCapability(DinosaurCapability.Pregnant.PREGNANT) == false);
    }

    /**
     * This method will implement the breeding behaviour for Pterodactyl
     * @param actor the Pterodactyl
     * @param map the current game map
     * @return
     */
    private Action pterodactylBreeding(Actor actor, GameMap map){
        Actor oneLeft, oneRight, oneUp, oneDown;
        Pterodactyl pterodactyl = (Pterodactyl) actor;
        Location actorLocation = map.locationOf(pterodactyl);
        int actor_x = actorLocation.x(), actor_y = actorLocation.y();

        // if pterodactyl is a Male and it's on a Tree check adjacent if has a tree and has a Pterodactyl
        // then it will find a female pterodactyl to mate
        if(pterodactyl.hasCapability(DinosaurCapability.Gender.MALE) && map.locationOf(pterodactyl).getGround() instanceof Tree){
            System.out.println(pterodactyl + " is a male and at tree and finding a mate");

            // Check for left adjacent square if there's same species, different sex, get the female dinosaur and mate them
            if (actor_x - 1 > 0 && (map.at(actor_x-1, actor_y).getGround() instanceof Tree)) {
                boolean containActorLeft = map.at(actor_x - 1, actor_y).containsAnActor();
                oneLeft = map.at(actor_x - 1, actor_y).getActor();
                // if the dinosaur on the left is same species, different sex and not pregnant
                if (containActorLeft && checkSameSpecies(oneLeft, actor) && (checkDiffSex(actor, oneLeft) != null) && notPregnant(oneLeft)) {
                    Actor female = checkDiffSex(actor, oneLeft);
                    return new MatingAction(female);
                }
                else{
                    return findNearestPterodactyl(actor, map);
                }
            }

            // Check for right adjacent square if there's same species, different sex
            if (actor_x + 1 < map.getXRange().max() && (map.at(actor_x+1, actor_y).getGround() instanceof Tree)) {
                boolean containActorRight = map.at(actor_x + 1, actor_y).containsAnActor();
                oneRight = map.at(actor_x + 1, actor_y).getActor();
                // if the dinosaur on the right is same species, different sex and not pregnant, get the female dinosaur and mate them
                if (containActorRight && checkSameSpecies(oneRight, actor) && (checkDiffSex(actor, oneRight) != null) && notPregnant(oneRight)) {
                    Actor female = checkDiffSex(actor, oneRight);
                    return new MatingAction(female);
                }
                else{
                    return findNearestPterodactyl(actor, map);
                }
            }

            // Check for upper adjacent square if there's same species, different sex
            if (actor_y - 1 > 0 && (map.at(actor_x, actor_y-1).getGround() instanceof Tree)) {
                boolean containActorUp = map.at(actor_x, actor_y - 1).containsAnActor();
                oneUp = map.at(actor_x, actor_y - 1).getActor();
                // if the dinosaur above is same species, different sex and not pregnant, get the female dinosaur and mate them
                if (containActorUp && checkSameSpecies(oneUp, actor) && (checkDiffSex(actor, oneUp) != null) && notPregnant(oneUp)) {
                    Actor female = checkDiffSex(actor, oneUp);
                    return new MatingAction(female);
                }
                else{
                    return findNearestPterodactyl(actor, map);
                }
            }

            // Check for bottom adjacent square if there's same species, different sex
            if (actor_y + 1 < map.getYRange().max() && (map.at(actor_x, actor_y+1).getGround() instanceof Tree)) {
                boolean containActorDown = map.at(actor_x, actor_y + 1).containsAnActor();
                oneDown = map.at(actor_x, actor_y + 1).getActor();
                // if the dinosaur at the bottom  is same species, different sex and not pregnant, get the female dinosaur and mate them
                if (containActorDown && checkSameSpecies(oneDown, actor) && (checkDiffSex(actor, oneDown) != null) && notPregnant(oneDown)) {
                    Actor female = checkDiffSex(actor, oneDown);
                    return new MatingAction(female);
                }
                else{
                    return findNearestPterodactyl(actor, map);
                }
            }
            else{
                return findNearestPterodactyl(actor, map);
            }
        }

        else if(pterodactyl.hasCapability(DinosaurCapability.Gender.MALE) && !(map.locationOf(pterodactyl).getGround() instanceof Tree)){
            System.out.println(pterodactyl + " is a male and finding a mate");
            return findNearestPterodactyl(actor, map);
        }

        // if pterodactyl is a Female then it will find a Tree and stay on it wait for a Male Pterodactyl and mate
        else if(pterodactyl.hasCapability(DinosaurCapability.Gender.FEMALE) && !(map.locationOf(pterodactyl).getGround() instanceof Tree)){
            System.out.println(pterodactyl + " is a female and walking to Tree and wait for mate");
            return Pterodactyl.findNearestUnoccupiedTree(actor, map);
        }

        // if it's a female and it's on a tree then just do nothing
        else if((pterodactyl.hasCapability(DinosaurCapability.Gender.FEMALE) && (map.locationOf(pterodactyl).getGround() instanceof Tree))){
            System.out.println(pterodactyl + " is a female and waiting for mate");
            return new DoNothingAction();
        }
        return new DoNothingAction();
    }

    /**
     * This method will find the nearest Pterodactyl and go one step closer to the tree that is nearest to the Pterodactyl
     * @param actor the Pterodactyl
     * @param map the current game map
     * @return an action which go one step closer to the target location
     */
    private Action findNearestPterodactyl(Actor actor, GameMap map){
        Location actorLocation = map.locationOf(actor);

        int minimumDistance = -1, distanceToMate = 0;
        Location mateLocation = null;


        // loop through the whole map
        for(int y = 0; y < map.getYRange().max(); y++) {
            for (int x = 0; x < map.getXRange().max(); x++) {
                Location there = map.at(x, y);

                // if there is a Tree and contains an Actor
                if (there.getGround() instanceof Tree && there.containsAnActor()) {
                    // if there actor is a Pterodactyl and is a Female then
                    if(there.getActor() instanceof Pterodactyl && there.getActor().hasCapability(DinosaurCapability.Gender.FEMALE) && adjacentTree(there, map)!=null){
                        Location nearestTreeToMate = adjacentTree(there, map);
                        distanceToMate = Distance.distance(actorLocation, nearestTreeToMate);
                        if (minimumDistance == -1) {
                            minimumDistance = distanceToMate;
                            mateLocation = nearestTreeToMate;
                        } else if (distanceToMate < minimumDistance && distanceToMate != 0) {
                            minimumDistance = distanceToMate;
                            mateLocation = nearestTreeToMate;
                        }
                    }

                }
            }
        }

        if(mateLocation == null){
            return new WanderBehaviour().getAction(actor, map);
        }

        // if there's mate on the map, then move on step closer
        else {
            System.out.println(actor + " target is at x:" + mateLocation.x() + " y:" + mateLocation.y());
            return new FollowLocationBehaviour(mateLocation).getAction(actor, map);
        }

    }

    /**
     * This method is to check if adjacent square is a Tree or not
     * @param location the current location
     * @param map the current game map
     * @return the location of the Tree if there is, null otherwise
     */
    private Location adjacentTree(Location location, GameMap map){
        int location_x = location.x(),  location_y = location.y();
        if (location_x - 1 > 0 && (map.at(location_x-1, location.y()).getGround() instanceof Tree)) {
           return map.at(location_x-1, location.y());
        }

        if (location_x + 1 < map.getXRange().max() && (map.at(location_x+1, location_y).getGround() instanceof Tree)) {
            return map.at(location_x+1, location_y);
        }

        if (location_y - 1 > 0 && (map.at(location_x, location_y-1).getGround() instanceof Tree)) {
            return map.at(location_x, location_y-1);
        }

        if (location_y + 1 < map.getYRange().max() && (map.at(location_x, location_y+1).getGround() instanceof Tree)) {
            return map.at(location_x, location_y+1);
        }

        return null;

    }



}
