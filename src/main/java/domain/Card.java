package domain;

import java.util.Map;

public class Card {
    public int level;
    public TokenColor bonusColor;
    public Map<TokenColor, Integer> cost;
    public int prestigePoints;

    public Card(int level, TokenColor bonusColor, Map<TokenColor, Integer> cost, int prestigePoints) {
        this.level = level;
        this.bonusColor = bonusColor;
        this.cost = cost;
        this.prestigePoints = prestigePoints;
    }
}
