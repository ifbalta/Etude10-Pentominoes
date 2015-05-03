package pentopieces;
public class PieceO implements PieceIntf{
	int pos = 0;
	int limit = 2;
	
	/* Places the O-pentomino */
	public int[][] place(){
		return coords(pos);
	}
	/* Rotates the O-pentomino */
	public int[][] rotate(){
		pos = (pos == (limit - 1))? 1:pos + 1;
		return coords(pos);
	}

	/* Switches between O-pentomino rotations */
	public int[][] coords(int i){
		switch(i){
			case 0:
				int[][] b1 = {{0, 0}, {0, 1}, {0, 2}, {0, 3}, {0, 4}};
				return b1;
			case 1:
				int[][] b2 = {{0, 0}, {1, 0} , {2, 0}, {3, 0}, {4, 0}};
				return b2;
		}
		return null;
	}

	public int getLimit(){
		return limit;
	}
}