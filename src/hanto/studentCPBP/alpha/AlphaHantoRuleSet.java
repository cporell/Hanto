package hanto.studentCPBP.alpha;

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
import hanto.studentCPBP.common.rules.EndConditionPlayNRounds;
import hanto.studentCPBP.common.rules.RuleLimitPiecesPerSpot;
import hanto.studentCPBP.common.rules.RuleMustBeContinousBoard;
import hanto.studentCPBP.common.rules.RuleMustStartAtOrigin;
import hanto.studentCPBP.common.rules.StartConditionCantPlaceAfterGameIsOver;
import hanto.tournament.HantoMoveRecord;

public class AlphaHantoRuleSet extends GenericHantoRuleCollection
{
	private class AlphaPlacementMoverValidator implements IHantoMoverValidator
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
	
	private class AlphaInvalidMoverValidator implements IHantoMoverValidator
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
