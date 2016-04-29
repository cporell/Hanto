package hanto.studentCPBP.common;

import hanto.common.MoveResult;

public class EndConditionPlayNRounds implements GenericHantoRuleCollection.IEndCondition
{
	private int moveCount;
	
	public EndConditionPlayNRounds(int roundCount) 
	{
		this.moveCount = roundCount * 2 - 1;
	}
	
	@Override
	public MoveResult checkForResult(IHantoGameState state) 
	{
		return state.getMoveNumber() >= moveCount ? MoveResult.DRAW : MoveResult.OK;
	}
}
