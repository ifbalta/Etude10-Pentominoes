package pentopieces;
public class PieceT implements PieceIntf{
	int pos = 0;
	int limit = 4;
	
	/* Places the T-pentomino */
	public int[][] place(){
		return coords(pos);
	}
	/* Rotates the T-pentomino */
	public int[][] rotate(){
		pos = (pos == (limit - 1))? 1:pos + 1;
		return coords(pos);
	}

	/* Switches between T-pentomino rotations */
	public int[][] coords(int i){
		switch(i){
			case 0:
				int[][] d1 = {{0,0}, {0,1}, {0,2}, {1,1}, {2,1}};
				return d1;
			case 1:
				int[][] d2 = {{0,0}, {1,0}, {2,0}, {1,1}, {1,2}};
				return d2;
			case 2:
				int[][] d3 = {{0,2}, {1,0}, {2,2}, {1,1}, {1,2}};
				return d3;
			case 3:
				int[][] d4 = {{2,0}, {0,1}, {2,2}, {1,1}, {2,1}};
				return d4;
		}
		return null;
	}
	
	public int getLimit(){
		return limit;
	}
}