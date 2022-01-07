//import java.io.File;
//import java.io.FileNotFoundException;
//import java.util.*;
//
//public class tester {
//
//    static int[] dx = { -1, 0, 1, 0 };
//    static int[] dy = { 0, 1, 0, -1 };
//
//
//    public static int min_moves(int[][] board) {
//        // TODO
//        int total = 0;
////		if (canSolve(0,0,board, total) == false) {
////			return -1;
////		} else {
////			return total;
////		}
//        int row = board.length;
//        int col = board[0].length;
//
//        return canSolve(row,col,board);
//    }
//
//    static class Cell {
//        int x;
//        int y;
//        int distance;
//        Cell(int x, int y, int distance) {
//            this.x = x;
//            this.y = y;
//            this.distance = distance;
//        }
//    }
//
//    static class distanceComparator
//            implements Comparator<Cell>
//    {
//        public int compare(Cell a, Cell b)
//        {
//            if (a.distance < b.distance)
//            {
//                return -1;
//            }
//            else if (a.distance > b.distance)
//            {
//                return 1;
//            }
//            else {
//                return 0;
//            }
//        }
//    }
//
//    private static int canSolve(int row, int col, int[][] grid) { // had int total as a parameter
//        int min = 0;
//
//        int[][] dist = new int[row][col];
//
//        for(int i = 0; i < row; i++)
//        {
//            for(int j = 0; j < col; j++)
//            {
//                dist[i][j] = Integer.MAX_VALUE;
//            }
//        }
//
//        dist[0][0] = grid[0][0];
//
//        PriorityQueue<Cell> pq = new PriorityQueue<Cell>(row*col, new distanceComparator());
//
//        pq.add(new Cell(0,0,dist[0][0]));
//
//        while(!pq.isEmpty()) {
//            Cell curr = pq.poll();
//            for (int i = 0; i < 4; i++) {
//
//                int rows = curr.x + grid[curr.x][curr.y]*dx[i]; //
//                int cols = curr.y + grid[curr.x][curr.y]*dy[i]; //
//
//                if (inBoard(grid,rows,cols)) {
//                    if (dist[rows][cols] > dist[curr.x][curr.y] + grid[rows][cols]) {
//                        // remove cell from pq if it's already been seen
//                        if (dist[rows][cols] != Integer.MAX_VALUE) {
//                            Cell adj = new Cell(rows, cols, dist[rows][cols]);
//                            pq.remove(adj);
////							min++;
//                        }
//                        // insert cell with updated distance
//                        dist[rows][cols] = dist[curr.x][curr.y] + grid[rows][cols];
//
//                        System.out.println(grid[rows][cols]);
//                        min ++;
//
//                        pq.add(new Cell(rows, cols, dist[rows][cols]));
//                    }
//                }
//            }
//        }
//        if (dist[row-1][col-1] == Integer.MAX_VALUE){
//            return -1;
//        } else {
//            return min;
//        }
//    }
//
////	 base case: if we're at the bottom right corner return total -> END
//
////		int value = board[row][col];
////
////		if (row == board.length - 1 && col == board[0].length - 1) {
////			return true;
////		}
////
////		for(int i = 1; i < 4; i++) {
////			row = value*dx[i];
////			col = value*dy[i];
////
////			if (inBoard(board, row, col)) {
////				total++;
////				return canSolve(row,col, board, total);
////			}
////		}
////		return false;
////	}
//
//    private static Boolean inBoard(int[][] board, int row, int col) {
//        try { int i = board[row][col];}
//        catch(Exception e) {return false;}
//
//        return true;
//    }
//
//    //-----------------------------------------------------------------------------------------------------
//
//    public void test(String filename) throws FileNotFoundException{
//        Scanner sc = new Scanner(new File(filename));
//        int num_rows = sc.nextInt();
//        int num_columns = sc.nextInt();
//        int [][]board = new int[num_rows][num_columns];
//        for (int i=0; i<num_rows; i++) {
//            char line[] = sc.next().toCharArray();
//            for (int j=0; j<num_columns; j++) {
//                board[i][j] = (int)(line[j]-'0');
//            }
//
//        }
//        sc.close();
//        int answer = min_moves(board);
//        System.out.println(answer);
//    }
//
//    public static void main(String[] args) throws FileNotFoundException{
//        Honors honors = new Honors();
//        honors.test(args[0]); // run 'javac Honors.java' to compile, then run 'java Honors testfilename'
//
//        // or you can test like this
//        // int [][]board = {1,2,3}; // not actual example
//        // int answer = min_moves(board);
//        // System.out.println(answer);
//    }
//
//}