package components;
/**
 * this class allows a pawn piece to be built by inheriting from piece and then allowing for customization for the move
 * @author jason dao, ryan coslove
 *	
 */
public class Pawn extends Piece {
	public boolean enpassant=false;
	/**
	 * constructor
	 * also has enpassant flag to check
	 * @param a makes this the type inherited from Piece
	 * @param b makes this the color inherited from Piece
	 */
	public Pawn(String a, String b) {
		super();
		type=a;
		color=b;
	}
	/** checks to see if the piece can move there <br>
	 * 1. pawn can only move 2 spaces max <br>
	 * 2. if piece is diagonal from it, has to be something there to capture, else if straight, spot must be empty <br>
	 * For both colors:
	 * 3. pawns can only go backwards <br>
	 * 4. moved pawns can only go one spot <br>
	 * 5. if you reach the end, you can also promote, 
	 * 5. unmoved pawns can also go 2, but subject to enpassant <br>
	 * 6. if the end, you can also promote <br>
	 * 7. fails if not in any of those positions because it cannot move there <br>
	 * @param board,num array of current piece (first two indexes), and destination place (final two indexes)
	 * @return int 0 if cannot move, 1 if can, 3 if enpassant move, 4 if promotion
	 */
	public int move(Piece[][] board,int num[]) {
		if (num[2]==num[0]&& num[1]==num[3]) {  //cant move to same space
			return 0;
		}
		if (num[2]>num[0]+2 || num[2]<num[0]-2 || num[3]<num[1]-1 || num[3]>num[1]+1) {  //cant be more than 2 spaces below or south and more than 1 space horizontal
			return 0;
		}
		if (num[3]==num[1]) {  //if you intend to move straight, straight must be cleared
			if (board[num[2]] [num[3]]!=null){
				return 0;
			}
		}
		if (color.equals("b")) {
			if (num[2]<=num[0]) {   //black pawns must go down
				return 0;
			}
			if (moved==true) {   //if previously moved
				if (num[2]!=num[0]+1) {    //can only go 1 space
					return 0;
				}
				if (num[3]!=num[1]) {
					if (board[num[2]] [num[3]]==null) {   //enpassant
						if (num[0]!=4 || num[2]!=5) {
							return 0;
						}
						if (board[4][num[3]]==null || (!board[4][num[3]].type.equals("p") ) || ((Pawn)board[4][num[3]]).enpassant==false) {
							return 0;
						}
						return 3;
					}
				}
			}
			else {
				if (num[3]!=num[1] && num[2]-num[0]==2 ) {  
					return 0;
				}
				if (num[3]!=num[1] && num[2]-num[0]==1 && board[num[2]][num[3]]==null ) {  
					return 0;
				}
				if (num[2]==3 && num[3]==num[1] && board[num[2]-1][num[3]]==null) {
					enpassant=true;
					return 1;
				}

			}
		}
		else {
			if (num[2]>=num[0]) {
				return 0;
			}
			if (moved==true) {
				if (num[2]!=num[0]-1) {
					return 0;
				}
				if (num[3]!=num[1]) {
					if (board[num[2]][ num[3]]==null) {
						if (num[0]!=3 || num[2]!=2) {
							return 0;
						}
						if (board[3][num[3]]==null || (!board[3][num[3]].type.equals("p") )|| ((Pawn)board[3][num[3]]).enpassant==false) {
							return 0;
						}
						return 3;
					}
				}
			}
			else {
				if (num[3]!=num[1] && num[0]-num[2]==2 ) {  
					return 0;
				}
				if (num[3]!=num[1] && num[0]-num[2]==1 && board[num[2]][num[3]]==null ) {  
					return 0;
				}
				if (num[2]==4 && num[3]==num[1] && board[num[2]+1][num[3]]==null) {
					enpassant=true;
					return 1;
				}
			}
		}
		if (color.equals("b")) {
			if(num[2]==7) {
				return 4;
			}
		}
		else {
			if(num[2]==0) {
				return 4;
			}
		}
		return 1;
	}
}
