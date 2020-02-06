import java.util.List;

/**
 * Stores information about the game.
 */
public class GameInfo {

    /**
     * Rank of the top card of the left deck, or -1 if that deck is empty.
     */
    public int leftRank;

    /**
     * Rank of the top card of the right deck, or -1 if that deck is empty.
     */
    public int rightRank;

    /**
     * Which journey we are on, 1 <= journey <= 3
     */
    public int journey;

    /**
     * The ranks of the cards in all player's banks. The ith index of the
     * list corresponds to the player with player number i. The ith index
     * of the array corresponds to the number of cards of rank i that player
     * has.
     */
    public List<int[]> bankRanks;


    /**
     * The ranks of the cards in all player's bags. The ith index of the
     * list corresponds to the player with player number i. The ith index
     * of the array corresponds to the number of cards of rank i that player
     * has.
     */
    public List<int[]> bagRanks;



}
