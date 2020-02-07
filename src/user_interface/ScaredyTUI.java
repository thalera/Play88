package user_interface;

import cards.Card;
import game.GameInfo;
import players.PlayerInfo;

import java.util.Arrays;
import java.util.List;
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
        System.out.println();
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
        System.out.println();
    }

    /**
     * Return true if the user wants to play the next game.
     * @return true to play the next game.
     */
    @Override
    public boolean playGame() {
        if (playNextGame) {
            playNextGame = false;
            return true;
        }
        return false;
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
        System.out.println();
        return responseScanner.nextInt();
    }

    /**
     * Displays info about the game to the user.
     * @param gameInfo the information about the current game.
     */
    @Override
    public void displayGameInfo(GameInfo gameInfo) {
        System.out.println("Journey: " + gameInfo.getJourney() + ", " +
                "Monsters Encountered: " + gameInfo.getMonstersThisJourney());
        System.out.println("Monsters Encountered overall: " +
                getRanksString(gameInfo.getMonsterRanks()));
        System.out.println();
        System.out.println("Left deck: " + gameInfo.getLeftRank() + ", " +
             "Right deck: " + gameInfo.getRightRank());
        System.out.println();
        for (int i = 0; i < gameInfo.getNumPlayers(); i++) {
            System.out.println("P" + i + " Bag: " +
                getRanksString(gameInfo.getBagRanks(i)) + ", " +
                "Bank: " + getRanksString(gameInfo.getBankRanks(i)));
        }
        enterToContinue();
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
            res += "R" + i + ": " + ranks[i] + ", ";
        }
        res += "R" + (ranks.length - 1) + ": " + ranks[ranks.length - 1] + "]";
        return res;
    }

    /**
     * Displays the playerInfo to the user.
     * @param playerInfo the information about the current player.
     */
    @Override
    public void displayPlayerInfo(PlayerInfo playerInfo) {
        System.out.println("Player " + playerInfo.getPlayerNumber() +":");
        System.out.println("    Bag:  " + playerInfo.getBag().toString());
        System.out.println("    Bank: " + playerInfo.getBank().toString());
        System.out.println();
    }

    /**
     * Prints a message saying you drew a monster.
     * @param card the monster card that was drawn.
     */
    @Override
    public void displayDrewMonster(Card card) {
        System.out.println("You drew a monster!");
        enterToContinue();
    }

    /**
     * Prints a message saying you drew treasure.
     * @param card the treasure card that was drawn.
     */
    @Override
    public void displayDrewTreasure(Card card) {
        System.out.println("You drew a treasure card!");
        System.out.println(card);
        enterToContinue();
    }

    /**
     * Prints a message saying the journey is over.
     */
    @Override
    public void displayEndJourney() {
        System.out.println("This journey has ended!");
        enterToContinue();
    }

    /**
     * Prints a message saying that the players won.
     * @param players the information about the players who won.
     */
    @Override
    public void displayWinners(List<PlayerInfo> players) {
        if (players.size() > 1) {
            System.out.print("The winners are: ");
        } else {
            System.out.print("The winner is: ");
        }
        for (int i = 0; i < players.size () - 1; i++) {
            PlayerInfo player = players.get(i);
            System.out.print("P" + player.getPlayerNumber() + ", ");
        }
        System.out.println("P" + players.get(players.size() - 1).
                getPlayerNumber() + "!");
        enterToContinue();
    }

    private void enterToContinue() {
        System.out.print("(Hit enter to continue) ");
        console.nextLine();
        System.out.println();
    }
}
