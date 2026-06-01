# BVA Analysis: RuleValidator

### Method under test: `validatePlayerCount(int count, Locale locale)`

| ID  | State of the System                           | Expected output                                  | Implemented?       |
|-----|-----------------------------------------------|--------------------------------------------------|--------------------|
| TC1 | `count` is 2 (Lower Bound), `locale` is US    | `isSuccess()` is true, empty message             | :white_check_mark: |
| TC2 | `count` is 4 (Upper Bound), `locale` is US    | `isSuccess()` is true, empty message             | :white_check_mark: |
| TC3 | `count` is 1 (Below Bound), `locale` is US    | `isSuccess()` is false, returns US error message | :white_check_mark: |
| TC4 | `count` is 5 (Above Bound), `locale` is CHINA | `isSuccess()` is false, returns CN error message | :white_check_mark: |

---

### Method under test: `validateTakeTokens(Player player, TokenBank bank, Map<TokenColor, Integer> tokensToTake, Locale locale)`

| ID   | State of the System                                                                 | Expected output                                                    | Implemented?       |
|------|-------------------------------------------------------------------------------------|--------------------------------------------------------------------|--------------------|
| TC5  | Player has 0 tokens, bank has tokens, take 1 DIAMOND, 1 RUBY, and 1 ONYX            | `isSuccess()` is true                                              | :white_check_mark: |
| TC6  | Player has 0 tokens, bank has 4 DIAMOND, take 2 DIAMOND                             | `isSuccess()` is true                                              | :white_check_mark: |
| TC7  | Player has 0 tokens, take 1 GOLD                                                    | `isSuccess()` is false, returns US invalid token selection message | :white_check_mark: |
| TC8  | Player has 0 tokens, take 2 DIAMOND and 1 RUBY                                      | `isSuccess()` is false, returns US invalid token selection message | :white_check_mark: |
| TC9  | Player has 0 tokens, bank has only 3 DIAMOND, take 2 DIAMOND                        | `isSuccess()` is false, returns US invalid token selection message | :white_check_mark: |
| TC10 | Player has 0 tokens, bank has 0 DIAMOND, take 1 DIAMOND, 1 RUBY, and 1 ONYX         | `isSuccess()` is false, returns US invalid token selection message | :white_check_mark: |
| TC11 | Player has 8 tokens, bank has tokens, take 1 DIAMOND, 1 RUBY, and 1 ONYX            | `isSuccess()` is false, returns US invalid token selection message | :white_check_mark: |
| TC12 | Player has 9 tokens, bank has tokens, take 2 DIAMOND                                | `isSuccess()` is false, returns US invalid token selection message | :white_check_mark: |
| TC13 | Player has 0 tokens, bank has tokens, take empty token map                          | `isSuccess()` is false, returns US invalid token selection message | :white_check_mark: |
| TC14 | Player has 0 tokens, bank has tokens, take 1 DIAMOND and 1 RUBY                     | `isSuccess()` is false, returns US invalid token selection message | :white_check_mark: |
| TC15 | Player has 0 tokens, bank has tokens, take 1 DIAMOND, 1 RUBY, 1 ONYX, and 1 EMERALD | `isSuccess()` is false, returns US invalid token selection message | :white_check_mark: |

---

### Method under test: `validateReserveCard(Player player, List<Card> faceUpCards, int cardIndex, Locale locale)`

| ID   | State of the System                                                | Expected output                                                 | Implemented?       |
|------|--------------------------------------------------------------------|-----------------------------------------------------------------|--------------------|
| TC16 | Player has 0 reserved cards, face-up cards has 4 cards, index = 0  | `isSuccess()` is true                                           | :white_check_mark: |
| TC17 | Player has 2 reserved cards, face-up cards has 4 cards, index = 3  | `isSuccess()` is true                                           | :white_check_mark: |
| TC18 | Player has 3 reserved cards, face-up cards has 4 cards, index = 0  | `isSuccess()` is false, returns US invalid reserve card message | :white_check_mark: |
| TC19 | Player has 0 reserved cards, face-up cards is null, index = 0      | `isSuccess()` is false, returns US invalid reserve card message | :white_check_mark: |
| TC20 | Player has 0 reserved cards, face-up cards is empty, index = 0     | `isSuccess()` is false, returns US invalid reserve card message | :white_check_mark: |
| TC21 | Player has 0 reserved cards, face-up cards has 4 cards, index = -1 | `isSuccess()` is false, returns US invalid reserve card message | :white_check_mark: |
| TC22 | Player has 0 reserved cards, face-up cards has 4 cards, index = 4  | `isSuccess()` is false, returns US invalid reserve card message | :white_check_mark: |

---

### Method under test: `validateBuyCard(Player player, Card card, Locale locale)`

| ID   | State of the System                                                                                              | Expected output                                             | Implemented?       |
|------|------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------|--------------------|
| TC23 | Player has 0 tokens and 0 bonuses, card is null                                                                  | `isSuccess()` is false, returns US invalid buy card message | :white_check_mark: |
| TC24 | Player has 0 tokens and 0 bonuses, card costs empty map                                                          | `isSuccess()` is true                                       | :white_check_mark: |
| TC25 | Player has 1 DIAMOND token and 0 bonuses, card costs 1 DIAMOND                                                   | `isSuccess()` is true                                       | :white_check_mark: |
| TC26 | Player has 0 DIAMOND tokens and 1 DIAMOND bonus, card costs 1 DIAMOND                                            | `isSuccess()` is true                                       | :white_check_mark: |
| TC27 | Player has 0 DIAMOND tokens and 2 DIAMOND bonuses, card costs 1 DIAMOND                                          | `isSuccess()` is true                                       | :white_check_mark: |
| TC28 | Player has 0 DIAMOND tokens, 0 bonuses, and 1 GOLD token, card costs 1 DIAMOND                                   | `isSuccess()` is true                                       | :white_check_mark: |
| TC29 | Player has 1 DIAMOND token, 0 SAPPHIRE tokens, and 1 GOLD token, card costs 1 DIAMOND and 1 SAPPHIRE             | `isSuccess()` is true                                       | :white_check_mark: |
| TC30 | Player has 0 DIAMOND tokens, 0 bonuses, and 0 GOLD tokens, card costs 1 DIAMOND                                  | `isSuccess()` is false, returns US invalid buy card message | :white_check_mark: |
| TC31 | Player has 0 DIAMOND tokens, 0 bonuses, and 1 GOLD token, card costs 2 DIAMOND                                   | `isSuccess()` is false, returns US invalid buy card message | :white_check_mark: |
| TC32 | Player has 1 DIAMOND token, 1 DIAMOND bonus, and 1 GOLD token, card costs 3 DIAMOND                              | `isSuccess()` is true                                       | :white_check_mark: |
| TC33 | Player has 1 DIAMOND token, 1 DIAMOND bonus, and 0 GOLD tokens, card costs 3 DIAMOND                             | `isSuccess()` is false, returns US invalid buy card message | :white_check_mark: |
| TC34 | Player has 1 DIAMOND token, 1 SAPPHIRE token, and 0 bonuses, card costs 1 DIAMOND and 1 SAPPHIRE                 | `isSuccess()` is true                                       | :white_check_mark: |
| TC35 | Player has 1 DIAMOND token, 0 SAPPHIRE tokens, 0 bonuses, and 0 GOLD tokens, card costs 1 DIAMOND and 1 SAPPHIRE | `isSuccess()` is false, returns US invalid buy card message | :white_check_mark: |

---

### Method under test: `validateNobleVisit(Player player, Noble noble, Locale locale)`

| ID   | State of the System                                                              | Expected output                                                | Implemented?       |
|------|----------------------------------------------------------------------------------|----------------------------------------------------------------|--------------------|
| TC36 | Player has 3 DIAMOND bonuses, noble requires 3 DIAMOND                           | `isSuccess()` is true                                          | :white_check_mark: |
| TC37 | Player has 2 DIAMOND bonuses, noble requires 3 DIAMOND                           | `isSuccess()` is false, returns US invalid noble visit message | :white_check_mark: |
| TC38 | Player has 3 DIAMOND bonuses and 3 RUBY bonuses, noble requires both colors      | `isSuccess()` is true                                          | :white_check_mark: |
| TC39 | Player has 3 DIAMOND bonuses and 2 RUBY bonuses, noble requires 3 of both colors | `isSuccess()` is false, returns US invalid noble visit message | :white_check_mark: |
| TC40 | Player has 0 bonuses, noble requirements are empty                               | `isSuccess()` is true                                          | :white_check_mark: |
| TC41 | Player is null, noble requires 3 DIAMOND                                         | `isSuccess()` is false, returns US invalid noble visit message | :white_check_mark: |
| TC42 | Player has 3 DIAMOND bonuses, noble is null                                      | `isSuccess()` is false, returns US invalid noble visit message | :white_check_mark: |
| TC43 | Player has 3 DIAMOND bonuses, noble requirements are null                        | `isSuccess()` is false, returns US invalid noble visit message | :white_check_mark: |
