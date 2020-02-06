package user_interface;

import game.GameInfo;
import players.PlayerInfo;

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
     * Returns the number of AI players for the game.
     * @return the number of AI players for the game.
     */
    int getNumComputers();

    /**
     * Displays the gameInfo to the user.
     * @param gameInfo the information about the current game.
     */
    void displayGameInfo(GameInfo gameInfo);

    /**
     * Displays the playerInfo to the user.
     * @param playerInfo the information about the current player.
     */
    void displayPlayerInfo(PlayerInfo playerInfo);

}
