# CardLoader — BVA

`CardLoader` loads development `Card` data from **classpath resource files** during game setup (Splendor-style). Each `Card` has `level`, `bonusColor`, `cost`, and `prestigePoints`. Game setup only requires **reliable loading of bundled JSON**; exhaustive validation of every bad field is out of scope for this iteration.

## Steps 1–3 (intermediate)

**Focus**

- **Resource resolution**: stream found vs missing (`getResourceAsStream` returns null).
- **Happy path**: valid JSON root **array** of card objects matching the agreed file format.

**Boundaries**

- Smallest useful file: JSON array with **one** card (setup smoke test).
- Missing resource: **null** stream (boundary between found / not found).

## Step 4 — Test cases (required)

### Method under test: `loadFromClasspath(Class<?> anchorClass, String resourcePath)`

JSON root is an **array** of objects with `level` (int), `bonusColor` (`TokenColor` enum name), `prestigePoints` (int), and `cost` (object: color name → int). Encoding UTF-8.

- **CL-FILE-01** ( :white_check_mark: )
  - **State of the system**: `resourcePath` points to a **present** classpath file whose array contains **at least one** well-formed card.
  - **Expected output**: non-empty `List<Card>`; first element’s fields match the file.

- **CL-FILE-02** ( :white_check_mark: )
  - **State of the system**: `anchorClass.getResourceAsStream(resourcePath)` is **null** (unknown path).
  - **Expected output**: fails with `IOException` (or subclass such as `FileNotFoundException`) carrying a message that identifies the missing resource.
