package players;

import cards.Card;
import game.GameInfo;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

/**
 * Stores information about a player in the game.
 */
public class Player {

    private PlayerInfo info;

    /**
     * The controller for this player.
     */
    private PlayerController controller;

    /**
     * Instantiates a new players.Player using the controller and with number
     * playerNumber.
     * @param controller the controller for this player.
     * @param playerNumber this player's number.
     */
    public Player(PlayerController controller, int playerNumber) {
        this.controller = controller;
        info = new PlayerInfo(playerNumber);
    }

    /**
     * Adds the card to the player's bag.
     * @param card the card to add to the bag.
     */
    public void addToBag(Card card) {
        info.bag.add(card);
    }

    /**
     * Banks the current bag and updates the player's score.
     */
    public void bankBag() {
        for (Card card : info.bag) {
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
        info.bank.add(card);
        info.bag.remove(card);
        info.score += card.getValue();
    }

    /**
     * Empties the bag and returns the cards.
     * @return the cards that were in the bag.
     */
    public Collection<Card> loseBag() {
        Set<Card> bagCopy = new TreeSet<>(info.bag);
        info.bag.clear();
        return bagCopy;
    }

    /**
     * Banks the highest value card in the bag and then empties and returns
     * the cards remaining in the bag.
     * @return the cards remaining in the bag after removing the highest value
     *         card.
     */
    public Collection<Card> loseMostOfBag() {
        Card highest = info.bag.last();
        bankCard(highest);
        return loseBag();
    }

    /**
     * Returns the information about this player.
     * @return the information about this player.
     */
    public PlayerInfo getInfo() {
        return info;
    }

    /**
     * Picks whether to draw from the left deck or the right deck.
     * @param gameInfo information about the current state of the game.
     * @return true for left, false for right.
     */
    boolean pickDeck(GameInfo gameInfo) {
        return controller.pickDeck(gameInfo, info.playerNumber);
    }

    /**
     * If both decks have a rank one card on top, this will return true
     * if we want to draw them both.
     * @param gameInfo information about the current state of the game.
     * @return true to draw both.
     */
    boolean drawBoth(GameInfo gameInfo) {
        return controller.drawBoth(gameInfo, info.playerNumber);
    }

    /**
     * Returns true if the player wants to leave the dungeon.
     * @param gameInfo information about the current state of the game.
     * @return true if the player wants to leave the dungeon.
     */
    boolean leaveDungeon(GameInfo gameInfo) {
        return controller.leaveDungeon(gameInfo, info.playerNumber);
    }



}
