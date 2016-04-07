package hanto.studentCPBP.common;

import hanto.common.HantoCoordinate;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;

public class PlaceholderPiece extends HantoCommonPiece
{

	public PlaceholderPiece(HantoPlayerColor color, HantoPieceType type)
	{
		super(color, type);
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
