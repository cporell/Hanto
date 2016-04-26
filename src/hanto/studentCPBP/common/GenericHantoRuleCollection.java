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
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;

public abstract class GenericHantoRuleCollection implements IHantoRuleSet 
{
	protected CommonHantoHand currentTurn;
	protected int moveCount = 0;
	private boolean isGameOver = false;
	private CommonHantoHand blueHand;
	private CommonHantoHand redHand;
	
	public interface IRule
	{
		void checkBoard(IHantoBoard board) throws HantoException;
	}
	
	public interface IStartCondition
	{
		void check(IHantoBoard board) throws HantoException;
	}
	
	public interface IEndCondition
	{
		MoveResult checkForResult(IHantoBoard board);
	}
	
	private ArrayList<IRule> rules = new ArrayList<>();
	private ArrayList<IStartCondition> startConditions = new ArrayList<>();
	private ArrayList<IEndCondition> endConditions = new ArrayList<>();
	
	/**
	 * Gets whether or not the game is over
	 * @return
	 */
	protected boolean getIsGameOver()
	{
		return isGameOver;
	}
	
	/**
	 * Sets whether the game is over or not
	 * @param gameOverStatus True if game is over, else false
	 */
	protected void setIsGameOver(boolean gameOverStatus)
	{
		isGameOver = gameOverStatus;
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
	public void beginTurn(IHantoBoard board) throws HantoException 
	{
		for(IStartCondition condition : startConditions)
		{
			condition.check(board);
		}
	}
	
	@Override
	public void checkBoard(IHantoBoard board) throws HantoException 
	{
		for(IRule rule : rules)
		{
			rule.checkBoard(board);
		}
	}

	@Override
	public CommonHantoHand getCurrentTurn() 
	{
		return currentTurn;
	}
	
	/**
	 * Sets the current turn, given a hand
	 * @param turn The Hand of the current player
	 */
	public void setCurrentTurn(CommonHantoHand turn)
	{
		currentTurn = turn;
	}

	@Override
	public MoveResult endTurn(IHantoBoard board) throws HantoException 
	{
		MoveResult result = MoveResult.OK;
		for(IEndCondition condition : endConditions)
		{
			result = condition.checkForResult(board);
			if(result != MoveResult.OK)
			{
				isGameOver = true;
				break;
			}
		}
		
		moveCount++;
		currentTurn = currentTurn == blueHand ? redHand : blueHand;
		
		return result;
	}

	@Override
	public int getTurnNumber() 
	{
		return (moveCount / 2) + 1;
	}
	
	/**
	 * Returns the hand of the blue player
	 * @return Blue's hand
	 */
	public CommonHantoHand getBlueHand()
	{
		return blueHand;
	}
	
	/**
	 * Sets the value of the blue hand
	 * @param Contents of the hand
	 */
	public void setBlueHand(CommonHantoHand hand)
	{
		blueHand = hand;
	}
	
	/**
	 * Returns the hand of the red player
	 * @return Red's hand
	 */
	public CommonHantoHand getRedHand()
	{
		return redHand;
	}
	
	/**
	 * Sets the value of the red hand
	 * @param Contents of the hand
	 */
	public void setRedHand(CommonHantoHand hand)
	{
		redHand = hand;
	}
}
