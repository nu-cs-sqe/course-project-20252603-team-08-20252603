package domain;

import java.util.Map;

public class Card<C> {
    public int level;
    public C bonusColor;
    public Map<C, Integer> cost;
    public int prestigePoints;

    public Card(int level, C bonusColor, Map<C, Integer> cost, int prestigePoints) {
        this.level = level;
        this.bonusColor = bonusColor;
        this.cost = cost;
        this.prestigePoints = prestigePoints;
    }
}
