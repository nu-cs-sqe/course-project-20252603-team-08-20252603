# BVA Analysis: MessageProvider

### Method under test: `getMessage(String key, Locale locale)`

| ID | State of the System | Expected output | Implemented? |
|----|---------------------|-----------------|--------------|
| TC1 | `key` exists in `messages_en_US.properties`, `locale` is `Locale.US` | Returns the English translation string | :x: |
| TC2 | `key` exists in `messages_zh_CN.properties`, `locale` is `Locale.SIMPLIFIED_CHINESE` | Returns the Chinese translation string | :x: |
| TC3 | `key` is not found in any properties file, `locale` is `Locale.US` | Returns the `key` string itself as a fallback | :x: |