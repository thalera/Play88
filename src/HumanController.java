import java.util.Scanner;

/**
 * Interacts with the console to ask what to do. Assumes the user can see
 * the game information on screen.
 */
public class HumanController implements PlayerController {

    /**
     * Scanner to interact with the user.
     */
    private Scanner console;

    /**
     * Instantiates a new HumanController using console as input.
     * @param console the input for the questions.
     */
    public HumanController(Scanner console) {
        this.console = console;
    }

    /**
     * Picks whether to draw from the left deck or the right deck.
     * @return true for left, false for right.
     */
    @Override
    public boolean pickDeck() {
        String response = "";
        while (!response.startsWith("L") && !response.startsWith("R")) {
            System.out.println("Draw from (L)eft or (R)ight deck? ");
            response = console.nextLine().toUpperCase();
        }
        return response.startsWith("L");
    }

    /**
     * If both decks have a rank one card on top, this will return true
     * if we want to draw them both.
     * @return true to draw both.
     */
    @Override
    public boolean drawBoth() {
        String response = "";
        while (!response.startsWith("Y") && !response.startsWith("N")) {
            System.out.println("Take both cards? (Yes/No): ");
            response = console.nextLine().toUpperCase();
        }
        return response.startsWith("Y");
    }

    /**
     * Returns true if the player wants to leave the dungeon.
     * @return true if the player wants to leave the dungeon.
     */
    @Override
    public boolean leaveDungeon() {
        String response = "";
        while (!response.startsWith("Y") && !response.startsWith("N")) {
            System.out.println("Leave the dungeon? (Yes/No): ");
            response = console.nextLine().toUpperCase();
        }
        return response.startsWith("Y");
    }
}