package hanto.studentCPBP.gamma;

import hanto.common.HantoCoordinate;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.studentCPBP.common.HantoCommonPiece;
import hanto.studentCPBP.common.IHantoMover;

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
	public IHantoMover createWalkMover(HantoCoordinate to) 
	{
		return null;
	}

}
