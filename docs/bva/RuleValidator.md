# BVA Analysis: RuleValidator

### Method under test: `validatePlayerCount(int count, Locale locale)`

| ID | State of the System | Expected output | Implemented? |
|----|---------------------|-----------------|--------------|
| TC1 | `count` is 2 (Lower Bound), `locale` is US | `isSuccess()` is true, empty message | :white_check_mark: |
| TC2 | `count` is 4 (Upper Bound), `locale` is US | `isSuccess()` is true, empty message | :white_check_mark: |
| TC3 | `count` is 1 (Below Bound), `locale` is US | `isSuccess()` is false, returns US error message | :white_check_mark: |
| TC4 | `count` is 5 (Above Bound), `locale` is CHINA | `isSuccess()` is false, returns CN error message | :white_check_mark: |