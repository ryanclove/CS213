package components;
/**
 * this class allows a king piece to be built by inheriting from piece and then allowing for customization for the move
 * @author jason dao, ryan coslove
 *	
 */
public class King extends Piece{
	/**
	 * constructor
	 * @param a makes this the type inherited from Piece
	 * @param b makes this the color inherited from Piece
	 */
	public King(String a, String b) {
		super();
		type=a;
		color=b;
	}
	/** checks to see if the piece can move there <br>
	 * 1. checks for castling <br>
	 * 2. checks if it is the 8 spaces around king that it can move <br>
	 * 3. fails if not in any of those positions because it cannot move there <br>
	 * @param board,num array of current piece (first two indexes), and destination place (final two indexes)
	 * @return 1 if can move there, 0 if not, 2 if castling
	 */
	public int move(Piece [][]board,int num[]) {
		if (board[num[2]][num[3]]==null) {
			if (num[0]==7 && num[1]==4 && moved==false && num[2]==7 && color.equals("w") && (num[3]==6 || num[3]==2)) {
				if(num[3]==6 && board[7][5]==null &&board[7][7].moved==false )
					return 2;
				if(num[3]==2 && board[7][1]==null && board[7][3]==null &&board[7][0].moved==false )
					return 2;
			}
			if (num[0]==0 && num[1]==4 && moved==false && num[2]==0 && color.equals("b") && (num[3]==6 || num[3]==2)) {
				if(num[3]==6 && board[0][5]==null &&board[0][7].moved==false )
					return 2;
				if(num[3]==2 && board[0][1]==null && board[0][3]==null &&board[0][0].moved==false )
					return 2;
			}
		}
		if (num[2]>num[0]+1 || num[2]<num[0]-1) {
			return 0;
		}
		if (num[3]>num[1]+1 || num[3]<num[1]-1) {
			return 0;
		}
		if (num[2]==num[0]&& num[1]==num[3]) {
			return 0;
		}
		return 1;
	}
	public boolean capture() {
		return false;
	}
}
