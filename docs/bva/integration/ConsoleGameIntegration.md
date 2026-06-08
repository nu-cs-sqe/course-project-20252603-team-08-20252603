# BVA Analysis - Console Game Integration

Integration tests for **console-driven gameplay** (`docs/requirements/game-rules.md` §Player Actions).  
These scenarios exercise multiple components together through `ConsoleGame.run()` with simulated stdin/stdout:

- `ConsoleGame`
- `Game`
- `RuleValidator`
- `MessageProvider`
- `TokenBank`
- `Player`

Unit-level BVA for individual classes lives in `docs/bva/Game.md`, `docs/bva/MessageProvider.md`, etc.  
This file covers **cross-layer** console gameplay behaviour only.

Test input is supplied as line-delimited text to `ConsoleGame(InputStream, PrintStream)`.

---

## Integration scenario: English locale take-token action through console

|                  | State of the System                                                                                                                                 | Expected output                                                                                                                                                                                                                         | Implemented? |
|------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------|
| Integration TC 1 | New `ConsoleGame` with stdin lines: `en`, `2`, `take RUBY DIAMOND ONYX`, `quit`; stdout captured                                                     | Console output contains `ui.game_started` (US locale), `ui.action_succeeded` (US locale), `ui.goodbye` (US locale), token bank line `RUBY: 3`, and next table shows current player as `Player 2` (turn advanced after valid take)      | :white_check_mark: |

---

## Integration scenario: Chinese locale reserve-card action through console

|                  | State of the System                                                                                                      | Expected output                                                                                                                                                                                                                  | Implemented? |
|------------------|--------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------|
| Integration TC 2 | New `ConsoleGame` with stdin lines: `zh`, `2`, `reserve 1 0`, `quit`; stdout captured                                  | Console output contains `ui.game_started`, `ui.action_succeeded`, `ui.reserved_cards`, and `ui.goodbye` using Simplified Chinese messages from `messages_zh_CN.properties`; does not contain the English `ui.game_started` text   | :white_check_mark: |

---

## Integration scenario: invalid player count rejected before game loop

|                  | State of the System                                                         | Expected output                                                                                                                                                                      | Implemented? |
|------------------|-----------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------|
| Integration TC 3 | New `ConsoleGame` with stdin lines: `en`, `1`; stdout captured              | Console output contains `error.invalid_player_count` (US locale); console output does **not** contain `ui.game_started` (US locale); `ConsoleGame.run()` returns without entering the action loop | :x:          |

---

## Integration scenario: unknown action handled without ending session

|                  | State of the System                                                                                    | Expected output                                                                                                                                                                                                 | Implemented? |
|------------------|--------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------|
| Integration TC 4 | New `ConsoleGame` with stdin lines: `en`, `2`, `not-a-command`, `quit`; stdout captured              | Console output contains `ui.game_started` (US locale), `ui.unknown_action` (US locale), and `ui.goodbye` (US locale); game table is printed before the unknown action prompt and before quit                     | :x:          |
