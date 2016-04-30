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

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.studentCPBP.common.IHantoGameState;
import hanto.studentCPBP.common.movers.FlyMover;

/**
 * EpsilonHantoFlyMoverValidator checks legality of Fly moves
 * @author bpeake
 * @author cgporell
 *
 */
public class EpsilonHantoFlyMoverValidator extends EpsilonCommonMovementMoverValidator
{
	private int maxDist;
	
	/**
	 * Creates a validator for walking pieces.
	 * @param mover The given WalkMover
	 * @param rules the rules for this version of Hanto
	 * @param maxDist The maximum distance we can fly in this version
	 */
	public EpsilonHantoFlyMoverValidator(FlyMover mover, EpsilonHantoRuleSet rules, int maxDist) 
	{
		super(mover, rules);
		this.maxDist = maxDist;
	}

	@Override
	public void checkIteration(IHantoGameState state) throws HantoException 
	{
		checkNotMovingTooFar(state);
		checkNotMovingToSameSpace(state);
		checkIsMovingOurPiece(state);
		checkNotMovingBeforeButterflyPlaced(state);
	}

	private void checkNotMovingTooFar(IHantoGameState state) throws HantoException
	{
		HantoCoordinate from = getMover().getOriginLocation();
		HantoCoordinate to = getMover().getTargetLocation();
		
		int deltaX = Math.abs(to.getX() - from.getX());
		int deltaY = Math.abs(to.getY() - from.getY());
		int delta = Math.abs(deltaY - deltaX);
		
		int dist = Math.max(deltaX, Math.max(deltaY, delta));
		if(dist > maxDist)
		{
			throw new HantoException("Flew too many spaces");
		}	
	}

	@Override
	public void onInvalidMoveHandled(IHantoGameState state) throws HantoException 
	{
	}
}
