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
     * @param playerInfo the information about the current player.
     * @return true for left, false for right.
     */
    @Override
    public boolean pickDeck(GameInfo info, PlayerInfo playerInfo) {
        String response = "";
        if (info.getLeftRank() >= 0 && info.getRightRank() >= 0) {
            while (!response.startsWith("L") && !response.startsWith("R")) {
                System.out.print("Draw from (L)eft or (R)ight deck? ");
                response = console.nextLine().toUpperCase();
            }
            System.out.println();
            return response.startsWith("L");
        } else if (info.getLeftRank() >= 0) {
            System.out.println("You must draw from (L)eft. ");
            System.out.print("(Hit enter to continue)");
            console.nextLine();
            return true;
        } else {
            System.out.println("You must draw from (R)ight. ");
            System.out.print("(Hit enter to continue)");
            console.nextLine();
            return false;
        }
    }

    /**
     * If both decks have a rank one card on top, this will return true
     * if we want to draw them both.
     * @param info information about the current state of the game.
     * @param playerInfo the information about the current player.
     * @return true to draw both.
     */
    @Override
    public boolean drawBoth(GameInfo info, PlayerInfo playerInfo) {
        String response = "";
        while (!response.startsWith("Y") && !response.startsWith("N")) {
            System.out.print("Take both cards? (Yes/No): ");
            response = console.nextLine().toUpperCase();
        }
        System.out.println();
        return response.startsWith("Y");
    }

    /**
     * Returns true if the player wants to leave the dungeon.
     * @param info information about the current state of the game.
     * @param playerInfo the information about the current player.
     * @return true if the player wants to leave the dungeon.
     */
    @Override
    public boolean leaveDungeon(GameInfo info, PlayerInfo playerInfo) {
        String response = "";
        while (!response.startsWith("Y") && !response.startsWith("N")) {
            System.out.print("Leave the dungeon? (Yes/No): ");
            response = console.nextLine().toUpperCase();
        }
        System.out.println();
        return response.startsWith("Y");
    }
}
