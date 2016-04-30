package hanto.studentCPBP.common.rules;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.studentCPBP.common.GenericHantoRuleCollection;
import hanto.studentCPBP.common.IHantoGameState;
import hanto.studentCPBP.common.GenericHantoRuleCollection.IRule;

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

	@Override
	public boolean isValidMoveLocation(IHantoGameState state, HantoCoordinate location) 
	{
		return true;
	}

	@Override
	public boolean isValidSearchLocation(IHantoGameState state, HantoCoordinate location) 
	{
		return true;
	}
}
