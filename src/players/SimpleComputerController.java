package players;

import game.GameInfo;

import java.util.Random;

/**
 * A simple PlayerController that does everything by chance.
 */
public class SimpleComputerController implements PlayerController {

    /**
     * Used for rolls.
     */
    private Random rand;

    /**
     * Initializes a new SimpleComputerController.
     */
    public SimpleComputerController() {
        this.rand = new Random();
    }

    /**
     * Always draws both. Prints a message saying so.
     * @param info information about the current state of the game.
     * @param playerInfo the information about the current player.
     * @return true.
     */
    @Override
    public boolean drawBoth(GameInfo info, PlayerInfo playerInfo) {
        System.out.println("Computer chose to draw both!");
        System.out.println();
        return true;
    }

    /**
     * Leaves with a 10% for each card in the bag.
     * @param info information about the current state of the game.
     * @param playerInfo the information about the current player.
     * @return true for leave, false for stay.
     */
    @Override
    public boolean leaveDungeon(GameInfo info, PlayerInfo playerInfo) {
        int limit = playerInfo.bag.size();
        return rand.nextInt(10) + 1 <= limit;
    }

    /**
     * Always picks the higher ranked deck. Flips a coin if they're the same.
     * @param info information about the current state of the game.
     * @param playerInfo the information about the current player.
     * @return true for left, false for right.
     */
    @Override
    public boolean pickDeck(GameInfo info, PlayerInfo playerInfo) {
        int leftRank = info.getLeftRank();
        int rightRank = info.getRightRank();
        boolean left = leftRank == rightRank ? rand.nextInt(2) == 1 :
                                               leftRank > rightRank;
        if (left) {
            System.out.println("Computer chose to draw from left!");
            System.out.println();
        } else {
            System.out.println("Computer chose to draw from right!");
            System.out.println();
        }
        return left;
    }


}
