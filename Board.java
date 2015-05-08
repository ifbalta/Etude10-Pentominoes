/**
	The Pentomino Board.
	Testing tetris style solving.
*/
import java.util.*;
public class Board{

	private final int PENTO_PIECES = 12;
	private final int PENTO_AREA_SIZE = 5; // Pentominoes occupy 5 squares only.
	private final String PENTO_STRING = "[OPQRSTUVWXYZ]"; 
	private final int MAX_TRIES = 100;
	private Piece[][] board;
	private Piece[][] puzzleBoard;
	private ArrayList<Pentomino> pentominoes;
	private int len = 10;	
	private int rowPointer = 0;
	private int colPointer = 0;
	private boolean succesfulPlacement = false;
	private ArrayList<Pentomino> usedPentominoes = new ArrayList<Pentomino>();
	private Map<Piece, ArrayList<int[][]>> pentoMap = new HashMap<>();

	public Board(ArrayList<String> puzzleStringList){
		this.puzzleBoard = buildPuzzle(puzzleStringList);
	}

	public void solveThisPuzzle(){
		Piece[][] gameBoard = copyOfBoard(puzzleBoard);
		initGame();
		System.out.println("Puzzle 1:");
		//displayBoard(puzzleBoard);

		//tryAllPlacements(copyOfBoard(puzzleBoard));
		produceAllPossibilities(gameBoard); // this guy will create all the nodes.
		gameBoard = beginSolving();
		if (gameBoard == null) {
			System.out.println("No solution.");
		}
	}

	/*
	* Choose a starting rotation, then choose the next pentominoes.
	*/
	public Piece[][] beginSolving(){
		Random rand = new Random();
		Piece[][] solvingBoard = copyOfBoard(puzzleBoard);
		Pentomino startPentomino = pentominoes.get(rand.nextInt(pentominoes.size()));
		System.out.println("starting: " + startPentomino);
		ArrayList<int[][]> firstPentominoPlacement = pentoMap.get(startPentomino.pieceName());

		for (int[][] row : firstPentominoPlacement) {
			if(testOnBoard(solvingBoard, row)) {
				for (int[] loc : row) {
	  			solvingBoard[loc[0]][loc[1]] = startPentomino.pieceName();	  			
  			}
  			usedPentominoes.add(startPentomino);
  			solvingBoard = chooseNextPentominoes();  			
  			if (usedPentominoes.size() != pentominoes.size()) {
  				// we haven't used everybody.
  				displayBoard(solvingBoard);
  				usedPentominoes.clear();
  				solvingBoard = copyOfBoard(puzzleBoard);
  			} else {
  				displayBoard(solvingBoard);
  				System.out.println("USED: " + usedPentominoes.size());
  				return solvingBoard;
  			}  			
			}
		}
		return null;
	}

	public Piece[][] chooseNextPentominoes(){
		Piece[][] testBoard = copyOfBoard(puzzleBoard);
			for (Pentomino p : pentominoes) {
				if(!usedPentominoes.contains(p)){
					testBoard = pickAPlacement(p, testBoard);
				}
				//displayBoard(testBoard);
			}
		// displayBoard(testBoard);
		return testBoard;
	}

	public Piece[][] pickAPlacement(Pentomino piece, Piece[][] copiedBoard){
		ArrayList<int[][]> availablePlacements = pentoMap.get(piece.pieceName());
		for (int[][] row : availablePlacements) {
			// we're fine, go ahead and place.
			if (testOnBoard(copiedBoard, row)) {
				for (int[] loc : row) {
	  			copiedBoard[loc[0]][loc[1]] = piece.pieceName();	  			
  			}
  			usedPentominoes.add(piece);
  			return copiedBoard;
			}
		}
		return copiedBoard;
	}

	/*
  * Checks that none of the coordinates are filled.
  */
  public boolean testOnBoard(Piece[][] testBoard, int[][] coords){
  	Piece[][] guineaPig = copyOfBoard(testBoard);
  	HoleChecker checker;
  	// ensure that places are available
  	for (int[] loc : coords) {
  		if (testBoard[loc[0]][loc[1]] != Piece.EMPTY) return false; // occupied
  	}
  	// ensure that placement doesn't result in holes
  	checker = new HoleChecker(guineaPig, coords);
  	if(checker.hasHolesNow()) return false;
  	if(checker.allEncompassingChecker()) return false;

  	return true;
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
          for(int col = 0; col < len; col++){
            puzzleBoard = copyOfBoard(puzzleInput);
            rowPointer = row;
            colPointer = col;
            if (checkValid(puzzleBoard, piece, rowPointer, colPointer)){
              addToMap(piece, coords, rowPointer, colPointer);
            }
          }
        }
        // done with this rotation
        coords = piece.rotate();
        rotations--;
      }

    }
  }

  /*
  * Method for storing all possibilities
  */
  public void addToMap(Pentomino piece, int[][] coords, int rowPt, int colPt){
  	ArrayList<int[][]> holder;
  	int x, y;
  	int[][] offsetCoords = new int[5][2];
		for(int i = 0; i < coords.length; i++){
			offsetCoords[i][0] = coords[i][0] + rowPt;
			offsetCoords[i][1] = coords[i][1] + colPt;
		}
		holder = pentoMap.get(piece.pieceName());
		holder.add(offsetCoords);
		pentoMap.put(piece.pieceName(), holder);
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

	public Piece[][] tryAllPlacements(Piece[][] board) {
		int tries = 0;
		ArrayList<Pentomino> availablePentominoes = new ArrayList<>();
		ArrayList<Pentomino> usedPentominoes = new ArrayList<>();
		availablePentominoes.addAll(pentominoes);
		// for each piece, try and place them.
		while(usedPentominoes.size() != availablePentominoes.size()){
			for (Pentomino piece : availablePentominoes) {
				if (!usedPentominoes.contains(piece)){
					board = findASpot(board, piece);
					if (succesfulPlacement) {
						usedPentominoes.add(piece);
					}
					tries++;
					if (tries == MAX_TRIES) {
						Collections.shuffle(pentominoes);
						return tryAllPlacements(copyOfBoard(puzzleBoard)); // copy the original board again.
					}
				}
			}
			displayBoard(board);
		}

		if (isNotCompletelyFilled(board)) {
			Collections.shuffle(pentominoes);
			return tryAllPlacements(copyOfBoard(puzzleBoard)); // copy the original board again.
		}
		return board;
	}

	public boolean isNotCompletelyFilled(Piece[][] board) {
		for (Piece[] row : board) {
			for (Piece cell : row ) {
				if (cell == Piece.EMPTY) return true;
			}
		}
		return false;
	}

	public Piece[][] findASpot(Piece[][] board, Pentomino piece){
		int[][] coords;
		int rowPt, colPt;
		int rotations = piece.getLimit();
		// reset
		succesfulPlacement = false;
		while (rotations > 0) {
			System.out.printf("rotation %s\n", rotations);
			coords = piece.placeOf();
			// find a spot
			for (int row = 0; row < board.length; row++ ) {
				for (int col = 0; col < board[0].length ; col++) {
					if (board[row][col] == Piece.EMPTY) {
						rowPt = row;
						colPt = col;
						if (checkValid(board, piece, rowPt, colPt)) {
							board = placePieceTest(board, piece, coords, rowPt, colPt);
							succesfulPlacement = true;
							System.out.println("Successful " + piece);
							displayBoard(board);
							return board;
						}
					}
				}
			}
			piece.rotate();
			rotations--;
		}
		System.out.println("Unsuccessful " + piece);
		displayBoard(board);
		return board;
	}




	public Piece[][] placePieceTest(Piece[][] b, Pentomino pento, int[][] points, int rowPt, int colPt){
		int x, y;

		for(int[] pair: points){
			x = pair[0] + rowPt;
			y = pair[1] + colPt;
			b[x][y] = pento.pieceName();
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
				pentoMap.put(p, new ArrayList<>());
			}
		}
	}

	/**
	 * Checks if space is available.	 
	*/
	public boolean checkValid(Piece[][] b, Pentomino trial, int rowPointer, int colPointer){
	//	System.out.println("check valid " + trial);
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
			if(!(x >= 0 && x < b.length)) {
//				System.err.println("out of row bounds : " + x);
				return false;
			}
			if(!(y >= 0 && y < b[0].length)) {
//				System.err.println("out of col bounds : " + y);
				return false;
			}
			if(b[x][y] != Piece.EMPTY){
//				System.err.println("Error: Not a free space");
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