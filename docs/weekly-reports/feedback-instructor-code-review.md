# Instructor Code Review Feedback

**Contact**: Dr. Yiji Zhang (yiji.zhang@northwestern.edu)

**Purpose of This Document**:
The instructor will perform code review with respect to software design, error handling, format and style on the main branch every week starting Week 6 using the letter grade A standards.
The following chapters of the textbook are considered: Chapter 1, 2, 3, 4, 5, 6, 7, 9, and 10. The corresponding lectures are considered, too.

Please note that this feedback does not include evaluation of your progress, the proper use of linters, the quality of your test cases, or your compliance of TDD/BDD workflow.  
You can find the weekly feedback from your dedicated PM/TA for that.
## Week 10 Feedback
The following table contains the _Grading Rubrics for the Team_ that was released on Canvas in the beginning of the quarter (Canvas -> Project Resources -> Grading).
The instructor reviewed all the code in your main branch against every rubric item except "Product Completion."
If any item is not satisfied, your grade will not be penalized as long as your team corrects it between now and the final submission.

Note that the instructor may have granted some teams specific exceptions, which are not reflected in this feedback.
So don't worry if an item is marked "significant issue found" but you've been granted an exception for it.
Just make sure to document that exception in your README.md when you finalize your submission.

After this feedback, make sure any new code you add continues to satisfy the rubric items.

*How to read the rubrics: The evaluation starts with the criteria for F and progresses through D, C, B, and A. The grade advances to the next level if all criteria for the current grade are met or exceeded.*

Status:
- ✅: The rubric item is met or exceeded!
- ⚠️: Attention needed
- ❌: Significant issue found
- ➖: Not evaluated by the instructor in this feedback

| Category                   | Status | Item                                                                                                                                                                            | Notes                                                                                                                                                                                                   |
|----------------------------|--------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **F: Not Sufficient Work** |        |                                                                                                                                                                                 |                                                                                                                                                                                                         |
|                            | ✅      | The project does not meet all the criteria of D.                                                                                                                                |                                                                                                                                                                                                         |
| **D: Progressing**         |        |                                                                                                                                                                                 |                                                                                                                                                                                                         |
| **Product Completion**     | ➖      | **_Note: This review does not evaluate anything in the "Product Completion" category._**                                                                                        |                                                                                                                                                                                                         |
|                            | ➖      | If the game has multiple cards or characters, at least one type is fully implemented.                                                                                           |                                                                                                                                                                                                         |
|                            | ➖      | The game is playable.                                                                                                                                                           |                                                                                                                                                                                                         |
| **Product Quality**        |        |                                                                                                                                                                                 |                                                                                                                                                                                                         |
|                            | ✅      | The team has a "definition of done" (BVA) fully documented for the part of the system that is done.                                                                             |                                                                                                                                                                                                         |
|                            | ✅      | All the automated tests correctly reflect the BVA analysis, including but not limited to using the correct input values identified by BVA and using the appropriate assertions. |                                                                                                                                                                                                         |
|                            | ✅      | Automated testing may indicate some failures.                                                                                                                                   |                                                                                                                                                                                                         |
| **Process Quality**        |        |                                                                                                                                                                                 |                                                                                                                                                                                                         |
|                            | ✅      | The team uses the project management board very rarely.                                                                                                                         |                                                                                                                                                                                                         |
|                            | ✅      | GitHub repository branch protection rules are not fully set up so that people can push into main without a pull request approval.                                               |                                                                                                                                                                                                         |
|                            | ✅      | Continuous Integration (CI) is not fully set up or only set up in the last two weeks.                                                                                           |                                                                                                                                                                                                         |
| **C: Satisfactory**        |        |                                                                                                                                                                                 |                                                                                                                                                                                                         |
| **Product Completion**     | ➖      | **_Note: This review does not evaluate anything in the "Product Completion" category._**                                                                                        |                                                                                                                                                                                                         |
|                            | ➖      | If the game has multiple cards or characters, most of the types are fully implemented.                                                                                          |                                                                                                                                                                                                         |
|                            | ➖      | The win condition is implemented.                                                                                                                                               |                                                                                                                                                                                                         |
| **Product Quality**        |        |                                                                                                                                                                                 |                                                                                                                                                                                                         |
|                            | ✅      | All the automated tests pass.                                                                                                                                                   |                                                                                                                                                                                                         |
|                            | ⚠️      | GitHub commit history demonstrates evidence of a TDD/BDD workflow for all the non-UI code.                                                                                      | I noticed some early commits that contains more than 1 test. Just pointing it out and clarifying that TDD requires each commit to be 1 passing test. Please keep this mind when you finish up the project. |
|                            | ✅      | There is evidence that the team uses mutation testing and code coverage to improve the quality of the test cases, but not all the issues are solved.                            |                                                                                                                                                                                                         |
|                            | ✅      | The team has set up some code style standards, but not all the code satisfies the standard.                                                                                     |                                                                                                                                                                                                         |
| **Process Quality**        |        |                                                                                                                                                                                 |                                                                                                                                                                                                         |
|                            | ✅      | The team uses the project management board steadily and frequently, but the description of each task is very vague.                                                             |                                                                                                                                                                                                         |
|                            | ✅      | GitHub repository branch protection rules are fully set up so that people cannot push into main without a pull request approval.                                                |                                                                                                                                                                                                         |
|                            | ✅      | Continuous Integration (CI) is not fully set up or only set up in the last five weeks.                                                                                          |                                                                                                                                                                                                         |
|                            | ✅      | The team rarely documents each week's planning and progress.                                                                                                                    |                                                                                                                                                                                                         |
| **B: Good Work**           |        |                                                                                                                                                                                 |                                                                                                                                                                                                         |
| **Product Completion**     | ➖      | **_Note: This review does not evaluate anything in the "Product Completion" category._**                                                                                        |                                                                                                                                                                                                         |
|                            | ➖      | (Applicable only if the chosen game has more than one winning condition) All the winning conditions are implemented.                                                            |                                                                                                                                                                                                         |
|                            | ➖      | If the game has multiple cards or characters, all the types are fully implemented.                                                                                              |                                                                                                                                                                                                         |
| **Product Quality**        |        |                                                                                                                                                                                 |                                                                                                                                                                                                         |
|                            | ✅      | There is evidence that the team uses mutation testing and code coverage as soon as the concepts are introduced and there is no issue revealed by them.                          |                                                                                                                                                                                                         |
|                            | ✅      | 100% mutants should be killed (except for equivalent mutant).                                                                                                                   |                                                                                                                                                                                                         |
|                            | ✅      | 100% cyclomatic coverage for non-GUI and non-enum code.                                                                                                                         | I noticed some missing coverage but I think it's reasonable.                                                                                                                                            |
|                            | ✅      | The code fully satisfies the style standards the team set up.                                                                                                                   |                                                                                                                                                                                                         |
|                            | ✅      | There is progress on integration testing.                                                                                                                                       |                                                                                                                                                                                                         |
| **Process Quality**        |        |                                                                                                                                                                                 |                                                                                                                                                                                                         |
|                            | ✅      | The team uses the project management board steadily and frequently, and the description of each task is detailed.                                                               |                                                                                                                                                                                                         |
|                            | ✅      | Continuous Integration (CI) is fully set up from the beginning.                                                                                                                 |                                                                                                                                                                                                         |
|                            | ⚠️      | The team documents every week's planning and progress evaluation professionally.                                                                                                | I noticed thatFor week 6-8, the report only contains work done by 1 teammate. Is this correct? If not, please correct the record asap.                                                                  |
| **A: Excellent**           |        |                                                                                                                                                                                 |                                                                                                                                                                                                         |
| **Product Completion**     | ➖      | **_Note: This review does not evaluate anything in the "Product Completion" category._**                                                                                        |                                                                                                                                                                                                         |
|                            | ➖      | Support of locale: the software allows the user to choose between at least two languages at the beginning of the game.                                                          |                                                                                                                                                                                                         |
|                            | ➖      | The code supports easily adding new locales without changing existing code.                                                                                                     |                                                                                                                                                                                                         |
| **Product Quality**        |        |                                                                                                                                                                                 |                                                                                                                                                                                                         |
|                            |  ⚠️     | The code fully satisfied the code standards discussed in Clean Code.                                                                                                            | Game#reserveFaceUpCard, buyFaceUpCard, buyReservedCardm and ConsoleGame#run: function too big. Should be split into smaller ones.                                                                       |
|                            | ➖      | Integration testing is done on at least 2 main features.                                                                                                                        |                                                                                                                                                                                                         |

### Other comments
I would appreciate it if you can please include a link to the game's rule book in your README because I am not very familiar with the game :). Thank you!

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