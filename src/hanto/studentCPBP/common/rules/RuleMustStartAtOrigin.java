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
import hanto.studentCPBP.common.HantoCoordinateImpl;
import hanto.studentCPBP.common.IHantoGameState;
import hanto.studentCPBP.common.GenericHantoRuleCollection.IRule;

/**
 * Rule that dictates that the first piece MUST be placed at origin
 * @author cgporell
 * @author bpeake
 *
 */
public class RuleMustStartAtOrigin implements GenericHantoRuleCollection.IRule
{
	private GenericHantoRuleCollection rules;
	
	@Override
	public void check(IHantoGameState state) throws HantoException
	{
		if(state.getMoveNumber() == 0 && state.getPieces(new HantoCoordinateImpl(0, 0)).length == 0)
		{
			throw new HantoException("Must start at origin.");
		}
	}

	@Override
	public boolean isValidMoveLocation(IHantoGameState state, HantoCoordinate location) 
	{
		if(state.getPieces().length == 0)
		{
			return  (location.getX() == 0 && location.getY() == 0);
		}
		
		return true;
	}

	@Override
	public boolean isValidSearchLocation(IHantoGameState state, HantoCoordinate location) 
	{
		if(state.getPieces().length == 0)
		{
			return (location.getX() == 0 && location.getY() == 0);
		}
		
		return true;
	}

}
