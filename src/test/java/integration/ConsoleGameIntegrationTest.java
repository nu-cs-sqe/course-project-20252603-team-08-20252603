package integration;

import domain.MessageProvider;
import org.junit.jupiter.api.Test;
import ui.ConsoleGame;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConsoleGameIntegrationTest {

    @Test
    void integrationTC1_englishLocaleTakeTokensAndQuit() {
        String input = String.join("\n",
                "en",
                "2",
                "take RUBY DIAMOND ONYX",
                "quit") + "\n";
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        ConsoleGame consoleGame = new ConsoleGame(
                new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)),
                new java.io.PrintStream(output, true, StandardCharsets.UTF_8));
        consoleGame.run();

        String consoleOutput = output.toString(StandardCharsets.UTF_8);
        assertTrue(consoleOutput.contains(MessageProvider.getMessage("ui.game_started", Locale.US)));
        assertTrue(consoleOutput.contains(MessageProvider.getMessage("ui.action_succeeded", Locale.US)));
        assertTrue(consoleOutput.contains(MessageProvider.getMessage("ui.goodbye", Locale.US)));
        assertTrue(consoleOutput.contains("RUBY: 3"));
        assertTrue(consoleOutput.contains(
                MessageProvider.getMessage("ui.current_player", Locale.US)
                        + " "
                        + MessageProvider.getMessage("ui.player", Locale.US)
                        + " 2"));
    }

    @Test
    void integrationTC2_chineseLocaleReserveCardAndQuit() {
        String input = String.join("\n",
                "zh",
                "2",
                "reserve 1 0",
                "quit") + "\n";
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        ConsoleGame consoleGame = new ConsoleGame(
                new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)),
                new java.io.PrintStream(output, true, StandardCharsets.UTF_8));
        consoleGame.run();

        String consoleOutput = output.toString(StandardCharsets.UTF_8);
        assertTrue(consoleOutput.contains(MessageProvider.getMessage("ui.game_started", Locale.SIMPLIFIED_CHINESE)));
        assertTrue(consoleOutput.contains(MessageProvider.getMessage("ui.action_succeeded", Locale.SIMPLIFIED_CHINESE)));
        assertTrue(consoleOutput.contains(MessageProvider.getMessage("ui.reserved_cards", Locale.SIMPLIFIED_CHINESE)));
        assertTrue(consoleOutput.contains(MessageProvider.getMessage("ui.goodbye", Locale.SIMPLIFIED_CHINESE)));

        assertFalse(consoleOutput.contains(MessageProvider.getMessage("ui.game_started", Locale.US)));
    }

    @Test
    void integrationTC3_invalidPlayerCountRejectedBeforeGameLoop() {
        String input = String.join("\n",
                "en",
                "1") + "\n";
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        ConsoleGame consoleGame = new ConsoleGame(
                new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)),
                new java.io.PrintStream(output, true, StandardCharsets.UTF_8));
        consoleGame.run();

        String consoleOutput = output.toString(StandardCharsets.UTF_8);
        assertTrue(consoleOutput.contains(MessageProvider.getMessage("error.invalid_player_count", Locale.US)));
        assertFalse(consoleOutput.contains(MessageProvider.getMessage("ui.game_started", Locale.US)));
    }

    @Test
    void integrationTC4_unknownActionHandledWithoutEndingSession() {
        String input = String.join("\n",
                "en",
                "2",
                "not-a-command",
                "quit") + "\n";
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        ConsoleGame consoleGame = new ConsoleGame(
                new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)),
                new java.io.PrintStream(output, true, StandardCharsets.UTF_8));
        consoleGame.run();

        String gameStarted = MessageProvider.getMessage("ui.game_started", Locale.US);
        String unknownAction = MessageProvider.getMessage("ui.unknown_action", Locale.US);
        String goodbye = MessageProvider.getMessage("ui.goodbye", Locale.US);
        String tokenBank = MessageProvider.getMessage("ui.token_bank", Locale.US);
        String enterAction = MessageProvider.getMessage("ui.enter_action", Locale.US);

        String consoleOutput = output.toString(StandardCharsets.UTF_8);
        assertTrue(consoleOutput.contains(gameStarted));
        assertTrue(consoleOutput.contains(unknownAction));
        assertTrue(consoleOutput.contains(goodbye));

        int gameStartedIndex = consoleOutput.indexOf(gameStarted);
        int tokenBankBeforeUnknownActionIndex = consoleOutput.indexOf(tokenBank);
        int unknownActionIndex = consoleOutput.indexOf(unknownAction);
        int enterActionBeforeQuitIndex = consoleOutput.lastIndexOf(enterAction);
        int goodbyeIndex = consoleOutput.indexOf(goodbye);

        assertTrue(gameStartedIndex < tokenBankBeforeUnknownActionIndex);
        assertTrue(tokenBankBeforeUnknownActionIndex < unknownActionIndex);
        assertTrue(unknownActionIndex < enterActionBeforeQuitIndex);
        assertTrue(enterActionBeforeQuitIndex < goodbyeIndex);
    }
}
