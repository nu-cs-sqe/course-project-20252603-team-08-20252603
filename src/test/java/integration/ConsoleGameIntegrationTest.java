package integration;

import domain.MessageProvider;
import org.junit.jupiter.api.Test;
import ui.ConsoleGame;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

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
}
