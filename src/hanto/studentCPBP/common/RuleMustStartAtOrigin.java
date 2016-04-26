package hanto.studentCPBP.common;

import hanto.common.HantoException;

public class RuleMustStartAtOrigin implements GenericHantoRuleCollection.IRule
{
	private GenericHantoRuleCollection rules;
	
	public RuleMustStartAtOrigin(GenericHantoRuleCollection rules) 
	{
		this.rules = rules;
	}
	
	@Override
	public void checkBoard(IHantoBoard board) throws HantoException
	{
		if(rules.getMoveNumber() == 0 && board.getPieces(new HantoCoordinateImpl(0, 0)).length == 0)
		{
			throw new HantoException("Must start at origin.");
		}
	}

}
