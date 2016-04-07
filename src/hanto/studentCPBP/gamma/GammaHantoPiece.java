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

import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;

/**
 * Implementation of a piece for GammaHanto
 * @author Benny Peake bpeake
 * @author Connor Porell cgporell
 */
public class GammaHantoPiece implements HantoPiece 
{
	private HantoPlayerColor color;
	private HantoPieceType type;
	
	/**
	 * Piece for Gamma Hanto, takes in a color and type
	 * @param color Player color (owner)
	 * @param type Type of the piece
	 */
	public GammaHantoPiece(HantoPlayerColor color, HantoPieceType type)
	{
		this.color = color;
		this.type = type;
	}
	
	@Override
	public HantoPlayerColor getColor() 
	{
		return color;
	}

	@Override
	public HantoPieceType getType() 
	{
		return type;
	}

}
