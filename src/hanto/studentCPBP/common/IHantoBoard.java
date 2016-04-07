package hanto.studentCPBP.common;

import hanto.common.HantoCoordinate;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.MoveResult;

public interface IHantoBoard
{	
	void addPiece(HantoCommonPiece piece, HantoCoordinate at);
	void movePiece(HantoCommonPiece piece, HantoCoordinate to);
	HantoCommonPiece[] getPieces(HantoCoordinate at);
	HantoCoordinate getPieceLocation(HantoCommonPiece piece);
	HantoCoordinate[] getAllTakenLocations();
	HantoCoordinate[] getAdjacent(HantoCoordinate at);
}
