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

import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.studentCPBP.common.HantoButterflyPiece;
import hanto.studentCPBP.common.HantoCommonPiece;
import hanto.studentCPBP.common.HantoCrabPiece;
import hanto.studentCPBP.common.HantoSparrowPiece;
import hanto.studentCPBP.common.IHantoPieceFactory;
import hanto.studentCPBP.common.PlaceholderPiece;

/**
 * Piece Factory for Delta Hanto
 * @author cgporell
 * @author bpeake
 *
 */
public class DeltaHantoPieceFactory implements IHantoPieceFactory
{

	@Override
	public HantoCommonPiece createPiece(HantoPieceType type, HantoPlayerColor color) 
	{
		switch(type)
		{
		case BUTTERFLY:
			return new HantoButterflyPiece(color);
		case SPARROW:
			return new HantoSparrowPiece(color);
		case CRAB:
			return new HantoCrabPiece(color);
		case CRANE:
		case DOVE:
		case HORSE:
		default:
			return new PlaceholderPiece(color, type);
		}
	}

}
