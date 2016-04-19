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

import hanto.common.HantoCoordinate;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.studentCPBP.common.HantoCommonPiece;
import hanto.studentCPBP.common.IHantoMover;
import hanto.studentCPBP.common.PlaceMover;
import hanto.studentCPBP.common.WalkMover;

/**
 * Implementation of Sparrow piece for Gamma Hanto
 * @author Benny Peake bpeake
 * @author Connor Porell cgporell
 */
public class GammaHantoSparrowPiece extends HantoCommonPiece
{

	/**
	 * Builds a Sparrow piece
	 * @param color Owner of this piece
	 */
	public GammaHantoSparrowPiece(HantoPlayerColor color)
	{
		super(color, HantoPieceType.SPARROW);
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