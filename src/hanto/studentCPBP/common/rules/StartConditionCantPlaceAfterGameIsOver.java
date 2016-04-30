package hanto.studentCPBP.common.rules;

import hanto.common.HantoException;
import hanto.studentCPBP.common.GenericHantoRuleCollection;
import hanto.studentCPBP.common.IHantoGameState;
import hanto.studentCPBP.common.GenericHantoRuleCollection.IStartCondition;

public class StartConditionCantPlaceAfterGameIsOver implements GenericHantoRuleCollection.IStartCondition
{	
	@Override
	public void check(IHantoGameState state) throws HantoException 
	{
		if(state.isGameOver())
		{
			throw new HantoException("Can't continue playing if game is already over.");
		}
	}

}
