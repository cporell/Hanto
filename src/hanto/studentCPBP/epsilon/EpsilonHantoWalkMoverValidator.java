/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentCPBP.epsilon;

import java.util.HashSet;
import java.util.Set;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.studentCPBP.common.IHantoGameState;
import hanto.studentCPBP.common.movers.WalkMover;

/**
 * EpsilonHantoWalkMoverValidator validates Walking behavior for any number of steps
 * @author bpeake
 *
 */
public class EpsilonHantoWalkMoverValidator extends EpsilonCommonMovementMoverValidator 
{
	private int stepsLeft;
	
	private HantoCoordinate itrStart;
	
	/**
	 * Creates a validator for walking pieces.
	 * @param mover The given WalkMover
	 * @param rules the rules for this version of Hanto
	 * @param maxSteps the maximum amount of spaces this piece can walk in a turn
	 */
	public EpsilonHantoWalkMoverValidator(WalkMover mover, EpsilonHantoRuleSet rules, int maxSteps) 
	{
		super(mover, rules);
		stepsLeft = maxSteps;
	}
	
	@Override
	public void checkIteration(IHantoGameState state) throws HantoException 
	{
		checkNotMovingToManySpaces();
		checkIsMovingOurPiece(state);
		checkNotMovingToSameSpace(state);
		checkNotMovingBeforeButterflyPlaced(state);		
		checkNotMovingThroughPieces(state);
	}

	private void checkNotMovingToManySpaces() throws HantoException 
	{
		stepsLeft--;
		if(stepsLeft < 0)
		{
			throw new HantoException("Moved to many spaces");
		}
	}

	private void checkNotMovingThroughPieces(IHantoGameState state) throws HantoException
	{
		HantoCoordinate from = itrStart;
		HantoCoordinate to = state.getPieceLocation(getMover().getPiece());
		
		HantoCoordinate[] adjFrom = state.getAdjacent(from);
		HantoCoordinate[] adjTo = state.getAdjacent(to);
		
		Set<HantoCoordinate> adjFromTakenSet = new HashSet<>();
		Set<HantoCoordinate> adjToTakenSet = new HashSet<>();
		
		for(HantoCoordinate coord : adjFrom)
		{
			if(state.getPieces(coord).length > 0)
			{
				adjFromTakenSet.add(coord);
			}
		}
		
		for(HantoCoordinate coord : adjTo)
		{
			if(state.getPieces(coord).length > 0)
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

	@Override
	public void onInvalidMoveHandled(IHantoGameState state) throws HantoException 
	{
		throw new HantoException("Cannot walk to location.");
	}

	@Override
	public void preIteration(IHantoGameState state) 
	{
		itrStart = state.getPieceLocation(getMover().getPiece());
	}
}