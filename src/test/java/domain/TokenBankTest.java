package domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TokenBankTest {

    @Test
    void initialize_twoPlayers_setsFourTokensPerGemColor() {
        TokenBank bank = new TokenBank();

        bank.initialize(2);

        assertEquals(4, bank.getTokenCount(TokenColor.DIAMOND));
        assertEquals(4, bank.getTokenCount(TokenColor.SAPPHIRE));
        assertEquals(4, bank.getTokenCount(TokenColor.EMERALD));
        assertEquals(4, bank.getTokenCount(TokenColor.RUBY));
        assertEquals(4, bank.getTokenCount(TokenColor.ONYX));
    }

    @Test
    void initialize_threePlayers_setsFiveTokensPerGemColor() {
        TokenBank bank = new TokenBank();

        bank.initialize(3);

        assertEquals(5, bank.getTokenCount(TokenColor.DIAMOND));
        assertEquals(5, bank.getTokenCount(TokenColor.SAPPHIRE));
        assertEquals(5, bank.getTokenCount(TokenColor.EMERALD));
        assertEquals(5, bank.getTokenCount(TokenColor.RUBY));
        assertEquals(5, bank.getTokenCount(TokenColor.ONYX));
    }

    @Test
    void initialize_fourPlayers_setsSevenTokensPerGemColor() {
        TokenBank bank = new TokenBank();

        bank.initialize(4);

        assertEquals(7, bank.getTokenCount(TokenColor.DIAMOND));
        assertEquals(7, bank.getTokenCount(TokenColor.SAPPHIRE));
        assertEquals(7, bank.getTokenCount(TokenColor.EMERALD));
        assertEquals(7, bank.getTokenCount(TokenColor.RUBY));
        assertEquals(7, bank.getTokenCount(TokenColor.ONYX));
    }

    @Test
    void initialize_setsFiveGoldTokens() {
        TokenBank bank = new TokenBank();

        bank.initialize(2);

        assertEquals(5, bank.getTokenCount(TokenColor.GOLD));
    }

    @Test
    void initialize_rejectsTooFewPlayers() {
        TokenBank bank = new TokenBank();

        assertThrows(IllegalArgumentException.class, () -> {
            bank.initialize(1);
        });
    }

    @Test
    void initialize_rejectsTooManyPlayers() {
        TokenBank bank = new TokenBank();

        assertThrows(IllegalArgumentException.class, () -> {
            bank.initialize(5);
        });
    }
}