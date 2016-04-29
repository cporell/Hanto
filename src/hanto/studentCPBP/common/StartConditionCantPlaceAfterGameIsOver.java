package hanto.studentCPBP.common;

import hanto.common.HantoException;

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
