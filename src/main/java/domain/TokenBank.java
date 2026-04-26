package domain;

import java.util.EnumMap;
import java.util.Map;

public class TokenBank {
    private static final int TWO_PLAYER_TOKEN_COUNT = 4;
    private static final int THREE_PLAYER_TOKEN_COUNT = 5;
    private static final int FOUR_PLAYER_TOKEN_COUNT = 7;
    private static final int GOLD_TOKEN_COUNT = 5;

    private final Map<TokenColor, Integer> tokens;

    public TokenBank() {
        this.tokens = new EnumMap<>(TokenColor.class);
    }

    public void initialize(int playerCount) {
        int tokenCount = getGemTokenCount(playerCount);

        tokens.put(TokenColor.DIAMOND, tokenCount);
        tokens.put(TokenColor.SAPPHIRE, tokenCount);
        tokens.put(TokenColor.EMERALD, tokenCount);
        tokens.put(TokenColor.RUBY, tokenCount);
        tokens.put(TokenColor.ONYX, tokenCount);
        tokens.put(TokenColor.GOLD, GOLD_TOKEN_COUNT);
    }

    public int getTokenCount(TokenColor color) {
        return tokens.getOrDefault(color, 0);
    }

    private int getGemTokenCount(int playerCount) {
        if (playerCount == 2) {
            return TWO_PLAYER_TOKEN_COUNT;
        }

        if (playerCount == 3) {
            return THREE_PLAYER_TOKEN_COUNT;
        }

        if (playerCount == 4) {
            return FOUR_PLAYER_TOKEN_COUNT;
        }

        throw new IllegalArgumentException("Player count must be between 2 and 4.");
    }
}