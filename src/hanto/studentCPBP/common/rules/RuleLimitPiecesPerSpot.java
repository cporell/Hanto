package hanto.studentCPBP.common.rules;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.studentCPBP.common.GenericHantoRuleCollection;
import hanto.studentCPBP.common.IHantoGameState;
import hanto.studentCPBP.common.GenericHantoRuleCollection.IRule;

public class RuleLimitPiecesPerSpot implements GenericHantoRuleCollection.IRule
{
	private int max;
	
	public RuleLimitPiecesPerSpot(int max)
	{
		this.max = max;
	}
	
	@Override
	public void check(IHantoGameState state) throws HantoException
	{
		for(HantoCoordinate coord : state.getAllTakenLocations())
		{
			if(state.getPieces(coord).length > max)
			{
				throw new HantoException("Cannot put more than " + max + " pieces on a space");
			}
		}
	}

	@Override
	public boolean isValidMoveLocation(IHantoGameState state, HantoCoordinate location) 
	{
		return state.getPieces(location).length < max;
	}

	@Override
	public boolean isValidSearchLocation(IHantoGameState state, HantoCoordinate location) 
	{
		return true;
	}

}
