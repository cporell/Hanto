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

import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;

/**
 * Factory for building Hanto pieces.
 * @author Benny Peake bpeake
 * @author Connor Porell cgporell
 */
public interface IHantoPieceFactory
{
	/**
	 * Create a piece of the desired type/color.
	 * @param type The type of piece
	 * @param color The player color
	 * @return A new HantoCommonPiece
	 */
	CommonHantoPiece createPiece(HantoPieceType type, HantoPlayerColor color);
}
