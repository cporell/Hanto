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

import hanto.common.HantoCoordinate;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;

/**
 * Placeholer piece is used for pieces that aren't valid in the current version of Hanto.
 * EG: If we tried making a Horse in GammaHanto, it would be created as a Placeholder piece.
 * @author Benny Peake bpeake
 * @author Connor Porell cgporell
 */
public class PlaceholderPiece extends CommonHantoPiece
{

	public PlaceholderPiece(HantoPlayerColor color, HantoPieceType type)
	{
		super(color, type);
	}

	@Override
	public IHantoMover createPlaceMover(HantoCoordinate at) 
	{
		return new PlaceMover(this, at);
	}

	@Override
	public IHantoMover createWalkMover(HantoCoordinate to) 
	{
		return null;
	}

}
