/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentCPBP.common;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;

/**
 * Fly Mover handles checking movement for pieces that move by Flying
 * @author bpeake
 * @author cgporell
 *
 */
public class FlyMover implements IHantoMover
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
	public FlyMover(CommonHantoPiece piece, HantoCoordinate to) 
	{
		this.piece = piece;
		this.to = to; 
	}
	
	@Override
	public boolean iterateMove(IHantoBoard board) throws HantoException 
	{
		board.movePiece(piece, to);
		
		return false;
	}

}
