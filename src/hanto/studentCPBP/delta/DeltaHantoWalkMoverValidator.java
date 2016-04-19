package hanto.studentCPBP.delta;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPieceType;
import hanto.studentCPBP.common.HantoCommonPiece;
import hanto.studentCPBP.common.HantoCoordinateImpl;
import hanto.studentCPBP.common.IHantoBoard;
import hanto.studentCPBP.common.IHantoMoverValidator;
import hanto.studentCPBP.common.IHantoRuleSet;
import hanto.studentCPBP.common.WalkMover;

public class DeltaHantoWalkMoverValidator extends DeltaCommonMovementMoverValidator 
{
	private int stepsLeft;
	
	/**
	 * Creates a validator for walking pieces.
	 * @param mover The given WalkMover
	 * @param rules the rules for this version of Hanto
	 */
	public DeltaHantoWalkMoverValidator(WalkMover mover, DeltaHantoRuleSet rules, int maxSteps) 
	{
		super(mover, rules);
		this.stepsLeft = maxSteps;
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

	private void checkNotMovingToManySpaces() throws HantoException {
		stepsLeft--;
		if(stepsLeft < 0)
		{
			throw new HantoException("Moved to many spaces");
		}
	}

	private void checkNotMovingThroughPieces(IHantoBoard board) throws HantoException {
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

	protected void checkNotMovingBeforeButterflyPlaced(IHantoBoard board) throws HantoException {
		if(!getRules().getCurrentHand().getButterflyPlaced())
		{
			throw new HantoException("Cannot move piece before placing your butterfly");
		}
	}

	protected void checkNotMovingToSameSpace(IHantoBoard board) throws HantoException
	{
		if(getMover().getTargetLocation().equals(board.getPieceLocation(getMover().getPiece())))
		{
			throw new HantoException("Cannot move to the same location.");
		}
	}

	protected void checkIsMovingOurPiece() throws HantoException {
		if(getRules().getCurrentTurn() != getMover().getPiece().getColor())
		{
			throw new HantoException("Cannot move piece that is not your color.");
		}
	}
}