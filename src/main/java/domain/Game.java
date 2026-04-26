package domain;

import java.util.Locale;

public class Game
{
    private GamePhase phase;

    public void startGame(int playerCount, Locale locale) {
        if (playerCount < 2 || playerCount > 4) {
            throw new IllegalArgumentException();
        }

        phase = GamePhase.PLAYER_TURN;
    }

    public GamePhase getPhase() {
        return phase;
    }
}
