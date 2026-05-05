package domain;

import org.junit.jupiter.api.Test;
import java.util.Locale;
import static org.junit.jupiter.api.Assertions.*;

public class RuleValidatorTest {

    private final RuleValidator validator = new RuleValidator();

    @Test
    public void testValidPlayerCountLowerBound() {
        ActionResult result = validator.validatePlayerCount(2, Locale.US);
        assertTrue(result.isSuccess());
    }

    @Test
    public void testValidPlayerCountUpperBound() {
        ActionResult result = validator.validatePlayerCount(4, Locale.US);
        assertTrue(result.isSuccess());
    }

    @Test
    public void testInvalidPlayerCountBelowMinimum() {
        ActionResult result = validator.validatePlayerCount(1, Locale.US);
        assertFalse(result.isSuccess());
        assertEquals("Player count must be between 2 and 4.", result.getMessage());
    }

    @Test
    public void testInvalidPlayerCountAboveMaximumInChinese() {
        ActionResult result = validator.validatePlayerCount(5, Locale.SIMPLIFIED_CHINESE);
        assertFalse(result.isSuccess());
        assertEquals("玩家人数必须在2到4人之间。", result.getMessage());
    }
}