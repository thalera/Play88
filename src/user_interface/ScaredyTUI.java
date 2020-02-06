package user_interface;

import game.GameInfo;
import players.PlayerInfo;

import java.util.Scanner;

public class ScaredyTUI implements ScaredyUI {

    private Scanner console;

    private boolean playNextGame;

    private boolean quit;

    /**
     * Creates a new ScaredyTUI using console for user interaction.
     * @param console Scanner for input.
     */
    public ScaredyTUI(Scanner console) {
        this.console = console;
    }

    /**
     * Displays an intro message to the user.
     */
    @Override
    public void displayIntro() {
        System.out.println("Welcome to Scaredy Cat Dungeon!");
    }

    /**
     * Displays a menu to the user and stores response.
     */
    @Override
    public void displayMenu() {
        String response = "";
        while (!response.startsWith("P") && !response.startsWith("Q")) {
            System.out.print("(P)lay or (Q)uit? ");
            response = console.nextLine().toUpperCase();
        }
        playNextGame = response.startsWith("P");
        quit = response.startsWith("Q");
    }

    /**
     * Return true if the user wants to play the next game.
     * @return true to play the next game.
     */
    @Override
    public boolean playGame() {
        return playNextGame;
    }

    /**
     * Returns true if the user wants to quit.
     * @return true if the user wants to quit.
     */
    @Override
    public boolean quitGame() {
        return quit;
    }

    /**
     * Returns the number of human players in the game.
     * @return the number of human players in the game.
     */
    @Override
    public int getNumHumans() {
        return getNumPlayers("human");
    }

    /**
     * Returns the number of computer players in the game.
     * @return the number of computer players in the game.
     */
    @Override
    public int getNumComputers() {
        return getNumPlayers("computer");
    }

    /**
     * Returns the number of type players in the game.
     * @param type the type of player, to be displayed.
     * @return the number of players of type.
     */
    private int getNumPlayers(String type) {
        String response = "";
        Scanner responseScanner = new Scanner(response);
        while (!responseScanner.hasNextInt()) {
            System.out.print("How many " + type + " players? ");
            response = console.nextLine();
            responseScanner = new Scanner(response);
        }
        return responseScanner.nextInt();
    }

    /**
     * Displays info about the game to the user.
     * @param gameInfo the information about the current game.
     */
    @Override
    public void displayGameInfo(GameInfo gameInfo) {
        System.out.println("Journey: " + gameInfo.getJourney());
        System.out.println("Left deck: " + gameInfo.getLeftRank() + ", " +
             "Right deck: " + gameInfo.getRightRank());
        for (int i = 0; i < gameInfo.getNumPlayers(); i++) {
            System.out.println("P" + i + " Bag: " +
                getRanksString(gameInfo.getBagRanks(i)) + ", " +
                "Bank: " + getRanksString(gameInfo.getBankRanks(i)));
        }
    }

    /**
     * Returns a String displaying the number of card of each rank in ranks.
     * @param ranks an array of the ranks, where [i] is the number of
     *              cards of rank i.
     * @return a String displaying ranks.
     */
    private String getRanksString(int[] ranks) {
        String res = "[";
        for (int i = 0; i < ranks.length - 1; i++) {
            res += "Rank " + i + ": " + ranks[i] + ", ";
        }
        res += "Rank " + ranks.length + ": " + ranks[ranks.length - 1] + "]";
        return res;
    }

    /**
     * Displays the playerInfo to the user.
     * @param playerInfo the information about the current player.
     */
    @Override
    public void displayPlayerInfo(PlayerInfo playerInfo) {

    }
}
