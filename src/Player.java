import java.util.Collection;
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
    private TreeSet<Card> bag;

    /**
     * The player's current score.
     */
    private int score;

    /**
     * The controller for this player.
     */
    private PlayerController controller;

    /**
     * This player's number.
     */
    private int playerNumber;


    public Player(PlayerController controller, int playerNumber) {
        this.controller = controller;
        this.bank = new TreeSet<>();
        this.bag = new TreeSet<>();
        this.playerNumber = playerNumber;
    }

    /**
     * Adds the card to the player's bag.
     * @param card the card to add to the bag.
     */
    public void addToBag(Card card) {
        bag.add(card);
    }

    /**
     * Banks the current bag and updates the player's score.
     */
    public void bankBag() {
        for (Card card : bag) {
            bankCard(card);
        }
    }

    /**
     * Banks the card in the bag.
     * @param card the card in the bag.
     */
    private void bankCard(Card card) {
        if (card == null) {
            throw new IllegalArgumentException();
        }
        bank.add(card);
        bag.remove(card);
        score += card.getValue();
    }

    /**
     * Empties the bag and returns the cards.
     * @return the cards that were in the bag.
     */
    public Collection<Card> loseBag() {
        Set<Card> bagCopy = new TreeSet<>(bag);
        bag.clear();
        return bagCopy;
    }

    /**
     * Banks the highest value card in the bag and then empties and returns
     * the cards remaining in the bag.
     * @return the cards remaining in the bag after removing the highest value
     *         card.
     */
    public Collection<Card> loseMostOfBag() {
        Card highest = bag.last();
        bankCard(highest);
        return loseBag();
    }

    /**
     * Returns the total value of the player's bank.
     * @return the total value of the player's bank.
     */
    public int getScore() {
        return this.score;
    }

    /**
     * Picks whether to draw from the left deck or the right deck.
     * @param info information about the current state of the game.
     * @return true for left, false for right.
     */
    boolean pickDeck(GameInfo info) {
        return controller.pickDeck(info);
    }

    /**
     * If both decks have a rank one card on top, this will return true
     * if we want to draw them both.
     * @param info information about the current state of the game.
     * @return true to draw both.
     */
    boolean drawBoth(GameInfo info) {
        return controller.drawBoth(info);
    }

    /**
     * Returns true if the player wants to leave the dungeon.
     * @param info information about the current state of the game.
     * @return true if the player wants to leave the dungeon.
     */
    boolean leaveDungeon(GameInfo info) {
        return controller.leaveDungeon(info);
    }



}
