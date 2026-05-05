package domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ActionResultTest {

    @Test
    public void testSuccessState() {
        ActionResult result = ActionResult.success();
        assertTrue(result.isSuccess());
        assertEquals("", result.getMessage());
    }

    @Test
    public void testFailureState() {
        ActionResult result = ActionResult.failure("Error message");
        assertFalse(result.isSuccess());
        assertEquals("Error message", result.getMessage());
    }
}