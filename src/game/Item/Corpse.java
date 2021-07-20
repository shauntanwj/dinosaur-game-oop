package game.Item;

import edu.monash.fit2099.engine.*;
import game.Dinosaur.Allosaurs;
import game.Dinosaur.Brachiosaur;
import game.Dinosaur.Pterodactyl;
import game.Dinosaur.Stegosaur;

/**
 * A class representing a Corpse item
 */
public class  Corpse extends PortableItem {
    private static final char displayCoprse = '%';
    private static final String dead = "dead ";

    /**
     * the remain time of the corpse, the counter indicates how long the Corpse on Ground
     */
    private int remainTime, counter = 0, corpseHp;
    private Actor target;


    /**
     * Constructor for the Corpse object
     * @param target the actor that is dead
     * @param remainTime the number of turn the corpse will be remain on the Ground
     */
    public Corpse(Actor target, int remainTime) {
        super(dead + target, displayCoprse);
        this.remainTime = remainTime;
        this.target = target;

        if(target instanceof Stegosaur || target instanceof Allosaurs){
            this.corpseHp = 50;
        }
        else if(target instanceof Brachiosaur){
            this.corpseHp = 100;
        }
        else if(target instanceof Pterodactyl){
            this.corpseHp = 30;
        }
    }

    /**
     * This method will keep tracks of the Corpse object each turn, if the counter is equal to the remain time then
     * the corpse will be removed.
     * @param location
     */
    public void tick(Location location){
        super.tick(location);
        counter++;
        if(counter == remainTime){
            location.removeItem(this);
        }
        else if(corpseHp < 0){
            location.removeItem(this);
        }
    }

    /**
     * a method that return the actor that is already dead
     * @return
     */
    public Actor getTarget() {
        return target;
    }

    public int getCorpseHp() {
        return corpseHp;
    }

    public void setCorpseHp(int corpseHp) {
        this.corpseHp = corpseHp;
    }
}
