package pentopieces;
public class PieceW implements PieceIntf{
	int pos = 0;
	int limit = 4;
	
	/* Places the W-pentomino */
	public int[][] place(){
		return coords(pos);
	}
	/* Rotates the W-pentomino */
	public int[][] rotate(){
		pos = (pos == (limit - 1))? 1:pos + 1;
		return coords(pos);
	}

	/* Switches between W-pentomino rotations */
	public int[][] coords(int i){
		switch(i){
			case 0:
				int[][] g1 = {{0,0},{1,0},{1,1},{2,1},{2,2}};
				return g1;
			case 1:
				int[][] g2 = {{0,3},{1,3},{1,2},{2,1},{2,2}};
				return g2;
			case 2:
				int[][] g3 = {{0,0},{0,1},{1,1},{1,2},{2,2}};
				return g3;
			case 3:
				int[][] g4 = {{0,1},{0,2},{1,0},{1,1},{2,0}};
				return g4;
		}
		return null;
	}

	public int getLimit(){
		return limit;
	}
}