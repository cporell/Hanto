/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentCPBP.common.pieces;

import hanto.common.HantoCoordinate;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.studentCPBP.common.CommonHantoPiece;
import hanto.studentCPBP.common.IHantoMover;
import hanto.studentCPBP.common.movers.JumpMover;
import hanto.studentCPBP.common.movers.PlaceMover;

/**
 * Class for Hanto Horse Pieces
 * @author cgporell
 *
 */
public class HantoHorsePiece extends CommonHantoPiece {

	/**
	 * Builds a Horse piece
	 * @param color Owner of this piece
	 */
	public HantoHorsePiece(HantoPlayerColor color) 
	{
		super(color, HantoPieceType.HORSE);
	}

	@Override
	public IHantoMover createPlaceMover(HantoCoordinate at) 
	{
		return new PlaceMover(this, at);
	}

	@Override
	public IHantoMover createWalkMover(HantoCoordinate from, HantoCoordinate to) 
	{
		return new JumpMover(this, from, to);
	}

}
