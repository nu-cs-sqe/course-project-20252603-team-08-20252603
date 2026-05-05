package domain;

import java.util.Locale;

public class RuleValidator {

    public ActionResult validatePlayerCount(int count, Locale locale) {
        if (count >= 2 && count <= 4) {
            return ActionResult.success();
        }
        String errorMessage = MessageProvider.getMessage("error.invalid_player_count", locale);
        return ActionResult.failure(errorMessage);
    }
}