package domain;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
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
    void newGame_hasNoWinner() {
        Game game = new Game();

        assertNull(game.getWinner());
        assertTrue(game.getWinners().isEmpty());
    }

    @Test
    void startGame_hasNoWinnerBeforeAnyPlayerReachesFifteenPrestigePoints() {
        Game game = new Game();

        game.startGame(2, Locale.US);

        assertNull(game.getWinner());
        assertTrue(game.getWinners().isEmpty());
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
    void getCurrentPlayerIndex_returnsZeroAfterStartGameForTwoPlayers() {
        Game game = new Game();

        game.startGame(2, Locale.US);

        assertEquals(0, game.getCurrentPlayerIndex());
    }

    @Test
    void getCurrentPlayerIndex_returnsOneAfterPlayerZeroTakesTokens() {
        Game game = new Game();
        game.startGame(2, Locale.US);

        ActionResult result = game.takeTokens(
                Map.of(TokenColor.DIAMOND, 1, TokenColor.RUBY, 1, TokenColor.ONYX, 1),
                Locale.US);

        assertTrue(result.isSuccess());
        assertEquals(1, game.getCurrentPlayerIndex());
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
    void getTokenBank_returnsSnapshotThatDoesNotMutateGameTokenBank() {
        Game game = new Game();
        game.startGame(2, Locale.US);

        TokenBank tokenBank = game.getTokenBank();
        tokenBank.removeTokens(Map.of(TokenColor.DIAMOND, 1));

        assertEquals(4, game.getTokenBank().getTokenCount(TokenColor.DIAMOND));
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
    void startGame_shuffleProducesDifferentLevelOneDrawOrderAcrossGames() {
        Game firstGame = new Game();
        firstGame.startGame(2, Locale.US);
        String firstOrder = levelOneDrawOrderFingerprint(firstGame);

        Game secondGame = new Game();
        secondGame.startGame(2, Locale.US);
        String secondOrder = levelOneDrawOrderFingerprint(secondGame);

        assertNotEquals(firstOrder, secondOrder);
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
    void takeTokens_lazyInitializesRuleValidatorWhenNull() throws Exception {
        Game game = new Game();
        game.startGame(2, Locale.US);
        Player playerZero = game.getCurrentPlayer();

        Field ruleValidatorField = Game.class.getDeclaredField("ruleValidator");
        ruleValidatorField.setAccessible(true);
        ruleValidatorField.set(game, null);

        ActionResult result = game.takeTokens(
                Map.of(TokenColor.DIAMOND, 1, TokenColor.RUBY, 1, TokenColor.ONYX, 1),
                Locale.US);

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

    @Test
    void reserveFaceUpCard_lazyInitializesRuleValidatorWhenNull() throws Exception {
        Game game = new Game();
        game.startGame(2, Locale.US);
        Player playerZero = game.getCurrentPlayer();
        Card originalCard = game.getFaceUpCards(1).get(0);
        int deckSizeBefore = game.getDeck(1).cards.size();

        Field ruleValidatorField = Game.class.getDeclaredField("ruleValidator");
        ruleValidatorField.setAccessible(true);
        ruleValidatorField.set(game, null);

        ActionResult result = game.reserveFaceUpCard(1, 0, Locale.US);

        assertTrue(result.isSuccess());
        assertEquals(1, playerZero.getReservedCards().size());
        assertEquals(originalCard, playerZero.getReservedCards().get(0));
        assertEquals(4, game.getFaceUpCards(1).size());
        assertEquals(deckSizeBefore - 1, game.getDeck(1).cards.size());
        assertEquals(1, playerZero.getTokenCount(TokenColor.GOLD));
        assertEquals(4, game.getTokenBank().getTokenCount(TokenColor.GOLD));
        assertEquals(game.getPlayers().get(1), game.getCurrentPlayer());
    }

    @Test
    void reserveFaceUpCard_validCardReservesCardTakesGoldRefillsMarketAndAdvancesCurrentPlayer() {
        Game game = new Game();
        game.startGame(2, Locale.US);
        Player playerZero = game.getCurrentPlayer();
        Card originalCard = game.getFaceUpCards(1).get(0);
        int deckSizeBefore = game.getDeck(1).cards.size();

        ActionResult result = game.reserveFaceUpCard(1, 0, Locale.US);

        assertTrue(result.isSuccess());
        assertEquals(1, playerZero.getReservedCards().size());
        assertEquals(originalCard, playerZero.getReservedCards().get(0));
        assertEquals(4, game.getFaceUpCards(1).size());
        assertEquals(deckSizeBefore - 1, game.getDeck(1).cards.size());
        assertEquals(1, playerZero.getTokenCount(TokenColor.GOLD));
        assertEquals(4, game.getTokenBank().getTokenCount(TokenColor.GOLD));
        assertEquals(game.getPlayers().get(1), game.getCurrentPlayer());
    }

    @Test
    void reserveFaceUpCard_whenBankHasNoGoldStillReservesCardWithoutGoldAndAdvancesCurrentPlayer() {
        Game game = new Game();
        game.startGame(2, Locale.US);
        Player playerOne = game.getPlayers().get(1);
        game.reserveFaceUpCard(1, 0, Locale.US);
        game.reserveFaceUpCard(1, 0, Locale.US);
        game.reserveFaceUpCard(1, 0, Locale.US);
        game.reserveFaceUpCard(1, 0, Locale.US);
        game.reserveFaceUpCard(1, 0, Locale.US);

        ActionResult result = game.reserveFaceUpCard(1, 0, Locale.US);

        assertTrue(result.isSuccess());
        assertEquals(3, playerOne.getReservedCards().size());
        assertEquals(2, playerOne.getTokenCount(TokenColor.GOLD));
        assertEquals(0, game.getTokenBank().getTokenCount(TokenColor.GOLD));
        assertEquals(game.getPlayers().get(0), game.getCurrentPlayer());
    }

    @Test
    void reserveFaceUpCard_rejectsPlayerWithThreeReservedCardsAndLeavesStateUnchanged() {
        Game game = new Game();
        game.startGame(2, Locale.US);
        Player playerZero = game.getCurrentPlayer();
        playerZero.addReservedCard(new Card());
        playerZero.addReservedCard(new Card());
        playerZero.addReservedCard(new Card());
        Card originalCard = game.getFaceUpCards(1).get(0);
        int deckSizeBefore = game.getDeck(1).cards.size();

        ActionResult result = game.reserveFaceUpCard(1, 0, Locale.US);

        assertFalse(result.isSuccess());
        assertEquals(MessageProvider.getMessage("error.invalid_reserve_card", Locale.US), result.getMessage());
        assertEquals(3, playerZero.getReservedCards().size());
        assertEquals(originalCard, game.getFaceUpCards(1).get(0));
        assertEquals(deckSizeBefore, game.getDeck(1).cards.size());
        assertEquals(5, game.getTokenBank().getTokenCount(TokenColor.GOLD));
        assertEquals(playerZero, game.getCurrentPlayer());
    }

    @Test
    void reserveFaceUpCard_rejectsInvalidLevelAndLeavesStateUnchanged() {
        Game game = new Game();
        game.startGame(2, Locale.US);
        Player playerZero = game.getCurrentPlayer();

        ActionResult result = game.reserveFaceUpCard(4, 0, Locale.US);

        assertFalse(result.isSuccess());
        assertEquals(MessageProvider.getMessage("error.invalid_reserve_card", Locale.US), result.getMessage());
        assertEquals(0, playerZero.getReservedCards().size());
        assertEquals(5, game.getTokenBank().getTokenCount(TokenColor.GOLD));
        assertEquals(playerZero, game.getCurrentPlayer());
    }

    @Test
    void reserveFaceUpCard_rejectsInvalidIndexAndLeavesStateUnchanged() {
        Game game = new Game();
        game.startGame(2, Locale.US);
        Player playerZero = game.getCurrentPlayer();
        Card originalCard = game.getFaceUpCards(1).get(0);
        int deckSizeBefore = game.getDeck(1).cards.size();

        ActionResult result = game.reserveFaceUpCard(1, 4, Locale.US);

        assertFalse(result.isSuccess());
        assertEquals(MessageProvider.getMessage("error.invalid_reserve_card", Locale.US), result.getMessage());
        assertEquals(0, playerZero.getReservedCards().size());
        assertEquals(originalCard, game.getFaceUpCards(1).get(0));
        assertEquals(deckSizeBefore, game.getDeck(1).cards.size());
        assertEquals(5, game.getTokenBank().getTokenCount(TokenColor.GOLD));
        assertEquals(playerZero, game.getCurrentPlayer());
    }

    @Test
    void reserveFaceUpCard_beforeStartGameReturnsFailureAndKeepsSetupPhase() {
        Game game = new Game();

        ActionResult result = game.reserveFaceUpCard(1, 0, Locale.US);

        assertFalse(result.isSuccess());
        assertEquals(GamePhase.SETUP, game.getPhase());
    }

    @Test
    void buyFaceUpCard_lazyInitializesRuleValidatorWhenNull() throws Exception {
        Game game = new Game();
        game.startGame(2, Locale.US);
        Player playerZero = game.getCurrentPlayer();
        Card card = new Card(1, TokenColor.DIAMOND, Map.of(), 0);
        game.getFaceUpCards(1).set(0, card);
        int deckSizeBefore = game.getDeck(1).cards.size();

        Field ruleValidatorField = Game.class.getDeclaredField("ruleValidator");
        ruleValidatorField.setAccessible(true);
        ruleValidatorField.set(game, null);

        ActionResult result = game.buyFaceUpCard(1, 0, Locale.US);

        assertTrue(result.isSuccess());
        assertEquals(1, playerZero.getDevelopmentCards().size());
        assertEquals(card, playerZero.getDevelopmentCards().get(0));
        assertEquals(4, game.getFaceUpCards(1).size());
        assertEquals(deckSizeBefore - 1, game.getDeck(1).cards.size());
        assertEquals(game.getPlayers().get(1), game.getCurrentPlayer());
    }

    @Test
    void buyFaceUpCard_validCardPaidWithGemTokensBuysCardRefillsMarketAndAdvancesCurrentPlayer() {
        Game game = new Game();
        game.startGame(2, Locale.US);
        Player playerZero = game.getCurrentPlayer();
        Card card = new Card(1, TokenColor.DIAMOND, Map.of(TokenColor.RUBY, 1), 1);
        game.getFaceUpCards(1).set(0, card);
        game.takeTokens(Map.of(TokenColor.RUBY, 1, TokenColor.DIAMOND, 1, TokenColor.ONYX, 1), Locale.US);
        game.takeTokens(Map.of(TokenColor.SAPPHIRE, 1, TokenColor.EMERALD, 1, TokenColor.ONYX, 1), Locale.US);
        int deckSizeBefore = game.getDeck(1).cards.size();

        ActionResult result = game.buyFaceUpCard(1, 0, Locale.US);

        assertTrue(result.isSuccess());
        assertEquals(1, playerZero.getDevelopmentCards().size());
        assertEquals(card, playerZero.getDevelopmentCards().get(0));
        assertEquals(0, playerZero.getTokenCount(TokenColor.RUBY));
        assertEquals(4, game.getTokenBank().getTokenCount(TokenColor.RUBY));
        assertEquals(1, playerZero.getPrestigePoints());
        assertEquals(4, game.getFaceUpCards(1).size());
        assertEquals(deckSizeBefore - 1, game.getDeck(1).cards.size());
        assertEquals(game.getPlayers().get(1), game.getCurrentPlayer());
    }

    @Test
    void buyFaceUpCard_usesExactGemTokensWithoutGold() {
        Game game = new Game();
        game.startGame(2, Locale.US);
        Player playerZero = game.getPlayers().get(0);
        Card card = new Card(1, TokenColor.DIAMOND, Map.of(TokenColor.RUBY, 2), 0);
        game.getFaceUpCards(1).set(0, card);
        assertTrue(game.takeTokens(Map.of(TokenColor.RUBY, 2), Locale.US).isSuccess());
        assertTrue(game.takeTokens(Map.of(TokenColor.SAPPHIRE, 1, TokenColor.EMERALD, 1, TokenColor.ONYX, 1), Locale.US).isSuccess());
        assertEquals(playerZero, game.getCurrentPlayer());
        int goldBefore = playerZero.getTokenCount(TokenColor.GOLD);
        int goldBankBefore = game.getTokenBank().getTokenCount(TokenColor.GOLD);

        ActionResult result = game.buyFaceUpCard(1, 0, Locale.US);

        assertTrue(result.isSuccess());
        assertEquals(0, playerZero.getTokenCount(TokenColor.RUBY));
        assertEquals(goldBefore, playerZero.getTokenCount(TokenColor.GOLD));
        assertEquals(goldBankBefore, game.getTokenBank().getTokenCount(TokenColor.GOLD));
        assertEquals(4, game.getTokenBank().getTokenCount(TokenColor.RUBY));
        assertEquals(game.getPlayers().get(1), game.getCurrentPlayer());
    }

    @Test
    void buyFaceUpCard_paysFullShortfallWithGoldWhenNoGemTokens() {
        Game game = new Game();
        game.startGame(2, Locale.US);
        Player playerZero = game.getPlayers().get(0);
        Card card = new Card(1, TokenColor.DIAMOND, Map.of(TokenColor.RUBY, 1), 0);
        game.getFaceUpCards(1).set(0, card);
        game.reserveFaceUpCard(1, 1, Locale.US);
        game.takeTokens(Map.of(TokenColor.SAPPHIRE, 1, TokenColor.EMERALD, 1, TokenColor.ONYX, 1), Locale.US);
        assertEquals(playerZero, game.getCurrentPlayer());
        assertEquals(0, playerZero.getTokenCount(TokenColor.RUBY));
        assertEquals(1, playerZero.getTokenCount(TokenColor.GOLD));
        int goldBankBefore = game.getTokenBank().getTokenCount(TokenColor.GOLD);
        int rubyBankBefore = game.getTokenBank().getTokenCount(TokenColor.RUBY);

        ActionResult result = game.buyFaceUpCard(1, 0, Locale.US);

        assertTrue(result.isSuccess());
        assertEquals(1, playerZero.getDevelopmentCards().size());
        assertEquals(card, playerZero.getDevelopmentCards().get(0));
        assertEquals(0, playerZero.getTokenCount(TokenColor.RUBY));
        assertEquals(0, playerZero.getTokenCount(TokenColor.GOLD));
        assertEquals(rubyBankBefore, game.getTokenBank().getTokenCount(TokenColor.RUBY));
        assertEquals(goldBankBefore + 1, game.getTokenBank().getTokenCount(TokenColor.GOLD));
        assertEquals(game.getPlayers().get(1), game.getCurrentPlayer());
    }

    @Test
    void buyFaceUpCard_bonusFullyCoversSingleColorCostWithoutSpendingTokens() {
        Game game = new Game();
        game.startGame(2, Locale.US);
        Player playerZero = game.getCurrentPlayer();
        playerZero.addDevelopmentCard(new Card(1, TokenColor.RUBY, Map.of(), 0));
        Card card = new Card(1, TokenColor.DIAMOND, Map.of(TokenColor.RUBY, 1), 1);
        game.getFaceUpCards(1).set(0, card);
        int rubyBankBefore = game.getTokenBank().getTokenCount(TokenColor.RUBY);
        int goldBankBefore = game.getTokenBank().getTokenCount(TokenColor.GOLD);

        ActionResult result = game.buyFaceUpCard(1, 0, Locale.US);

        assertTrue(result.isSuccess());
        assertEquals(2, playerZero.getDevelopmentCards().size());
        assertEquals(card, playerZero.getDevelopmentCards().get(1));
        assertEquals(0, playerZero.getTokenCount(TokenColor.RUBY));
        assertEquals(0, playerZero.getTokenCount(TokenColor.GOLD));
        assertEquals(rubyBankBefore, game.getTokenBank().getTokenCount(TokenColor.RUBY));
        assertEquals(goldBankBefore, game.getTokenBank().getTokenCount(TokenColor.GOLD));
        assertEquals(game.getPlayers().get(1), game.getCurrentPlayer());
    }

    @Test
    void buyFaceUpCard_validCardUsesBonusToReduceCost() {
        Game game = new Game();
        game.startGame(2, Locale.US);
        Player playerZero = game.getCurrentPlayer();
        playerZero.addDevelopmentCard(new Card(1, TokenColor.RUBY, Map.of(), 0));
        Card card = new Card(1, TokenColor.DIAMOND, Map.of(TokenColor.RUBY, 2), 0);
        game.getFaceUpCards(1).set(0, card);
        game.takeTokens(Map.of(TokenColor.RUBY, 1, TokenColor.DIAMOND, 1, TokenColor.ONYX, 1), Locale.US);
        game.takeTokens(Map.of(TokenColor.SAPPHIRE, 1, TokenColor.EMERALD, 1, TokenColor.ONYX, 1), Locale.US);

        ActionResult result = game.buyFaceUpCard(1, 0, Locale.US);

        assertTrue(result.isSuccess());
        assertEquals(2, playerZero.getDevelopmentCards().size());
        assertEquals(card, playerZero.getDevelopmentCards().get(1));
        assertEquals(0, playerZero.getTokenCount(TokenColor.RUBY));
        assertEquals(4, game.getTokenBank().getTokenCount(TokenColor.RUBY));
        assertEquals(game.getPlayers().get(1), game.getCurrentPlayer());
    }

    @Test
    void buyFaceUpCard_validCardUsesGoldForMissingGemToken() {
        Game game = new Game();
        game.startGame(2, Locale.US);
        Player playerZero = game.getCurrentPlayer();
        Card card = new Card(1, TokenColor.DIAMOND, Map.of(TokenColor.RUBY, 1, TokenColor.SAPPHIRE, 1), 0);
        game.getFaceUpCards(1).set(0, card);
        game.reserveFaceUpCard(1, 1, Locale.US);
        game.takeTokens(Map.of(TokenColor.DIAMOND, 1, TokenColor.EMERALD, 1, TokenColor.ONYX, 1), Locale.US);
        game.takeTokens(Map.of(TokenColor.RUBY, 1, TokenColor.DIAMOND, 1, TokenColor.ONYX, 1), Locale.US);
        game.takeTokens(Map.of(TokenColor.SAPPHIRE, 1, TokenColor.EMERALD, 1, TokenColor.ONYX, 1), Locale.US);

        ActionResult result = game.buyFaceUpCard(1, 0, Locale.US);

        assertTrue(result.isSuccess());
        assertEquals(1, playerZero.getDevelopmentCards().size());
        assertEquals(card, playerZero.getDevelopmentCards().get(0));
        assertEquals(0, playerZero.getTokenCount(TokenColor.RUBY));
        assertEquals(0, playerZero.getTokenCount(TokenColor.GOLD));
        assertEquals(4, game.getTokenBank().getTokenCount(TokenColor.RUBY));
        assertEquals(5, game.getTokenBank().getTokenCount(TokenColor.GOLD));
        assertEquals(game.getPlayers().get(1), game.getCurrentPlayer());
    }

    @Test
    void buyFaceUpCard_whenDeckIsEmptyDoesNotRefillMarket() {
        Game game = new Game();
        game.startGame(2, Locale.US);
        Player playerZero = game.getCurrentPlayer();
        Card card = new Card(1, TokenColor.DIAMOND, Map.of(), 0);
        game.getFaceUpCards(1).set(0, card);
        game.getDeck(1).cards.clear();

        ActionResult result = game.buyFaceUpCard(1, 0, Locale.US);

        assertTrue(result.isSuccess());
        assertEquals(1, playerZero.getDevelopmentCards().size());
        assertEquals(card, playerZero.getDevelopmentCards().get(0));
        assertEquals(3, game.getFaceUpCards(1).size());
        assertEquals(0, game.getDeck(1).cards.size());
        assertEquals(game.getPlayers().get(1), game.getCurrentPlayer());
    }

    @Test
    void buyFaceUpCard_rejectsUnaffordableCardAndLeavesStateUnchanged() {
        Game game = new Game();
        game.startGame(2, Locale.US);
        Player playerZero = game.getCurrentPlayer();
        Card card = new Card(1, TokenColor.DIAMOND, Map.of(TokenColor.RUBY, 1), 1);
        game.getFaceUpCards(1).set(0, card);
        int deckSizeBefore = game.getDeck(1).cards.size();

        ActionResult result = game.buyFaceUpCard(1, 0, Locale.US);

        assertFalse(result.isSuccess());
        assertEquals(MessageProvider.getMessage("error.invalid_buy_card", Locale.US), result.getMessage());
        assertEquals(0, playerZero.getDevelopmentCards().size());
        assertEquals(0, playerZero.getPrestigePoints());
        assertEquals(card, game.getFaceUpCards(1).get(0));
        assertEquals(deckSizeBefore, game.getDeck(1).cards.size());
        assertEquals(4, game.getTokenBank().getTokenCount(TokenColor.RUBY));
        assertEquals(playerZero, game.getCurrentPlayer());
    }

    @Test
    void buyFaceUpCard_rejectsInvalidLevelAndLeavesStateUnchanged() {
        Game game = new Game();
        game.startGame(2, Locale.US);
        Player playerZero = game.getCurrentPlayer();
        game.takeTokens(Map.of(TokenColor.RUBY, 1, TokenColor.DIAMOND, 1, TokenColor.ONYX, 1), Locale.US);
        game.takeTokens(Map.of(TokenColor.SAPPHIRE, 1, TokenColor.EMERALD, 1, TokenColor.ONYX, 1), Locale.US);

        ActionResult result = game.buyFaceUpCard(4, 0, Locale.US);

        assertFalse(result.isSuccess());
        assertEquals(MessageProvider.getMessage("error.invalid_buy_card", Locale.US), result.getMessage());
        assertEquals(0, playerZero.getDevelopmentCards().size());
        assertEquals(1, playerZero.getTokenCount(TokenColor.RUBY));
        assertEquals(3, game.getTokenBank().getTokenCount(TokenColor.RUBY));
        assertEquals(playerZero, game.getCurrentPlayer());
    }

    @Test
    void buyFaceUpCard_rejectsInvalidIndexAndLeavesStateUnchanged() {
        Game game = new Game();
        game.startGame(2, Locale.US);
        Player playerZero = game.getCurrentPlayer();
        Card originalCard = game.getFaceUpCards(1).get(0);
        int deckSizeBefore = game.getDeck(1).cards.size();

        ActionResult result = game.buyFaceUpCard(1, 4, Locale.US);

        assertFalse(result.isSuccess());
        assertEquals(MessageProvider.getMessage("error.invalid_buy_card", Locale.US), result.getMessage());
        assertEquals(0, playerZero.getDevelopmentCards().size());
        assertEquals(originalCard, game.getFaceUpCards(1).get(0));
        assertEquals(deckSizeBefore, game.getDeck(1).cards.size());
        assertEquals(playerZero, game.getCurrentPlayer());
    }

    @Test
    void buyFaceUpCard_beforeStartGameReturnsFailureAndKeepsSetupPhase() {
        Game game = new Game();

        ActionResult result = game.buyFaceUpCard(1, 0, Locale.US);

        assertFalse(result.isSuccess());
        assertEquals(GamePhase.SETUP, game.getPhase());
    }

    @Test
    void buyFaceUpCard_whenBoughtCardSatisfiesOneNobleAddsNobleToPlayerAndRemovesItFromRevealedNobles() {
        Game game = new Game();
        game.startGame(2, Locale.US);
        Player playerZero = game.getCurrentPlayer();
        Noble noble = game.getRevealedNobles().get(0);
        playerZero.addDevelopmentCard(new Card(1, TokenColor.SAPPHIRE, Map.of(), 0));
        playerZero.addDevelopmentCard(new Card(1, TokenColor.SAPPHIRE, Map.of(), 0));
        playerZero.addDevelopmentCard(new Card(1, TokenColor.SAPPHIRE, Map.of(), 0));
        playerZero.addDevelopmentCard(new Card(1, TokenColor.EMERALD, Map.of(), 0));
        playerZero.addDevelopmentCard(new Card(1, TokenColor.EMERALD, Map.of(), 0));
        playerZero.addDevelopmentCard(new Card(1, TokenColor.EMERALD, Map.of(), 0));
        playerZero.addDevelopmentCard(new Card(1, TokenColor.RUBY, Map.of(), 0));
        playerZero.addDevelopmentCard(new Card(1, TokenColor.RUBY, Map.of(), 0));
        Card card = new Card(1, TokenColor.RUBY, Map.of(), 1);
        game.getFaceUpCards(1).set(0, card);

        ActionResult result = game.buyFaceUpCard(1, 0, Locale.US);

        assertTrue(result.isSuccess());
        assertEquals(1, playerZero.getNobles().size());
        assertEquals(noble, playerZero.getNobles().get(0));
        assertFalse(game.getRevealedNobles().contains(noble));
        assertEquals(2, game.getRevealedNobles().size());
        assertEquals(4, playerZero.getPrestigePoints());
        assertEquals(game.getPlayers().get(1), game.getCurrentPlayer());
    }

    @Test
    void buyFaceUpCard_whenNoNobleIsSatisfiedLeavesRevealedNoblesUnchanged() {
        Game game = new Game();
        game.startGame(2, Locale.US);
        Player playerZero = game.getCurrentPlayer();
        Noble firstNoble = game.getRevealedNobles().get(0);
        Noble secondNoble = game.getRevealedNobles().get(1);
        Noble thirdNoble = game.getRevealedNobles().get(2);
        Card card = new Card(1, TokenColor.DIAMOND, Map.of(), 0);
        game.getFaceUpCards(1).set(0, card);

        ActionResult result = game.buyFaceUpCard(1, 0, Locale.US);

        assertTrue(result.isSuccess());
        assertEquals(0, playerZero.getNobles().size());
        assertEquals(3, game.getRevealedNobles().size());
        assertEquals(firstNoble, game.getRevealedNobles().get(0));
        assertEquals(secondNoble, game.getRevealedNobles().get(1));
        assertEquals(thirdNoble, game.getRevealedNobles().get(2));
        assertEquals(game.getPlayers().get(1), game.getCurrentPlayer());
    }

    @Test
    void buyFaceUpCard_whenMultipleNoblesAreSatisfiedAddsOnlyFirstSatisfiedNoble() {
        Game game = new Game();
        game.startGame(2, Locale.US);
        Player playerZero = game.getCurrentPlayer();
        Noble firstNoble = game.getRevealedNobles().get(0);
        Noble secondNoble = game.getRevealedNobles().get(1);
        playerZero.addDevelopmentCard(new Card(1, TokenColor.SAPPHIRE, Map.of(), 0));
        playerZero.addDevelopmentCard(new Card(1, TokenColor.SAPPHIRE, Map.of(), 0));
        playerZero.addDevelopmentCard(new Card(1, TokenColor.SAPPHIRE, Map.of(), 0));
        playerZero.addDevelopmentCard(new Card(1, TokenColor.EMERALD, Map.of(), 0));
        playerZero.addDevelopmentCard(new Card(1, TokenColor.EMERALD, Map.of(), 0));
        playerZero.addDevelopmentCard(new Card(1, TokenColor.EMERALD, Map.of(), 0));
        playerZero.addDevelopmentCard(new Card(1, TokenColor.RUBY, Map.of(), 0));
        playerZero.addDevelopmentCard(new Card(1, TokenColor.RUBY, Map.of(), 0));
        playerZero.addDevelopmentCard(new Card(1, TokenColor.DIAMOND, Map.of(), 0));
        playerZero.addDevelopmentCard(new Card(1, TokenColor.DIAMOND, Map.of(), 0));
        playerZero.addDevelopmentCard(new Card(1, TokenColor.DIAMOND, Map.of(), 0));
        Card card = new Card(1, TokenColor.RUBY, Map.of(), 0);
        game.getFaceUpCards(1).set(0, card);

        ActionResult result = game.buyFaceUpCard(1, 0, Locale.US);

        assertTrue(result.isSuccess());
        assertEquals(1, playerZero.getNobles().size());
        assertEquals(firstNoble, playerZero.getNobles().get(0));
        assertFalse(game.getRevealedNobles().contains(firstNoble));
        assertTrue(game.getRevealedNobles().contains(secondNoble));
        assertEquals(2, game.getRevealedNobles().size());
        assertEquals(game.getPlayers().get(1), game.getCurrentPlayer());
    }

    @Test
    void buyFaceUpCard_whenPurchaseFailsDoesNotVisitSatisfiedNoble() {
        Game game = new Game();
        game.startGame(2, Locale.US);
        Player playerZero = game.getCurrentPlayer();
        Noble noble = game.getRevealedNobles().get(0);
        playerZero.addDevelopmentCard(new Card(1, TokenColor.SAPPHIRE, Map.of(), 0));
        playerZero.addDevelopmentCard(new Card(1, TokenColor.SAPPHIRE, Map.of(), 0));
        playerZero.addDevelopmentCard(new Card(1, TokenColor.SAPPHIRE, Map.of(), 0));
        playerZero.addDevelopmentCard(new Card(1, TokenColor.EMERALD, Map.of(), 0));
        playerZero.addDevelopmentCard(new Card(1, TokenColor.EMERALD, Map.of(), 0));
        playerZero.addDevelopmentCard(new Card(1, TokenColor.EMERALD, Map.of(), 0));
        playerZero.addDevelopmentCard(new Card(1, TokenColor.RUBY, Map.of(), 0));
        playerZero.addDevelopmentCard(new Card(1, TokenColor.RUBY, Map.of(), 0));
        playerZero.addDevelopmentCard(new Card(1, TokenColor.RUBY, Map.of(), 0));
        Card card = new Card(1, TokenColor.DIAMOND, Map.of(TokenColor.RUBY, 4), 0);
        game.getFaceUpCards(1).set(0, card);

        ActionResult result = game.buyFaceUpCard(1, 0, Locale.US);

        assertFalse(result.isSuccess());
        assertEquals(0, playerZero.getNobles().size());
        assertEquals(3, game.getRevealedNobles().size());
        assertTrue(game.getRevealedNobles().contains(noble));
        assertEquals(playerZero, game.getCurrentPlayer());
    }

    @Test
    void buyFaceUpCard_whenPlayerReachesFifteenPrestigePointsStartsFinalRoundAndAdvancesCurrentPlayer() {
        Game game = new Game();
        game.startGame(2, Locale.US);
        Player playerZero = game.getCurrentPlayer();
        playerZero.addDevelopmentCard(new Card(1, TokenColor.DIAMOND, Map.of(), 14));
        Card card = new Card(1, TokenColor.RUBY, Map.of(), 1);
        game.getFaceUpCards(1).set(0, card);
        int deckSizeBefore = game.getDeck(1).cards.size();

        ActionResult result = game.buyFaceUpCard(1, 0, Locale.US);

        assertTrue(result.isSuccess());
        assertEquals(15, playerZero.getPrestigePoints());
        assertEquals(GamePhase.FINAL_ROUND, game.getPhase());
        assertNull(game.getWinner());
        assertTrue(game.getWinners().isEmpty());
        assertEquals(4, game.getFaceUpCards(1).size());
        assertEquals(deckSizeBefore - 1, game.getDeck(1).cards.size());
        assertEquals(game.getPlayers().get(1), game.getCurrentPlayer());
    }

    @Test
    void buyFaceUpCard_whenPlayerStaysBelowFifteenPrestigePointsKeepsPlayerTurnAndNoWinner() {
        Game game = new Game();
        game.startGame(2, Locale.US);
        Player playerZero = game.getCurrentPlayer();
        playerZero.addDevelopmentCard(new Card(1, TokenColor.DIAMOND, Map.of(), 13));
        Card card = new Card(1, TokenColor.RUBY, Map.of(), 1);
        game.getFaceUpCards(1).set(0, card);

        ActionResult result = game.buyFaceUpCard(1, 0, Locale.US);

        assertTrue(result.isSuccess());
        assertEquals(14, playerZero.getPrestigePoints());
        assertEquals(GamePhase.PLAYER_TURN, game.getPhase());
        assertNull(game.getWinner());
        assertEquals(game.getPlayers().get(1), game.getCurrentPlayer());
    }

    @Test
    void buyFaceUpCard_afterGameOverReturnsFailureAndLeavesWinnerUnchanged() {
        Game game = new Game();
        game.startGame(2, Locale.US);
        Player playerZero = game.getCurrentPlayer();
        playerZero.addDevelopmentCard(new Card(1, TokenColor.DIAMOND, Map.of(), 14));
        Card winningCard = new Card(1, TokenColor.RUBY, Map.of(), 1);
        game.getFaceUpCards(1).set(0, winningCard);
        game.buyFaceUpCard(1, 0, Locale.US);
        game.takeTokens(Map.of(TokenColor.SAPPHIRE, 1, TokenColor.EMERALD, 1, TokenColor.ONYX, 1), Locale.US);
        Card laterCard = new Card(1, TokenColor.SAPPHIRE, Map.of(), 1);
        game.getFaceUpCards(1).set(0, laterCard);

        ActionResult result = game.buyFaceUpCard(1, 0, Locale.US);

        assertFalse(result.isSuccess());
        assertEquals(MessageProvider.getMessage("error.invalid_buy_card", Locale.US), result.getMessage());
        assertEquals(GamePhase.GAME_OVER, game.getPhase());
        assertEquals(playerZero, game.getWinner());
        assertEquals(1, game.getWinners().size());
        assertEquals(playerZero, game.getWinners().get(0));
        assertEquals(playerZero, game.getCurrentPlayer());
    }

    @Test
    void buyReservedCard_lazyInitializesRuleValidatorWhenNull() throws Exception {
        Game game = new Game();
        game.startGame(2, Locale.US);
        Player playerZero = game.getCurrentPlayer();
        Card card = new Card(1, TokenColor.DIAMOND, Map.of(), 0);
        playerZero.addReservedCard(card);

        Field ruleValidatorField = Game.class.getDeclaredField("ruleValidator");
        ruleValidatorField.setAccessible(true);
        ruleValidatorField.set(game, null);

        ActionResult result = game.buyReservedCard(0, Locale.US);

        assertTrue(result.isSuccess());
        assertEquals(0, playerZero.getReservedCards().size());
        assertEquals(1, playerZero.getDevelopmentCards().size());
        assertEquals(card, playerZero.getDevelopmentCards().get(0));
        assertEquals(game.getPlayers().get(1), game.getCurrentPlayer());
    }

    @Test
    void buyReservedCard_validCardPaidWithGemTokensBuysReservedCardAndAdvancesCurrentPlayer() {
        Game game = new Game();
        game.startGame(2, Locale.US);
        Player playerZero = game.getCurrentPlayer();
        Card card = new Card(1, TokenColor.DIAMOND, Map.of(TokenColor.RUBY, 1), 1);
        game.getFaceUpCards(1).set(0, card);
        game.reserveFaceUpCard(1, 0, Locale.US);
        game.takeTokens(Map.of(TokenColor.SAPPHIRE, 1, TokenColor.EMERALD, 1, TokenColor.ONYX, 1), Locale.US);
        game.takeTokens(Map.of(TokenColor.RUBY, 1, TokenColor.DIAMOND, 1, TokenColor.ONYX, 1), Locale.US);
        game.takeTokens(Map.of(TokenColor.SAPPHIRE, 1, TokenColor.EMERALD, 1, TokenColor.DIAMOND, 1), Locale.US);

        ActionResult result = game.buyReservedCard(0, Locale.US);

        assertTrue(result.isSuccess());
        assertEquals(0, playerZero.getReservedCards().size());
        assertEquals(1, playerZero.getDevelopmentCards().size());
        assertEquals(card, playerZero.getDevelopmentCards().get(0));
        assertEquals(0, playerZero.getTokenCount(TokenColor.RUBY));
        assertEquals(1, playerZero.getTokenCount(TokenColor.GOLD));
        assertEquals(1, playerZero.getPrestigePoints());
        assertEquals(4, game.getTokenBank().getTokenCount(TokenColor.RUBY));
        assertEquals(4, game.getTokenBank().getTokenCount(TokenColor.GOLD));
        assertEquals(game.getPlayers().get(1), game.getCurrentPlayer());
    }

    @Test
    void buyReservedCard_validCardUsesBonusToReduceCost() {
        Game game = new Game();
        game.startGame(2, Locale.US);
        Player playerZero = game.getCurrentPlayer();
        playerZero.addDevelopmentCard(new Card(1, TokenColor.RUBY, Map.of(), 0));
        Card card = new Card(1, TokenColor.DIAMOND, Map.of(TokenColor.RUBY, 2), 0);
        game.getFaceUpCards(1).set(0, card);
        game.reserveFaceUpCard(1, 0, Locale.US);
        game.takeTokens(Map.of(TokenColor.SAPPHIRE, 1, TokenColor.EMERALD, 1, TokenColor.ONYX, 1), Locale.US);
        game.takeTokens(Map.of(TokenColor.RUBY, 1, TokenColor.DIAMOND, 1, TokenColor.ONYX, 1), Locale.US);
        game.takeTokens(Map.of(TokenColor.SAPPHIRE, 1, TokenColor.EMERALD, 1, TokenColor.DIAMOND, 1), Locale.US);

        ActionResult result = game.buyReservedCard(0, Locale.US);

        assertTrue(result.isSuccess());
        assertEquals(0, playerZero.getReservedCards().size());
        assertEquals(2, playerZero.getDevelopmentCards().size());
        assertEquals(card, playerZero.getDevelopmentCards().get(1));
        assertEquals(0, playerZero.getTokenCount(TokenColor.RUBY));
        assertEquals(4, game.getTokenBank().getTokenCount(TokenColor.RUBY));
        assertEquals(game.getPlayers().get(1), game.getCurrentPlayer());
    }

    @Test
    void buyReservedCard_validCardUsesGoldForMissingGemToken() {
        Game game = new Game();
        game.startGame(2, Locale.US);
        Player playerZero = game.getCurrentPlayer();
        Card card = new Card(1, TokenColor.DIAMOND, Map.of(TokenColor.RUBY, 1, TokenColor.SAPPHIRE, 1), 0);
        game.getFaceUpCards(1).set(0, card);
        game.reserveFaceUpCard(1, 0, Locale.US);
        game.takeTokens(Map.of(TokenColor.SAPPHIRE, 1, TokenColor.EMERALD, 1, TokenColor.ONYX, 1), Locale.US);
        game.takeTokens(Map.of(TokenColor.RUBY, 1, TokenColor.DIAMOND, 1, TokenColor.ONYX, 1), Locale.US);
        game.takeTokens(Map.of(TokenColor.SAPPHIRE, 1, TokenColor.EMERALD, 1, TokenColor.DIAMOND, 1), Locale.US);

        ActionResult result = game.buyReservedCard(0, Locale.US);

        assertTrue(result.isSuccess());
        assertEquals(0, playerZero.getReservedCards().size());
        assertEquals(1, playerZero.getDevelopmentCards().size());
        assertEquals(card, playerZero.getDevelopmentCards().get(0));
        assertEquals(0, playerZero.getTokenCount(TokenColor.RUBY));
        assertEquals(0, playerZero.getTokenCount(TokenColor.GOLD));
        assertEquals(4, game.getTokenBank().getTokenCount(TokenColor.RUBY));
        assertEquals(5, game.getTokenBank().getTokenCount(TokenColor.GOLD));
        assertEquals(game.getPlayers().get(1), game.getCurrentPlayer());
    }

    @Test
    void buyReservedCard_rejectsUnaffordableReservedCardAndLeavesStateUnchanged() {
        Game game = new Game();
        game.startGame(2, Locale.US);
        Player playerZero = game.getCurrentPlayer();
        Card card = new Card(1, TokenColor.DIAMOND, Map.of(TokenColor.RUBY, 1), 1);
        playerZero.addReservedCard(card);

        ActionResult result = game.buyReservedCard(0, Locale.US);

        assertFalse(result.isSuccess());
        assertEquals(MessageProvider.getMessage("error.invalid_buy_card", Locale.US), result.getMessage());
        assertEquals(1, playerZero.getReservedCards().size());
        assertEquals(card, playerZero.getReservedCards().get(0));
        assertEquals(0, playerZero.getDevelopmentCards().size());
        assertEquals(0, playerZero.getTokenCount(TokenColor.RUBY));
        assertEquals(0, playerZero.getTokenCount(TokenColor.GOLD));
        assertEquals(4, game.getTokenBank().getTokenCount(TokenColor.RUBY));
        assertEquals(5, game.getTokenBank().getTokenCount(TokenColor.GOLD));
        assertEquals(playerZero, game.getCurrentPlayer());
    }

    @Test
    void buyReservedCard_rejectsEmptyReservedCardsAndLeavesStateUnchanged() {
        Game game = new Game();
        game.startGame(2, Locale.US);
        Player playerZero = game.getCurrentPlayer();

        ActionResult result = game.buyReservedCard(0, Locale.US);

        assertFalse(result.isSuccess());
        assertEquals(MessageProvider.getMessage("error.invalid_buy_card", Locale.US), result.getMessage());
        assertEquals(0, playerZero.getReservedCards().size());
        assertEquals(0, playerZero.getDevelopmentCards().size());
        assertEquals(5, game.getTokenBank().getTokenCount(TokenColor.GOLD));
        assertEquals(playerZero, game.getCurrentPlayer());
    }

    @Test
    void buyReservedCard_rejectsNegativeIndexAndLeavesStateUnchanged() {
        Game game = new Game();
        game.startGame(2, Locale.US);
        Player playerZero = game.getCurrentPlayer();
        Card card = new Card(1, TokenColor.DIAMOND, Map.of(), 0);
        playerZero.addReservedCard(card);

        ActionResult result = game.buyReservedCard(-1, Locale.US);

        assertFalse(result.isSuccess());
        assertEquals(MessageProvider.getMessage("error.invalid_buy_card", Locale.US), result.getMessage());
        assertEquals(1, playerZero.getReservedCards().size());
        assertEquals(card, playerZero.getReservedCards().get(0));
        assertEquals(0, playerZero.getDevelopmentCards().size());
        assertEquals(playerZero, game.getCurrentPlayer());
    }

    @Test
    void buyReservedCard_rejectsIndexEqualToReservedCardsSizeAndLeavesStateUnchanged() {
        Game game = new Game();
        game.startGame(2, Locale.US);
        Player playerZero = game.getCurrentPlayer();
        Card card = new Card(1, TokenColor.DIAMOND, Map.of(), 0);
        playerZero.addReservedCard(card);

        ActionResult result = game.buyReservedCard(1, Locale.US);

        assertFalse(result.isSuccess());
        assertEquals(MessageProvider.getMessage("error.invalid_buy_card", Locale.US), result.getMessage());
        assertEquals(1, playerZero.getReservedCards().size());
        assertEquals(card, playerZero.getReservedCards().get(0));
        assertEquals(0, playerZero.getDevelopmentCards().size());
        assertEquals(playerZero, game.getCurrentPlayer());
    }

    @Test
    void buyReservedCard_beforeStartGameReturnsFailureAndKeepsSetupPhase() {
        Game game = new Game();

        ActionResult result = game.buyReservedCard(0, Locale.US);

        assertFalse(result.isSuccess());
        assertEquals(GamePhase.SETUP, game.getPhase());
    }

    @Test
    void buyReservedCard_whenBoughtCardSatisfiesOneNobleAddsNobleToPlayerAndRemovesItFromRevealedNobles() {
        Game game = new Game();
        game.startGame(2, Locale.US);
        Player playerZero = game.getCurrentPlayer();
        Noble noble = game.getRevealedNobles().get(0);
        playerZero.addDevelopmentCard(new Card(1, TokenColor.SAPPHIRE, Map.of(), 0));
        playerZero.addDevelopmentCard(new Card(1, TokenColor.SAPPHIRE, Map.of(), 0));
        playerZero.addDevelopmentCard(new Card(1, TokenColor.SAPPHIRE, Map.of(), 0));
        playerZero.addDevelopmentCard(new Card(1, TokenColor.EMERALD, Map.of(), 0));
        playerZero.addDevelopmentCard(new Card(1, TokenColor.EMERALD, Map.of(), 0));
        playerZero.addDevelopmentCard(new Card(1, TokenColor.EMERALD, Map.of(), 0));
        playerZero.addDevelopmentCard(new Card(1, TokenColor.RUBY, Map.of(), 0));
        playerZero.addDevelopmentCard(new Card(1, TokenColor.RUBY, Map.of(), 0));
        Card card = new Card(1, TokenColor.RUBY, Map.of(), 1);
        game.getFaceUpCards(1).set(0, card);
        game.reserveFaceUpCard(1, 0, Locale.US);
        game.takeTokens(Map.of(TokenColor.SAPPHIRE, 1, TokenColor.EMERALD, 1, TokenColor.ONYX, 1), Locale.US);

        ActionResult result = game.buyReservedCard(0, Locale.US);

        assertTrue(result.isSuccess());
        assertEquals(0, playerZero.getReservedCards().size());
        assertEquals(1, playerZero.getNobles().size());
        assertEquals(noble, playerZero.getNobles().get(0));
        assertFalse(game.getRevealedNobles().contains(noble));
        assertEquals(2, game.getRevealedNobles().size());
        assertEquals(4, playerZero.getPrestigePoints());
        assertEquals(game.getPlayers().get(1), game.getCurrentPlayer());
    }

    @Test
    void buyReservedCard_whenNoNobleIsSatisfiedLeavesRevealedNoblesUnchanged() {
        Game game = new Game();
        game.startGame(2, Locale.US);
        Player playerZero = game.getCurrentPlayer();
        Noble firstNoble = game.getRevealedNobles().get(0);
        Noble secondNoble = game.getRevealedNobles().get(1);
        Noble thirdNoble = game.getRevealedNobles().get(2);
        Card card = new Card(1, TokenColor.DIAMOND, Map.of(), 0);
        game.getFaceUpCards(1).set(0, card);
        game.reserveFaceUpCard(1, 0, Locale.US);
        game.takeTokens(Map.of(TokenColor.SAPPHIRE, 1, TokenColor.EMERALD, 1, TokenColor.ONYX, 1), Locale.US);

        ActionResult result = game.buyReservedCard(0, Locale.US);

        assertTrue(result.isSuccess());
        assertEquals(0, playerZero.getReservedCards().size());
        assertEquals(0, playerZero.getNobles().size());
        assertEquals(3, game.getRevealedNobles().size());
        assertEquals(firstNoble, game.getRevealedNobles().get(0));
        assertEquals(secondNoble, game.getRevealedNobles().get(1));
        assertEquals(thirdNoble, game.getRevealedNobles().get(2));
        assertEquals(game.getPlayers().get(1), game.getCurrentPlayer());
    }

    @Test
    void buyReservedCard_whenPlayerReachesFifteenPrestigePointsStartsFinalRoundAndAdvancesCurrentPlayer() {
        Game game = new Game();
        game.startGame(2, Locale.US);
        Player playerZero = game.getCurrentPlayer();
        playerZero.addDevelopmentCard(new Card(1, TokenColor.DIAMOND, Map.of(), 14));
        Card card = new Card(1, TokenColor.RUBY, Map.of(), 1);
        playerZero.addReservedCard(card);

        ActionResult result = game.buyReservedCard(0, Locale.US);

        assertTrue(result.isSuccess());
        assertEquals(15, playerZero.getPrestigePoints());
        assertEquals(GamePhase.FINAL_ROUND, game.getPhase());
        assertNull(game.getWinner());
        assertTrue(game.getWinners().isEmpty());
        assertEquals(game.getPlayers().get(1), game.getCurrentPlayer());
    }

    @Test
    void buyReservedCard_afterGameOverReturnsFailureAndLeavesWinnerUnchanged() {
        Game game = new Game();
        game.startGame(2, Locale.US);
        Player playerZero = game.getCurrentPlayer();
        playerZero.addDevelopmentCard(new Card(1, TokenColor.DIAMOND, Map.of(), 14));
        Card winningCard = new Card(1, TokenColor.RUBY, Map.of(), 1);
        playerZero.addReservedCard(winningCard);
        game.buyReservedCard(0, Locale.US);
        game.takeTokens(Map.of(TokenColor.SAPPHIRE, 1, TokenColor.EMERALD, 1, TokenColor.ONYX, 1), Locale.US);
        Card laterCard = new Card(1, TokenColor.SAPPHIRE, Map.of(), 1);
        playerZero.addReservedCard(laterCard);

        ActionResult result = game.buyReservedCard(0, Locale.US);

        assertFalse(result.isSuccess());
        assertEquals(MessageProvider.getMessage("error.invalid_buy_card", Locale.US), result.getMessage());
        assertEquals(GamePhase.GAME_OVER, game.getPhase());
        assertEquals(playerZero, game.getWinner());
        assertEquals(1, game.getWinners().size());
        assertEquals(playerZero, game.getWinners().get(0));
        assertEquals(playerZero, game.getCurrentPlayer());
    }

    @Test
    void takeTokens_duringFinalRoundCompletesFinalRoundAndCalculatesWinner() {
        Game game = new Game();
        game.startGame(2, Locale.US);
        Player playerZero = game.getCurrentPlayer();
        playerZero.addDevelopmentCard(new Card(1, TokenColor.DIAMOND, Map.of(), 14));
        Card card = new Card(1, TokenColor.RUBY, Map.of(), 1);
        game.getFaceUpCards(1).set(0, card);
        game.buyFaceUpCard(1, 0, Locale.US);

        ActionResult result = game.takeTokens(Map.of(TokenColor.SAPPHIRE, 1, TokenColor.EMERALD, 1, TokenColor.ONYX, 1), Locale.US);

        assertTrue(result.isSuccess());
        assertEquals(GamePhase.GAME_OVER, game.getPhase());
        assertEquals(playerZero, game.getWinner());
        assertEquals(1, game.getWinners().size());
        assertEquals(playerZero, game.getWinners().get(0));
        assertEquals(playerZero, game.getCurrentPlayer());
    }

    @Test
    void takeTokens_duringFinalRoundTiedPrestigeAndDevelopmentCardsSharesWin() {
        Game game = new Game();
        game.startGame(2, Locale.US);
        Player playerZero = game.getCurrentPlayer();
        Player playerOne = game.getPlayers().get(1);
        playerZero.addDevelopmentCard(new Card(1, TokenColor.DIAMOND, Map.of(), 14));
        playerOne.addDevelopmentCard(new Card(1, TokenColor.EMERALD, Map.of(), 15));
        playerOne.addDevelopmentCard(new Card(1, TokenColor.SAPPHIRE, Map.of(), 0));
        Card triggerCard = new Card(1, TokenColor.RUBY, Map.of(), 1);
        game.getFaceUpCards(1).set(0, triggerCard);
        game.buyFaceUpCard(1, 0, Locale.US);

        ActionResult result = game.takeTokens(Map.of(TokenColor.SAPPHIRE, 1, TokenColor.EMERALD, 1, TokenColor.ONYX, 1), Locale.US);

        assertTrue(result.isSuccess());
        assertEquals(GamePhase.GAME_OVER, game.getPhase());
        assertEquals(15, playerZero.getPrestigePoints());
        assertEquals(15, playerOne.getPrestigePoints());
        assertEquals(2, playerZero.getDevelopmentCards().size());
        assertEquals(2, playerOne.getDevelopmentCards().size());
        assertEquals(2, game.getWinners().size());
        assertTrue(game.getWinners().contains(playerZero));
        assertTrue(game.getWinners().contains(playerOne));
        assertEquals(playerZero, game.getCurrentPlayer());
    }

    @Test
    void takeTokens_invalidActionDuringFinalRoundLeavesFinalRoundUnchanged() {
        Game game = new Game();
        game.startGame(2, Locale.US);
        Player playerZero = game.getCurrentPlayer();
        Player playerOne = game.getPlayers().get(1);
        playerZero.addDevelopmentCard(new Card(1, TokenColor.DIAMOND, Map.of(), 14));
        Card card = new Card(1, TokenColor.RUBY, Map.of(), 1);
        game.getFaceUpCards(1).set(0, card);
        game.buyFaceUpCard(1, 0, Locale.US);

        ActionResult result = game.takeTokens(Map.of(TokenColor.GOLD, 1), Locale.US);

        assertFalse(result.isSuccess());
        assertEquals(MessageProvider.getMessage("error.invalid_token_selection", Locale.US), result.getMessage());
        assertEquals(GamePhase.FINAL_ROUND, game.getPhase());
        assertNull(game.getWinner());
        assertTrue(game.getWinners().isEmpty());
        assertEquals(playerOne, game.getCurrentPlayer());
    }

    @Test
    void buyFaceUpCard_duringFinalRoundHigherPrestigePlayerWins() {
        Game game = new Game();
        game.startGame(2, Locale.US);
        Player playerZero = game.getCurrentPlayer();
        Player playerOne = game.getPlayers().get(1);
        playerZero.addDevelopmentCard(new Card(1, TokenColor.DIAMOND, Map.of(), 14));
        playerOne.addDevelopmentCard(new Card(1, TokenColor.SAPPHIRE, Map.of(), 15));
        Card triggerCard = new Card(1, TokenColor.RUBY, Map.of(), 1);
        game.getFaceUpCards(1).set(0, triggerCard);
        game.buyFaceUpCard(1, 0, Locale.US);
        Card finalRoundCard = new Card(1, TokenColor.EMERALD, Map.of(), 1);
        game.getFaceUpCards(1).set(0, finalRoundCard);

        ActionResult result = game.buyFaceUpCard(1, 0, Locale.US);

        assertTrue(result.isSuccess());
        assertEquals(GamePhase.GAME_OVER, game.getPhase());
        assertEquals(16, playerOne.getPrestigePoints());
        assertEquals(playerOne, game.getWinner());
        assertEquals(1, game.getWinners().size());
        assertEquals(playerOne, game.getWinners().get(0));
    }

    @Test
    void buyFaceUpCard_duringFinalRoundTiedPrestigePlayerWithFewerDevelopmentCardsWins() {
        Game game = new Game();
        game.startGame(2, Locale.US);
        Player playerZero = game.getCurrentPlayer();
        Player playerOne = game.getPlayers().get(1);
        playerZero.addDevelopmentCard(new Card(1, TokenColor.DIAMOND, Map.of(), 13));
        playerZero.addDevelopmentCard(new Card(1, TokenColor.SAPPHIRE, Map.of(), 1));
        playerOne.addDevelopmentCard(new Card(1, TokenColor.EMERALD, Map.of(), 14));
        Card triggerCard = new Card(1, TokenColor.RUBY, Map.of(), 1);
        game.getFaceUpCards(1).set(0, triggerCard);
        game.buyFaceUpCard(1, 0, Locale.US);
        Card finalRoundCard = new Card(1, TokenColor.ONYX, Map.of(), 1);
        game.getFaceUpCards(1).set(0, finalRoundCard);

        ActionResult result = game.buyFaceUpCard(1, 0, Locale.US);

        assertTrue(result.isSuccess());
        assertEquals(GamePhase.GAME_OVER, game.getPhase());
        assertEquals(15, playerZero.getPrestigePoints());
        assertEquals(15, playerOne.getPrestigePoints());
        assertEquals(3, playerZero.getDevelopmentCards().size());
        assertEquals(2, playerOne.getDevelopmentCards().size());
        assertEquals(playerOne, game.getWinner());
        assertEquals(1, game.getWinners().size());
        assertEquals(playerOne, game.getWinners().get(0));
    }

    @Test
    void buyFaceUpCard_duringFinalRoundTiedPrestigeAndDevelopmentCardsSharesWin() {
        Game game = new Game();
        game.startGame(2, Locale.US);
        Player playerZero = game.getCurrentPlayer();
        Player playerOne = game.getPlayers().get(1);
        playerZero.addDevelopmentCard(new Card(1, TokenColor.DIAMOND, Map.of(), 14));
        playerOne.addDevelopmentCard(new Card(1, TokenColor.EMERALD, Map.of(), 14));
        Card triggerCard = new Card(1, TokenColor.RUBY, Map.of(), 1);
        game.getFaceUpCards(1).set(0, triggerCard);
        game.buyFaceUpCard(1, 0, Locale.US);
        Card finalRoundCard = new Card(1, TokenColor.ONYX, Map.of(), 1);
        game.getFaceUpCards(1).set(0, finalRoundCard);

        ActionResult result = game.buyFaceUpCard(1, 0, Locale.US);

        assertTrue(result.isSuccess());
        assertEquals(GamePhase.GAME_OVER, game.getPhase());
        assertEquals(15, playerZero.getPrestigePoints());
        assertEquals(15, playerOne.getPrestigePoints());
        assertEquals(2, playerZero.getDevelopmentCards().size());
        assertEquals(2, playerOne.getDevelopmentCards().size());
        assertEquals(2, game.getWinners().size());
        assertTrue(game.getWinners().contains(playerZero));
        assertTrue(game.getWinners().contains(playerOne));
    }

    @Test
    void buyReservedCard_duringFinalRoundCompletesFinalRoundAndCalculatesWinner() {
        Game game = new Game();
        game.startGame(2, Locale.US);
        Player playerZero = game.getCurrentPlayer();
        Player playerOne = game.getPlayers().get(1);
        playerZero.addDevelopmentCard(new Card(1, TokenColor.DIAMOND, Map.of(), 14));
        Card triggerCard = new Card(1, TokenColor.RUBY, Map.of(), 1);
        playerZero.addReservedCard(triggerCard);
        game.buyReservedCard(0, Locale.US);
        Card finalRoundCard = new Card(1, TokenColor.SAPPHIRE, Map.of(), 1);
        playerOne.addReservedCard(finalRoundCard);

        ActionResult result = game.buyReservedCard(0, Locale.US);

        assertTrue(result.isSuccess());
        assertEquals(GamePhase.GAME_OVER, game.getPhase());
        assertEquals(playerZero, game.getWinner());
        assertEquals(1, game.getWinners().size());
        assertEquals(playerZero, game.getWinners().get(0));
        assertEquals(playerZero, game.getCurrentPlayer());
    }

    private static String levelOneDrawOrderFingerprint(Game game) {
        StringBuilder fingerprint = new StringBuilder();
        for (Card card : game.getFaceUpCards(1)) {
            fingerprint.append(cardFingerprint(card)).append('|');
        }
        fingerprint.append(cardFingerprint(game.getDeck(1).cards.get(0)));
        return fingerprint.toString();
    }

    private static String cardFingerprint(Card card) {
        return card.bonusColor + ":" + card.prestigePoints + ":" + card.cost;
    }
}
