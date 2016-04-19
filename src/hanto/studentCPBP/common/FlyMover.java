package hanto.studentCPBP.common;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;

public class FlyMover implements IHantoMover
{
	private HantoCommonPiece piece;
	private HantoCoordinate to;
	
	public HantoCoordinate getTargetLocation()
	{
		return new HantoCoordinateImpl(to);
	}
	
	public HantoCommonPiece getPiece()
	{
		return piece;
	}
	
	/**
	 * Builds a WalkMover for the given piece and location
	 * @param piece The piece we are placing
	 * @param to The location we are placing at
	 */
	public FlyMover(HantoCommonPiece piece, HantoCoordinate to) 
	{
		this.piece = piece;
		this.to = to; 
	}
	
	@Override
	public boolean iterateMove(IHantoBoard board) throws HantoException 
	{
		board.movePiece(piece, to);
		
		return false;
	}

}
