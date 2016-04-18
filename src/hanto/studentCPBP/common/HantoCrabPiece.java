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
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;

/**
 * Crab piece for Hanto
 * @author cgporell
 * @author bpeake
 *
 */
public class HantoCrabPiece extends HantoCommonPiece {

	/**
	 * Builds a Sparrow piece
	 * @param color Owner of this piece
	 */
	public HantoCrabPiece(HantoPlayerColor color)
	{
		super(color, HantoPieceType.CRAB);
	}

	@Override
	public IHantoMover createPlaceMover(HantoCoordinate at) 
	{
		return new PlaceMover(this, at);
	}

	@Override
	public IHantoMover createWalkMover(HantoCoordinate to)
	{
		return new WalkMover(this, to);
	}

}
