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

import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.studentCPBP.common.GenericHantoRuleCollection;
import hanto.studentCPBP.common.IHantoGameState;
import hanto.studentCPBP.common.GenericHantoRuleCollection.IEndCondition;

/**
 * EndCondition that checks if a player is surrounded and gets a winner.
 * @author cgporell
 * @author bpeake
 *
 */
public class EndConditionGetWinner implements GenericHantoRuleCollection.IEndCondition
{
	private EndConditionPlayerSurrounded blueSurrounded, redSurrounded;
	
	public EndConditionGetWinner()
	{
		blueSurrounded = new EndConditionPlayerSurrounded(HantoPlayerColor.BLUE);
		redSurrounded = new EndConditionPlayerSurrounded(HantoPlayerColor.RED);
	}
	
	@Override
	public MoveResult checkForResult(IHantoGameState board) 
	{
		MoveResult blueResult = blueSurrounded.checkForResult(board);
		MoveResult redResult = redSurrounded.checkForResult(board);
		
		if(blueResult != MoveResult.OK && redResult != MoveResult.OK)
		{
			return MoveResult.DRAW;
		}
		else if(blueResult != MoveResult.OK)
		{
			return blueResult;
		}
		else if(redResult != MoveResult.OK)
		{
			return redResult;
		}
		
		return MoveResult.OK;
	}

}
