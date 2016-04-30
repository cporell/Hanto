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
import hanto.studentCPBP.common.GenericHantoRuleCollection;
import hanto.studentCPBP.common.IHantoGameState;
import hanto.studentCPBP.common.GenericHantoRuleCollection.IRule;

/**
 * Rule that determines how many pieces can legally occupy the same spot.
 * This rule is meant to prevent overlapping pieces in normal Hanto
 * @author cgporell
 * @author bpeake
 *
 */
public class RuleLimitPiecesPerSpot implements GenericHantoRuleCollection.IRule
{
	private int max;
	
	/**
	 * Constructor for RuleLimitPiecesPerSpot
	 * @param max Maximum amount of pieces per spot
	 */
	public RuleLimitPiecesPerSpot(int max)
	{
		this.max = max;
	}
	
	@Override
	public void check(IHantoGameState state) throws HantoException
	{
		for(HantoCoordinate coord : state.getAllTakenLocations())
		{
			if(state.getPieces(coord).length > max)
			{
				throw new HantoException("Cannot put more than " + max + " pieces on a space");
			}
		}
	}

	@Override
	public boolean isValidMoveLocation(IHantoGameState state, HantoCoordinate location) 
	{
		return state.getPieces(location).length < max;
	}

	@Override
	public boolean isValidSearchLocation(IHantoGameState state, HantoCoordinate location) 
	{
		return true;
	}

}
