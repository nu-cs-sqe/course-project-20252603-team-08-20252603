package domain;

import java.util.Map;

public class Noble {
    public Map<TokenColor, Integer> requirements;
    public int prestigePoints;

    public Noble(Map<TokenColor, Integer> requirements, int prestigePoints) {
        this.requirements = requirements;
        this.prestigePoints = prestigePoints;
    }
}
