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
import hanto.common.HantoPieceType;
import hanto.studentCPBP.common.IHantoGameState;
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
	 * @param state The current game state
	 * @throws HantoException If we are attempting to move in place
	 */
	protected void checkNotMovingToSameSpace(IHantoGameState state) throws HantoException
	{
		if(getMover().getTargetLocation().equals(state.getPieceLocation(getMover().getPiece())))
		{
			throw new HantoException("Cannot move to the same location.");
		}
	}

	/**
	 * Check that we are only moving pieces of our own color
	 * @param state The current game state
	 * @throws HantoException If we attempt to move an opponent's piece
	 */
	protected void checkIsMovingOurPiece(IHantoGameState state) throws HantoException 
	{
		if(getRules().getCurrentPlayer(state) != getMover().getPiece().getColor())
		{
			throw new HantoException("Cannot move piece that is not your color.");
		}
	}
	
	/**
	 * Check that we are not attempting to move a piece if our Butterfly is not on the board
	 * @param state The current game state
	 * @throws HantoException If we attempt to move a piece before placing the Butterfly
	 */
	protected void checkNotMovingBeforeButterflyPlaced(IHantoGameState state) throws HantoException 
	{
		if(state.getPieces(getRules().getCurrentPlayer(state), HantoPieceType.BUTTERFLY).length == 0)
		{
			throw new HantoException("Cannot move piece before placing your butterfly");
		}
	}
}
