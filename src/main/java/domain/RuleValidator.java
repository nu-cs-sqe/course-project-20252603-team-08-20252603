package domain;

import java.util.Locale;
import java.util.List;
import java.util.Map;

public class RuleValidator {
    private static final int MAX_PLAYER_COUNT = 4;
    private static final int PLAYER_TOKEN_LIMIT = 10;
    private static final int THREE_TOKEN_TAKE_COUNT = 3;
    private static final int TWO_TOKEN_TAKE_COUNT = 2;
    private static final int MIN_BANK_TOKENS_FOR_DOUBLE_TAKE = 4;
    private static final int RESERVED_CARD_LIMIT = 3;

    public ActionResult validatePlayerCount(int count, Locale locale) {
        if (count >= 2 && count <= MAX_PLAYER_COUNT) {
            return ActionResult.success();
        }
        String errorMessage = MessageProvider.getMessage("error.invalid_player_count", locale);
        return ActionResult.failure(errorMessage);
    }

    public ActionResult validateTakeTokens(Player player, TokenBank bank, Map<TokenColor, Integer> tokensToTake, Locale locale) {
        String errorMessage = MessageProvider.getMessage("error.invalid_token_selection", locale);

        if (tokensToTake.isEmpty()) {
            return ActionResult.failure(errorMessage);
        }

        int totalTokensToTake = 0;
        for (Map.Entry<TokenColor, Integer> entry : tokensToTake.entrySet()) {
            TokenColor color = entry.getKey();
            int count = entry.getValue();

            if (color == TokenColor.GOLD || count <= 0 || bank.getTokenCount(color) < count) {
                return ActionResult.failure(errorMessage);
            }

            totalTokensToTake += count;
        }

        if (player.getTotalTokenCount() + totalTokensToTake > PLAYER_TOKEN_LIMIT) {
            return ActionResult.failure(errorMessage);
        }

        if (totalTokensToTake == THREE_TOKEN_TAKE_COUNT && tokensToTake.size() == THREE_TOKEN_TAKE_COUNT) {
            return ActionResult.success();
        }

        if (totalTokensToTake == TWO_TOKEN_TAKE_COUNT && tokensToTake.size() == 1) {
            Map.Entry<TokenColor, Integer> entry = tokensToTake.entrySet().iterator().next();
            if (entry.getValue() == TWO_TOKEN_TAKE_COUNT && bank.getTokenCount(entry.getKey()) >= MIN_BANK_TOKENS_FOR_DOUBLE_TAKE) {
                return ActionResult.success();
            }
        }

        return ActionResult.failure(errorMessage);
    }

    public ActionResult validateReserveCard(Player player, List<Card> faceUpCards, int cardIndex, Locale locale) {
        String errorMessage = MessageProvider.getMessage("error.invalid_reserve_card", locale);

        if (player.getReservedCards().size() >= RESERVED_CARD_LIMIT) {
            return ActionResult.failure(errorMessage);
        }

        if (faceUpCards == null || faceUpCards.isEmpty()) {
            return ActionResult.failure(errorMessage);
        }

        if (cardIndex < 0 || cardIndex >= faceUpCards.size()) {
            return ActionResult.failure(errorMessage);
        }

        return ActionResult.success();
    }

    public ActionResult validateBuyCard(Player player, Card card, Locale locale) {
        String errorMessage = MessageProvider.getMessage("error.invalid_buy_card", locale);

        if (card == null) {
            return ActionResult.failure(errorMessage);
        }

        int goldNeeded = 0;
        for (Map.Entry<TokenColor, Integer> entry : card.cost.entrySet()) {
            TokenColor color = entry.getKey();
            int cost = entry.getValue();
            int remainingCost = cost - player.getBonusCount(color);
            if (remainingCost > 0) {
                int gemTokensUsed = Math.min(player.getTokenCount(color), remainingCost);
                goldNeeded += remainingCost - gemTokensUsed;
            }
        }

        if (goldNeeded <= player.getTokenCount(TokenColor.GOLD)) {
            return ActionResult.success();
        }

        return ActionResult.failure(errorMessage);
    }
}
