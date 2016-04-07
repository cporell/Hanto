package hanto.studentCPBP.common;

import hanto.common.HantoCoordinate;

public class PlaceMover implements IHantoMover
{
	private HantoCommonPiece piece;
	private HantoCoordinate at;
	
	public PlaceMover(HantoCommonPiece piece, HantoCoordinate at)
	{
		this.piece = piece;
		this.at = at;
	}
	
	@Override
	public boolean iterateMove(IHantoBoard board)
	{
		board.addPiece(piece, at);
		return false;
	}

}
