package pentopieces;
public class PieceQ implements PieceIntf{
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
				int[][] j1 = {{0,0},{1,0},{2,0},{3,0},{0,1}};
				return j1;
			case 1:
				int[][] j2 = {{0,0},{0,1},{0,2},{0,3},{1,0}};
				return j2;
			case 2:
				int[][] j3 = {{0,0},{1,1},{1,2 },{1,3},{1,0}};
				return j3;
			case 3:
				int[][] j4 = {{0,0},{1,1},{2,1},{3,1},{0,1}};
				return j4;
			case 4:
				int[][] j5 = {{3,0},{1,1},{2,1},{3,1},{0,1}};
				return j5;
			case 5:
				int[][] j6 = {{0,0},{0,1},{0,2},{0,3},{1,3}};
				return j6;		
			case 6:
				int[][] j7 = {{0,0},{1,0},{2,0 },{3,0},{3,1}};
				return j7;
			case 7:
				int[][] j8 = {{0,3},{1,1},{1,2 },{1,3},{1,0}};
				return j8;
		}
		return null;
	}

	public int getLimit(){
		return limit;
	}
}