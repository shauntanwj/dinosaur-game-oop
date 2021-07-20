package game.Behaviour;

import edu.monash.fit2099.engine.*;
import game.Action.DrinkAction;
import game.Distance;
import game.Ground.Lake;

/**
 * @author shauntan, johanazlan
 * @version 1.0.0
 * @see game.Application
 */

/**
 * A class which represent a Behaviour when a Dinosaur is thirsty.
 */
public class ThirstyBehaviour implements Behaviour{

    /**
     * the location of the Dinosaur, the location of the Lake
     */
    private Location actorLocation, lakeLocation;

    @Override
    public Action getAction(Actor actor, GameMap map) {
        actorLocation = map.locationOf(actor);
        System.out.println(actor + " at x:" + actorLocation.x() + ", y:" + actorLocation.y() + " is getting thirsty!");
        lakeLocation = adjacentLake(actor, map);
        if(lakeLocation != null){
            return new DrinkAction(lakeLocation);
        }
        return findNearestLake(actor, map);
    }

    /**
     * This method will return the location of the lake if there's a Lake besides the Dinosaur
     * if there's no lake besides the Dinosaur, return null
     * @param actor the Dinosaur
     * @param map the current Gamemap
     * @return the location of the lake if there is, otherwise null
     */
    public Location adjacentLake(Actor actor, GameMap map){
        Ground oneLeft, oneRight, oneUp, oneDown;
        int actor_x = map.locationOf(actor).x(), actor_y = map.locationOf(actor).y();

        // check if the left side of the Dinosaur is a Lake
        if (actor_x - 1 > 0) {
            oneLeft = map.at(actor_x - 1, actor_y).getGround();
            if(oneLeft instanceof Lake){
                return map.at(actor_x - 1, actor_y);
            }
        }
        // check if the right side of the Dinosaur is a Lake
        if (actor_x + 1 < map.getXRange().max()) {
            oneRight = map.at(actor_x + 1, actor_y).getGround();
            if(oneRight instanceof Lake){
                return map.at(actor_x + 1, actor_y);
            }
        }

        // check if the above of the Dinosaur is a Lake
        if (actor_y - 1 > 0) {
            oneUp = map.at(actor_x, actor_y - 1).getGround();
            if(oneUp instanceof Lake){
                return map.at(actor_x, actor_y - 1);
            }
        }

        // check if the bottom of the Dinosaur is a Lake
        if (actor_y + 1 < map.getYRange().max()) {
            oneDown = map.at(actor_x, actor_y + 1).getGround();
            if(oneDown instanceof Lake){
                return map.at(actor_x, actor_y + 1);
            }
        }
        return null;
    }

    /**
     * This method will loop through the whole map to find the nearest lake from the dinosaur
     * and move one step closer to the lake
     * @param actor the Dinosaur
     * @param map the current gamemap
     * @return FollowLocationBehaviour if there's a Lake on the map, WanderBehavour otherwise
     */
    public Action findNearestLake(Actor actor, GameMap map){
        actorLocation = map.locationOf(actor);
        int minimumDistance = -1, distanceToLake = 0;
        Location lakeLocation = null;

        // loop through the whole map
        for(int y = 0; y < map.getYRange().max(); y++){
            for(int x = 0; x < map.getXRange().max(); x++){
                Location there = map.at(x, y);

                // check if that ground is an instance of Lake
                if(there.getGround() instanceof Lake){
                    distanceToLake = Distance.distance(actorLocation, there);
                    if(minimumDistance == -1) {
                        minimumDistance = distanceToLake;
                        lakeLocation = there;
                    }
                    else if(distanceToLake < minimumDistance && distanceToLake != 0){
                        minimumDistance = distanceToLake;
                        lakeLocation = there;
                    }
                }
            }
        }

        // if currently no Lake on the map, return Wander around action
        if(lakeLocation == null){
            return new WanderBehaviour().getAction(actor, map);
        }

        // if there's Lake on the map, then move on step closer
        else {
            return new FollowLocationBehaviour(lakeLocation).getAction(actor, map);
        }
    }

}
