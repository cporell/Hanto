package hanto.studentCPBP.common;

import hanto.common.HantoCoordinate;
import hanto.tournament.HantoMoveRecord;

public class ZeroPieceMoveGenerator implements IHantoPieceMoveGenerator {

	@Override
	public HantoMoveRecord[] getValidMovesFrom(HantoCoordinate[] validLocations, IHantoGameState state) 
	{
		return new HantoMoveRecord[0];
	}

}
