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
| **Phase 2** | **CC-GAME-12 … 16, CC-RV-06 … 07, CC-MSG-02 (opt.)** | **~8** | :white_check_mark: **7/7** required |

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
| CC-GAME-16 | `startGame_throwsWhenResourceLoaderFails` **`@ParameterizedTest`** | Test seam makes card or noble loader throw | `IllegalStateException` (*Unable to initialize cards/nobles.*) | :white_check_mark: |

*Also add to `docs/bva/Game.md` as Test Cases 93–97 when implemented.*

**CC-GAME-12 row count:** 11 parameterized rows (same coverage as former CC-GAME-12 … 22).  
**CC-GAME-16:** requires a **minimal test seam** on `Game` (injectable `CardLoader` / `NobleLoader`); without it, catch blocks stay uncovered.

---

## Phase 2: `RuleValidator` — CC-RV-06 … 07

| ID | Test method (planned) | State of the System (summary) | Expected output | Implemented? |
|----|----------------------|-------------------------------|-----------------|--------------|
| CC-RV-06 | `validateTakeTokens_boundaryTokenRules` **`@ParameterizedTest`** | (1) bank **1** DIAMOND, take 1D+1R+1O; (2) player **7** tokens, take 3 gems; (3) map contains count **−1** | (1)(2) `isSuccess() == true`; (3) `isSuccess() == false` | :white_check_mark: |
| CC-RV-07 | `validateBuyCard_succeedsWhenBonusCoversEntireColorCost` | Card costs 1 DIAMOND; player has 1 DIAMOND bonus, 0 gems, 0 gold | `isSuccess() == true` | :white_check_mark: |

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
| domain branch coverage | **100%** | `build/reports/jacoco/domain/index.html` | **97%** (237/242) after Phase 2 |
| domain PIT mutation | **100%** killed | `build/reports/pitest/domain/index.html` | **98%** (258/264) after Phase 2 |
| all tests pass | yes | `./gradlew test` | :white_check_mark: |

### Post–Phase 1 verification (2026-06-11)

| Scope | JaCoCo branch | PIT mutation |
|-------|---------------|--------------|
| **domain (package total)** | **92%** (was 89%) | **97%** (was 92%) |
| ui | 60% | 0% (not graded for cyclomatic) |
| whole project (PIT incl. ui) | — | 72% |

### Post–Phase 2 verification (2026-06-12)

| Scope | JaCoCo branch | PIT mutation |
|-------|---------------|--------------|
| **domain (package total)** | **97%** (237/242) | **98%** (258/264) |
| `Game` | 97% branch, 98% mutation (157/161) | CC-GAME-12 … 16 :white_check_mark: |
| `RuleValidator` | 98% branch, 97% mutation (58/60) | CC-RV-06 … 07 :white_check_mark: |
| `MessageProvider` | 80% line, 100% mutation (2/2) | CC-MSG-02 optional |
| `Deck` | 100% / 100% | :white_check_mark: |

**Remaining gaps after Phase 2 (addressed by Phase 3 below):** JaCoCo 5 missed branches (`Game` buy paths, `calculateWinners` tie-break, `RuleValidator` L52); PIT 6 surviving mutants → **0** after Phase 3.

---

## Phase 3 — Remaining gaps to **100%** (12 commits)

Phase 2 closed most gaps but verification still reported **domain branch 97%** and **PIT mutation 98%**. Phase 3 adds **11 new `@Test` methods** + **1 parameterized row** (+ small assertion enhancements on 2 existing tests).

**Replay guide:** `docs/bva/coverage/Phase3CommitPlan.md`  
**Baseline commit:** `4c2e87e`  
**Full test diff reference:** `docs/bva/coverage/phase3-tests-reference.patch`

| Phase | BVA cases | New test methods / rows | Status |
|-------|-----------|-------------------------|--------|
| Phase 1 | CC-GAME-01 … CC-RV-05 | 18+ | :white_check_mark: |
| Phase 2 | CC-GAME-12 … 16, CC-RV-06 … 07 | ~8 | :white_check_mark: |
| **Phase 3** | **CC-GAME-17 … 23, CC-RV-08 … 11, CC-MSG-02** | **11 methods + 1 row** | :x: |

### Phase 3 → gap mapping

| Remaining gap | Covered by |
|---------------|------------|
| `validateBuyReservedState` — `players` / `tokenBank` null | CC-GAME-17, CC-GAME-18 |
| `validateBuyFaceUpState` — `cardIndex < 0`, `cards == null` | CC-GAME-19, CC-GAME-20 |
| `calculateWinners` — `developmentCardCount == fewestDevelopmentCards` tie branch | CC-GAME-21 (`FEWEST_DEV_CARDS_WINS` row) |
| PIT `calculateWinners` — entry `winners.clear()` void call | CC-GAME-22 |
| PIT `calculatePayment` — `remainingCost > 0` boundary at zero | CC-GAME-23 |
| JaCoCo `RuleValidator` L52 — `entry.getValue() == 2` false branch | CC-RV-10 |
| PIT `validateTakeTokens` — `count <= 0` boundary | CC-RV-09 |
| `validateTakeTokens` — bank strictly above minimum for double take | CC-RV-08 |
| PIT `validateBuyCard` — `remainingCost > 0` boundary at zero | CC-RV-11 (+ TC50 counting assert) |
| `MessageProvider` default constructor line | CC-MSG-02 |

---

## Phase 3: `Game` — CC-GAME-17 … 23

| ID | Test method | State of the System (summary) | Expected output | Implemented? |
|----|-------------|-------------------------------|-----------------|--------------|
| CC-GAME-17 | `buyReservedCard_failsWhenPlayersIsNull` | After `startGame(2, US)`; reserved card on current player; `players` set to `null` | `isSuccess() == false`, invalid buy card message | :white_check_mark: |
| CC-GAME-18 | `buyReservedCard_failsWhenTokenBankIsNull` | Same; `tokenBank` set to `null` | `isSuccess() == false`, invalid buy card message | :white_check_mark: |
| CC-GAME-19 | `buyFaceUpCard_rejectsNegativeCardIndexAndLeavesStateUnchanged` | After `startGame(2, US)`; buy level 1 at index **−1** | `isSuccess() == false`; state unchanged | :x: |
| CC-GAME-20 | `buyFaceUpCard_failsWhenLevelMarketListIsNull` | After `startGame(2, US)`; level-1 market list set to `null` | `isSuccess() == false`, invalid buy card message | :x: |
| CC-GAME-21 | `calculateWinners_threePlayerFinalRound` row `FEWEST_DEV_CARDS_WINS` | 3 players; two at 15 prestige / 3 dev cards; one at 15 / 2 dev cards | After final round: single winner with fewest dev cards | :x: |
| CC-GAME-22 | `calculateWinners_clearsExistingWinnersBeforeSelectingNewWinner` | `winners` pre-filled with stale players; invoke `calculateWinners` via reflection | `getWinners().size() == 1`; `clear()` called **2** times on winners list | :x: |
| CC-GAME-23 | `calculatePayment_omitsZeroGemAndGoldEntriesWhenNotNeeded` | Reflection on private `calculatePayment`; bonus-only, gem-only, gold-only, multi-color cases | Payment map has no zero-value keys; bonus-only path reads **0** tokens | :x: |

*Also in `docs/bva/Game.md` as Test Cases 91–92, 98–99, 95†, 100–101.*

---

## Phase 3: `RuleValidator` — CC-RV-08 … 11

| ID | Test method | State of the System (summary) | Expected output | Implemented? |
|----|-------------|-------------------------------|-----------------|--------------|
| CC-RV-08 | `validateTakeTokens_acceptsTwoSameGemTokensWhenBankHasMoreThanMinimum` | Bank **5** DIAMOND; take 2 DIAMOND | `isSuccess() == true` | :x: |
| CC-RV-09 | `validateTakeTokens_rejectsZeroCountEntryEvenWhenOtherTokensFormValidTake` | `{DIAMOND:0, RUBY:2, SAPPHIRE:1}` | `isSuccess() == false` | :x: |
| CC-RV-10 | `validateTakeTokens_rejectsDoubleTakeWhenEntryValueIsNotTwo` | Custom `Map` — loop sees count 2, inner check sees 1 | `isSuccess() == false` | :x: |
| CC-RV-11 | `validateBuyCard_succeedsWhenBonusCoversOneColorInMultiColorCost` | Costs 1 DIAMOND + 1 SAPPHIRE; DIAMOND bonus; 1 GOLD; assert **2** `getTokenCount` calls | `isSuccess() == true` | :x: |

*Also enhance `validateBuyCard_succeedsWhenBonusCoversEntireColorCost` (TC50): assert **1** `getTokenCount` call (GOLD check only).*

*Also in `docs/bva/RuleValidator.md` as TC51–TC54.*

---

## Phase 3: `MessageProvider` — CC-MSG-02

| ID | Test method | State of the System | Expected output | Implemented? |
|----|-------------|---------------------|-----------------|--------------|
| CC-MSG-02 | `messageProvider_defaultConstructor` | `new MessageProvider()` | Instance not null | :x: |

*Also in `docs/bva/MessageProvider.md` as TC5.*

---

## Verification checklist (after Phase 3 complete)

```bash
./gradlew clean test jacocoTestReport pitest
```

| Check | Target | Report | Result (after Phase 3) |
|-------|--------|--------|------------------------|
| domain branch coverage | **100%** | `build/reports/jacoco/domain/index.html` | :x: pending |
| domain PIT Line / Mutation / Test Strength | **100%** each | `build/reports/pitest/domain/index.html` | :x: pending |
| all tests pass | yes | `./gradlew test` | :x: pending |

**Note:** Project-level PIT terminal summary (~74%) includes **ui** mutants with no tests; use **`pitest/domain/index.html`** for grading.

**Optional build fix (separate):** `build.gradle.kts` sets `targetClasses = domain.*, ui.*` but `targetTests = domain.*` only.
