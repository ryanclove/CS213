
package components;
/**
 * this class allows a queen piece to be built by inheriting from piece and then allowing for customization for the move
 * @author jason dao, ryan coslove
 *	
 */
public class Queen extends Piece {
	/**
	 * constructor
	 * @param a makes this the type inherited from Piece
	 * @param b makes this the color inherited from Piece
	 */
	public Queen(String a, String b) {
		super();
		type=a;
		color=b;
	}
	/** checks to see if the piece can move there <br>
	 * 1. if piece is vertical/horizontal from it, checks to see if any piece blocking way from that spot to path <br>
	 * 2. if piece is diagonal from it, checks to see if any piece blocking way from that spot to path <br>
	 * 3. fails if not in any of those positions because it cannot move there <br>
	 * @param board,num array of current piece (first two indexes), and destination place (final two indexes)
	 * @return 1 if can move there, 0 if not
	 */
	public int move(Piece [][] board,int num[]) {
		if (num[2]==num[0]&& num[1]==num[3]) {
			return 0;
		}
		if (num[2] == num[0] || num[3]==num[1]) {  //if not vertical/horizontal can't move there
			int tempnum2=num[2];
			int tempnum1=num[3];
			if (num[2]!=num[0]) {
				for (int i=0;i<Math.abs(num[2]-num[0])-1;i++) {   //checks to see if there is chess piece in way of path to spot
					if (num[2]-num[0]<0) {
						tempnum2++;
					}
					else {
						tempnum2--;
					}
					if (board[tempnum2][tempnum1]!=null) {
						return 0;
					}
				}
			}
			else {
				for (int i=0;i<Math.abs(num[3]-num[1])-1;i++) {   //checks to see if there is chess piece in way of path to spot
					if (num[3]-num[1]<0) {
						tempnum1++;
					}
					else {
						tempnum1--;
					}
					if (board[tempnum2][tempnum1]!=null) {
						return 0;
					}
				}
			}
		}
		else if (Math.abs(num[2]-num[0])==Math.abs(num[3]-num[1])){
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
				if (board[tempnum2][tempnum1]!=null) {
					return 0;
				}
			}
		}
		else {
			return 0;
		}
		
		return 1;
	}
	
}
