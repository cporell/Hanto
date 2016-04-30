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

import hanto.common.HantoException;
import hanto.studentCPBP.common.GenericHantoRuleCollection;
import hanto.studentCPBP.common.IHantoGameState;
import hanto.studentCPBP.common.GenericHantoRuleCollection.IStartCondition;

/**
 * Start condition that makes sure a player isn't attempting 
 * to make a move after the game is over.
 * @author cgporell
 * @author bpeake
 *
 */
public class StartConditionCantPlaceAfterGameIsOver implements GenericHantoRuleCollection.IStartCondition
{	
	@Override
	public void check(IHantoGameState state) throws HantoException 
	{
		if(state.isGameOver())
		{
			throw new HantoException("Can't continue playing if game is already over.");
		}
	}

}
