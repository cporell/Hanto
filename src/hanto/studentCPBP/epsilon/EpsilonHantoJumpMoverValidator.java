package hanto.studentCPBP.epsilon;

import java.util.Arrays;
import java.util.HashSet;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.studentCPBP.common.HantoCoordinateImpl;
import hanto.studentCPBP.common.IHantoGameState;
import hanto.studentCPBP.common.IHantoMoverValidator;
import hanto.studentCPBP.common.movers.JumpMover;

public class EpsilonHantoJumpMoverValidator extends EpsilonCommonMovementMoverValidator {

	/**
	 * Creates a jump mover validator
	 * @param mover The given JumpMover
	 * @param rules The current rule set
	 */
	public EpsilonHantoJumpMoverValidator(JumpMover mover, EpsilonHantoRuleSet rules)
	{
		super(mover, rules);
	}
	
	@Override
	public void checkIteration(IHantoGameState state) throws HantoException 
	{
		checkNotMovingAdjacent(state);
		checkNotMovingToSameSpace(state);
		checkMovingInStraightLine(state);
		checkOnlyMovingOverOccupiedTiles(state);
		checkIsMovingOurPiece(state);
		checkNotMovingBeforeButterflyPlaced(state);		
	}

	private void checkNotMovingAdjacent(IHantoGameState state) throws HantoException
	{
		HantoCoordinate[] adjacents = state.getAdjacent(getMover().getOriginLocation());	
		HashSet<HantoCoordinate> adj = new HashSet<HantoCoordinate>(Arrays.asList(adjacents));
		if(adj.contains(getMover().getTargetLocation()))
		{
			throw new HantoException("You cannot Jump to an adjacent space.");
		}
	}

	private void checkOnlyMovingOverOccupiedTiles(IHantoGameState state) throws HantoException
	{
		// Get direction
		HantoCoordinate from = getMover().getOriginLocation();
		HantoCoordinate to = getMover().getTargetLocation();
		int offsetX = to.getX() - from.getX();
		int offsetY = to.getY() - from.getY();
		int dirX = (int) Math.signum(offsetX);
		int dirY = (int) Math.signum(offsetY);
		
		HantoCoordinateImpl target = new HantoCoordinateImpl(from.getX() + dirX, from.getY() + dirY);
		
		while(!target.equals(to))
		{
			if(state.getPieces(target).length == 0)
			{
				throw new HantoException("Can only Jump over occupied spaces");
			}
			// increment target by 1 unit in the direction
			target = new HantoCoordinateImpl(target.getX() + dirX, target.getY() + dirY);
		}
	}

	private void checkMovingInStraightLine(IHantoGameState state) throws HantoException 
	{
		HantoCoordinate from = getMover().getOriginLocation();
		HantoCoordinate to = getMover().getTargetLocation();
		
		int deltaX = to.getX() - from.getX();
		int deltaY = to.getY() - from.getY();
		
		if(!((from.getX() != to.getX() && from.getY() == to.getY()) ||
			(from.getY() != to.getY() && from.getX() == to.getX()) ||
			(deltaX + deltaY == 0)))
		{
			throw new HantoException("Can only move the Horse in a straight line.");
		}
	}

	@Override
	public void onInvalidMoveHandled(IHantoGameState state) throws HantoException 
	{
	}

}
