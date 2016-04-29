/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentCPBP.common;

import java.util.ArrayList;

import hanto.common.HantoException;
import hanto.common.HantoGameID;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;

public abstract class GenericHantoRuleCollection implements IHantoRuleSet 
{
	private HantoPlayerColor startingPlayerColor;
	
	public interface IRule
	{
		void check(IHantoGameState state) throws HantoException;
	}
	
	public interface IStartCondition
	{
		void check(IHantoGameState state) throws HantoException;
	}
	
	public interface IEndCondition
	{
		MoveResult checkForResult(IHantoGameState state);
	}
	
	private ArrayList<IRule> rules = new ArrayList<>();
	private ArrayList<IStartCondition> startConditions = new ArrayList<>();
	private ArrayList<IEndCondition> endConditions = new ArrayList<>();
	
	public GenericHantoRuleCollection(HantoPlayerColor startingPlayer, CommonHantoHand blueHand, CommonHantoHand redHand)
	{
		startingPlayerColor = startingPlayer;
	}
	/**
	 * Add a rule to this rule collection
	 * @param rule The rule we are adding
	 */
	public void addRule(IRule rule)
	{
		rules.add(rule);
	}
	
	/**
	 * Add a start condition to this ruleset
	 * @param condition The start condition we are adding
	 */
	public void addStartCondition(IStartCondition condition)
	{
		startConditions.add(condition);
	}
	
	/**
	 * Add an end condition to this ruleset
	 * @param condition The end condition we are adding
	 */
	public void addEndCondition(IEndCondition condition)
	{
		endConditions.add(condition);
	}
	
	@Override
	public void beginTurn(IHantoGameState state) throws HantoException 
	{
		for(IStartCondition condition : startConditions)
		{
			condition.check(state);
		}
	}
	
	@Override
	public void check(IHantoGameState state) throws HantoException 
	{
		for(IRule rule : rules)
		{
			rule.check(state);
		}
	}

	@Override
	public HantoPlayerColor getCurrentPlayer(IHantoGameState state) 
	{
		HantoPlayerColor oppositeStarting = startingPlayerColor == HantoPlayerColor.RED ? HantoPlayerColor.BLUE : HantoPlayerColor.RED;
		return state.getMoveNumber() % 2 == 0 ? startingPlayerColor : oppositeStarting;
	}

	@Override
	public MoveResult endTurn(IHantoGameState state) throws HantoException 
	{
		MoveResult result = MoveResult.OK;
		for(IEndCondition condition : endConditions)
		{
			result = condition.checkForResult(state);
			if(result != MoveResult.OK)
			{
				state.triggerGameOver();
				break;
			}
		}
		
		state.endMove();
		
		return result;
	}

	@Override
	public int getTurnNumber(IHantoGameState state) 
	{
		return (state.getMoveNumber() / 2) + 1;
	}
}
