package hanto.studentCPBP.common;

import hanto.common.HantoException;

public class RuleMustStartAtOrigin implements GenericHantoRuleCollection.IRule
{
	private GenericHantoRuleCollection rules;
	
	@Override
	public void check(IHantoGameState state) throws HantoException
	{
		if(state.getMoveNumber() == 0 && state.getPieces(new HantoCoordinateImpl(0, 0)).length == 0)
		{
			throw new HantoException("Must start at origin.");
		}
	}

}
