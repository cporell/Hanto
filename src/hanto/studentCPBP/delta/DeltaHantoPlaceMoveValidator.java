package hanto.studentCPBP.delta;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPiece;
import hanto.studentCPBP.common.IHantoBoard;
import hanto.studentCPBP.common.IHantoMoverValidator;
import hanto.studentCPBP.common.IHantoRuleSet;
import hanto.studentCPBP.common.PlaceMover;

public class DeltaHantoPlaceMoveValidator implements IHantoMoverValidator 
{
	private PlaceMover mover;
	private DeltaHantoRuleSet rules;
	
	/**
	 * Creates a validator for placing pieces.
	 * @param mover The given PlaceMover
	 * @param rules the rules for this version of Hanto
	 */
	public DeltaHantoPlaceMoveValidator(PlaceMover mover, DeltaHantoRuleSet rules)
	{
		this.mover = mover;
		this.rules = rules;
	}
	
	@Override
	public void checkIteration(IHantoBoard board) throws HantoException 
	{
		if(rules.getCurrentHand().getCountOfPieceInHand(mover.getPiece().getType()) <= 0)
		{
			throw new HantoException("You cannot place a piece of type: " +
									 mover.getPiece().getType().toString() +
									 ". None are left in your hand.");
		}
		
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
		
		rules.getCurrentHand().takePieceFromHand(mover.getPiece().getType());
	}
}