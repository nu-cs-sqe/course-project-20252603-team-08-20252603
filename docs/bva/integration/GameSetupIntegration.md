# BVA Analysis - Game Setup Integration

Integration tests for the **Game Setup Phase** (design.txt §4.1).  
These scenarios exercise multiple components together through `Game.startGame(int playerCount, Locale locale)`:

- `Game`
- `RuleValidator`
- `CardLoader` (classpath `/cards/cards.json`)
- `NobleLoader` (classpath `/nobles/nobles.json`)
- `Deck`
- `TokenBank`
- `Player`

Unit-level BVA for individual classes lives in `docs/bva/Game.md`, `docs/bva/CardLoader.md`, etc.  
This file covers **cross-component** setup behaviour only.

---

## Integration scenario: successful full game setup

|                    | State of the System                                                                 | Expected output                                                                                                                                                                                                                                                                  | Implemented? |
|--------------------|-------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------|
| Integration TC 1   | New `Game`, `playerCount = 2`, `locale = US`, classpath card and noble JSON present | `ActionResult.isSuccess() == true`, `phase == PLAYER_TURN`, `players.size() == 2`, each player has 0 tokens / 0 development cards / 0 reserved cards / 0 prestige points, `getCurrentPlayer()` is player 0, gem token count per color == 4, gold token count == 5, 4 face-up cards per level (1–3), remaining deck sizes == 36 / 26 / 16, `getRevealedNobles().size() == 3`, each revealed noble has non-empty requirements and prestige points > 0, total cards in play == 90 | :white_check_mark: |

---

## Integration scenario: invalid player count blocks setup pipeline

|                    | State of the System                                      | Expected output                                                                                                                                                                                                                         | Implemented? |
|--------------------|----------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------|
| Integration TC 2   | New `Game`, `playerCount = 1`, `locale = US`             | `ActionResult.isSuccess() == false`, message == `error.invalid_player_count` for US locale, `phase == SETUP`, `getTokenBank() == null`, `getFaceUpCards(1) == null`, `getDeck(1) == null`, `getRevealedNobles() == null`              | :white_check_mark: |
| Integration TC 3   | New `Game`, `playerCount = 5`, `locale = US`             | `ActionResult.isSuccess() == false`, message == `error.invalid_player_count` for US locale, `phase == SETUP`, `getTokenBank() == null`, `getFaceUpCards(1) == null`, `getDeck(1) == null`, `getRevealedNobles() == null`              | :x:          |

---

## Integration scenario: player count scales token bank and noble supply

|                    | State of the System                                      | Expected output                                                                                                                                      | Implemented? |
|--------------------|----------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------|--------------|
| Integration TC 4   | Three separate new `Game` instances; `startGame(2, US)`, `startGame(3, US)`, `startGame(4, US)` each succeeds | 2-player game: gem token count per color == 4, `getRevealedNobles().size() == 3`; 3-player game: gem token count per color == 5, nobles == 4; 4-player game: gem token count per color == 7, nobles == 5 | :x:          |
