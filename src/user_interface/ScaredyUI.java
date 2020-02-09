package user_interface;

import cards.Card;
import game.GameInfo;
import players.PlayerInfo;

import java.util.List;

/**
 * An interface is used for user interaction when outside of the game,
 * and relaying information about the game to the user. Can optionally
 * work with a PlayerController, but it is not necessary to do so.
 */
public interface ScaredyUI {

    /**
     * Displays an intro or message to the user
     * welcoming them to the game.
     */
    void displayIntro();

    /**
     * Shows a menu with options to the user.
     */
    void displayMenu();

    /**
     * Returns true to play the game.
     * @return true to play the game.
     */
    boolean playGame();

    /**
     * Returns true to quit playing.
     * @return true to quit.
     */
    boolean quitGame();

    /**
     * Returns the number of human players for the game.
     * @return the number of human players for the game.
     */
    int getNumHumans();

    /**
     * Returns the number of easy AI players for the game.
     * @return the number of easy AI players for the game.
     */
    int getNumEasyComputers();

    /**
     * Returns the number of hard AI players for the game.
     * @return the number of hard AI players for the game.
     */
    int getNumHardComputers();

    /**
     * Is called at the start of every turn
     * @param gameInfo the information about the current game.
     */
    void displayGameInfo(GameInfo gameInfo);

    /**
     * Is called at the start of a player's turn.
     * @param playerInfo the information about the current player.
     * @param hidden if the information should be hidden.
     */
    void displayPlayerInfo(PlayerInfo playerInfo, boolean hidden);

    /**
     * Is called when a player draws a card.
     * @param info the information about the player who drew the card.
     * @param card the monster card that was drawn.
     * @param hidden if the card value should be hidden.
     */
    void displayDrewCard(PlayerInfo info, Card card, boolean hidden);

    /**
     * Is called when player leaves the dungeon.
     * @param player the player who left.
     */
    void displayLeftDungeon(PlayerInfo player);

    /**
     * Is called when the journey ends.
     */
    void displayEndJourney();

    /**
     * Is called when the game is over
     * @param players the information about the players who won.
     */
    void displayWinners(List<PlayerInfo> players);

}
