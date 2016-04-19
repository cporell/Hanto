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

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPiece;
import hanto.studentCPBP.common.IHantoBoard;
import hanto.studentCPBP.common.IHantoMoverValidator;
import hanto.studentCPBP.common.PlaceMover;

/**
 * Place Mover Validator for Delta Hanto
 * @author cgporell
 * @author bpeake
 *
 */
public class DeltaHantoPlaceMoverValidator implements IHantoMoverValidator {

	private PlaceMover mover;
	private DeltaHantoRuleSet rules;
	
	/**
	 * Creates a validator for placing pieces.
	 * @param mover The given PlaceMover
	 * @param rules the rules for this version of Hanto
	 */
	public DeltaHantoPlaceMoverValidator(PlaceMover mover, DeltaHantoRuleSet rules)
	{
		this.mover = mover;
		this.rules = rules;
	}
	
	@Override
	public void checkIteration(IHantoBoard board) throws HantoException 
	{
		if(!rules.getCurrentHand().checkHandForType(mover.getPiece().getType()))
		{
			throw new HantoException("Invalid piece placed on board.");
		}
		
		if(rules.getCurrentHand().getCountOfPieceInHand(mover.getPiece().getType()) <= 0)
		{
			throw new HantoException("You cannot place a piece of type: " +
									 mover.getPiece().getType().toString() +
									 ". None are left in your hand.");
		}
		
		if(rules.getTurnNumber() > 1)
		{
			HantoCoordinate[] adjacent = board.getAdjacent(mover.getTargetLocation());
			int adjOfSameColor = 0;
			int adjOfOpntColor = 0;
			for(HantoCoordinate coord : adjacent)
			{
				HantoPiece[] pieces = board.getPieces(coord);
				for(HantoPiece piece : pieces)
				{
					if(piece.getColor() == mover.getPiece().getColor())
					{
						adjOfSameColor++;
					}
					else
					{
						adjOfOpntColor++;
					}
				}
			}
			
			if(adjOfSameColor < 1)
			{
				throw new HantoException("Piece must be placed next to a piece of the same color.");
			}
			
			if(adjOfOpntColor > 0)
			{
				throw new HantoException("You cannot place next to an opponents piece.");
			}
		}
		rules.getCurrentHand().takePieceFromHand(mover.getPiece().getType());
	}
}
