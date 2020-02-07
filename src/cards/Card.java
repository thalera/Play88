package cards;

/**
 * A single card in the game. Represents either treasure with a numeric value
 * or a monster.
 */
public class Card implements Comparable<Card> {

    /**
     * If this card is a monster card.
     */
    private boolean monster;

    /**
     * How many points this is worth.
     */
    private int value;

    /**
     * What rank (1, 2, 3) this is.
     */
    private int rank;

    /**
     * Instantiates a new treasure card.
     * @param value a value for the card if not a monster.
     * @param rank what rank this card is.
     */
    public Card(int value, int rank) {
        this.monster = false;
        this.value = value;
        this.rank = rank;
    }

    /**
     * Instantiates a new monster card
     * @param rank the rank of the card.
     */
    public Card(int rank) {
        this.value = -1;
        this.monster = true;
        this.rank = rank;
    }

    /**
     * Returns true if this is a monster.
     * @return true if this is a monster.
     */
    public boolean isMonster() {
        return monster;
    }

    /**
     * Returns the value of this card.
     * @return the value of this card, or -1 if this is a monster card.
     */
    public int getValue() {
        return value;
    }

    /**
     * Returns the rank of this card.
     * @return the rank of this card.
     */
    public int getRank() {
        return rank;
    }

    /**
     * A String representation of the card.
     * @return a String representation of the card.
     */
    @Override
    public String toString() {
        return "(V" + value + ", R" + rank + ")";
    }

    /**
     * Compares this to other. Returns positive if greater than, 0 if
     * equal to, and negative if less than.
     * @param other the card to compare to.
     * @return positive if greater than, 0 if equal to, negative if less than
     */
    public int compareTo(Card other) {
        if (this.rank > other.rank) {
            return 1;
        } else if (this.rank == other.rank) {
            return this.value - other.value;
        } else {
            return -1;
        }
    }

}
