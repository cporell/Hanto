/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentCPBP.beta;

import hanto.common.HantoException;
import hanto.common.HantoPlayerColor;
import hanto.studentCPBP.common.GenericHantoRuleCollection;
import hanto.studentCPBP.common.IHantoGameState;
import hanto.studentCPBP.common.IHantoMover;
import hanto.studentCPBP.common.IHantoMoverValidator;
import hanto.studentCPBP.common.movers.PlaceMover;
import hanto.studentCPBP.common.rules.EndConditionGetWinner;
import hanto.studentCPBP.common.rules.EndConditionPlayNRounds;
import hanto.studentCPBP.common.rules.RuleButterflyPlacedAfterRound;
import hanto.studentCPBP.common.rules.RuleLimitPiecesPerSpot;
import hanto.studentCPBP.common.rules.RuleMustBeContinousBoard;
import hanto.studentCPBP.common.rules.RuleMustStartAtOrigin;
import hanto.studentCPBP.common.rules.StartConditionCantPlaceAfterGameIsOver;

/**
 * Ruleset for Beta Hanto
 * @author cgporell
 * @author bpeake
 *
 */
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
