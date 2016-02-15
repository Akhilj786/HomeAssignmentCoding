KnightTour Solution
Approach:
1) Create a board matrix representing a square by pair of coordinates of the form(X,Y),where both X and Y are integers between 1 and N.
2) Start from given start coordinates(x,y) with index=0. 
3) Check if index== N*N-1;means Knight has cov­ered all the cells. return true and print the solu­tion matrix. Now try to solve rest of the prob­lem recur­sively by mak­ing index +1. Check all 8 direc­tions. (Knight can move to 8 cells from its cur­rent posi­tion.) Check the bound­ary con­di­tions as well. 
4) If none of the 8 recur­sive calls return true, BACKTRACK and undo the changes ( put 0 to cor­re­spond­ing cell in solu­tion matrix) and return false.  

We have a function takeTurn(N, (X, Y)) to list the squares that a knight can jump to from (X, Y) on a chessboard. And finally,we represent the solution of our problem as a list of knight positions (the knight's tour).

Important Note: We can optimize the naive algorithm using the Warnsdorff's rule. When the knight has to choose next step, it will always proceed to the square, from which it has the fewest onwards moves. This heuristic reduces the probability that the knight won't be able to visit some square.

How to compile and run:
$ javac KnightTour.java
// Single Solution 
$ java KnightTour
5
0
0
true
true
Single solution
Solution #0
0 3 8 17 20
9 16 19 2 7
4 1 12 21 18
15 10 23 6 13
24 5 14 11 22

// For Multiple Solution please enter isSingle->false.
$ java KnightTour
6
1
1
false
true
1,1->0,3
Solution #4
10 21 4 1 12 19
5 0 11 20 3 30
22 9 2 29 18 13
35 6 17 26 31 28
16 23 8 33 14 25
7 34 15 24 27 32

1,1->0,3
Solution #5
10 19 4 1 12 21
5 0 11 20 3 30
18 9 2 29 22 13
35 6 17 26 31 28
16 25 8 33 14 23
7 34 15 24 27 32

.....(contd)