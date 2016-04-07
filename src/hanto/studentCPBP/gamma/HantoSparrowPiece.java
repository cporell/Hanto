package hanto.studentCPBP.gamma;

import hanto.common.HantoCoordinate;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.studentCPBP.common.HantoCommonPiece;
import hanto.studentCPBP.common.IHantoMover;

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
	public IHantoMover createWalkMover(HantoCoordinate to)
	{
		// TODO Auto-generated method stub
		return new WalkMover(this, to);
	}

}
