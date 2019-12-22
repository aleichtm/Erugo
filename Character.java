import java.util.Iterator;
import java.util.Scanner;
/**
 * Abstract class Character - fields that all characters have,
 * methods to override CharactersDo methods that don't vary between types of
 * characters (move and attack). Includes accessors and mutators for the
 * benefit of child classes.
 *
 * @author Ari
 * @version 2019-12-19
 */
public abstract class Character implements CharactersDo
{
    private String character;
    private int attack;
    private int location;
    private int health;
    private int maxHealth;
    private Scanner reader = new Scanner(System.in);

    /**
     * Constructor for objects of class Character
     */
    public Character(String character, int attack, int maxHealth)
    {
        this.character = character;
        this.attack = attack;
        this.maxHealth = maxHealth;
        health = maxHealth;
    }
    
    /**
     * Take input and either move to that direction or attack that direction
     * 
     * @param input the number in which direction to move/attack
     */
    @Override
    public void move(int input)
    {
        int oldLocation = location;
        int direction = input;
        int newLocation = Main.map.move(character, oldLocation, direction);
        if(!(newLocation == oldLocation) && 
           (Main.map.getCharacter(newLocation).equals(".") ||
           Main.map.getCharacter(newLocation).equals(character))) {
            location = newLocation;
        } else {
            attack(newLocation);
        }
    }
    
    /**
     * Lower health of Character being attacked. Trigger death if health<=0
     * 
     * @param location the location to be attacked
     */
    @Override
    public void attack(int location)
    {
        Iterator it = Main.characters.iterator();
        boolean found = false;
        while(!found && it.hasNext()) {
            //Downcasting found from https://www.leepoint.net/data/collections/iterators.html
            Character character = (Character)it.next();
            if(character.getLocation() == location) {
                character.setHealth(character.getHealth() - attack);
                System.out.println("Ouch!");
                if(character.getHealth() <= 0) {
                    character.die();
                }
                found = true;
            }
        }
    }

    /**
     * @return the character
     */
    public String getCharacter() {
        return character;
    }
    /**
     * @param character the character to set
     */
    public void setCharacter(String character) {
        this.character = character;
    }

    /**
     * @return the location
     */
    public int getLocation() {
        return location;
    }
    /**
     * @param location the location to set
     */
    public void setLocation(int location) {
        this.location = location;
    }

    /**
     * @return the health
     */
    public int getHealth() {
        return health;
    }
    /**
     * @param health the health to set
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * @return the maxHealth
     */
    public int getMaxHealth() {
        return maxHealth;
    }
    /**
     * @param maxHealth the maxHealth to set
     */
    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    /**
     * @return the reader
     */
    public Scanner getReader() {
        return reader;
    }
}
