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

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;

/**
 * IHantoMover - handles checking the validity of moves step-by-step.
 * @author Benny Peake bpeake
 * @author Connor Porell cgporell
 */
public interface IHantoMover
{
	/**
	 * Gets the target location for a move.
	 * @return The target location for a move.
	 */
	HantoCoordinate getTargetLocation();
	
	/**
	 * Gets the piece that this mover is one.
	 * @return The piece that this mover is on.
	 */
	HantoCommonPiece getPiece();
	
	/**
	 * Check the move step-by-step to make sure it is valid.
	 * @param board The current game board
	 * @return False when movement is done (i.e it succeeded)
	 * @throws HantoException Whenever an illegal move is made
	 */
	boolean iterateMove(IHantoBoard board) throws HantoException;
}
