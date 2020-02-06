public interface PlayerController {

    /**
     * Picks whether to draw from the left deck or the right deck.
     * @return true for left, false for right.
     */
    boolean pickDeck();

    /**
     * If both decks have a rank one card on top, this will return true
     * if we want to draw them both.
     * @return true to draw both.
     */
    boolean drawBoth();

    /**
     * Returns true if the player wants to leave the dungeon.
     * @return true if the player want to leave the dungeon.
     */
    boolean leaveDungeon();

}
