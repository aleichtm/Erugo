import java.util.ArrayList;
import java.util.Iterator;

/**
* Write a description of class Player here.
*
* @author Ari
* @version 2019-12-19
*/
public class Player extends Character implements CharactersDo
{
    public static boolean dead = false;
    
    /**
     * Constructor for objects of class Player
     */
    public Player(String character, int attack, int location, int maxHealth)
    {
        super(character, attack, maxHealth);
        setLocation(location);
        Main.map.setCharacter(getCharacter(), getLocation());
    }
    
    /**
     * Set dead to true
     */
    public void die()
    {
        dead = true;
    }
}
