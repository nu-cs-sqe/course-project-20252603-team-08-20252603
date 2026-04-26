package domain;

import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void startGame_rejectsTooFewPlayers() {
        Game game = new Game();

        assertThrows(IllegalArgumentException.class, () -> {
            game.startGame(1, Locale.ENGLISH);
        });
    }

    @Test
    void startGame_rejectsTooManyPlayers() {
        Game game = new Game();

        assertThrows(IllegalArgumentException.class, () -> {
            game.startGame(5, Locale.ENGLISH);
        });
    }

    @Test
    void startGame_acceptsMinimumValidPlayers() {
        Game game = new Game();

        game.startGame(2, Locale.ENGLISH);

        assertEquals(GamePhase.PLAYER_TURN, game.getPhase());
    }

    @Test
    void startGame_acceptsMaximumValidPlayers() {
        Game game = new Game();

        game.startGame(4, Locale.ENGLISH);

        assertEquals(GamePhase.PLAYER_TURN, game.getPhase());
    }

    @Test
    void startGame_setsPhaseToPlayerTurnForValidPlayerCount() {
        Game game = new Game();

        game.startGame(3, Locale.ENGLISH);

        assertEquals(GamePhase.PLAYER_TURN, game.getPhase());
    }


    @Test
    void startGame_createsCorrectNumberOfPlayers() {
        Game game = new Game();

        game.startGame(2, Locale.ENGLISH);

        assertEquals(2, game.getPlayers().size());
    }

    @Test
    void newGame_startsInSetupPhase() {
        Game game = new Game();

        assertEquals(GamePhase.SETUP, game.getPhase());
    }

    @Test
    void startGame_setsCurrentPlayerToFirstPlayerForTwoPlayers() {
        Game game = new Game();

        game.startGame(2, Locale.ENGLISH);

        assertEquals(game.getPlayers().get(0), game.getCurrentPlayer());
    }

    @Test
    void startGame_setsCurrentPlayerToFirstPlayerForFourPlayers() {
        Game game = new Game();

        game.startGame(4, Locale.ENGLISH);

        assertEquals(game.getPlayers().get(0), game.getCurrentPlayer());
    }
}