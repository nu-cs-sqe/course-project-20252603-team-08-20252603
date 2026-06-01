# Instructor Code Review Feedback

**Contact**: Dr. Yiji Zhang (yiji.zhang@northwestern.edu)

**Purpose of This Document**:
The instructor will perform code review with respect to software design, error handling, format and style on the main branch every week starting Week 6 using the letter grade A standards.
The following chapters of the textbook are considered: Chapter 1, 2, 3, 4, 5, 6, 7, 9, and 10. The corresponding lectures are considered, too.

Please note that this feedback does not include evaluation of your progress, the proper use of linters, the quality of your test cases, or your compliance of TDD/BDD workflow.  
You can find the weekly feedback from your dedicated PM/TA for that.

## Week 7-8 Code Review
This review is for the code your team developed in Week 7 and 8.
I apologize for this delayed code review (should have been given last Friday but I got really sick...).
As compensation, I will add one extra code review in Week 10 (around Thursday).

Overall, good work! I only have one minor comment: the methods in `Game` class probably can be broken into multiple private methods so that 
the principle "each function only does one thing" is better complied with. For instance, 
```
public ActionResult reserveFaceUpCard(int level, int cardIndex, Locale locale) {
        //// One method for the input validation below
        if (phase != GamePhase.PLAYER_TURN || players == null || tokenBank == null || faceUpCards == null || decks == null) {
            String errorMessage = MessageProvider.getMessage("error.invalid_reserve_card", locale);
            return ActionResult.failure(errorMessage);
        }

        if (ruleValidator == null) {
            initializeRuleValidator();
        }

        //// One method for the code below
        List<Card> cards = faceUpCards.get(level);
        Player currentPlayer = getCurrentPlayer();
        ActionResult validationResult = ruleValidator.validateReserveCard(currentPlayer, cards, cardIndex, locale);
        if (!validationResult.isSuccess()) {
            return validationResult;
        }

        Card reservedCard = cards.remove(cardIndex);
        currentPlayer.addReservedCard(reservedCard);

        //// One method for the code below
        Deck deck = decks.get(level);
        if (deck != null && !deck.isEmpty()) {
            cards.add(deck.drawCard());
        }

        //// One method for the if below
        if (tokenBank.getTokenCount(TokenColor.GOLD) > 0) {
            currentPlayer.addTokens(Map.of(TokenColor.GOLD, 1));
            tokenBank.removeTokens(Map.of(TokenColor.GOLD, 1));
        }

        currentPlayerIndex = (currentPlayerIndex + NEXT_PLAYER_OFFSET) % players.size();

        return ActionResult.success();
    }
```

I am very excited about your work! Can't wait to see the final product!


## Week 6 Code Review
I have read every line of production code currently in the main branch. Here are 2 suggestions: 

1. CardLoader class and NobleLoader class: there is a try block but there is no catch. I could have missed some but I can see the following possibilities and they are worth catching and dealt with.
    - JsonParser.parseReader throws com.google.gson.JsonIOException, com.google.gson.JsonSyntaxException
    - TokenColor.valueOf throws IllegalArgumentException
2. Use of magic number in the Game class and TokenBank class should be avoided.

Otherwise, looks good!

Look forward to reviewing more of your domain logic in the next review.

Please approve and merge the PR once the team has read the feedback. Thanks!