package hanto.studentCPBP.beta;

import hanto.common.HantoException;
import hanto.common.HantoPlayerColor;
import hanto.studentCPBP.common.EndConditionGetWinner;
import hanto.studentCPBP.common.EndConditionPlayNRounds;
import hanto.studentCPBP.common.GenericHantoRuleCollection;
import hanto.studentCPBP.common.IHantoGameState;
import hanto.studentCPBP.common.IHantoMover;
import hanto.studentCPBP.common.IHantoMoverValidator;
import hanto.studentCPBP.common.PlaceMover;
import hanto.studentCPBP.common.RuleButterflyPlacedAfterRound;
import hanto.studentCPBP.common.RuleLimitPiecesPerSpot;
import hanto.studentCPBP.common.RuleMustBeContinousBoard;
import hanto.studentCPBP.common.RuleMustStartAtOrigin;
import hanto.studentCPBP.common.StartConditionCantPlaceAfterGameIsOver;

public class BetaHantoRuleSet extends GenericHantoRuleCollection
{
	private class BetaPlacementMoverValidator implements IHantoMoverValidator
	{
		@Override
		public void checkIteration(IHantoGameState state) throws HantoException 
		{
			
		}
		
	}
	
	private class BetaInvalidMoverValidator implements IHantoMoverValidator
	{
		@Override
		public void checkIteration(IHantoGameState state) throws HantoException 
		{
			throw new HantoException("Invalid movement input.");
		}
		
	}
	
	public BetaHantoRuleSet(HantoPlayerColor startingPlayer) 
	{
		super(startingPlayer);
		
		addStartCondition(new StartConditionCantPlaceAfterGameIsOver());
		
		addRule(new RuleMustStartAtOrigin());
		addRule(new RuleLimitPiecesPerSpot(1));
		addRule(new RuleMustBeContinousBoard());
		addRule(new RuleButterflyPlacedAfterRound(this, 4));
		
		addEndCondition(new EndConditionGetWinner());
		addEndCondition(new EndConditionPlayNRounds(6));
	}

	@Override
	public IHantoMoverValidator createMoverValidator(IHantoMover mover) 
	{
		if(mover instanceof PlaceMover)
		{
			return new BetaPlacementMoverValidator();
		}
		
		return new BetaInvalidMoverValidator();
	}

	@Override
	public void onNoInput(IHantoGameState state) 
	{
		
	}

}
