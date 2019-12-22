import java.util.Iterator;
import java.util.Random;
/**
 * Class for NonPlayer Characters. Has check in case placed in invalid spot,
 * boolean for whether it has the wincondition, and specific die() method
 *
 * @author Ari
 * @version 2019-12-19
 */
public class NonPlayer extends Character implements CharactersDo
{
    private Random rand;
    private boolean alive;
    private boolean hasWin;

    /**
     * Constructor for objects of class NonPlayer
     */
    public NonPlayer(String character, int attack, int location,
                     int maxHealth, boolean hasWin)
    {
        super(character, attack, maxHealth);
        boolean valid = false;
        int newLocation = location;
        rand = new Random();
        while(!valid) {
            if(Main.map.getCharacter(newLocation).equals(".")) {
                setLocation(newLocation);
                Main.map.setCharacter(getCharacter(), getLocation());
                valid = true;
            } else {
                newLocation = rand.nextInt(2046);
            }
        }
        this.hasWin = hasWin;
        alive = true;
    }

    /**
     * Print dying text, remove from Main.characters,
     * trigger main.win() if hasWin
     */
    @Override
    public void die()
    {
        System.out.println("You hear a scream as another one bites the dust");
        Main.map.setCharacter(".", getLocation());
        Main.killed.add(getCharacter());
        Iterator it = Main.characters.iterator();
        boolean found = false;
        while(!found && it.hasNext()) {
            Character character = (Character)it.next();
            if(character.getLocation() == getLocation()) {
                found = true;
                Main.characters.remove(character);
            }
        }
        if(hasWin) {
            Main.win();
        }
    }
}
