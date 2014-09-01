chess_problem
=============

The problem is to find all unique configurations of a set of normal chess pieces on   a chess board with dimensions M×N where none of the pieces is in a position to take any   of the others. Assume the colour of the piece does not matter, and that there are no pawns   among the pieces.


Notes:
The board is considered to be NxN.
There are missing solutions.
Should add unit tests.
It has very poor performance, and there are probably some path branches that are not being trimmed.
The output of 7×7 board with 2 Kings, 2 Queens, 2 Bishops and 1 Knight is: 3960
