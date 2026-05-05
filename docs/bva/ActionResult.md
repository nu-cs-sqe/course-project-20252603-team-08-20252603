# BVA Analysis: ActionResult

### Methods under test: `success()`, `isSuccess()`, `getMessage()`

| ID | State of the System | Expected output | Implemented? |
|----|---------------------|-----------------|--------------|
| TC1 | `ActionResult` instance is created via `ActionResult.success()` | `isSuccess()` returns `true`, `getMessage()` returns `""` | :white_check_mark: |

### Methods under test: `failure(String message)`, `isSuccess()`, `getMessage()`

| ID | State of the System | Expected output | Implemented? |
|----|---------------------|-----------------|--------------|
| TC2 | `ActionResult` instance is created via `ActionResult.failure("Error")` | `isSuccess()` returns `false`, `getMessage()` returns `"Error"` | :white_check_mark: |