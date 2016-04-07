package hanto.studentCPBP.common;

import hanto.common.HantoCoordinate;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;

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
	public IHantoMover createWalkMover() 
	{
		return null;
	}

}
