# BVA Analysis - TokenBank

## Method: initialize(int playerCount)

|             | State of the System | Expected output                | Implemented?       |
|-------------|---------------------|--------------------------------|--------------------|
| Test Case 1 | playerCount = 1     | Throw IllegalArgumentException | :white_check_mark: |
| Test Case 2 | playerCount = 2     | 4 tokens for each gem color    | :white_check_mark: |
| Test Case 3 | playerCount = 3     | 5 tokens for each gem color    | :white_check_mark: |
| Test Case 4 | playerCount = 4     | 7 tokens for each gem color    | :white_check_mark: |
| Test Case 5 | playerCount = 5     | Throw IllegalArgumentException | :white_check_mark: |

---

## Method: getTokenCount(TokenColor color)

|             | State of the System                  | Expected output | Implemented?       |
|-------------|--------------------------------------|-----------------|--------------------|
| Test Case 6 | After initialize(2), color = DIAMOND | 4               | :white_check_mark: |
| Test Case 7 | After initialize(3), color = RUBY    | 5               | :white_check_mark: |
| Test Case 8 | After initialize(4), color = ONYX    | 7               | :white_check_mark: |
| Test Case 9 | After initialize(...), color = GOLD  | 5               | :white_check_mark: |

---

## Method: addTokens(Map<TokenColor, Integer> tokensToAdd)

|              | State of the System                           | Expected output                                 | Implemented? |
|--------------|-----------------------------------------------|-------------------------------------------------|--------------|
| Test Case 10 | After initialize(2), add 1 DIAMOND            | DIAMOND token count == 5                        | :x:          |
| Test Case 11 | After initialize(2), add 2 DIAMOND and 1 RUBY | DIAMOND token count == 6, RUBY token count == 5 | :x:          |
| Test Case 12 | After initialize(2), add 1 GOLD               | GOLD token count == 6                           | :x:          |
| Test Case 13 | After initialize(2), add empty token map      | Token counts remain unchanged                   | :x:          |

---

## Method: removeTokens(Map<TokenColor, Integer> tokensToRemove)

|              | State of the System                              | Expected output                                                 | Implemented? |
|--------------|--------------------------------------------------|-----------------------------------------------------------------|--------------|
| Test Case 14 | After initialize(2), remove 1 DIAMOND            | DIAMOND token count == 3                                        | :x:          |
| Test Case 15 | After initialize(2), remove 2 DIAMOND and 1 RUBY | DIAMOND token count == 2, RUBY token count == 3                 | :x:          |
| Test Case 16 | After initialize(2), remove 1 GOLD               | GOLD token count == 4                                           | :x:          |
| Test Case 17 | After initialize(2), remove empty token map      | Token counts remain unchanged                                   | :x:          |
| Test Case 18 | After initialize(2), remove 5 DIAMOND            | Throw IllegalArgumentException and leave token counts unchanged | :x:          |
| Test Case 19 | New TokenBank object, remove 1 DIAMOND           | Throw IllegalArgumentException and leave token counts unchanged | :x:          |
