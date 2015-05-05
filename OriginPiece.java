/**
  Origin piece holds data about occupied square.
  Gives the squares's surrounding top, bottom, left and right locations.
  If a location is unavailable, then it is null
*/
public class OriginPiece{
  /** Surrounding coordinate locations */
  int[] top = null;
  int[] bottom = null;
  int[] left = null;
  int[] right = null; 
  /** Indicates the total number of possible locations */
  int totalSpots = 0;

  /**
    Takes the board and the square location to determine
    available top, bottom, left and right locations.
    @param board a 2D array of Piece objects
    @param location an int array representing coordinates
  */
  public OriginPiece(Piece[][] board, int[] location){
    int x = location[0];
    int y = location[1];

    // check top
    if( x > 0 && board[x - 1][y] == Piece.EMPTY){
      int[] topp = {x - 1, y};
      totalSpots++;
      this.top = topp;
    }

    // check bottom
    if( x < board.length && board[x + 1][y] == Piece.EMPTY){
      int[] bot = {x + 1, y};
      totalSpots++;
      this.bottom = bot;
    }

    // check left
    if( y > 0 && board[x][y - 1] == Piece.EMPTY){
      int[] lft = {x, y - 1};
      totalSpots++;
      this.left = lft;
    }

    // check right
    if( y < board[0].length && board[x][y + 1] == Piece.EMPTY){
      int[] rig = {x, y + 1};
      totalSpots++;
      this.right = rig;
    }
  }

  /**
    Given a board of Pieces, determine if the OriginPiece
    is surrounded by holes.
  */
  public boolean hasHoles(Piece[][] board){
      if(top != null && board[top[0]][top[1]] == Piece.EMPTY) return true;
      if(bottom != null && board[bottom[0]][bottom[1]] == Piece.EMPTY) return true;
      if(left != null && board[left[0]][left[1]] == Piece.EMPTY) return true;
      if(right != null && board[right[0]][right[1]] == Piece.EMPTY) return true;
      return false;
  }

  /**
    Given a board and location, determine if that place is empty.
    @param board the current board
    @param locations the tentative piece's locations
    @param x x-coordinates
    @param y y-coordinates
    @return indicates if the spot is filled
  */
  public boolean unfilled(Piece[][] board, int x, int y, int[][] locations){
    for(int[] pair : locations){
      board[pair[0] + x][pair[1] + y] = Piece.DUMMY;
    }
    // displayBoard(board);
    return board[x][y] == Piece.EMPTY;
  }

  /** 
    Indicates if the piece is still open for placement
    by counting how many coordinates are filled.
    @return true if there are still open spots
   */
  public boolean isAvailable(Piece[][] board){
      int placement = 0;
      if(top != null && board[top[0]][top[1]] == Piece.EMPTY) {
       // System.err.printf("open: %s %s\n", top[0], top[1]);
        placement++;
      }
      if(bottom != null && board[bottom[0]][bottom[1]] == Piece.EMPTY) {
      //  System.err.printf("open: %s %s\n", bottom[0], bottom[1]);
        placement++;
      }
      if(left != null && board[left[0]][left[1]] == Piece.EMPTY) {
      //  System.err.printf("open: %s %s\n", left[0], left[1]);
        placement++;
      }
      if(right != null && board[right[0]][right[1]] == Piece.EMPTY) {
      //  System.err.printf("open: %s %s\n", right[0], right[1]);
        placement++;
      }
      //System.err.println("AVAILABLE SPACES: " + placement);
      return placement != 0; // if zero, then all spots are filled  
  }

  public static void displayBoard(Piece[][] b){
    System.out.println("origin piece checker");
    for(Piece[] row : b){
      for(Piece p : row){
        System.out.print(p.named() + " ");
      }
      System.out.println();
    }
  }

}