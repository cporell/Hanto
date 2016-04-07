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
	 * @param board The current game board
	 * @throws HantoException Whenever there is an illegal move.
	 */
	void check(IHantoBoard board) throws HantoException;
}
