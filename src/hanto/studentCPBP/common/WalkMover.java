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
import hanto.common.HantoException;

/**
 * Mover for the "walk" behavior.
 * @author Benny Peake bpeake
 * @author Connor Porell cgporell
 */
public class WalkMover implements IHantoMover
{
	private CommonHantoPiece piece;
	private HantoCoordinate to;
	
	public HantoCoordinate getTargetLocation()
	{
		return new HantoCoordinateImpl(to);
	}
	
	public CommonHantoPiece getPiece()
	{
		return piece;
	}
	
	/**
	 * Builds a WalkMover for the given piece and location
	 * @param piece The piece we are placing
	 * @param to The location we are placing at
	 */
	public WalkMover(CommonHantoPiece piece, HantoCoordinate to) 
	{
		this.piece = piece;
		this.to = to; 
	}
	
	@Override
	public boolean iterateMove(IHantoGameState state) throws HantoException 
	{
		HantoCoordinate current = state.getPieceLocation(piece);
		int offsetX = to.getX() - current.getX();
		int offsetY = to.getY() - current.getY();
		int dirX = (int) Math.signum(offsetX);
		int dirY = (int) Math.signum(offsetY);
		
		HantoCoordinateImpl target = new HantoCoordinateImpl(current.getX() + dirX, current.getY() + dirY);
		state.movePiece(piece, target);
		
		return !(target.getX() == to.getX() && target.getY() == to.getY());
	}

}
