package ui;

import domain.ActionResult;
import domain.Card;
import domain.Game;
import domain.MessageProvider;
import domain.Noble;
import domain.TokenColor;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.EnumMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class ConsoleGame {
    private final InputStream in;
    private final PrintStream out;

    public ConsoleGame(InputStream in, PrintStream out) {
        this.in = in;
        this.out = out;
    }

    public void run() {
        Scanner scanner = new Scanner(in);
        Locale locale = readLocale(scanner);
        Game game = new Game();
        int playerCount = readPlayerCount(scanner, locale);
        ActionResult result = game.startGame(playerCount, locale);

        if (!result.isSuccess()) {
            out.println(result.getMessage());
            return;
        }

        out.println(message("ui.game_started", locale));
        while (true) {
            printTable(game, locale);
            out.println(message("ui.enter_action", locale));
            if (!scanner.hasNextLine()) {
                return;
            }

            String line = scanner.nextLine().trim();
            if (line.equalsIgnoreCase("quit")) {
                out.println(message("ui.goodbye", locale));
                return;
            }

            if (line.toLowerCase(Locale.US).startsWith("take ")) {
                ActionResult actionResult = game.takeTokens(parseTokens(line.substring(5)), locale);
                if (actionResult.isSuccess()) {
                    out.println(message("ui.action_succeeded", locale));
                } else {
                    out.println(actionResult.getMessage());
                }
            } else if (line.toLowerCase(Locale.US).startsWith("reserve ")) {
                ActionResult actionResult = reserveFaceUpCard(game, line.substring(8), locale);
                if (actionResult.isSuccess()) {
                    out.println(message("ui.action_succeeded", locale));
                } else {
                    out.println(actionResult.getMessage());
                }
            } else {
                out.println(message("ui.unknown_action", locale));
            }
        }
    }

    private Locale readLocale(Scanner scanner) {
        out.println(message("ui.select_language", Locale.US) + " / " + message("ui.select_language", Locale.SIMPLIFIED_CHINESE));
        if (!scanner.hasNextLine()) {
            return Locale.US;
        }

        String line = scanner.nextLine().trim();
        if (line.equalsIgnoreCase("zh")) {
            return Locale.SIMPLIFIED_CHINESE;
        }
        return Locale.US;
    }

    private int readPlayerCount(Scanner scanner, Locale locale) {
        while (true) {
            out.println(message("ui.enter_player_count", locale));
            if (!scanner.hasNextLine()) {
                return 2;
            }

            String line = scanner.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                out.println(message("ui.enter_number", locale));
            }
        }
    }

    private void printTable(Game game, Locale locale) {
        out.println();
        out.println(message("ui.current_player", locale) + " " + message("ui.player", locale) + " " + (game.getCurrentPlayerIndex() + 1));
        printTokenBank(game, locale);
        printCards(game, locale);
        printNobles(game, locale);
    }

    private void printTokenBank(Game game, Locale locale) {
        out.println(message("ui.token_bank", locale));
        for (TokenColor color : TokenColor.values()) {
            out.println("  " + color + ": " + game.getTokenBank().getTokenCount(color));
        }
    }

    private void printCards(Game game, Locale locale) {
        out.println(message("ui.face_up_cards", locale));
        for (int level = 1; level <= 3; level++) {
            out.println("  " + message("ui.level", locale) + " " + level + ":");
            List<Card> cards = game.getFaceUpCards(level);
            for (int i = 0; i < cards.size(); i++) {
                Card card = cards.get(i);
                out.println("    [" + i + "] " + message("ui.bonus", locale) + "=" + card.bonusColor + ", " + message("ui.points", locale) + "=" + card.prestigePoints + ", " + message("ui.cost", locale) + "=" + formatCost(card.cost));
            }
        }
    }

    private void printNobles(Game game, Locale locale) {
        out.println(message("ui.nobles", locale));
        List<Noble> nobles = game.getRevealedNobles();
        for (int i = 0; i < nobles.size(); i++) {
            Noble noble = nobles.get(i);
            out.println("  [" + i + "] " + message("ui.points", locale) + "=" + noble.prestigePoints + ", " + message("ui.requirements", locale) + "=" + formatCost(noble.requirements));
        }
    }

    private String formatCost(Map<TokenColor, Integer> cost) {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<TokenColor, Integer> entry : cost.entrySet()) {
            if (builder.length() > 0) {
                builder.append(", ");
            }
            builder.append(entry.getKey()).append("=").append(entry.getValue());
        }
        return builder.toString();
    }

    private Map<TokenColor, Integer> parseTokens(String input) {
        Map<TokenColor, Integer> tokens = new EnumMap<>(TokenColor.class);
        String[] parts = input.trim().split("\\s+");
        for (String part : parts) {
            if (!part.isEmpty()) {
                TokenColor color = TokenColor.valueOf(part.toUpperCase(Locale.US));
                tokens.put(color, tokens.getOrDefault(color, 0) + 1);
            }
        }
        return tokens;
    }

    private ActionResult reserveFaceUpCard(Game game, String input, Locale locale) {
        String[] parts = input.trim().split("\\s+");
        if (parts.length != 2) {
            return ActionResult.failure(message("ui.unknown_action", locale));
        }

        try {
            int level = Integer.parseInt(parts[0]);
            int cardIndex = Integer.parseInt(parts[1]);
            return game.reserveFaceUpCard(level, cardIndex, locale);
        } catch (NumberFormatException e) {
            return ActionResult.failure(message("ui.unknown_action", locale));
        }
    }

    private String message(String key, Locale locale) {
        return MessageProvider.getMessage(key, locale);
    }
}
