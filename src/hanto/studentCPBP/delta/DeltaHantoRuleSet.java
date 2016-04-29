/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentCPBP.delta;

import hanto.common.HantoGameID;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.studentCPBP.common.DefaultHantoMoverValidator;
import hanto.studentCPBP.common.EndConditionGetWinner;
import hanto.studentCPBP.common.EndConditionStaticWinner;
import hanto.studentCPBP.common.FlyMover;
import hanto.studentCPBP.common.GenericHantoRuleCollection;
import hanto.studentCPBP.common.HantoHandFactory;
import hanto.studentCPBP.common.IHantoGameState;
import hanto.studentCPBP.common.IHantoMover;
import hanto.studentCPBP.common.IHantoMoverValidator;
import hanto.studentCPBP.common.PlaceMover;
import hanto.studentCPBP.common.RuleButterflyPlacedAfterRound;
import hanto.studentCPBP.common.RuleLimitPiecesPerSpot;
import hanto.studentCPBP.common.RuleMustBeContinousBoard;
import hanto.studentCPBP.common.RuleMustStartAtOrigin;
import hanto.studentCPBP.common.RuleValidPieceTypes;
import hanto.studentCPBP.common.StartConditionCantPlaceAfterGameIsOver;
import hanto.studentCPBP.common.WalkMover;

/**
 * Custom rules for Delta Hanto
 * @author cgporell
 * @author bpeake
 *
 */
public class DeltaHantoRuleSet extends GenericHantoRuleCollection
{	
	private EndConditionStaticWinner winnerTrigger;
	
	/**
	 * Construct a DeltaHanto rule set
	 * @param startingColor The player who goes first
	 */
	public DeltaHantoRuleSet(HantoPlayerColor startingColor)
	{
		super(startingColor, 
				HantoHandFactory.getInstance().makeHantoHand(HantoGameID.DELTA_HANTO, HantoPlayerColor.BLUE), 
				HantoHandFactory.getInstance().makeHantoHand(HantoGameID.DELTA_HANTO, HantoPlayerColor.RED));
		
		addStartCondition(new StartConditionCantPlaceAfterGameIsOver());
		
		addRule(new RuleButterflyPlacedAfterRound(this, 4));
		addRule(new RuleMustBeContinousBoard());
		addRule(new RuleLimitPiecesPerSpot(1));
		addRule(new RuleMustStartAtOrigin());
		addRule(new RuleValidPieceTypes(HantoPieceType.BUTTERFLY, HantoPieceType.CRAB, HantoPieceType.SPARROW));
		
		addEndCondition(winnerTrigger = new EndConditionStaticWinner());
		addEndCondition(new EndConditionGetWinner());
	}
	
	@Override
	public IHantoMoverValidator createMoverValidator(IHantoMover mover) 
	{
		if(mover instanceof PlaceMover)
		{
			return new DeltaHantoPlaceMoverValidator((PlaceMover) mover, this);
		}
		else if(mover instanceof WalkMover)
		{
			switch(((WalkMover) mover).getPiece().getType())
			{
			case BUTTERFLY:
				return new DeltaHantoWalkMoverValidator((WalkMover) mover, this, 1);
			case CRAB:
				return new DeltaHantoWalkMoverValidator((WalkMover) mover, this, 3);
			}
		}
		else if(mover instanceof FlyMover)
		{
			return new DeltaHantoFlyMoverValidator((FlyMover) mover, this);
		}
		
		return new DefaultHantoMoverValidator();
	}

	@Override
	public void onNoInput(IHantoGameState state) 
	{
		winnerTrigger.setWinner(getCurrentPlayer(state) == HantoPlayerColor.BLUE ? 
				HantoPlayerColor.RED : HantoPlayerColor.BLUE);
	}
}
