import java.util.Set;
import java.util.TreeSet;

/**
 * Stores information about a player in the game.
 */
public class Player {

    /**
     * Cards the player has secured.
     */
    private Set<Card> bank;

    /**
     * Cards the player has not secured.
     */
    private Set<Card> bag;

    /**
     * The player's current score.
     */
    private int score;

    /**
     * The controller for this player.
     */
    private PlayerController controller;


    public Player() {
        this.bank = new TreeSet<>();
        this.bag = new TreeSet<>();
    }


}
