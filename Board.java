/**
	The Pentomino Board.
*/
public class Board{
	private int size;
	private int rowPointer;
	private int colPointer;
	private Piece[][] board;
	private Pentomino pento;

	public Board(int size, Piece p){
		this.size = size;
		this.board = new Piece[size][size];
		this.pento = new Pentomino(p);
		for(int r = 0; r < size; r++){
			for (int c = 0; c < size; c++) {
				board[r][c] = Piece.EMPTY;
			}
		}
	}

	public String toString(){
		StringBuilder res = new StringBuilder();
		for(Piece[] row : board){
			for(Piece p : row){
				res.append(p.named() + " ");
			}
			res.append("\n");
		}
		return res.toString();
	}

	public Piece[][] placePiece(Piece p){
		int[][] coords = pento.placeOf();
		int x = 0, y = 0;
		
		for(int[] point : coords){
			x = point[0] + rowPointer;
			y = point[1] + colPointer;
			if(board[x][y] == Piece.EMPTY && board[x][y] != Piece.INVALID){
				board[x][y] = p;
			}
		}
		// last spot
		rowPointer = x;
		colPointer = y + 1;
		return board;
	}

}