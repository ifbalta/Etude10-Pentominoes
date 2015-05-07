/**
	The Pentomino Board.
	Testing tetris style solving.
*/
import java.util.*;
public class Board{

	private final int PENTO_PIECES = 12;
	private final int PENTO_AREA_SIZE = 5; // Pentominoes occupy 5 squares only.
	private final String PENTO_STRING = "[OPQRSTUVWXYZ]"; 
	private Piece[][] board;
	private Piece[][] puzzleBoard;
	private List<Pentomino> pentominoes;
	private int len = 10;	
	private int rowPointer = 0;
	private int colPointer = 0;
	private ArrayList<Pentomino> used = new ArrayList<Pentomino>();

	public Board(ArrayList<String> puzzleStringList){
		this.puzzleBoard = buildPuzzle(puzzleStringList);
	}

	public void solveThisPuzzle(){
		displayBoard(puzzleBoard);
		Piece[][] gameBoard = copyOfBoard(puzzleBoard);
		initGame();
		System.out.println("Board:");
		displayBoard(puzzleBoard);

		// produceAllPossibilities(gameBoard); // this guy will create all the nodes.
	}

	public Piece[][] copyOfBoard(Piece[][] original){
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
	 * Places piece.
	*/
	public void placePiece(Piece[][] b, Pentomino pento, int[][] points){
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
	public void produceAllPossibilities(Piece[][] puzzleInput){
		Piece[][] puzzleBoard = puzzleInput;
		int[][] coords;
		int rotations;
		for(Pentomino piece : pentominoes){
			coords = piece.placeOf(); // returns coordinates of a pentomino
			rotations = piece.getLimit(); // number of possible rotations
			while(rotations > 0){
				for(int row = 0; row < len; row++){	
						
				//	System.out.print(rowPointer+"  ////  ");			
					for(int col = 0; col < len; col++){
						puzzleBoard = copyOfBoard(puzzleInput);
						rowPointer = row;
						colPointer = col;
						if (checkValid(puzzleBoard, piece, rowPointer, colPointer)){
							//System.out.println();
							puzzleBoard = placePieceTest(puzzleBoard, piece, coords);
							System.out.println("all possibilities");
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

	public Piece[][] placePieceTest(Piece[][] b, Pentomino pento, int[][] points){
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

	public void initGame(){
		setPentominoes();
	}

	public void setPentominoes(){
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
	public boolean checkValid(Piece[][] b, Pentomino trial, int rowPointer, int colPointer){
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

	public Piece[][] clearTestBoard(){
		Piece[][] testBoard = new Piece[len][len];
		for(Piece[] row : testBoard){
			for(int cell = 0; cell < testBoard[0].length ; cell++){
				row[cell] = Piece.EMPTY;
			}
		}
		return testBoard;		
	}

	public void displayBoard(Piece[][] b){
		for(Piece[] row : b){
			for(Piece p : row){
				System.out.print(p.named() + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

	public void setBoard(){
		board = new Piece[len][len];
		for(int r = 0; r < len; r++){
			for(int c = 0; c < len; c++){
				board[r][c] = Piece.EMPTY;
			}
		}
	}

	public Piece[][] buildPuzzle(ArrayList<String> input) {
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