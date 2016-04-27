package hanto.studentCPBP.common;

import hanto.common.MoveResult;

public class EndConditionPlayNRounds implements GenericHantoRuleCollection.IEndCondition
{
	private GenericHantoRuleCollection rules;
	private int moveCount;
	
	public EndConditionPlayNRounds(GenericHantoRuleCollection rules, int roundCount) 
	{
		this.rules = rules;
		this.moveCount = roundCount * 2 - 1;
	}
	
	@Override
	public MoveResult checkForResult(IHantoBoard board) 
	{
		return rules.getMoveNumber() >= moveCount ? MoveResult.DRAW : MoveResult.OK;
	}
}
