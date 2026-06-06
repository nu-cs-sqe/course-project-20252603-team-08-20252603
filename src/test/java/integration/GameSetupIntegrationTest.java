package integration;

import domain.ActionResult;
import domain.Game;
import domain.GamePhase;
import domain.MessageProvider;
import domain.Noble;
import domain.Player;
import domain.TokenColor;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameSetupIntegrationTest {

    private static final int TOTAL_DEVELOPMENT_CARDS = 90;
    private static final int FACE_UP_CARDS_PER_LEVEL = 4;
    private static final int LEVEL_ONE_DECK_SIZE = 36;
    private static final int LEVEL_TWO_DECK_SIZE = 26;
    private static final int LEVEL_THREE_DECK_SIZE = 16;
    private static final int TWO_PLAYER_GEM_TOKEN_COUNT = 4;
    private static final int THREE_PLAYER_GEM_TOKEN_COUNT = 5;
    private static final int FOUR_PLAYER_GEM_TOKEN_COUNT = 7;
    private static final int GOLD_TOKEN_COUNT = 5;

    @Test
    void integrationTC1_successfulFullGameSetupForTwoPlayers() {
        Game game = new Game();

        ActionResult result = game.startGame(2, Locale.US);

        assertTrue(result.isSuccess());
        assertEquals(GamePhase.PLAYER_TURN, game.getPhase());
        assertEquals(2, game.getPlayers().size());
        assertEquals(game.getPlayers().get(0), game.getCurrentPlayer());

        for (Player player : game.getPlayers()) {
            assertEquals(0, player.getPrestigePoints());
            assertEquals(0, player.getDevelopmentCards().size());
            assertEquals(0, player.getReservedCards().size());
            for (TokenColor color : TokenColor.values()) {
                assertEquals(0, player.getTokenCount(color));
            }
        }

        for (TokenColor color : TokenColor.values()) {
            if (color == TokenColor.GOLD) {
                assertEquals(GOLD_TOKEN_COUNT, game.getTokenBank().getTokenCount(color));
            } else {
                assertEquals(TWO_PLAYER_GEM_TOKEN_COUNT, game.getTokenBank().getTokenCount(color));
            }
        }

        assertEquals(FACE_UP_CARDS_PER_LEVEL, game.getFaceUpCards(1).size());
        assertEquals(FACE_UP_CARDS_PER_LEVEL, game.getFaceUpCards(2).size());
        assertEquals(FACE_UP_CARDS_PER_LEVEL, game.getFaceUpCards(3).size());
        assertEquals(LEVEL_ONE_DECK_SIZE, game.getDeck(1).cards.size());
        assertEquals(LEVEL_TWO_DECK_SIZE, game.getDeck(2).cards.size());
        assertEquals(LEVEL_THREE_DECK_SIZE, game.getDeck(3).cards.size());
        assertEquals(3, game.getRevealedNobles().size());

        for (Noble noble : game.getRevealedNobles()) {
            assertFalse(noble.requirements.isEmpty());
            assertTrue(noble.prestigePoints > 0);
            assertNotNull(noble);
        }

        int cardsInPlay = game.getFaceUpCards(1).size()
                + game.getFaceUpCards(2).size()
                + game.getFaceUpCards(3).size()
                + game.getDeck(1).cards.size()
                + game.getDeck(2).cards.size()
                + game.getDeck(3).cards.size();
        assertEquals(TOTAL_DEVELOPMENT_CARDS, cardsInPlay);
    }

    @Test
    void integrationTC2_invalidPlayerCountBlocksSetupPipeline() {
        Game game = new Game();

        ActionResult result = game.startGame(1, Locale.US);

        assertFalse(result.isSuccess());
        assertEquals(
                MessageProvider.getMessage("error.invalid_player_count", Locale.US),
                result.getMessage());
        assertEquals(GamePhase.SETUP, game.getPhase());
        assertNull(game.getTokenBank());
        assertNull(game.getFaceUpCards(1));
        assertNull(game.getDeck(1));
        assertNull(game.getRevealedNobles());
    }

    @Test
    void integrationTC3_tooManyPlayersBlocksSetupPipeline() {
        Game game = new Game();

        ActionResult result = game.startGame(5, Locale.US);

        assertFalse(result.isSuccess());
        assertEquals(
                MessageProvider.getMessage("error.invalid_player_count", Locale.US),
                result.getMessage());
        assertEquals(GamePhase.SETUP, game.getPhase());
        assertNull(game.getTokenBank());
        assertNull(game.getFaceUpCards(1));
        assertNull(game.getDeck(1));
        assertNull(game.getRevealedNobles());
    }

    @Test
    void integrationTC4_playerCountScalesTokenBankAndNobleSupply() {
        Game twoPlayerGame = new Game();
        Game threePlayerGame = new Game();
        Game fourPlayerGame = new Game();

        assertTrue(twoPlayerGame.startGame(2, Locale.US).isSuccess());
        assertTrue(threePlayerGame.startGame(3, Locale.US).isSuccess());
        assertTrue(fourPlayerGame.startGame(4, Locale.US).isSuccess());

        assertGemTokenCountPerColor(twoPlayerGame, TWO_PLAYER_GEM_TOKEN_COUNT);
        assertEquals(3, twoPlayerGame.getRevealedNobles().size());

        assertGemTokenCountPerColor(threePlayerGame, THREE_PLAYER_GEM_TOKEN_COUNT);
        assertEquals(4, threePlayerGame.getRevealedNobles().size());

        assertGemTokenCountPerColor(fourPlayerGame, FOUR_PLAYER_GEM_TOKEN_COUNT);
        assertEquals(5, fourPlayerGame.getRevealedNobles().size());
    }

    private static void assertGemTokenCountPerColor(Game game, int expectedGemCount) {
        for (TokenColor color : TokenColor.values()) {
            if (color != TokenColor.GOLD) {
                assertEquals(expectedGemCount, game.getTokenBank().getTokenCount(color));
            }
        }
    }
}
