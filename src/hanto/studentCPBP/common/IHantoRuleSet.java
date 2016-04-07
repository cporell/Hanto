package hanto.studentCPBP.common;

import hanto.common.HantoException;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;

public interface IHantoRuleSet
{
	MoveResult checkBoard(IHantoBoard board) throws HantoException;
	HantoPlayerColor getCurrentTurn();
	void beginTurn();
	void endTurn();
	int getTurnNumber();
	IHantoMoverValidator createMoverValidator(IHantoMover mover);
}
