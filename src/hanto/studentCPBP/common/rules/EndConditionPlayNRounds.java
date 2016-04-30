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

import hanto.common.MoveResult;
import hanto.studentCPBP.common.GenericHantoRuleCollection;
import hanto.studentCPBP.common.IHantoGameState;
import hanto.studentCPBP.common.GenericHantoRuleCollection.IEndCondition;

/**
 * EndCondition that checks the turn limit.
 * @author cgporell
 * @author bpeake
 *
 */
public class EndConditionPlayNRounds implements GenericHantoRuleCollection.IEndCondition
{
	private int moveCount;
	
	/**
	 * Constructor for EndConditionPlayNRounds
	 * @param roundCount The number of rounds that have elapsed.
	 */
	public EndConditionPlayNRounds(int roundCount) 
	{
		moveCount = roundCount * 2 - 1;
	}
	
	@Override
	public MoveResult checkForResult(IHantoGameState state) 
	{
		return state.getMoveNumber() >= moveCount ? MoveResult.DRAW : MoveResult.OK;
	}
}
