package players;

import cards.Card;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class PlayerInfo {

    /**
     * Cards the player has secured.
     */
    protected List<Card> bank;

    /**
     * Cards the player has not secured.
     */
    protected List<Card> bag;

    /**
     * The player's current score.
     */
    protected int score;

    /**
     * This player's number.
     */
    protected int playerNumber;

    /**
     * If this player is a human.
     */
    private boolean human;

    /**
     * Instantiates a new player number with player number playerNumber.
     * @param playerNumber the number of the player whose info this stores.
     * @param human if this player is a human.
     */
    public PlayerInfo(int playerNumber, boolean human) {
        this.bank = new LinkedList<>();
        this.bag = new LinkedList<>();
        this.human = human;
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
    public List<Card> getBank() {
        return List.copyOf(bank);
    }

    /**
     * Returns a copy of this player's bag.
     * @return a copy of this player's bag.
     */
    public List<Card> getBag() {
        return List.copyOf(bag);
    }

    /**
     * Returns true if this player is a human.
     * @return true if this player is a human.
     */
    public boolean isHuman() {
        return human;
    }

}
