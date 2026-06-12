package domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import org.junit.jupiter.api.Test;

class DeckTest {

    private static Card anyCard() {
        return new Card(1, TokenColor.DIAMOND, new HashMap<>(), 0);
    }

    @Test
    void gameSetup_newDeckIsEmpty() {
        Deck deck = new Deck();
        assertTrue(deck.isEmpty());
        assertNull(deck.drawCard());
    }

    @Test
    void gameSetup_addAndDrawPopsInOrder() {
        Deck deck = new Deck();
        Card a = new Card(1, TokenColor.DIAMOND, new HashMap<>(), 0);
        Card b = new Card(2, TokenColor.RUBY, new HashMap<>(), 0);
        deck.addCard(a);
        deck.addCard(b);

        assertEquals(a, deck.drawCard());
        assertEquals(b, deck.drawCard());
        assertTrue(deck.isEmpty());
    }

    @Test
    void gameSetup_shuffle_doesNotChangeSize() {
        Deck deck = new Deck();
        deck.addCard(anyCard());
        deck.addCard(anyCard());
        int before = deck.cards.size();

        deck.shuffle();

        assertEquals(before, deck.cards.size());
        assertFalse(deck.isEmpty());
    }

    @Test
    void gameSetup_shuffle_actuallyChangesOrder() {
        Deck deck = new Deck();

        for (int i = 0; i < 20; i++) {
            Card uniqueCard = new Card(1, TokenColor.DIAMOND, new HashMap<>(), i);
            deck.addCard(uniqueCard);
        }

        java.util.List<Card> originalOrder = new java.util.ArrayList<>(deck.cards);

        deck.shuffle();

        boolean orderChanged = false;
        for (int i = 0; i < originalOrder.size(); i++) {
            if (originalOrder.get(i) != deck.cards.get(i)) {
                orderChanged = true;
                break;
            }
        }

        assertTrue(orderChanged, "Shuffle should actively change the order of cards in the deck");
    }
}
