package user_interface;

import cards.Card;
import game.GameInfo;
import players.PlayerInfo;

import java.util.ArrayList;
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
     * Displays info about the game to the user.
     * @param gameInfo the information about the current game.
     */
    @Override
    public void displayGameInfo(GameInfo gameInfo) {
        System.out.println("------------------------------------");
        System.out.println();
        System.out.println("Journey: " + gameInfo.getJourney() + ", " +
                "Monsters Encountered: " + gameInfo.getMonstersThisJourney());
        System.out.println("Monsters Encountered Overall: ");
        int[] monsterRanks = gameInfo.getMonsterRanks();
        List<Card> monsters = new ArrayList<>();
        for (int i = 0; i < monsterRanks.length; i++) {
            for (int j = 0; j < monsterRanks[i]; j++) {
                monsters.add(new Card(i));
            }
        }
        printMonsterCards(monsters);
        System.out.println();
        for (int i = 0; i < gameInfo.getNumPlayers(); i++) {
            int[] bagRanks = gameInfo.getBagRanks(i);
            int[] bankRanks = gameInfo.getBankRanks(i);
            int[] totalRanks = new int[3];
            for (int j = 0; j < totalRanks.length; j++) {
                totalRanks[j] = bankRanks[j] + bagRanks[j];
            }
            int min = totalRanks[0] + totalRanks[1] * 3 + totalRanks[2] * 5;
            int max = totalRanks[0] * 3 + totalRanks[1] * 5 + totalRanks[2] * 7;
            System.out.println("P" + i + " Bag: " +
                getRanksString(bagRanks) + ", " +
                "Bank: " + getRanksString(bankRanks) +
                    ", Score: (" + min + " to " + max + ")");
        }
        System.out.println();
        System.out.println("Cards remaining: " + gameInfo.getCardsLeft());
        List<Card> deckCards = new ArrayList<>();
        deckCards.add(new Card(gameInfo.getLeftRank()));
        deckCards.add(new Card(gameInfo.getRightRank()));
        printCardsHidden(deckCards);
        System.out.println();
        System.out.println("It is player " + gameInfo.getCurrentPlayer() +
                "'s turn.");
        enterToContinue();
    }

    /**
     * Displays the playerInfo to the user.
     * @param playerInfo the information about the current player.
     * @param hidden if the information should be hidden
     */
    @Override
    public void displayPlayerInfo(PlayerInfo playerInfo, boolean hidden) {
        System.out.println("Player " + playerInfo.getPlayerNumber() +":");
        if (!hidden) {
            int bagScore = 0;
            for (Card card : playerInfo.getBag()) {
                bagScore += card.getValue();
            }
            System.out.println("    Bag (" + bagScore + "):");
            printTreasureCards(playerInfo.getBag());
            System.out.println("    Bank (" + playerInfo.getScore() + "):");
            printTreasureCards(playerInfo.getBank());
            System.out.println("    Score: " + (playerInfo.getScore() + bagScore));
            System.out.println();
        } else {
            int[] bagScore = getMinMaxScore(playerInfo.getBag());
            int[] bankScore = getMinMaxScore(playerInfo.getBank());
            System.out.println("    Bag (" + bagScore[0] + " to " + bagScore[1] + "):");
            printCardsHidden(playerInfo.getBag());
            System.out.println("    Bank (" + bankScore[0] + " to " + bankScore[1] + "):");
            printCardsHidden(playerInfo.getBank());
            System.out.println("    Score: " + (bagScore[0] + bankScore[0]) +
                    " to " + (bagScore[1] + bankScore[1]));
            System.out.println();
            enterToContinue();
        }
    }

    /**
     * Prints out a message saying the player drew a card.
     * @param card the card that was drawn.
     * @param hidden if the card value should be hidden.
     */
    @Override
    public void displayDrewCard(PlayerInfo info, Card card, boolean hidden) {
        if (hidden) {
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

    /**
     * Prints out a cute little picture of the card.
     * @param card the card to print.
     */
    private void printCard(Card card) {
        List<Card> cards = new ArrayList<>();
        cards.add(card);
        if (card.isMonster()) {
            printMonsterCards(cards);
        } else {
            printTreasureCards(cards);
        }
    }

    /**
     * Prints out a cute picture of the treasure cards.
     * @param cards the cards to print.
     */
    private void printTreasureCards(List<Card> cards) {
        int indentation = 5;
        // tops
        printSpaces(indentation);
        for (Card card : cards) {
            System.out.print(" _____   ");
        }
        System.out.println();
        // top mid
        printSpaces(indentation);
        for (Card card : cards) {
            System.out.print(getTopCardString(card) + "  ");
        }
        System.out.println();
        // mid
        printSpaces(indentation);
        for (Card card : cards) {
            System.out.print("|  " + card.getValue() + "  |  ");
        }
        System.out.println();
        // bottom
        printSpaces(indentation);
        for (Card card : cards) {
            System.out.print("|_____|  ");
        }
        System.out.println();
    }

    /**
     * Prints out a cute picture of the cards face down.
     * @param cards the cards to print.
     */
    private void printCardsHidden(List<Card> cards) {
        int indentation = 5;
        // tops
        printSpaces(indentation);
        for (Card card : cards) {
            System.out.print(" _____   ");
        }
        System.out.println();
        printSpaces(indentation);
        for (Card card : cards) {
            System.out.print("|  ");
            if (card.getRank() > 0) {
                System.out.print("*  |  ");
            } else {
                System.out.print("   |  ");
            }
        }
        System.out.println();
        printSpaces(indentation);
        for (Card card : cards) {
            System.out.print("|  ");
            if (card.getRank() > 1) {
                System.out.print("*  |  ");
            } else {
                System.out.print("   |  ");
            }
        }
        System.out.println();
        printSpaces(indentation);
        for (Card card : cards) {
            System.out.print("|_____|  ");
        }
        System.out.println();
    }

    /**
     * Prints out num spaces without moving to the next line.
     * @param num the number of spaces to print.
     */
    private void printSpaces(int num) {
        for (int i = 0; i < num; i++) {
            System.out.print(" ");
        }
    }

    /**
     * Print out a cute little picture of the monster cards.
     * @param cards the monster cards to print.
     */
    private void printMonsterCards(List<Card> cards) {
        int indentation = 5;
        printSpaces(indentation);
        for (Card card : cards) {
            System.out.print(" _____   ");
        }
        System.out.println();
        printSpaces(indentation);
        for (Card card : cards) {
            System.out.print(getTopCardString(card) + "  ");
        }
        System.out.println();
        printSpaces(indentation);
        for (Card card : cards) {
            System.out.print("| >:( |  ");
        }
        System.out.println();
        printSpaces(indentation);
        for (Card card : cards) {
            System.out.print("|_____|  ");
        }
        System.out.println();
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
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < ranks.length - 1; i++) {
            sb.append("R");
            sb.append(i);
            sb.append(": ");
            sb.append(ranks[i]);
            sb.append(", ");
        }
        sb.append("R");
        sb.append(ranks.length - 1);
        sb.append(": ");
        sb.append(ranks[ranks.length - 1]);
        sb.append("]");
        return sb.toString();
    }

    /**
     * Prints a message saying computer drew a card.
     * @param card the card that was drawn.
     */
    private void displayComputerDrewCard(Card card) {
        if (card.isMonster()) {
            displayMonsterMessage("Computer", card);
        } else {
            List<Card> cards = new ArrayList<>();
            cards.add(card);
            printCardsHidden(cards);
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
        List<Card> cards = new ArrayList<>();
        cards.add(card);
        printMonsterCards(cards);
        System.out.println();
        System.out.println(player + " drew a Rank " + card.getRank() +
                " monster!");
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
     * Returns an array [min, max] of the possible scores of cards with the
     * ranks of the cards in cards.
     * @param cards the cards to judge min, max of.
     * @return an array with the [min, max] scores.
     */
    private int[] getMinMaxScore(List<Card> cards) {
        int[] minMax = new int[2];
        for (Card card : cards) {
            int rank = card.getRank();
            if (rank == 0) {
                minMax[0] += 1;
                minMax[1] += 3;
            } else if (rank == 1) {
                minMax[0] += 3;
                minMax[1] += 5;
            } else {
                minMax[0] += 5;
                minMax[1] += 7;
            }
        }
        return minMax;
    }

}
