# BVA Analysis - Game

## Method: startGame(int playerCount, Locale locale)

|             | State of the System | Expected output                               | Implemented?       |
|-------------|---------------------|-----------------------------------------------|--------------------|
| Test Case 1 | playerCount = 1     | Throw IllegalArgumentException                | :white_check_mark: |
| Test Case 2 | playerCount = 2     | Game starts successfully, phase = PLAYER_TURN | :white_check_mark: |
| Test Case 3 | playerCount = 3     | Game starts successfully, phase = PLAYER_TURN | :white_check_mark: |
| Test Case 4 | playerCount = 4     | Game starts successfully, phase = PLAYER_TURN | :white_check_mark: |
| Test Case 5 | playerCount = 5     | Throw IllegalArgumentException                | :white_check_mark: |

---

## Method: getPlayers()

|              | State of the System | Expected output | Implemented? |
|--------------|---------------------|-----------------|--------------|
| Test Case 6  | After startGame(2)  | players.size() == 2 | :white_check_mark: |

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