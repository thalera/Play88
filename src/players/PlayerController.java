package players;

import game.GameInfo;

/**
 * A controller for players of the game. One controller can handle multiple
 * players by passing in different numbers for the players.
 */
public interface PlayerController {

    /**
     * Picks whether to draw from the left deck or the right deck.
     * @param info information about the current state of the game.
     * @param playerNumber the number of the player whose decision this is.
     * @return true for left, false for right.
     */
    boolean pickDeck(GameInfo info, int playerNumber);

    /**
     * If both decks have a rank one card on top, this will return true
     * if we want to draw them both.
     * @param info information about the current state of the game.
     * @param playerNumber the number of the player whose decision this is.
     * @return true to draw both.
     */
    boolean drawBoth(GameInfo info, int playerNumber);

    /**
     * Returns true if the player wants to leave the dungeon.
     * @param info information about the current state of the game.
     * @param playerNumber the number of the player whose decision this is.
     * @return true if the player wants to leave the dungeon.
     */
    boolean leaveDungeon(GameInfo info, int playerNumber);

}
