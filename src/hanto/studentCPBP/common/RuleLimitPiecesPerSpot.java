package hanto.studentCPBP.common;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;

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

}
