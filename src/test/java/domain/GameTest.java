package domain;

import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void startGame_rejectsTooFewPlayers() {
        Game game = new Game();

        ActionResult result = game.startGame(1, Locale.ENGLISH);
        assertFalse(result.isSuccess());
    }

    @Test
    void startGame_rejectsTooManyPlayers() {
        Game game = new Game();

        ActionResult result = game.startGame(5, Locale.ENGLISH);
        assertFalse(result.isSuccess());
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

        ActionResult result = game.startGame(1, Locale.ENGLISH);
        assertFalse(result.isSuccess());

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

    @Test
    void startGame_initializesFourFaceUpCardsForEachLevel() {
        Game game = new Game();
        game.startGame(2, Locale.US);

        assertEquals(4, game.getFaceUpCards(1).size());
        assertEquals(4, game.getFaceUpCards(2).size());
        assertEquals(4, game.getFaceUpCards(3).size());
    }

    @Test
    void startGame_initializesRemainingDecksAfterDealingFaceUpCards() {
        Game game = new Game();
        game.startGame(2, Locale.US);

        assertEquals(36, game.getDeck(1).cards.size());
        assertEquals(26, game.getDeck(2).cards.size());
        assertEquals(16, game.getDeck(3).cards.size());
    }

    @Test
    void startGame_twoPlayersRevealsThreeNobles() {
        Game game = new Game();
        game.startGame(2, Locale.US);

        assertEquals(3, game.getRevealedNobles().size());
    }

    @Test
    void startGame_threePlayersRevealsFourNobles() {
        Game game = new Game();
        game.startGame(3, Locale.US);

        assertEquals(4, game.getRevealedNobles().size());
    }

    @Test
    void startGame_fourPlayersRevealsFiveNobles() {
        Game game = new Game();
        game.startGame(4, Locale.US);

        assertEquals(5, game.getRevealedNobles().size());
    }

    @Test
    void failedStartGame_doesNotInitializeTableCardsOrNobles() {
        Game game = new Game();

        ActionResult result = game.startGame(1, Locale.US);

        assertFalse(result.isSuccess());
        assertNull(game.getFaceUpCards(1));
        assertNull(game.getDeck(1));
        assertNull(game.getRevealedNobles());
    }

    @Test
    void takeTokens_validThreeDifferentTokensUpdatesPlayerBankAndCurrentPlayer() {
        Game game = new Game();
        game.startGame(2, Locale.US);
        Player playerZero = game.getCurrentPlayer();

        ActionResult result = game.takeTokens(Map.of(TokenColor.DIAMOND, 1, TokenColor.RUBY, 1, TokenColor.ONYX, 1), Locale.US);

        assertTrue(result.isSuccess());
        assertEquals(1, playerZero.getTokenCount(TokenColor.DIAMOND));
        assertEquals(1, playerZero.getTokenCount(TokenColor.RUBY));
        assertEquals(1, playerZero.getTokenCount(TokenColor.ONYX));
        assertEquals(3, game.getTokenBank().getTokenCount(TokenColor.DIAMOND));
        assertEquals(3, game.getTokenBank().getTokenCount(TokenColor.RUBY));
        assertEquals(3, game.getTokenBank().getTokenCount(TokenColor.ONYX));
        assertEquals(game.getPlayers().get(1), game.getCurrentPlayer());
    }

    @Test
    void takeTokens_validTwoSameTokensUpdatesPlayerBankAndCurrentPlayer() {
        Game game = new Game();
        game.startGame(2, Locale.US);
        Player playerZero = game.getCurrentPlayer();

        ActionResult result = game.takeTokens(Map.of(TokenColor.DIAMOND, 2), Locale.US);

        assertTrue(result.isSuccess());
        assertEquals(2, playerZero.getTokenCount(TokenColor.DIAMOND));
        assertEquals(2, game.getTokenBank().getTokenCount(TokenColor.DIAMOND));
        assertEquals(game.getPlayers().get(1), game.getCurrentPlayer());
    }

    @Test
    void takeTokens_twoPlayerGameWrapsCurrentPlayerAfterBothPlayersTakeTokens() {
        Game game = new Game();
        game.startGame(2, Locale.US);
        Player playerZero = game.getPlayers().get(0);
        Player playerOne = game.getPlayers().get(1);

        ActionResult firstResult = game.takeTokens(Map.of(TokenColor.DIAMOND, 1, TokenColor.RUBY, 1, TokenColor.ONYX, 1), Locale.US);
        ActionResult secondResult = game.takeTokens(Map.of(TokenColor.SAPPHIRE, 1, TokenColor.EMERALD, 1, TokenColor.ONYX, 1), Locale.US);

        assertTrue(firstResult.isSuccess());
        assertTrue(secondResult.isSuccess());
        assertEquals(3, playerZero.getTotalTokenCount());
        assertEquals(3, playerOne.getTotalTokenCount());
        assertEquals(game.getPlayers().get(0), game.getCurrentPlayer());
    }

    @Test
    void takeTokens_threePlayerGameWrapsCurrentPlayerAfterAllPlayersTakeTokens() {
        Game game = new Game();
        game.startGame(3, Locale.US);
        Player playerZero = game.getPlayers().get(0);
        Player playerOne = game.getPlayers().get(1);
        Player playerTwo = game.getPlayers().get(2);

        ActionResult firstResult = game.takeTokens(Map.of(TokenColor.DIAMOND, 1, TokenColor.RUBY, 1, TokenColor.ONYX, 1), Locale.US);
        ActionResult secondResult = game.takeTokens(Map.of(TokenColor.SAPPHIRE, 1, TokenColor.EMERALD, 1, TokenColor.ONYX, 1), Locale.US);
        ActionResult thirdResult = game.takeTokens(Map.of(TokenColor.DIAMOND, 1, TokenColor.SAPPHIRE, 1, TokenColor.EMERALD, 1), Locale.US);

        assertTrue(firstResult.isSuccess());
        assertTrue(secondResult.isSuccess());
        assertTrue(thirdResult.isSuccess());
        assertEquals(3, playerZero.getTotalTokenCount());
        assertEquals(3, playerOne.getTotalTokenCount());
        assertEquals(3, playerTwo.getTotalTokenCount());
        assertEquals(game.getPlayers().get(0), game.getCurrentPlayer());
    }

    @Test
    void takeTokens_rejectsGoldAndLeavesGameStateUnchanged() {
        Game game = new Game();
        game.startGame(2, Locale.US);
        Player playerZero = game.getCurrentPlayer();

        ActionResult result = game.takeTokens(Map.of(TokenColor.GOLD, 1), Locale.US);

        assertFalse(result.isSuccess());
        assertEquals(MessageProvider.getMessage("error.invalid_token_selection", Locale.US), result.getMessage());
        assertEquals(0, playerZero.getTotalTokenCount());
        assertEquals(5, game.getTokenBank().getTokenCount(TokenColor.GOLD));
        assertEquals(playerZero, game.getCurrentPlayer());
    }

    @Test
    void takeTokens_rejectsTwoSameAndOneDifferentAndLeavesGameStateUnchanged() {
        Game game = new Game();
        game.startGame(2, Locale.US);
        Player playerZero = game.getCurrentPlayer();

        ActionResult result = game.takeTokens(Map.of(TokenColor.DIAMOND, 2, TokenColor.RUBY, 1), Locale.US);

        assertFalse(result.isSuccess());
        assertEquals(MessageProvider.getMessage("error.invalid_token_selection", Locale.US), result.getMessage());
        assertEquals(0, playerZero.getTotalTokenCount());
        assertEquals(4, game.getTokenBank().getTokenCount(TokenColor.DIAMOND));
        assertEquals(4, game.getTokenBank().getTokenCount(TokenColor.RUBY));
        assertEquals(playerZero, game.getCurrentPlayer());
    }

    @Test
    void takeTokens_rejectsEmptyTokenMapAndLeavesGameStateUnchanged() {
        Game game = new Game();
        game.startGame(2, Locale.US);
        Player playerZero = game.getCurrentPlayer();

        ActionResult result = game.takeTokens(Map.of(), Locale.US);

        assertFalse(result.isSuccess());
        assertEquals(MessageProvider.getMessage("error.invalid_token_selection", Locale.US), result.getMessage());
        assertEquals(0, playerZero.getTotalTokenCount());
        assertEquals(4, game.getTokenBank().getTokenCount(TokenColor.DIAMOND));
        assertEquals(playerZero, game.getCurrentPlayer());
    }

    @Test
    void takeTokens_rejectsActionThatWouldExceedTenTokensAndLeavesGameStateUnchanged() {
        Game game = new Game();
        game.startGame(2, Locale.US);
        Player playerZero = game.getCurrentPlayer();
        playerZero.addTokens(Map.of(TokenColor.DIAMOND, 4, TokenColor.RUBY, 4, TokenColor.ONYX, 1));

        ActionResult result = game.takeTokens(Map.of(TokenColor.DIAMOND, 2), Locale.US);

        assertFalse(result.isSuccess());
        assertEquals(MessageProvider.getMessage("error.invalid_token_selection", Locale.US), result.getMessage());
        assertEquals(9, playerZero.getTotalTokenCount());
        assertEquals(4, game.getTokenBank().getTokenCount(TokenColor.DIAMOND));
        assertEquals(playerZero, game.getCurrentPlayer());
    }

    @Test
    void takeTokens_beforeStartGameReturnsFailureAndKeepsSetupPhase() {
        Game game = new Game();

        ActionResult result = game.takeTokens(Map.of(TokenColor.DIAMOND, 1, TokenColor.RUBY, 1, TokenColor.ONYX, 1), Locale.US);

        assertFalse(result.isSuccess());
        assertEquals(GamePhase.SETUP, game.getPhase());
    }
}
