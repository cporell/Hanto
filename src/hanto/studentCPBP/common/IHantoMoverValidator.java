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

/**
 * IHantoMoverValidator - given a board layout, checks that that move/layout is acceptable.
 * @author Benny Peake bpeake
 * @author Connor Porell cgporell
 */
public interface IHantoMoverValidator
{
	/**
	 * Runs a series of validity tests on a move, throws Exception if any fail.
	 * @param state The current game state
	 * @throws HantoException Whenever there is an illegal move.
	 */
	void checkIteration(IHantoGameState state) throws HantoException;
	
	/**
	 * Called when a mover resets after an invalid move.
	 * @param state The reset state of the game.
	 * @throws HantoException On an invalid reset.
	 */
	void onInvalidMoveHandled(IHantoGameState state) throws HantoException;
}
