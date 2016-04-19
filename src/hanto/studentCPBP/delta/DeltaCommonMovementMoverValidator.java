package hanto.studentCPBP.delta;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPieceType;
import hanto.studentCPBP.common.FlyMover;
import hanto.studentCPBP.common.HantoCommonPiece;
import hanto.studentCPBP.common.IHantoBoard;
import hanto.studentCPBP.common.IHantoMover;
import hanto.studentCPBP.common.IHantoMoverValidator;
import hanto.studentCPBP.common.IHantoRuleSet;

/**
 * This is a base helper class to use with any validator for movement.
 * @author Benny
 *
 */
public abstract class DeltaCommonMovementMoverValidator implements IHantoMoverValidator 
{
	private IHantoMover mover;
	private DeltaHantoRuleSet rules;
	
	/**
	 * Creates a validator for walking pieces.
	 * @param mover The given WalkMover
	 * @param rules the rules for this version of Hanto
	 */
	public DeltaCommonMovementMoverValidator(IHantoMover mover, DeltaHantoRuleSet rules) 
	{
		this.mover = mover;
		this.rules = rules;
	}
	
	public IHantoMover getMover()
	{
		return mover;
	}
	
	public DeltaHantoRuleSet getRules()
	{
		return rules;
	}

	protected void checkNotMovingToSameSpace(IHantoBoard board) throws HantoException
	{
		if(getMover().getTargetLocation().equals(board.getPieceLocation(getMover().getPiece())))
		{
			throw new HantoException("Cannot move to the same location.");
		}
	}

	protected void checkIsMovingOurPiece() throws HantoException 
	{
		if(getRules().getCurrentTurn() != getMover().getPiece().getColor())
		{
			throw new HantoException("Cannot move piece that is not your color.");
		}
	}
	
	protected void checkNotMovingBeforeButterflyPlaced(IHantoBoard board) throws HantoException 
	{
		if(!getRules().getCurrentHand().getButterflyPlaced())
		{
			throw new HantoException("Cannot move piece before placing your butterfly");
		}
	}
}
