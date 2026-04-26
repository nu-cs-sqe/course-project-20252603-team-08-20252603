package domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck<C> {
    public List<Card<C>> cards;

    public Deck() {
        this.cards = new ArrayList<>();
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card<C> drawCard() {
        if (isEmpty()) {
            return null;
        }
        return cards.remove(0);
    }

    public void addCard(Card<C> card) {
        cards.add(card);
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }
}
