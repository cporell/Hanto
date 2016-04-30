/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentCPBP.epsilon;

import hanto.common.HantoGameID;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.studentCPBP.common.DefaultHantoMoverValidator;
import hanto.studentCPBP.common.GenericHantoRuleCollection;
import hanto.studentCPBP.common.IHantoGameState;
import hanto.studentCPBP.common.IHantoMover;
import hanto.studentCPBP.common.IHantoMoverValidator;
import hanto.studentCPBP.common.movers.FlyMover;
import hanto.studentCPBP.common.movers.JumpMover;
import hanto.studentCPBP.common.movers.PlaceMover;
import hanto.studentCPBP.common.movers.WalkMover;
import hanto.studentCPBP.common.rules.EndConditionGetWinner;
import hanto.studentCPBP.common.rules.EndConditionStaticWinner;
import hanto.studentCPBP.common.rules.RuleButterflyPlacedAfterRound;
import hanto.studentCPBP.common.rules.RuleLimitPiecesPerSpot;
import hanto.studentCPBP.common.rules.RuleMustBeContinousBoard;
import hanto.studentCPBP.common.rules.RuleMustStartAtOrigin;
import hanto.studentCPBP.common.rules.RuleValidPieceTypes;
import hanto.studentCPBP.common.rules.StartConditionCantPlaceAfterGameIsOver;

public class EpsilonHantoRuleSet extends GenericHantoRuleCollection 
{
	private EndConditionStaticWinner winnerTrigger;
	
	public EpsilonHantoRuleSet(HantoPlayerColor startingPlayer)
	{
		super(startingPlayer);
		
		addStartCondition(new StartConditionCantPlaceAfterGameIsOver());
		
		addRule(new RuleButterflyPlacedAfterRound(this, 4));
		addRule(new RuleMustBeContinousBoard());
		addRule(new RuleLimitPiecesPerSpot(1));
		addRule(new RuleMustStartAtOrigin());
		
		addEndCondition(winnerTrigger = new EndConditionStaticWinner());
		addEndCondition(new EndConditionGetWinner());
	}

	@Override
	public IHantoMoverValidator createMoverValidator(IHantoMover mover) 
	{
		if(mover instanceof PlaceMover)
		{
			return new EpsilonHantoPlaceMoverValidator((PlaceMover) mover, this);
		}
		else if(mover instanceof WalkMover)
		{
			switch(((WalkMover) mover).getPiece().getType())
			{
			case BUTTERFLY:
				return new EpsilonHantoWalkMoverValidator((WalkMover) mover, this, 1);
			case CRAB:
				return new EpsilonHantoWalkMoverValidator((WalkMover) mover, this, 3);
			}
		}
		else if(mover instanceof FlyMover)
		{
			return new EpsilonHantoFlyMoverValidator((FlyMover) mover, this, 4);
		}
		else if(mover instanceof JumpMover)
		{
			return new EpsilonHantoJumpMoverValidator((JumpMover) mover, this);
		}
		
		return null;
	}

	@Override
	public void onNoInput(IHantoGameState state) 
	{
		winnerTrigger.setWinner(getCurrentPlayer(state) == HantoPlayerColor.BLUE ? 
				HantoPlayerColor.RED : HantoPlayerColor.BLUE);
	}

}
