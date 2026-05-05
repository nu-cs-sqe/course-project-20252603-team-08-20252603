package domain;

import org.junit.jupiter.api.Test;
import java.util.Locale;
import static org.junit.jupiter.api.Assertions.*;

public class MessageProviderTest {

    @Test
    public void testGetMessageInEnglish() {
        String message = MessageProvider.getMessage("error.invalid_player_count", Locale.US);
        assertEquals("Player count must be between 2 and 4.", message);
    }

    @Test
    public void testGetMessageInChinese() {
        String message = MessageProvider.getMessage("error.invalid_player_count", Locale.SIMPLIFIED_CHINESE);
        assertEquals("玩家人数必须在2到4人之间。", message);
    }

    @Test
    public void testMissingKeyReturnsKeyNameAsFallback() {
        String message = MessageProvider.getMessage("error.unknown", Locale.US);
        assertEquals("error.unknown", message);
    }
}