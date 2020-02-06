package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Stores information about the game.
 */
public class GameInfo {

    /**
     * Rank of the top card of the left deck, or -1 if that deck is empty.
     */
    protected int leftRank;

    /**
     * Rank of the top card of the right deck, or -1 if that deck is empty.
     */
    protected int rightRank;

    /**
     * Which journey we are on, 1 <= journey <= 3
     */
    protected int journey;

    /**
     * The number of cards left in the deck.
     */
    protected int cardsLeft;

    /**
     * The ranks of the cards in all player's banks. The ith index of the
     * list corresponds to the player with player number i. The ith index
     * of the array corresponds to the number of cards of rank i that player
     * has.
     */
    protected int[][] bankRanks;

    /**
     * The ranks of the cards in all player's bags. The ith index of the
     * list corresponds to the player with player number i. The ith index
     * of the array corresponds to the number of cards of rank i that player
     * has.
     */
    protected int[][] bagRanks;

    /**
     * Number of players in the game.
     */
    private int numPlayers;

    /**
     * Initializes a new game.GameInfo, ready for use with numPlayers players.
     * @param numPlayers the number of players for this game.
     */
    public GameInfo(int numPlayers) {
        bankRanks = new int[numPlayers][];
        bagRanks = new int[numPlayers][];
        this.numPlayers = numPlayers;
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
     * Returns the number of cards left in the deck.
     * @return the number of cards left in the deck.
     */
    public int getCardsLeft() {
        return cardsLeft;
    }

    /**
     * Returns an array containing the bag ranks of player playerNumber, where
     * [i] is the number of cards of rank i.
     * @param playerNumber the number of the player whose ranks to get
     * @return an array containing the bag ranks of the player.
     * @throws IllegalArgumentException if playerNumber is not valid.
     */
    public int[] getBagRanks(int playerNumber) {
        if (playerNumber >= numPlayers || playerNumber < 0) {
            throw new IllegalArgumentException();
        }
        return Arrays.copyOf(bagRanks[playerNumber], bagRanks[playerNumber].length);
    }

    /**
     * Returns an array containing the bank ranks of player playerNumber, where
     * [i] is the number of cards of rank i.
     * @param playerNumber the number of the player whose ranks to get
     * @return an array containing the bank ranks of the player.
     * @throws IllegalArgumentException if playerNumber is not valid.
     */
    public int[] getBankRanks(int playerNumber) {
        if (playerNumber >= numPlayers || playerNumber < 0) {
            throw new IllegalArgumentException();
        }
        return Arrays.copyOf(bankRanks[playerNumber], bankRanks[playerNumber].length);
    }

    /**
     * Returns the number of players in the game.
     * @return the number of players in the game.
     */
    public int getNumPlayers() {
        return numPlayers;
    }
}
