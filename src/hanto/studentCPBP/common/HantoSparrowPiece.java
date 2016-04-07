package hanto.studentCPBP.common;

import hanto.common.HantoCoordinate;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;

public class HantoSparrowPiece extends HantoCommonPiece {

	public HantoSparrowPiece(HantoPlayerColor color)
	{
		super(color, HantoPieceType.SPARROW);
	}

	@Override
	public IHantoMover createPlaceMover(HantoCoordinate at) 
	{
		return new PlaceMover(this, at);
	}

	@Override
	public IHantoMover createWalkMover() {
		// TODO Auto-generated method stub
		return null;
	}

}
