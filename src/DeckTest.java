import org.junit.Test;

import static org.junit.Assert.*;

public class DeckTest {

    @Test
    public void constructorTest() {
        Deck deck = new Deck();
        while (deck.size() > 0) {
            System.out.println(deck.draw());
        }
    }

    @Test
    public void shuffleTest() {
        Deck deck = new Deck();
        deck.shuffle();
        while (deck.size() > 0) {
            System.out.println(deck.draw());
        }
    }

}