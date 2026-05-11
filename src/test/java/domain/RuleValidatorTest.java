package domain;

import org.junit.jupiter.api.Test;
import java.util.Locale;
import java.util.Map;
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
        assertEquals(MessageProvider.getMessage("error.invalid_player_count", Locale.US), result.getMessage());
    }

    @Test
    public void testInvalidPlayerCountAboveMaximumInChinese() {
        ActionResult result = validator.validatePlayerCount(5, Locale.SIMPLIFIED_CHINESE);
        assertFalse(result.isSuccess());
        assertEquals(MessageProvider.getMessage("error.invalid_player_count", Locale.SIMPLIFIED_CHINESE), result.getMessage());
    }

    @Test
    public void validateTakeTokens_acceptsThreeDifferentGemTokens() {
        Player player = new Player();
        TokenBank bank = new TokenBank();
        bank.initialize(2);

        ActionResult result = validator.validateTakeTokens(player, bank, Map.of(TokenColor.DIAMOND, 1, TokenColor.RUBY, 1, TokenColor.ONYX, 1), Locale.US);

        assertTrue(result.isSuccess());
    }

    @Test
    public void validateTakeTokens_acceptsTwoSameGemTokensWhenBankHasFour() {
        Player player = new Player();
        TokenBank bank = new TokenBank();
        bank.initialize(2);

        ActionResult result = validator.validateTakeTokens(player, bank, Map.of(TokenColor.DIAMOND, 2), Locale.US);

        assertTrue(result.isSuccess());
    }

    @Test
    public void validateTakeTokens_rejectsGoldToken() {
        Player player = new Player();
        TokenBank bank = new TokenBank();
        bank.initialize(2);

        ActionResult result = validator.validateTakeTokens(player, bank, Map.of(TokenColor.GOLD, 1), Locale.US);

        assertFalse(result.isSuccess());
        assertEquals(MessageProvider.getMessage("error.invalid_token_selection", Locale.US), result.getMessage());
    }

    @Test
    public void validateTakeTokens_rejectsTwoSameAndOneDifferentGemToken() {
        Player player = new Player();
        TokenBank bank = new TokenBank();
        bank.initialize(2);

        ActionResult result = validator.validateTakeTokens(player, bank, Map.of(TokenColor.DIAMOND, 2, TokenColor.RUBY, 1), Locale.US);

        assertFalse(result.isSuccess());
        assertEquals(MessageProvider.getMessage("error.invalid_token_selection", Locale.US), result.getMessage());
    }

    @Test
    public void validateTakeTokens_rejectsTwoSameGemTokensWhenBankHasOnlyThree() {
        Player player = new Player();
        TokenBank bank = new TokenBank();
        bank.initialize(2);
        bank.removeTokens(Map.of(TokenColor.DIAMOND, 1));

        ActionResult result = validator.validateTakeTokens(player, bank, Map.of(TokenColor.DIAMOND, 2), Locale.US);

        assertFalse(result.isSuccess());
        assertEquals(MessageProvider.getMessage("error.invalid_token_selection", Locale.US), result.getMessage());
    }

    @Test
    public void validateTakeTokens_rejectsUnavailableBankToken() {
        Player player = new Player();
        TokenBank bank = new TokenBank();
        bank.initialize(2);
        bank.removeTokens(Map.of(TokenColor.DIAMOND, 4));

        ActionResult result = validator.validateTakeTokens(player, bank, Map.of(TokenColor.DIAMOND, 1, TokenColor.RUBY, 1, TokenColor.ONYX, 1), Locale.US);

        assertFalse(result.isSuccess());
        assertEquals(MessageProvider.getMessage("error.invalid_token_selection", Locale.US), result.getMessage());
    }

    @Test
    public void validateTakeTokens_rejectsThreeTokensWhenPlayerWouldExceedTen() {
        Player player = new Player();
        player.addTokens(Map.of(TokenColor.DIAMOND, 4, TokenColor.RUBY, 4));
        TokenBank bank = new TokenBank();
        bank.initialize(2);

        ActionResult result = validator.validateTakeTokens(player, bank, Map.of(TokenColor.DIAMOND, 1, TokenColor.RUBY, 1, TokenColor.ONYX, 1), Locale.US);

        assertFalse(result.isSuccess());
        assertEquals(MessageProvider.getMessage("error.invalid_token_selection", Locale.US), result.getMessage());
    }

    @Test
    public void validateTakeTokens_rejectsTwoTokensWhenPlayerWouldExceedTen() {
        Player player = new Player();
        player.addTokens(Map.of(TokenColor.DIAMOND, 4, TokenColor.RUBY, 4, TokenColor.ONYX, 1));
        TokenBank bank = new TokenBank();
        bank.initialize(2);

        ActionResult result = validator.validateTakeTokens(player, bank, Map.of(TokenColor.DIAMOND, 2), Locale.US);

        assertFalse(result.isSuccess());
        assertEquals(MessageProvider.getMessage("error.invalid_token_selection", Locale.US), result.getMessage());
    }

    @Test
    public void validateTakeTokens_rejectsEmptyTokenMap() {
        Player player = new Player();
        TokenBank bank = new TokenBank();
        bank.initialize(2);

        ActionResult result = validator.validateTakeTokens(player, bank, Map.of(), Locale.US);

        assertFalse(result.isSuccess());
        assertEquals(MessageProvider.getMessage("error.invalid_token_selection", Locale.US), result.getMessage());
    }

    @Test
    public void validateTakeTokens_rejectsTwoDifferentGemTokens() {
        Player player = new Player();
        TokenBank bank = new TokenBank();
        bank.initialize(2);

        ActionResult result = validator.validateTakeTokens(player, bank, Map.of(TokenColor.DIAMOND, 1, TokenColor.RUBY, 1), Locale.US);

        assertFalse(result.isSuccess());
        assertEquals(MessageProvider.getMessage("error.invalid_token_selection", Locale.US), result.getMessage());
    }

    @Test
    public void validateTakeTokens_rejectsFourGemTokens() {
        Player player = new Player();
        TokenBank bank = new TokenBank();
        bank.initialize(2);

        ActionResult result = validator.validateTakeTokens(player, bank, Map.of(TokenColor.DIAMOND, 1, TokenColor.RUBY, 1, TokenColor.ONYX, 1, TokenColor.EMERALD, 1), Locale.US);

        assertFalse(result.isSuccess());
        assertEquals(MessageProvider.getMessage("error.invalid_token_selection", Locale.US), result.getMessage());
    }
}
