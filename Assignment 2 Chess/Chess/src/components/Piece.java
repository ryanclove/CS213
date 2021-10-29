package components;
/**
 * abstract class to group all pieces together, all pieces have type(rook,queen etc), color (black or white) , whether they have been moved
 *  and checks to see if moves is valid in move method
 * @author jason dao,ryan coslove
 *  
 */
public abstract class Piece {
	public String type;
	public String color;
	public boolean moved=false;
	/**
	 * check to see if move is valid for any piece
	 * @param board
	 * @param a
	 * @return
	 */
	public abstract int move(Piece [][]board,int a[]);
}
