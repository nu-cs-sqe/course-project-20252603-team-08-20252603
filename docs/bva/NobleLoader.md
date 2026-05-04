# NobleLoader — BVA

`NobleLoader` loads **noble tile** data from **classpath resource files** during game setup (Splendor-style). Each `Noble` has `requirements` (`Map<TokenColor, Integer>`) and `prestigePoints`. This iteration matches `CardLoader`: **reliable loading of bundled JSON** for setup; broad invalid-input validation is out of scope.

## Steps 1–3 (intermediate)

**Focus**

- **Resource resolution**: stream found vs missing (`getResourceAsStream` returns null).
- **Happy path**: valid JSON root **array** of noble objects matching the agreed file format.

**Boundaries**

- Smallest useful file: JSON array with **one** noble (setup smoke test).
- Missing resource: **null** stream (boundary between found / not found).

## Step 4 — Test cases (required)

### Method under test: `loadFromClasspath(Class<?> anchorClass, String resourcePath)`

JSON root is an **array** of objects with `requirements` (object: `TokenColor` enum name → non-negative int) and `prestigePoints` (int). Encoding UTF-8.

- **NL-FILE-01** ( :white_check_mark: )
  - **State of the system**: `resourcePath` points to a **present** classpath file whose array contains **at least one** well-formed noble.
  - **Expected output**: non-empty `List<Noble>`; first element’s `requirements` and `prestigePoints` match the file.

- **NL-FILE-02** ( :x: )
  - **State of the system**: `anchorClass.getResourceAsStream(resourcePath)` is **null** (unknown path).
  - **Expected output**: fails with `IOException` (or subclass such as `FileNotFoundException`) carrying a message that identifies the missing resource.
