import java.util.*;
import pentopieces.*;
/**
  * The Pentomino Game.
		TODO LIST:
			- fix holes function
			-- can be fixed by using a copied board when surrounding each OriginPiece.
			-- once filled, call hasHoles() on the copied board 
			---- if true, discard the board
			---- else return the board
			-- remove the for loop in growIsland(), 
			---- make it so that it cycles through a different starting position

			~~ DON'T FORGET TO WORK WITH THE EMULATOR ~~

*/
public class PentominoApp{
	private static final int PENTO_PIECES = 12;
	private static final String PENTO_STRING = "[OPQRSTUVWXYZ]"; 
	private static Piece[][] board;
	private static List<Pentomino> pentominoes;
	private static int len = 10;	
	private static int rowPointer = 0;
	private static int colPointer = 0;
	private static ArrayList<Pentomino> used = new ArrayList<Pentomino>();

	public static void main(String[] args){
	
		Piece[][] gameBoard = clearTestBoard();
		initGame();
		System.out.println("PentominoApp internal implementation");
		
		// for(Pentomino p :  pentominoes){
		// 	System.out.println(p.pieceName());
		// }
		if(args.length == 1 && args[0].matches(PENTO_STRING)){
			System.err.println("Received " + args[0]);
			handleArgs(args, gameBoard);
		} else {
			gameBoard = buildIsland(gameBoard, pentominoes.get(11));
			System.out.println();
			displayBoard(gameBoard);
			System.out.println();
		}
	


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
		int counter = 0;
		for (OriginPiece ooP : originPieces){
	//		System.out.println("Surrounding: " + ++counter);
			board = growIsland(board, ooP, piece);
			displayBoard(board);
		}
		return board;
	}

	public static Piece[][] growIsland(Piece[][] board, OriginPiece origin, Pentomino startPiece){
		int[][] pentoCoords;
		int xOrigin, yOrigin;
		int limit;
		//	System.err.println("Attempting to grow");
		for(Pentomino available : pentominoes){
		//	System.err.println("Placing " + available);
			pentoCoords = available.placeOf();
			limit = available.getLimit(); // already have one
			while(limit > 0 && origin.isAvailable(board)){
				if(!used.contains(available) && !available.pieceName().equals(startPiece.pieceName())) {
					System.err.println("Used: " + used);
				//	System.err.println("Origin available? " + origin.isAvailable(board));
					// try top
					if(origin.top != null && !used.contains(available)){
					//	System.out.println("I have a top");
							xOrigin = origin.top[0];
							yOrigin = origin.top[1];
							if(checkValidIsland(copyOfBoard(board), pentoCoords, xOrigin, yOrigin, origin)){
					//			System.out.printf("valid place: %s %s\n", xOrigin, yOrigin);
								board = placeIslandPiece(board, available, xOrigin, yOrigin);
								used.add(available);
							}
					}
					// try bottom
					if(origin.bottom != null && !used.contains(available)){
					//	System.out.println("I have a bottom");
							xOrigin = origin.bottom[0];
							yOrigin = origin.bottom[1];
							if(checkValidIsland(copyOfBoard(board), pentoCoords, xOrigin, yOrigin, origin)){
					//			System.out.printf("valid place: %s %s\n", xOrigin, yOrigin);
								board = placeIslandPiece(board, available, xOrigin, yOrigin);
								used.add(available);
							}
					}
					// try left
					if(origin.left != null && !used.contains(available)){
					//	System.out.println("I have a left");
							xOrigin = origin.left[0];
							yOrigin = origin.left[1];
							if(checkValidIsland(copyOfBoard(board), pentoCoords, xOrigin - 1, yOrigin - 1, origin)){
					//			System.out.printf("valid place: %s %s\n", xOrigin, yOrigin);
								board = placeIslandPiece(board, available, xOrigin, yOrigin);
								used.add(available);
							}
					}
					// try right
					if(origin.right != null && !used.contains(available)){
					//	System.out.println("I have a right");
							xOrigin = origin.right[0];
							yOrigin = origin.right[1];
							if(checkValidIsland(copyOfBoard(board), pentoCoords, xOrigin, yOrigin, origin)){
					//			System.out.printf("valid place: %s %s\n", xOrigin, yOrigin);
								board = placeIslandPiece(board, available, xOrigin, yOrigin);
								used.add(available);
							}
					}
				}				
				pentoCoords = available.rotate();
				limit--;
			}
		}
		return board;
	}	

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
		if(origin.unfilled(b, rowPointer, colPointer, points)){
				System.err.printf("Error: Results in holes.\n");
				return false;
		} 
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

	public static void initGame(){
		setPentominoes();
		setBoard();
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
	public static boolean checkValid(Piece[][] b, int[][] points){
		int x, y;
		// for(int[] paire : points){
		// 	System.err.printf(" (%s, %s) ", paire[0] + rowPointer, paire[1] + colPointer);
		// }
		// System.err.println();
		for(int[] pair: points){
			x = pair[0] + rowPointer;
			y = pair[1] + colPointer;
			if(!(x >= 0 && x < len)) {
				//System.out.println("out of row bounds : " + x);
				return false;
			}
			if(!(y >= 0 && y < len)) {
			//	System.out.println("out of col bounds : " + y);
				return false;
			}
			if(b[x][y] != Piece.EMPTY){
				//System.out.println("Error: Not a free space");
				return false;
			} 
		}
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

		/* 
			~ SCUPPERED ~
			~~ our puzzle is different :P
			the idea is to get every possible place
			a pentomino can take on the board.
			Once we have this, DFS through all possibilities,
			ensuring that no coordinate pair is repeated.

		*/
	public static void getCombinations(){}

	public static void handleArgs(String[] args, Piece[][] gameBoard){
		Pentomino startPoint = null;
		switch(args[0].charAt(0)){
			case 'O':
			startPoint = new Pentomino(Piece.O);
			break;
			case 'P':
			startPoint = new Pentomino(Piece.P);
			break;
			case 'Q':
			startPoint = new Pentomino(Piece.Q);
			break;
			case 'R':
			startPoint = new Pentomino(Piece.R);
			break;
			case 'S':
			startPoint = new Pentomino(Piece.S);
			break;
			case 'T':
			startPoint = new Pentomino(Piece.T);
			break;
			case 'U':
			startPoint = new Pentomino(Piece.U);
			break;
			case 'V':
			startPoint = new Pentomino(Piece.V);
			break;
			case 'W':
			startPoint = new Pentomino(Piece.W);
			break;
			case 'X':
			startPoint = new Pentomino(Piece.X);
			break;
			case 'Y':
			startPoint = new Pentomino(Piece.Y);
			break;
			case 'Z':
			startPoint = new Pentomino(Piece.Z);
			break;
		}
		gameBoard = buildIsland(gameBoard, startPoint);
		System.out.println();
		displayBoard(gameBoard);
		System.out.println();
	}

}