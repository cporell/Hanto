package hanto.studentCPBP.delta;

import hanto.common.HantoException;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.studentCPBP.common.IHantoBoard;
import hanto.studentCPBP.common.IHantoMover;
import hanto.studentCPBP.common.IHantoMoverValidator;
import hanto.studentCPBP.common.IHantoRuleSet;

public class DeltaHantoRuleSet implements IHantoRuleSet
{

	@Override
	public void checkBoard(IHantoBoard board) throws HantoException 
	{
	}

	@Override
	public HantoPlayerColor getCurrentTurn() 
	{
		return null;
	}

	@Override
	public void beginTurn(IHantoBoard board) throws HantoException 
	{
	}

	@Override
	public MoveResult endTurn(IHantoBoard board) throws HantoException 
	{
		return null;
	}

	@Override
	public int getTurnNumber() 
	{
		return 0;
	}

	@Override
	public IHantoMoverValidator createMoverValidator(IHantoMover mover) 
	{
		return null;
	}

}
