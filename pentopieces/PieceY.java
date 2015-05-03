package pentopieces;
public class PieceY implements PieceIntf{
	int pos = 0;
	int limit = 8;
	
	/* Places the Y-pentomino */
	public int[][] place(){
		return coords(pos);
	}
	/* Rotates the Y-pentomino */
	public int[][] rotate(){
		pos = (pos == (limit - 1))? 1:pos + 1;
		return coords(pos);
	}

	/* Switches between Y-pentomino rotations */
	public int[][] coords(int i){
		switch(i){
			case 0:
				int[][] m1 = {{0,0},{0,1},{0,2},{0,3},{1,2}};
				return m1;
			case 1:
				int[][] m2 = {{1,0},{1,1},{1,2},{1,3},{0,2}};
				return m2;
			case 2:
				int[][] m3 = {{0,1},{1,1},{2,1},{3,1},{1,0}};
				return m3;
			case 3:
				int[][] m4 = {{0,0},{1,0},{2,0},{3,0},{1,1}};
				return m4;
			case 4:
				int[][] m5 = {{0,0},{0,1},{0,2},{0,3},{1,1}};
				return m5;
			case 5:
				int[][] m6 = {{1,0},{1,1},{1,2},{1,3},{0,1}};
				return m6;
			case 6:
				int[][] m7 = {{0,1},{1,1},{2,1},{3,1},{2,0}};
				return m7;
			case 7:
				int[][] m8 = {{0,0},{1,0},{2,0},{3,0},{2,1}};
				return m8;
		}
		return null;
	}

	public int getLimit(){
		return limit;
	}
}