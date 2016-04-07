package hanto.studentCPBP.gamma;

import hanto.common.HantoCoordinate;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.studentCPBP.common.HantoCommonPiece;
import hanto.studentCPBP.common.IHantoMover;

public class HantoButterflyPiece extends HantoCommonPiece
{

	public HantoButterflyPiece(HantoPlayerColor color)
	{
		super(color, HantoPieceType.BUTTERFLY);
	}

	@Override
	public IHantoMover createPlaceMover(HantoCoordinate at) 
	{
		return new PlaceMover(this, at);
	}

	@Override
	public IHantoMover createWalkMover(HantoCoordinate to) 
	{
		return new WalkMover(this, to);
	}

}
