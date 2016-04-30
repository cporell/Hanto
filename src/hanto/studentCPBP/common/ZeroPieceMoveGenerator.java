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

import hanto.common.HantoCoordinate;
import hanto.tournament.HantoMoveRecord;

/**
 * Piece mover searcher that grabs the first potential move from a list of valid moves.
 * @author cgporell
 * @author bpeake
 *
 */
public class ZeroPieceMoveGenerator implements IHantoPieceMoveGenerator {

	@Override
	public HantoMoveRecord[] getValidMovesFrom(HantoCoordinate[] validLocations, IHantoGameState state) 
	{
		return new HantoMoveRecord[0];
	}

}
