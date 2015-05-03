package pentopieces;
public class PieceV implements PieceIntf{
	int pos = 0;
	int limit = 4;
	
	/* Places the V-pentomino */
	public int[][] place(){
		return coords(pos);
	}
	/* Rotates the V-pentomino */
	public int[][] rotate(){
		pos = (pos == (limit - 1))? 1:pos + 1;
		return coords(pos);
	}

	/* Switches between V-pentomino rotations */
	public int[][] coords(int i){
		switch(i){
			case 0:
				int[][] f1 = {{0,0}, {0,1}, {0,2}, {1,0}, {2,0}};
				return f1;
			case 1:
				int[][] f2 = {{0,0}, {0,1}, {0,2}, {1,2}, {2,2}};
				return f2;
			case 2:
				int[][] f3 = {{2,0}, {2,1}, {0,2}, {1,2}, {2,2}};
				return f3;
			case 3:
				int[][] f4 = {{2,0}, {2,1}, {0,0}, {1,0}, {2,2}};
				return f4;
		}
		return null;
	}

	public int getLimit(){
		return limit;
	}
}