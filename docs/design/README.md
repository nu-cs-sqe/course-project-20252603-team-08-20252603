# System Design

## Project Goal

This project implements a command-line version of Splendor. The goal is to build the core game logic in Java, including game setup, token management, cards, nobles, player actions, rule validation, and game progress.

## Classes and Responsibilities

### Game
Controls the overall game state and setup process. It manages players, game phase, current player, decks, nobles, and token bank.

### Player
Represents one player. It stores the player's tokens, development cards, reserved cards, and prestige points.

### TokenBank
Represents the shared token supply. It initializes token counts based on player count and tracks available tokens.

### TokenColor
Enum for all token colors: diamond, sapphire, emerald, ruby, onyx, and gold.

### GamePhase
Enum for game phases, such as setup and player turn.

### Card
Represents a development card. It stores card level, bonus color, cost, and prestige points.

### Deck
Represents a deck of development cards. It stores cards and supports shuffling and drawing cards.

### Noble
Represents a noble tile. It stores card requirements and prestige points.

### RuleValidator
Checks whether player actions are legal, such as taking tokens, reserving cards, buying cards, and receiving nobles.

### ActionResult
Represents the result of a player action. It stores whether the action succeeded and a message key for output.

### MessageProvider
Provides localized messages for command-line output.

### CardLoader
Loads development card data from resource files.

### NobleLoader
Loads noble data from resource files.

## Design Notes

Game logic is kept separate from command-line input and output so that the domain classes can be tested directly. Fixed concepts such as token colors and game phases are represented as enums. Rule checking is planned to be separated into `RuleValidator` to keep `Game` from becoming too large.