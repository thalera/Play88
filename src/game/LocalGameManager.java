package game;

import cards.Card;
import cards.Deck;
import players.*;
import user_interface.ScaredyUI;

import java.util.*;

/**
 * Manages and runs multiple games of Scaredy Cat Dungeon locally. Assumes all
 * human players using the same computer.
 */
public class LocalGameManager implements GameManager {

    /**
     * Info about the current game.
     */
    private GameInfo gameInfo;

    /**
     * Map from player numbers to players
     */
    private Map<Integer, Player> numbersToPlayers;

    /**
     * Players in the dungeon
     */
    private List<Player> playersInDungeon;

    /**
     * Players who left the dungeon
     */
    private List<Player> playersWhoLeft;

    /**
     * Interface for interacting with the user.
     */
    private ScaredyUI userInterface;

    /**
     * Scanner used for input when necessary.
     */
    private Scanner input;

    /**
     * Random for random things.
     */
    private Random rand;

    /**
     * The left deck.
     */
    private Deck leftDeck;

    /**
     * The right deck.
     */
    private Deck rightDeck;

    /**
     * Initializes a new GameManager using the userInterface to
     * interact with the user.
     * @param userInterface the interface to interact with the user.
     * @param input Scanner used for input if a PlayerController requires it.
     */
    public LocalGameManager(ScaredyUI userInterface, Scanner input) {
        this.playersInDungeon = new ArrayList<>();
        this.playersWhoLeft = new ArrayList<>();
        this.userInterface = userInterface;
        this.input = input;
        this.rand = new Random();
        this.numbersToPlayers = new HashMap<>();
    }

    /**
     * Starts the games.
     */
    @Override
    public void begin() {
        // set up stuff
        userInterface.displayIntro();

        boolean quit = false;
        while (!quit) {
            userInterface.displayMenu();
            // wait until the user wants to do something
            boolean play = false;

            while (!play && !quit) {
                play = userInterface.playGame();
                quit = userInterface.quitGame();
            }

            if (!quit) {
                setUpGameInfo();
                playOneGame();
            }
        }

    }

    /**
     * Gets the number of players and and creates the game info object for
     * this game.
     */
    private void setUpGameInfo() {
        int numHumans = userInterface.getNumHumans();
        int numEasyComputers = userInterface.getNumEasyComputers();
        int numHardComputers = userInterface.getNumHardComputers();
        PlayerController humanController = new ConsoleController(input);
        PlayerController simpleController = new SimpleComputerController();
        PlayerController smartController = new SmarterComputerController();
        for (int i = 0; i < numHumans; i++) {
            addPlayer(humanController, i, true);
        }
        for (int i = 0; i < numEasyComputers; i++) {
            addPlayer(simpleController, i + numHumans, false);
        }
        for (int i = 0; i < numHardComputers; i++) {
            addPlayer(smartController, i + numEasyComputers + numHumans, false);
        }
        this.gameInfo = new GameInfo(numEasyComputers + numHumans + numHardComputers);
    }

    /**
     * Adds a new player to the game with the controller and number.
     * @param controller the controller for the player.
     * @param number the player's number.
     * @param human if this player is a human.
     */
    private void addPlayer(PlayerController controller, int number, boolean human) {
        Player player = new Player(controller, number, human);
        numbersToPlayers.put(number, player);
        playersInDungeon.add(player);
    }

    /**
     * Plays one game of ScaredyCatDungeon, using the current gameInfo.
     */
    private void playOneGame() {
        int numJourneys = 3;
        int firstPlayer = 0;
        for (int i = 0; i < numJourneys; i++) {
            gameInfo.journey = i + 1;
            if (i == 0) {
                leftDeck = new Deck();
                leftDeck.setUpGame();
            } else {
                leftDeck.addCards(rightDeck);
            }
            leftDeck.shuffle();
            firstPlayer = journeyIntoDungeon(firstPlayer);
            userInterface.displayEndJourney();
            playersInDungeon.addAll(playersWhoLeft);
            Collections.sort(playersInDungeon);
            playersWhoLeft.clear();
        }
        printResults();
    }

    /**
     * Prints the results of the game using the current playersInDungeon
     * list.
     */
    private void printResults() {
        List<PlayerInfo> winners = new ArrayList<>();
        int hi = 0;
        for (Player player : playersInDungeon) {
            int score = player.getInfo().getScore();
            if (score > hi) {
                hi = score;
                winners.clear();
                winners.add(player.getInfo());
            } else if (score == hi) {
                winners.add(player.getInfo());
            }
        }
        userInterface.displayWinners(winners);
    }

    /**
     * Performs one round of the game. Assumes card in left deck but not right.
     * @param currentPlayer the player who is going first.
     * @return the player number of who took the last turn.
     */
    private int journeyIntoDungeon(int currentPlayer) {
        rightDeck = leftDeck.cut();
        writeDeckInfo();
        writePlayerInfo();
        boolean firstPlayer = true;
        while (playersInDungeon.size() > 0 &&
                gameInfo.monstersThisJourney < 3) {
            if (firstPlayer) {
                firstPlayer = false;
            } else {
                do {
                    currentPlayer = (currentPlayer + 1) %
                            numbersToPlayers.size();
                    gameInfo.currentPlayer = currentPlayer;
                } while (!playersInDungeon.contains(
                        numbersToPlayers.get(currentPlayer)));
            }
            userInterface.displayGameInfo(gameInfo);
            takeTurn(numbersToPlayers.get(currentPlayer));
            writeDeckInfo();
            writePlayerInfo();
        }
        if (gameInfo.monstersThisJourney == 3) {
            Player lastPlayer = numbersToPlayers.get(currentPlayer);
            leftDeck.addCards(lastPlayer.loseBag());
            playersInDungeon.remove(lastPlayer);
            playersWhoLeft.add(lastPlayer);
            for (Player player : playersInDungeon) {
                leftDeck.addCards(player.loseMostOfBag());
            }
        }
        gameInfo.monstersThisJourney = 0;
        return playersWhoLeft.get(playersWhoLeft.size() - 1).
                getInfo().getPlayerNumber();
    }

    /**
     * Lets player take a turn.
     * @param player the player whose turn it is.
     */
    private void takeTurn(Player player) {
        userInterface.displayPlayerInfo(player.getInfo(),
                !player.getInfo().isHuman());
        if (player.leaveDungeon(gameInfo)) {
            player.bankBag();
            playersInDungeon.remove(player);
            playersWhoLeft.add(player);
            userInterface.displayLeftDungeon(player.getInfo());
        } else if (gameInfo.leftRank == 0 && gameInfo.rightRank == 0 &&
                    player.drawBoth(gameInfo)) {
            if (player.pickDeck(gameInfo)) {
                drawBoth(leftDeck, rightDeck, player);
            } else {
                drawBoth(rightDeck, leftDeck, player);
            }
        } else {
            if (player.pickDeck(gameInfo)) {
                drawCard(leftDeck, player);
            } else {
                drawCard(rightDeck, player);
            }
        }
    }

    /**
     * Draws the card from the deck for player. Return true
     * if it was a monster.
     * @param deck the deck to draw from.
     * @param player the player who is drawing.
     * @return true if the card was a monster
     */
    public boolean drawCard(Deck deck, Player player) {
        Card card = deck.draw();
        PlayerInfo info = player.getInfo();
        if (card.isMonster()) {
            gameInfo.monstersThisJourney++;
            gameInfo.monsterRanks[card.getRank()]++;
            userInterface.displayDrewCard(info, card, player.getInfo().isHuman());
            return true;
        } else {
            player.addToBag(card);
            userInterface.displayDrewCard(info, card, player.getInfo().isHuman());
            return false;
        }
    }

    /**
     * Draws a card from the first deck, if it's not a monster then
     * draws from the second one too.
     * @param first the first deck to draw from.
     * @param second the second deck to draw from.
     * @param player the player who is drawing.
     * @return true if the player drew a monster.
     */
    public boolean drawBoth(Deck first, Deck second, Player player) {
        if (!drawCard(first, player)) {
            return (drawCard(second, player));
        }
        return true;
    }

    /**
     * Writes the current deck info to the gameInfo.
     */
    private void writeDeckInfo() {
        gameInfo.cardsLeft = leftDeck.size() + rightDeck.size();
        gameInfo.leftRank = leftDeck.peekRank();
        gameInfo.rightRank = rightDeck.peekRank();
    }

    /**
     * Writes the current player info to the gameInfo.
     */
    private void writePlayerInfo() {
        writePlayerInfo(playersInDungeon);
        writePlayerInfo(playersWhoLeft);
    }

    /**
     * Writes the current player info from players to the gameInfo.
     * @param players the players to write the info of.
     */
    private void writePlayerInfo(List<Player> players) {
        for (Player player : players) {
            PlayerInfo info = player.getInfo();
            gameInfo.bagRanks[info.getPlayerNumber()] =
                    getRanksArray(info.getBag());
            gameInfo.bankRanks[info.getPlayerNumber()] =
                    getRanksArray(info.getBank());
        }
    }

    /**
     * Counts up the number of cards for each rank in cards.
     * @param cards the cards to count the ranks of.
     * @return an array containing counts of each rank of card.
     */
    private int[] getRanksArray(List<Card> cards) {
        int numRanks = 3;
        int[] ranks = new int[numRanks];
        for (Card card : cards) {
            ranks[card.getRank()]++;
        }
        return ranks;
    }

}
