package players;

import game.GameInfo;

import java.util.Scanner;

/**
 * Interacts with the console to ask what to do. Assumes the player already
 * has the game information available to them.
 */
public class HumanController implements PlayerController {

    /**
     * Scanner to interact with the user.
     */
    private Scanner console;

    /**
     * Instantiates a new players.HumanController using console as input.
     * @param console the input for the questions.
     */
    public HumanController(Scanner console) {
        this.console = console;
    }

    /**
     * Picks whether to draw from the left deck or the right deck.
     * @param info information about the current state of the game.
     * @param playerNumber the number of the player whose decision this is.
     * @return true for left, false for right.
     */
    @Override
    public boolean pickDeck(GameInfo info, int playerNumber) {
        String response = "";
        while (!response.startsWith("L") && !response.startsWith("R")) {
            System.out.print("Draw from (L)eft or (R)ight deck? ");
            response = console.nextLine().toUpperCase();
        }
        return response.startsWith("L");
    }

    /**
     * If both decks have a rank one card on top, this will return true
     * if we want to draw them both.
     * @param info information about the current state of the game.
     * @param playerNumber the number of the player whose decision this is.
     * @return true to draw both.
     */
    @Override
    public boolean drawBoth(GameInfo info, int playerNumber) {
        String response = "";
        while (!response.startsWith("Y") && !response.startsWith("N")) {
            System.out.print("Take both cards? (Yes/No): ");
            response = console.nextLine().toUpperCase();
        }
        return response.startsWith("Y");
    }

    /**
     * Returns true if the player wants to leave the dungeon.
     * @param info information about the current state of the game.
     * @param playerNumber the number of the player whose decision this is.
     * @return true if the player wants to leave the dungeon.
     */
    @Override
    public boolean leaveDungeon(GameInfo info, int playerNumber) {
        String response = "";
        while (!response.startsWith("Y") && !response.startsWith("N")) {
            System.out.print("Leave the dungeon? (Yes/No): ");
            response = console.nextLine().toUpperCase();
        }
        return response.startsWith("Y");
    }
}
