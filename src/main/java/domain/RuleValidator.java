package domain;

import java.util.Locale;
import java.util.List;
import java.util.Map;

public class RuleValidator {

    public ActionResult validatePlayerCount(int count, Locale locale) {
        if (count >= 2 && count <= 4) {
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

        if (player.getTotalTokenCount() + totalTokensToTake > 10) {
            return ActionResult.failure(errorMessage);
        }

        if (totalTokensToTake == 3 && tokensToTake.size() == 3) {
            return ActionResult.success();
        }

        if (totalTokensToTake == 2 && tokensToTake.size() == 1) {
            TokenColor color = tokensToTake.keySet().iterator().next();
            if (tokensToTake.get(color) == 2 && bank.getTokenCount(color) >= 4) {
                return ActionResult.success();
            }
        }

        return ActionResult.failure(errorMessage);
    }

    public ActionResult validateReserveCard(Player player, List<Card> faceUpCards, int cardIndex, Locale locale) {
        String errorMessage = MessageProvider.getMessage("error.invalid_reserve_card", locale);

        if (player.getReservedCards().size() >= 3) {
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
}
