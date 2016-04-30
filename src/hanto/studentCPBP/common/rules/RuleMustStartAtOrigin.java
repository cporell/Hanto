package hanto.studentCPBP.common.rules;

import hanto.common.HantoCoordinate;
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

	@Override
	public boolean isValidMoveLocation(IHantoGameState state, HantoCoordinate location) 
	{
		return state.getPieces().length == 0 && (location.getX() == 0 && location.getY() == 0);
	}

	@Override
	public boolean isValidSearchLocation(IHantoGameState state, HantoCoordinate location) 
	{
		return state.getPieces().length == 0 && (location.getX() == 0 && location.getY() == 0);
	}

}
