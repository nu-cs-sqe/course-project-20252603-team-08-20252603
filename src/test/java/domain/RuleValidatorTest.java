package domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Stream;

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
    public void validateTakeTokens_acceptsTwoSameGemTokensWhenBankHasExactlyFourBoundary() {
        Player player = new Player();
        TokenBank bank = new TokenBank();
        bank.addTokens(Map.of(TokenColor.DIAMOND, 4));
        assertEquals(4, bank.getTokenCount(TokenColor.DIAMOND));

        ActionResult result = validator.validateTakeTokens(player, bank, Map.of(TokenColor.DIAMOND, 2), Locale.US);

        assertTrue(result.isSuccess());
    }

    @Test
    public void validateTakeTokens_rejectsTwoSameGemTokensWhenBankHasExactlyThreeBoundary() {
        Player player = new Player();
        TokenBank bank = new TokenBank();
        bank.addTokens(Map.of(TokenColor.DIAMOND, 3));
        assertEquals(3, bank.getTokenCount(TokenColor.DIAMOND));

        ActionResult result = validator.validateTakeTokens(player, bank, Map.of(TokenColor.DIAMOND, 2), Locale.US);

        assertFalse(result.isSuccess());
        assertEquals(MessageProvider.getMessage("error.invalid_token_selection", Locale.US), result.getMessage());
    }

    @Test
    public void validateTakeTokens_rejectsTokenMapWithZeroCount() {
        Player player = new Player();
        TokenBank bank = new TokenBank();
        bank.initialize(2);

        ActionResult result = validator.validateTakeTokens(player, bank, Map.of(TokenColor.DIAMOND, 0), Locale.US);

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

    @ParameterizedTest
    @MethodSource("takeTokensBoundaryCases")
    public void validateTakeTokens_boundaryTokenRules(TakeTokensBoundaryCase testCase) {
        Player player = new Player();
        TokenBank bank = new TokenBank();
        bank.initialize(2);
        testCase.prepare(player, bank);

        ActionResult result = validator.validateTakeTokens(player, bank, testCase.tokensToTake(), Locale.US);

        assertEquals(testCase.expectedSuccess(), result.isSuccess());
        if (!testCase.expectedSuccess()) {
            assertEquals(MessageProvider.getMessage("error.invalid_token_selection", Locale.US), result.getMessage());
        }
    }

    private static Stream<Arguments> takeTokensBoundaryCases() {
        return Stream.of(
                Arguments.of(TakeTokensBoundaryCase.BANK_COUNT_EQUALS_TAKE_COUNT),
                Arguments.of(TakeTokensBoundaryCase.PLAYER_AT_TOKEN_LIMIT_BOUNDARY),
                Arguments.of(TakeTokensBoundaryCase.NEGATIVE_TOKEN_COUNT)
        );
    }

    private enum TakeTokensBoundaryCase {
        BANK_COUNT_EQUALS_TAKE_COUNT {
            @Override
            void prepare(Player player, TokenBank bank) {
                bank.removeTokens(Map.of(TokenColor.DIAMOND, 3));
            }

            @Override
            Map<TokenColor, Integer> tokensToTake() {
                return Map.of(TokenColor.DIAMOND, 1, TokenColor.RUBY, 1, TokenColor.ONYX, 1);
            }

            @Override
            boolean expectedSuccess() {
                return true;
            }
        },
        PLAYER_AT_TOKEN_LIMIT_BOUNDARY {
            @Override
            void prepare(Player player, TokenBank bank) {
                player.addTokens(Map.of(TokenColor.DIAMOND, 4, TokenColor.RUBY, 3));
            }

            @Override
            Map<TokenColor, Integer> tokensToTake() {
                return Map.of(TokenColor.DIAMOND, 1, TokenColor.SAPPHIRE, 1, TokenColor.EMERALD, 1);
            }

            @Override
            boolean expectedSuccess() {
                return true;
            }
        },
        NEGATIVE_TOKEN_COUNT {
            @Override
            void prepare(Player player, TokenBank bank) {
            }

            @Override
            Map<TokenColor, Integer> tokensToTake() {
                Map<TokenColor, Integer> tokens = new HashMap<>();
                tokens.put(TokenColor.DIAMOND, -1);
                return tokens;
            }

            @Override
            boolean expectedSuccess() {
                return false;
            }
        };

        abstract void prepare(Player player, TokenBank bank);

        abstract Map<TokenColor, Integer> tokensToTake();

        abstract boolean expectedSuccess();
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
    public void validateBuyCard_acceptsCardWhenGoldNeededEqualsPlayerGoldExactly() {
        Player player = new Player();
        player.addTokens(Map.of(TokenColor.GOLD, 2));
        assertEquals(2, player.getTokenCount(TokenColor.GOLD));
        Card card = new Card(1, TokenColor.RUBY, Map.of(TokenColor.DIAMOND, 2), 0);

        ActionResult result = validator.validateBuyCard(player, card, Locale.US);

        assertTrue(result.isSuccess());
    }

    @Test
    public void validateBuyCard_rejectsCardWhenGoldNeededExceedsPlayerGoldByOne() {
        Player player = new Player();
        player.addTokens(Map.of(TokenColor.GOLD, 2));
        assertEquals(2, player.getTokenCount(TokenColor.GOLD));
        Card card = new Card(1, TokenColor.RUBY, Map.of(TokenColor.DIAMOND, 3), 0);

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

    @Test
    public void validateNobleVisit_acceptsSingleColorRequirementAtBoundary() {
        Player player = new Player();
        player.addDevelopmentCard(new Card(1, TokenColor.DIAMOND, Map.of(), 0));
        player.addDevelopmentCard(new Card(1, TokenColor.DIAMOND, Map.of(), 0));
        player.addDevelopmentCard(new Card(1, TokenColor.DIAMOND, Map.of(), 0));
        Noble noble = new Noble(Map.of(TokenColor.DIAMOND, 3), 3);

        ActionResult result = validator.validateNobleVisit(player, noble, Locale.US);

        assertTrue(result.isSuccess());
    }

    @Test
    public void validateNobleVisit_rejectsSingleColorRequirementBelowBoundary() {
        Player player = new Player();
        player.addDevelopmentCard(new Card(1, TokenColor.DIAMOND, Map.of(), 0));
        player.addDevelopmentCard(new Card(1, TokenColor.DIAMOND, Map.of(), 0));
        Noble noble = new Noble(Map.of(TokenColor.DIAMOND, 3), 3);

        ActionResult result = validator.validateNobleVisit(player, noble, Locale.US);

        assertFalse(result.isSuccess());
        assertEquals(MessageProvider.getMessage("error.invalid_noble_visit", Locale.US), result.getMessage());
    }

    @Test
    public void validateNobleVisit_acceptsMultipleColorRequirementsAtBoundary() {
        Player player = new Player();
        player.addDevelopmentCard(new Card(1, TokenColor.DIAMOND, Map.of(), 0));
        player.addDevelopmentCard(new Card(1, TokenColor.DIAMOND, Map.of(), 0));
        player.addDevelopmentCard(new Card(1, TokenColor.DIAMOND, Map.of(), 0));
        player.addDevelopmentCard(new Card(1, TokenColor.RUBY, Map.of(), 0));
        player.addDevelopmentCard(new Card(1, TokenColor.RUBY, Map.of(), 0));
        player.addDevelopmentCard(new Card(1, TokenColor.RUBY, Map.of(), 0));
        Noble noble = new Noble(Map.of(TokenColor.DIAMOND, 3, TokenColor.RUBY, 3), 3);

        ActionResult result = validator.validateNobleVisit(player, noble, Locale.US);

        assertTrue(result.isSuccess());
    }

    @Test
    public void validateNobleVisit_rejectsWhenOneRequiredColorIsBelowBoundary() {
        Player player = new Player();
        player.addDevelopmentCard(new Card(1, TokenColor.DIAMOND, Map.of(), 0));
        player.addDevelopmentCard(new Card(1, TokenColor.DIAMOND, Map.of(), 0));
        player.addDevelopmentCard(new Card(1, TokenColor.DIAMOND, Map.of(), 0));
        player.addDevelopmentCard(new Card(1, TokenColor.RUBY, Map.of(), 0));
        player.addDevelopmentCard(new Card(1, TokenColor.RUBY, Map.of(), 0));
        Noble noble = new Noble(Map.of(TokenColor.DIAMOND, 3, TokenColor.RUBY, 3), 3);

        ActionResult result = validator.validateNobleVisit(player, noble, Locale.US);

        assertFalse(result.isSuccess());
        assertEquals(MessageProvider.getMessage("error.invalid_noble_visit", Locale.US), result.getMessage());
    }

    @Test
    public void validateNobleVisit_acceptsEmptyRequirements() {
        Player player = new Player();
        Noble noble = new Noble(Map.of(), 3);

        ActionResult result = validator.validateNobleVisit(player, noble, Locale.US);

        assertTrue(result.isSuccess());
    }

    @Test
    public void validateNobleVisit_rejectsNullPlayer() {
        Noble noble = new Noble(Map.of(TokenColor.DIAMOND, 3), 3);

        ActionResult result = validator.validateNobleVisit(null, noble, Locale.US);

        assertFalse(result.isSuccess());
        assertEquals(MessageProvider.getMessage("error.invalid_noble_visit", Locale.US), result.getMessage());
    }

    @Test
    public void validateNobleVisit_rejectsNullNoble() {
        Player player = new Player();
        player.addDevelopmentCard(new Card(1, TokenColor.DIAMOND, Map.of(), 0));
        player.addDevelopmentCard(new Card(1, TokenColor.DIAMOND, Map.of(), 0));
        player.addDevelopmentCard(new Card(1, TokenColor.DIAMOND, Map.of(), 0));

        ActionResult result = validator.validateNobleVisit(player, null, Locale.US);

        assertFalse(result.isSuccess());
        assertEquals(MessageProvider.getMessage("error.invalid_noble_visit", Locale.US), result.getMessage());
    }

    @Test
    public void validateNobleVisit_rejectsNullRequirements() {
        Player player = new Player();
        player.addDevelopmentCard(new Card(1, TokenColor.DIAMOND, Map.of(), 0));
        player.addDevelopmentCard(new Card(1, TokenColor.DIAMOND, Map.of(), 0));
        player.addDevelopmentCard(new Card(1, TokenColor.DIAMOND, Map.of(), 0));
        Noble noble = new Noble(null, 3);

        ActionResult result = validator.validateNobleVisit(player, noble, Locale.US);

        assertFalse(result.isSuccess());
        assertEquals(MessageProvider.getMessage("error.invalid_noble_visit", Locale.US), result.getMessage());
    }

}
