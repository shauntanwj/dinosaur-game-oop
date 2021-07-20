package game.Behaviour;

import edu.monash.fit2099.engine.*;
import game.Action.EatAction;
import game.Dinosaur.*;
import game.DinosaurCapability;
import game.Distance;
import game.Ground.Bush;
import game.Ground.Lake;
import game.Ground.Tree;
import game.Item.Corpse;
import game.Item.Egg;
import game.Item.Fruit;


/**
 * @author shauntan, johanazlan
 * @version 1.1.0
 * @see game.Behaviour.UnconsciousBehaviour
 */

/**
 * A class which represent a Behaviour when an Dinosaur is hungry.
 */
public class HungryBehaviour implements Behaviour {

    /**
     * the location of the Actor
     */
    private Location actorLocation;



    /**
     * This method will check if the actor is an instance of which Dinosaur then it will first check the current
     * location of the Actor if there's food for that Dinosaur, if it has then it will return an EatAction(),
     * otherwise it will call the function findNearestFood() to get the nearest food around the Actor
     * @param actor the Actor acting
     * @param map the GameMap containing the Actor
     * @return
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        actorLocation = map.locationOf(actor);
        System.out.println(actor + " at x:" + actorLocation.x() + ", y:" + actorLocation.y() + " is hungry");

        // check if current ground contain Fruit or Bush
        if(actor instanceof Stegosaur){
            boolean containFruit = actorLocation.getItems().stream().anyMatch(c->c instanceof Fruit);

            if(containFruit){
                return new EatAction();
            }
            if((actorLocation.getGround() instanceof Bush) ){
                Bush currentBush = (Bush) actorLocation.getGround();
                if(currentBush.getListOfFruits().size() != 0){
                    return new EatAction();
                }

                else{
                    return findNearestFood(actor, map);
                }
            }
            else{
                return findNearestFood(actor, map);
            }

        }

        // check current ground contain Tree
        else if(actor instanceof Brachiosaur){
            boolean containTree = actorLocation.getGround() instanceof Tree;
            if(containTree){
                Tree currentTree = (Tree) actorLocation.getGround();
                if(currentTree.getListOfFruits().size() != 0){
                    return new EatAction();
                }
            }
            else{
                return findNearestFood(actor, map);
            }

        }

        // check current ground contain Corpse or Egg
        // otherwise it will check for adjacent square to check if there's Stegosaur, or a Pterodactyl that is walking
        // call attackDinosaur() method
        else if(actor instanceof Allosaurs){
            boolean containCorpse = actorLocation.getItems().stream().anyMatch(c -> c instanceof Corpse);
            boolean containEgg = actorLocation.getItems().stream().anyMatch(c -> c instanceof Egg);

            if(containCorpse || containEgg){
                return new EatAction();
            }
            else{
                // check the left adjacent square if have a Stegosaur or a walking Pterodactyl
                if(actorLocation.x()-1 > 0 && map.at(actorLocation.x() - 1, actorLocation.y()).containsAnActor()){
                    Actor oneLeft = map.at(actorLocation.x() - 1, actorLocation.y()).getActor();
                    if(oneLeft.isConscious()){
                        if(oneLeft instanceof Stegosaur){
                            attackDinosaur(actor, oneLeft);
                        }
                        else if(oneLeft instanceof Pterodactyl && oneLeft.hasCapability(DinosaurCapability.Status.WALKING)){
                            attackDinosaur(actor, oneLeft);
                            map.removeActor(oneLeft);
                        }
                    }

                    else{
                        return findNearestFood(actor, map);
                    }
                }
                // check the right adjacent square if have a Stegosaur or a walking Pterodactyl
                if(actorLocation.x()+1 < map.getXRange().max() && map.at(actorLocation.x() + 1, actorLocation.y()).containsAnActor()){
                    Actor oneRight = map.at(actorLocation.x() + 1, actorLocation.y()).getActor();
                    if(oneRight.isConscious()){
                        if(oneRight instanceof Stegosaur){
                            attackDinosaur(actor, oneRight);
                        }
                        else if(oneRight instanceof Pterodactyl && oneRight.hasCapability(DinosaurCapability.Status.WALKING)){
                            attackDinosaur(actor, oneRight);
                            map.removeActor(oneRight);
                        }
                    }

                    else{
                        return findNearestFood(actor, map);
                    }
                }
                // check the upper adjacent square if have a Stegosaur or a walking Pterodactyl
                if(actorLocation.y()-1 > 0 && map.at(actorLocation.x(), actorLocation.y()-1).containsAnActor()){
                    Actor oneUp = map.at(actorLocation.x(), actorLocation.y()-1).getActor();
                    if(oneUp.isConscious()){
                        if(oneUp instanceof Stegosaur){
                            attackDinosaur(actor, oneUp);
                        }
                        else if(oneUp instanceof Pterodactyl && oneUp.hasCapability(DinosaurCapability.Status.WALKING)){
                            attackDinosaur(actor, oneUp);
                            map.removeActor(oneUp);
                        }
                    }

                    else{
                        return findNearestFood(actor, map);
                    }
                }
                // check the bottom adjacent square if have a Stegosaur or a walking Pterodactyl
                if(actorLocation.y()+1 < map.getYRange().max() && map.at(actorLocation.x(), actorLocation.y()+1).containsAnActor()){
                    Actor oneDown = map.at(actorLocation.x(), actorLocation.y()+1).getActor();
                    if(oneDown.isConscious()){
                        if(oneDown instanceof Stegosaur){
                            attackDinosaur(actor, oneDown);
                        }
                        else if(oneDown instanceof Pterodactyl && oneDown.hasCapability(DinosaurCapability.Status.WALKING)){
                            attackDinosaur(actor, oneDown);
                            map.removeActor(oneDown);
                        }
                    }

                    else{
                        return findNearestFood(actor, map);
                    }
                }
                return findNearestFood(actor, map);
            }
        }

        // if it's a Pterodactyl check if current ground has a lake or a corpse
        else if(actor instanceof Pterodactyl){
            boolean containLake = actorLocation.getGround() instanceof Lake;
            boolean containCorpse = actorLocation.getItems().stream().anyMatch(c -> c instanceof Corpse);

            if(containLake || containCorpse){
                return new EatAction();
            }

            else{
                return findNearestFood(actor, map);
            }
        }
        return new WanderBehaviour().getAction(actor, map);
    }


    /**
     * This function will loop through the whole Gamemap to search for the nearest food source for the Actor
     * If currently there's no food for the Actor, then it will wander around first, else it will return a FollowFoodBehaviuour.getAction()
     * to move one step nearer to the nearest food source
     * @param actor
     * @param map
     * @return
     */
    public Action findNearestFood(Actor actor, GameMap map){
        actorLocation = map.locationOf(actor);
        int minimumDistance = -1, distanceToFood = 0;
        Location foodLocation = null;

        // loop through the whole map
        for(int y = 0; y < map.getYRange().max(); y++){
            for(int x = 0; x < map.getXRange().max(); x++){
                Location there = map.at(x, y);

                // if it's a Stegosaur
                if(actor instanceof Stegosaur){
                    // check if that location ground is a Bush or have Fruits on the ground
                    if(there.getGround() instanceof Bush || there.getItems().stream().anyMatch(c->c instanceof Fruit)){
                        // calculate the distance from the actor location to that location
                        distanceToFood = Distance.distance(actorLocation, there);
                        if(minimumDistance == -1) {
                            minimumDistance = distanceToFood;
                            foodLocation = there;
                        }
                        else if(distanceToFood < minimumDistance && distanceToFood != 0){
                            minimumDistance = distanceToFood;
                            foodLocation = there;
                        }
                    }
                }

                // if it's a Brachiosaur
                else if(actor instanceof Brachiosaur){
                    // check if that location ground is a Tree instance
                    if(there.getGround() instanceof Tree){
                        distanceToFood = Distance.distance(actorLocation, there);
                        if(minimumDistance == -1) {
                            minimumDistance = distanceToFood;
                            foodLocation = there;
                        }
                        else if(distanceToFood < minimumDistance && distanceToFood != 0){
                            minimumDistance = distanceToFood;
                            foodLocation = there;
                        }

                    }
                }
                // if it's a Allosaur
                else if(actor instanceof Allosaurs){
                    // check if that location ground has Corpse, Egg or Stegosaur
                    if(there.getItems().stream().anyMatch(c -> c instanceof Corpse || c instanceof Egg) || there.getActor() instanceof Stegosaur){
                        distanceToFood = Distance.distance(actorLocation, there);
                        if(minimumDistance == -1) {
                            minimumDistance = distanceToFood;
                            foodLocation = there;
                        }
                        else if(distanceToFood < minimumDistance && distanceToFood != 0){
                            minimumDistance = distanceToFood;
                            foodLocation = there;
                        }
                    }

                }
                // if it's a Pterodactyl
                else if(actor instanceof Pterodactyl){
                    // check if that location ground is a Lake or has a Coprse on the ground
                    if(there.getGround() instanceof Lake || there.getItems().stream().anyMatch(c -> c instanceof Corpse)){
                        distanceToFood = Distance.distance(actorLocation, there);
                        if(minimumDistance == -1) {
                            minimumDistance = distanceToFood;
                            foodLocation = there;
                        }
                        else if(distanceToFood < minimumDistance && distanceToFood != 0){
                            minimumDistance = distanceToFood;
                            foodLocation = there;
                        }

                    }
                }
            }
        }

        // if currently no food on the map, return Wander around action
        if(foodLocation == null){
            return new WanderBehaviour().getAction(actor, map);
        }

        // if there's food on the map, then move on step closer
        else {
            return new FollowLocationBehaviour(foodLocation).getAction(actor, map);
        }

    }



    /**
     * This function will increase the food level of the attacker and decrease the food level of the target
     * @param actor the attacker
     * @param target the target of the attacker
     */
    private void attackDinosaur(Actor actor, Actor target){
        Dinosaur allosaur = (Dinosaur) actor;
        // if the target is a Stegosaur
        if(target instanceof Stegosaur){
            // check if it's a baby, hurt the Stegosaur only 10
            if(allosaur.isIs_baby()){
                target.hurt(10);
                actor.heal(10);
            }
            else{
                // if not a baby hurt the Stegosaur 20
                target.hurt(20);
                actor.heal(20);
            }
        }

        // if the target is a Pterodactyl
        else if(target instanceof Pterodactyl){
            target.hurt(100);
            actor.heal(100);

        }

        System.out.println(actor + " attacked " + target);

    }



}


