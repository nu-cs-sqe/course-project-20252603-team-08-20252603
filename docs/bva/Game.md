# BVA Analysis - Game

## Method: startGame(int playerCount, Locale locale)

|              | State of the System          | Expected output                                                                             | Implemented?       |
|--------------|------------------------------|---------------------------------------------------------------------------------------------|--------------------|
| Test Case 1  | playerCount = 1              | Throw IllegalArgumentException                                                              | :white_check_mark: |
| Test Case 2  | playerCount = 2              | Game starts successfully, phase = PLAYER_TURN                                               | :white_check_mark: |
| Test Case 3  | playerCount = 3              | Game starts successfully, phase = PLAYER_TURN                                               | :white_check_mark: |
| Test Case 4  | playerCount = 4              | Game starts successfully, phase = PLAYER_TURN                                               | :white_check_mark: |
| Test Case 5  | playerCount = 5              | Throw IllegalArgumentException                                                              | :white_check_mark: |
| Test Case 17 | playerCount = 3, locale = US | Returns ActionResult (isSuccess=true), phase = PLAYER_TURN                                  | :white_check_mark: |
| Test Case 18 | playerCount = 5, locale = US | Returns ActionResult (isSuccess=false), phase remains SETUP                                 | :white_check_mark: |
| Test Case 28 | After startGame(2)           | getFaceUpCards(1).size() == 4, getFaceUpCards(2).size() == 4, getFaceUpCards(3).size() == 4 | :white_check_mark: |
| Test Case 29 | After startGame(2)           | getDeck(1).cards.size() == 36, getDeck(2).cards.size() == 26, getDeck(3).cards.size() == 16 | :white_check_mark: |
| Test Case 30 | After startGame(2)           | getRevealedNobles().size() == 3                                                             | :white_check_mark: |
| Test Case 31 | After startGame(3)           | getRevealedNobles().size() == 4                                                             | :white_check_mark: |
| Test Case 32 | After startGame(4)           | getRevealedNobles().size() == 5                                                             | :white_check_mark: |
| Test Case 33 | After failed startGame(1)    | getFaceUpCards(1), getDeck(1), and getRevealedNobles() are null                             | :white_check_mark: |

---

## Method: getPlayers()

|             | State of the System | Expected output     | Implemented?       |
|-------------|---------------------|---------------------|--------------------|
| Test Case 6 | After startGame(2)  | players.size() == 2 | :white_check_mark: |

---

## Method: getPhase()

|             | State of the System  | Expected output     | Implemented?       |
|-------------|----------------------|---------------------|--------------------|
| Test Case 7 | New Game object      | phase = SETUP       | :white_check_mark: |
| Test Case 8 | After startGame(...) | phase = PLAYER_TURN | :white_check_mark: |

---

## Method: getCurrentPlayer()

|              | State of the System | Expected output                | Implemented?       |
|--------------|---------------------|--------------------------------|--------------------|
| Test Case 9  | After startGame(2)  | First player is current player | :white_check_mark: |
| Test Case 10 | After startGame(4)  | First player is current player | :white_check_mark: |


## Method: getTokenBank()

|              | State of the System       | Expected output                            | Implemented?       |
|--------------|---------------------------|--------------------------------------------|--------------------|
| Test Case 11 | Before startGame()        | getTokenBank() == null                     | :white_check_mark: |
| Test Case 12 | After startGame(2)        | getTokenBank().getTokenCount(DIAMOND) == 4 | :white_check_mark: |
| Test Case 13 | After startGame(3)        | getTokenBank().getTokenCount(DIAMOND) == 5 | :white_check_mark: |
| Test Case 14 | After startGame(4)        | getTokenBank().getTokenCount(DIAMOND) == 7 | :white_check_mark: |
| Test Case 15 | After startGame(2)        | getTokenBank().getTokenCount(GOLD) == 5    | :white_check_mark: |
| Test Case 16 | After failed startGame(1) | getTokenBank() == null                     | :white_check_mark: |


## Method: takeTokens(Map<TokenColor, Integer> tokensToTake, Locale locale)

|              | State of the System                                                                 | Expected output                                                                                                                                      | Implemented?       |
|--------------|-------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------|--------------------|
| Test Case 19 | After startGame(2), current player takes 1 DIAMOND, 1 RUBY, and 1 ONYX              | Returns ActionResult (isSuccess=true), current player's tokens increase, token bank counts decrease, current player advances to player 1             | :white_check_mark: |
| Test Case 20 | After startGame(2), current player takes 2 DIAMOND                                  | Returns ActionResult (isSuccess=true), current player has 2 DIAMOND, token bank has 2 DIAMOND, current player advances to player 1                   | :white_check_mark: |
| Test Case 21 | After startGame(2), player 0 takes valid tokens and player 1 takes valid tokens     | Returns ActionResult (isSuccess=true) for both actions, current player wraps back to player 0                                                        | :white_check_mark: |
| Test Case 22 | After startGame(3), three players each take valid tokens                            | Returns ActionResult (isSuccess=true) for all actions, current player wraps back to player 0                                                         | :white_check_mark: |
| Test Case 23 | After startGame(2), current player tries to take 1 GOLD                             | Returns ActionResult (isSuccess=false) with invalid token selection message, player tokens unchanged, token bank unchanged, current player unchanged | :white_check_mark: |
| Test Case 24 | After startGame(2), current player tries to take 2 DIAMOND and 1 RUBY               | Returns ActionResult (isSuccess=false) with invalid token selection message, player tokens unchanged, token bank unchanged, current player unchanged | :white_check_mark: |
| Test Case 25 | After startGame(2), current player tries to take empty token map                    | Returns ActionResult (isSuccess=false) with invalid token selection message, player tokens unchanged, token bank unchanged, current player unchanged | :white_check_mark: |
| Test Case 26 | After startGame(2), current player already has 9 tokens and tries to take 2 DIAMOND | Returns ActionResult (isSuccess=false) with invalid token selection message, player tokens unchanged, token bank unchanged, current player unchanged | :white_check_mark: |
| Test Case 27 | New Game object before startGame(), takeTokens is called                            | Returns ActionResult (isSuccess=false), phase remains SETUP                                                                                          | :white_check_mark: |

---

## Method: reserveFaceUpCard(int level, int cardIndex, Locale locale)

|              | State of the System                                                                                  | Expected output                                                                                                                                                                                                                                                                         | Implemented?       |
|--------------|------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------------|
| Test Case 34 | After startGame(2), current player reserves level 1 card at index 0                                  | Returns ActionResult (isSuccess=true), current player's reserved cards increase, reserved card is original face-up card, level 1 face-up cards size stays 4, level 1 deck size decreases by 1, current player gets 1 GOLD, token bank loses 1 GOLD, current player advances to player 1 | :white_check_mark: |
| Test Case 35 | After startGame(2), token bank has 0 GOLD, current player reserves level 1 card at index 0           | Returns ActionResult (isSuccess=true), current player's reserved cards increase, current player gets no GOLD, token bank has 0 GOLD, current player advances to player 1                                                                                                                | :white_check_mark: |
| Test Case 36 | After startGame(2), current player already has 3 reserved cards and reserves level 1 card at index 0 | Returns ActionResult (isSuccess=false) with invalid reserve card message, reserved cards unchanged, face-up cards unchanged, token bank unchanged, current player unchanged                                                                                                             | :white_check_mark: |
| Test Case 37 | After startGame(2), current player reserves invalid level 4 card at index 0                          | Returns ActionResult (isSuccess=false) with invalid reserve card message, reserved cards unchanged, token bank unchanged, current player unchanged                                                                                                                                      | :white_check_mark: |
| Test Case 38 | After startGame(2), current player reserves level 1 card at invalid index 4                          | Returns ActionResult (isSuccess=false) with invalid reserve card message, reserved cards unchanged, face-up cards unchanged, token bank unchanged, current player unchanged                                                                                                             | :white_check_mark: |
| Test Case 39 | New Game object before startGame(), reserveFaceUpCard is called                                      | Returns ActionResult (isSuccess=false), phase remains SETUP                                                                                                                                                                                                                             | :white_check_mark: |
