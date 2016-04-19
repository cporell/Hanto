package hanto.studentCPBP.delta;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPieceType;
import hanto.studentCPBP.common.FlyMover;
import hanto.studentCPBP.common.HantoCommonPiece;
import hanto.studentCPBP.common.IHantoBoard;
import hanto.studentCPBP.common.IHantoMoverValidator;
import hanto.studentCPBP.common.IHantoRuleSet;
import hanto.studentCPBP.common.WalkMover;

public class DeltaHantoFlyMoverValidator implements IHantoMoverValidator
{
	private FlyMover mover;
	private IHantoRuleSet rules;
	
	/**
	 * Creates a validator for walking pieces.
	 * @param mover The given WalkMover
	 * @param rules the rules for this version of Hanto
	 */
	public DeltaHantoFlyMoverValidator(FlyMover mover, IHantoRuleSet rules) 
	{
		this.mover = mover;
		this.rules = rules;
	}

	@Override
	public void checkIteration(IHantoBoard board) throws HantoException 
	{
		checkNotMovingToSameSpace(board);
		checkIsMovingOurPiece();
		checkNotMovingBeforeButterflyPlaced(board);
	}

	private void checkNotMovingToSameSpace(IHantoBoard board) throws HantoException
	{
		if(mover.getTargetLocation().equals(board.getPieceLocation(mover.getPiece())))
		{
			throw new HantoException("Cannot move to the same location.");
		}
	}

	private void checkIsMovingOurPiece() throws HantoException 
	{
		if(rules.getCurrentTurn() != mover.getPiece().getColor())
		{
			throw new HantoException("Cannot move piece that is not your color.");
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
}
