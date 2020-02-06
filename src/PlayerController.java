public interface PlayerController {

    /**
     * Picks whether to draw from the left deck or the right deck.
     * @param info information about the current state of the game.
     * @return true for left, false for right.
     */
    boolean pickDeck(GameInfo info);

    /**
     * If both decks have a rank one card on top, this will return true
     * if we want to draw them both.
     * @param info information about the current state of the game.
     * @return true to draw both.
     */
    boolean drawBoth(GameInfo info);

    /**
     * Returns true if the player wants to leave the dungeon.
     * @param info information about the current state of the game.
     * @return true if the player wants to leave the dungeon.
     */
    boolean leaveDungeon(GameInfo info);

}
