# PyramidSolitaire

A PyramidSolitaire game using Java11.
The game generates a pyramid of cards and a number of draw cards from a standard 52 card deck.

Win condition:
All cards in the pyramid are removed, before there is no legal move left.

Game options include 3 game types: basic, relaxed, multipyramid.

Player interactions/commands:
 - rm1 [row] [card]: remove 1 card of value 13
 - rm2 [row1] [card1] [row2] [card2]: remove 2 cards that sum up to 13
 - rmwd [draw] [row] [card]: remove 1 pyramid card and a draw card with a sum of 13
 - dd [draw]: discard a draw card

To start:
[game_type] [number_of_rows] [number_of_draw_cards]

To interact:
[command] [arg1] [arg2] ...
