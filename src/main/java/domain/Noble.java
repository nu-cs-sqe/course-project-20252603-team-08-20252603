package domain;

import java.util.Map;

public class Noble<C> {
    public Map<C, Integer> requirements;
    public int prestigePoints;

    public Noble(Map<C, Integer> requirements, int prestigePoints) {
        this.requirements = requirements;
        this.prestigePoints = prestigePoints;
    }
}
