package domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.EnumMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class CardTest {

    @Test
    void gameSetup_createsCardWithDesignFields() {
        Map<TokenColor, Integer> cost = new EnumMap<>(TokenColor.class);
        cost.put(TokenColor.ONYX, 2);
        cost.put(TokenColor.EMERALD, 1);

        Card card = new Card(2, TokenColor.SAPPHIRE, cost, 1);

        assertEquals(2, card.level);
        assertEquals(TokenColor.SAPPHIRE, card.bonusColor);
        assertEquals(1, card.prestigePoints);
        assertEquals(2, card.cost.get(TokenColor.ONYX));
        assertEquals(1, card.cost.get(TokenColor.EMERALD));
    }
}
