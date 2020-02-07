package user_interface;

import cards.Card;
import game.GameInfo;
import players.PlayerInfo;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * A simple text based UI that works through the console.
 */
public class ScaredyTUI implements ScaredyUI {

    /**
     * Used for user input.
     */
    private Scanner console;

    /**
     * True if the user wants to play the next game.
     */
    private boolean playNextGame;

    /**
     * True if the user wants to quit.
     */
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
        System.out.println("------------------------------------");
        System.out.println();
        System.out.println("Journey: " + gameInfo.getJourney() + ", " +
                "Monsters Encountered: " + gameInfo.getMonstersThisJourney());
        System.out.println("Monsters Encountered Overall: " +
                getRanksString(gameInfo.getMonsterRanks()));
        System.out.println();
        for (int i = 0; i < gameInfo.getNumPlayers(); i++) {
            System.out.println("P" + i + " Bag: " +
                getRanksString(gameInfo.getBagRanks(i)) + ", " +
                "Bank: " + getRanksString(gameInfo.getBankRanks(i)));
        }
        System.out.println();
        System.out.println("Cards remaining: " + gameInfo.getCardsLeft());
        System.out.println();
        printDecks(gameInfo.getLeftRank(), gameInfo.getRightRank());
        System.out.println();
        System.out.println("It is player " + gameInfo.getCurrentPlayer() +
                "'s turn.");
        enterToContinue();
    }

    /**
     * Prints a cute little picture of the decks.
     * @param leftRank the rank of the left card.
     * @param rightRank the rank of the right card.
     */
    private void printDecks(int leftRank, int rightRank) {
        char leftTop = leftRank > 0 ? '*' : ' ';
        char leftMid = leftRank > 1 ? '*' : ' ';
        char rightTop = rightRank > 0 ? '*' : ' ';
        char rightMid = rightRank > 1 ? '*' : ' ';
        System.out.println(" _____     _____");
        System.out.println("|  " + leftTop + "  |   |  " + rightTop + "  |");
        System.out.println("|  " + leftMid + "  |   |  " + rightMid + "  |");
        System.out.println("|_____|   |_____|");
    }

    /**
     * Prints out a cute little picture of the card.
     * @param card the card to print.
     */
    private void printCard(Card card) {
        if (card.isMonster()) {
            printMonster(card);
        } else {
            printTreasure(card);
        }
    }

    /**
     * Prints out a cute little picture of the card back.
     * @param card the card to print.
     */
    private void printCardBack(Card card) {
        char top = card.getRank() > 0 ? '*' : ' ';
        char mid = card.getRank() > 1 ? '*' : ' ';
        System.out.println(" _____");
        System.out.println("|  " + top + "  |");
        System.out.println("|  " + mid + "  |");
        System.out.println("|_____|");
    }

    /**
     * Print out a cute little picture of a monster card.
     * @param card the monster card to print.
     */
    private void printMonster(Card card) {
        System.out.println(" _____");
        System.out.println(getTopCardString(card));
        System.out.println("| >:( |");
        System.out.println("|_____|");
    }

    /**
     * Prints out a cute little picture of a treasure card.
     * @param card the treasure card to print.
     */
    private void printTreasure(Card card) {
        System.out.println(" _____");
        System.out.println(getTopCardString(card));
        System.out.println("|  " + card.getValue() + "  |");
        System.out.println("|_____|");
    }

    /**
     * Returns the top row of the card picture, changing based on rank.
     * @param card the card that is being printed.
     * @return the top row of the card picture.
     */
    private String getTopCardString(Card card) {
        String top = "|     |";
        if (card.getRank() == 1) {
            top = "|  *  |";
        } else if (card.getRank() == 2) {
            top = "| * * |";
        }
        return top;
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
        int bagScore = 0;
        for (Card card : playerInfo.getBag()) {
            bagScore += card.getValue();
        }
        System.out.println("    Bag:   " + playerInfo.getBag().toString() +
                " (" + bagScore + ")");
        System.out.println("    Bank:  " + playerInfo.getBank().toString() +
                " (" + playerInfo.getScore() + ")");
        System.out.println("    Score: " + (playerInfo.getScore() + bagScore));
        System.out.println();
    }

    /**
     * Prints out a message saying the player drew a card.
     * @param card the monster card that was drawn.
     * @param human if the player is a human.
     */
    @Override
    public void displayDrewCard(Card card, boolean human) {
        if (human) {
            displayHumanDrewCard(card);
        } else {
            displayComputerDrewCard(card);
        }
    }

    /**
     * Prints a message saying the player left the dungeon.
     * @param player the player who left.
     */
    @Override
    public void displayLeftDungeon(PlayerInfo player) {
        System.out.println("Player " + player.getPlayerNumber() +
                " left the dungeon!");
        enterToContinue();
    }

    /**
     * Prints a message saying computer drew a card.
     * @param card the card that was drawn.
     */
    private void displayComputerDrewCard(Card card) {
        if (card.isMonster()) {
            displayMonsterMessage("Computer", card);
        } else {
            printCardBack(card);
            System.out.println();
            System.out.println("Computer drew a Rank " + card.getRank() +
                    " treasure!");
        }
        enterToContinue();
    }

    /**
     * Prints a message saying human drew a card
     * @param card the card that was drawn.
     */
    private void displayHumanDrewCard(Card card) {
        if (card.isMonster()) {
            displayMonsterMessage("You", card);
        } else {
            printCard(card);
            System.out.println();
            System.out.println("You drew a treasure! " + card);
        }
        enterToContinue();
    }

    /**
     * Displays a message that playerType drew a monster.
     * @param player "Computer" or "You"
     * @param card the monster card that was drawn.
     */
    private void displayMonsterMessage(String player, Card card) {
        printMonster(card);
        System.out.println();
        System.out.println(player + " drew a Rank " + card.getRank() +
                " monster!");
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
            System.out.print("P" + player.getPlayerNumber() +
                    " (" + player.getScore() + "), ");
        }
        PlayerInfo player = players.get(players.size() - 1);
        System.out.println("P" + player.getPlayerNumber() +
                " (" + player.getScore() + ")!");
        enterToContinue();
    }

    /**
     * Prints a message and waits until the user hits enter before continuing.
     */
    private void enterToContinue() {
        System.out.print("(Hit enter to continue) ");
        console.nextLine();
        System.out.println();
    }
}
