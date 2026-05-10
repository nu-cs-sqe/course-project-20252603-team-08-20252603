package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Game
{
    private GamePhase phase;
    private List<Player> players;
    int currentPlayerIndex;
    private TokenBank tokenBank;
    private RuleValidator ruleValidator;

    public Game(){
        this.phase = GamePhase.SETUP;
    }

    public ActionResult startGame(int playerCount, Locale locale) {
        initializeRuleValidator();

        ActionResult validationResult = ruleValidator.validatePlayerCount(playerCount, locale);
        if (!validationResult.isSuccess()) {
            return validationResult;
        }

        initializePlayers(playerCount);
        initializeTokenBank(playerCount);
        currentPlayerIndex = 0;
        phase = GamePhase.PLAYER_TURN;

        return ActionResult.success();
    }

    private void initializeRuleValidator() {
        this.ruleValidator = new RuleValidator();
    }

    public GamePhase getPhase() {
        return phase;
    }

    private void initializePlayers(int playerCount) {
        players = new ArrayList<>(playerCount);

        for (int i = 0; i < playerCount; i++) {
            players.add(new Player());
        }
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }


    private void initializeTokenBank(int playerCount) {
        tokenBank = new TokenBank();
        tokenBank.initialize(playerCount);
    }

    public TokenBank getTokenBank() {
        return tokenBank;
    }

    public List<Card> getFaceUpCards(int level) {
        return null;
    }

    public Deck getDeck(int level) {
        return null;
    }

    public List<Noble> getRevealedNobles() {
        return null;
    }

    public ActionResult takeTokens(Map<TokenColor, Integer> tokensToTake, Locale locale) {
        if (phase != GamePhase.PLAYER_TURN || players == null || tokenBank == null) {
            String errorMessage = MessageProvider.getMessage("error.invalid_token_selection", locale);
            return ActionResult.failure(errorMessage);
        }

        if (ruleValidator == null) {
            initializeRuleValidator();
        }

        Player currentPlayer = getCurrentPlayer();
        ActionResult validationResult = ruleValidator.validateTakeTokens(currentPlayer, tokenBank, tokensToTake, locale);
        if (!validationResult.isSuccess()) {
            return validationResult;
        }

        currentPlayer.addTokens(tokensToTake);
        tokenBank.removeTokens(tokensToTake);
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();

        return ActionResult.success();
    }
}
