package players;

import cards.Card;
import cards.Deck;
import game.GameInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SmarterComputerController implements PlayerController {

    @Override
    public boolean drawBoth(GameInfo info, PlayerInfo playerInfo) {
        double leftExpVal = expectedValue(info, playerInfo, 0);
        double rightExpVal = expectedValue(info, playerInfo, 0);
        return leftExpVal + rightExpVal >= 2;
    }

    @Override
    public boolean leaveDungeon(GameInfo info, PlayerInfo playerInfo) {
        boolean twoMonsters = info.getMonstersThisJourney() == 2;
        // expected value must be high if we've hit two monsters
        int modifier = twoMonsters ? 2 : 0;
        int[] minExpVals = {1, 2 + modifier, 4 + modifier};
        // choose based on expected value of both cards.
        int leftRank = info.getLeftRank();
        double leftExpVal = expectedValue(info, playerInfo, leftRank);
        int rightRank = info.getRightRank();
        double rightExpVal = expectedValue(info, playerInfo, rightRank);
        return leftExpVal < minExpVals[leftRank] && rightExpVal < minExpVals[rightRank];
    }

    @Override
    public boolean pickDeck(GameInfo info, PlayerInfo playerInfo) {
        double leftExpVal = expectedValue(info, playerInfo, info.getLeftRank());
        double rightExpVal = expectedValue(info, playerInfo, info.getRightRank());
        if (leftExpVal > rightExpVal) {
            System.out.println("Computer chose to draw from left!");
            return true;
        }
        System.out.println("Computer chose to draw from right!");
        return false;
    }

    /**
     * Calculates the expected value of drawing a card of rank rank.
     * @param info GameInfo about the current state of the game.
     * @param playerInfo info about the current player.
     * @param rank the rank of card to calculate the exp value of.
     * @return the expected value of drawing the card.
     */
    private double expectedValue(GameInfo info, PlayerInfo playerInfo, int rank) {
        // possible values for this rank
        int[] values = {1 + 2 * rank, 2 + 2 * rank, 3 + 2 * rank};
        int[] monsterCounts = {2, 3, 4};
        // counts of each value of card
        Map<Integer, Integer> maxValueCounts = new HashMap<>();
        // used in recursion to keep track of counts
        Map<Integer, Integer> curValueCounts = new HashMap<>();
        for (Integer value : values) {
            maxValueCounts.put(value, 3);
            curValueCounts.put(value, 0);
        }
        // reduce counts of cards we have
        List<Card> cards = new ArrayList<>();
        cards.addAll(playerInfo.getBag());
        cards.addAll(playerInfo.getBank());
        for (Card card : cards) {
            if (rank == card.getRank()) {
                maxValueCounts.put(card.getValue(),
                        maxValueCounts.get(card.getValue()) - 1);
            }
        }
        int monsters = monsterCounts[rank] - info.getMonsterRanks()[rank];
        int rankCount = getRankCount(info, rank);
        List<Double> expectedVals = new ArrayList<>();
        calcExpectedValues(values[0], rankCount, monsters, maxValueCounts,
                curValueCounts, expectedVals);
        double sum = 0;
        for (Double expVal : expectedVals) {
            sum += expVal;
        }
        return sum / expectedVals.size();
    }

    /**
     * Recursively calculates the expected values of drawing a card.
     * @param currentVal the current value having its count altered
     * @param maxTreasureCount the maximum number of treasure cards.
     * @param monsters the number of monsters still in the deck with the same
     *                 rank as the cards with the values in the keySets
     * @param maxValCounts map from card values to their max counts.
     * @param curValCounts map from values to their current counts, should be 0
     *                     for initial start.
     * @param expectedVals where we store the possible expected values.
     */
    private void calcExpectedValues(int currentVal, int maxTreasureCount,
                                        int monsters,
                                        Map<Integer, Integer> maxValCounts,
                                        Map<Integer, Integer> curValCounts,
                                        List<Double> expectedVals) {
        int sumCounts = 0;
        for (Integer value : curValCounts.keySet()) {
            sumCounts += curValCounts.get(value);
        }
        if (sumCounts < maxTreasureCount && maxValCounts.containsKey(currentVal)) { // need to add more
            for (int i = 1; i <= maxValCounts.get(currentVal); i++) {
                curValCounts.put(currentVal, i);
                calcExpectedValues(currentVal + 1, maxTreasureCount, monsters, maxValCounts,
                                    curValCounts, expectedVals);
            }
            curValCounts.put(currentVal, 0);
        } else if (sumCounts == maxTreasureCount) {
            double expVal = 0;
            for (Integer value : curValCounts.keySet()) {
                expVal += value * (double) curValCounts.get(value) / (maxTreasureCount + monsters);
            }
            expectedVals.add(expVal);
        }

    }

    /**
     * Gets the number of cards of rank rank left in the decks.
     * @param info info about the current state of the game.
     * @param rank the rank of the card.
     * @return the number of cards of rank rank left.
     */
    private int getRankCount(GameInfo info, int rank) {
        int total = 9;
        total -= info.getMonsterRanks()[rank];
        for (int i = 0; i < info.getNumPlayers(); i++) {
            total -= info.getBagRanks(i)[rank];
            total -= info.getBankRanks(i)[rank];
        }
        return total;
    }

}
