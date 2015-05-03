package pentopieces;
public class PieceS implements PieceIntf{
	int pos = 0;
	int limit = 8;
	
	/* Places the S-pentomino */
	public int[][] place(){
		return coords(pos);
	}
	/* Rotates the S-pentomino */
	public int[][] rotate(){
		pos = (pos == (limit - 1))? 1:pos + 1;
		return coords(pos);
	}

	/* Switches between S-pentomino rotations */
	public int[][] coords(int i){
		switch(i){
			case 0:
				int[][] k1 = {{0,0}, {0,1}, {1,1}, {1,2}, {1,3}};
				return k1;
			case 1:
				int[][] k2 = {{0,2}, {0,3}, {1,1}, {1,2}, {1,0}};
				return k2;
			case 2:
				int[][] k3 = {{0,0},{1,0},{1,1},{2,1},{3,1}};
				return k3;
			case 3:
				int[][] k4 = {{0,1},{1,1},{3,0},{1,0},{2,0}};
				return k4;
			case 4:
				int[][] k5 = {{0,0},{0,1},{0,2},{1,2},{1,3}};
				return k5;
			case 5:
				int[][] k6 = {{1,0},{1,1},{0,1},{0,2},{0,3}};
				return k6;
			case 6:
				int[][] k7 = {{0,0},{1,0},{2,0},{2,1},{3,1}};
				return k7;
			case 7:
				int[][] k8 = {{0,1},{1,1},{2,1},{2,0},{3,0}};
				return k8;
		}
		return null;
	}

	public int getLimit(){
		return limit;
	}
}