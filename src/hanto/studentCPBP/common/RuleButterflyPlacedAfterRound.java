package hanto.studentCPBP.common;

import hanto.common.HantoException;

public class RuleButterflyPlacedAfterRound implements GenericHantoRuleCollection.IRule
{
	private GenericHantoRuleCollection rules;
	private int round;
	
	public RuleButterflyPlacedAfterRound(GenericHantoRuleCollection rules, int round)
	{
		this.rules = rules;
		this.round = round;
	}
	
	@Override
	public void checkBoard(IHantoBoard board) throws HantoException 
	{
		if(rules.getTurnNumber() == round)
		{
			if(rules.getCurrentPlayer() == rules.getBlueHand())
			{
				if(!rules.getBlueHand().getButterflyPlaced())
				{
					throw new HantoException("Blue did not place butterfly by turn " + round + ".");	
				}
			}
			else
			{
				if(!rules.getRedHand().getButterflyPlaced())
				{
					throw new HantoException("Red did not place butterfly by turn " + round + ".");
				}
			}
		}
	}
}
