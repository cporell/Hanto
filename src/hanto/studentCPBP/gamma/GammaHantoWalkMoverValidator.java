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

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPieceType;
import hanto.studentCPBP.common.CommonHantoPiece;
import hanto.studentCPBP.common.HantoCoordinateImpl;
import hanto.studentCPBP.common.IHantoBoard;
import hanto.studentCPBP.common.IHantoMoverValidator;
import hanto.studentCPBP.common.IHantoRuleSet;
import hanto.studentCPBP.common.WalkMover;

/**
 * Move validator for the "walk" behavior.
 * @author Benny Peake bpeake
 * @author Connor Porell cgporell
 */
public class GammaHantoWalkMoverValidator implements IHantoMoverValidator 
{
	private WalkMover mover;
	private IHantoRuleSet rules;
	
	/**
	 * Creates a validator for walking pieces.
	 * @param mover The given WalkMover
	 * @param rules the rules for this version of Hanto
	 */
	public GammaHantoWalkMoverValidator(WalkMover mover, IHantoRuleSet rules) 
	{
		this.mover = mover;
		this.rules = rules;
	}
	
	@Override
	public void checkIteration(IHantoBoard board) throws HantoException 
	{
		checkIsMovingOurPiece();
		checkNotMovingToSameSpace(board);
		checkNotMovingMoreThanOneSpace(board);
		checkNotMovingBeforeButterflyPlaced(board);		
		checkNotMovingThroughPieces(board);
	}

	private void checkNotMovingThroughPieces(IHantoBoard board) throws HantoException {
		HantoCoordinate from = board.getPieceLocation(mover.getPiece());
		HantoCoordinate to = mover.getTargetLocation();
		
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

	private void checkNotMovingBeforeButterflyPlaced(IHantoBoard board) throws HantoException {
		HantoCoordinate[] takenCoords = board.getAllTakenLocations();
		for(HantoCoordinate coord : takenCoords)
		{
			CommonHantoPiece[] pieces = board.getPieces(coord);
			for(CommonHantoPiece piece : pieces)
			{
				if(piece.getType() == HantoPieceType.BUTTERFLY && piece.getColor() == rules.getCurrentTurn())
				{
					return;
				}
			}
		}
		
		throw new HantoException("Cannot move piece before placing your butterfly");
	}

	private void checkNotMovingMoreThanOneSpace(IHantoBoard board) throws HantoException {
		Set<HantoCoordinate> adjacent = new HashSet<>(Arrays.asList(board.getAdjacent(board.getPieceLocation(mover.getPiece()))));
		if(!adjacent.contains(new HantoCoordinateImpl(mover.getTargetLocation())))
		{
			throw new HantoException("Cannot walk more than one space.");
		}
	}

	private void checkNotMovingToSameSpace(IHantoBoard board) throws HantoException
	{
		if(mover.getTargetLocation().equals(board.getPieceLocation(mover.getPiece())))
		{
			throw new HantoException("Cannot move to the same location.");
		}
	}

	private void checkIsMovingOurPiece() throws HantoException {
		if(rules.getCurrentTurn() != mover.getPiece().getColor())
		{
			throw new HantoException("Cannot move piece that is not your color.");
		}
	}

}
