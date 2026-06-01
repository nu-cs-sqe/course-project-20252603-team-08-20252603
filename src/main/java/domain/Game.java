package domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Game
{
    private static final int FIRST_PLAYER_INDEX = 0;
    private static final int NO_WINNER_INDEX = -1;
    private static final int NEXT_PLAYER_OFFSET = 1;
    private static final int MIN_CARD_LEVEL = 1;
    private static final int MAX_CARD_LEVEL = 3;
    private static final int FACE_UP_CARDS_PER_LEVEL = 4;
    private static final int EXTRA_REVEALED_NOBLE_COUNT = 1;
    private static final int WINNING_PRESTIGE_POINTS = 15;
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
    private int winnerIndex;

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
        winnerIndex = NO_WINNER_INDEX;
        phase = GamePhase.PLAYER_TURN;

        return ActionResult.success();
    }

    private void initializeRuleValidator() {
        this.ruleValidator = new RuleValidator();
    }

    public GamePhase getPhase() {
        return phase;
    }

    public Player getWinner() {
        if (players == null || winnerIndex == NO_WINNER_INDEX) {
            return null;
        }
        return players.get(winnerIndex);
    }

    private void initializePlayers(int playerCount) {
        players = new ArrayList<>(playerCount);

        for (int i = 0; i < playerCount; i++) {
            players.add(new Player());
        }
    }

    public List<Player> getPlayers() {
        return Collections.unmodifiableList(players);
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
        if (tokenBank == null) {
            return null;
        }
        return new TokenBank(tokenBank);
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
        if (revealedNobles == null) {
            return null;
        }
        return Collections.unmodifiableList(revealedNobles);
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

    public ActionResult reserveFaceUpCard(int level, int cardIndex, Locale locale) {
        if (phase != GamePhase.PLAYER_TURN || players == null || tokenBank == null || faceUpCards == null || decks == null) {
            String errorMessage = MessageProvider.getMessage("error.invalid_reserve_card", locale);
            return ActionResult.failure(errorMessage);
        }

        if (ruleValidator == null) {
            initializeRuleValidator();
        }

        List<Card> cards = faceUpCards.get(level);
        Player currentPlayer = getCurrentPlayer();
        ActionResult validationResult = ruleValidator.validateReserveCard(currentPlayer, cards, cardIndex, locale);
        if (!validationResult.isSuccess()) {
            return validationResult;
        }

        Card reservedCard = cards.remove(cardIndex);
        currentPlayer.addReservedCard(reservedCard);

        Deck deck = decks.get(level);
        if (deck != null && !deck.isEmpty()) {
            cards.add(deck.drawCard());
        }

        if (tokenBank.getTokenCount(TokenColor.GOLD) > 0) {
            currentPlayer.addTokens(Map.of(TokenColor.GOLD, 1));
            tokenBank.removeTokens(Map.of(TokenColor.GOLD, 1));
        }

        currentPlayerIndex = (currentPlayerIndex + NEXT_PLAYER_OFFSET) % players.size();

        return ActionResult.success();
    }

    public ActionResult buyFaceUpCard(int level, int cardIndex, Locale locale) {
        if (phase != GamePhase.PLAYER_TURN || players == null || tokenBank == null || faceUpCards == null || decks == null) {
            String errorMessage = MessageProvider.getMessage("error.invalid_buy_card", locale);
            return ActionResult.failure(errorMessage);
        }

        if (ruleValidator == null) {
            initializeRuleValidator();
        }

        List<Card> cards = faceUpCards.get(level);
        if (cards == null || cardIndex < 0 || cardIndex >= cards.size()) {
            String errorMessage = MessageProvider.getMessage("error.invalid_buy_card", locale);
            return ActionResult.failure(errorMessage);
        }

        Player currentPlayer = getCurrentPlayer();
        Card card = cards.get(cardIndex);
        ActionResult validationResult = ruleValidator.validateBuyCard(currentPlayer, card, locale);
        if (!validationResult.isSuccess()) {
            return validationResult;
        }

        Map<TokenColor, Integer> payment = calculatePayment(currentPlayer, card);
        currentPlayer.removeTokens(payment);
        tokenBank.addTokens(payment);

        Card boughtCard = cards.remove(cardIndex);
        currentPlayer.addDevelopmentCard(boughtCard);
        visitAvailableNoble(currentPlayer, locale);

        if (finishGameIfWinner(currentPlayer)) {
            return ActionResult.success();
        }

        Deck deck = decks.get(level);
        if (deck != null && !deck.isEmpty()) {
            cards.add(deck.drawCard());
        }

        currentPlayerIndex = (currentPlayerIndex + NEXT_PLAYER_OFFSET) % players.size();

        return ActionResult.success();
    }

    public ActionResult buyReservedCard(int reservedIndex, Locale locale) {
        if (phase != GamePhase.PLAYER_TURN || players == null || tokenBank == null) {
            String errorMessage = MessageProvider.getMessage("error.invalid_buy_card", locale);
            return ActionResult.failure(errorMessage);
        }

        if (ruleValidator == null) {
            initializeRuleValidator();
        }

        Player currentPlayer = getCurrentPlayer();
        List<Card> reservedCards = currentPlayer.getReservedCards();
        if (reservedIndex < 0 || reservedIndex >= reservedCards.size()) {
            String errorMessage = MessageProvider.getMessage("error.invalid_buy_card", locale);
            return ActionResult.failure(errorMessage);
        }

        Card card = reservedCards.get(reservedIndex);
        ActionResult validationResult = ruleValidator.validateBuyCard(currentPlayer, card, locale);
        if (!validationResult.isSuccess()) {
            return validationResult;
        }

        Map<TokenColor, Integer> payment = calculatePayment(currentPlayer, card);
        currentPlayer.removeTokens(payment);
        tokenBank.addTokens(payment);
        currentPlayer.removeReservedCard(card);
        currentPlayer.addDevelopmentCard(card);
        visitAvailableNoble(currentPlayer, locale);

        if (finishGameIfWinner(currentPlayer)) {
            return ActionResult.success();
        }

        currentPlayerIndex = (currentPlayerIndex + NEXT_PLAYER_OFFSET) % players.size();

        return ActionResult.success();
    }

    private Map<TokenColor, Integer> calculatePayment(Player player, Card card) {
        Map<TokenColor, Integer> payment = new HashMap<>();
        int goldNeeded = 0;

        for (Map.Entry<TokenColor, Integer> entry : card.cost.entrySet()) {
            TokenColor color = entry.getKey();
            int remainingCost = entry.getValue() - player.getBonusCount(color);
            if (remainingCost > 0) {
                int gemTokensUsed = Math.min(player.getTokenCount(color), remainingCost);
                if (gemTokensUsed > 0) {
                    payment.put(color, gemTokensUsed);
                }
                goldNeeded += remainingCost - gemTokensUsed;
            }
        }

        if (goldNeeded > 0) {
            payment.put(TokenColor.GOLD, goldNeeded);
        }

        return payment;
    }

    private boolean finishGameIfWinner(Player player) {
        if (player.getPrestigePoints() >= WINNING_PRESTIGE_POINTS) {
            winnerIndex = currentPlayerIndex;
            phase = GamePhase.GAME_OVER;
            return true;
        }

        return false;
    }

    private void visitAvailableNoble(Player player, Locale locale) {
        if (revealedNobles == null) {
            return;
        }

        for (Noble noble : new ArrayList<>(revealedNobles)) {
            if (ruleValidator.validateNobleVisit(player, noble, locale).isSuccess()) {
                player.addNoble(noble);
                revealedNobles.remove(noble);
                return;
            }
        }
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
