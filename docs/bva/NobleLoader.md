# BVA Analysis - NobleLoader

## Method: loadFromClasspath(Class<?> anchorClass, String resourcePath)

JSON root is an **array** of objects with `requirements` (object: `TokenColor` enum name → non-negative int) and `prestigePoints` (int). Encoding UTF-8.

| ID         | State of the System                                                                                   | Expected output                                                                                                                | Implemented?       |
|------------|-------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------|--------------------|
| NL-FILE-01 | `resourcePath` points to a present classpath file whose array contains at least one well-formed noble | non-empty `List<Noble>`; first element's `requirements` and `prestigePoints` match the file                                    | :white_check_mark: |
| NL-FILE-02 | `anchorClass.getResourceAsStream(resourcePath)` is null (unknown path)                                | fails with `IOException` (or subclass such as `FileNotFoundException`) carrying a message that identifies the missing resource | :white_check_mark: |
| NL-FILE-03 | `resourcePath` points to production resource `/nobles/nobles.json`                                    | loads 10 nobles and every noble has 3 prestige points                                                                          | :white_check_mark: |
| NL-FILE-04 | `resourcePath` points to a present classpath file with an invalid token color                         | fails with `IOException` carrying a message that identifies the invalid resource                                               | :white_check_mark: |
