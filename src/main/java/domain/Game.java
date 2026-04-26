package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Game
{
    private GamePhase phase;
    private List<Player> players;

    public void startGame(int playerCount, Locale locale) {
        if (playerCount < 2 || playerCount > 4) {
            throw new IllegalArgumentException();
        }

        initializePlayers(playerCount);
        phase = GamePhase.PLAYER_TURN;
    }

    public GamePhase getPhase() {
        return phase;
    }

    public void initializePlayers(int playerCount) {
        players = new ArrayList<>(playerCount);

        for (int i = 0; i < playerCount; i++) {
            players.add(new Player());
        }
    }

    public List<Player> getPlayers() {
        return players;
    }
}
