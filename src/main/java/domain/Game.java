package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
}
