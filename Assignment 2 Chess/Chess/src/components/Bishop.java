package components;
/**
 * this class allows a bishop piece to be built by inheriting from piece and then allowing for customization for the move
 * @author jason dao, ryan coslove
 *	
 */
public class Bishop extends Piece {
	/**
	 * constructor
	 * @param a makes this the type inherited from Piece
	 * @param b makes this the color inherited from Piece
	 */
	public Bishop(String a, String b) {
		super();
		type=a;
		color=b;
	}
	/** checks to see if the piece can move there <br>
	 1. checks to see if piece is diagonal from it <br>
	 2. checks to see if any piece blocking way from that spot to path <br>
	 * @param board,num array of current piece (first two indexes), and destination place (final two indexes)
	 * @return int 0 if not moved ,1 if can
	 */
	public int move(Piece [][] board,int num[]) {
		if (num[2]==num[0]&& num[1]==num[3]) {
			return 0;
		}
		if (Math.abs(num[2]-num[0])!=Math.abs(num[3]-num[1])) {  //if not diagonal can't move there
			return 0;
		}
		int tempnum2=num[2];
		int tempnum1=num[3];
		for (int i=0;i<Math.abs(num[2]-num[0])-1;i++) {   //checks to see if there is chess piece in way of path to spot
			if (num[2]-num[0]<0) {
				tempnum2++;
			}
			else {
				tempnum2--;
			}
			if (num[3]-num[1]<0) {
				tempnum1++;
			}
			else {
				tempnum1--;
			}
			if (board[tempnum2][ tempnum1]!=null) {
				return 0;
			}
		}
		
		return 1;
	}
}
