/**
	The Pentomino Board.
	Testing tetris style solving.
*/
import java.util.*;
import java.util.ArrayList;

public class EfficientBoard{
    private static int PUZZLE_COUNTER = 0;
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
	private Map<Piece, ArrayList<int[][]>> pentoMap = new HashMap<Piece, ArrayList<int[][]>>();

	public EfficientBoard(ArrayList<String> puzzleStringList){
		this.puzzleBoard = buildPuzzle(puzzleStringList);
	}

	public void solveThisPuzzle(){
		System.out.printf("Puzzle %s:\n", ++PUZZLE_COUNTER);
		// if size != 12 * 5
		if (puzzleBoard.length * puzzleBoard[0].length < (12 * 5)) {
			System.out.println("No solution");
			return;
		}
		Piece[][] gameBoard = copyOfBoard(puzzleBoard);
		initGame();
		
		//displayBoard(puzzleBoard);

		//tryAllPlacements(copyOfBoard(puzzleBoard));
		produceAllPossibilities(gameBoard); // this guy will create all the nodes.
		gameBoard = beginDfsSolving(copyOfBoard(puzzleBoard));
		if (gameBoard == null) {
			System.out.println("No solution.");
		} else {
			if (isNotCompletelyFilled(gameBoard)){
				System.out.println("No solution.");
			} else{
				displayBoard(gameBoard);
			}
		}
	}

    /**
     * Find an empty spot and cover it.
     * Find the next spot, then dfs through that stuff.
     * */
	public Piece[][] beginDfsSolving(Piece[][] puzzleBoard){
		Piece[][] testBoard = copyOfBoard(puzzleBoard);
        Piece[][] resultBoard;
        ArrayList<Pentomino> orderList = new ArrayList<Pentomino>();
		orderList.addAll(pentominoes);
		Collections.shuffle(orderList);
		ArrayList<int[][]> pieceLocations;
        ArrayList<Pentomino> used = new ArrayList<Pentomino>();
        int emptyX = 0, emptyY = 0;
        // find first empty spot and find a pentomino that fits it.
        // find an empty spot first
        for (int row = 0; row < testBoard.length; row++) {
            for (int col = 0; col < testBoard[0].length; col++) {
                if (testBoard[row][col] == Piece.EMPTY){
                    emptyX = row;
                    emptyY = col;
                    break;
                }
            }
        }
        for (Pentomino pentomino : orderList) {
            // get pentomino placements
            pieceLocations = pentoMap.get(pentomino.pieceName());
            for (int[][] locs : pieceLocations){
                for(int[] xy : locs){
                    if (xy[0] == emptyX && xy[1] == emptyY) {
                     // check that this location can be placed.
                     if (testOnBoard(copyOfBoard(testBoard), locs)) {
                         testBoard = placeOnBoard(testBoard, locs, pentomino);
                         used.add(pentomino);
                         //displayBoard(testBoard);
                         resultBoard = dfsPentomino( testBoard, orderList, used);
                         if (resultBoard != null) {
                             return resultBoard;
                         }
                         // failed. take it out and find another pentomino.
                         testBoard = erasePreviousMove(pentomino, testBoard);
                         used.remove(pentomino);
                     }
                 }
             }
            }
        }



		return null;
	}

	public Piece[][] placeOnBoard(Piece[][] copiedBoard, int[][] location, Pentomino piece){
		for (int[] loc : location) {
	  			copiedBoard[loc[0]][loc[1]] = piece.pieceName();	  			
  			}
        usedPentominoes.add(piece);
  			return copiedBoard;
	}

    /**
     * Instead of blindly placing a pentomino, look for an empty spot, then find an unused
     * pentomino that can fit it.
     * */
	public Piece[][] dfsPentomino(Piece[][] partialBoard, ArrayList<Pentomino> orderList, ArrayList<Pentomino> used){
		Piece[][] dummyBoard = copyOfBoard(partialBoard);
		Piece[][] resultBoard;
        int emptyX = 0, emptyY = 0;
		if(used.size() == orderList.size()) return partialBoard; // found a solution, because I've covered 60 places

        // find an empty spot first
        for (int row = 0; row < partialBoard.length; row++) {
            for (int col = 0; col < partialBoard[0].length; col++) {
                if (partialBoard[row][col] == Piece.EMPTY){
                    emptyX = row;
                    emptyY = col;
                    break;
                }
            }
        }
      //  System.err.printf("Empty %s %s\n", emptyX, emptyY);
		ArrayList<int[][]> pieceLocations;
        for (Pentomino pentomino : orderList) {
            if (!used.contains(pentomino)) {
                // get pentomino placements
                pieceLocations = pentoMap.get(pentomino.pieceName());
                for (int[][] locs : pieceLocations) {
                    for (int[] xy : locs) {
                        if (xy[0] == emptyX && xy[1] == emptyY) {
                            // check that this location can be placed.
                            if (testOnBoard(dummyBoard, locs)) {
                                partialBoard = placeOnBoard(partialBoard, locs, pentomino);
                                used.add(pentomino);
                               // displayBoard(partialBoard);
                                resultBoard = dfsPentomino(partialBoard, orderList, used);
                                if (resultBoard != null) {
                                    return resultBoard;
                                }
                                // failed. take it out and find another pentomino.
                                partialBoard = erasePreviousMove(pentomino, partialBoard);
                                used.remove(pentomino);
                            }
                        }
                    }
                }
            }
        }
        return null;
	}

	public Piece[][] erasePreviousMove(Pentomino prev, Piece[][] prevBoard){
		Piece prevPiece = prev.pieceName();
		for (int row = 0; row < prevBoard.length; row++) {
			for (int col = 0; col < prevBoard[0].length; col++) {
				if (prevBoard[row][col] == prevPiece) {
					prevBoard[row][col] = Piece.EMPTY;
				}
			}
		}
		usedPentominoes.remove(prev);
		return prevBoard;
	}

	/*
  * Checks that none of the coordinates are filled.
  */
  public boolean testOnBoard(Piece[][] testBoard, int[][] coords){
  	Piece[][] guineaPig = copyOfBoard(testBoard);
  	// ensure that placement doesn't result in holes
  	HoleChecker checker = new HoleChecker(guineaPig, coords);
  	// ensure that places are available
  	for (int[] loc : coords) {
  		if (testBoard[loc[0]][loc[1]] != Piece.EMPTY) return false; // occupied
  		 if(checker.checkOccupiedSurroundings(loc)) return false;
  	}
  	
	 
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



	public boolean isNotCompletelyFilled(Piece[][] board) {
		//System.out.println("not isNotCompletelyFilled");
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

		int[][] points = trial.placeOf();
		int[][] offsetLocs;
		Piece[][] guineaPig;
		int x, y, neighbours, surroundingLimit, i;
		HoleChecker checker; 
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
		guineaPig  = copyOfBoard(b);
		offsetLocs = new int[5][2];
		i = 0;
		// place pieces on board.
		for (int[] validLoc : points) {
			x = validLoc[0] + rowPointer;
			y = validLoc[1] + colPointer;
			guineaPig[x][y] = Piece.DUMMY;
			offsetLocs[i][0] = x;
			offsetLocs[i][1] = y;
			i++;
		}

		// check that there are no holes
		for (int row = 0; row < guineaPig.length; row++) {
			for (int col = 0; col < guineaPig[0].length; col++) {
				if (guineaPig[row][col] == Piece.EMPTY){
					surroundingLimit = 4;
					neighbours = 0;
					if (row > 0) {
						if(guineaPig[row - 1][col] != Piece.EMPTY) neighbours++;
					} else {
						surroundingLimit--;
					}

					if (row < guineaPig.length - 1) {
						if(guineaPig[row + 1][col] != Piece.EMPTY) neighbours++;
					} else {
						surroundingLimit--;
					}

					if (col > 0) {
						if(guineaPig[row][col - 1] != Piece.EMPTY) neighbours++;
					} else {
						surroundingLimit--;
					}


					if (col < guineaPig[0].length - 1) {
						if(guineaPig[row][col + 1] != Piece.EMPTY) neighbours++;
					} else {
						surroundingLimit--;
					}

					if (neighbours == surroundingLimit) return false;
				}
			}
		}
		return true;
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