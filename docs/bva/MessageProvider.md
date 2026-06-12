# BVA Analysis: MessageProvider

### Method under test: `getMessage(String key, Locale locale)`

| ID | State of the System | Expected output | Implemented? |
|----|---------------------|-----------------|--------------|
| TC1 | `key` exists in `messages_en_US.properties`, `locale` is `Locale.US` | Returns the English translation string | :white_check_mark: |
| TC2 | `key` exists in `messages_zh_CN.properties`, `locale` is `Locale.SIMPLIFIED_CHINESE` | Returns the Chinese translation string | :white_check_mark: |
| TC3 | `key` is not found in any properties file, `locale` is `Locale.US` | Returns the `key` string itself as a fallback | :white_check_mark: |
| TC4 | `key = "ui.game_started"`, `locale = Locale.FRENCH` (no `messages_fr_FR.properties`) | Returns the `key` string itself as a fallback | :white_check_mark: |
| TC5 | CC-MSG-02: `new MessageProvider()` | Instance created (`assertNotNull`) | :x: |