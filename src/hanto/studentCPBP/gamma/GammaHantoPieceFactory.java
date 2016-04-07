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

package hanto.studentCPBP.gamma;

import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.studentCPBP.common.HantoCommonPiece;
import hanto.studentCPBP.common.IHantoPieceFactory;

/**
 * Piece factory for Gamma Hanto
 * @author Benny Peake bpeake
 * @author Connor Porell cgporell
 */
public class GammaHantoPieceFactory implements IHantoPieceFactory
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
		case CRANE:
		case DOVE:
		case HORSE:
		default:
			return new PlaceholderPiece(color, type);
		
		}
	}

}
