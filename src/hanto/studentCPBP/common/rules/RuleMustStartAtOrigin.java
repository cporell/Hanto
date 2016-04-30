package hanto.studentCPBP.common.rules;

import hanto.common.HantoException;
import hanto.studentCPBP.common.GenericHantoRuleCollection;
import hanto.studentCPBP.common.HantoCoordinateImpl;
import hanto.studentCPBP.common.IHantoGameState;
import hanto.studentCPBP.common.GenericHantoRuleCollection.IRule;

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
