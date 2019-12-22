import java.util.ArrayList;
/**
 * Actions that all Characters Do
 *
 * @author Ari
 * @version 2019-12-17
 */
public interface CharactersDo
{
    /**
     * @param direction int representation of direction to move
     */
    void move(int direction);
    
    /**
     * @param location the location of the thing being attacked
     */
    void attack(int location);
    
    void die();
}
