package domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class CardTest {

    @Test
    void gameSetup_createsCardWithDesignFields() {
        Map<String, Integer> cost = new HashMap<>();
        cost.put("ONYX", 2);
        cost.put("EMERALD", 1);

        Card<String> card = new Card<>(2, "SAPPHIRE", cost, 1);

        assertEquals(2, card.level);
        assertEquals("SAPPHIRE", card.bonusColor);
        assertEquals(1, card.prestigePoints);
        assertEquals(2, card.cost.get("ONYX"));
        assertEquals(1, card.cost.get("EMERALD"));
    }
}
