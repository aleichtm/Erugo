import java.awt.Font;
import java.util.HashSet;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
/**
 * UI and logic for map. map operates with location represented by an int
 * Includes move() method so that other classes don't have to worry too much
 * about how the map works
 *
 * @author Ari
 * @version 2019-12-17
 */
public class Map extends JFrame
{
    private Font font;
    private JPanel panel;
    private JTextArea mapArea;
    private String map;

    //Using HashSets rather than ArrayLists because
    //https://stackoverflow.com/a/32552348
    private HashSet<Integer> northEdge = new HashSet<>();
    private HashSet<Integer> eastEdge = new HashSet<>();
    private HashSet<Integer> southEdge = new HashSet<>();
    private HashSet<Integer> westEdge = new HashSet<>();
    private HashSet<Integer> outOfBounds = new HashSet<>();

    /**
     * Constructor for objects of class Map
     */
    public Map()
    {
        setSize(1000, 650);

        font = new Font("Monospaced", Font.PLAIN, 18);
        panel = new JPanel();
        mapArea = new JTextArea(24, 80);
        map = "";

        mapArea.setFont(font);
        mapArea.setEditable(false);

        panel.add(mapArea);
        add(panel);
        setVisible(true);

        setInitialMap();
        setBounds();
    }

    /**
     * Set up initial map.
     * 89 characters per line including the \n (one character)
     * Total of 2047 characters
     */
    private void setInitialMap()
    {
        for(int i = 0; i < 23; i++) {
            map += "........................................................................................\n";
        }
        mapArea.append(map); 
    }

    /**
     * Set up HashSets to hold edges and bounds
     */
    private void setBounds()
    {
        for(int i = 0; i < 88; i++) {
            northEdge.add(i);
        }
        for(int i = 87; i < 2046; i += 89) {
            eastEdge.add(i);
        }
        for(int i = 1958; i < 2046; i++) {
            southEdge.add(i);
        }
        for(int i = 0; i < 1959; i += 89) {
            westEdge.add(i);
        }
        for(int i = 88; i < 2047; i += 89) {
            outOfBounds.add(i);
        }
    }

    /**
     * Move character to new location one distance away.
     * Includes checks so you don't wrap around the map
     * 
     * @param character String of character used to represent character
     * @param oldLocation int of location where character is prior to moving
     * @param direction int of direction to move (from numpad)
     * @return the new location
     */
    public int move(String character, int oldLocation, int direction)
    {
        int location = oldLocation;
        if(direction < 1 || direction == 5 || direction > 9 ||
           outOfBounds.contains(location)) {
            System.out.println("Please input a valid direction");
        } else if(direction == 1 && !(southEdge.contains(location) ||
                                       westEdge.contains(location))) {
            location += 88;
        } else if(direction == 2 && !southEdge.contains(location)) {
            location += 89;
        } else if(direction == 3 && !(southEdge.contains(location) ||
                                       eastEdge.contains(location))) {
            location += 90;
        } else if(direction == 4 && !westEdge.contains(location)) {
            location -= 1;
        } else if(direction == 6 && !eastEdge.contains(location)) {
            location += 1;
        } else if(direction == 7 && !(northEdge.contains(location) ||
                                       westEdge.contains(location))) {
            location -= 90;
        } else if(direction == 8 && !northEdge.contains(location)) {
            location -=89;
        } else if(direction == 9 && !(northEdge.contains(location) ||
                                       eastEdge.contains(location))) {
            location -= 88;
        } else {
            System.out.println("A fight has been had with the wall and");
            System.out.println("the wall won. A cry of pain is heard:");
        }
        if(!(location == oldLocation) &&
           getCharacter(location).equals(".")) {
            mapArea.replaceRange(".", oldLocation, oldLocation + 1);
            mapArea.replaceRange(character, location, location + 1);
        }
        return location;
    }
    
    /**
     * Show new character in given location
     * 
     * @param character String representation of character
     * @param location int of location to be spawned
     */
    public void setCharacter(String character, int location)
    {
        mapArea.replaceRange(character, location, location + 1);
    }
    
    /**
     * @param location the location to check
     * @return character at given location
     */
    public String getCharacter(int location)
    {
        try {
            return mapArea.getText(location, 1);
        } catch(BadLocationException ble) {
            System.out.println("Programmer dun goofed");
            return "";
        }
    }
}
