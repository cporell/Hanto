/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentCPBP.tournament;

import hanto.studentCPBP.common.CommonHantoGame;
import hanto.tournament.HantoMoveRecord;

/**
 * IHantoPlayerThinker allows us to have different manners of selecting moves
 * in a way similar to Strategy Pattern.
 * @author bpeake
 * @author cgporell
 *
 */
public interface IHantoPlayerThinker 
{
	/**
	 * Selects a move from the set of possible valid moves.
	 * @param game The game
	 * @return A move record detailing our move in the game.
	 */
	HantoMoveRecord selectMove(CommonHantoGame game);
}
