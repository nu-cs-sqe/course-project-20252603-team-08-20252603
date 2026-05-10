# BVA Analysis - CardLoader

## Method: loadFromClasspath(Class<?> anchorClass, String resourcePath)

JSON root is an **array** of objects with `level` (int), `bonusColor` (`TokenColor` enum name), `prestigePoints` (int), and `cost` (object: color name → int). Encoding UTF-8.

| ID         | State of the System                                                                                  | Expected output                                                                                                                | Implemented?       |
|------------|------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------|--------------------|
| CL-FILE-01 | `resourcePath` points to a present classpath file whose array contains at least one well-formed card | non-empty `List<Card>`; first element's fields match the file                                                                  | :white_check_mark: |
| CL-FILE-02 | `anchorClass.getResourceAsStream(resourcePath)` is null (unknown path)                               | fails with `IOException` (or subclass such as `FileNotFoundException`) carrying a message that identifies the missing resource | :white_check_mark: |
| CL-FILE-03 | `resourcePath` points to production resource `/cards/cards.json`                                     | loads 90 cards with 40 level 1 cards, 30 level 2 cards, and 20 level 3 cards                                                   | :white_check_mark: |
| CL-FILE-04 | `resourcePath` points to a present classpath file with an invalid token color                        | fails with `IOException` carrying a message that identifies the invalid resource                                               | :white_check_mark: |
