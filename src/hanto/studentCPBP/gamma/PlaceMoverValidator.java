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
import hanto.common.HantoException;
import hanto.common.HantoPiece;
import hanto.studentCPBP.common.IHantoBoard;
import hanto.studentCPBP.common.IHantoMoverValidator;
import hanto.studentCPBP.common.IHantoRuleSet;
import hanto.studentCPBP.common.PlaceMover;

/**
 * Validator for placing pieces.
 * @author Benny Peake bpeake
 * @author Connor Porell cgporell
 */
public class PlaceMoverValidator implements IHantoMoverValidator
{
	private PlaceMover mover;
	private IHantoRuleSet rules;
	
	/**
	 * Creates a validator for placing pieces.
	 * @param mover The given PlaceMover
	 * @param rules the rules for this version of Hanto
	 */
	public PlaceMoverValidator(PlaceMover mover, IHantoRuleSet rules)
	{
		this.mover = mover;
		this.rules = rules;
	}
	
	@Override
	public void checkIteration(IHantoBoard board) throws HantoException 
	{
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
	}

}
