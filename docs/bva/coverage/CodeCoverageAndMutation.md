# BVA Analysis - Code Coverage & Mutation Testing (Domain)

This document plans the remaining test work needed to meet **Grade B** product-quality requirements from `grading.txt`:

- **100% cyclomatic coverage** for non-GUI and non-enum code (JaCoCo **branch coverage** is the project’s practical measure).
- **100% mutants killed** in domain code (PIT), except equivalent mutants.

It is a **coverage-gap supplement**, not a replacement for per-class BVA files in `docs/bva/`.

---

## How this BVA relates to existing files

| Document type | Purpose | Example |
|---------------|---------|---------|
| `docs/bva/<Class>.md` | Required BVA for each class / public method (project rule) | `Game.md`, `Deck.md` |
| `docs/bva/integration/*.md` | Cross-component scenarios | `GameSetupIntegration.md` |
| **`docs/bva/coverage/CodeCoverageAndMutation.md`** | Gap analysis from JaCoCo/PIT → new test cases to close B-grade gaps | this file |

**Workflow (BVA-first, same as integration):**

1. Add test case here with `:x:`.
2. When implementing, **also add the same case** to the matching per-class BVA file (e.g. `Game.md` Test Case 82).
3. Write the unit test in the matching `*Test.java`.
4. Run `./gradlew test jacocoTestReport pitest` and confirm domain branch + mutation reach 100%.
5. Mark `:white_check_mark:` here **and** in the per-class BVA file.

**Out of scope for cyclomatic-coverage grading target:** `ui.*`, `TokenColor`, `GamePhase` (enum).  
Integration tests in `integration.*` help UI JaCoCo but do **not** replace domain unit tests for mutation killing.

---

## Baseline (before this branch’s new tests)

Measured with `./gradlew clean test jacocoTestReport pitest` on `main`:

| Scope | JaCoCo branch | PIT mutation |
|-------|---------------|--------------|
| **domain (non-enum)** | **89%** (target **100%**) | **92%** (target **100%**) |
| ui | 60% | 0% (not graded for cyclomatic) |
| whole project | 83% | 67% |

### Classes still below 100% (domain, non-enum)

| Class | JaCoCo branch | PIT mutation | Primary gap source |
|-------|---------------|--------------|-------------------|
| `Game` | 82% | 91% | `getCurrentPlayerIndex`, lazy `ruleValidator` init, `calculatePayment` boundaries, `calculateWinners`, `initializeCards` shuffle |
| `RuleValidator` | 96% | 93% | boundary branches in `validateTakeTokens` / `validateBuyCard` |
| `MessageProvider` | n/a | 100% mut / 80% line | `MissingResourceException` fallback path |
| `Deck` | 100% branch | 80% mutation | `shuffle()` call effect not asserted |

---

## Integration scenario: `Game.getCurrentPlayerIndex()`

| ID | Method(s) under test | State of the System | Expected output | Implemented? |
|----|----------------------|---------------------|-----------------|--------------|
| CC-GAME-01 | `getCurrentPlayerIndex()` | After `startGame(2, US)` | Returns `0` | :white_check_mark: |
| CC-GAME-02 | `getCurrentPlayerIndex()` | After `startGame(2, US)` and player 0 completes one valid `takeTokens` | Returns `1` | :white_check_mark: |

*Also add to `docs/bva/Game.md` when implemented.*

---

## Integration scenario: `Game` lazy `ruleValidator` initialization

PIT reports `initializeRuleValidator()` as **NO_COVERAGE** in `takeTokens`, `reserveFaceUpCard`, `buyFaceUpCard`, and `buyReservedCard` when `ruleValidator == null`.

| ID | Method(s) under test | State of the System | Expected output | Implemented? |
|----|----------------------|---------------------|-----------------|--------------|
| CC-GAME-03 | `takeTokens(...)` | `Game` after `startGame(2, US)`; `ruleValidator` field set to `null` via test-only access; valid token take for current player | `ActionResult.isSuccess() == true`, tokens and turn advance as in existing take-token success cases | :white_check_mark: |
| CC-GAME-04 | `reserveFaceUpCard(...)` | Same `ruleValidator == null` setup; valid reserve at level 1 index 0 | `ActionResult.isSuccess() == true`, card reserved and turn advances | :white_check_mark: |
| CC-GAME-05 | `buyFaceUpCard(...)` | Same setup; player can afford face-up card at level 1 index 0 | `ActionResult.isSuccess() == true`, card bought and turn advances | :white_check_mark: |
| CC-GAME-06 | `buyReservedCard(...)` | Same setup; player has affordable reserved card at index 0 | `ActionResult.isSuccess() == true`, reserved card bought and turn advances | :white_check_mark: |

*Also add to `docs/bva/Game.md` when implemented.*

---

## Integration scenario: `Game.calculatePayment(...)` boundary branches

PIT **SURVIVED** `changed conditional boundary` mutants in `calculatePayment`.

| ID | Method(s) under test | State of the System | Expected output | Implemented? |
|----|----------------------|---------------------|-----------------|--------------|
| CC-GAME-07 | `buyFaceUpCard` → `calculatePayment` | Player bonus **equals** full gem cost for one color (`remainingCost == 0`); no gems or gold spent for that color | Purchase succeeds; payment map has no entry for that color | :white_check_mark: |
| CC-GAME-08 | `buyFaceUpCard` → `calculatePayment` | Player has **exactly** enough gem tokens for remaining cost (`gemTokensUsed == remainingCost`); no gold used | Purchase succeeds; gold not in payment map | :white_check_mark: |
| CC-GAME-09 | `buyFaceUpCard` → `calculatePayment` | Player has **zero** gem tokens for a color; entire remaining cost paid with gold | Purchase succeeds; payment includes `GOLD` for full shortfall | :white_check_mark: |

*Also add to `docs/bva/Game.md` when implemented.*

---

## Integration scenario: `Game.calculateWinners()` and `initializeCards()`

| ID | Method(s) under test | State of the System | Expected output | Implemented? |
|----|----------------------|---------------------|-----------------|--------------|
| CC-GAME-10 | `calculateWinners()` via final round | 2-player game; after final round both players tied on prestige **and** development-card count | `getWinners().size() == 2` | :white_check_mark: |
| CC-GAME-11 | `initializeCards()` via `startGame` | Two separate `startGame(2, US)` calls | Level-1 deck draw order (first 4 face-up + first deck card) is **not identical** across both games (proves `shuffle()` ran) | :white_check_mark: |

*Also add to `docs/bva/Game.md` when implemented.*

---

## Integration scenario: `Deck.shuffle()`

| ID | Method(s) under test | State of the System | Expected output | Implemented? |
|----|----------------------|---------------------|-----------------|--------------|
| CC-DECK-01 | `shuffle()` | Deck with ≥ 2 distinct cards; record order before shuffle | After `shuffle()`, card list order differs from pre-shuffle order | :white_check_mark: |

*Also add to `docs/bva/Deck.md` when implemented.*

---

## Integration scenario: `MessageProvider.getMessage(...)`

Existing `MessageProvider.md` TC3 covers missing **key**; JaCoCo still shows 80% line coverage. Add case for missing **bundle** (locale with no properties file).

| ID | Method(s) under test | State of the System | Expected output | Implemented? |
|----|----------------------|---------------------|-----------------|--------------|
| CC-MSG-01 | `getMessage(String key, Locale locale)` | `key = "ui.game_started"`, `locale = Locale.FRENCH` (no `messages_fr_FR.properties`) | Returns `"ui.game_started"` (fallback `key`) | :white_check_mark: |

*Also add to `docs/bva/MessageProvider.md` as TC4 when implemented.*

---

## Integration scenario: `RuleValidator` boundary branches

PIT mutation coverage 93% (4 surviving mutants). Target boundary values not yet asserted.

| ID | Method(s) under test | State of the System | Expected output | Implemented? |
|----|----------------------|---------------------|-----------------|--------------|
| CC-RV-01 | `validateTakeTokens(...)` | Bank has **exactly 4** of one color; take **2** of that color | `isSuccess() == true` | :white_check_mark: |
| CC-RV-02 | `validateTakeTokens(...)` | Bank has **3** of one color; take **2** of that color | `isSuccess() == false` | :white_check_mark: |
| CC-RV-03 | `validateTakeTokens(...)` | Map contains color with count **0** | `isSuccess() == false` | :white_check_mark: |
| CC-RV-04 | `validateBuyCard(...)` | `goldNeeded == player GOLD count` exactly (affordable edge) | `isSuccess() == true` | :white_check_mark: |
| CC-RV-05 | `validateBuyCard(...)` | `goldNeeded == player GOLD count + 1` (not affordable) | `isSuccess() == false` | :white_check_mark: |

*Also add to `docs/bva/RuleValidator.md` when implemented.*

---

## Phase 2 — Remaining gaps to **100%** (consolidated)

Phase 1 (CC-GAME-01 … CC-RV-05) is complete. Verification on 2026-06-11 still reports **domain branch 92%** and **domain mutation 97%**.

**Design principle:** one BVA row can map to one **`@ParameterizedTest`** (many rows) or one test method with multiple assertions — not one JUnit method per branch.  
Target: **8 new test methods** (7 required + 1 optional) instead of 25.

| Phase | BVA cases | Planned test methods | Status |
|-------|-----------|----------------------|--------|
| Phase 1 | 18 | 18+ | :white_check_mark: |
| **Phase 2** | **CC-GAME-12 … 16, CC-RV-06 … 07, CC-MSG-02 (opt.)** | **~8** | :x: **4/7** required |

### Phase 2 → gap mapping (why this is enough)

| Remaining gap (JaCoCo / PIT) | Covered by |
|------------------------------|------------|
| `takeTokens` / `validate*State` null operands (16 branches) | CC-GAME-12 parameterized rows |
| `buyFaceUpCard` level 4 → `cards == null` | CC-GAME-12 row |
| `replenishFaceUpCard` deck null; `visitAvailableNoble` revealedNobles null | CC-GAME-13 (2 rows) |
| `calculateWinners` tie branch + `winners.clear()` mutant | CC-GAME-14 (2 rows) |
| `calculatePayment` 3 boundary mutants | CC-GAME-15 one multi-color buy |
| `initializeCards` / `initializeNobles` catch | CC-GAME-16 (2 rows; needs test seam) |
| `RuleValidator` 4 boundary mutants | CC-RV-06 (3 rows) + CC-RV-07 |
| `MessageProvider` constructor line (optional) | CC-MSG-02 — mutation already 100% |

---

## Phase 2: `Game` — CC-GAME-12 … 16

| ID | Test method (planned) | State of the System (summary) | Expected output | Implemented? |
|----|----------------------|-------------------------------|-----------------|--------------|
| CC-GAME-12 | `gameAction_failsWhenRequiredFieldIsNull` **`@ParameterizedTest`** | After `startGame(2, US)`; reflection sets one field to `null` per row: `players`, `tokenBank`, `faceUpCards`, `decks` × action (`takeTokens`, `reserveFaceUpCard`, `buyFaceUpCard`, `buyReservedCard`); plus row: `buyFaceUpCard(4, 0)` | Each row: `isSuccess() == false` with matching error message | :white_check_mark: |
| CC-GAME-13 | `buyFaceUpCard_skipsReplenishOrNobleWhenTargetIsNull` **`@ParameterizedTest`** | (1) level-1 deck set to `null`, affordable buy; (2) `revealedNobles` set to `null`, affordable buy | (1) buy succeeds, market size −1; (2) buy succeeds, nobles unchanged | :white_check_mark: |
| CC-GAME-14 | `calculateWinners_threePlayerFinalRound` **`@ParameterizedTest`** | 3-player `startGame`; final-round setup per row: (1) one player strictly highest prestige; (2) two players tied on prestige **and** dev-card count | (1) `getWinners().size() == 1`; (2) `getWinners().size() == 2` | :white_check_mark: |
| CC-GAME-15 | `buyFaceUpCard_multiColorPurchaseKillsCalculatePaymentBoundaryMutants` | Card costs 1 RUBY + 2 SAPPHIRE; player has 1 RUBY **bonus**, 0 RUBY gems, 1 SAPPHIRE gem, 1 GOLD | Buy succeeds; spends 1 SAPPHIRE + 1 GOLD only; ruby bank unchanged | :white_check_mark: |
| CC-GAME-16 | `startGame_throwsWhenResourceLoaderFails` **`@ParameterizedTest`** | Test seam makes card or noble loader throw | `IllegalStateException` (*Unable to initialize cards/nobles.*) | :x: |

*Also add to `docs/bva/Game.md` as Test Cases 93–97 when implemented.*

**CC-GAME-12 row count:** 11 parameterized rows (same coverage as former CC-GAME-12 … 22).  
**CC-GAME-16:** requires a **minimal test seam** on `Game` (injectable `CardLoader` / `NobleLoader`); without it, catch blocks stay uncovered.

---

## Phase 2: `RuleValidator` — CC-RV-06 … 07

| ID | Test method (planned) | State of the System (summary) | Expected output | Implemented? |
|----|----------------------|-------------------------------|-----------------|--------------|
| CC-RV-06 | `validateTakeTokens_boundaryTokenRules` **`@ParameterizedTest`** | (1) bank **1** DIAMOND, take 1D+1R+1O; (2) player **7** tokens, take 3 gems; (3) map contains count **−1** | (1)(2) `isSuccess() == true`; (3) `isSuccess() == false` | :x: |
| CC-RV-07 | `validateBuyCard_succeedsWhenBonusCoversEntireColorCost` | Card costs 1 DIAMOND; player has 1 DIAMOND bonus, 0 gems, 0 gold | `isSuccess() == true` | :x: |

*Also add to `docs/bva/RuleValidator.md` as TC49–TC50 when implemented.*

---

## Phase 2: `MessageProvider` — CC-MSG-02 (optional)

| ID | Test method (planned) | State of the System | Expected output | Implemented? |
|----|----------------------|---------------------|-----------------|--------------|
| CC-MSG-02 | `messageProvider_defaultConstructor` | `new MessageProvider()` | Instance created | :x: |

PIT mutation is already **100%** for `MessageProvider`; include only if line coverage is graded. Otherwise skip.

*Also add to `docs/bva/MessageProvider.md` as TC5 when implemented.*

---

## Verification checklist (run after all cases implemented)

```bash
./gradlew clean test jacocoTestReport pitest
```

| Check | Target | Report | Result |
|-------|--------|--------|--------|
| domain branch coverage | **100%** | `build/reports/jacoco/domain/index.html` | **92%** after Phase 1 — Phase 2 pending |
| domain PIT mutation | **100%** killed | `build/reports/pitest/domain/index.html` | **97%** after Phase 1 — Phase 2 pending |
| all tests pass | yes | `./gradlew test` | :white_check_mark: (Phase 1) |

### Post–Phase 1 verification (2026-06-11)

| Scope | JaCoCo branch | PIT mutation |
|-------|---------------|--------------|
| **domain (package total)** | **92%** (was 89%) | **97%** (was 92%) |
| ui | 60% | 0% (not graded for cyclomatic) |
| whole project (PIT incl. ui) | — | 72% |

### Classes still below 100% (Phase 2 targets)

| Class | JaCoCo branch | PIT mutation | Phase 2 case IDs |
|-------|---------------|--------------|------------------|
| `Game` | 88% | 97% (155/159) | CC-GAME-12 … 16 |
| `RuleValidator` | 98% | 93% (56/60) | CC-RV-06 … 07 |
| `MessageProvider` | n/a (80% line) | 100% (2/2) | CC-MSG-02 (optional) |
| `Deck` | 100% | 100% (5/5) | :white_check_mark: (done) |

**Run verification again after Phase 2 (18 Phase 1 + 7 required Phase 2 BVA cases, ~8 test methods):**

```bash
./gradlew clean test jacocoTestReport pitest
```

**Optional build fix (separate from test cases):** `build.gradle.kts` sets `targetClasses = domain.*, ui.*` but `targetTests = domain.*` only. Consider aligning PIT config so UI mutants do not deflate the project summary, or add UI tests to `targetTests`.
