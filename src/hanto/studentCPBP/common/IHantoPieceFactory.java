package hanto.studentCPBP.common;

import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;

public interface IHantoPieceFactory
{
	HantoCommonPiece createPiece(HantoPieceType type, HantoPlayerColor color);
}
