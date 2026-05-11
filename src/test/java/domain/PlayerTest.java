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

}
