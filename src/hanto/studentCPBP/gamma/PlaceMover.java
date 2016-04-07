package hanto.studentCPBP.gamma;

import hanto.common.HantoCoordinate;
import hanto.studentCPBP.common.HantoCommonPiece;
import hanto.studentCPBP.common.IHantoBoard;
import hanto.studentCPBP.common.IHantoMover;

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
