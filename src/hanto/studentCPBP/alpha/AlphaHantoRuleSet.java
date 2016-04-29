package hanto.studentCPBP.alpha;

import hanto.common.HantoException;
import hanto.common.HantoPlayerColor;
import hanto.studentCPBP.common.EndConditionPlayNRounds;
import hanto.studentCPBP.common.GenericHantoRuleCollection;
import hanto.studentCPBP.common.IHantoGameState;
import hanto.studentCPBP.common.IHantoMover;
import hanto.studentCPBP.common.IHantoMoverValidator;
import hanto.studentCPBP.common.PlaceMover;
import hanto.studentCPBP.common.RuleLimitPiecesPerSpot;
import hanto.studentCPBP.common.RuleMustBeContinousBoard;
import hanto.studentCPBP.common.RuleMustStartAtOrigin;
import hanto.studentCPBP.common.StartConditionCantPlaceAfterGameIsOver;

public class AlphaHantoRuleSet extends GenericHantoRuleCollection
{
	private class AlphaPlacementMoverValidator implements IHantoMoverValidator
	{
		@Override
		public void checkIteration(IHantoGameState state) throws HantoException 
		{
			
		}
		
	}
	
	private class AlphaInvalidMoverValidator implements IHantoMoverValidator
	{
		@Override
		public void checkIteration(IHantoGameState state) throws HantoException 
		{
			throw new HantoException("Invalid movement input.");
		}
		
	}
	
	public AlphaHantoRuleSet(HantoPlayerColor startingPlayer) 
	{
		super(startingPlayer);
		
		addStartCondition(new StartConditionCantPlaceAfterGameIsOver());
		
		addRule(new RuleMustStartAtOrigin());
		addRule(new RuleLimitPiecesPerSpot(1));
		addRule(new RuleMustBeContinousBoard());
		
		addEndCondition(new EndConditionPlayNRounds(1));
	}

	@Override
	public IHantoMoverValidator createMoverValidator(IHantoMover mover) 
	{
		if(mover instanceof PlaceMover)
		{
			return new AlphaPlacementMoverValidator();
		}
		
		return new AlphaInvalidMoverValidator();
	}

	@Override
	public void onNoInput(IHantoGameState state) 
	{
		
	}

}
