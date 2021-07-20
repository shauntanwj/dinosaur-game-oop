package game.Action;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;
import game.Dinosaur.Allosaurs;
import game.Dinosaur.Pterodactyl;
import game.Ground.Bush;
import game.Dinosaur.Brachiosaur;
import game.Dinosaur.Stegosaur;
import game.Ground.Lake;
import game.Item.Corpse;
import game.Item.Egg;
import game.Item.Fruit;
import game.Ground.Tree;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author shauntan, johanazlan
 * @version 1.0.0
 * @see game.Action.FeedAction
 */

/**
 * A class that represent an action when the Dinosaur is eating
 */
public class EatAction extends Action {

    /**
     * The total fruits on the Bush or Tree
     */
    private int totalFruits = 0;

    private Random rand = new Random();


    /**
     * This function will check the Dinosaur species of the Actor and then eat the food from the current location
     * and remove the food from the current location and increase the Actor foodlevel/hitpoint
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return a String
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        Location actorLocation = map.locationOf(actor);
        int actor_x = actorLocation.x(), actor_y = actorLocation.y();
        // check if instance of Stegosaur if yes then search for Bush or Fruit on the current location
        if(actor instanceof Stegosaur){
            // if the location contain Fruits
            if(actorLocation.getItems().stream().anyMatch(c->c instanceof Fruit)) {
                    // check how many fruit on the current location and remove it from the list
                    for (int i = 0; i < actorLocation.getItems().size(); i++) {
                        if (actorLocation.getItems().get(i) instanceof Fruit) {
                            totalFruits += 1;
                            map.locationOf(actor).removeItem(actorLocation.getItems().get(i));
                        }
                    }
                }
            // if the current ground is an instance of Bush
            else if(actorLocation.getGround() instanceof Bush){
                Bush newBush = (Bush) map.locationOf(actor).getGround();
                // get the total fruit from the bush
                totalFruits = newBush.getListOfFruits().size();
                actor.heal(10*totalFruits);
                newBush.setListOfFruits(new ArrayList<Fruit>());
                return actor + " eat fruit from bush at x:" + actor_x + ", y:" + actor_y;
            }
                actor.heal(10*totalFruits);
                return actor + " eat fruit at x:" + actor_x + ", y:" + actor_y;
        }

        // check if instance of Brachiosaur if yes then search for Tree on the current location
        else if(actor instanceof Brachiosaur){
            Tree currentTree = (Tree) actorLocation.getGround();
            totalFruits = currentTree.getListOfFruits().size();
            actor.heal(5*totalFruits);
            currentTree.setListOfFruits(new ArrayList<Fruit>());
            return actor + " eat fruit from tree at x:" + actor_x + ", y:" + actor_y;
        }

        // check if instance of Allosaur if yes then search for Egg or Corpse on the current location
        else if(actor instanceof Allosaurs){
            // loop the current ground list to check if there's a corpse item
            for(int i= 0; i < actorLocation.getItems().size(); i++){
                if(actorLocation.getItems().get(i) instanceof Corpse){
                    Corpse corpse = (Corpse) actorLocation.getItems().get(i);

                    // eat the corpse of Stegosaur or Allosaurs and heal 50
                    if(corpse.getTarget() instanceof Stegosaur || corpse.getTarget() instanceof Allosaurs){
                        actor.heal(50);
                        System.out.println(corpse.getTarget() + " HP is " + corpse.getCorpseHp());
                        corpse.setCorpseHp(corpse.getCorpseHp() - 50);
                        System.out.println(corpse.getTarget() + " HP is " + corpse.getCorpseHp());
                        map.locationOf(actor).removeItem(actorLocation.getItems().get(i));
                        return actor + " eat corpse at x:" + actor_x + ", y:" + actor_y;
                    }
                    // eat the corpse of Brachiosaur and heal 100
                    else if(corpse.getTarget() instanceof Brachiosaur){
                        actor.heal(100);
                        System.out.println(corpse.getTarget() + " HP is " + corpse.getCorpseHp());
                        corpse.setCorpseHp(corpse.getCorpseHp() - 100);
                        System.out.println(corpse.getTarget() + " HP is " + corpse.getCorpseHp());
                        map.locationOf(actor).removeItem(actorLocation.getItems().get(i));
                        return actor + " eat corpse at x:" + actor_x + ", y:" + actor_y;
                    }
                    else if(corpse.getTarget() instanceof Pterodactyl){
                        actor.heal(30);
                        System.out.println(corpse.getTarget() + " HP is " + corpse.getCorpseHp());
                        corpse.setCorpseHp(corpse.getCorpseHp() - 30);
                        System.out.println(corpse.getTarget() + " HP is " + corpse.getCorpseHp());
                        map.locationOf(actor).removeItem(actorLocation.getItems().get(i));
                        return actor + " eat corpse at x:" + actor_x + ", y:" + actor_y;
                    }
                }
                // check if there's egg on the ground
                else if(actorLocation.getItems().get(i) instanceof Egg){
                    actor.heal(10);
                    return actor + " eat egg at x:" + actor_x + ", y:" + actor_y;
                }
            }
        }

        else if(actor instanceof Pterodactyl){
            Pterodactyl pterodactyl = (Pterodactyl) actor;
            if(actorLocation.getGround() instanceof Lake){
                Lake currentLake = (Lake) actorLocation.getGround();
                if(currentLake.getListOfFish().size() != 0){
                    int random_number = rand.nextInt(3);
                    int fishCaught = Math.min(random_number, currentLake.getListOfFish().size());
                    pterodactyl.heal(5 * fishCaught);
                    pterodactyl.setThirstLevel(pterodactyl.getThirstLevel() + 30);
                    return actor + " eat " + random_number + " fish from Lake at x:" + actor_x + ", y:" + actor_y;
                }
            }
            else{
                for(int i= 0; i < actorLocation.getItems().size(); i++){
                    if(actorLocation.getItems().get(i) instanceof Corpse){
                        Corpse corpse = (Corpse) actorLocation.getItems().get(i);

                        // eat the corpse of Stegosaur or Allosaurs and heal 50
                        if(corpse.getTarget() instanceof Stegosaur || corpse.getTarget() instanceof Allosaurs || corpse.getTarget() instanceof Pterodactyl){
                            actor.heal(10);
                            System.out.println(corpse.getTarget() + " HP is " + corpse.getCorpseHp());
                            corpse.setCorpseHp(corpse.getCorpseHp() - 10);
                            System.out.println(corpse.getTarget() + " HP is " + corpse.getCorpseHp());
                            return actor + " eat corpse at x:" + actor_x + ", y:" + actor_y;
                        }

                        // eat the corpse of Brachiosaur and heal 100
                        else if(corpse.getTarget() instanceof Brachiosaur){
                            actor.heal(10);
                            System.out.println(corpse.getTarget() + " HP is " + corpse.getCorpseHp());
                            corpse.setCorpseHp(corpse.getCorpseHp() - 10);
                            System.out.println(corpse.getTarget() + " HP is " + corpse.getCorpseHp());
                            map.locationOf(actor).removeItem(actorLocation.getItems().get(i));
                            return actor + " eat corpse at x:" + actor_x + ", y:" + actor_y;
                        }
                    }
                }
            }
        }
        return actor + " has not eaten";
    }

    @Override
    public String menuDescription(Actor actor) {
        return "";
    }
}
