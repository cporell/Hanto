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
import hanto.common.HantoPieceType;
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
	 * Check the state of the game to make sure it's valid.
	 * @param state The current game state
	 * @throws HantoException Thrown if a move is illegal.
	 */
	void check(IHantoGameState state) throws HantoException;
	
	/**
	 * Gets the color of the player whose turn it is.
	 * @param state The game state.
	 * @return HantoHand of the current turn
	 */
	HantoPlayerColor getCurrentPlayer(IHantoGameState state);
	
	/**
	 * Gets the turn number given a game state.
	 * @param state The game state.
	 * @return The turn number the game is in.
	 */
	int getTurnNumber(IHantoGameState state);

	/**
	 * Actions to do at the start of a turn
	 * @param state The game state
	 * @throws HantoException A Hanto Exception upon discovering an illegal move
	 */
	void beginTurn(IHantoGameState state) throws HantoException;
	
	/**
	 * Actions to do at the end of a turn, mainly increment the turn counter, switch current player.
	 * @param state The game state
	 * @throws HantoException A Hanto Exception upon discovering an illegal move
	 * @return The result of the move (Red win, Blue win, OK, Draw)
	 */
	MoveResult endTurn(IHantoGameState state) throws HantoException;

	/**
	 * Creates a validator for a given mover.
	 * @param mover The mover we wish to construct a validator from.
	 * @return A validator based on the given mover.
	 */
	IHantoMoverValidator createMoverValidator(IHantoMover mover);

	/**
	 * Called when no input is given to the game.
	 */
	void onNoInput(IHantoGameState state);
}
