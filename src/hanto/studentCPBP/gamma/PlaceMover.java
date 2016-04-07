package hanto.studentCPBP.gamma;

import hanto.common.HantoCoordinate;
import hanto.common.HantoPiece;
import hanto.studentCPBP.common.HantoCommonPiece;
import hanto.studentCPBP.common.IHantoBoard;
import hanto.studentCPBP.common.IHantoMover;

public class PlaceMover implements IHantoMover
{
	private HantoCommonPiece piece;
	private HantoCoordinate at;
	
	public HantoCoordinate getTargetLocation()
	{
		return at;
	}
	
	public HantoCommonPiece getPiece()
	{
		return piece;
	}
	
	public PlaceMover(HantoCommonPiece piece, HantoCoordinate at)
	{
		this.piece = piece;
		this.at = at;
	}
	
	@Override
	public boolean iterateMove(IHantoBoard board)
	{
		board.addPiece(piece, at);
		
		HantoCoordinate[] adjacent = board.getAdjacent(at);
		int ofSameColor = 0;
		for(HantoCoordinate coord : adjacent)
		{
			HantoPiece[] pieces = board.getPieces(at);
			for(HantoPiece adjPiece : pieces)
			{
				if(adjPiece.getColor() == piece.getColor())
					ofSameColor++;
			}
		}
		
		return false;
	}

}
