# Noble — BVA

`Noble` is a noble tile: **requirements** (`Map<TokenColor, Integer>`) and **prestige points**. Game **setup** coverage here matches **`NobleTest`** only (one constructor, one smoke case).

## Steps 1–3 (intermediate)

**Focus (setup tests)**

- One representative noble: three gem requirements at 3 each, prestige 3.
- Stored `requirements` entries and `prestigePoints` match constructor arguments.

## Step 4 — Test cases (required)

### Method under test: `Noble(Map<TokenColor, Integer> requirements, int prestigePoints)`

- **NOBLE-CTOR-01** ( :white_check_mark: )
  - **State of the system**: `requirements` has `EMERALD`→3, `RUBY`→3, `ONYX`→3; `prestigePoints` = 3.
  - **Expected output**: `noble.prestigePoints` and each `noble.requirements` entry match (`gameSetup_createsNobleWithDesignFields`).
