import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Akhil Jain
 * 
 *         Backtracking Knight's tour solver Approach: 1) Create a board matrix.
 *         2) Start from given start coordinates with index=0 (Every square will
 *         be indexed). 3) Check if index== N*N-1;means Knight has cov­ered all
 *         the cells. return true and print the solu­tion matrix. Now try to
 *         solve rest of the prob­lem recur­sively by mak­ing index +1. Check
 *         all 8 direc­tions. (Knight can move to 8 cells from its cur­rent
 *         posi­tion.) Check the bound­ary con­di­tions as well 4) If none of
 *         the 8 recur­sive calls return true, BACKTRACK and undo the changes (
 *         put 0 to cor­re­spond­ing cell in solu­tion matrix) and return false.
 * 
 *         Important Note: We can optimize the naive algorithm using the
 *         Warnsdorff's rule. When the knight has to choose next step, it will
 *         always proceed to the square, from which it has the fewest onwards
 *         moves. This heuristic reduces the probability that the knight won't
 *         be able to visit some square.
 */
public class KnightTour {

	/**
	 * Indicator that the square was not visited yet
	 */
	private static int NOT_VISITED = -1;
	/**
	 * Width of the chessboard
	 */
	private int xSize;
	/**
	 * Height of the chessboard
	 */
	private int ySize;
	/**
	 * Numver of solutions
	 */
	private int solutionsCount;

	private int start_x;
	private int start_y;
	/**
	 * Solution board 0 -> Initial position of the knight 1 -> first move 2 ->
	 * second move . . . n -> n-th move
	 */
	private int[][] board;

	public static void main(String[] args) {

		@SuppressWarnings("resource")
		Scanner s = new Scanner(System.in);

		int N = s.nextInt(); // Chess board N x N
		int x = s.nextInt(); // Starting X position
		int y = s.nextInt(); // Starting Y position
		boolean isSingle = s.nextBoolean(); // Single tour if set else multiple
		boolean isClosed = s.nextBoolean(); // Closed tour.

		KnightTour kt = new KnightTour(x, y, N, N);

		// Single Solution
		if (isSingle) {
			System.out.println("Single solution");
			kt.solve(x, y);
		} else {
			// Closed But Multiple solution
			kt.solve(x, y, isSingle, isClosed);

		}

		System.out.println("Total Solutions" + kt.solutionsCount);
	}

	/**
	 * Constructor
	 * 
	 * @param xSize
	 *            width of the chessboard
	 * @param ySize
	 *            height of the chessboard
	 */
	public KnightTour(int start_x, int start_y, int xSize, int ySize) {
		solutionsCount = 0;
		this.start_x = start_x;
		this.start_y = start_y;
		this.xSize = xSize;
		this.ySize = ySize;

		board = new int[ySize][xSize];
		for (int i = 0; i < ySize; i++) {
			for (int j = 0; j < xSize; j++) {
				board[i][j] = NOT_VISITED;
			}
		}
	}

	/**
	 * 
	 * Solve the knight's tour: Single
	 */
	public void solve(int x, int y) {
		takeTurn(x, y, 0);
		board[x][y] = NOT_VISITED;
	}

	/**
	 * Perform the move
	 * 
	 * @param x
	 *            destination x coord
	 * @param y
	 *            destination y coord
	 * @param turnNr
	 *            number of the move
	 */
	private void takeTurn(int x, int y, int turnNr) {
		board[y][x] = turnNr;
		if (turnNr == (xSize * ySize) - 1) {
			if (solutionsCount == 0) {
				printBoard();
				solutionsCount++;
				System.exit(0);
				return;

			}
		} else {
			for (Cordinates c : getFields(x, y)) {
				if (board[c.getY()][c.getX()] == NOT_VISITED) {
					takeTurn(c.getX(), c.getY(), turnNr + 1);
					board[c.getY()][c.getX()] = NOT_VISITED; // reset square
				}
			}
		}
	}

	/**
	 * 
	 * Solve the knight's tour: Multiple and closed
	 */

	public void solve(int x, int y, boolean isSingle, boolean isClosed) {
		
		takeTurn(x, y, 0, isSingle, isClosed);
		board[x][y] = NOT_VISITED;
	}

	private void takeTurn(int x, int y, int turnNr, boolean isSingle,
			boolean closed) {
		board[y][x] = turnNr;
		if (turnNr == (xSize * ySize) - 1) {
			// Helper function to check if the solution found is closed or not
			if (!isSingle && closed) {
				if (isClosed(start_x, start_y, x, y)) {
					System.out.println(start_x + "," + start_y + "->" + x + ","
							+ y);
					printBoard();
				}
			} else {

				printBoard();
			}
			solutionsCount++;

			return;

		} else {
			for (Cordinates c : getFields(x, y)) {
				if (board[c.getY()][c.getX()] == NOT_VISITED) {
					takeTurn(c.getX(), c.getY(), turnNr + 1, isSingle, closed);
					board[c.getY()][c.getX()] = NOT_VISITED; // reset
																// the
																// square
				}
			}
		}
	}

	/**
	 * Checks if tour is closed
	 * 
	 * @param (x,y)->start position (endx,endy)->end position
	 * @return true if closed.
	 */
	private boolean isClosed(int x, int y, int endx, int endy) {

		if (x + 2 < xSize && y - 1 >= 0)
			if (x + 2 == endx && y - 1 == endy)
				return true;
		if (x + 1 < xSize && y - 2 >= 0)
			if (x + 1 == endx && y - 2 == endy)
				return true;
		if (x - 1 >= 0 && y - 2 >= 0)
			if (x - 1 == endx && y - 2 == endy)
				return true;
		if (x - 2 >= 0 && y - 1 >= 0)
			if (x - 2 == endx && y - 1 == endy)
				return true;
		if (x - 2 >= 0 && y + 1 < ySize)
			if (x - 2 == endx && y + 1 == endy)
				return true;
		if (x - 1 >= 0 && y + 2 < ySize)
			if (x - 1 == endx && y + 2 == endy)
				return true;

		if (x + 1 < xSize && y + 2 < ySize)
			if (x + 1 == endx && y + 2 == endy)
				return true;

		if (x + 2 < xSize && y + 1 < ySize)
			if (x + 2 == endx && y + 1 == endy)
				return true;

		return false;
	}

	/**
	 * Return possible destinations of the knight
	 * 
	 * @param x
	 *            x coord of the knight
	 * @param y
	 *            y coord of the knight
	 * @return possible destinations of the knight
	 */
	private List<Cordinates> getFields(int x, int y) {
		List<Cordinates> l = new ArrayList<Cordinates>();
		if (x + 2 < xSize && y - 1 >= 0)
			l.add(new Cordinates(x + 2, y - 1)); // right and upward
		if (x + 1 < xSize && y - 2 >= 0)
			l.add(new Cordinates(x + 1, y - 2)); // upward and right
		if (x - 1 >= 0 && y - 2 >= 0)
			l.add(new Cordinates(x - 1, y - 2)); // upward and left
		if (x - 2 >= 0 && y - 1 >= 0)
			l.add(new Cordinates(x - 2, y - 1)); // left and upward
		if (x - 2 >= 0 && y + 1 < ySize)
			l.add(new Cordinates(x - 2, y + 1)); // left and downward
		if (x - 1 >= 0 && y + 2 < ySize)
			l.add(new Cordinates(x - 1, y + 2)); // downward and left
		if (x + 1 < xSize && y + 2 < ySize)
			l.add(new Cordinates(x + 1, y + 2)); // downward and right
		if (x + 2 < xSize && y + 1 < ySize)
			l.add(new Cordinates(x + 2, y + 1)); // right and downward
		return l;
	}

	/**
	 * Print out the Board
	 */
	private void printBoard() {
		System.out.println("Solution #" + solutionsCount);
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				System.out.print(board[i][j] + " ");
			}
			System.out.println("");
		}
		System.out.println("");
	}

	/**
	 * @return the solutionsCount
	 */
	public int getSolutionsCount() {
		return solutionsCount;
	}

}

// Helper class to maintain X and Y.
class Cordinates {
	private int x;
	private int y;

	public Cordinates(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}
}