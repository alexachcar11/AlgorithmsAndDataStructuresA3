import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

// note: I discussed quite a bit with CSUS tutors during their office hours on 4/7 and 4/8. I don't remember which ones.
// 		 Most extensively for Q1 and Q3.

public class Honors {

	public int min_moves(int[][] board) {

		int[] xDirec = { 1, 0, -1, 0};
		int[] yDirec = { 0, -1, 0, 1 };

		if (board[0][0] == 0 ) {
			return -1;
		}

		int answer = 0;

		int[][] total = new int[board.length][board[0].length];

		int rowMax = board.length-1;
		int colMax = board[0].length-1;

		for(int i = 0; i <= rowMax; i++) {
			for(int j = 0; j <= colMax; j++) {
				total[i][j] = Integer.MAX_VALUE;
			}
		}
		total[0][0] = 0;

		PriorityQueue<Cell> priorityQueue = new PriorityQueue<Cell>(total.length * total[0].length, new valueRater());
		priorityQueue.add(new Cell(0, 0, 0));

		// while the queue is empty
		while (!priorityQueue.isEmpty()) {

			Cell c = priorityQueue.poll();

			// for each of the 4 cardinal directions that we can go in, process going in that direction
			for(int i = 0; i < 4; i++) {

				int rowJump = c.x + board[c.x][c.y]*xDirec[i];
				int colJump = c.y + board[c.x][c.y]*yDirec[i];

				if (inBoard(board, rowJump, colJump)) {

					if (total[rowJump][colJump] > total[c.x][c.y] + 1) {

						if (!(total[rowJump][colJump] == Integer.MAX_VALUE)) {
							Cell neighbor = new Cell(rowJump, colJump, total[rowJump][colJump]);
							priorityQueue.remove(neighbor);
						}

						total[rowJump][colJump] = total[c.x][c.y] + 1;

						priorityQueue.add(new Cell(rowJump, colJump, total[rowJump][colJump]));
					}
				}
			}
		}
		if (total[rowMax][colMax] == Integer.MAX_VALUE) {
			answer = -1;
		} else {
			answer = total[rowMax][colMax];
		}
		return answer;
	}

	private class Cell {
		int x;
		int y;
		int value;
		Cell(int x, int y, int distance) {
			this.x = x;
			this.y = y;
			this.value = distance;
		}
	}

	private class valueRater implements Comparator<Cell>
	{
		@Override
		public int compare(Cell c1, Cell c2)
		{
			if (c1.value < c2.value)
			{
				return -1;
			}
			else if (c1.value > c2.value)
			{
				return 1;
			}
			else {return 0;}
		}
	}

	private boolean inBoard(int[][] board, int i, int j) {
		if(i >= 0 && i < board.length && j >= 0 && j < board[0].length) {
			return true;
		}
		return false;
	}

	public void test(String filename) throws FileNotFoundException{
		Scanner sc = new Scanner(new File(filename));
		int num_rows = sc.nextInt();
		int num_columns = sc.nextInt();
		int [][]board = new int[num_rows][num_columns];
		for (int i=0; i<num_rows; i++) {
			char line[] = sc.next().toCharArray();
			for (int j=0; j<num_columns; j++) {
				board[i][j] = (int)(line[j]-'0');
			}

		}
		sc.close();
		int answer = min_moves(board);
		System.out.println(answer);
	}

	public static void main(String[] args) throws FileNotFoundException{
		Honors honors = new Honors();
//		honors.test(args[0]); // run 'javac Honors.java' to compile, then run 'java Honors testfilename'

		honors.test("Grid-22.in");
	}

}
