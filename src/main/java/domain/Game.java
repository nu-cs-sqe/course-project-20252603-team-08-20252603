package domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Game
{
    private static final int FIRST_PLAYER_INDEX = 0;
    private static final int NEXT_PLAYER_OFFSET = 1;
    private static final int MIN_CARD_LEVEL = 1;
    private static final int MAX_CARD_LEVEL = 3;
    private static final int FACE_UP_CARDS_PER_LEVEL = 4;
    private static final int EXTRA_REVEALED_NOBLE_COUNT = 1;
    private static final String CARDS_RESOURCE_PATH = "/cards/cards.json";
    private static final String NOBLES_RESOURCE_PATH = "/nobles/nobles.json";

    private GamePhase phase;
    private List<Player> players;
    int currentPlayerIndex;
    private TokenBank tokenBank;
    private RuleValidator ruleValidator;
    private Map<Integer, Deck> decks;
    private Map<Integer, List<Card>> faceUpCards;
    private List<Noble> revealedNobles;

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
        initializeCards();
        initializeNobles(playerCount);
        currentPlayerIndex = FIRST_PLAYER_INDEX;
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

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }


    private void initializeTokenBank(int playerCount) {
        tokenBank = new TokenBank();
        tokenBank.initialize(playerCount);
    }

    public TokenBank getTokenBank() {
        return tokenBank;
    }

    public List<Card> getFaceUpCards(int level) {
        if (faceUpCards == null) {
            return null;
        }
        return faceUpCards.get(level);
    }

    public Deck getDeck(int level) {
        if (decks == null) {
            return null;
        }
        return decks.get(level);
    }

    public List<Noble> getRevealedNobles() {
        return revealedNobles;
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
        currentPlayerIndex = (currentPlayerIndex + NEXT_PLAYER_OFFSET) % players.size();

        return ActionResult.success();
    }

    private void initializeCards() {
        try {
            List<Card> cards = new CardLoader().loadFromClasspath(Game.class, CARDS_RESOURCE_PATH);
            decks = new HashMap<>();
            faceUpCards = new HashMap<>();

            for (int level = MIN_CARD_LEVEL; level <= MAX_CARD_LEVEL; level++) {
                decks.put(level, new Deck());
                faceUpCards.put(level, new ArrayList<>());
            }

            for (Card card : cards) {
                decks.get(card.level).addCard(card);
            }

            for (int level = MIN_CARD_LEVEL; level <= MAX_CARD_LEVEL; level++) {
                decks.get(level).shuffle();
                for (int i = 0; i < FACE_UP_CARDS_PER_LEVEL; i++) {
                    faceUpCards.get(level).add(decks.get(level).drawCard());
                }
            }
        } catch (Exception e) {
            throw new IllegalStateException("Unable to initialize cards.", e);
        }
    }

    private void initializeNobles(int playerCount) {
        try {
            List<Noble> nobles = new NobleLoader().loadFromClasspath(Game.class, NOBLES_RESOURCE_PATH);
            revealedNobles = new ArrayList<>();
            for (int i = 0; i < playerCount + EXTRA_REVEALED_NOBLE_COUNT; i++) {
                revealedNobles.add(nobles.get(i));
            }
        } catch (Exception e) {
            throw new IllegalStateException("Unable to initialize nobles.", e);
        }
    }
}
