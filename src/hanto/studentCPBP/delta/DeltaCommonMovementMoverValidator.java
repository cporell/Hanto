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

import hanto.common.HantoException;
import hanto.studentCPBP.common.IHantoBoard;
import hanto.studentCPBP.common.IHantoMover;
import hanto.studentCPBP.common.IHantoMoverValidator;

/**
 * This is a base helper class to use with any validator for movement.
 * @author Benny
 *
 */
public abstract class DeltaCommonMovementMoverValidator implements IHantoMoverValidator 
{
	private IHantoMover mover;
	private DeltaHantoRuleSet rules;
	
	/**
	 * Creates a validator for walking pieces.
	 * @param mover The given WalkMover
	 * @param rules the rules for this version of Hanto
	 */
	public DeltaCommonMovementMoverValidator(IHantoMover mover, DeltaHantoRuleSet rules) 
	{
		this.mover = mover;
		this.rules = rules;
	}
	
	public IHantoMover getMover()
	{
		return mover;
	}
	
	public DeltaHantoRuleSet getRules()
	{
		return rules;
	}

	/**
	 * Check that we are not attempting to move a piece to the same spot it started from
	 * (i.e. moving in place)
	 * @param board The current game state
	 * @throws HantoException If we are attempting to move in place
	 */
	protected void checkNotMovingToSameSpace(IHantoBoard board) throws HantoException
	{
		if(getMover().getTargetLocation().equals(board.getPieceLocation(getMover().getPiece())))
		{
			throw new HantoException("Cannot move to the same location.");
		}
	}

	/**
	 * Check that we are only moving pieces of our own color
	 * @throws HantoException If we attempt to move an opponent's piece
	 */
	protected void checkIsMovingOurPiece() throws HantoException 
	{
		if(getRules().getCurrentTurn() != getMover().getPiece().getColor())
		{
			throw new HantoException("Cannot move piece that is not your color.");
		}
	}
	
	/**
	 * Check that we are not attempting to move a piece if our Butterfly is not on the board
	 * @param board The current game state
	 * @throws HantoException If we attempt to move a piece before placing the Butterfly
	 */
	protected void checkNotMovingBeforeButterflyPlaced(IHantoBoard board) throws HantoException 
	{
		if(!getRules().getCurrentHand().getButterflyPlaced())
		{
			throw new HantoException("Cannot move piece before placing your butterfly");
		}
	}
}
