package domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import org.junit.jupiter.api.Test;

class DeckTest {

    private static Card<String> anyCard() {
        return new Card<>(1, "DIAMOND", new HashMap<>(), 0);
    }

    @Test
    void gameSetup_newDeckIsEmpty() {
        Deck<String> deck = new Deck<>();
        assertTrue(deck.isEmpty());
        assertNull(deck.drawCard());
    }

    @Test
    void gameSetup_addAndDrawPopsInOrder() {
        Deck<String> deck = new Deck<>();
        Card<String> a = new Card<>(1, "A", new HashMap<>(), 0);
        Card<String> b = new Card<>(2, "B", new HashMap<>(), 0);
        deck.addCard(a);
        deck.addCard(b);

        assertEquals(a, deck.drawCard());
        assertEquals(b, deck.drawCard());
        assertTrue(deck.isEmpty());
    }

    @Test
    void gameSetup_shuffle_doesNotChangeSize() {
        Deck<String> deck = new Deck<>();
        deck.addCard(anyCard());
        deck.addCard(anyCard());
        int before = deck.cards.size();

        deck.shuffle();

        assertEquals(before, deck.cards.size());
        assertFalse(deck.isEmpty());
    }
}
