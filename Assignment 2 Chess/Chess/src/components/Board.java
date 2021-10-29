package components;
/**
 * this implements board class which has the board and can return what is on there as well as executing moves
 * @author jason dao, ryan coslove
 * 
 */
public class Board {
	/**
	 * board, turn number, color name of whose turn it is, color name of enemy, whether draw has been called
	 */
	public Piece [][]board;
	public int turn;
	public String turnname;
	public String turnenemy;
	public boolean draw;
	/**
	 * this instantiates class and puts pieces where they belong
	 */
	public Board() {
		turn=0;
		draw=false;
		board=new Piece[8][8];
		for (int i=0;i<8;i++) {
			if (i==0 || i==7) {
				board[0][i]=new Rook("R","b");
				board[7][i]=new Rook("R","w");
			}
			if (i==1 || i==6) {
				board[0][i]=new Knight("N","b");
				board[7][i]=new Knight("N","w");
			}
			if (i==2 || i==5 ) {
				board[0][i]=new Bishop("B","b");
				board[7][i]=new Bishop("B","w");
			}
			if (i==3) {
				board[0][i]=new Queen("Q","b");
				board[7][i]=new Queen("Q","w");
			}
			if (i==4) {
				board[0][i]=new King("K","b");
				board[7][i]=new King("K","w");
			}
			board[1][i]=new Pawn("p","b");
			board[6][i]=new Pawn("p","w");
		}
	}
	/**
	 * return piece based on coordinate
	 * @param a int x coordinate
	 * @param b int y coordinate
	 * @return piece, null if no piece on that space
	 */
	public Piece getPiece(int a, int b) {
		if (a>7 || a<0) {
			return null;
		}
		if (b>7 || b<0) {
			return null;
		}
		return board[a][b];
	}
	/**
	 * check to see if x coordinate and y coordinate are in bound
	 * @param a x coordinate
	 * @param b y coordinate
	 * @return boolean
	 */
	public boolean valid(int a, int b) {
		if (a>7|| a<0 || b>7 ||b<0) {
			return false;
		}
		return true;
	}
	/**
	 * 
	 * @param a int of x coordinate on board
	 * @param b int of y coodinate on board
	 * @return String array of info of piece based on coordinates (what type it is or color), if no piece return null
	 */
	public String [] getPieceInfo (int a, int b) {
		if (a>7 || a<0) {
			return null;
		}
		if (b>7 || b<0) {
			return null;
		}
		if (board[a][b]==null) {
			return null;
		}
		String []thing=new String[2];
		thing[0]=board[a][b].type;
		thing[1]=board[a][b].color;
		return thing;
	}
	/**
	 * uses string array[], compares two pieces on what type, color it is
	 * @param a string array of piece 1
	 * @param b string array of piece 2
	 * @return  boolean if equal or not
	 */
	public boolean checkPieceInfo(String []a,String []b) {
		if (a[0].equals(b[0]) &&a[1].equals(b[1])){
			return true;
		}
		return false;
	}
	/**
	 * gives to class whose turn it is and what the respective string is
	 */
	public void getTurn() {
		if (turn%2==0) {
			this.turnenemy="b";
			this.turnname="w";
		}
		else {
			this.turnenemy="w";
			this.turnname="b";
		}
	}
	/**
	 *  checks to see if final space is movable to (universal across all pieces) <br>
	 * 1. You can move to any empty space <br>
	 * 2. You can move to place that your color is not occupying <br>
	 * @param num array of current piece (first two indexes), and destination place (final two indexes)
	 * @return boolean to see if final space is valid to move to
	 */
	public boolean isSpaceValid(int num[]) {
		if (board[num[2]][num[3]] ==null) {
			return true;
		}
		Piece temp=board[num[2]][num[3]];
		if (temp.color=="b" && turn%2==0) {
			return true;
		}
		if (temp.color=="w" && turn%2==1) {
			return true;
		}
		return false;
	}
	/**
	 *  checks to see if current piece can be moved (universal across all pieces) <br>
	 * 1. You cannot move from a place with no piece <br>
	 * 2. You cannot move other person's piece <br>
	 * @param num array of current piece (first two indexes), and destination place (final two indexes)
	 * @return boolean to see if current piece is valid to move
	 */
	public boolean isCurrentCorrect(int num[]) {
		if (board[num[0]][num[1]] ==null) {
			return false;
		}
		Piece temp=board[num[0]][num[1]];
		if (temp.color=="b" && turn%2!=1) {
			return false;
		}
		if (temp.color=="w" && turn%2!=0) {
			return false;
		}
		return true;
	}
	/** this executes the move, moving piece and getting rid of old piece if there
	 * 
	 * @param num array , 4 spaces x,y of moving piece, x,y of destination
	 * @param numm 1 if normal move, 2 if castling,3 if enpassant,4 if promotion
	 * @return Piece [][]
	 */
	public Piece [][] execute(int num[],int numm) {
		if (numm==1 ||numm==4 || numm==3) {
			Piece temp=board[num[0]][num[1]];
			temp.moved=true;
			board[num[2]][num[3]]=temp;
			board[num[0]][num[1]]=null;
		}
		else if (numm==2) {
			if (num[3]==6) {
				Piece temp=board[num[0]][num[1]];
				temp.moved=true;
				Piece temp2=board[num[0]][7];
				temp2.moved=true;
				board[num[0]][num[3]]=temp;
				board[num[0]][5]=temp2;
				board[num[0]][num[1]]=null;
				board[num[0]][7]=null;
			}
			else {
				Piece temp=board[num[0]][num[1]];
				temp.moved=true;
				Piece temp2=board[num[0]][0];
				temp2.moved=true;
				board[num[0]][num[3]]=temp;
				board[num[0]][3]=temp2;
				board[num[0]][num[1]]=null;
				board[num[0]][0]=null;
			}
		}
		return board;
	}
	/**
	 * print error message
	 */
	public void error() {
		System.out.println("Illegal move, try again");
		return;
	}
	/**
	 * if piece after enpassant has not been touched remove flag of it
	 */
	public void enPassantClear() {
		if (turn%2==0) {
			for (int i=0;i<8;i++) {
				if (board[3][i]!=null && board[3][i].type.equals("p") && board[3][i].color.equals("b")) {
					((Pawn)board[3][i]).enpassant=false;
					
				}
			}
		}
		else {
			for (int i=0;i<8;i++) {
				if (board[5][i]!=null && board[5][i].type.equals("p") && board[5][i].color.equals("w")) {
					((Pawn)getPiece(5,i)).enpassant=false;
				}
			}
		}
	}
	/**
	 * prints board
	 */
	public void printBoard() {
		int count=1;
		String temp[]= {"a","b","c","d","e","f","g","h"};
		for (int i=0;i<8;i++) {
			for (int j=0;j<8;j++) {
					if (board[i][j]!=null) {
						System.out.print(board[i][j].color+board[i][j].type);
						System.out.print(" ");
					}
					else {
						if (count%2==0) {
							System.out.print("##");
						}
						else {
							System.out.print("  ");
						}
						System.out.print(" ");
					}
					count++;
			}
			count++;
			System.out.println(8-i);
		}
		for (int i=0;i<8;i++) {
			System.out.print(" "+temp[i]+" ");
		}
		System.out.println();
	}
}
