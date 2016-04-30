/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentCPBP.common.rules;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.studentCPBP.common.GenericHantoRuleCollection;
import hanto.studentCPBP.common.IHantoGameState;
import hanto.studentCPBP.common.GenericHantoRuleCollection.IRule;

/**
 * Rule that checks if a Butterfly was placed by a certain round.
 * This round is specified by a particular version of Hanto.
 * @author cgporell
 * @author bpeake
 *
 */
public class RuleButterflyPlacedAfterRound implements GenericHantoRuleCollection.IRule
{
	private GenericHantoRuleCollection rules;
	private int round;
	
	/**
	 * Constructor for RuleButterflyPlacedAfterRound
	 * @param rules A rule set for a particular game of Hanto
	 * @param round The round by which the Butterfly must be placed
	 */
	public RuleButterflyPlacedAfterRound(GenericHantoRuleCollection rules, int round)
	{
		this.rules = rules;
		this.round = round;
	}
	
	@Override
	public void check(IHantoGameState state) throws HantoException 
	{
		if(rules.getTurnNumber(state) == round)
		{
			if(rules.getCurrentPlayer(state) == HantoPlayerColor.BLUE)
			{
				if(state.getPieces(HantoPlayerColor.BLUE, HantoPieceType.BUTTERFLY).length == 0)
				{
					throw new HantoException("Blue did not place butterfly by turn " + round + ".");	
				}
			}
			else
			{
				if(state.getPieces(HantoPlayerColor.RED, HantoPieceType.BUTTERFLY).length == 0)
				{
					throw new HantoException("Red did not place butterfly by turn " + round + ".");
				}
			}
		}
	}

	@Override
	public boolean isValidMoveLocation(IHantoGameState state, HantoCoordinate location) 
	{
		return true;
	}

	@Override
	public boolean isValidSearchLocation(IHantoGameState state, HantoCoordinate location) 
	{
		return true;
	}
}
