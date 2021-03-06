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
import hanto.studentCPBP.common.IHantoGameState;
import hanto.studentCPBP.common.movers.FlyMover;

/**
 * DeltaHantoFlyMoverValidator checks legality of Fly moves
 * @author bpeake
 * @author cgporell
 *
 */
public class DeltaHantoFlyMoverValidator extends DeltaCommonMovementMoverValidator
{	
	/**
	 * Creates a validator for walking pieces.
	 * @param mover The given WalkMover
	 * @param rules the rules for this version of Hanto
	 */
	public DeltaHantoFlyMoverValidator(FlyMover mover, DeltaHantoRuleSet rules) 
	{
		super(mover, rules);
	}

	@Override
	public void checkIteration(IHantoGameState state) throws HantoException 
	{
		checkNotMovingToSameSpace(state);
		checkIsMovingOurPiece(state);
		checkNotMovingBeforeButterflyPlaced(state);
	}

	@Override
	public void onInvalidMoveHandled(IHantoGameState state) throws HantoException 
	{
	}

	@Override
	public void preIteration(IHantoGameState state) 
	{
	}
}
