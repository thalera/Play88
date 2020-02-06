package cards;

import java.util.*;

/**
 * A deck in the game. Is capable of drawing, cutting, and adding cards.
 */
public class Deck {

    /**
     * The deck.
     */
    private Queue<Card> deck;

    /**
     * Random for shuffling.
     */
    private Random rand;

    /**
     * Creates a new deck with no cards.
     */
    public Deck() {
        deck = new ArrayDeque<>();
        rand = new Random();
    }

    /**
     * Fills this deck with all cards needed to play Scaredy Cat Dungeon.
     */
    public void setUpGame() {
        this.clear();
        // make treasure cards
        int modifier = -1;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 3; j++) {
                Card card = new Card(i - modifier, i % 3);
                deck.add(card);
            }
            if (i == 2 || i == 6) {
                modifier++;
            }
        }
        // make monster cards
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2 + i; j++) {
                Card card = new Card(i);
                deck.add(card);
            }
        }
    }

    /**
     * Returns the card on the top of the deck.
     * @return the card on the top of the deck.
     * @throws IllegalStateException if the deck is empty.
     */
    public Card draw() {
        if (this.size() == 0) {
            throw new IllegalStateException();
        }
        return deck.remove();
    }

    /**
     * Shuffles this deck.
     */
    public void shuffle() {
        List<Card> cards = new ArrayList<>();
        while (!this.isEmpty()) {
            cards.add(deck.remove());
        }
        while (!this.isEmpty()) {
            int index = rand.nextInt(cards.size());
            deck.add(cards.remove(index));
        }
    }

    /**
     * Adds card to the bottom of the deck.
     * @param card the card to add.
     * @throws IllegalArgumentException if card is null.
     */
    public void addCard(Card card) {
        if (card == null) {
            throw new IllegalArgumentException();
        }
        deck.add(card);
    }

    /**
     * Adds the cards to the bottom deck in the order given.
     * @param cards the cards to add.
     */
    public void addCards(Collection<Card> cards) {
        deck.addAll(cards);
    }

    /**
     * Adds all cards from other to the bottom of this in order. This is like
     * placing this deck on top of other.
     * @param other the deck to add to the bottom.
     */
    public void addCards(Deck other) {
        while(!other.isEmpty()) {
            deck.add(other.draw());
        }
    }

    /**
     * Removes all cards from the deck.
     */
    public void clear() {
        while (!deck.isEmpty()) {
            deck.remove();
        }
    }

    /**
     * Returns a new deck containing half of the cards in this one. Takes
     * cards from the top of the deck until half have been taken. If this
     * has an odd number of cards, this will have more cards than the new
     * deck.
     * @return a new deck containing the top half of this one.
     * @throws IllegalStateException if this is empty.
     */
    public Deck cut() {
        if (this.isEmpty()) {
            throw new IllegalStateException();
        }
        Deck other = new Deck();
        int amt = this.size() / 2;
        for (int i = 0; i < amt; i++) {
            other.addCard(this.draw());
        }
        return other;
    }

    /**
     * Returns the rank of the top card on the deck.
     * @return the rank of the top card on the deck.
     * @throws IllegalArgumentException if the deck is empty.
     */
    public int peekRank() {
        if (deck.size() == 0 || deck.peek() == null) {
            throw new IllegalStateException();
        }
        return deck.peek().getRank();
    }

    /**
     * Returns how many cards are left in the deck.
     * @return the number of cards in the deck.
     */
    public int size() {
        return deck.size();
    }

    /**
     * Returns true if this deck is empty.
     * @return true if this deck is empty.
     */
    public boolean isEmpty() {
        return this.size() == 0;
    }

}
