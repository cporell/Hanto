package hanto.studentCPBP.common;

import hanto.common.HantoException;

public class StartConditionCantPlaceAfterGameIsOver implements GenericHantoRuleCollection.IStartCondition
{
	private GenericHantoRuleCollection rules;
	
	public StartConditionCantPlaceAfterGameIsOver(GenericHantoRuleCollection rules)
	{
		this.rules = rules;
	}
	
	@Override
	public void check(IHantoBoard board) throws HantoException 
	{
		if(rules.getIsGameOver())
		{
			throw new HantoException("Can't continue playing if game is already over.");
		}
	}

}
