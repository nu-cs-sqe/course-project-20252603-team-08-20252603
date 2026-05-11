package domain;

import org.junit.jupiter.api.Test;

import java.util.Map;

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

    @Test
    void addTokens_addsSingleGemToken() {
        TokenBank bank = new TokenBank();
        bank.initialize(2);

        bank.addTokens(Map.of(TokenColor.DIAMOND, 1));

        assertEquals(5, bank.getTokenCount(TokenColor.DIAMOND));
    }

    @Test
    void addTokens_addsMultipleGemColors() {
        TokenBank bank = new TokenBank();
        bank.initialize(2);

        bank.addTokens(Map.of(TokenColor.DIAMOND, 2, TokenColor.RUBY, 1));

        assertEquals(6, bank.getTokenCount(TokenColor.DIAMOND));
        assertEquals(5, bank.getTokenCount(TokenColor.RUBY));
    }

    @Test
    void addTokens_addsGoldToken() {
        TokenBank bank = new TokenBank();
        bank.initialize(2);

        bank.addTokens(Map.of(TokenColor.GOLD, 1));

        assertEquals(6, bank.getTokenCount(TokenColor.GOLD));
    }

    @Test
    void addTokens_emptyMapLeavesTokensUnchanged() {
        TokenBank bank = new TokenBank();
        bank.initialize(2);

        bank.addTokens(Map.of());

        assertEquals(4, bank.getTokenCount(TokenColor.DIAMOND));
        assertEquals(5, bank.getTokenCount(TokenColor.GOLD));
    }

    @Test
    void removeTokens_removesSingleGemToken() {
        TokenBank bank = new TokenBank();
        bank.initialize(2);

        bank.removeTokens(Map.of(TokenColor.DIAMOND, 1));

        assertEquals(3, bank.getTokenCount(TokenColor.DIAMOND));
    }

    @Test
    void removeTokens_removesMultipleGemColors() {
        TokenBank bank = new TokenBank();
        bank.initialize(2);

        bank.removeTokens(Map.of(TokenColor.DIAMOND, 2, TokenColor.RUBY, 1));

        assertEquals(2, bank.getTokenCount(TokenColor.DIAMOND));
        assertEquals(3, bank.getTokenCount(TokenColor.RUBY));
    }

    @Test
    void removeTokens_removesGoldToken() {
        TokenBank bank = new TokenBank();
        bank.initialize(2);

        bank.removeTokens(Map.of(TokenColor.GOLD, 1));

        assertEquals(4, bank.getTokenCount(TokenColor.GOLD));
    }

    @Test
    void removeTokens_emptyMapLeavesTokensUnchanged() {
        TokenBank bank = new TokenBank();
        bank.initialize(2);

        bank.removeTokens(Map.of());

        assertEquals(4, bank.getTokenCount(TokenColor.DIAMOND));
        assertEquals(5, bank.getTokenCount(TokenColor.GOLD));
    }

    @Test
    void removeTokens_rejectsRemovingMoreThanBankHas() {
        TokenBank bank = new TokenBank();
        bank.initialize(2);

        assertThrows(IllegalArgumentException.class, () -> {
            bank.removeTokens(Map.of(TokenColor.DIAMOND, 5));
        });
        assertEquals(4, bank.getTokenCount(TokenColor.DIAMOND));
    }

    @Test
    void removeTokens_rejectsRemovingFromUninitializedBank() {
        TokenBank bank = new TokenBank();

        assertThrows(IllegalArgumentException.class, () -> {
            bank.removeTokens(Map.of(TokenColor.DIAMOND, 1));
        });
        assertEquals(0, bank.getTokenCount(TokenColor.DIAMOND));
    }
}
