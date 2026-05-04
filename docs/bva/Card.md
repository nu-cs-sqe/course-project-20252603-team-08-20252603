# Card — BVA

`Card` is a development card: **level**, **bonus gem color**, **token cost** map, and **prestige points**. The class also exposes a no-arg `Card()` for other use; **game setup** is covered here only by **`CardTest`** using the **full constructor**.

## Steps 1–3 (intermediate)

**Focus (setup tests)**

- One representative card: level 2, sapphire bonus, two cost entries, one prestige point.
- Field values match constructor arguments exactly.

## Step 4 — Test cases (required)

### Method under test: `Card(int level, TokenColor bonusColor, Map<TokenColor, Integer> cost, int prestigePoints)`

- **CARD-CTOR-01** ( :white_check_mark: )
  - **State of the system**: `level` = 2, `bonusColor` = `SAPPHIRE`, `cost` has `ONYX`→2 and `EMERALD`→1, `prestigePoints` = 1.
  - **Expected output**: `card.level`, `bonusColor`, `prestigePoints`, and `cost` entries match (`gameSetup_createsCardWithDesignFields`).
