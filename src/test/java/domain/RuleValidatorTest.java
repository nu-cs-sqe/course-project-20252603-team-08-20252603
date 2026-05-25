package domain;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
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

    @Test
    public void validateReserveCard_acceptsFirstFaceUpCardWhenPlayerHasNoReservedCards() {
        Player player = new Player();
        List<Card> faceUpCards = new ArrayList<>();
        faceUpCards.add(new Card());
        faceUpCards.add(new Card());
        faceUpCards.add(new Card());
        faceUpCards.add(new Card());

        ActionResult result = validator.validateReserveCard(player, faceUpCards, 0, Locale.US);

        assertTrue(result.isSuccess());
    }

    @Test
    public void validateReserveCard_acceptsLastFaceUpCardWhenPlayerHasTwoReservedCards() {
        Player player = new Player();
        player.addReservedCard(new Card());
        player.addReservedCard(new Card());
        List<Card> faceUpCards = new ArrayList<>();
        faceUpCards.add(new Card());
        faceUpCards.add(new Card());
        faceUpCards.add(new Card());
        faceUpCards.add(new Card());

        ActionResult result = validator.validateReserveCard(player, faceUpCards, 3, Locale.US);

        assertTrue(result.isSuccess());
    }

    @Test
    public void validateReserveCard_rejectsPlayerWithThreeReservedCards() {
        Player player = new Player();
        player.addReservedCard(new Card());
        player.addReservedCard(new Card());
        player.addReservedCard(new Card());
        List<Card> faceUpCards = new ArrayList<>();
        faceUpCards.add(new Card());
        faceUpCards.add(new Card());
        faceUpCards.add(new Card());
        faceUpCards.add(new Card());

        ActionResult result = validator.validateReserveCard(player, faceUpCards, 0, Locale.US);

        assertFalse(result.isSuccess());
        assertEquals(MessageProvider.getMessage("error.invalid_reserve_card", Locale.US), result.getMessage());
    }

    @Test
    public void validateReserveCard_rejectsNullFaceUpCards() {
        Player player = new Player();

        ActionResult result = validator.validateReserveCard(player, null, 0, Locale.US);

        assertFalse(result.isSuccess());
        assertEquals(MessageProvider.getMessage("error.invalid_reserve_card", Locale.US), result.getMessage());
    }

    @Test
    public void validateReserveCard_rejectsEmptyFaceUpCards() {
        Player player = new Player();

        ActionResult result = validator.validateReserveCard(player, new ArrayList<>(), 0, Locale.US);

        assertFalse(result.isSuccess());
        assertEquals(MessageProvider.getMessage("error.invalid_reserve_card", Locale.US), result.getMessage());
    }

    @Test
    public void validateReserveCard_rejectsNegativeIndex() {
        Player player = new Player();
        List<Card> faceUpCards = new ArrayList<>();
        faceUpCards.add(new Card());
        faceUpCards.add(new Card());
        faceUpCards.add(new Card());
        faceUpCards.add(new Card());

        ActionResult result = validator.validateReserveCard(player, faceUpCards, -1, Locale.US);

        assertFalse(result.isSuccess());
        assertEquals(MessageProvider.getMessage("error.invalid_reserve_card", Locale.US), result.getMessage());
    }

    @Test
    public void validateReserveCard_rejectsIndexEqualToFaceUpCardsSize() {
        Player player = new Player();
        List<Card> faceUpCards = new ArrayList<>();
        faceUpCards.add(new Card());
        faceUpCards.add(new Card());
        faceUpCards.add(new Card());
        faceUpCards.add(new Card());

        ActionResult result = validator.validateReserveCard(player, faceUpCards, 4, Locale.US);

        assertFalse(result.isSuccess());
        assertEquals(MessageProvider.getMessage("error.invalid_reserve_card", Locale.US), result.getMessage());
    }

    @Test
    public void validateBuyCard_rejectsNullCard() {
        Player player = new Player();

        ActionResult result = validator.validateBuyCard(player, null, Locale.US);

        assertFalse(result.isSuccess());
        assertEquals(MessageProvider.getMessage("error.invalid_buy_card", Locale.US), result.getMessage());
    }

    @Test
    public void validateBuyCard_acceptsCardWithEmptyCost() {
        Player player = new Player();
        Card card = new Card(1, TokenColor.DIAMOND, Map.of(), 0);

        ActionResult result = validator.validateBuyCard(player, card, Locale.US);

        assertTrue(result.isSuccess());
    }

    @Test
    public void validateBuyCard_acceptsCardPaidWithSingleGemToken() {
        Player player = new Player();
        player.addTokens(Map.of(TokenColor.DIAMOND, 1));
        Card card = new Card(1, TokenColor.RUBY, Map.of(TokenColor.DIAMOND, 1), 0);

        ActionResult result = validator.validateBuyCard(player, card, Locale.US);

        assertTrue(result.isSuccess());
    }

    @Test
    public void validateBuyCard_acceptsCardPaidByMatchingBonus() {
        Player player = new Player();
        player.addDevelopmentCard(new Card(1, TokenColor.DIAMOND, Map.of(), 0));
        Card card = new Card(1, TokenColor.RUBY, Map.of(TokenColor.DIAMOND, 1), 0);

        ActionResult result = validator.validateBuyCard(player, card, Locale.US);

        assertTrue(result.isSuccess());
    }

    @Test
    public void validateBuyCard_acceptsCardWhenBonusExceedsCost() {
        Player player = new Player();
        player.addDevelopmentCard(new Card(1, TokenColor.DIAMOND, Map.of(), 0));
        player.addDevelopmentCard(new Card(1, TokenColor.DIAMOND, Map.of(), 0));
        Card card = new Card(1, TokenColor.RUBY, Map.of(TokenColor.DIAMOND, 1), 0);

        ActionResult result = validator.validateBuyCard(player, card, Locale.US);

        assertTrue(result.isSuccess());
    }

    @Test
    public void validateBuyCard_acceptsCardPaidWithGoldToken() {
        Player player = new Player();
        player.addTokens(Map.of(TokenColor.GOLD, 1));
        Card card = new Card(1, TokenColor.RUBY, Map.of(TokenColor.DIAMOND, 1), 0);

        ActionResult result = validator.validateBuyCard(player, card, Locale.US);

        assertTrue(result.isSuccess());
    }

    @Test
    public void validateBuyCard_acceptsCardPaidWithGemAndGoldTokens() {
        Player player = new Player();
        player.addTokens(Map.of(TokenColor.DIAMOND, 1, TokenColor.GOLD, 1));
        Card card = new Card(1, TokenColor.RUBY, Map.of(TokenColor.DIAMOND, 1, TokenColor.SAPPHIRE, 1), 0);

        ActionResult result = validator.validateBuyCard(player, card, Locale.US);

        assertTrue(result.isSuccess());
    }

    @Test
    public void validateBuyCard_rejectsCardWhenGemAndGoldTokensAreMissing() {
        Player player = new Player();
        Card card = new Card(1, TokenColor.RUBY, Map.of(TokenColor.DIAMOND, 1), 0);

        ActionResult result = validator.validateBuyCard(player, card, Locale.US);

        assertFalse(result.isSuccess());
        assertEquals(MessageProvider.getMessage("error.invalid_buy_card", Locale.US), result.getMessage());
    }

    @Test
    public void validateBuyCard_rejectsCardWhenGoldTokenCannotCoverFullShortage() {
        Player player = new Player();
        player.addTokens(Map.of(TokenColor.GOLD, 1));
        Card card = new Card(1, TokenColor.RUBY, Map.of(TokenColor.DIAMOND, 2), 0);

        ActionResult result = validator.validateBuyCard(player, card, Locale.US);

        assertFalse(result.isSuccess());
        assertEquals(MessageProvider.getMessage("error.invalid_buy_card", Locale.US), result.getMessage());
    }

    @Test
    public void validateBuyCard_acceptsCardPaidWithBonusGemAndGoldToken() {
        Player player = new Player();
        player.addDevelopmentCard(new Card(1, TokenColor.DIAMOND, Map.of(), 0));
        player.addTokens(Map.of(TokenColor.DIAMOND, 1, TokenColor.GOLD, 1));
        Card card = new Card(1, TokenColor.RUBY, Map.of(TokenColor.DIAMOND, 3), 0);

        ActionResult result = validator.validateBuyCard(player, card, Locale.US);

        assertTrue(result.isSuccess());
    }

    @Test
    public void validateBuyCard_rejectsCardWhenBonusAndGemTokenStillLeaveShortage() {
        Player player = new Player();
        player.addDevelopmentCard(new Card(1, TokenColor.DIAMOND, Map.of(), 0));
        player.addTokens(Map.of(TokenColor.DIAMOND, 1));
        Card card = new Card(1, TokenColor.RUBY, Map.of(TokenColor.DIAMOND, 3), 0);

        ActionResult result = validator.validateBuyCard(player, card, Locale.US);

        assertFalse(result.isSuccess());
        assertEquals(MessageProvider.getMessage("error.invalid_buy_card", Locale.US), result.getMessage());
    }

    @Test
    public void validateBuyCard_acceptsCardPaidWithTwoGemColors() {
        Player player = new Player();
        player.addTokens(Map.of(TokenColor.DIAMOND, 1, TokenColor.SAPPHIRE, 1));
        Card card = new Card(1, TokenColor.RUBY, Map.of(TokenColor.DIAMOND, 1, TokenColor.SAPPHIRE, 1), 0);

        ActionResult result = validator.validateBuyCard(player, card, Locale.US);

        assertTrue(result.isSuccess());
    }

    @Test
    public void validateBuyCard_rejectsCardWhenOneRequiredColorIsMissing() {
        Player player = new Player();
        player.addTokens(Map.of(TokenColor.DIAMOND, 1));
        Card card = new Card(1, TokenColor.RUBY, Map.of(TokenColor.DIAMOND, 1, TokenColor.SAPPHIRE, 1), 0);

        ActionResult result = validator.validateBuyCard(player, card, Locale.US);

        assertFalse(result.isSuccess());
        assertEquals(MessageProvider.getMessage("error.invalid_buy_card", Locale.US), result.getMessage());
    }

}
