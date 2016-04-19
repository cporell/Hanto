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

import java.util.HashSet;
import java.util.Set;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.studentCPBP.common.IHantoBoard;
import hanto.studentCPBP.common.WalkMover;

/**
 * DeltaHantoWalkMoverValidator validates Walking behavior for any number of steps
 * @author bpeake
 *
 */
public class DeltaHantoWalkMoverValidator extends DeltaCommonMovementMoverValidator 
{
	private int stepsLeft;
	
	/**
	 * Creates a validator for walking pieces.
	 * @param mover The given WalkMover
	 * @param rules the rules for this version of Hanto
	 * @param maxSteps the maximum amount of spaces this piece can walk in a turn
	 */
	public DeltaHantoWalkMoverValidator(WalkMover mover, DeltaHantoRuleSet rules, int maxSteps) 
	{
		super(mover, rules);
		stepsLeft = maxSteps;
	}
	
	@Override
	public void checkIteration(IHantoBoard board) throws HantoException 
	{
		checkNotMovingToManySpaces();
		checkIsMovingOurPiece();
		checkNotMovingToSameSpace(board);
		checkNotMovingBeforeButterflyPlaced(board);		
		checkNotMovingThroughPieces(board);
	}

	private void checkNotMovingToManySpaces() throws HantoException 
	{
		stepsLeft--;
		if(stepsLeft < 0)
		{
			throw new HantoException("Moved to many spaces");
		}
	}

	private void checkNotMovingThroughPieces(IHantoBoard board) throws HantoException
	{
		HantoCoordinate from = board.getPieceLocation(getMover().getPiece());
		HantoCoordinate to = getMover().getTargetLocation();
		
		HantoCoordinate[] adjFrom = board.getAdjacent(from);
		HantoCoordinate[] adjTo = board.getAdjacent(to);
		
		Set<HantoCoordinate> adjFromTakenSet = new HashSet<>();
		Set<HantoCoordinate> adjToTakenSet = new HashSet<>();
		
		for(HantoCoordinate coord : adjFrom)
		{
			if(board.getPieces(coord).length > 0)
			{
				adjFromTakenSet.add(coord);
			}
		}
		
		for(HantoCoordinate coord : adjTo)
		{
			if(board.getPieces(coord).length > 0)
			{
				adjToTakenSet.add(coord);
			}
		}
		
		adjFromTakenSet.retainAll(adjToTakenSet);
		
		if(adjFromTakenSet.size() >= 2)
		{
			throw new HantoException("Cannot move piece through other pieces");
		}
	}
}