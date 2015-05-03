package pentopieces;
public class PieceR implements PieceIntf{
	int pos = 0;
	int limit = 7;
	
	/* Places the R-pentomino */
	public int[][] place(){
		return coords(pos);
	}
	/* Rotates the R-pentomino */
	public int[][] rotate(){
		pos = (pos == (limit - 1))? 1:pos + 1;
		return coords(pos);
	}

	/* Switches between R-pentomino rotations */
	public int[][] coords(int i){
		switch(i){
			case 0:
				int[][] i1 = {{1,0},{0,1},{0,2},{1,1},{2,1}};
				return i1;
			case 1:
				int[][] i2 = {{0,0},{1,0},{1,1},{1,2},{2,1}};
				return i2;
			case 2:
				int[][] i3 = {{0,2},{1,0},{1,1},{1,2},{2,1}};
				return i3;
			case 3:
				int[][] i4 = {{1,2},{0,1},{0,0},{1,1},{2,1}};
				return i4;
			case 4:
				int[][] i5 = {{1,0}, {2,0}, {0,1}, {1,1}, {1,2}};
				return i5;
			case 5:
				int[][] i6 = {{1,0}, {0,1}, {1,1}, {2,1}, {2,2}};
				return i6;
			case 6:
				int[][] i7 = {{2,0}, {0,1}, {1,1}, {2,1}, {1,2}};
				return i7;
			case 7:
				int[][] i8 = {{0,1},{1,0},{1,1},{1,2},{2,2}};
				return i8;
		}
		return null;
	}

	public int getLimit(){
		return limit;
	}
}