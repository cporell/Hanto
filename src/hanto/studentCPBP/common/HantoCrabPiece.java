package hanto.studentCPBP.common;

import hanto.common.HantoCoordinate;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;

public class HantoCrabPiece extends HantoCommonPiece {

	/**
	 * Builds a Sparrow piece
	 * @param color Owner of this piece
	 */
	public HantoCrabPiece(HantoPlayerColor color)
	{
		super(color, HantoPieceType.CRAB);
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
