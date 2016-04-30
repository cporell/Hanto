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
 * EndCondition that gets a winner.
 * @author cgporell
 * @author bpeake
 *
 */
public class EndConditionStaticWinner implements GenericHantoRuleCollection.IEndCondition
{
	private HantoPlayerColor winner;
	
	public void setWinner(HantoPlayerColor winner)
	{
		this.winner = winner;
	}
	
	@Override
	public MoveResult checkForResult(IHantoGameState state) 
	{
		if(winner != null)
		{
			return winner == HantoPlayerColor.BLUE ? MoveResult.BLUE_WINS : MoveResult.RED_WINS;
		}
		
		return MoveResult.OK;
	}	
}
