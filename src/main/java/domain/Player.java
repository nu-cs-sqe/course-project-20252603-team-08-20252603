package domain;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class Player {
    private final Map<TokenColor, Integer> tokens;
    private final List<Card> developmentCards;
    private final List<Card> reservedCards;
    private int prestigePoints;

    public Player() {
        this.tokens = new EnumMap<>(TokenColor.class);
        this.developmentCards = new ArrayList<>();
        this.reservedCards = new ArrayList<>();
        this.prestigePoints = 0;
    }

    public int getPrestigePoints() {
        return prestigePoints;
    }

    public int getTokenCount(TokenColor color) {
        return tokens.getOrDefault(color, 0);
    }

    public void addTokens(Map<TokenColor, Integer> tokensToAdd) {
    }

    public void removeTokens(Map<TokenColor, Integer> tokensToRemove) {
    }

    public List<Card> getDevelopmentCards() {
        return developmentCards;
    }

    public List<Card> getReservedCards() {
        return reservedCards;
    }

    public int getTotalTokenCount() {
        int total = 0;

        for (int count : tokens.values()) {
            total += count;
        }

        return total;
    }
}
