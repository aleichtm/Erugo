import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

//imports for io
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException; //also for opening webpage

//imports for opening a webpage
import java.awt.Desktop;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Main logic of the program. Code that starts the game and that ties the
 * classes together
 *
 * @author Ari
 * @version 2019-12-19
 */
public class Main
{
    public static ArrayList<Character> characters = new ArrayList<>();
    public static ArrayList<String> killed = new ArrayList<>();
    public static Map map = new Map();

    private Player player;
    private static boolean won;
    private ArrayList<String> enemyTypes;
    private String input;
    
    /**
     * Constructor for objects of class Main
     */
    public Main()
    {
        enemyTypes = new ArrayList<>();
        setEnemyTypes();
        won = false;
        
        System.out.println("You trip and fall down a trap door, landing on a field of grass, your");
        System.out.println("beloved Amulet of Yendor seems to have flown off during the fall.");
        System.out.println("You see a Jelly trying to sneak away from you. Did they take it?");
        System.out.println();
        Main.printHelp();
        Random rand = new Random();
        NonPlayer jelly = new NonPlayer("J", 1, rand.nextInt(2046), 3, true);
        player = new Player("@", 2, 1023, 25);
        characters.add(jelly);
        characters.add(player);
        input();
    }

    /**
     * main method. Start this to start the game
     */
    public static void main(String[] args)
    {
        Main main = new Main();
    }
    
    /**
     * Print help message
     */
    public static void printHelp() {
        Scanner reader = new Scanner(System.in);
        System.out.println("Welcome to Erugo. Your goal is to retrieve the Amulet of Yendor or die trying.");
        System.out.println("Each turn, choose a direction with the numpad and press enter (you may need to toggle numlock)");
        System.out.println("Type help to see this help screen again or quit to quit the game");
        System.out.println("You can attack an enemy by walking into them. Perhaps one of them has your amulet?");
        System.out.println();
        System.out.println("Press enter to continue");
        reader.nextLine();
        System.out.println("Press a direction:");
    }
    
    /**
     * read enemyTypes.txt for comma delimited characters (no spaces)
     * to create list of NPCs to show
     */
    public void setEnemyTypes()
    {
        try {
            FileReader fReader = new FileReader("enemyTypes.txt");
            Scanner sReader = new Scanner(fReader);
            sReader.useDelimiter(",");
            while(sReader.hasNext()) {
                enemyTypes.add(sReader.next());
            }
            fReader.close();
        } catch(IOException ioe) {
            System.out.println("You'll need a file called enemyTypes.txt in the folder");
            System.out.println("to run this game. Put a couple characters inside the file");
            System.out.println("with commas, no spaces, between characters");
        }
    }
    
    /**
     * main game loop
     */
    public void input()
    {
        Scanner reader = new Scanner(System.in);
        boolean valid;
        int numForSpawn = 0;
        String input;
        int pDirection;
        int npDirection;
        Random rand = new Random();
        Character nonPlayer;
        while(!won && !Player.dead) {
            //Player turn:
            valid = false;
            while(!valid) {
                input = reader.nextLine();
                if(input.equals("help")) {
                    Main.printHelp();
                } else if(input.equals("quit")) {
                    System.exit(0);
                } else {
                    try {
                        pDirection = Integer.parseInt(input);
                        if(pDirection > 0 && !(pDirection == 5) && pDirection < 10) {
                            player.move(pDirection);
                            valid = true;
                        } else {
                            System.out.println("Please enter a valid direction");
                        }
                    } catch(NumberFormatException nfe) {
                        System.out.println("Please enter a valid direction");
                    }
                }
            }
            //NonPlayer turn:
            for(int i = 0; i < characters.size(); i++) {
                npDirection = 0;
                while(npDirection == 0 || npDirection == 5) {
                    npDirection = rand.nextInt(10);
                }
                //instanceof from https://stackoverflow.com/a/36378281
                if(characters.get(i) instanceof NonPlayer) {
                    characters.get(i).move(npDirection);
                }
            }
            numForSpawn++;
            if(numForSpawn % 5 == 0) {
                String enemy = enemyTypes.get(rand.nextInt(enemyTypes.size()));
                nonPlayer = new NonPlayer(enemy, 1, rand.nextInt(2046), 3, false);
                characters.add(nonPlayer);
            }
        }
    }
    
    /**
     * Execute final commands.
     * Change won to true, show victory message, play song,
     * write lastKilled.txt
     */
    public static void win()
    {
        Main.won = true;
        Scanner reader = new Scanner(System.in);
        System.out.println("You start to put on the Amulet with nary a thought");
        System.out.println("of all the helpless creatures you killed and you");
        System.out.println("feel the drums kick in as you're filled with emotion");
        System.out.println("and start to sing... (press enter to continue)");
        reader.nextLine();
        try {
            Desktop.getDesktop().browse(new URI("https://www.youtube.com/watch?v=oHg5SJYRHA0/"));
        } catch(IOException ioe) {
            System.out.println("Something went wrong. There's an IO Exception");
        } catch(URISyntaxException uriSE) {
            System.out.println("Something went wrong. There's a URI Syntax Exception");
        }
        Main.sortKilled();
        String toWrite = "";
        for(String character : killed) {
            toWrite += (character + "\n");
        }
        try {
            FileWriter writer = new FileWriter("lastKilled.txt");
            writer.write(toWrite);
            writer.close();
        } catch(IOException ioe) {
            System.out.println("Never run games in folders you don't have the perms for!");
            System.out.println("Or maybe the programmer dun goofed ¯\\_(ツ)_/¯");
        }
    }
    
    /**
     * Use selection sort to sort killed alphabetically
     */
    private static void sortKilled()
    {
        int lowestIndexValue = 0;
        for(int startValue = 0; startValue < killed.size() -1; startValue++) {
            lowestIndexValue = startValue;
            for(int nextIndex = startValue; nextIndex < killed.size(); nextIndex++) {
                //ignore case found from https://beginnersbook.com/2013/12/java-string-compareto-method-example/
                if(killed.get(nextIndex).compareToIgnoreCase(killed.get(lowestIndexValue)) < 0) {
                    lowestIndexValue = nextIndex;
                }
            }
            String holder = killed.get(startValue);
            killed.set(startValue, killed.get(lowestIndexValue));
            killed.set(lowestIndexValue, holder);
        }
    }
}
