package players;

import cards.Card;
import game.GameInfo;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

/**
 * Stores information about a player in the game.
 */
public class Player implements Comparable<Player> {

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
     * @param human true if this player is a human.
     */
    public Player(PlayerController controller, int playerNumber, boolean human) {
        this.controller = controller;
        info = new PlayerInfo(playerNumber, human);
    }

    /**
     * Adds the card to the player's bag.
     * @param card the card to add to the bag.
     */
    public void addToBag(Card card) {
        info.bag.add(card);
        Collections.sort(info.bag);
    }

    /**
     * Banks the current bag and updates the player's score.
     */
    public void bankBag() {
        for (Card card : info.bag) {
            bankCard(card);
        }
        info.bag.clear();
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
        if (info.bag.size() > 0) {
            Card highest = info.bag.get(info.bag.size() - 1);
            bankCard(highest);
            info.bag.remove(highest);
        }
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
    public boolean pickDeck(GameInfo gameInfo) {
        return controller.pickDeck(gameInfo, info);
    }

    /**
     * If both decks have a rank one card on top, this will return true
     * if we want to draw them both.
     * @param gameInfo information about the current state of the game.
     * @return true to draw both.
     */
    public boolean drawBoth(GameInfo gameInfo) {
        return controller.drawBoth(gameInfo, info);
    }

    /**
     * Returns true if the player wants to leave the dungeon.
     * @param gameInfo information about the current state of the game.
     * @return true if the player wants to leave the dungeon.
     */
    public boolean leaveDungeon(GameInfo gameInfo) {
        return controller.leaveDungeon(gameInfo, info);
    }

    /**
     * Compares this to other based on player number.
     * @param other the other player.
     * @return positive if greater, 0 if equal, negative if less.
     */
    public int compareTo(Player other) {
        return this.info.playerNumber - other.info.playerNumber;
    }

}
