# BVA Analysis - Player

## Method: getPrestigePoints()

|             | State of the System | Expected output     | Implemented?       |
|-------------|---------------------|---------------------|--------------------|
| Test Case 1 | New Player object   | prestigePoints == 0 | :white_check_mark: |

---

## Method: getTokenCount(TokenColor color)

|             | State of the System                 | Expected output  | Implemented?       |
|-------------|-------------------------------------|------------------|--------------------|
| Test Case 2 | New Player object, color = DIAMOND  | token count == 0 | :white_check_mark: |
| Test Case 3 | New Player object, color = SAPPHIRE | token count == 0 | :white_check_mark: |
| Test Case 4 | New Player object, color = EMERALD  | token count == 0 | :white_check_mark: |
| Test Case 5 | New Player object, color = RUBY     | token count == 0 | :white_check_mark: |
| Test Case 6 | New Player object, color = ONYX     | token count == 0 | :white_check_mark: |
| Test Case 7 | New Player object, color = GOLD     | token count == 0 | :white_check_mark: |

---

## Method: getDevelopmentCards()

|             | State of the System | Expected output                | Implemented?       |
|-------------|---------------------|--------------------------------|--------------------|
| Test Case 8 | New Player object   | development card list is empty | :white_check_mark: |

---

## Method: getReservedCards()

|             | State of the System | Expected output             | Implemented?       |
|-------------|---------------------|-----------------------------|--------------------|
| Test Case 9 | New Player object   | reserved card list is empty | :white_check_mark: |

---

## Method: getTotalTokenCount()

|              | State of the System | Expected output        | Implemented?       |
|--------------|---------------------|------------------------|--------------------|
| Test Case 10 | New Player object   | total token count == 0 | :white_check_mark: |

---

## Method: addTokens(Map<TokenColor, Integer> tokensToAdd)

|              | State of the System                         | Expected output                                                         | Implemented?       |
|--------------|---------------------------------------------|-------------------------------------------------------------------------|--------------------|
| Test Case 11 | New Player object, add 1 DIAMOND            | DIAMOND token count == 1, total token count == 1                        | :white_check_mark: |
| Test Case 12 | New Player object, add 2 DIAMOND and 1 RUBY | DIAMOND token count == 2, RUBY token count == 1, total token count == 3 | :white_check_mark: |
| Test Case 13 | Player has 1 DIAMOND, add 2 DIAMOND         | DIAMOND token count == 3, total token count == 3                        | :white_check_mark: |
| Test Case 14 | New Player object, add empty token map      | All token counts remain 0, total token count == 0                       | :white_check_mark: |

---

## Method: removeTokens(Map<TokenColor, Integer> tokensToRemove)

|              | State of the System                                | Expected output                                                         | Implemented?       |
|--------------|----------------------------------------------------|-------------------------------------------------------------------------|--------------------|
| Test Case 15 | Player has 2 DIAMOND, remove 1 DIAMOND             | DIAMOND token count == 1, total token count == 1                        | :white_check_mark: |
| Test Case 16 | Player has 2 DIAMOND and 1 RUBY, remove all tokens | DIAMOND token count == 0, RUBY token count == 0, total token count == 0 | :white_check_mark: |
| Test Case 17 | Player has 1 DIAMOND, remove 2 DIAMOND             | Throw IllegalArgumentException and leave token counts unchanged         | :white_check_mark: |
| Test Case 18 | New Player object, remove 1 DIAMOND                | Throw IllegalArgumentException and leave token counts unchanged         | :white_check_mark: |
| Test Case 19 | Player has 1 DIAMOND, remove empty token map       | DIAMOND token count == 1, total token count == 1                        | :white_check_mark: |

---

## Method: addReservedCard(Card card)

|              | State of the System                            | Expected output                                   | Implemented?       |
|--------------|------------------------------------------------|---------------------------------------------------|--------------------|
| Test Case 20 | New Player object, add one reserved card       | reservedCards.size() == 1 and contains the card   | :white_check_mark: |
| Test Case 21 | Player has one reserved card, add another card | reservedCards.size() == 2 and contains both cards | :white_check_mark: |
