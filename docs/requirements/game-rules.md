## Players and Setup

The game supports 2 to 4 players.

A new game starts with player 0 as the current player.

Each player starts with 0 prestige points, 0 tokens, no development cards, no reserved cards, and no nobles.

The token bank starts with 5 gold tokens.

Each gem color starts with 4 tokens in a 2-player game, 5 tokens in a 3-player game, and 7 tokens in a 4-player game.

Development cards are split into level 1, level 2, and level 3 decks.

Each level has up to 4 face-up cards in the market.

The game reveals player count + 1 nobles.

## Turn Order

Players take turns in order.

After a valid action, the current player advances to the next player.

After the last player, turn order returns to player 0.

An invalid action must not change game state and must not advance the turn.

## Player Actions

On a turn, a player must perform exactly one action: take tokens, reserve a card, or buy a card.

## Taking Tokens

A player may take 3 different gem tokens if the bank has at least 1 of each selected color.

A player may take 2 tokens of the same gem color only if the bank has at least 4 tokens of that color before the action.

Gold cannot be taken with the normal take-token action.

A player may not finish a turn with more than 10 total tokens.

## Reserving Cards

A player may reserve one face-up card or the top card from a deck.

A player may have at most 3 reserved cards.

When a player reserves a card, the player takes 1 gold token if the bank has gold available.

If no gold is available, the player still reserves the card without taking gold.

A reserved face-up card is replaced from its deck if possible.

## Buying Cards

A player may buy one face-up card or one of their reserved cards.

Development card bonuses reduce future costs by color.

Gold tokens may be spent as wild tokens for any gem color.

Spent tokens return to the bank.

The bought card is added to the player's development cards.

The player's prestige points increase by the card's prestige value.

A bought face-up card is replaced from its deck if possible.

An invalid buy must not spend tokens, move cards, award points, refill the market, award nobles, or advance the turn.

## Nobles

After buying a card, the player checks noble requirements.

A player qualifies for a noble when their development card bonuses meet or exceed that noble's requirements.

A player may receive at most one noble per turn.

If a player qualifies for multiple nobles, the player chooses one.

Received nobles add prestige points and are removed from the shared noble area.

## Game End

When a player has at least 15 prestige points at the end of their turn, the final round is triggered.

The game continues until all players have taken the same number of turns.

After the final round is complete, no more actions are allowed.

The winner is the player with the most prestige points.

If tied, the tied player with the fewest development cards wins.

If still tied, the tied players share the win.