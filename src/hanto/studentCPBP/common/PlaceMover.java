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
import hanto.common.HantoPiece;

/**
 * Mover for piece placement.
 * @author Benny Peake bpeake
 * @author Connor Porell cgporell
 */
public class PlaceMover implements IHantoMover
{
	private CommonHantoPiece piece;
	private HantoCoordinate at;
	
	public HantoCoordinate getTargetLocation()
	{
		return new HantoCoordinateImpl(at);
	}
	
	public CommonHantoPiece getPiece()
	{
		return piece;
	}
	
	/**
	 * Builds a PlaceMover for the given piece and location
	 * @param piece The piece we are placing
	 * @param at The location we are placing at
	 */
	public PlaceMover(CommonHantoPiece piece, HantoCoordinate at)
	{
		this.piece = piece;
		this.at = at;
	}
	
	@Override
	public boolean iterateMove(IHantoBoard board)
	{
		board.addPiece(piece, at);
		
		HantoCoordinate[] adjacent = board.getAdjacent(at);
		int ofSameColor = 0;
		for(HantoCoordinate coord : adjacent)
		{
			HantoPiece[] pieces = board.getPieces(at);
			for(HantoPiece adjPiece : pieces)
			{
				if(adjPiece.getColor() == piece.getColor())
				{
					ofSameColor++;
				}
			}
		}
		
		return false;
	}

}
