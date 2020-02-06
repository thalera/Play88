package players;

import cards.Card;

import java.util.Set;
import java.util.TreeSet;

public class PlayerInfo {

    /**
     * Cards the player has secured.
     */
    protected Set<Card> bank;

    /**
     * Cards the player has not secured.
     */
    protected TreeSet<Card> bag;

    /**
     * The player's current score.
     */
    protected int score;

    /**
     * This player's number.
     */
    protected int playerNumber;

    /**
     * Instantiates a new player number with player number playerNumber.
     * @param playerNumber the number of the player whose info this stores.
     */
    public PlayerInfo(int playerNumber) {
        this.bank = new TreeSet<>();
        this.bag = new TreeSet<>();
        this.playerNumber = playerNumber;
    }

    /**
     * Returns the score of this player.
     * @return the score of this player.
     */
    public int getScore() {
        return score;
    }

    /**
     * Returns this player's number.
     * @return this player's number.
     */
    public int getPlayerNumber() {
        return playerNumber;
    }

    /**
     * Returns a copy of this player's bank.
     * @return a copy of this player's bank.
     */
    public Set<Card> getBank() {
        return Set.copyOf(bank);
    }

    /**
     * Returns a copy of this player's bag.
     * @return a copy of this player's bag.
     */
    public Set<Card> bag() {
        return Set.copyOf(bag);
    }

}
