/**
	The Pentomino Board.
	Testing tetris style solving.
*/
import java.util.*;
public class Board{
	private static final int PENTO_PIECES = 12;
	private static final String PENTO_STRING = "[OPQRSTUVWXYZ]"; 
	private static Piece[][] board;
	private static Piece[][] puzzleBoard;
	private static List<Pentomino> pentominoes;
	private static List<ColumnNode> pentominoControlRow;
	private static int len = 10;	
	private static int rowPointer = 0;
	private static int colPointer = 0;
	private static ArrayList<Pentomino> used = new ArrayList<Pentomino>();

	public static void main(String[] args){
		Scanner input = new Scanner(System.in);
		ArrayList<String> puzzleStringList = new ArrayList<>();
		while (input.hasNextLine()) {
			puzzleStringList.add(input.nextLine());
		}
		puzzleBoard = buildPuzzle(puzzleStringList);
		displayBoard(puzzleBoard);
		Piece[][] gameBoard = copyOfBoard(puzzleBoard);
		initGame();
		//System.out.println("PentominoApp internal implementation");
		
		// for(Pentomino p :  pentominoes){
		// 	System.out.println(p.pieceName());
		// }
			//testNewRoutine();
	


	}

	public static Piece[][] buildIsland(Piece[][] board, Pentomino piece){
		//place the pentomino dead center and store coordinates
		ArrayList<int[]> occupied = new ArrayList<int[]>();	
		ArrayList<OriginPiece> originPieces = new ArrayList<OriginPiece>();		
		int[][] initialPlace = piece.placeOf();
		int rPoint = len/2 - 2;
		int cPoint = len/2 - 2;

		board = placeIslandPiece(board, piece, rPoint, cPoint);
		used.add(piece);
		displayBoard(board);
		for (int[] pair : initialPlace){
			int[] offset = {pair[0] + rPoint, pair[1] + cPoint};
			OriginPiece oP = new OriginPiece(board, offset);
			originPieces.add(oP);
			occupied.add(offset);
		}
		// for every coordinate pair
		// attempt to place another pentomino around it
		board = growIsland(board, originPieces, piece);
		return board;
	}

	public static Piece[][] growIsland(Piece[][] board, ArrayList<OriginPiece> origins, Pentomino startPiece){
		int[][] pentoCoords;
		int xOrigin, yOrigin;
		int limit;

		// for every originPiece,
		// copy the board and attempt to surround it
		
		return board;
	}

	public static Piece[][] tryThisBoard(){ return null; }	

	public static Piece[][] copyOfBoard(Piece[][] original){
		Piece [][] copied = new Piece[original.length][];
		for (int i = 0; i < original.length; i++) {
		  Piece[] aMatrix = original[i];
		  int aLength = aMatrix.length;
		  copied[i] = new Piece[aLength];
		  System.arraycopy(aMatrix, 0, copied[i], 0, aLength);
		} 
		return copied;
	}


	/**
	 * Checks if space is available.	 
	*/
	public static boolean checkValidIsland(Piece[][] b, int[][] points, int rowPointer, int colPointer, OriginPiece origin){
		int x, y;
		// for(int[] paire : points){
		// 	System.err.printf(" (%s, %s) ", paire[0] + rowPointer, paire[1] + colPointer);
		// }
		// System.err.println();
		for(int[] pair: points){
			x = pair[0] + rowPointer;
			y = pair[1] + colPointer;
			if(!(x >= 0 && x < len)) {
		//		System.err.println("out of row bounds : " + x);
				return false;
			}
			if(!(y >= 0 && y < len)) {
	//			System.err.println("out of col bounds : " + y);
				return false;
			}
			if(b[x][y] != Piece.EMPTY){
		//		System.err.println("Error: Not a free space");
				return false;
			}
		}
		// if(origin.unfilled(b, rowPointer, colPointer, points)){
		// 		System.err.printf("Error: Results in holes.\n");
		// 		return false;
		// } 
		return true;//noHoles(points);
	}

	/**
	 * Places piece.
	*/
	public static Piece[][] placeIslandPiece(Piece[][] b, Pentomino pento, int rowPointer, int colPointer){
		int[][] points = pento.placeOf();
		int x, y;
	//	System.out.println("placing island: " + pento);

		// hack so that pieces that don't start at 0,0 can be placed properly
		boolean cornerStart = false;
		for(int[] xy : points){
			if(xy[0] == 0 && xy[1] == 0) {
				System.err.println(pento + " starts at 0 0 ");
				cornerStart = true;
			}
		}
		// move up diagonally
		if(!cornerStart){
			System.err.println(pento + " does not start at  0 0");
			//rowPointer--;
			//colPointer--;
		}

		for(int[] pair: points){
			x = pair[0] + rowPointer;
			y = pair[1] + colPointer;
	//		System.out.printf("%s %s\n", x, y);
			b[x][y] = pento.pieceName();
		}
		return b;
	}

		/**
	 * Places piece.
	*/
	public static void placePiece(Piece[][] b, Pentomino pento, int[][] points){
		int x, y;
		for(int[] pair: points){
			x = pair[0] + rowPointer;
			y = pair[1] + colPointer;
			b[x][y] = pento.pieceName();
		}
		// get a new rowPointer
		for (int row = 0; row < b.length ; row++) {
			for (int col = 0;col < b[row].length ;col++ ) {
				if(b[row][col] == Piece.EMPTY){
					// System.out.printf("Empty at %s %s\n", row, col );
					rowPointer = row;
					colPointer = col;
					return;
				}
			}
		}

	}

		// for each pentomino
	// place it on every possible place on the board
	public static void testNewRoutine(Piece[][] puzzleBoard){
		int[][] coords;
		int rotations;
		for(Pentomino piece : pentominoes){
			coords = piece.placeOf(); // returns coordinates of a pentomino
			rotations = piece.getLimit(); // number of possible rotations
			while(rotations > 0){
				for(int row = 0; row < len; row++){	
						
				//	System.out.print(rowPointer+"  ////  ");			
					for(int col = 0; col < len; col++){
						puzzleBoard = clearTestBoard();
						rowPointer = row;
						colPointer = col;
						if (checkValid(puzzleBoard, piece, rowPointer, colPointer)){
							//System.out.println();
							puzzleBoard = placePieceTest(puzzleBoard, piece, coords);
							if (puzzleBoard != null) {
								System.out.println();
								displayBoard(puzzleBoard);
							}
						}
					}
					//System.out.print(rowPointer);
					//System.out.println();
				}
				// done with this rotation
				coords = piece.rotate();
				rotations--;
			}

		}
	}

	public static Piece[][] placePieceTest(Piece[][] b, Pentomino pento, int[][] points){
		int x, y;
		for (int[] locs : points) {
			if (!checkValid(b, pento, rowPointer, colPointer)) return null;
		}
		
		for(int[] pair: points){
			x = pair[0] + rowPointer;
			y = pair[1] + colPointer;
			b[x][y] = pento.pieceName();
		}
		// get a new rowPointer
		for (int row = 0; row < b.length ; row++) {
			for (int col = 0;col < b[row].length ;col++ ) {
				if(b[row][col] == Piece.EMPTY){
					// System.out.printf("Empty at %s %s\n", row, col );
					rowPointer = row;
					colPointer = col;
					return b;
				}
			}
		}
		return b;
	}

	public static void initGame(){
		setPentominoes();
		setPentominoColumns();
	}

	public static void setPentominoColumns() {
		pentominoControlRow = new ArrayList<ColumnNode>();
		for (Piece p : Piece.values()) {
			pentominoControlRow.add(new ColumnNode(p));
		}
		// init pointers of first item of doubly linked list.
		pentominoControlRow.get(0).right = pentominoControlRow.get(1);
		pentominoControlRow.get(0).left = pentominoControlRow.get(pentominoControlRow.size() - 1);
		//init right pointer of last item of doubly linked list
		pentominoControlRow.get(pentominoControlRow.size() - 1).right = pentominoControlRow.get(0);
		for (int i = 1; i < pentominoControlRow.size() - 1; i++) {
			pentominoControlRow.get(i).left = pentominoControlRow.get(i - 1);
			pentominoControlRow.get(i).right = pentominoControlRow.get(i + 1);
		}
	}

	public static void setPentominoes(){
		pentominoes = new ArrayList<Pentomino>();
		for(Piece p: Piece.values()){
			if (!(p == Piece.EMPTY || p == Piece.INVALID || p == Piece.DUMMY)){
				pentominoes.add(new Pentomino(p));
			}
		}
	}

	/**
	 * Checks if space is available.	 
	*/
	public static boolean checkValid(Piece[][] b, Pentomino trial, int rowPointer, int colPointer){
		int[][] points = trial.placeOf();
		int x, y;
		HoleChecker checker; 
		// for(int[] paire : points){
		// 	System.err.printf(" (%s, %s) ", paire[0] + rowPointer, paire[1] + colPointer);
		// }
		// System.err.println();
		for(int[] pair: points){
			x = pair[0] + rowPointer;
			y = pair[1] + colPointer;
			if(!(x >= 0 && x < len)) {
		//		System.err.println("out of row bounds : " + x);
				return false;
			}
			if(!(y >= 0 && y < len)) {
	//			System.err.println("out of col bounds : " + y);
				return false;
			}
			if(b[x][y] != Piece.EMPTY){
		//		System.err.println("Error: Not a free space");
				return false;
			}

		}
		// if(origin.unfilled(b, rowPointer, colPointer, points)){
		// //		System.err.printf("Error: Does not actually fill space.\n");
		// 		return false;
		// }

		// checker = new HoleChecker(b, rowPointer, colPointer, points, trial.toString());
		// if(checker.hasHolesNow()){
		// //		System.err.printf("Error: Results in holes.\n");
		// 		return false;
		// }


		return true;//noHoles(points);
	}

	public static Piece[][] clearTestBoard(){
		Piece[][] testBoard = new Piece[len][len];
		for(Piece[] row : testBoard){
			for(int cell = 0; cell < testBoard[0].length ; cell++){
				row[cell] = Piece.EMPTY;
			}
		}
		return testBoard;		
	}

	public static void displayBoard(Piece[][] b){
		for(Piece[] row : b){
			for(Piece p : row){
				System.out.print(p.named() + " ");
			}
			System.out.println();
		}
	}

	public static void setBoard(){
		board = new Piece[len][len];
		for(int r = 0; r < len; r++){
			for(int c = 0; c < len; c++){
				board[r][c] = Piece.EMPTY;
			}
		}
	}

	public static Piece[][] buildPuzzle(ArrayList<String> input) {
		Piece[][] puzzleBoard = new Piece[input.size()][input.get(0).length()];
		Piece currPiece = null;
		String curr;
		for (int row = 0; row < input.size(); row++) {
			curr = input.get(row);
			for (int col = 0; col < input.get(row).length(); col++) {
				char c = curr.charAt(col);
				switch (c) {
					case '.':
						currPiece = Piece.EMPTY;
					break;
					case '*':
						currPiece = Piece.INVALID;
					break;					
				}
				puzzleBoard[row][col] = currPiece;
			}	
		}
		return puzzleBoard;
	}


}