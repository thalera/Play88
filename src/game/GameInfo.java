package game;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores information about the game.
 */
public class GameInfo {

    /**
     * Rank of the top card of the left deck, or -1 if that deck is empty.
     */
    private int leftRank;

    /**
     * Rank of the top card of the right deck, or -1 if that deck is empty.
     */
    private int rightRank;

    /**
     * Which journey we are on, 1 <= journey <= 3
     */
    private int journey;

    /**
     * The ranks of the cards in all player's banks. The ith index of the
     * list corresponds to the player with player number i. The ith index
     * of the array corresponds to the number of cards of rank i that player
     * has.
     */
    private int[][] bankRanks;

    /**
     * The ranks of the cards in all player's bags. The ith index of the
     * list corresponds to the player with player number i. The ith index
     * of the array corresponds to the number of cards of rank i that player
     * has.
     */
    private int[][] bagRanks;

    /**
     * Initializes a new game.GameInfo, ready for use with numPlayers players.
     * @param numPlayers the number of players for this game.
     */
    public GameInfo(int numPlayers) {
        bankRanks = new int[numPlayers][];
        bagRanks= new int[numPlayers][];
    }

    /**
     * Returns the rank of the top of left deck, -1 if the deck is empty.
     * @return the rank of the top of left deck, -1 if the deck is empty.
     */
    public int getLeftRank() {
        return leftRank;
    }

    /**
     * Returns the rank of the top of right deck, -1 if the deck is empty.
     * @return the rank of the top of right deck, -1 if the deck is empty.
     */
    public int getRightRank() {
        return rightRank;
    }

    /**
     * Returns which number journey we're on.
     * @return which number journey we're on.
     */
    public int getJourney() {
        return journey;
    }

    /**
     * Sets the bag ranks of player playerNumber to ranks. Creates a copy of
     * the array.
     * @param playerNumber the number of the player whose ranks to set.
     * @param ranks the ranks of cards in the bag of the player.
     */
    public void setBagRanks(int playerNumber, int[] ranks) {
        bagRanks[playerNumber] = ranks;
    }

}
