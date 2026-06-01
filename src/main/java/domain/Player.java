package domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class Player {
    private final Map<TokenColor, Integer> tokens;
    private final List<Card> developmentCards;
    private final List<Card> reservedCards;
    private final List<Noble> nobles;
    private int prestigePoints;

    public Player() {
        this.tokens = new EnumMap<>(TokenColor.class);
        this.developmentCards = new ArrayList<>();
        this.reservedCards = new ArrayList<>();
        this.nobles = new ArrayList<>();
        this.prestigePoints = 0;
    }

    public int getPrestigePoints() {
        return prestigePoints;
    }

    public int getTokenCount(TokenColor color) {
        return tokens.getOrDefault(color, 0);
    }

    public void addTokens(Map<TokenColor, Integer> tokensToAdd) {
        for (Map.Entry<TokenColor, Integer> entry : tokensToAdd.entrySet()) {
            TokenColor color = entry.getKey();
            int count = entry.getValue();
            tokens.put(color, getTokenCount(color) + count);
        }
    }

    public void removeTokens(Map<TokenColor, Integer> tokensToRemove) {
        for (Map.Entry<TokenColor, Integer> entry : tokensToRemove.entrySet()) {
            if (getTokenCount(entry.getKey()) < entry.getValue()) {
                throw new IllegalArgumentException();
            }
        }

        for (Map.Entry<TokenColor, Integer> entry : tokensToRemove.entrySet()) {
            TokenColor color = entry.getKey();
            int count = entry.getValue();
            tokens.put(color, getTokenCount(color) - count);
        }
    }

    public void addReservedCard(Card card) {
        reservedCards.add(card);
    }

    public void removeReservedCard(Card card) {
        if (!reservedCards.remove(card)) {
            throw new IllegalArgumentException();
        }
    }

    public void addDevelopmentCard(Card card) {
        developmentCards.add(card);
        prestigePoints += card.prestigePoints;
    }

    public void addNoble(Noble noble) {
        nobles.add(noble);
        prestigePoints += noble.prestigePoints;
    }

    public int getBonusCount(TokenColor color) {
        if (color == TokenColor.GOLD) {
            return 0;
        }

        int bonusCount = 0;
        for (Card card : developmentCards) {
            if (card.bonusColor == color) {
                bonusCount++;
            }
        }
        return bonusCount;
    }

    public List<Card> getDevelopmentCards() {
        return Collections.unmodifiableList(developmentCards);
    }

    public List<Card> getReservedCards() {
        return Collections.unmodifiableList(reservedCards);
    }

    public List<Noble> getNobles() {
        return Collections.unmodifiableList(nobles);
    }

    public int getTotalTokenCount() {
        int total = 0;

        for (int count : tokens.values()) {
            total += count;
        }

        return total;
    }
}
