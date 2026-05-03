package domain;

import java.util.Collections;
import java.util.List;

public class Player {

    public int getPrestigePoints() {
        return -1;
    }

    public int getTokenCount(TokenColor color) {
        return -1;
    }

    public List<Card> getDevelopmentCards() {
        return Collections.singletonList(new Card());
    }

    public List<Card> getReservedCards() {
        return Collections.singletonList(new Card());
    }

    public int getTotalTokenCount() {
        return -1;
    }
}