# Deck — BVA

`Deck` holds a list of development `Card`s for one level during setup and play. Public API: construct an empty deck, **add** cards (typically from a loader), **shuffle**, **draw** from the front, and query **isEmpty**. Drawing uses index **0** as the “top” of the deck.

## Steps 1–3 (intermediate)

**Partitions**

- **Size**: 0 cards vs 1 vs 2+.
- **drawCard**: deck empty vs non-empty.
- **shuffle**: effect on size (order may vary; tests only fix size / non-empty).

**Boundaries**

- **Empty deck**: `isEmpty` true; `drawCard` returns `null`.
- **After last draw**: deck becomes empty again.

## Step 4 — Test cases (required)

### Method under test: `Deck()` (constructor)

- **DECK-CTOR-01** ( :white_check_mark: )
  - **State of the system**: newly constructed `Deck`.
  - **Expected output**: internal `cards` is empty; `isEmpty()` is `true`; `drawCard()` returns `null`.

### Method under test: `isEmpty()`

- **DECK-IE-01** ( :white_check_mark: )
  - **State of the system**: deck with **no** cards.
  - **Expected output**: `true`.

- **DECK-IE-02** ( :white_check_mark: )
  - **State of the system**: deck with **at least one** card after `addCard`.
  - **Expected output**: `false` (until all cards are drawn).

### Method under test: `drawCard()`

- **DECK-DRAW-01** ( :white_check_mark: )
  - **State of the system**: deck with **zero** cards.
  - **Expected output**: `null`; deck remains empty.

- **DECK-DRAW-02** ( :white_check_mark: )
  - **State of the system**: cards added in order **A** then **B** (two draws possible).
  - **Expected output**: first `drawCard()` returns **A** and removes it; second returns **B**; then `isEmpty()` is `true`.

### Method under test: `addCard(Card card)`

- **DECK-ADD-01** ( :white_check_mark: )
  - **State of the system**: empty deck; `addCard` with a non-null `Card`.
  - **Expected output**: subsequent `drawCard()` returns that same card reference (FIFO with single card).

- **DECK-ADD-02** ( :x: )
  - **State of the system**: `card` is **`null`** (boundary; behavior not specified in design doc).
  - **Expected output**: *document when decided* (reject vs allow); **no automated test yet**.

### Method under test: `shuffle()`

- **DECK-SHUF-01** ( :x: )
  - **State of the system**: deck with **zero** cards; then `shuffle()`.
  - **Expected output**: still empty; no exception (**not covered by its own test**).

- **DECK-SHUF-02** ( :white_check_mark: )
  - **State of the system**: deck with **two** non-null cards; record `cards.size()` before shuffle.
  - **Expected output**: size unchanged; deck still not empty.
