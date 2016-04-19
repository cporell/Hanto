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
	public void checkIteration(IHantoBoard board) throws HantoException 
	{
		checkNotMovingToSameSpace(board);
		checkIsMovingOurPiece();
		checkNotMovingBeforeButterflyPlaced(board);
	}
}
