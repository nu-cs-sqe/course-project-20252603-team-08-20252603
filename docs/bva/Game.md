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
| Test Case 92 | Two separate `startGame(2, US)` calls on different Game objects                            | Level-1 draw order (first 4 face-up cards plus first remaining deck card) is not identical across both games | :white_check_mark: |

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

## Method: getWinner()

|              | State of the System                           | Expected output             | Implemented?       |
|--------------|-----------------------------------------------|-----------------------------|--------------------|
| Test Case 63 | New Game object                               | winner is null              | :white_check_mark: |
| Test Case 64 | After startGame(2), no player has won         | winner is null              | :white_check_mark: |
| Test Case 65 | After final round ends with player 0 winning  | winner is player 0          | :white_check_mark: |

---

## Method: getWinners()

|              | State of the System                                                  | Expected output                    | Implemented?       |
|--------------|----------------------------------------------------------------------|------------------------------------|--------------------|
| Test Case 71 | New Game object                                                      | winners list is empty              | :white_check_mark: |
| Test Case 72 | After startGame(2), no player has won                                | winners list is empty              | :white_check_mark: |
| Test Case 73 | After final round ends with one winner                               | winners list contains only winner  | :white_check_mark: |
| Test Case 74 | After final round ends with tied prestige and tied development count | winners list contains tied players | :white_check_mark: |

---

## Method: getCurrentPlayer()

|              | State of the System | Expected output                | Implemented?       |
|--------------|---------------------|--------------------------------|--------------------|
| Test Case 9  | After startGame(2)  | First player is current player | :white_check_mark: |
| Test Case 10 | After startGame(4)  | First player is current player | :white_check_mark: |

---

## Method: getCurrentPlayerIndex()

|              | State of the System | Expected output              | Implemented?       |
|--------------|---------------------|------------------------------|--------------------|
| Test Case 82 | After startGame(2, US) | `getCurrentPlayerIndex()` returns `0` | :white_check_mark: |
| Test Case 83 | After startGame(2, US) and player 0 completes one valid `takeTokens` | `getCurrentPlayerIndex()` returns `1` | :white_check_mark: |

---

## Method: getTokenBank()

|              | State of the System       | Expected output                                                           | Implemented?       |
|--------------|---------------------------|---------------------------------------------------------------------------|--------------------|
| Test Case 11 | Before startGame()        | getTokenBank() == null                                                    | :white_check_mark: |
| Test Case 12 | After startGame(2)        | getTokenBank().getTokenCount(DIAMOND) == 4                                | :white_check_mark: |
| Test Case 13 | After startGame(3)        | getTokenBank().getTokenCount(DIAMOND) == 5                                | :white_check_mark: |
| Test Case 14 | After startGame(4)        | getTokenBank().getTokenCount(DIAMOND) == 7                                | :white_check_mark: |
| Test Case 15 | After startGame(2)        | getTokenBank().getTokenCount(GOLD) == 5                                   | :white_check_mark: |
| Test Case 16 | After failed startGame(1) | getTokenBank() == null                                                    | :white_check_mark: |
| Test Case 48 | After startGame(2)        | Mutating getTokenBank() result does not change Game's internal token bank | :white_check_mark: |


## Method: takeTokens(Map<TokenColor, Integer> tokensToTake, Locale locale)

|              | State of the System                                                                      | Expected output                                                                                                                                      | Implemented?       |
|--------------|------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------|--------------------|
| Test Case 19 | After startGame(2), current player takes 1 DIAMOND, 1 RUBY, and 1 ONYX                   | Returns ActionResult (isSuccess=true), current player's tokens increase, token bank counts decrease, current player advances to player 1             | :white_check_mark: |
| Test Case 20 | After startGame(2), current player takes 2 DIAMOND                                       | Returns ActionResult (isSuccess=true), current player has 2 DIAMOND, token bank has 2 DIAMOND, current player advances to player 1                   | :white_check_mark: |
| Test Case 21 | After startGame(2), player 0 takes valid tokens and player 1 takes valid tokens          | Returns ActionResult (isSuccess=true) for both actions, current player wraps back to player 0                                                        | :white_check_mark: |
| Test Case 22 | After startGame(3), three players each take valid tokens                                 | Returns ActionResult (isSuccess=true) for all actions, current player wraps back to player 0                                                         | :white_check_mark: |
| Test Case 23 | After startGame(2), current player tries to take 1 GOLD                                  | Returns ActionResult (isSuccess=false) with invalid token selection message, player tokens unchanged, token bank unchanged, current player unchanged | :white_check_mark: |
| Test Case 24 | After startGame(2), current player tries to take 2 DIAMOND and 1 RUBY                    | Returns ActionResult (isSuccess=false) with invalid token selection message, player tokens unchanged, token bank unchanged, current player unchanged | :white_check_mark: |
| Test Case 25 | After startGame(2), current player tries to take empty token map                         | Returns ActionResult (isSuccess=false) with invalid token selection message, player tokens unchanged, token bank unchanged, current player unchanged | :white_check_mark: |
| Test Case 26 | After startGame(2), current player already has 9 tokens and tries to take 2 DIAMOND      | Returns ActionResult (isSuccess=false) with invalid token selection message, player tokens unchanged, token bank unchanged, current player unchanged | :white_check_mark: |
| Test Case 27 | New Game object before startGame(), takeTokens is called                                 | Returns ActionResult (isSuccess=false), phase remains SETUP                                                                                          | :white_check_mark: |
| Test Case 75 | During FINAL_ROUND, current player takes valid tokens and this completes the final round | Returns ActionResult (isSuccess=true), phase becomes GAME_OVER, winners are calculated, current player advances to final-round trigger player        | :white_check_mark: |
| Test Case 76 | During FINAL_ROUND, current player takes invalid tokens                                  | Returns ActionResult (isSuccess=false), phase remains FINAL_ROUND, winners list remains empty, current player unchanged                              | :white_check_mark: |
| Test Case 91 | During FINAL_ROUND, both players tied on prestige and development-card count, current player takes valid tokens completing the final round       | Returns ActionResult (isSuccess=true), phase becomes GAME_OVER, `getWinners().size() == 2`, current player advances to final-round trigger player | :white_check_mark: |
| Test Case 84 | After startGame(2, US), `ruleValidator` set to `null`, current player takes 1 DIAMOND, 1 RUBY, and 1 ONYX | Returns ActionResult (isSuccess=true), current player's tokens increase, token bank counts decrease, current player advances to player 1 | :white_check_mark: |

---

## Method: reserveFaceUpCard(int level, int cardIndex, Locale locale)

|              | State of the System                                                                                                              | Expected output                                                                                                                                                                                                                                                                         | Implemented?       |
|--------------|----------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------------|
| Test Case 34 | After startGame(2), current player reserves level 1 card at index 0                                                              | Returns ActionResult (isSuccess=true), current player's reserved cards increase, reserved card is original face-up card, level 1 face-up cards size stays 4, level 1 deck size decreases by 1, current player gets 1 GOLD, token bank loses 1 GOLD, current player advances to player 1 | :white_check_mark: |
| Test Case 35 | After startGame(2), five successful reserve actions have reduced token bank GOLD to 0, player 1 reserves level 1 card at index 0 | Returns ActionResult (isSuccess=true), player 1 reserved cards increase to 3, player 1 GOLD remains 2, token bank GOLD remains 0, current player advances to player 0                                                                                                                   | :white_check_mark: |
| Test Case 36 | After startGame(2), current player already has 3 reserved cards and reserves level 1 card at index 0                             | Returns ActionResult (isSuccess=false) with invalid reserve card message, reserved cards unchanged, face-up cards unchanged, token bank unchanged, current player unchanged                                                                                                             | :white_check_mark: |
| Test Case 37 | After startGame(2), current player reserves invalid level 4 card at index 0                                                      | Returns ActionResult (isSuccess=false) with invalid reserve card message, reserved cards unchanged, token bank unchanged, current player unchanged                                                                                                                                      | :white_check_mark: |
| Test Case 38 | After startGame(2), current player reserves level 1 card at invalid index 4                                                      | Returns ActionResult (isSuccess=false) with invalid reserve card message, reserved cards unchanged, face-up cards unchanged, token bank unchanged, current player unchanged                                                                                                             | :white_check_mark: |
| Test Case 39 | New Game object before startGame(), reserveFaceUpCard is called                                                                  | Returns ActionResult (isSuccess=false), phase remains SETUP                                                                                                                                                                                                                             | :white_check_mark: |
| Test Case 85 | After startGame(2, US), `ruleValidator` set to `null`, current player reserves level 1 card at index 0                         | Returns ActionResult (isSuccess=true), card reserved, market refilled, player gets 1 GOLD, current player advances to player 1                                                                                                                                                          | :white_check_mark: |

---

## Method: buyFaceUpCard(int level, int cardIndex, Locale locale)

|              | State of the System                                                                                                                                                                                            | Expected output                                                                                                                                                                                                                                | Implemented?       |
|--------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------------|
| Test Case 40 | After startGame(2), player 0 takes RUBY/DIAMOND/ONYX, player 1 takes SAPPHIRE/EMERALD/ONYX, then player 0 buys level 1 card costing 1 RUBY at index 0                                                          | Returns ActionResult (isSuccess=true), card moves from face-up cards to player 0 development cards, player 0 RUBY becomes 0, token bank RUBY becomes 4, prestige increases by card points, market refills, current player advances to player 1 | :white_check_mark: |
| Test Case 41 | After startGame(2), player 0 has one RUBY bonus, player 0 takes RUBY/DIAMOND/ONYX, player 1 takes SAPPHIRE/EMERALD/ONYX, then player 0 buys level 1 card costing 2 RUBY at index 0                             | Returns ActionResult (isSuccess=true), player spends only 1 RUBY after bonus, player 0 RUBY becomes 0, token bank RUBY becomes 4, bought card is added to development cards, current player advances to player 1                               | :white_check_mark: |
| Test Case 42 | After startGame(2), player 0 reserves a card to get GOLD, player 1 takes tokens, player 0 takes RUBY/DIAMOND/ONYX, player 1 takes tokens, then player 0 buys level 1 card costing RUBY and SAPPHIRE at index 0 | Returns ActionResult (isSuccess=true), player spends RUBY and GOLD, player 0 RUBY and GOLD become 0, token bank RUBY becomes 4, token bank GOLD becomes 5, bought card is added to development cards, current player advances to player 1      | :white_check_mark: |
| Test Case 43 | After startGame(2), current player buys level 1 card when deck is empty after removing face-up card                                                                                                            | Returns ActionResult (isSuccess=true), bought card is added to development cards, face-up cards size decreases by 1, no replacement card is added, current player advances                                                                     | :white_check_mark: |
| Test Case 44 | After startGame(2), current player cannot afford level 1 card at index 0                                                                                                                                       | Returns ActionResult (isSuccess=false) with invalid buy card message, player tokens unchanged, token bank unchanged, face-up cards unchanged, development cards unchanged, prestige unchanged, current player unchanged                        | :white_check_mark: |
| Test Case 45 | After startGame(2), player 0 takes RUBY/DIAMOND/ONYX, player 1 takes SAPPHIRE/EMERALD/ONYX, then player 0 buys invalid level 4 card at index 0                                                                 | Returns ActionResult (isSuccess=false) with invalid buy card message, player 0 keeps 1 RUBY, token bank RUBY remains 3, development cards unchanged, current player unchanged                                                                  | :white_check_mark: |
| Test Case 46 | After startGame(2), current player buys level 1 card at invalid index 4                                                                                                                                        | Returns ActionResult (isSuccess=false) with invalid buy card message, player tokens unchanged, token bank unchanged, face-up cards unchanged, development cards unchanged, current player unchanged                                            | :white_check_mark: |
| Test Case 47 | New Game object before startGame(), buyFaceUpCard is called                                                                                                                                                    | Returns ActionResult (isSuccess=false), phase remains SETUP                                                                                                                                                                                    | :white_check_mark: |
| Test Case 57 | After startGame(2), player 0 buys a face-up card and then satisfies one revealed noble                                                                                                                         | Returns ActionResult (isSuccess=true), bought card is added to development cards, satisfied noble moves from revealed nobles to player 0 nobles, prestige includes card and noble points, current player advances                              | :white_check_mark: |
| Test Case 58 | After startGame(2), player 0 buys a face-up card and does not satisfy any revealed noble                                                                                                                       | Returns ActionResult (isSuccess=true), bought card is added to development cards, player 0 nobles stay empty, revealed nobles unchanged, current player advances                                                                               | :white_check_mark: |
| Test Case 59 | After startGame(2), player 0 buys a face-up card and satisfies multiple revealed nobles                                                                                                                        | Returns ActionResult (isSuccess=true), only the first satisfied revealed noble moves to player 0 nobles, other satisfied nobles remain revealed, current player advances                                                                       | :white_check_mark: |
| Test Case 60 | After startGame(2), current player cannot afford a face-up card but would otherwise satisfy a revealed noble                                                                                                   | Returns ActionResult (isSuccess=false) with invalid buy card message, player 0 nobles stay empty, revealed nobles unchanged, current player unchanged                                                                                          | :white_check_mark: |
| Test Case 66 | After startGame(2), player 0 buys a face-up card and reaches exactly 15 prestige points                                                                                                                        | Returns ActionResult (isSuccess=true), phase becomes FINAL_ROUND, winners list remains empty, current player advances to player 1, market refills if possible                                                                                  | :white_check_mark: |
| Test Case 67 | After startGame(2), player 0 buys a face-up card and remains below 15 prestige points                                                                                                                          | Returns ActionResult (isSuccess=true), phase remains PLAYER_TURN, winner is null, current player advances                                                                                                                                      | :white_check_mark: |
| Test Case 68 | After game phase is GAME_OVER, current player tries to buy a face-up card                                                                                                                                      | Returns ActionResult (isSuccess=false) with invalid buy card message, phase remains GAME_OVER, winner unchanged                                                                                                                                | :white_check_mark: |
| Test Case 77 | During FINAL_ROUND, current player buys a face-up card and has lower prestige than final-round trigger player                                                                                                  | Returns ActionResult (isSuccess=true), phase becomes GAME_OVER, trigger player is the only winner                                                                                                                                              | :white_check_mark: |
| Test Case 78 | During FINAL_ROUND, current player buys a face-up card and has higher prestige than final-round trigger player                                                                                                 | Returns ActionResult (isSuccess=true), phase becomes GAME_OVER, current player is the only winner                                                                                                                                              | :white_check_mark: |
| Test Case 79 | During FINAL_ROUND, current player buys a face-up card and ties prestige with fewer development cards                                                                                                          | Returns ActionResult (isSuccess=true), phase becomes GAME_OVER, current player is the only winner                                                                                                                                              | :white_check_mark: |
| Test Case 80 | During FINAL_ROUND, current player buys a face-up card and ties prestige and development card count                                                                                                            | Returns ActionResult (isSuccess=true), phase becomes GAME_OVER, both tied players are winners                                                                                                                                                  | :white_check_mark: |
| Test Case 86 | After startGame(2, US), `ruleValidator` set to `null`, current player buys affordable level 1 card at index 0                                                                                                  | Returns ActionResult (isSuccess=true), card moves to development cards, market refills, current player advances to player 1                                                                                                                  | :white_check_mark: |
| Test Case 88 | After startGame(2, US), player 0 has 1 RUBY bonus, buys level 1 card costing 1 RUBY with no RUBY or GOLD tokens                                                                                                | Returns ActionResult (isSuccess=true), card bought, player and bank RUBY/GOLD unchanged, current player advances to player 1                                                                                                               | :white_check_mark: |
| Test Case 89 | After startGame(2, US), player 0 takes 2 RUBY, then buys level 1 card costing 2 RUBY with no bonus or GOLD                                                                                                     | Returns ActionResult (isSuccess=true), player spends exactly 2 RUBY, GOLD unchanged, card bought, current player advances to player 1                                                                                                      | :white_check_mark: |
| Test Case 90 | After startGame(2, US), player 0 has 0 RUBY and 1 GOLD from reserve, buys level 1 card costing 1 RUBY                                                                                                          | Returns ActionResult (isSuccess=true), player spends 1 GOLD only, bank RUBY unchanged, bank GOLD increases by 1, card bought, current player advances to player 1                                                                          | :white_check_mark: |
| Test Case 91 | CC-GAME-19: After startGame(2, US), current player buys level 1 card at index **−1**                                                                                                                            | Returns ActionResult (isSuccess=false) with invalid buy card message; face-up card unchanged; development cards unchanged; current player unchanged                                                                                          | :x: |
| Test Case 92 | CC-GAME-20: After startGame(2, US), reflection sets level-1 face-up market list to `null`, then buy at index 0                                                                                                  | Returns ActionResult (isSuccess=false) with invalid buy card message                                                                                                                                                                         | :x: |

---

## Method: buyReservedCard(int reservedIndex, Locale locale)

|              | State of the System                                                                                                                                                                                                  | Expected output                                                                                                                                                                                                                                        | Implemented?       |
|--------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------------|
| Test Case 49 | After startGame(2), player 0 reserves a level 1 card costing 1 RUBY, player 1 takes tokens, player 0 takes RUBY/DIAMOND/ONYX, player 1 takes tokens, then player 0 buys reserved card at index 0                     | Returns ActionResult (isSuccess=true), reserved card moves to player 0 development cards, reserved card list becomes empty, player 0 RUBY becomes 0, token bank RUBY becomes 4, prestige increases by card points, current player advances to player 1 | :white_check_mark: |
| Test Case 50 | After startGame(2), player 0 has one RUBY bonus, reserves a level 1 card costing 2 RUBY, player 1 takes tokens, player 0 takes RUBY/DIAMOND/ONYX, player 1 takes tokens, then player 0 buys reserved card at index 0 | Returns ActionResult (isSuccess=true), player spends only 1 RUBY after bonus, reserved card moves to development cards, reserved card list becomes empty, token bank RUBY becomes 4, current player advances to player 1                               | :white_check_mark: |
| Test Case 51 | After startGame(2), player 0 reserves a level 1 card costing RUBY and SAPPHIRE, player 1 takes tokens, player 0 takes RUBY/DIAMOND/ONYX, player 1 takes tokens, then player 0 buys reserved card at index 0          | Returns ActionResult (isSuccess=true), player spends RUBY and GOLD, player 0 RUBY and GOLD become 0, token bank RUBY becomes 4, token bank GOLD returns to 5, reserved card moves to development cards, current player advances to player 1            | :white_check_mark: |
| Test Case 52 | After startGame(2), player 0 has one reserved card costing 1 RUBY but no RUBY or GOLD tokens, then player 0 buys reserved card at index 0                                                                            | Returns ActionResult (isSuccess=false) with invalid buy card message, player tokens unchanged, token bank unchanged, reserved cards unchanged, development cards unchanged, prestige unchanged, current player unchanged                               | :white_check_mark: |
| Test Case 53 | After startGame(2), current player has no reserved cards and buys reserved card at index 0                                                                                                                           | Returns ActionResult (isSuccess=false) with invalid buy card message, player tokens unchanged, token bank unchanged, development cards unchanged, current player unchanged                                                                             | :white_check_mark: |
| Test Case 54 | After startGame(2), current player has one reserved card and buys reserved card at index -1                                                                                                                          | Returns ActionResult (isSuccess=false) with invalid buy card message, player tokens unchanged, token bank unchanged, reserved cards unchanged, development cards unchanged, current player unchanged                                                   | :white_check_mark: |
| Test Case 55 | After startGame(2), current player has one reserved card and buys reserved card at index 1                                                                                                                           | Returns ActionResult (isSuccess=false) with invalid buy card message, player tokens unchanged, token bank unchanged, reserved cards unchanged, development cards unchanged, current player unchanged                                                   | :white_check_mark: |
| Test Case 56 | New Game object before startGame(), buyReservedCard is called                                                                                                                                                        | Returns ActionResult (isSuccess=false), phase remains SETUP                                                                                                                                                                                            | :white_check_mark: |
| Test Case 61 | After startGame(2), player 0 buys a reserved card and then satisfies one revealed noble                                                                                                                              | Returns ActionResult (isSuccess=true), reserved card moves to development cards, satisfied noble moves from revealed nobles to player 0 nobles, prestige includes card and noble points, current player advances                                       | :white_check_mark: |
| Test Case 62 | After startGame(2), player 0 buys a reserved card and does not satisfy any revealed noble                                                                                                                            | Returns ActionResult (isSuccess=true), reserved card moves to development cards, player 0 nobles stay empty, revealed nobles unchanged, current player advances                                                                                        | :white_check_mark: |
| Test Case 69 | After startGame(2), player 0 buys a reserved card and reaches exactly 15 prestige points                                                                                                                             | Returns ActionResult (isSuccess=true), phase becomes FINAL_ROUND, winners list remains empty, current player advances to player 1                                                                                                                      | :white_check_mark: |
| Test Case 70 | After game phase is GAME_OVER, current player tries to buy a reserved card                                                                                                                                           | Returns ActionResult (isSuccess=false) with invalid buy card message, phase remains GAME_OVER, winner unchanged                                                                                                                                        | :white_check_mark: |
| Test Case 81 | During FINAL_ROUND, current player buys a reserved card and this completes the final round                                                                                                                           | Returns ActionResult (isSuccess=true), phase becomes GAME_OVER and winners are calculated                                                                                                                                                              | :white_check_mark: |
| Test Case 87 | After startGame(2, US), `ruleValidator` set to `null`, current player buys affordable reserved card at index 0                                                                                                       | Returns ActionResult (isSuccess=true), reserved card moves to development cards, reserved list empty, current player advances to player 1                                                                                                              | :white_check_mark: |
| Test Case 98 | CC-GAME-17: After startGame(2, US), player has one reserved card; reflection sets `players` to `null`; buyReservedCard(0)                                                                                          | Returns ActionResult (isSuccess=false) with invalid buy card message                                                                                                                                                                                    | :white_check_mark: |
| Test Case 99 | CC-GAME-18: After startGame(2, US), player has one reserved card; reflection sets `tokenBank` to `null`; buyReservedCard(0)                                                                                       | Returns ActionResult (isSuccess=false) with invalid buy card message                                                                                                                                                                                    | :white_check_mark: |

---

## Phase 2 coverage supplement (consolidated — see `docs/bva/coverage/CodeCoverageAndMutation.md`)

| ID | Summary | Implemented? |
|----|---------|--------------|
| Test Case 93 | CC-GAME-12: `@ParameterizedTest` — null-field guards for all actions (11 rows) | :white_check_mark: |
| Test Case 94 | CC-GAME-13: `@ParameterizedTest` — deck null (no refill) and `revealedNobles` null | :white_check_mark: |
| Test Case 95 | CC-GAME-14: `@ParameterizedTest` — 3-player `calculateWinners` single winner vs tie vs **fewest dev cards** (3 rows) | :white_check_mark: / :x: row 3 |
| Test Case 96 | CC-GAME-15: one multi-color buy killing `calculatePayment` boundary mutants | :white_check_mark: |
| Test Case 97 | CC-GAME-16: `@ParameterizedTest` — `startGame` when card/noble loader fails (needs test seam) | :white_check_mark: |

---

## Phase 3 coverage supplement (see `docs/bva/coverage/CodeCoverageAndMutation.md`)

| ID | Summary | Implemented? |
|----|---------|--------------|
| Test Case 91 | CC-GAME-19: `buyFaceUpCard` rejects negative card index | :x: |
| Test Case 92 | CC-GAME-20: `buyFaceUpCard` when level market list is `null` | :x: |
| Test Case 98 | CC-GAME-17: `buyReservedCard` when `players` is `null` | :white_check_mark: |
| Test Case 99 | CC-GAME-18: `buyReservedCard` when `tokenBank` is `null` | :white_check_mark: |
| Test Case 95† | CC-GAME-21: `calculateWinners_threePlayerFinalRound` — `FEWEST_DEV_CARDS_WINS` row | :x: |
| Test Case 100 | CC-GAME-22: `calculateWinners` clears stale winners (reflection + `ClearCountingList`) | :x: |
| Test Case 101 | CC-GAME-23: `calculatePayment` omits zero gem/gold map entries (reflection) | :x: |

† Third row of Test Case 95 / CC-GAME-14 parameterized test.
