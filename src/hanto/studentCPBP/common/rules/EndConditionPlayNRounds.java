package hanto.studentCPBP.common.rules;

import hanto.common.MoveResult;
import hanto.studentCPBP.common.GenericHantoRuleCollection;
import hanto.studentCPBP.common.IHantoGameState;
import hanto.studentCPBP.common.GenericHantoRuleCollection.IEndCondition;

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
