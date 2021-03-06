/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentCPBP.epsilon;

import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.studentCPBP.common.CommonHantoPiece;
import hanto.studentCPBP.common.IHantoPieceFactory;
import hanto.studentCPBP.common.pieces.HantoButterflyPiece;
import hanto.studentCPBP.common.pieces.HantoCrabPiece;
import hanto.studentCPBP.common.pieces.HantoHorsePiece;
import hanto.studentCPBP.common.pieces.HantoSparrowPiece;
import hanto.studentCPBP.common.pieces.PlaceholderPiece;

/**
 * Piece Factory for Delta Hanto
 * @author cgporell
 * @author bpeake
 *
 */
public class EpsilonHantoPieceFactory implements IHantoPieceFactory
{

	@Override
	public CommonHantoPiece createPiece(HantoPieceType type, HantoPlayerColor color) 
	{
		switch(type)
		{
		case BUTTERFLY:
			return new HantoButterflyPiece(color);
		case SPARROW:
			return new HantoSparrowPiece(color);
		case CRAB:
			return new HantoCrabPiece(color);
		case HORSE:
			return new HantoHorsePiece(color);
		case CRANE:
		case DOVE:
		default:
			return new PlaceholderPiece(color, type);
		}
	}

}
