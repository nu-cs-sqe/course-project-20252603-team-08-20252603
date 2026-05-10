# BVA Analysis - Game

## Method: startGame(int playerCount, Locale locale)

|              | State of the System          | Expected output                                             | Implemented?       |
|--------------|------------------------------|-------------------------------------------------------------|--------------------|
| Test Case 1  | playerCount = 1              | Throw IllegalArgumentException                              | :white_check_mark: |
| Test Case 2  | playerCount = 2              | Game starts successfully, phase = PLAYER_TURN               | :white_check_mark: |
| Test Case 3  | playerCount = 3              | Game starts successfully, phase = PLAYER_TURN               | :white_check_mark: |
| Test Case 4  | playerCount = 4              | Game starts successfully, phase = PLAYER_TURN               | :white_check_mark: |
| Test Case 5  | playerCount = 5              | Throw IllegalArgumentException                              | :white_check_mark: |
| Test Case 17 | playerCount = 3, locale = US | Returns ActionResult (isSuccess=true), phase = PLAYER_TURN  | :white_check_mark: |
| Test Case 18 | playerCount = 5, locale = US | Returns ActionResult (isSuccess=false), phase remains SETUP | :white_check_mark: |

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
