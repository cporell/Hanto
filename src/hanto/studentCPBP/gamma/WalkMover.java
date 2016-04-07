package hanto.studentCPBP.gamma;

import java.util.Arrays;
import java.util.HashSet;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.studentCPBP.common.HantoCommonPiece;
import hanto.studentCPBP.common.HantoCoordinateImpl;
import hanto.studentCPBP.common.IHantoBoard;
import hanto.studentCPBP.common.IHantoMover;

public class WalkMover implements IHantoMover
{
	private HantoCommonPiece piece;
	private HantoCoordinate to;
	
	public WalkMover(HantoCommonPiece piece, HantoCoordinate to) 
	{
		this.piece = piece;
		this.to = to; 
	}
	
	@Override
	public boolean iterateMove(IHantoBoard board) throws HantoException 
	{
		if(to.equals(board.getPieceLocation(piece)))
		{
			throw new HantoException("Cannot move to the same location.");
		}
		
		HashSet<HantoCoordinate> adjacent = new HashSet<>(Arrays.asList(board.getAdjacent(board.getPieceLocation(piece))));
		if(!adjacent.contains(new HantoCoordinateImpl(to)))
		{
			throw new HantoException("Cannot walk more than one space.");
		}
		
		board.movePiece(piece, to);
		
		return false;
	}

}
