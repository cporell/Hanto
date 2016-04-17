package hanto.studentCPBP.delta;

import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.studentCPBP.common.HantoButterflyPiece;
import hanto.studentCPBP.common.HantoCommonPiece;
import hanto.studentCPBP.common.HantoCrabPiece;
import hanto.studentCPBP.common.HantoSparrowPiece;
import hanto.studentCPBP.common.IHantoPieceFactory;
import hanto.studentCPBP.gamma.PlaceholderPiece;

public class DeltaHantoPieceFactory implements IHantoPieceFactory
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
			return new HantoCrabPiece(color);
		case CRANE:
		case DOVE:
		case HORSE:
		default:
			return new PlaceholderPiece(color, type);
		
		}
	}

}
