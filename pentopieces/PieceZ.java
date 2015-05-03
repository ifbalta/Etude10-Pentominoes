package pentopieces;
public class PieceZ implements PieceIntf{
	int pos = 0;
	int limit = 4;
	
	/* Places the Z-pentomino */
	public int[][] place(){
		return coords(pos);
	}
	/* Rotates the Z-pentomino */
	public int[][] rotate(){
		pos = (pos == (limit - 1))? 1:pos + 1;
		return coords(pos);
	}

	/* Switches between Z-pentomino rotations */
	public int[][] coords(int i){
		switch(i){
			case 0:
				int[][] h1 = {{0,0},{0,1},{1,1},{2,1},{2,2}};
				return h1;
			case 1:
				int[][] h2 = {{0,2},{1,0},{1,1},{1,2},{2,0}};
				return h2;
			case 2:
				int[][] h3 = {{0,0},{1,0},{1,1},{1,2},{2,2}};
				return h3;
			case 3:
				int[][] h4 = {{2,0},{0,1},{0,2},{1,1},{2,1}};
				return h4;
		}
		return null;
	}

	public int getLimit(){
		return limit;
	}
}