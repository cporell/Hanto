/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentCPBP.alpha;

import hanto.common.HantoException;
import hanto.common.HantoPlayerColor;
import hanto.studentCPBP.common.GenericHantoRuleCollection;
import hanto.studentCPBP.common.IHantoGameState;
import hanto.studentCPBP.common.IHantoMover;
import hanto.studentCPBP.common.IHantoMoverValidator;
import hanto.studentCPBP.common.movers.PlaceMover;
import hanto.studentCPBP.common.rules.EndConditionPlayNRounds;
import hanto.studentCPBP.common.rules.RuleLimitPiecesPerSpot;
import hanto.studentCPBP.common.rules.RuleMustBeContinousBoard;
import hanto.studentCPBP.common.rules.RuleMustStartAtOrigin;
import hanto.studentCPBP.common.rules.StartConditionCantPlaceAfterGameIsOver;

/**
 * Ruleset for Alpha Hanto
 * @author cgporell
 * @author bpeake
 *
 */
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
