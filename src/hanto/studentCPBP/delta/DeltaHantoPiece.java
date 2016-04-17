/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentCPBP.delta;

import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;

/**
 * Implementation of a piece for Delta
 * @author Benny Peake bpeake
 * @author Connor Porell cgporell
 */
public class DeltaHantoPiece implements HantoPiece {

	private HantoPlayerColor color;
	private HantoPieceType type;

	/**
	 * Piece for Delta Hanto, takes in a color and type
	 * @param color Player color (owner)
	 * @param type Type of the piece
	 */
	public DeltaHantoPiece(HantoPlayerColor color, HantoPieceType type)
	{
		this.color = color;
		this.type = type;
	}

	@Override
	public HantoPlayerColor getColor() {
		return color;
	}

	@Override
	public HantoPieceType getType() {
		return type;
	}

}
