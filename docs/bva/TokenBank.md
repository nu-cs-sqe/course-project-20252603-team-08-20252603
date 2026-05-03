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