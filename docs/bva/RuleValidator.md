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

| ID   | State of the System                                                                 | Expected output                                                     | Implemented? |
|------|-------------------------------------------------------------------------------------|---------------------------------------------------------------------|--------------|
| TC5  | Player has 0 tokens, bank has tokens, take 1 DIAMOND, 1 RUBY, and 1 ONYX            | `isSuccess()` is true                                               | :x:          |
| TC6  | Player has 0 tokens, bank has 4 DIAMOND, take 2 DIAMOND                             | `isSuccess()` is true                                               | :x:          |
| TC7  | Player has 0 tokens, take 1 GOLD                                                    | `isSuccess()` is false, returns US invalid token selection message  | :x:          |
| TC8  | Player has 0 tokens, take 2 DIAMOND and 1 RUBY                                      | `isSuccess()` is false, returns US invalid token selection message  | :x:          |
| TC9  | Player has 0 tokens, bank has only 3 DIAMOND, take 2 DIAMOND                        | `isSuccess()` is false, returns US invalid token selection message  | :x:          |
| TC10 | Player has 0 tokens, bank has 0 DIAMOND, take 1 DIAMOND, 1 RUBY, and 1 ONYX         | `isSuccess()` is false, returns US invalid token selection message  | :x:          |
| TC11 | Player has 8 tokens, bank has tokens, take 1 DIAMOND, 1 RUBY, and 1 ONYX            | `isSuccess()` is false, returns US invalid token selection message  | :x:          |
| TC12 | Player has 9 tokens, bank has tokens, take 2 DIAMOND                                | `isSuccess()` is false, returns US invalid token selection message  | :x:          |
| TC13 | Player has 0 tokens, bank has tokens, take empty token map                          | `isSuccess()` is false, returns US invalid token selection message  | :x:          |
| TC14 | Player has 0 tokens, bank has tokens, take 1 DIAMOND and 1 RUBY                     | `isSuccess()` is false, returns US invalid token selection message  | :x:          |
| TC15 | Player has 0 tokens, bank has tokens, take 1 DIAMOND, 1 RUBY, 1 ONYX, and 1 EMERALD | `isSuccess()` is false, returns US invalid token selection message  | :x:          |
