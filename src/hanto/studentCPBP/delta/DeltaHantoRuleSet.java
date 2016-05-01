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

import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.studentCPBP.common.GenericHantoRuleCollection;
import hanto.studentCPBP.common.IHantoGameState;
import hanto.studentCPBP.common.IHantoMover;
import hanto.studentCPBP.common.IHantoMoverValidator;
import hanto.studentCPBP.common.movers.FlyMover;
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

/**
 * Custom rules for Delta Hanto
 * 
 * @author cgporell
 * @author bpeake
 *
 */
public class DeltaHantoRuleSet extends GenericHantoRuleCollection {
	private EndConditionStaticWinner winnerTrigger;

	/**
	 * Construct a DeltaHanto rule set
	 * 
	 * @param startingColor
	 *            The player who goes first
	 */
	public DeltaHantoRuleSet(HantoPlayerColor startingColor) {
		super(startingColor);

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
	public IHantoMoverValidator createMoverValidator(IHantoMover mover) {
		if (mover instanceof PlaceMover) {
			return new DeltaHantoPlaceMoverValidator((PlaceMover) mover, this);
		} else if (mover instanceof WalkMover) {
			switch (((WalkMover) mover).getPiece().getType()) {
			case BUTTERFLY:
				return new DeltaHantoWalkMoverValidator((WalkMover) mover, this, 1);
			case CRAB:
				return new DeltaHantoWalkMoverValidator((WalkMover) mover, this, 3);
			}
		} else if (mover instanceof FlyMover) {
			return new DeltaHantoFlyMoverValidator((FlyMover) mover, this);
		}

		return null;
	}

	@Override
	public void onNoInput(IHantoGameState state) {
		winnerTrigger.setWinner(
				getCurrentPlayer(state) == HantoPlayerColor.BLUE ? HantoPlayerColor.RED : HantoPlayerColor.BLUE);
	}
}
