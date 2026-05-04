package domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;

class CardLoaderTest {

    @Test
    void loadFromClasspath_presentResourceWithOneCard_returnsNonEmptyListWithMatchingFields() throws IOException {
        CardLoader loader = new CardLoader();
        List<Card> cards = loader.loadFromClasspath(CardLoaderTest.class, "/cards/one-card.json");

        assertEquals(1, cards.size());
        Card card = cards.get(0);
        assertEquals(2, card.level);
        assertEquals(TokenColor.SAPPHIRE, card.bonusColor);
        assertEquals(1, card.prestigePoints);
        assertEquals(2, card.cost.get(TokenColor.ONYX));
        assertEquals(1, card.cost.get(TokenColor.EMERALD));
    }

    @Test
    void loadFromClasspath_missingResource_throwsIOExceptionWithPathInMessage() {
        CardLoader loader = new CardLoader();
        String missingPath = "/cards/no-such-file.json";

        IOException thrown =
                assertThrows(IOException.class, () -> loader.loadFromClasspath(CardLoaderTest.class, missingPath));

        assertTrue(thrown.getMessage().contains(missingPath));
    }
}
