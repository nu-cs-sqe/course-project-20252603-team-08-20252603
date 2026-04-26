package domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.EnumMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class NobleTest {

    @Test
    void gameSetup_createsNobleWithDesignFields() {
        Map<TokenColor, Integer> requirements = new EnumMap<>(TokenColor.class);
        requirements.put(TokenColor.EMERALD, 3);
        requirements.put(TokenColor.RUBY, 3);
        requirements.put(TokenColor.ONYX, 3);

        Noble noble = new Noble(requirements, 3);

        assertEquals(3, noble.prestigePoints);
        assertEquals(3, noble.requirements.get(TokenColor.EMERALD));
        assertEquals(3, noble.requirements.get(TokenColor.RUBY));
        assertEquals(3, noble.requirements.get(TokenColor.ONYX));
    }
}
