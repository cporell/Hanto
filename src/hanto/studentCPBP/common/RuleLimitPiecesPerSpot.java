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
	public void checkBoard(IHantoBoard board) throws HantoException
	{
		for(HantoCoordinate coord : board.getAllTakenLocations())
		{
			if(board.getPieces(coord).length > max)
			{
				throw new HantoException("Cannot put more than " + max + " pieces on a space");
			}
		}
	}

}
