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

public class DeltaHantoWalkMoverValidator implements IHantoMoverValidator 
{
	private WalkMover mover;
	private IHantoRuleSet rules;
	private int stepsLeft;
	
	/**
	 * Creates a validator for walking pieces.
	 * @param mover The given WalkMover
	 * @param rules the rules for this version of Hanto
	 */
	public DeltaHantoWalkMoverValidator(WalkMover mover, IHantoRuleSet rules, int maxSteps) 
	{
		this.mover = mover;
		this.rules = rules;
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
			HantoCommonPiece[] pieces = board.getPieces(coord);
			for(HantoCommonPiece piece : pieces)
			{
				if(piece.getType() == HantoPieceType.BUTTERFLY && piece.getColor() == rules.getCurrentTurn())
				{
					return;
				}
			}
		}
		
		throw new HantoException("Cannot move piece before placing your butterfly");
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