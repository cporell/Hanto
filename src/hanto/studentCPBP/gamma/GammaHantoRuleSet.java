/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Copyright ©2016 Gary F. Pollice
 *******************************************************************************/

package hanto.studentCPBP.gamma;

import hanto.common.HantoGameID;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.studentCPBP.common.EndConditionGetWinner;
import hanto.studentCPBP.common.EndConditionPlayNRounds;
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
 * Custom rules for Gamma Hanto
 * @author Benny Peake bpeake
 * @author Connor Porell cgporell
 */
public class GammaHantoRuleSet extends GenericHantoRuleCollection
{	
	/**
	 * Builds a ruleset for Gamma Hanto, based on the starting color.
	 * @param startingColor THe starting player
	 */
	public GammaHantoRuleSet(HantoPlayerColor startingColor)
	{
		super(startingColor, 
				HantoHandFactory.getInstance().makeHantoHand(HantoGameID.GAMMA_HANTO, HantoPlayerColor.BLUE), 
				HantoHandFactory.getInstance().makeHantoHand(HantoGameID.GAMMA_HANTO, HantoPlayerColor.RED));
		
		addStartCondition(new StartConditionCantPlaceAfterGameIsOver());
		
		addRule(new RuleButterflyPlacedAfterRound(this, 4));
		addRule(new RuleMustBeContinousBoard());
		addRule(new RuleLimitPiecesPerSpot(1));
		addRule(new RuleMustStartAtOrigin());
		addRule(new RuleValidPieceTypes(HantoPieceType.BUTTERFLY, HantoPieceType.SPARROW));

		addEndCondition(new EndConditionGetWinner());
		addEndCondition(new EndConditionPlayNRounds(20));
	}
	
	@Override
	public IHantoMoverValidator createMoverValidator(IHantoMover mover) 
	{
		if(mover instanceof PlaceMover)
		{
			return new GammaHantoPlaceMoverValidator((PlaceMover) mover, this);
		}
		else if(mover instanceof WalkMover)
		{
			return new GammaHantoWalkMoverValidator((WalkMover) mover, this);
		}
		
		return null;
	}

	@Override
	public void onNoInput(IHantoGameState state) 
	{
	}

	
}
