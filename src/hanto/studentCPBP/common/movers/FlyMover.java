/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentCPBP.common.movers;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.studentCPBP.common.CommonHantoPiece;
import hanto.studentCPBP.common.HantoCoordinateImpl;
import hanto.studentCPBP.common.IHantoGameState;
import hanto.studentCPBP.common.IHantoMover;

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
	private HantoCoordinate from;
	
	public HantoCoordinate getTargetLocation()
	{
		return new HantoCoordinateImpl(to);
	}
	
	public HantoCoordinate getOriginLocation()
	{
		return new HantoCoordinateImpl(from);
	}
	
	public CommonHantoPiece getPiece()
	{
		return piece;
	}
	
	/**
	 * Builds a WalkMover for the given piece and location
	 * @param piece The piece we are placing
	 * @param from Where the piece is moving from
	 * @param to The location we are placing at
	 */
	public FlyMover(CommonHantoPiece piece, HantoCoordinate from, HantoCoordinate to) 
	{
		this.piece = piece;
		this.to = to; 
		this.from = from;
	}
	
	@Override
	public boolean iterateMove(IHantoGameState state) throws HantoException 
	{
		state.movePiece(piece, to);
		
		return false;
	}

	@Override
	public boolean handleInvalidIteration(IHantoGameState state) 
	{
		return false;
	}

	@Override
	public void reset(IHantoGameState state) 
	{
		state.movePiece(piece, from);
	}
}
