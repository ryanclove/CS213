package components;
/**
 * 
 * @author jason dao, ryan coslove
 *	this class allows a knight piece to be built by inheriting from piece and then allowing for customization for the move
 */
public class Knight extends Piece {
	/**
	 * constructor
	 * @param a makes this the type inherited from Piece
	 * @param b makes this the color inherited from Piece
	 */
	public Knight(String a, String b) {
		super();
		type=a;
		color=b;
	}
	/** checks to see if the piece can move there <br>
	 
	 1. checks to see if piece is L shape from it <br>
	 * @param board,num array of current piece (first two indexes), and destination place (final two indexes)
	 * @return 1 if can move there, 0 if not
	 */
	public int move(Piece[][] board,int num[]) {
		if (num[2]==num[0]&& num[1]==num[3]) {
			return 0;
		}
		if (num[2]>num[0]+2 || num[2]<num[0]-2) {
			return 0;
		}
		if (num[3]>num[1]+2 || num[3]<num[1]-2) {
			return 0;
		}
		if (num[2]==num[0]+2 || num[2]==num[0]-2) {
			if (num[3]!=num[1]+1 &&num[3]!=num[1]-1) {
				return 0;
			}
		}
		if (num[2]==num[0]+1 || num[2]==num[0]-1) {
			if (num[3]!=num[1]+2 &&num[3]!=num[1]-2) {
				return 0;
			}
		}
		if (num[2]==num[0]) {
			return 0;
		}
		
		return 1;
	}
	
}
