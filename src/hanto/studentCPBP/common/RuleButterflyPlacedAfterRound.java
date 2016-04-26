package hanto.studentCPBP.common;

import hanto.common.HantoException;

public class RuleButterflyPlacedAfterRound implements GenericHantoRuleCollection.IRule
{
	private GenericHantoRuleCollection rules;
	
	@Override
	public void checkBoard(IHantoBoard board) throws HantoException {
		if(rules.getTurnNumber() == 4)
		{
			if(rules.getCurrentPlayer() == rules.getBlueHand())
			{
				if(!rules.getBlueHand().getButterflyPlaced())
				{
					throw new HantoException("Blue did not place butterfly by 4th turn.");	
				}
			}
			else
			{
				if(!rules.getRedHand().getButterflyPlaced())
				{
					throw new HantoException("Red did not place butterfly by 4th turn.");
				}
			}
		}
	}
}
