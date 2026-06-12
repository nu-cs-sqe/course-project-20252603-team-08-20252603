# Phase 3 — commit replay plan (100% domain coverage)

**Baseline (tests not yet applied):** `4c2e87e` — `test: complete Phase 2 coverage BVA (CC-RV-07) and record verification`

**Full implementation reference:** `docs/bva/coverage/phase3-tests-reference.patch` (generated from baseline; apply hunks per commit below)

**Target after all commits:**

| Check | Report | Target |
|-------|--------|--------|
| JaCoCo domain branch | `build/reports/jacoco/domain/index.html` | **100%** (242/242) |
| PIT domain Line / Mutation / Test Strength | `build/reports/pitest/domain/index.html` | **100%** each (443/443, 264/264, 264/264) |

> Terminal `pitest` summary shows ~74% — that is **project** (`domain` + `ui`). Grade against **`pitest/domain/index.html`** only.

---

## Commit 0 — BVA only (you do this first)

**Files:** `docs/bva/Game.md`, `RuleValidator.md`, `MessageProvider.md`, `CodeCoverageAndMutation.md`, this file, `phase3-tests-reference.patch`

**Message:**
```
docs: add Phase 3 coverage BVA and replay plan for 100% domain
```

---

## Commits 1–12 — one test (or row) per commit

| # | BVA ID | Test method / change | File | Commit message |
|---|--------|----------------------|------|----------------|
| 1 | CC-GAME-17 / TC 98 | `buyReservedCard_failsWhenPlayersIsNull` | `GameTest.java` | `test: buyReservedCard fails when players is null (CC-GAME-17)` |
| 2 | CC-GAME-18 / TC 99 | `buyReservedCard_failsWhenTokenBankIsNull` | `GameTest.java` | `test: buyReservedCard fails when token bank is null (CC-GAME-18)` |
| 3 | CC-GAME-19 / TC 91 | `buyFaceUpCard_rejectsNegativeCardIndexAndLeavesStateUnchanged` | `GameTest.java` | `test: buyFaceUpCard rejects negative card index (CC-GAME-19)` |
| 4 | CC-GAME-20 / TC 92 | `buyFaceUpCard_failsWhenLevelMarketListIsNull` | `GameTest.java` | `test: buyFaceUpCard fails when level market list is null (CC-GAME-20)` |
| 5 | CC-GAME-21 / TC 95† | Add `FEWEST_DEV_CARDS_WINS` row to `calculateWinners_threePlayerFinalRound` | `GameTest.java` | `test: calculateWinners breaks dev-card ties for three players (CC-GAME-21)` |
| 6 | CC-GAME-22 / TC 100 | `calculateWinners_clearsExistingWinnersBeforeSelectingNewWinner` + `ClearCountingList` helper | `GameTest.java` | `test: calculateWinners clears stale winners before recalculating (CC-GAME-22)` |
| 7 | CC-GAME-23 / TC 101 | `calculatePayment_omitsZeroGemAndGoldEntriesWhenNotNeeded` + `TokenReadCountingPlayer` (Game) + imports | `GameTest.java` | `test: calculatePayment omits zero gem and gold entries (CC-GAME-23)` |
| 8 | CC-RV-08 / TC 51 | `validateTakeTokens_acceptsTwoSameGemTokensWhenBankHasMoreThanMinimum` | `RuleValidatorTest.java` | `test: validateTakeTokens accepts two same gems when bank above minimum (CC-RV-08)` |
| 9 | CC-RV-09 / TC 52 | `validateTakeTokens_rejectsZeroCountEntryEvenWhenOtherTokensFormValidTake` | `RuleValidatorTest.java` | `test: validateTakeTokens rejects zero-count entry in valid-shaped take (CC-RV-09)` |
| 10 | CC-RV-10 / TC 53 | `validateTakeTokens_rejectsDoubleTakeWhenEntryValueIsNotTwo` + imports | `RuleValidatorTest.java` | `test: validateTakeTokens rejects double take when entry value is not two (CC-RV-10)` |
| 11 | CC-RV-11 / TC 54 | `validateBuyCard_succeedsWhenBonusCoversOneColorInMultiColorCost` + enhance `validateBuyCard_succeedsWhenBonusCoversEntireColorCost` with `TokenReadCountingPlayer` + helper class | `RuleValidatorTest.java` | `test: validateBuyCard covers multi-color cost with partial bonus (CC-RV-11)` |
| 12 | CC-MSG-02 / TC 5 | `messageProvider_defaultConstructor` | `MessageProviderTest.java` | `test: MessageProvider default constructor is usable (CC-MSG-02)` |

† **TC 95** extends existing Test Case 95 (`CC-GAME-14`); adds third parameterized row, not a new `@Test` method.

**11 new `@Test` methods** = rows 1–4, 6–7, 8–12 (row 5 is enum row only).

After each commit (optional): `./gradlew test`

After commit 12: `./gradlew clean test jacocoTestReport pitest` → confirm `pitest/domain/index.html` all **100%**.

---

## Applying from patch

```bash
# Example: apply only GameTest hunks for commit 1
git apply --check docs/bva/coverage/phase3-tests-reference.patch  # dry-run whole patch
# Prefer: copy the method body from the patch manually per commit, or:
git apply --3way docs/bva/coverage/phase3-tests-reference.patch  # all at once (then split commits with git add -p)
```

Recommended: implement from BVA tables in per-class docs; use patch when unsure of exact code.
