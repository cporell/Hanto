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

package hanto.studentCPBP.common;

import hanto.common.HantoException;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;

/**
 * Ruleset for a Hanto game. Allows us to customize the rules for each version of Hanto.
 * @author Benny Peake bpeake
 * @author Connor Porell cgporell
 */
public interface IHantoRuleSet
{
	/**
	 * Check the state of the board to see if the move is okay, or game-ending.
	 * @param board The current board
	 * @return A move result stating if that move is okay, results in victory, or a draw.
	 * @throws HantoException Thrown if a move is illegal.
	 */
	void checkBoard(IHantoBoard board) throws HantoException;
	
	/**
	 * Gets the color of the player whose turn it is
	 * @return PlayerColor of the current turn
	 */
	HantoPlayerColor getCurrentTurn();
	
	/**
	 * Actions to do at the start of a turn
	 */
	void beginTurn(IHantoBoard board) throws HantoException;
	
	/**
	 * Actions to do at the end of a turn, mainly increment the turn counter, switch current player.
	 */
	MoveResult endTurn(IHantoBoard board) throws HantoException;
	
	/**
	 * Returns the turn number based on the number of moves that have passed.
	 * @return Turn number
	 */
	int getTurnNumber();
	
	/**
	 * Creates a validator for a given mover.
	 * @param mover The mover we wish to construct a validator from.
	 * @return A validator based on the given mover.
	 */
	IHantoMoverValidator createMoverValidator(IHantoMover mover);
}
