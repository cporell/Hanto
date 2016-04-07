package hanto.studentCPBP.gamma;

import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.studentCPBP.common.HantoCommonPiece;
import hanto.studentCPBP.common.IHantoPieceFactory;

public class GammaHantoPieceFactory implements IHantoPieceFactory
{

	@Override
	public HantoCommonPiece createPiece(HantoPieceType type, HantoPlayerColor color) 
	{
		switch(type)
		{
		case BUTTERFLY:
			return new HantoButterflyPiece(color);
		case SPARROW:
			return new HantoSparrowPiece(color);
		case CRAB:
		case CRANE:
		case DOVE:
		case HORSE:
		default:
			return new PlaceholderPiece(color, type);
		
		}
	}

}
