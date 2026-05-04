package domain;

import org.junit.jupiter.api.Test;

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
}