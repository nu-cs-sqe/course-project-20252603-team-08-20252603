package domain;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void newPlayer_startsWithZeroPrestigePoints() {
        Player player = new Player();

        assertEquals(0, player.getPrestigePoints());
    }

    @Test
    void newPlayer_startsWithZeroDiamondTokens() {
        Player player = new Player();

        assertEquals(0, player.getTokenCount(TokenColor.DIAMOND));
    }

    @Test
    void newPlayer_startsWithZeroSapphireTokens() {
        Player player = new Player();

        assertEquals(0, player.getTokenCount(TokenColor.SAPPHIRE));
    }

    @Test
    void newPlayer_startsWithZeroEmeraldTokens() {
        Player player = new Player();

        assertEquals(0, player.getTokenCount(TokenColor.EMERALD));
    }

    @Test
    void newPlayer_startsWithZeroRubyTokens() {
        Player player = new Player();

        assertEquals(0, player.getTokenCount(TokenColor.RUBY));
    }

    @Test
    void newPlayer_startsWithZeroOnyxTokens() {
        Player player = new Player();

        assertEquals(0, player.getTokenCount(TokenColor.ONYX));
    }

    @Test
    void newPlayer_startsWithZeroGoldTokens() {
        Player player = new Player();

        assertEquals(0, player.getTokenCount(TokenColor.GOLD));
    }

    @Test
    void newPlayer_startsWithNoDevelopmentCards() {
        Player player = new Player();

        assertTrue(player.getDevelopmentCards().isEmpty());
    }

    @Test
    void newPlayer_startsWithNoReservedCards() {
        Player player = new Player();

        assertTrue(player.getReservedCards().isEmpty());
    }

    @Test
    void newPlayer_startsWithZeroTotalTokens() {
        Player player = new Player();

        assertEquals(0, player.getTotalTokenCount());
    }

    @Test
    void addTokens_addsSingleToken() {
        Player player = new Player();

        player.addTokens(Map.of(TokenColor.DIAMOND, 1));

        assertEquals(1, player.getTokenCount(TokenColor.DIAMOND));
        assertEquals(1, player.getTotalTokenCount());
    }

    @Test
    void addTokens_addsMultipleColors() {
        Player player = new Player();

        player.addTokens(Map.of(TokenColor.DIAMOND, 2, TokenColor.RUBY, 1));

        assertEquals(2, player.getTokenCount(TokenColor.DIAMOND));
        assertEquals(1, player.getTokenCount(TokenColor.RUBY));
        assertEquals(3, player.getTotalTokenCount());
    }

    @Test
    void addTokens_accumulatesExistingTokens() {
        Player player = new Player();

        player.addTokens(Map.of(TokenColor.DIAMOND, 1));
        player.addTokens(Map.of(TokenColor.DIAMOND, 2));

        assertEquals(3, player.getTokenCount(TokenColor.DIAMOND));
        assertEquals(3, player.getTotalTokenCount());
    }

    @Test
    void addTokens_emptyMapLeavesTokensUnchanged() {
        Player player = new Player();

        player.addTokens(Map.of());

        assertEquals(0, player.getTotalTokenCount());
    }

    @Test
    void removeTokens_removesSingleToken() {
        Player player = new Player();
        player.addTokens(Map.of(TokenColor.DIAMOND, 2));

        player.removeTokens(Map.of(TokenColor.DIAMOND, 1));

        assertEquals(1, player.getTokenCount(TokenColor.DIAMOND));
        assertEquals(1, player.getTotalTokenCount());
    }

    @Test
    void removeTokens_removesAllRequestedTokens() {
        Player player = new Player();
        player.addTokens(Map.of(TokenColor.DIAMOND, 2, TokenColor.RUBY, 1));

        player.removeTokens(Map.of(TokenColor.DIAMOND, 2, TokenColor.RUBY, 1));

        assertEquals(0, player.getTokenCount(TokenColor.DIAMOND));
        assertEquals(0, player.getTokenCount(TokenColor.RUBY));
        assertEquals(0, player.getTotalTokenCount());
    }

    @Test
    void removeTokens_rejectsRemovingMoreThanPlayerHas() {
        Player player = new Player();
        player.addTokens(Map.of(TokenColor.DIAMOND, 1));

        assertThrows(IllegalArgumentException.class, () -> {
            player.removeTokens(Map.of(TokenColor.DIAMOND, 2));
        });
        assertEquals(1, player.getTokenCount(TokenColor.DIAMOND));
        assertEquals(1, player.getTotalTokenCount());
    }

    @Test
    void removeTokens_rejectsRemovingTokenFromEmptyPlayer() {
        Player player = new Player();

        assertThrows(IllegalArgumentException.class, () -> {
            player.removeTokens(Map.of(TokenColor.DIAMOND, 1));
        });
        assertEquals(0, player.getTokenCount(TokenColor.DIAMOND));
        assertEquals(0, player.getTotalTokenCount());
    }

    @Test
    void removeTokens_emptyMapLeavesTokensUnchanged() {
        Player player = new Player();
        player.addTokens(Map.of(TokenColor.DIAMOND, 1));

        player.removeTokens(Map.of());

        assertEquals(1, player.getTokenCount(TokenColor.DIAMOND));
        assertEquals(1, player.getTotalTokenCount());
    }

    @Test
    void addReservedCard_addsOneReservedCard() {
        Player player = new Player();
        Card card = new Card();

        player.addReservedCard(card);

        assertEquals(1, player.getReservedCards().size());
        assertTrue(player.getReservedCards().contains(card));
    }

    @Test
    void addReservedCard_addsAnotherReservedCard() {
        Player player = new Player();
        Card firstCard = new Card();
        Card secondCard = new Card();

        player.addReservedCard(firstCard);
        player.addReservedCard(secondCard);

        assertEquals(2, player.getReservedCards().size());
        assertTrue(player.getReservedCards().contains(firstCard));
        assertTrue(player.getReservedCards().contains(secondCard));
    }

    @Test
    void removeReservedCard_removesOnlyReservedCard() {
        Player player = new Player();
        Card card = new Card();
        player.addReservedCard(card);

        player.removeReservedCard(card);

        assertTrue(player.getReservedCards().isEmpty());
    }

    @Test
    void removeReservedCard_removesFirstReservedCardAndLeavesSecondCard() {
        Player player = new Player();
        Card firstCard = new Card();
        Card secondCard = new Card();
        player.addReservedCard(firstCard);
        player.addReservedCard(secondCard);

        player.removeReservedCard(firstCard);

        assertEquals(1, player.getReservedCards().size());
        assertFalse(player.getReservedCards().contains(firstCard));
        assertTrue(player.getReservedCards().contains(secondCard));
    }

    @Test
    void removeReservedCard_removesSecondReservedCardAndLeavesFirstCard() {
        Player player = new Player();
        Card firstCard = new Card();
        Card secondCard = new Card();
        player.addReservedCard(firstCard);
        player.addReservedCard(secondCard);

        player.removeReservedCard(secondCard);

        assertEquals(1, player.getReservedCards().size());
        assertTrue(player.getReservedCards().contains(firstCard));
        assertFalse(player.getReservedCards().contains(secondCard));
    }

    @Test
    void removeReservedCard_rejectsCardThatIsNotReservedAndLeavesReservedCardsUnchanged() {
        Player player = new Player();
        Card reservedCard = new Card();
        Card unreservedCard = new Card();
        player.addReservedCard(reservedCard);

        assertThrows(IllegalArgumentException.class, () -> {
            player.removeReservedCard(unreservedCard);
        });
        assertEquals(1, player.getReservedCards().size());
        assertTrue(player.getReservedCards().contains(reservedCard));
    }

    @Test
    void addDevelopmentCard_addsOneZeroPointDevelopmentCard() {
        Player player = new Player();
        Card card = new Card(1, TokenColor.DIAMOND, Map.of(TokenColor.RUBY, 1), 0);

        player.addDevelopmentCard(card);

        assertEquals(1, player.getDevelopmentCards().size());
        assertTrue(player.getDevelopmentCards().contains(card));
        assertEquals(0, player.getPrestigePoints());
    }

    @Test
    void addDevelopmentCard_addsOnePointDevelopmentCardAndPrestigePoint() {
        Player player = new Player();
        Card card = new Card(1, TokenColor.DIAMOND, Map.of(TokenColor.RUBY, 1), 1);

        player.addDevelopmentCard(card);

        assertEquals(1, player.getDevelopmentCards().size());
        assertTrue(player.getDevelopmentCards().contains(card));
        assertEquals(1, player.getPrestigePoints());
    }

    @Test
    void addDevelopmentCard_addsAnotherDevelopmentCardAndAccumulatesPrestigePoints() {
        Player player = new Player();
        Card firstCard = new Card(1, TokenColor.DIAMOND, Map.of(TokenColor.RUBY, 1), 1);
        Card secondCard = new Card(2, TokenColor.SAPPHIRE, Map.of(TokenColor.ONYX, 2), 2);

        player.addDevelopmentCard(firstCard);
        player.addDevelopmentCard(secondCard);

        assertEquals(2, player.getDevelopmentCards().size());
        assertTrue(player.getDevelopmentCards().contains(firstCard));
        assertTrue(player.getDevelopmentCards().contains(secondCard));
        assertEquals(3, player.getPrestigePoints());
    }

    @Test
    void getBonusCount_returnsZeroForNewPlayer() {
        Player player = new Player();

        assertEquals(0, player.getBonusCount(TokenColor.DIAMOND));
    }

    @Test
    void getBonusCount_countsOneMatchingDevelopmentCard() {
        Player player = new Player();
        Card card = new Card(1, TokenColor.DIAMOND, Map.of(TokenColor.RUBY, 1), 0);

        player.addDevelopmentCard(card);

        assertEquals(1, player.getBonusCount(TokenColor.DIAMOND));
    }

    @Test
    void getBonusCount_countsTwoMatchingDevelopmentCards() {
        Player player = new Player();
        Card firstCard = new Card(1, TokenColor.DIAMOND, Map.of(TokenColor.RUBY, 1), 0);
        Card secondCard = new Card(1, TokenColor.DIAMOND, Map.of(TokenColor.ONYX, 1), 0);

        player.addDevelopmentCard(firstCard);
        player.addDevelopmentCard(secondCard);

        assertEquals(2, player.getBonusCount(TokenColor.DIAMOND));
    }

    @Test
    void getBonusCount_doesNotCountDifferentColorDevelopmentCard() {
        Player player = new Player();
        Card card = new Card(1, TokenColor.RUBY, Map.of(TokenColor.DIAMOND, 1), 0);

        player.addDevelopmentCard(card);

        assertEquals(0, player.getBonusCount(TokenColor.DIAMOND));
    }

    @Test
    void getBonusCount_doesNotCountReservedCard() {
        Player player = new Player();
        Card card = new Card(1, TokenColor.DIAMOND, Map.of(TokenColor.RUBY, 1), 0);

        player.addReservedCard(card);

        assertEquals(0, player.getBonusCount(TokenColor.DIAMOND));
    }

    @Test
    void getBonusCount_returnsZeroForGold() {
        Player player = new Player();
        Card card = new Card(1, TokenColor.DIAMOND, Map.of(TokenColor.RUBY, 1), 0);

        player.addDevelopmentCard(card);

        assertEquals(0, player.getBonusCount(TokenColor.GOLD));
    }

}
