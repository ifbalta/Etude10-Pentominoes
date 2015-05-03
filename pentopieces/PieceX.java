package pentopieces;
public class PieceX implements PieceIntf{
	/* Coordinates */
	int[][] a = {{0,1}, {1,0}, {1,1} , {1,2}, {2,1}};
	int limit = 1;
	
	/* Places the X-pentomino */
	public int[][] place(){
		return a;
	}

	/* The X-pentomino cannot rotate*/
	public int[][] rotate(){
		return a;
	}

	public int getLimit(){
		return limit;
	}
}