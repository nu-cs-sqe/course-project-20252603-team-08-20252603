package domain;

import java.util.Locale;
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
        return ActionResult.success();
    }
}
