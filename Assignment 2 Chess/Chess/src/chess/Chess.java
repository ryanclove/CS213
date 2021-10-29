package chess;
import components.Bishop;
import components.Board;
import components.Knight;
import components.Piece;
import components.Queen;
import components.Rook;
import components.Pawn;
import java.util.Scanner;
/**
 * 
 * @author jason dao, ryan coslove
 *
 */
public class Chess {
/**
 * main method, has scanner that will take input and run oneturn into it is false(when someone loses or quits)
 * @param args
 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Board game=new Board();
		game.printBoard();
		System.out.println();
		System.out.print("White's move:");
		Scanner s=new Scanner(System.in);
		String r=s.nextLine();
		while ((oneTurn(r,game))==true){
			System.out.println();
			if (game.turn%2==1) {
				System.out.print("Black's move:");
			}
			else {
				System.out.print("White's move:");
			}
			r=s.nextLine();
		}
		
	}
	/** counts how many pieces can move to a king (if num==null) or a certain coordinate , this tells you how many people are checking piece
	 * 
	 * @param board
	 * @param you, name of color you on attack
	 * @param enemy, name of king you want to get
	 * @param num, array of coordinate
	 * @param noking, exclude king from seeing if it can check a spot of king
	 * @return int of how many pieces are checking a spot or king
	 */
	public static int check(Piece [][]board,String you, String enemy,int num[], boolean noking) {
		int checknum=0;
		int num2[]=new int[4];
		if (num==null) {
			for(int k=0;k<8;k++) {
				for (int l=0;l<8;l++) {
					if (board[k][l]!=null&& board[k][l].color.equals(enemy) && board[k][l].type.equals("K")){
						num2[2]=k;
						num2[3]=l;
					}
				}
			}
		}
		else {
			num2[2]=num[0];
			num2[3]=num[1];
		}
		for(int k=0;k<8;k++) {
			for (int l=0;l<8;l++) {
				if (board[k][l]!=null&& board[k][l].color.equals(you)){
					num2[0]=k;
					num2[1]=l;
					if (board[k][l].type.equals("K")&& noking==true) {   //king on king
						continue;
					}
					if (board[k][l].move(board, num2)==1) {
						checknum++;
					}
					if (board[k][l].move(board, num2)==4) {
						checknum++;
					}
				}
			
			}
		}
		return checknum;
	}
	/**
	 * returns all coordinates of pieces checking current piece as a String
	 * @param board
	 * @param you name of color on attack
	 * @param enemy name of enemy you want to checkmate
	 * @param num int array of coordinates
	 * @return String
	 */
	public static String checkCoordinates(Piece [][]board,String you, String enemy,int num[]) {
		int num2[]=new int[4];
		String i="";
		if (num==null) {
			for(int k=0;k<8;k++) {
				for (int l=0;l<8;l++) {
					if (board[k][l]!=null&& board[k][l].color.equals(enemy) && board[k][l].type.equals("K")){
						num2[2]=k;
						num2[3]=l;
					}
				}
			}
		}
		else {
			num2[2]=num[0];
			num2[3]=num[1];
		}
		for(int k=0;k<8;k++) {
			for (int l=0;l<8;l++) {
				if (board[k][l]!=null&& board[k][l].color.equals(you)){
					num2[0]=k;
					num2[1]=l;
					if (board[k][l].type.equals("K")&& num==null) {  //king on king
						continue;
					}
					if (board[k][l].move(board, num2)==1) {
						i+=String.valueOf(k);
						i+=String.valueOf(l);
						
					}
					if (board[k][l].move(board, num2)==4) {
						i+=String.valueOf(k);
						i+=String.valueOf(l);
					}
				}
			}
			
		}
		return i;
	}	
	/**
	 * check to see if a move will put your own king into attack
	 * @param board
	 * @param num array of coordinates
	 * @param a, what kind of move is it, regular, castling, en passant, or promotion
	 * @param string to choose what promtion you can
	 * @return boolean true if move is valid, false if not
	 */
	public static boolean selfCheck(Board board,int num[], int a, String s) {   
		Piece queue[]=new Piece[2];
		if (check(board.board,board.turnenemy,board.turnname,null, false)!=0 && a==2) {
			return false;
		}
		if (board.getPiece(num[2],num[3])!=null) {
			queue[1]=board.getPiece(num[2],num[3]);
			queue[0]=board.getPiece(num[0],num[1]);
			boolean before=queue[0].moved;
			board.execute(num,a);
			if (check(board.board,board.turnenemy,board.turnname,null, false)!=0) {
				board.board[num[0]][num[1]]=null;
				board.board[num[0]][num[1]]=queue[0];
				board.board[num[0]][num[1]].moved=before;
				board.board[num[2]][num[3]]=null;
				board.board[num[2]][num[3]]=queue[1];
				return false;
			}	
		}
		else {
			queue[0]=board.getPiece(num[0],num[1]);
			boolean ep=false;
			if (queue[0].type.equals("p")) {
				ep=((Pawn)queue[0]).enpassant;
			}
			boolean before=queue[0].moved;
			board.execute(num,a);
			if (check(board.board,board.turnenemy,board.turnname,null, false)!=0) {
				if (a==2) {
					if (num[3]==6) {
						board.board[num[0]][num[1]]=board.board[num[2]][num[3]];
						board.board[num[0]][num[1]].moved=false;
						board.board[num[0]][7]=board.board[num[0]][5];
						board.board[num[0]][7].moved=false;
						board.board[num[0]][5]=null;
						board.board[num[2]][num[3]]=null;
					}else {
						board.board[num[0]][num[1]]=board.board[num[2]][num[3]];
						board.board[num[0]][num[1]].moved=false;
						board.board[num[0]][0]=board.board[num[0]][3];
						board.board[num[0]][0].moved=false;
						board.board[num[0]][3]=null;
						board.board[num[2]][num[3]]=null;
					}
				}
				else {
					board.board[num[2]][num[3]]=null;
					board.board[num[0]][num[1]]=queue[0];
					board.board[num[0]][num[1]].moved=before;
					if (queue[0].type.equals("p")) {
						((Pawn)board.board[num[0]][num[1]]).enpassant=ep;
					}
				}
				return false;
			}
		}
		if (a==3) {
			if (board.turnname.equals("b")) {
				board.board[num[2]-1][num[3]]=null;
			}
			else {
				board.board[num[2]+1][num[3]]=null;
			}
		}
		if (a==4) {
			if (s.equals("")) {
				board.board[num[2]][num[3]]=new Queen("Q",board.turnname);
			}
			else if (s.equals("N")) {
				board.board[num[2]][num[3]]=new Knight("N",board.turnname);
			}
			else if (s.equals("R")) {
				board.board[num[2]][num[3]]=new Rook("R",board.turnname);
			}
			else if (s.equals("B")) {
				board.board[num[2]][num[3]]=new Bishop("B",board.turnname);
			}
		}
		return true;
				
	}
	/**
	 * 
	 * @param board
	 * @param num int array of 4[], simulates a move to check if there is checkmate (if a piece moves to go after checking piece, will king be in checkmate)
	 * @return
	 */
	public static boolean selfCheckMate(Board board,int num[]) {   
		boolean t=false;
		Piece queue[]=new Piece[2];
		if (board.getPiece(num[2],num[3])!=null) {
			queue[1]=board.getPiece(num[2],num[3]);
			queue[0]=board.getPiece(num[0],num[1]);
			boolean stuff=board.getPiece(num[0],num[1]).moved;
			board.execute(num,1);
			if (check(board.board,board.turnname,board.turnenemy,null, false)!=0) {
				t=true;
			}	
			board.board[num[0]][num[1]]=null;
			board.board[num[0]][num[1]]=queue[0];
			board.board[num[0]][num[1]].moved=stuff;
			board.board[num[2]][num[3]]=null;
			board.board[num[2]][num[3]]=queue[1];
		}
		return t;
	}
	/**
	 * Checks to see if there is checkmate , if 1 person, check if you can block them, capture them, or run away
	 * if 2 see if you can run away
	 * @param board
	 * @return boolean to see if there is checkmate or not
	 */
	public static boolean checkMate(Board board) {
		int num[]=new int [2];
		for(int k=0;k<8;k++) {
			for (int l=0;l<8;l++) {
				if (board.board[k][l]!=null&& board.board[k][l].color.equals(board.turnenemy) && board.board[k][l].type.equals("K")){
					num[0]=k;
					num[1]=l;
				}
			}
		}
		if (check(board.board,board.turnname,board.turnenemy,null,false)!=0) {
			if (check(board.board,board.turnname,board.turnenemy,null,false)==1) {
				String s=checkCoordinates(board.board,board.turnname, board.turnenemy,null);
				int numv[]=new int[2];
				int numw[]=new int[4];
				numw[0]=num[0];
				numw[1]=num[1];
				numw[2]=Integer.parseInt(s.substring(0, 1));
				numw[3]=Integer.parseInt(s.substring(1, 2));
				numv[0]=Integer.parseInt(s.substring(0, 1));
				numv[1]=Integer.parseInt(s.substring(1, 2));
				if (board.getPiece(num[0], num[1]).move(board.board, numw)==1) {
					if (check(board.board,board.turnname,board.turnenemy,numv,false)==0) {
						return false;
					}
				}
				if (check(board.board,board.turnenemy,board.turnname,numv,true)!=0){  //check if movement will cause another checkmate
					int numu[]=new int[4];
					String t=checkCoordinates(board.board,board.turnenemy,board.turnname,numv);
					for (int i=0;i<checkCoordinates(board.board,board.turnenemy,board.turnname,numv).length();i+=2) {
						numu[0]=Integer.parseInt(t.substring(i, i+1));
						numu[1]=Integer.parseInt(t.substring(i+1, i+2));
						numu[2]=numv[0];
						numu[3]=numv[1];
						if (selfCheckMate(board,numu)==false) {
							return false;
						}
					}
				}
				int tempnum2=numw[2];
				int tempnum1=numw[3];
				if (numw[0]==numw[2]) {
					for (int i=0;i<Math.abs(numw[2]-numw[0])-1;i++) {   //checks to see if there is chess piece in way of path to spot
						if (numw[2]-numw[0]<0) {
							tempnum2++;
						}
						else {
							tempnum2--;
						}
						int othertemp[]=new int[2];
						othertemp[0]=tempnum2;
						othertemp[1]=tempnum1;
						if (check(board.board,board.turnenemy,board.turnname,othertemp,true)!=0) {
							return false;
						}
						
					}
				}
				else if (numw[1]==numw[3]) {
					for (int i=0;i<Math.abs(numw[3]-numw[1])-1;i++) {   //checks to see if there is chess piece in way of path to spot
						if (numw[3]-numw[1]<0) {
							tempnum1++;
						}
						else {
							tempnum1--;
						}
						int othertemp[]=new int[2];
						othertemp[0]=tempnum2;
						othertemp[1]=tempnum1;
						if (check(board.board,board.turnenemy,board.turnname,othertemp,true)!=0) {
							return false;
						}
					}
				}
				else  if (Math.abs(numw[2]-numw[0])==Math.abs(numw[3]-numw[1])){
					for (int i=0;i<Math.abs(numw[2]-numw[0])-1;i++) {   //checks to see if there is chess piece in way of path to spot
						if (numw[2]-numw[0]<0) {
							tempnum2++;
						}
						else {
							tempnum2--;
						}
						if (numw[3]-numw[1]<0) {
							tempnum1++;
						}
						else {
							tempnum1--;
						}
						int othertemp[]=new int[2];
						othertemp[0]=tempnum2;
						othertemp[1]=tempnum1;
						if (check(board.board,board.turnenemy,board.turnname,othertemp,true)!=0) {
							return false;
						}
					}
					
				}
			}
			int stuff[][]= {{1,1},{0,1},{-1,-1},{1,0},{-1,0},{0,-1},{1,-1},{-1,1}};
			for (int i=0; i<stuff.length;i++) {
				if(board.valid(num[0]+stuff[i][0], num[1]+stuff[i][1])==true) {
					int number[]=new int[2];
					number[0]=num[0]+stuff[i][0];
					number[1]=num[1]+stuff[i][1];
					if (check(board.board,board.turnname,board.turnenemy,number,false)==0) {
						if (board.getPieceInfo(num[0]+stuff[i][0], num[1]+stuff[i][1])==null){
							return false;
						}
						if (board.getPieceInfo(num[0]+stuff[i][0], num[1]+stuff[i][1])[1].equals(board.turnname)){
							return false;
						}
					}
				}
			}	
			}
		else {
			return false;
		}
		return true;
	}
	/**
	 * simulates one turn of chess, does movement, check for draw, resignation, does checks to see if move valid and sees if checkmate occured
	 * @param input (what person typed), 
	 * @param board
	 * @return boolean (false if game ends, true if game continues)
	 */
	public static boolean oneTurn(String input, Board board) {
		board.getTurn();
		if (board.draw==true) {
			System.out.println("draw");
			return false;
		}
		int num[]=new int[4];
		if (input.equalsIgnoreCase("resign")) {
			if (board.turnenemy.equals("b")) {
				System.out.println("Black wins");
			}
			else {
				System.out.println("White wins");
			}
			return false;
		}
		if (input.length()>10) {
			board.draw=true;
		}
		//translate string 
		String progression="";
		String one=input.substring(0,2);
		String two=input.substring(3,5);
		if (input.length()>5 && input.length()<10) {
			progression=input.substring(6,7);
		}
		String oneone=one.substring(0,1);
		String onetwo=one.substring(1,2);
		String twoone=two.substring(0,1);
		String twotwo=two.substring(1,2);
		if (oneone.equals("a")) {
			num[1]=0;
		}
		else if (oneone.equals("b")) {
			num[1]=1;
		}
		else if (oneone.equals("c")) {
			num[1]=2;
		}
		else if (oneone.equals("d")) {
			num[1]=3;
		}
		else if (oneone.equals("e")) {
			num[1]=4;
		}
		else if (oneone.equals("f")) {
			num[1]=5;
		}
		else if (oneone.equals("g")) {
			num[1]=6;
		}
		else if (oneone.equals("h")) {
			num[1]=7;
		}
		else {
			board.error();
			return true;
		}
		int i=Integer.parseInt(onetwo);
		if (i>=1 && i<=8) {
			num[0]=8-i;
		}
		else {
			board.error();
			return true;
		}
		if (twoone.equals("a")) {
			num[3]=0;
		}
		else if (twoone.equals("b")) {
			num[3]=1;
		}
		else if (twoone.equals("c")) {
			num[3]=2;
		}
		else if (twoone.equals("d")) {
			num[3]=3;
		}
		else if (twoone.equals("e")) {
			num[3]=4;
		}
		else if (twoone.equals("f")) {
			num[3]=5;
		}
		else if (twoone.equals("g")) {
			num[3]=6;
		}
		else if (twoone.equals("h")) {
			num[3]=7;
		}
		else {
			board.error();
			return true;
		}
		int j=Integer.parseInt(twotwo);
		if (j>=1 && j<=8) {
			num[2]=8-j;
		}
		else {
			board.error();
			return true;
		}
		if (board.isSpaceValid(num)==false || board.isCurrentCorrect(num)==false) {
			board.error();
			return true;
		}
		Piece current=board.getPiece(num[0], num[1]);
		int code=0;
		if (current.move(board.board, num)==0) {
			board.error();
			return true;
		}
		else if (current.move(board.board, num)==1) {
			code=1;
		}
		else if (current.move(board.board, num)==2) {
			code=2;
		}
		else if (current.move(board.board, num)==3) {
			code=3;
		}
		else if (current.move(board.board, num)==4) {
			code=4;
		}
		if (selfCheck(board,num,code,progression)==false) {
			board.error();
			return true;
		}
		if (checkMate(board)==true) {
			System.out.println("Checkmate");
			if (board.turnenemy.equals("b")) {
				System.out.println("White wins");
			}
			else {
				System.out.println("Black wins");
			}
			return false;
		}
		if (check(board.board,board.turnname,board.turnenemy,null,false)!=0) {
			System.out.println("Check");
		}
		System.out.println();
		board.enPassantClear();
		board.printBoard();
		board.turn++;
		return true;
		
	}

}
