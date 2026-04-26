package domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class NobleTest {

    @Test
    void gameSetup_createsNobleWithDesignFields() {
        Map<String, Integer> requirements = new HashMap<>();
        requirements.put("EMERALD", 3);
        requirements.put("RUBY", 3);
        requirements.put("ONYX", 3);

        Noble<String> noble = new Noble<>(requirements, 3);

        assertEquals(3, noble.prestigePoints);
        assertEquals(3, noble.requirements.get("EMERALD"));
        assertEquals(3, noble.requirements.get("RUBY"));
        assertEquals(3, noble.requirements.get("ONYX"));
    }
}
