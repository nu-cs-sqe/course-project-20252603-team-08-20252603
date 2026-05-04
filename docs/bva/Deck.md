# Deck — BVA

`Deck` holds development `Card`s for one pile during **game setup** and later play: **construct** an empty deck, **add** cards, **shuffle**, **draw** from the front (index `0`), and **isEmpty**. This document lists only cases covered by **`DeckTest`** for the setup phase (all implemented).

## Steps 1–3 (intermediate)

**Focus**

- Fresh deck is empty; drawing is `null` until cards are added.
- **FIFO** draw order matches add order for setup smoke tests.
- **Shuffle** does not change how many cards are in the deck.

**Boundaries**

- Size **0** vs **2+** for shuffle test; draw until empty again.

## Step 4 — Test cases (required)

### Method under test: `Deck()` (constructor)

- **DECK-CTOR-01** ( :white_check_mark: )
  - **State of the system**: `new Deck()` right after construction.
  - **Expected output**: `isEmpty()` is `true` and `drawCard()` is `null` (see `gameSetup_newDeckIsEmpty`).

### Method under test: `isEmpty()`

- **DECK-IE-01** ( :white_check_mark: )
  - **State of the system**: newly constructed deck, no `addCard` yet.
  - **Expected output**: `true` (`gameSetup_newDeckIsEmpty`).

- **DECK-IE-02** ( :white_check_mark: )
  - **State of the system**: after two cards were added and both drawn in order.
  - **Expected output**: `true` (`gameSetup_addAndDrawPopsInOrder`).

- **DECK-IE-03** ( :white_check_mark: )
  - **State of the system**: deck with two cards after `shuffle()`.
  - **Expected output**: `false` (`gameSetup_shuffle_doesNotChangeSize`).

### Method under test: `drawCard()`

- **DECK-DRAW-01** ( :white_check_mark: )
  - **State of the system**: deck with zero cards.
  - **Expected output**: `null` (`gameSetup_newDeckIsEmpty`).

- **DECK-DRAW-02** ( :white_check_mark: )
  - **State of the system**: `addCard(A)` then `addCard(B)`; no shuffle between adds.
  - **Expected output**: first `drawCard()` returns `A`, second returns `B` (`gameSetup_addAndDrawPopsInOrder`).

### Method under test: `addCard(Card card)`

- **DECK-ADD-01** ( :white_check_mark: )
  - **State of the system**: empty deck; `addCard` twice with two distinct `Card` instances.
  - **Expected output**: draws return those instances in FIFO order (`gameSetup_addAndDrawPopsInOrder`).

### Method under test: `shuffle()`

- **DECK-SHUF-01** ( :white_check_mark: )
  - **State of the system**: deck with exactly two cards; record `cards.size()` before `shuffle()`.
  - **Expected output**: size unchanged after shuffle; deck is still not empty (`gameSetup_shuffle_doesNotChangeSize`).
