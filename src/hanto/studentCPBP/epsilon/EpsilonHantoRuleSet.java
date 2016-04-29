package hanto.studentCPBP.epsilon;

import hanto.common.HantoPlayerColor;
import hanto.studentCPBP.common.GenericHantoRuleCollection;
import hanto.studentCPBP.common.IHantoGameState;
import hanto.studentCPBP.common.IHantoMover;
import hanto.studentCPBP.common.IHantoMoverValidator;

public class EpsilonHantoRuleSet extends GenericHantoRuleCollection 
{
	
	
	public EpsilonHantoRuleSet(HantoPlayerColor startingPlayer) {
		super(startingPlayer);
		// TODO Auto-generated constructor stub
	}

	@Override
	public IHantoMoverValidator createMoverValidator(IHantoMover mover) 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onNoInput(IHantoGameState state) {
		// TODO Auto-generated method stub
		
	}

}
