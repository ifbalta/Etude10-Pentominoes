/**
	
	There are 12 unique Pentominoes labelled O-Z.
		(R Q S P Y) have 6 rotations
		(T U V W Z) have 4 rotations
		(O) has 2 rotations
		(X) has 1 rotation
	Counting each piece's rotations, there are 63 possible Piece placements.
	The EMPTY Piece indicates an unfilled space.
	The INVALID Piece indicates an unused space.
*/
public enum Piece{
	EMPTY(" "), INVALID(" "), DUMMY("#"),
	O("O"), X("X"),
	T("T"), U("U"), 
	V("V"), W("W"), 
	Z("Z"), R("R"),
	Q("Q"),	S("S"), 
	P("P"), Y("Y"),;

	String name;
	Piece(String name){
		this.name = name;
	}
	String named(){
		return name;
	}


}