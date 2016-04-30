package hanto.studentCPBP.beta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPlayerColor;
import hanto.studentCPBP.common.CommonHantoPiece;
import hanto.studentCPBP.common.GenericHantoRuleCollection;
import hanto.studentCPBP.common.HantoCoordinateImpl;
import hanto.studentCPBP.common.IHantoGameState;
import hanto.studentCPBP.common.IHantoMover;
import hanto.studentCPBP.common.IHantoMoverValidator;
import hanto.studentCPBP.common.IHantoPieceMoveGenerator;
import hanto.studentCPBP.common.ZeroPieceMoveGenerator;
import hanto.studentCPBP.common.movers.PlaceMover;
import hanto.studentCPBP.common.rules.EndConditionGetWinner;
import hanto.studentCPBP.common.rules.EndConditionPlayNRounds;
import hanto.studentCPBP.common.rules.RuleButterflyPlacedAfterRound;
import hanto.studentCPBP.common.rules.RuleLimitPiecesPerSpot;
import hanto.studentCPBP.common.rules.RuleMustBeContinousBoard;
import hanto.studentCPBP.common.rules.RuleMustStartAtOrigin;
import hanto.studentCPBP.common.rules.StartConditionCantPlaceAfterGameIsOver;
import hanto.tournament.HantoMoveRecord;

public class BetaHantoRuleSet extends GenericHantoRuleCollection
{
	private class BetaPlacementMoverValidator implements IHantoMoverValidator
	{
		@Override
		public void checkIteration(IHantoGameState state) throws HantoException 
		{
			
		}

		@Override
		public void onInvalidMoveHandled(IHantoGameState state) throws HantoException 
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

		@Override
		public void onInvalidMoveHandled(IHantoGameState state) throws HantoException 
		{
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
