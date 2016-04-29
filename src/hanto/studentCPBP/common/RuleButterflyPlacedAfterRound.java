package hanto.studentCPBP.common;

import hanto.common.HantoException;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;

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
	public void check(IHantoGameState state) throws HantoException 
	{
		if(rules.getTurnNumber(state) == round)
		{
			if(rules.getCurrentPlayer(state) == HantoPlayerColor.BLUE)
			{
				if(state.getPieces(HantoPlayerColor.BLUE, HantoPieceType.BUTTERFLY).length == 0)
				{
					throw new HantoException("Blue did not place butterfly by turn " + round + ".");	
				}
			}
			else
			{
				if(state.getPieces(HantoPlayerColor.RED, HantoPieceType.BUTTERFLY).length == 0)
				{
					throw new HantoException("Red did not place butterfly by turn " + round + ".");
				}
			}
		}
	}
}
