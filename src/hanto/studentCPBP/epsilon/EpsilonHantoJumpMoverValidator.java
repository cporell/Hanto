package hanto.studentCPBP.epsilon;

import hanto.common.HantoException;
import hanto.studentCPBP.common.IHantoGameState;
import hanto.studentCPBP.common.IHantoMoverValidator;
import hanto.studentCPBP.common.movers.FlyMover;
import hanto.studentCPBP.common.movers.JumpMover;

public class EpsilonHantoJumpMoverValidator implements IHantoMoverValidator {

	public EpsilonHantoJumpMoverValidator(JumpMover mover, EpsilonHantoRuleSet rules)
	{
	}
	
	@Override
	public void checkIteration(IHantoGameState state) throws HantoException 
	{
		
	}

	@Override
	public void onInvalidMoveHandled(IHantoGameState state) throws HantoException 
	{
	}

}
