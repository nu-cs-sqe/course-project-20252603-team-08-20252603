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


    @Test
    void getTokenBank_returnsNullBeforeStartGame() {
        Game game = new Game();

        assertNull(game.getTokenBank());
    }

    @Test
    void startGame_initializesTokenBankForTwoPlayers() {
        Game game = new Game();

        game.startGame(2, Locale.ENGLISH);

        assertEquals(4, game.getTokenBank().getTokenCount(TokenColor.DIAMOND));
    }

    @Test
    void startGame_initializesTokenBankForThreePlayers() {
        Game game = new Game();

        game.startGame(3, Locale.ENGLISH);

        assertEquals(5, game.getTokenBank().getTokenCount(TokenColor.DIAMOND));
    }

    @Test
    void startGame_initializesTokenBankForFourPlayers() {
        Game game = new Game();

        game.startGame(4, Locale.ENGLISH);

        assertEquals(7, game.getTokenBank().getTokenCount(TokenColor.DIAMOND));
    }

    @Test
    void startGame_initializesGoldTokens() {
        Game game = new Game();

        game.startGame(2, Locale.ENGLISH);

        assertEquals(5, game.getTokenBank().getTokenCount(TokenColor.GOLD));
    }

    @Test
    void failedStartGame_doesNotInitializeTokenBank() {
        Game game = new Game();

        assertThrows(IllegalArgumentException.class, () -> {
            game.startGame(1, Locale.ENGLISH);
        });

        assertNull(game.getTokenBank());
    }

    @Test
    public void testStartGameWithValidPlayers() {
        Game game = new Game();
        ActionResult result = game.startGame(3, Locale.US);
        assertTrue(result.isSuccess());
        assertEquals(GamePhase.PLAYER_TURN, game.getPhase());
    }

    @Test
    public void testStartGameWithInvalidPlayersReturnsFailure() {
        Game game = new Game();
        ActionResult result = game.startGame(5, Locale.US);
        assertFalse(result.isSuccess());
        assertEquals(GamePhase.SETUP, game.getPhase());
    }
}